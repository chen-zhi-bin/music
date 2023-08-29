<template>
    <div>
        <div>
            <el-button type="primary" aria-setsize="mini" @click="showAddLoop()">添加轮播图</el-button>
        </div>
        <div class="image-list-box">
            <el-table :data="listLoop" style="width: 100%" height="500">
                <el-table-column fixed prop="id" label="id" width="180" />
                <el-table-column prop="imageUrl" label="图片" width="130">
                    <template v-slot="scope">
                        <el-avatar shape="square" style="width: 100px; height: 100px" :src="baseUrl + scope.row.imageUrl" />
                    </template>
                </el-table-column>
                <el-table-column prop="title" label="标题" width="150">
                    <template v-slot="scope">
                        <div>
                            <el-text :truncated="true" class="mx-1" size="large">{{ scope.row.title }}</el-text>
                            <el-button type="primary" plain size="small" @click="showBigImg(scope.row)"
                                style="width: 100%;">查看大图</el-button>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="targetUrl" label="目标地址" width="150">
                    <template v-slot="scope">
                        <div>
                            <el-text :truncated="true" class="mx-1">{{ scope.row.targetUrl }}</el-text>
                            <el-button type="primary" plain size="small" @click="toUrl(scope.row)"
                                style="width: 100%;">目标页</el-button>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="order" label="优先级" width="120" />
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

                <el-table-column prop="createTime" label="上传时间" width="180">
                    <template v-slot="scope">
                        <span v-text="formatDate(scope.row.createTime)">
                        </span>
                    </template>
                </el-table-column>

                <el-table-column fixed="right" label="操作" width="150">
                    <template v-slot="scope">
                        <el-button type="primary" size="small" @click="edit(scope.row)">编辑</el-button>
                        <el-button type="danger" size="small" @click="deleteItem(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>

        <!-- dialog -->
        <div>

            <el-dialog v-model="deleteDialogShow" title="删除提示" width="30%">
                <span>确认删除： {{ deleteMessage }} 吗？</span>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="deleteDialogShow = false">取 消</el-button>
                        <el-button type="danger" @click="doDeleteItem()">
                            确 认
                        </el-button>
                    </span>
                </template>
            </el-dialog>

            <el-dialog v-model="editDialogShow" :title="editTitle" width="30%" :close-on-click-modal="false"
                :close-on-press-escape="false">
                <div class="user-edit-box">
                    <el-form label-width="100px" style="max-width: 460px">
                        <el-form-item label="轮播图">
                            <el-avatar v-if="loop.avatar != '' && loop.avatar != null" shape="square"
                                style="width: 100px; height: 100px" :src="baseUrl + '/' + loop.targetUrl"
                                class="img-dialog" />
                            <el-upload v-model:file-list="fileList" class="upload-demo" :action="baseUrlImage"
                                :on-success="handleUpImage" :before-remove="beforeRemove" :limit="1">
                                <el-button type="primary">上传轮播图</el-button>
                                <template #tip>
                                    <div class="el-upload__tip">
                                        上传一张 jpg/png 图片
                                    </div>
                                </template>
                            </el-upload>
                        </el-form-item>

                        <el-form-item label="title">
                            <el-input v-model="loop.title" />
                        </el-form-item>
                        <el-form-item label="目标地址">
                            <el-input v-model="loop.targetUrl" type="textarea" :row="2" maxlength="128" />
                        </el-form-item>
                        <el-form-item label="优先级">
                            <el-input-number v-model="loop.order" :min="0" :max="10" />
                        </el-form-item>
                    </el-form>
                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dissmiss()" type="danger">取 消</el-button>
                        <el-button type="primary" @click="postLoop()">
                            {{ editorCommitText }}
                        </el-button>
                    </span>
                </template>
            </el-dialog>
        </div>
    </div>
</template>
  
<script>
import * as api from '../../api/api';
import * as dateUtils from '../../utils/date';
const axios = require('axios');
export default {
    data() {
        return {
            listLoop: [],
            loop: {
                id: '',
                targetUrl: '',
                imageUrl: '',
                title: '',
                order: '',
                state: '1',
            },
            editDialogShow: false,
            editorCommitText: '',
            editTitle: '',
            baseUrlImage: 'admin/admin/image',
            baseUrl: 'admin/admin',
            deleteDialogShow: false,
            deleteMessage: '',
            deleteTargetId: '',
        }
    },
    mounted() {
        this.getLoopList();
    },
    methods: {
        edit(item) {
            this.loop.id = item.id;
            this.loop.targetUrl = item.targetUrl;
            this.loop.title = item.title;
            this.loop.order = item.order;
            this.loop.imageUrl = item.imageUrl;
            // 显示dialog
            this.editDialogShow = true;
            this.editorCommitText = '修改轮播图';
            this.editTitle = '编辑轮播图';
        },
        deleteItem(item) {
            //给出提示
            this.deleteDialogShow = true;
            this.deleteMessage = item.title;
            this.deleteTargetId = item.id;
        },
        doDeleteItem(){
            api.deleteLoop(this.deleteTargetId)
            .then(result=>{
                if (result.code === api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.getLoopList();
                    } else {
                        this.toastE(result.message);
                    }
                    this.deleteDialogShow = false;
            });
        },
        formatDate(dateStr) {
            let date = new Date(dateStr);
            return dateUtils.formatDate(date, 'yyyy-MM-dd hh:mm:ss');
        },
        getLoopList() {
            api.listLoop()
                .then(res => {
                    if (res.code === api.success_code) {
                        this.listLoop = res.data.list;
                    }
                })
        },
        toUrl(item) {
            window.open(item.targetUrl);
        },
        showBigImg(item) {
            window.open('admin/' + item.imageUrl);
        },
        postLoop() {
            if (this.loop.id === '') {
                axios({
                    method: 'post',
                    url: 'admin/admin/looper',
                    data: this.loop
                }).then(res => {
                    let result = res.data;
                    if (result.code === api.success_code) {
                        this.$message({
                            message: '管理员添加成功',
                            center: true,
                            type: 'success'
                        });
                        this.dissmiss();
                        this.resetLoop();
                        this.getLoopList();
                    } else {
                        this.toastE(result.message);
                    }
                })
                // api.addLoop(this.loop)
                // .then(result=>{
                //     if (result.code === api.success_code) {
                //             this.$message({
                //                 message: '管理员添加成功',
                //                 center: true,
                //                 type: 'success'
                //             });
                //             this.dissmiss();
                //             this.resetLoop();
                //             this.getLoopList();
                //         } else {
                //             this.toastE(result.message);
                //         }
                // })
            }else{
                api.updateLoop(this.loop.id,this.loop)
                .then(result=>{
                    if (result.code === api.success_code) {
                        this.$message({
                            message: '管理员添加成功',
                            center: true,
                            type: 'success'
                        });
                        this.dissmiss();
                        this.resetLoop();
                        this.getLoopList();
                    } else {
                        this.toastE(result.message);
                    }
                })
            }
        },
        toastE(msg) {
            this.$message({
                message: msg,
                center: true,
                type: 'error'
            })
        },
        dissmiss() {
            this.resetLoop();
            this.editDialogShow = false;
        },

        handleUpImage(res) {
            this.loop.imageUrl = '/image/' + res.data.id;
        },
        resetLoop() {
            this.loop.imageUrl = '';
            this.loop.targetUrl = '';
            this.loop.title = '';
            this.loop.order = '';
            this.loop.id = '';
        },
        showAddLoop() {
            this.resetLoop();
            this.editTitle = '添加轮播图';
            this.editorCommitText = '添 加'
            this.editDialogShow = true;
        }
    }

}
</script>
  
<style></style>
  