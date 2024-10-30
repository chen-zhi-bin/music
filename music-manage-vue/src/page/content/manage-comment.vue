<template>
    <div>
        <div class="music-list-box">
            <el-table :data="listComment" style="width: 100%" height="500">
                <el-table-column fixed prop="id" label="id" width="180" />
                <el-table-column prop="picId" label="音乐封面" width="130">
                    <template v-slot="scope">
                        <el-avatar shape="square" style="width: 100px; height: 100px"
                            :src="baseUrlImage + '/' + scope.row.picId" />
                    </template>
                </el-table-column>
                <el-table-column prop="musicName" label="音乐名字" width="150">
                    <!-- <template v-slot="scope">
                        <div v-html="scope.row.userName"></div>
                    </template> -->
                </el-table-column>
                <el-table-column prop="singerName" label="歌手名字" width="150" />

                <el-table-column prop="avatar" label="评论人头像" width="200">
                    <template v-slot="scope">
                        <el-avatar shape="square" style="width: 100px; height: 100px"
                            :src="baseUrlImage + '/' + scope.row.avatar" />
                    </template> 
                </el-table-column>
                <el-table-column prop="userName" label="用户名" width="150">
                  
                </el-table-column>
                <el-table-column prop="comment" label="评论内容" width="120">
                    <template v-slot="scope">
                        <div >
                            <el-text truncated="true" >
                                {{ scope.row.comment }}
                            </el-text>
                            <el-button type="primary" plain v-if="scope.row.comment.length > 4" size="small"
                            @click="showDetail(scope.row)">详情</el-button>
                        </div>

                    </template>
                </el-table-column>
                <el-table-column prop="commentTime" label="评论时间" width="180">
                    <template v-slot="scope">
                        <span v-text="formatDate(scope.row.commentTime)">
                        </span>
                    </template>
                </el-table-column>
                <el-table-column fixed="right" label="Operations" width="100">
                    <template v-slot="scope">
                        <el-button type="danger" v-if="scope.row.state !== '0'" size="small"
                            @click="deleteItem(scope.row)">删除</el-button>
                    </template>
                </el-table-column>
            </el-table>
        </div>
        <div class="music-manage-foot">
            <el-pagination layout="prev, pager, next" :total="1000" @current-change="handleCurrentChange"
                v-model:page-count="totalPage" />
        </div>
        <div class="music-dialog-box">
            <el-dialog v-model="detailsShow" title="评论内容" width="60%">
                <el-text class="mx-1" size="large">{{ commentDetails }}</el-text>
            </el-dialog>

            <!-- 删除 -->
            <el-dialog v-model="deleteDialogShow" title="删除提示" width="30%">
                <span>确认删除评论： {{ deleteMessage }} ？</span>
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
    </div>
</template>
  
<script>
import * as api from '../../api/api';
import * as dateUtils from '../../utils/date';
export default {
    data() {
        return {
            listComment: [],
            currentPage: 1,
            totalPage: 10,
            baseUrlImage: 'admin/admin/image',
            commentDetails: '',
            detailsShow: false,
            deleteDialogShow: false,
            deleteMessage: '',
            deleteTargetId: '',
        };
    },
    mounted() {
        this.getListComment();
    },
    methods: {
        formatDate(dateStr) {
            let date = new Date(dateStr);
            return dateUtils.formatDate(date, 'yyyy-MM-dd hh:mm:ss');
        },
        showDetail(item) {
            console.log(item);
            this.commentDetails = item.comment;
            this.detailsShow = true;
        },
        deleteItem(item) {
            //给出提示
            this.deleteDialogShow = true;
            this.deleteMessage = item.comment;
            this.deleteTargetId = item.id;
            // console.log(item)
        },
        doDeleteItem() {
            api.deleteComment(this.deleteTargetId)
                .then(result => {
                    if (result.code === api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.getListComment();
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
        handleCurrentChange(page) {
            this.currentPage = page;
            this.getListComment();
        },
        getListComment() {
            api.getCommentList(this.currentPage)
                .then(result => {
                    if (result.code === api.success_code) {
                        this.listComment = result.data.list;
                        this.totalPage = result.data.maxPage;
                      
                    }
                });
        }
    },

}
</script>
  
<style>
.music-manage-foot {
    width: 100%;
    margin-left: 200px;
}
</style>
  