<template>
    <div class="admin-login-box">
        <!-- 顶部内容 -->
        <div class="admin-login-header-box">
            <div class="admin-login-header-center center">
                <div class="admin-login-logo">
                    博客系统|登录
                </div>
            </div>
        </div>
        <!-- 中间内容 -->
        <div class="admin-login-center-bo">
            <div class="center login-center-box">
                <el-row :gutter="20">
                    <el-col :span="12">
                        <el-form label-position="right" label-width="100px" style="max-width: 460px">
                            <el-form-item label="账号" required>
                                <el-input v-model="user.userName" placeholder="用户名" />
                            </el-form-item>
                            <el-form-item label="密码" required>
                                <el-input v-model="user.password" placeholder="密码" />
                            </el-form-item>
                            <el-form-item label="人类验证码" required>
                                <el-input v-model="loginInfo.verifyCode" placeholder="右侧验证码" @keypress.enter="doLogin" />
                                <img :src="captchaPath" @click="updateVerifyCode" class="captcha-code">
                            </el-form-item>
                            <el-form-item class="login-button">
                                <el-button type="primary" @click="doLogin" aria-setsize="small"> 登 录 </el-button>
                            </el-form-item>
                        </el-form>
                    </el-col>
                </el-row>
            </div>
        </div>
        <!-- 底部内容 -->

    </div>
</template>
  
<script>
import {base_url} from '../../api/api';
import { v1 as uuidv1 } from 'uuid';
const axios = require('axios');
export default{
    data() {
        return {
            user: {
                userName: '',
                password: ''
            },
            loginInfo: {
                verifyCode: '',
                captcha_key: ''
            },
            captchaPath: ''
        }
    },
    methods:{
        toastE(msg) {
            this.$message({
                message: msg,
                center: true,
                type: 'error'
            })
        },
        doLogin(){
            if (this.user.userName === '') {
                this.toastE('用户名不可以为空');
                return;
            }
            if (this.user.password === '') {
                this.toastE('密码不可以为空');
                return;
            }
            if (this.user.verifyCode === '') {
                this.toastE('验证码不可以为空');
                return;
            }
            axios({
                method: 'post',
                url: 'user/user/login/' + this.loginInfo.verifyCode + '/' + this.loginInfo.captcha_key,
                data: this.user
            }).then(result => {
                // console.log(result)
                //处理结果
                //判断状态
                let data = result.data
                if (data.code == 20000) {
                    this.$message({
                        message: data.message,
                        center: true,
                        type: 'success'
                    })
                    //如果成功
                    //成功则跳转--》判断角色
                    this.$router.push('/index');
                } else {
                    this.toastE(data.message);
                    this.updateVerifyCode();
                }
            })
        },
        updateVerifyCode() {
            this.loginInfo.captcha_key = uuidv1();
            this.captchaPath = base_url+'/user/captcha?captcha_key=' + this.loginInfo.captcha_key;
        }
    },
    mounted() {
        this.loginInfo.captcha_key = uuidv1();
        this.updateVerifyCode();
    }
}
</script>
  
<style>
.captcha-code {
    width: 100px;
    height: 40px;
    cursor: pointer;
}

.login-button {
    margin-bottom: 0px;
}

.admin-login-header-box {
    width: 100%;
    height: 46px;
    background: dodgerblue;
    margin-bottom: 20px;
}

.center {
    margin: 0 auto;
    width: 1140px;
}

.login-center-box {
    padding: 20px;
    border-radius: 4px;
    width: 1100px;

    background: #fff;
    box-shadow: 0 1px 2px 0 #afafaf;
}

.admin-login-header-center {
    line-height: 46px;
    margin: 0 auto;
    width: 1140px;
}

.admin-login-logo {
    color: #fff;
    font-size: 18px;
    font-weight: 600;
}

.login-center-box .el-input {
    width: 70%;
}

.login-center-box .el-input inner {
    border: #E6E6E6E6 solid 1px;
    height: 42px;
    border-radius: 0;
}

.login-center-box .el-form-item label {
    background: #FBFBFB;
    border-left: #E6E6E6E6 solid 1px;
    border-top: #E6E6E6E6 solid 1px;
    border-bottom: #E6E6E6E6 solid 1px;
    text-align: center;
}
</style>
  