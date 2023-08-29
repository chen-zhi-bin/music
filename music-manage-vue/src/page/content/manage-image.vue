<template>
    <div>
        <div class="image-list-box">
            <el-table :data="listImage" style="width: 100%" height="500">
                <el-table-column fixed prop="id" label="id" width="180" />
                <el-table-column prop="url" label="图片" width="130">
                    <template v-slot="scope">
                        <el-avatar shape="square" style="width: 100px; height: 100px"
                            :src="baseUrlImage + '/' + scope.row.url" />
                    </template>
                </el-table-column>
                <el-table-column prop="name" label="图片名字" width="150" />
                <el-table-column prop="contentType" label="图片规格" width="150">
                    <template v-slot="scope">
                        <div>
                            <el-text class="mx-1">{{ scope.row.contentType }}</el-text>
                             <el-button type="primary" plain  size="small"
                            @click="showBigImg(scope.row)">查看大图</el-button>
                        </div>
                    
                    </template>
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

                <el-table-column prop="createTime" label="上传时间" width="180">
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
        <div class="music-manage-foot">
            <el-pagination layout="prev, pager, next" :total="1000" @current-change="handleCurrentChange"
                v-model:page-count="totalPage" />
        </div>
        <div class="music-dialog-box">
            <!-- 删除 -->
            <el-dialog v-model="deleteDialogShow" title="删除提示" width="30%">
                <span>确认删除图片： {{ deleteMessage }} ？</span>
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
            listImage: [],
            currentPage: 1,
            totalPage: 10,
            baseUrlImage: 'admin/admin/image',
            deleteDialogShow: false,
            deleteMessage: '',
            deleteTargetId: '',
        }
    },
    mounted() {
        this.getListImage();
    },
    methods: {
        showBigImg(item){
            window.open('admin/image/'+item.url);
        },
        deleteItem(item) {
            //给出提示
            this.deleteDialogShow = true;
            this.deleteMessage = item.name;
            this.deleteTargetId = item.id;
            // console.log(item)
        },
        doDeleteItem(){
            api.deleteImage(this.deleteTargetId)
                .then(result => {
                    if (result.code === api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.getListImage();
                    } else {
                        this.toastE(result.message);
                    }
                    this.deleteDialogShow = false;
                })
        },
        recoverItem(item){
            api.reImage(item.id)
            .then(result=>{
                if (result.code === api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.getListImage();
                    } else {
                        this.toastE(result.message);
                    }
            })
        },
        formatDate(dateStr) {
            let date = new Date(dateStr);
            return dateUtils.formatDate(date, 'yyyy-MM-dd hh:mm:ss');
        },
        handleCurrentChange(page) {
            this.currentPage = page;
            this.getListImage();
        },
        getListImage() {
            api.listImages(this.currentPage)
                .then(res => {
                    if (res.code === api.success_code) {
                        this.listImage = res.data.imageList;
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
  