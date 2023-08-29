package com.chen.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.chen.music.mapper.RefreshTokenDao;
import com.chen.music.mapper.SettingsDao;
import com.chen.music.mapper.UserNotPasswordDao;
import com.chen.music.pojo.RefreshToken;
import com.chen.music.pojo.Settings;
import com.chen.music.pojo.User;
import com.chen.music.mapper.UserDao;
import com.chen.music.pojo.UserNotPassword;
import com.chen.music.response.ResponseResult;
import com.chen.music.response.ResponseState;
import com.chen.music.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.music.utils.*;
import com.google.gson.Gson;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author chen
 * @since 2023-07-31
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private SettingsDao settingsDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserNotPasswordDao userNotPasswordDao;

    @Autowired
    private RefreshTokenDao refreshTokenDao;

    @Autowired
    private IdWorker idWorker;

    @Autowired
    private Random random;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${music.system.image.save-path-img}")
    public String savePath;

    @Value("${music.system.image.default-avatar}")
    public String defaultAvatar;

    @Autowired
    private Gson gson;



    public static final int[] captchaFontTypes = {
            Captcha.FONT_1,
            Captcha.FONT_2,
            Captcha.FONT_3,
            Captcha.FONT_4,
            Captcha.FONT_5,
            Captcha.FONT_6,
            Captcha.FONT_7,
            Captcha.FONT_8,
            Captcha.FONT_9,
            Captcha.FONT_10,
    };

    @Override
    public ResponseResult initUserManagerAccount(User admin, HttpServletRequest request) {
        QueryWrapper<Settings> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("`key`", Constants.Settings.HAS_MANAGER_ACCOUNT_INT_STATE);
        Settings managerAccountState = settingsDao.selectOne(queryWrapper);
        if (managerAccountState != null) {
            return ResponseResult.FAILED("超级管理员账号已经初始化过了");
        }
        //检查数据
        if (TextUtils.isEmpty(admin.getUserName())) {
            return ResponseResult.FAILED("用户名不能为空");
        }
        if (TextUtils.isEmpty(admin.getPassword())) {
            return ResponseResult.FAILED("密码不能为空");
        }
        if (TextUtils.isEmpty(admin.getEmail())) {
            return ResponseResult.FAILED("Email不能为空");
        }
        admin.setId(String.valueOf(idWorker.nextId()));
        admin.setRoleId(Constants.AdminPermission.ROLE_SUPER_ADMIN);
        admin.setAvatar(defaultAvatar);
        admin.setState(Constants.User.DEFAULT_STATE);
        String localAddr = request.getLocalAddr();
        String remoteAddr = request.getRemoteAddr();
        admin.setRegIp(remoteAddr);
        admin.setLoginIp(remoteAddr);
        admin.setCreateTime(new Date());
        admin.setUpdateTime(new Date());
        String password = admin.getPassword();
        String encode = bCryptPasswordEncoder.encode(password);
        admin.setPassword(encode);
        userDao.insert(admin);
        Settings setting = new Settings();
        setting.setKey(Constants.Settings.HAS_MANAGER_ACCOUNT_INT_STATE);
        setting.setId(idWorker.nextId() + "");
        setting.setCreateTime(new Date());
        setting.setUpdateTime(new Date());
        setting.setValue("1");
        settingsDao.insert(setting);
        return ResponseResult.SUCCESS("初始化成功");
    }

    public void createCaptcha(HttpServletResponse response, String captchaKey) throws Exception {
        if (TextUtils.isEmpty(captchaKey) || captchaKey.length() < 13) {
            return;
        }
        String key = "";
        try {
            key = captchaKey;
            //处理

        } catch (Exception e) {
            return;
        }
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        int captchaType = random.nextInt(3);
        Captcha targetCapcha = null;
        int width = 120;
        int height = 40;
        if (captchaType == 0) {
            // 三个参数分别为宽、高、位数
            targetCapcha = new SpecCaptcha(width, height, 5);

        } else if ((captchaType == 1)) {
            //gif
            targetCapcha = new GifCaptcha(width, height);
        } else {
            //算数
//            targetCapcha = new ArithmeticCaptcha(200, 60);
//            targetCapcha.setLen(2);    //几位运算
//       //     ((ArithmeticCaptcha)targetCapcha).getArithmeticString();
//       //     targetCapcha.text();

            targetCapcha = new SpecCaptcha(200, 60, 5);
        }
        int index = random.nextInt(captchaFontTypes.length);
        log.info("index == >" + index);
        targetCapcha.setFont(captchaFontTypes[index]);
        targetCapcha.setCharType(Captcha.TYPE_DEFAULT);
        String content = targetCapcha.text().toLowerCase();
        log.info(content);
        //存入rides
        //删除时机
        //1.自然过期，也就是10分钟后自己删除
        //2.验证码用完后自己删除
        //3.用完的情况：
        //
        redisUtils.set(Constants.User.KEY_CAPTCHA_CONTENT + key, content, 60 * 10);
        targetCapcha.out(response.getOutputStream());
    }

    @Autowired
    private SendEmailService emailSender;

    /**
     * 发送验证码
     * 注册register
     * 找回forget
     * 修改邮箱update
     *
     * @param request
     * @param emailAddress
     * @return
     */
    @Override
    public ResponseResult sendEmail(String type, HttpServletRequest request, String emailAddress) {
        if (emailAddress == null) {
            return ResponseResult.FAILED("邮箱地址不可以为空");
        }
        //根据类型
        if ("register".equals(type) ) {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("email", emailAddress);
            User userEmail = userDao.selectOne(userQueryWrapper);
            if (userEmail != null) {
                return ResponseResult.FAILED("该邮箱已经注册");
            }
        } else if ("forget".equals(type)|| "update".equals(type)) {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("email", emailAddress);
            List<User> userEmail = userDao.selectList(userQueryWrapper);
            if (userEmail == null) {
                return ResponseResult.FAILED("该邮箱未注册");
            }
        }
        //防止暴力发送
        String remoteAddr = request.getRemoteAddr();
//        log.info("ip ==>"+remoteAddr);
        if (remoteAddr != null) {
            remoteAddr.replaceAll(":", "_");
        }
        Integer ipSendTime = (Integer) redisUtils.get(Constants.User.KEY_EMAIL_SEND_IP + remoteAddr);
        if (ipSendTime != null && ipSendTime >= 10) {
            return ResponseResult.FAILED("发送验证码太频繁了");
        }
        Object hasSendEmail = redisUtils.get(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress);
        if (hasSendEmail != null) {
            return ResponseResult.FAILED("发送验证码太频繁了");
        }
        //检查邮箱地址
        boolean isEmail = TextUtils.isEmailAddressOk(emailAddress);
        if (!isEmail) {
            return ResponseResult.FAILED("邮箱地址不正确");
        }
        //发送验证码,6位数：100000~999999
        int code = random.nextInt(999999);
        if (code < 100000) {
            code += 100000;
        }
        log.info("sendEmail code ==>" + code);
        try {
            emailSender.sendEmailVerifyCode(String.valueOf(code), emailAddress);
        } catch (Exception e) {
            return ResponseResult.FAILED("发送失败，请稍后重试");
        }
        //记录
        if (ipSendTime == null) {
            ipSendTime = 0;
        }
        ipSendTime++;
        redisUtils.set(Constants.User.KEY_EMAIL_SEND_IP + remoteAddr, ipSendTime, 60 * 60);//1小时有效
        redisUtils.set(Constants.User.KEY_EMAIL_SEND_ADDRESS + emailAddress, true, 30);
        //保存code
        redisUtils.set(Constants.User.KEY_EMAIL_CODE_CONTENT + emailAddress, code + "", 60 * 10);
        return ResponseResult.SUCCESS("验证码发送成功");
    }

    @Override
    public ResponseResult register(User user, String emailCode, String captchaCode, String captchaKey, HttpServletRequest request) {
        //检查当前用户名是否已经注册
        String userName = user.getUserName();
        if (TextUtils.isEmpty(userName)) {
            return ResponseResult.FAILED("用户名不可为空");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        User userFromDbByUserName = userDao.selectOne(userQueryWrapper);
        if (userFromDbByUserName != null) {
            return ResponseResult.FAILED("该用户已注册");
        }
        //检查邮箱格式是否正确
        String email = user.getEmail();
        if (TextUtils.isEmpty(email)) {
            return ResponseResult.FAILED("邮箱地址不能为空");
        }
        if (!TextUtils.isEmailAddressOk(email)) {
            return ResponseResult.FAILED("邮箱格式不正确");
        }
        //检查邮箱是否注册
        userQueryWrapper.clear();
        userQueryWrapper.eq("email",email);
        User userByEmail = userDao.selectOne(userQueryWrapper);
        if (userByEmail != null) {
            return ResponseResult.FAILED("该邮箱地址已经注册");
        }
        //检查邮箱验证码是否正确
        String emailVerifyCode = (String) redisUtils.get(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        if (TextUtils.isEmpty(emailVerifyCode)) {
            return ResponseResult.FAILED("邮箱验证码已过期");
        }
        if (!emailVerifyCode.equals(emailCode)) {
            return ResponseResult.FAILED("邮箱验证码错误");
        } else {
            //正确，干掉redis中的内容
            redisUtils.del(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        }
        //检查图灵验证码是否正确
        String captchaVerifyCode = (String) redisUtils.get(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        if (TextUtils.isEmpty(captchaVerifyCode)) {
            return ResponseResult.FAILED("人类验证码已过期");
        }
        if (!captchaVerifyCode.equals(captchaCode)) {
            return ResponseResult.FAILED("人类验证码不正确");
        } else {
            redisUtils.del(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        }
        //可以注册
        //加密密码
        String password = user.getPassword();
        if (TextUtils.isEmpty(password)) {
            return ResponseResult.FAILED("密码不能为空");
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
        //补全数据 ip、角色、头像
        String ipAddress = request.getRemoteAddr();
        user.setLoginIp(ipAddress);
        user.setRegIp(ipAddress);
//        user.setCreateTime(new Date());
//        user.setUpdateTime(new Date());
        if (user.getAvatar()==null){
            user.setAvatar(Constants.User.DEFAULT_AVATAR);
        }
        user.setRoleId(Constants.User.ROLE_NORMAL);
        user.setState("1");
        user.setId(idWorker.nextId() + "");
        userDao.insert(user);
        return ResponseResult.GET(ResponseState.JOIN_IN_SUCCESS);
    }

    @Override
    public ResponseResult doLogin(String captchaKey, String captcha, User user, HttpServletRequest request, HttpServletResponse response) {
        String captchaValue = (String) redisUtils.get(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        if (!captcha.equals(captchaValue)) {
            return ResponseResult.FAILED("人类验证码不正确");
        }
        //验证成功，删除redis里的验证码
        redisUtils.del(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        //有可能是邮箱，也有可能是用户名
        String userName = user.getUserName();
        if (TextUtils.isEmpty(userName)) {
            return ResponseResult.FAILED("账号不可为空");
        }
        String password = user.getPassword();
        if (TextUtils.isEmpty(password)) {
            return ResponseResult.FAILED("密码不可为空");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",userName);
        User userFromDb = userDao.selectOne(userQueryWrapper);
        if (userFromDb == null) {
            userQueryWrapper.clear();
            userQueryWrapper.eq("email",userName);
            userFromDb = userDao.selectOne(userQueryWrapper);
        }
        if (userFromDb == null) {
            return ResponseResult.FAILED("用户名或密码错误");
        }
        //用户存在
        //对比密码
        boolean matches = bCryptPasswordEncoder.matches(password, userFromDb.getPassword());
        if (!matches) {
            return ResponseResult.FAILED("用户名或密码错误");
        }
        //密码正确
        //判断用户状态，如果非正常返回结果
        if (!"1".equals(userFromDb.getState())) {
            return ResponseResult.ACCOUNT_DENIED();
        }
        createToken(response, userFromDb);
        HashMap<String, Object> res = new HashMap<>();
        res.put("id",userFromDb.getId());
        return ResponseResult.SUCCESS("登录成功").setData(res);
    }

    /**
     * @param response
     * @param userFromDb
     * @return 返回tokenKey
     */
    private String createToken(HttpServletResponse response, User userFromDb) {
        int deleteResult = refreshTokenDao.deleteById(userFromDb.getId());
        log.info("deleteResult of refresh token ==>" + deleteResult);
        //生成token
        Map<String, Object> cliams = ClaimsUtils.user2Claims(userFromDb);

        String token = JwtUtil.createToken(cliams);//默认两小时
        //返回token的md5值，token保存到redis里
        //前端访问的时候，携带md5key，从redis中获取
        String tokenKey = DigestUtils.md5DigestAsHex(token.getBytes());
//        log.info(token+"====="+tokenKey);
        //保存token到redis里，有效期是2小时，key是tokenKey
        redisUtils.set(Constants.User.KEY_TOKEN + tokenKey, token, Constants.TimeValueInSecond.HOUR_2);
//        Cookie cookie = new Cookie(Constants.User.COOKIE_TOKEN_KEY,tokenKey);
        //这个要动态获取，可以从request中获取
        //把tokenKey写到cookies里
        CookieUtils.setUpCookie(response, Constants.User.COOKIE_TOKEN_KEY, tokenKey);

        //生成refreshToken里
        String refreshTokenValue = JwtUtil.createRefreshToken(userFromDb.getId(), Constants.TimeValueInMillions.MONTH);
        //保存到数据库
        //refreshToken,tokenKey,userid,创建时间、修改时间
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setId(idWorker.nextId() + "");
        refreshToken.setRefreshToken(refreshTokenValue);
        refreshToken.setUserId(userFromDb.getId());
        refreshToken.setTokenKey(tokenKey);
        refreshTokenDao.insert(refreshToken);
        return tokenKey;
    }

    /**
     * 本质就是检查用户是否有登录，如果有登录就返回用户信息，如果登录了就返回tokenKey
     */
    @Override
    public User checkUser() {
        //拿到request和response
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        //拿到token_key
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        if (tokenKey==null){
            tokenKey = request.getHeader(Constants.User.COOKIE_TOKEN_KEY);
            System.out.println();
        }
        log.info("checkSobUser tokenKey ==>" + tokenKey);
        User user = parseByToken(tokenKey);
        if (user == null) {
            //解析出错，过期了
            //1.去mysql查询refreshToken
            QueryWrapper<RefreshToken> refreshTokenQueryWrapper = new QueryWrapper<>();
            refreshTokenQueryWrapper.eq("token_key",tokenKey);
            RefreshToken refreshToken = refreshTokenDao.selectOne(refreshTokenQueryWrapper);
            //2.如果不存在，就是当前访问没登录，提示用户登录
            log.info("refreshToken == " + refreshToken);
            if (refreshToken == null) {
                log.info("refresh token is null ...");
                return null;
            }
            //3.如果存在，就解析refreshToken
            try {
                Claims claims = JwtUtil.parseJWT(refreshToken.getRefreshToken());
                //5.如果refreshToken有效，就创建新的token，和使用新的refreshToken
                String userId = refreshToken.getUserId();
                User userFromDB = userDao.selectById(userId);
                //删除refreshToken的记录

                String newTokenKey = createToken(response, userFromDB);
                //返回token
                log.info("create new token and refresh token ...");
                return parseByToken(newTokenKey);
            } catch (Exception e1) {
                //4.如果refreshToken过期了，就是当前访问没有登录，提示用户登录
                log.info("refresh token is 过期 ...");
                return null;
            }
        }

        return user;
    }

    @Override
    public ResponseResult updateUserPassword(String verifyCode, User user) {
        //检查邮箱是否有填写
        String email = user.getEmail();
        if (TextUtils.isEmpty(email)) {
            return ResponseResult.FAILED("邮箱不可以为空");
        }
        //根据邮箱去redis里拿验证
        String redisVerifyCode = (String) redisUtils.get(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
//        log.info("updateUserPassword  redisVerifyCode ==>"+redisVerifyCode);
//        log.info("updateUserPassword  verifyCode ==>"+verifyCode);
//        log.info("updateUserPassword  redisVerifyCode is verifyCode==>"+redisVerifyCode.equals(verifyCode));
        //对比
        if (redisVerifyCode == null||!redisVerifyCode.equals(verifyCode)) {
            return ResponseResult.FAILED("验证码错误");
        }
        redisUtils.del(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        //修改密码
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email",email);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        int result = userDao.update(user, userQueryWrapper);
        return result>0?ResponseResult.SUCCESS("密码修改成功"):ResponseResult.FAILED("密码修改失败");
    }

    @Override
    public ResponseResult getUserInfo(String userId) {
        //从数据库获取
        User user = userDao.selectById(userId);
        //判断结果
        if (user == null) {
            return ResponseResult.FAILED("用户不存在");
        }
        //如果存在，就复制对象，清空密码\Emial\loginID\注册IP
        String userJson = gson.toJson(user);
        User newSobUser = gson.fromJson(userJson, User.class);
        newSobUser.setPassword("");
        newSobUser.setEmail("");
        newSobUser.setRegIp("");
        newSobUser.setLoginIp("");
        return ResponseResult.SUCCESS("获取成功").setData("info",newSobUser);
    }

    /**
     * 更新用户信息
     * @param userId
     * @param user
     * @return
     */
    @Override
    public ResponseResult updateUserInfo(String userId, User user) {
        //从token中解析出来的user，为了校验权限
        //只有用户自己才可以修改自己的信息
        User userFromTokenKey = checkUser();
        if (userFromTokenKey == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN("账号未登录");
        }
        //判断用户的id和要修该的id是否一致，一致才可以修改
        User userFromDb = userDao.selectById(userFromTokenKey.getId());
        if (!userFromDb.getId().equals(user.getId())){
            return ResponseResult.PERMISSION_DENIED();
        }
        //可以进行修改
        //可经修改的项
        //用户名
        String userName = user.getUserName();
        if (!TextUtils.isEmpty(userName)) {
            QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("user_name",userName);
            User userByUserName = userDao.selectOne(userQueryWrapper);
            if (userByUserName != null) {
                return ResponseResult.FAILED("该用户名已注册");
            }
            userFromDb.setUserName(userName);
        }
        //头像
        if (!TextUtils.isEmpty(user.getAvatar())) {
            userFromDb.setAvatar(user.getAvatar());
        }
        userFromDb.setUpdateTime(new Date());
        //签名,可以为空

        userFromDb.setSign(user.getSign());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("id",userId);
        userDao.update(userFromDb,userQueryWrapper);
        //干掉redis里的token，下一次请求，需要解析token的，就会根据refreshToken重新创建一个
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String tokenKey = CookieUtils.getCookie(request, Constants.User.COOKIE_TOKEN_KEY);
        redisUtils.del(Constants.User.KEY_TOKEN+tokenKey);
        return ResponseResult.SUCCESS("用户信息更新成功");
    }

    @Override
    public ResponseResult listUser(int page, int size) {
        //可以获取用户列表
        //分页查询
        if (page<Constants.Page.DEFAULT_PAGE){
            page=1;
        }
        //size也限制一下
        if (size<Constants.Page.DEFAULT_SIZE){
            size=Constants.Page.DEFAULT_SIZE;
        }
        //根据注册日期来排序
        QueryWrapper<UserNotPassword> userQueryWrapper = new QueryWrapper<>();

        com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserNotPassword> requestPage = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(page, size);
        requestPage.addOrder(OrderItem.desc("create_time"));
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<UserNotPassword> userPage = userNotPasswordDao.selectPage(requestPage, userQueryWrapper);
        List<UserNotPassword> all = userPage.getRecords();
        Map<String,Object> res = new HashMap<>();
        long currentPage = userPage.getCurrent();
        long maxPage = userPage.getPages();
        res.put("userList",all);
        res.put("currentPage",currentPage);
        res.put("maxPage",maxPage);
        return ResponseResult.SUCCESS("获取用户列表成功").setData(res);
    }

    @Override
    public ResponseResult deleteUserById(String userId) {
        User user = this.checkUser();
        if (user.getId().equals(userId)) {
            return ResponseResult.FAILED("不能删除自己");
        }
        User userFromDB = userDao.selectById(userId);
        if (!userFromDB.getRoleId().equals(Constants.User.ROLE_NORMAL)&&!user.getRoleId().equals(Constants.User.ROLE_ADMIN_SUPER_ID)) {
            return ResponseResult.FAILED("无法删除管理员");
        }
        //可以删除用户
//        int result = userDao.deleteByUserId(userId);
        userFromDB.setState("0");
        userFromDB.setUpdateTime(new Date());
        int result = userDao.updateById(userFromDB);
        if (result>0){
            return ResponseResult.SUCCESS("删除成功");
        }
        return ResponseResult.FAILED("用户不存在");
    }

    @Override
    public ResponseResult checkEmail(String email) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email",email);
        User user = userDao.selectOne(userQueryWrapper);
        return user == null ? ResponseResult.FAILED("该邮箱未注册") : ResponseResult.SUCCESS("该邮箱已经注册");
    }

    @Override
    public ResponseResult checkUserName(String userName) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",userName);
        User user = userDao.selectOne(userQueryWrapper);
        return user == null ? ResponseResult.FAILED("该用户名未注册") : ResponseResult.SUCCESS("该用户名已经注册");
    }

    @Override
    public ResponseResult updateEmail(String email, String verifyCode) {
        User user = this.checkUser();
        if (user == null) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("email",email);
        User userFromDB = userDao.selectOne(userQueryWrapper);
        if (userFromDB != null) {
            return ResponseResult.FAILED("邮箱被使用了");
        }
        //2.对比验证码,确保新的邮箱地址是属于当前用户的
        String redisVerifyCode = (String) redisUtils.get(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        if (TextUtils.isEmpty(redisVerifyCode)||!redisVerifyCode.equals(verifyCode)) {
            return ResponseResult.FAILED("验证码错误");
        }
        //验证码正确，删除验证码
        redisUtils.del(Constants.User.KEY_EMAIL_CODE_CONTENT + email);
        //可以修改
        int result = userDao.updateEmail(user.getId(), email);
        return result>0?ResponseResult.SUCCESS("邮箱修改成功"):ResponseResult.FAILED("邮箱修改失败");
    }

    @Override
    public ResponseResult doLogout() {
        //拿到token_key
        String tokenKey = CookieUtils.getCookie(getRequest(), Constants.User.COOKIE_TOKEN_KEY);
        if (TextUtils.isEmpty(tokenKey)) {
            return ResponseResult.ACCOUNT_NOT_LOGIN();
        }
        //删除redis里的token
        redisUtils.del(Constants.User.KEY_TOKEN+tokenKey);
        //删除mysql中的refreshToken
        QueryWrapper<RefreshToken> refreshTokenQueryWrapper = new QueryWrapper<>();
        refreshTokenQueryWrapper.eq("token_key",tokenKey);
        refreshTokenDao.delete(refreshTokenQueryWrapper);

        //删除cookie
        CookieUtils.deleteCookie(getRequest(),getResponse(),Constants.User.COOKIE_TOKEN_KEY);
        return ResponseResult.SUCCESS("退出登录成功");

    }

    @Override
    public ResponseResult registerAdmin(User user, String roleId, String captchaCode, String captchaKey, HttpServletRequest request) {
        //检查当前用户名是否已经注册
        String userName = user.getUserName();
        if (TextUtils.isEmpty(userName)) {
            return ResponseResult.FAILED("用户名不可为空");
        }
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name", userName);
        User userFromDbByUserName = userDao.selectOne(userQueryWrapper);
        if (userFromDbByUserName != null) {
            return ResponseResult.FAILED("该用户已注册");
        }
        //检查邮箱格式是否正确
        String email = user.getEmail();
        if (TextUtils.isEmpty(email)) {
            return ResponseResult.FAILED("邮箱地址不能为空");
        }
        if (!TextUtils.isEmailAddressOk(email)) {
            return ResponseResult.FAILED("邮箱格式不正确");
        }
        //检查邮箱是否注册
        userQueryWrapper.clear();
        userQueryWrapper.eq("email",email);
        User userByEmail = userDao.selectOne(userQueryWrapper);
        if (userByEmail != null) {
            return ResponseResult.FAILED("该邮箱地址已经注册");
        }
        //检查图灵验证码是否正确
        String captchaVerifyCode = (String) redisUtils.get(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        if (TextUtils.isEmpty(captchaVerifyCode)) {
            return ResponseResult.FAILED("人类验证码已过期");
        }
        if (!captchaVerifyCode.equals(captchaCode)) {
            return ResponseResult.FAILED("人类验证码不正确");
        } else {
            redisUtils.del(Constants.User.KEY_CAPTCHA_CONTENT + captchaKey);
        }
        //可以注册
        //加密密码
        String password = user.getPassword();
        if (TextUtils.isEmpty(password)) {
            return ResponseResult.FAILED("密码不能为空");
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
        //补全数据 ip、角色、头像
        String ipAddress = request.getRemoteAddr();
        user.setLoginIp(ipAddress);
        user.setRegIp(ipAddress);
        if (user.getAvatar()==null){
            user.setAvatar(Constants.User.DEFAULT_AVATAR);
        }
        user.setRoleId(roleId);
        user.setState("1");
        user.setId(idWorker.nextId() + "");
        userDao.insert(user);
        return ResponseResult.SUCCESS("管理员注册成功");
    }

    @Override
    public ResponseResult deleteAdminById(String userId) {
        User user = this.checkUser();
        if (user.getId().equals(userId)) {
            return ResponseResult.FAILED("不能删除自己");
        }
        //可以删除
        int result = userDao.deleteByUserId(userId);
        if (result>0){
            return ResponseResult.SUCCESS("删除成功");
        }
        return ResponseResult.FAILED("用户不存在");
    }

    @Override
    public ResponseResult parseToken() {
        User user = checkUser();
        if (user==null) {
            return ResponseResult.FAILED("用户未登录");
        }else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("user",user);
            return ResponseResult.SUCCESS("获取成功").setData(map);
        }
    }

    @Override
    public ResponseResult reUserById(String userId) {
        User user = this.checkUser();
        User userFromDB = userDao.selectById(userId);
        if (!userFromDB.getRoleId().equals(Constants.User.ROLE_NORMAL)&&!user.getRoleId().equals(Constants.User.ROLE_ADMIN_SUPER_ID)) {
            return ResponseResult.FAILED("无法恢复管理员，权限不足");
        }
        //可以删除用户
//        int result = userDao.deleteByUserId(userId);
        userFromDB.setState("1");
        userFromDB.setUpdateTime(new Date());
        int result = userDao.updateById(userFromDB);
        if (result>0){
            return ResponseResult.SUCCESS("恢复成功");
        }
        return ResponseResult.FAILED("用户不存在");
    }

    private User parseByToken(String tokenKey) {
        String token = (String) redisUtils.get(Constants.User.KEY_TOKEN + tokenKey);
        log.info("parseByToken token ==> " + token);
        if (token != null) {
            try {
                Claims claims = JwtUtil.parseJWT(token);

                return ClaimsUtils.claims2User(claims);
            } catch (Exception e) {
                log.info("parseByToken ==> " + tokenKey + "  过期了");
                return null;
            }
        }
        return null;
    }

    private HttpServletRequest getRequest(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }
    private HttpServletResponse getResponse(){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return requestAttributes.getResponse();
    }
}
