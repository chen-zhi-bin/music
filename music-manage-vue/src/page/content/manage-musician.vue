<template>
    <div>
        <div>
            <el-button type="primary" aria-setsize="mini" @click="showAddMusician()">添加歌手</el-button>
        </div>
        <div class="music-list-box">
            <el-table :data="listMusician" style="width: 100%" height="500">
                <el-table-column fixed prop="musicianId" label="id" width="180" />
                <el-table-column prop="picId" label="歌手图片" width="130">
                    <template v-slot="scope">
                        <el-avatar shape="square" style="width: 100px; height: 100px"
                            :src="baseUrlImage + '/' + scope.row.picId" />
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="音乐人名字" width="150">
                </el-table-column>
                <el-table-column prop="sex" label="歌手类别" width="150" >
                    <template v-slot="scope">
                        <div v-if="scope.row.sex === '0'">
                            <el-tag type="danger">女</el-tag>
                        </div>
                        <div v-if="scope.row.sex === '1'">
                            <el-tag>男</el-tag>
                        </div>
                        <div v-if="scope.row.sex === '2'">
                            <el-tag>组合</el-tag>
                        </div>
                        <div v-if="scope.row.sex === '3'">
                            <el-tag>未知</el-tag>
                        </div>
                    </template>
                </el-table-column>

                <el-table-column prop="state" label="音乐人状态" width="150" >
                    <template v-slot="scope">
                        <div v-if="scope.row.state === '0'">
                            <el-tag type="danger">已删除</el-tag>
                        </div>
                        <div v-if="scope.row.state === '1'">
                            <el-tag>正常</el-tag>
                        </div>
                    </template>
                </el-table-column>

                <el-table-column prop="introduction" label="描述" width="200">
                    <template v-slot="scope">
                        <el-text v-if="scope.row.introduction == null" class="w-100px" truncated>暂 无 描 述</el-text>
                        <el-text v-else class="w-100px" truncated>{{ scope.row.introduction }}</el-text>
                    </template>
                </el-table-column>

                <el-table-column prop="userName" label="上一个修改人" width="120"/>
                
                <el-table-column prop="createTime" label="创建时间" width="180">
                    <template v-slot="scope">
                        <span v-text="formatDate(scope.row.createTime)">
                        </span>
                    </template>
                </el-table-column>
                <el-table-column prop="updateTime" label="上一次更新时间" width="180">
                    <template v-slot="scope">
                        <span v-text="formatDate(scope.row.updateTime)">
                        </span>
                    </template>
                </el-table-column>

                <el-table-column fixed="right" label="Operations" width="200">
                    <template v-slot="scope">
                        <el-button type="primary" size="small" @click="edit(scope.row)">编辑</el-button>
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

        <div class="music-manage-foot">
            <el-pagination layout="prev, pager, next" :total="1000" @current-change="handleCurrentChange"
                v-model:page-count="totalPage" />
        </div>

        <div class="musician-dialog-box">
            <el-dialog v-model="deleteDialogShow" title="删除提示" width="30%">
                <span>确认删除音乐人： {{ deleteMessage }} 吗？</span>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="deleteDialogShow = false">取 消</el-button>
                        <el-button type="danger" @click="doDeleteItem()">
                            确 认
                        </el-button>
                    </span>
                </template>
            </el-dialog>
            <!-- 音乐人编辑/添加 -->
            <el-dialog v-model="editDialogShow" :title="editTitle" width="30%" :close-on-click-modal="false"
                :close-on-press-escape="false">
                <div class="musician-edit-box">
                    <el-form label-width="100px" style="max-width: 460px">
                        <el-form-item label="歌手图片">
                            <el-avatar v-if="musician.picId != '' && musician.picId != null" shape="square"
                                style="width: 100px; height: 100px" :src="baseUrlImage + '/' + musician.picId"
                                class="img-dialog" />
                            <el-upload v-model:file-list="fileList" class="upload-demo" :action="baseUrlImage"
                                :on-success="handleUpImage" :before-remove="beforeRemove" :limit="1">
                                <el-button type="primary">上传歌手图片</el-button>
                                <template #tip>
                                    <div class="el-upload__tip">
                                        上传一张 jpg/png 图片
                                    </div>
                                </template>

                            </el-upload>
                        </el-form-item>
                       
                        <el-form-item label="歌音乐人名">
                            <el-input v-model="musician.name" />
                        </el-form-item>
                        <el-form-item label="描述">
                            <el-input v-model="musician.introduction" type="textarea" :row="2" maxlength="128" />
                        </el-form-item>
                        <el-form-item label="状类别态">
                            <el-select v-model="musician.sex" placeholder="状态选择">
                                <el-option label="女" value="0" />
                                <el-option label="男" value="1" />
                                <el-option label="组合" value="2" />
                                <el-option label="未知" value="3" />
                            </el-select>
                        </el-form-item>
                        <el-form-item label="状态">
                            <el-select v-model="musician.state" placeholder="状态选择">
                                <el-option label="已删除" value="0" />
                                <el-option label="正常" value="1" />
                            </el-select>
                        </el-form-item>
                    </el-form>
                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dissmiss()" type="danger">取 消</el-button>
                        <el-button type="primary" @click="postMusicican()">
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
            listMusician: [],
            currentPage: 1,
            totalPage: 10,
            baseUrlImage: 'admin/admin/image',
            deleteDialogShow: false,
            deleteMessage: '',
            deleteTargetId: '',
            musician:{
                id:'',
                picId:'',
                name:'',
                sex:'',
                state:'',
                introduction:''
            },
            editDialogShow: false,
            editorCommitText: '',
            editTitle: '',
        }
    },
    mounted() {
        this.getMusicianList();
    },
    methods: {
        showAddMusician(){
            this.resetMusician();
            this.editTitle = '添加音乐人';
            this.editorCommitText = '添 加'
            this.editDialogShow = true;
        },
        handleCurrentChange(page) {
            this.currentPage = page;
            this.getMusicianList();
        },
        postMusicican(){
            if (this.musician.id==='') {
                axios({
                method: 'post',
                url: 'admin/admin/singer',
                data: this.musician
            }).then(res => {
                let result = res.data;
                if (result.code == api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.editDialogShow = false;
                        this.resetMusician();
                        this.getMusicianList();
                    } else {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'error'
                        });
                    }
            })
                // api.updateMusicianInfo(this.musician)
                // .then(result => {
                //     if (result.code == api.success_code) {
                //         this.$message({
                //             message: result.message,
                //             center: true,
                //             type: 'success'
                //         });
                //         this.editDialogShow = false;
                //         this.resetMusician();
                //         this.getMusicianList();
                //     } else {
                //         this.$message({
                //             message: result.message,
                //             center: true,
                //             type: 'error'
                //         });
                //     }
                // })
            }else{
                api.updateMusicianInfo(this.musician.id,this.musician)
            .then(result=>{
                if (result.code === api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.getMusicianList();
                        this.editDialogShow = false;
                        this.resetMusician();
                    } else {
                        this.toastE(result.message);
                    }
            });
            }
            
        },
        deleteItem(item) {
            //给出提示
            this.deleteDialogShow = true;
            this.deleteMessage = item.name;
            this.deleteTargetId = item.musicianId;
        },
        doDeleteItem() {
            api.deleteMusician(this.deleteTargetId)
                .then(result => {
                    if (result.code === api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.getMusicianList();
                    } else {
                        this.toastE(result.message);
                    }
                    this.deleteDialogShow = false;
                })
        },
        recoverItem(item){
            api.reMusician(item.musicianId)
            .then(result=>{
                if (result.code === api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.getMusicianList();
                    } else {
                        this.toastE(result.message);
                    }
            })
        },
        dissmiss(){
            this.editDialogShow = false;
            this.editorCommitText = '';
            this.editTitle = '';
            this.resetMusician();
        },
        resetMusician(){
            this.musician.id = '';
            this.musician.sex = '';
            this.musician.picId = '';
            this.musician.name = '';
            this.musician.state = '';
            this.musician.introduction = '';
        },
        edit(item){
            this.musician.id = item.musicianId;
            this.musician.sex = item.sex;
            this.musician.picId = item.picId;
            this.musician.name = item.name;
            this.musician.state = item.state;
            this.musician.introduction = item.introduction;
            // 显示dialog
            this.editDialogShow = true;
            this.editorCommitText = '修改音乐人信息';
            this.editTitle = '编辑音乐人信息';
        },
        toastE(msg) {
            this.$message({
                message: msg,
                center: true,
                type: 'error'
            })
        },
        handleUpImage(res) {
            this.musician.picId = res.data.id;
        },
        formatDate(dateStr) {
            let date = new Date(dateStr);
            return dateUtils.formatDate(date, 'yyyy-MM-dd hh:mm:ss');
        },
        getMusicianList() {
            api.listMusician(this.currentPage)
                .then(res => {
                    if (res.code === api.success_code) {
                        this.listMusician = res.data.list;
                        this.totalPage = res.data.maxPage;
                    }
                });
        }
    }
}

</script>
  
<style>
.music-manage-foot {
    width: 100%;
    margin-left: 200px;
}
</style>
  