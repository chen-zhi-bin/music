package com.chen.music.controller.user;

import com.chen.music.pojo.User;
import com.chen.music.response.ResponseResult;
import com.chen.music.service.IUserService;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserApi {

    @Autowired
    private IUserService userService;

    /**
     * 初始化管理员
     */
    @PostMapping("/admin_account")
    public ResponseResult initManagerAccount(@RequestBody User admin, HttpServletRequest request){
        log.info("username ==>"+admin.getUserName());
        return userService.initUserManagerAccount(admin,request);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/join_in")
    public ResponseResult register(@RequestBody User user,
                                   @RequestParam("email_code")String emailCode,
                                   @RequestParam("captcha_code")String captchaCode,
                                   @RequestParam("captcha_key")String captchaKey,
                                   HttpServletRequest request){
        return userService.register(user,emailCode,captchaCode,captchaKey,request);
    }

    /**
     * 登录
     *
     * @param captcha_key 图灵验证码的key
     * @param captcha   图灵验证码
     * @param user   用户bean类，封装账号和密码
     * @return
     */
    @PostMapping("/login/{captcha}/{captcha_key}")
    public ResponseResult login(@PathVariable("captcha_key")String captcha_key,
                                @PathVariable("captcha")String captcha,
                                @RequestBody User user,
                                HttpServletRequest request,
                                HttpServletResponse response){
        return userService.doLogin(captcha_key,captcha,user,request,response);
    }


    /**
     * 获取图灵验证码
     * 有效时间10分钟
     * @return
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletResponse response,@RequestParam("captcha_key")String captchaKey) {
        log.info("captcha");
        try {
            userService.createCaptcha(response,captchaKey);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * 发送邮件
     * 使用场景：注册、找回密码、修改邮箱
     * @return
     */
    @GetMapping("/verify_code")
    public ResponseResult sendVerifyCode(HttpServletRequest request,
                                         @RequestParam("type")String type,
                                         @RequestParam("email")String emailAddress){
        log.info("email ==> "+emailAddress);
        return userService.sendEmail(type,request,emailAddress);
    }

    /**
     * 修改password
     * 修改密码，找回密码
     * 普通做法：通过旧密码对比来更新密码
     *
     * 找回密码：发送验证码到邮箱/手机 --》判断验证码是否正确
     * 来判断对应邮箱、手机号所注册的账号是否属于你
     *
     * 步骤：
     * 1.用户填写邮箱
     * 2.用户填写验证码type=forget
     * 3.填写验证码
     * 4.用户填写新的密码
     * 5.提交数据
     * 数据包括：
     * 1.邮箱和新密码
     * 2.验证码
     * 如果验证码正确--》所用邮箱注册的账号是你的，可以修改密码
     *
     * @return
     */
    @PutMapping("/password/{verifyCode}")
    public ResponseResult updatePassword(@PathVariable("verifyCode")String verifyCode,
                                         @RequestBody User user){
        return userService.updateUserPassword(verifyCode,user);
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/user_info/{userId}")
    public ResponseResult getUserInfo(@PathVariable("userId")String userId){
        return userService.getUserInfo(userId);
    }

    /**
     * 修改用户信息
     *
     * 允许修改内容
     * 1.头像
     * 2.用户名（唯一的）
     * 3.密码 （单独修改）
     * 4.签名
     * 5.email（唯一的，单独修改）
     * @return
     */
    @PutMapping("/user_info/{userId}")
    public ResponseResult updateUserInfo(@PathVariable("userId")String userId,
                                         @RequestBody User user){
        return userService.updateUserInfo(userId,user);
    }
    /**
     * 获取用户列表
     *
     * 权限：管理员
     * @param page
     * @param size
     * @return
     */
    @PreAuthorize("@permission.superAdmin()||@permission.userAdmin()")    //有这个就不需要在impl中判断权限了
    @GetMapping("/list")
    public ResponseResult listUsers(@RequestParam("page")int page,
                                    @RequestParam("size")int size){
        return userService.listUser(page,size);
    }

    /**
     * 需要管理员权限
     * @param userId
     * @return
     */
    @PreAuthorize("@permission.superAdmin()||@permission.userAdmin()")//是管理员才能使用
    @DeleteMapping("/{userId}")
    public ResponseResult deleteUser(@PathVariable("userId")String userId){
        //判断当前的用户
        //根据用户角色判断是否可以删除
        return userService.deleteUserById(userId);
    }

    /**
     * 检查该email是否注册
     * @param email 邮箱地址
     * @return SUCCESS --》已经注册了，FAILED --》没有注册
     */
    @ApiResponses({
            @ApiResponse(code = 20000,message = "表示当前邮箱已经注册"),
            @ApiResponse(code = 40000,message = "表示当前邮箱未注册")
    })
    @GetMapping("/email")
    public ResponseResult checkEmail(@RequestParam("email")String email){
        return userService.checkEmail(email);
    }


    /**
     * 检查该用户名是否注册
     * @param userName 用户名
     * @return SUCCESS --》已经注册了，FAILED --》没有注册
     */
    @ApiResponses({
            @ApiResponse(code = 20000,message = "表示当前用户名已经注册"),
            @ApiResponse(code = 40000,message = "表示当前用户名未注册")
    })
    @GetMapping("/user_name")
    public ResponseResult checkUserName(@RequestParam("userName")String userName){
        return userService.checkUserName(userName);
    }

    /**
     * 1.必须登录
     * 2.新的邮箱，尚未注册
     *
     * 用户步骤
     * 1.已经登录
     * 2.输入新的邮箱
     * 3.获取验证码 type=update
     * 4.输入验证码
     * 5.提交数据
     *
     * 需要提交的数据
     * 1.新的邮箱地址
     * 2.验证码
     * 3.其他信息可以在token中获取
     * @return
     */
    @PutMapping("/email")
    public ResponseResult updateEmail(@RequestParam("email")String email,
                                      @RequestParam("verify_code")String verifyCode){
        return userService.updateEmail(email,verifyCode);
    }

    /**
     * 退出登录
     * 拿tokenKey
     * 删除redis里对应的token
     * 删除mysql里对应的refreshToken
     * 删除cookie里的token_key
     * @return
     */
    @GetMapping("logout")
    public ResponseResult logout(){
        return userService.doLogout();
    }


    @PreAuthorize("@permission.superAdmin()")
    @PostMapping("/addAdmin")
    public ResponseResult registerAdmin(@RequestBody User user,
                                   @RequestParam("captcha_code")String captchaCode,
                                   @RequestParam("captcha_key")String captchaKey,
                                   @RequestParam("role_id")String roleId,
                                   HttpServletRequest request){
        return userService.registerAdmin(user,roleId,captchaCode,captchaKey,request);
    }


    @PreAuthorize("@permission.superAdmin()")
    @DeleteMapping("/delAdmin/{userId}")
    public ResponseResult deleteAdmin(@PathVariable("userId")String userId){
        return userService.deleteAdminById(userId);
    }

    @GetMapping("/check-token")
    public ResponseResult parseToken(){
        return userService.parseToken();
    }
}
