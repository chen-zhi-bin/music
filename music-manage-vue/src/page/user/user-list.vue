<template>
    <div>
        <div>
            <el-button type="primary" aria-setsize="mini" @click="showAddAdmin()">添加管理员</el-button>
        </div>
        <div class="image-list-box">
            <el-table :data="userList" style="width: 100%" height="480">
                <el-table-column fixed prop="id" label="id" width="180" />
                <el-table-column prop="avatar" label="头像" width="130">
                    <template v-slot="scope">
                        <el-avatar shape="square" style="width: 100px; height: 100px"
                            :src="baseUrlImage + '/' + scope.row.avatar" />
                    </template>
                </el-table-column>
                <el-table-column prop="userName" label="名称" width="150" />
                <el-table-column prop="email" label="邮箱" width="150">
                </el-table-column>

                <el-table-column prop="state" label="状态" width="120">
                    <template v-slot="scope">
                        <div v-if="scope.row.state === '0'">
                            <el-tag type="danger">已删除</el-tag>
                        </div>
                        <div v-if="scope.row.state === '1'">
                            <el-tag>正 常</el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="sign" label="sign" width="150" />
                <el-table-column prop="createTime" label="创建时间" width="180">
                    <template v-slot="scope">
                        <span v-text="formatDate(scope.row.createTime)">
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="updateTime" label="更新时间" width="180">
                    <template v-slot="scope">
                        <span v-text="formatDate(scope.row.updateTime)">
                        </span>
                    </template>
                </el-table-column>

                <el-table-column fixed="right" label="Operations" width="150">
                    <template v-slot="scope">
                        <el-button type="success" v-if="scope.row.state !== '1'" size="small"
                            @click="recoverItem(scope.row)">恢复</el-button>
                        <el-button type="success" v-if="scope.row.state === '1'" size="small" disabled>恢复</el-button>
                        <el-button type="danger" v-if="scope.row.state !== '0'" size="small"
                            @click="deleteItem(scope.row)">删除</el-button>
                        <el-button type="danger" v-if="scope.row.state === '0'" size="small" disabled>删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <div class="user-dialog-box">
            <el-dialog v-model="deleteDialogShow" title="删除提示" width="30%">
                <span>确认删除user： {{ deleteMessage }} 吗？</span>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="deleteDialogShow = false">取 消</el-button>
                        <el-button type="danger" @click="doDeleteItem()">
                            确 认
                        </el-button>
                    </span>
                </template>
            </el-dialog>
        </div>
        <div class="user-manage-foot">
            <el-pagination layout="prev, pager, next" :total="1000" @current-change="handleCurrentChange"
                v-model:page-count="totalPage" />
        </div>

        <el-dialog v-model="editDialogShow" :title="editTitle" width="30%" :close-on-click-modal="false"
                :close-on-press-escape="false">
                <div class="user-edit-box">
                    <el-form label-width="100px" style="max-width: 460px">
                        <el-form-item label="头像">
                            <el-avatar v-if="admin.avatar != '' && admin.avatar != null" shape="square"
                                style="width: 100px; height: 100px" :src="baseUrlImage + '/' + admin.avatar"
                                class="img-dialog" />
                            <el-upload v-model:file-list="fileList" class="upload-demo" :action="baseUrlImage"
                                :on-success="handleUpImage" :before-remove="beforeRemove" :limit="1">
                                <el-button type="primary">上传头像</el-button>
                                <template #tip>
                                    <div class="el-upload__tip">
                                        上传一张 jpg/png 图片
                                    </div>
                                </template>

                            </el-upload>
                        </el-form-item>
                       
                        <el-form-item label="管理名">
                            <el-input v-model="admin.userName" />
                        </el-form-item>
                        <el-form-item label="密码">
                            <el-input v-model="admin.password" type="textarea" :row="2" maxlength="128" />
                        </el-form-item>
                        <el-form-item label="验证码">
                            <el-input v-model="admin.captcha_code" >
                            </el-input>
                            <img :src="captchaPath" @click="updateVerifyCode">
                        </el-form-item>
                        <el-form-item label="邮箱">
                            <el-input v-model="admin.email"/>
                        </el-form-item>
                        <el-form-item label="sign">
                            <el-input v-model="admin.sign" />
                        </el-form-item>
                        <el-form-item label="权限">
                            <el-select v-model="admin.roleId" placeholder="状态选择">
                                <el-option label="超级管理员" value="1" />
                                <el-option label="用户管理员" value="2" />
                                <el-option label="图片管理员" value="3" />
                                <el-option label="音乐管理员" value="4" />
                            </el-select>
                        </el-form-item>
                        
                    </el-form>
                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dissmiss()" type="danger">取 消</el-button>
                        <el-button type="primary" @click="postAdmin()">
                            {{ editorCommitText }}
                        </el-button>
                    </span>
                </template>
            </el-dialog>
    </div>
</template>
  
<script>
import * as api from '../../api/api';
import * as dateUtils from '../../utils/date';
import { v1 as uuidv1 } from 'uuid';
const axios = require('axios');
export default {
    data() {
        return {
            userList: [],
            currentPage: 1,
            totalPage: 10,
            baseUrlImage: 'admin/admin/image',
            deleteDialogShow: false,
            deleteMessage: '',
            deleteTargetId: '',
            admin:{
                avatar:'',
                userName:'',
                sign:'',
                email:'',
                password:'',
                roleId:'',
                captcha_key:'',
                captcha_code:''
            },
            captchaPath:'',
            editDialogShow: false,
            editorCommitText: '',
            editTitle: '',
        }
    },
    mounted() {
        this.getUserList();
       
    },
    methods: {
        postAdmin(){
            axios({
                method: 'post',
                url: 'user/user/addAdmin?captcha_code='+this.admin.captcha_code+'&captcha_key='+this.admin.captcha_key+'&role_id='+this.admin.roleId,
                data: this.admin
            }).then(res => {
                let result = res.data;
                if (result.code === api.success_code) {
                        this.$message({
                            message: '管理员添加成功',
                            center: true,
                            type: 'success'
                        });
                        this.dissmiss();
                        this.resetAdmin();
                        this.getUserList();
                    } else {
                        this.toastE(result.message);
                    }
            })
            // api.addAdmin(this.admin.captcha_key,this.admin.captcha_code,this.admin.roleId,this.admin)
            // .then(result=>{
            //     if (result.code === api.success_code) {
            //             this.$message({
            //                 message: result.message,
            //                 center: true,
            //                 type: 'success'
            //             });
            //             this.resetAdmin();
            //             this.getUserList();
            //         } else {
            //             this.toastE(result.message);
            //         }
            // });
        },
        dissmiss(){
            this.resetAdmin();
            this.editDialogShow = false;
        },
        handleCurrentChange(page) {
            this.currentPage = page;
            this.getUserList();
        },
        updateVerifyCode() {
            this.admin.captcha_key = uuidv1();
            this.captchaPath = api.base_url+'/user/captcha?captcha_key=' + this.admin.captcha_key;
        },
        handleUpImage(res) {
            this.admin.avatar = res.data.id;
        },
        showAddAdmin(){
            this.resetAdmin();
            this.editTitle = '添加管理员';
            this.editorCommitText = '添 加'
            this.editDialogShow = true;
        },
        resetAdmin(){
            this.admin.avatar='';
            this.admin.userName='';
            this.admin.sign='';
            this.admin.email='';
            this.admin.password='';
            this.admin.roleId='';
            this.admin.captcha_code='';
            this.updateVerifyCode();
        },
        formatDate(dateStr) {
            let date = new Date(dateStr);
            return dateUtils.formatDate(date, 'yyyy-MM-dd hh:mm:ss');
        },
        recoverItem(item){
            api.reUser(item.id)
            .then(result=>{
                if (result.code === api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.getUserList();
                    } else {
                        this.toastE(result.message);
                    }
            })
        },
        deleteItem(item) {
            //给出提示
            this.deleteDialogShow = true;
            this.deleteMessage = item.userName;
            this.deleteTargetId = item.id;
        },
        doDeleteItem(){
            api.deleteUser(this.deleteTargetId)
            .then(result=>{
                if (result.code === api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.getUserList();
                    } else {
                        this.toastE(result.message);
                    }
                    this.deleteDialogShow = false;
            })
        },
        toastE(msg) {
            this.$message({
                message: msg,
                center: true,
                type: 'error'
            })
        },
        getUserList() {
            api.listUser(this.currentPage)
                .then(res => {
                    if (res.code === api.success_code) {
                        this.userList = res.data.userList;
                        this.totalPage = res.data.maxPage;
                    }
                })
        }
    }
}
</script>
  
<style>
.user-manage-foot {
    width: 100%;
    margin-left: 200px;
}
</style>
  