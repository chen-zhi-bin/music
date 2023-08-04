package com.chen.music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.chen.music.mapper.SettingsDao;
import com.chen.music.pojo.Settings;
import com.chen.music.pojo.User;
import com.chen.music.mapper.UserDao;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chen.music.utils.Constants;
import com.chen.music.utils.IdWorker;
import com.chen.music.utils.RedisUtils;
import com.chen.music.utils.TextUtils;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Random;

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
    private IdWorker idWorker;

    @Autowired
    private Random random;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${music.system.image.save-path-img}")
    public String savePath;

    @Value("${music.system.image.default-avatar}")
    public String defaultAvatar;


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
        long key = 0l;
        try {
            key = Long.parseLong(captchaKey);
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
        if (captchaType == 0) {
            // 三个参数分别为宽、高、位数
            targetCapcha = new SpecCaptcha(200, 60, 5);

        } else if ((captchaType == 1)) {
            //gif
            targetCapcha = new GifCaptcha(200, 60);
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




}
