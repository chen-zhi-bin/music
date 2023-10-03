<template>
    <div>
        <div class="music-action-bar">
            <el-button plain @click="getList()">待审核列表</el-button>
            <el-button type="danger" plain @click="getRefuseList()">被拒绝列表</el-button>

        </div>

        <div class="music-list-box">
            <el-table :data="listmusic" style="width: 100%" height="550">
            <el-table-column fixed prop="musicId" label="id" width="180" />
                <el-table-column prop="picId" label="封面" width="130">
                    <template v-slot="scope">
                        <el-text v-if="scope.row.picId == null" class="w-100px" truncated>暂 无 封 面</el-text>
                        <el-avatar v-else shape="square" style="width: 100px; height: 100px"
                            :src="baseUrlImage + '/' + scope.row.picId" />
                    </template>
                </el-table-column>
                <el-table-column prop="musicName" label="歌名" width="150" >
                    <template v-slot="scope">
                        <div v-html="scope.row.musicName"></div>
                    </template>
                </el-table-column>
                <el-table-column prop="singerName" label="歌手" width="150" />
                <el-table-column prop="state" label="状态" width="120">
                    <template v-slot="scope">
                        <div v-if="scope.row.state === '0'">
                            <el-tag type="danger">已删除</el-tag>
                        </div>
                        <div v-if="scope.row.state === '1'">
                            <el-tag>正 常</el-tag>
                        </div>
                        <div v-if="scope.row.state === '3'">
                            <el-tag type="success">顶 置</el-tag>
                        </div>
                        <div v-if="scope.row.state === '4'">
                            <el-tag type="danger">待审核</el-tag>
                        </div>
                        <div v-if="scope.row.state === '5'">
                            <el-tag type="danger">拒绝</el-tag>
                        </div>
                    </template>
                </el-table-column>
                <el-table-column prop="lyric" label="歌词" width="200">
                    <template v-slot="scope">
                        <el-text v-if="scope.row.lyric == null" class="w-100px" truncated>暂 无 歌 词</el-text>
                        <el-text v-else class="w-100px" truncated>{{ scope.row.lyric }}</el-text>
                    </template>
                </el-table-column>
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
                <el-table-column prop="userName" label="更改人" width="120" />

                <el-table-column fixed="right" label="Operations" width="200">
                    <template v-slot="scope">
                        <el-button type="primary" v-if="scope.row.state == '4'" size="small" @click="pass(scope.row)">通过</el-button>
                        <el-button type="primary" v-if="scope.row.state == '5'" size="small" @click="pass(scope.row)">通过</el-button>
                        <el-button type="danger" v-if="scope.row.state == '4'" size="small"
                            @click="refuse(scope.row)">禁止</el-button>
                    </template>
                </el-table-column>

                
            </el-table>
        </div>
        <div class="music-manage-foot">
            <el-pagination layout="prev, pager, next" :total="1000" @current-change="handleCurrentChange"
                v-model:page-count="totalPage" />
        </div>
        <div class="music-dialog-box">
            <el-dialog v-model="deleteDialogShow" title="拒绝提示" width="30%">
                <span>确认拒绝通过审核： {{ musicName }} 这首音乐吗？</span>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="deleteDialogShow = false">取 消</el-button>
                        <el-button type="danger" @click="doResfuse()">
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
            listmusic: [],
            musicPage: 1,
            totalPage: 20,
            baseUrlImage: 'admin/admin/image',
            deleteDialogShow: false,
            musicName: '',
            deleteTargetId: '',
            isAssessor:true,
            refuseLitPage: 1,
            refuseLitTotalPage: 20,
        }
    },
    mounted() {
        this.getList();
    },
    methods: {
        getRefuseList(){
        api.refuseList(this.refuseLitPage,30)
        .then(result=>{
            if (result.code === api.success_code) {
                        this.listmusic = result.data.list;
                        this.refuseLitTotalPage = result.data.maxPage;
                        console.log(this.listmusic)
                }
        })
        this.isAssessor = false;
        },
        doResfuse(){
            api.refuseAssessor(this.deleteTargetId)
            .then(result=>{
                if (result.code === api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.getList();
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
        pass(item){
            api.passAssessor(item.musicId)
            .then(result=>{
                if (result.code === api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.getList();
                    } else {
                        this.toastE(result.message);
                    }
            })
        },
        refuse(item){
             //给出提示
         this.deleteDialogShow = true;
            this.musicName = item.musicName;
            this.deleteTargetId = item.musicId;
            // console.log(item)
        },
        formatDate(dateStr) {
            let date = new Date(dateStr);
            return dateUtils.formatDate(date, 'yyyy-MM-dd hh:mm:ss');
        },
        handleCurrentChange(page) {
            if(this.isAssessor){
                    this.musicPage = page;
                 this.getList();
            }else{
                this.refuseLitPage = page;
                this.getRefuseList();
            }
          
           
        },
       getList(){
        api.assessorList(this.musicPage,30)
        .then(result=>{
            if (result.code === api.success_code) {
                        this.listmusic = result.data.list;
                        this.totalPage = result.data.maxPage;
                        console.log(this.listmusic)
                }
        })
        this.isAssessor = true;
       }
    }
}
</script>
  
<style>
.img-dialog {
    margin-bottom: 10px;
}

.music-manage-foot {
    width: 100%;
    margin-left: 200px;
}

.music-action-bar {
    display: flex;
}

.music-action-bar-right {
    width: 30%;
    float: right;
    margin-left: 100px;
}
</style>
  