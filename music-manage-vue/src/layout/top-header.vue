<template>
    <div>
        <div class="header-left-box">
            <div class="header-logo">音乐管理中心</div>
        </div>
        <div class="header-right-box">
            <el-avatar :size="40">
                <img :src="this.admin.avatar" />
            </el-avatar>
            <div class="header-right-box">
                <!-- <el-text class="mx-1">Default</el-text> -->
                <span class="header-right-name">{{ this.admin.name }}</span>
                <el-button type="danger" :icon="Delete" @click="logout">退出登录</el-button>
            </div>
        </div>
    </div>
</template>
  
<script>
import * as api from "../api/api";
export default {
    data() {
        return {
            menuList: [],
            admin: {
                roleId: '',
                avatar: '',
                name: ''
            }
        }
    },
    mounted() {
        this.checkToken();
    },
    methods: {
        checkToken() {
            api.checkToken()
                .then(result => {
                    this.admin.roleId = result.data.user.roleId;
                    this.admin.avatar = api.base_url + '/image/' + result.data.user.avatar;
                    this.admin.name = result.data.user.userName;
                });

        },
        logout() {
            api.logout()
                .then(result => {
                    if (result.code === 20000) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        })
                        this.$router.push('/login');
                    } else {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'error'
                        })
                    }
                })
        }
    }
}
</script>
    
<style>
.header-logo {
    float: left;
    color: #fff;
    font-size: 20px;
    font-weight: 600;
}

.header-right-box {
    padding-left: 10px;
    margin-top: 2px;
    padding-right: 10px;
    float: right;
    color: break;
    font-size: 14px;
}

.header-right-box img {
    width: 40px;
    height: 40px;
}

.header-right-box .header-right-name {
    width: 100px;
}

.header-right-box .el-button {
    margin-left: 10px;
    margin-top: -6px;
}</style>
  