<template>
    <div>
        <div class="music-action-bar">
            <el-button type="primary" aria-setsize="mini" @click="showAddMusic()">添加音乐</el-button>
            <el-button plain @click="getAllMusic()">全部音乐列表</el-button>
            <el-button type="success" plain @click="getTopMusic()">被顶置列表</el-button>
            <el-button type="danger" plain @click="getDeleteMusic()">被删除列表</el-button>

            <div class="music-action-bar-right">
                <!-- <el-input v-model="input2" class="w-50 m-2" placeholder="搜索" :suffix-icon="Search" /> -->
                
                <el-input v-model="searchKey" placeholder="搜索" class="input-with-select">
                    <template #prepend>
                        <el-icon @click="changSearchKey">
                            <Search />
                        </el-icon>
                    </template>

                </el-input>
            </div>
        </div>

        <div class="music-list-box">
            <el-table :data="listmusic" style="width: 100%" height="450">
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
                        <el-button type="primary" size="small" @click="edit(scope.row)">编辑</el-button>
                        <el-button type="success" v-if="scope.row.state !== '3'" size="small"
                            @click="topItem(scope.row)">顶置</el-button>
                        <el-button type="success" v-if="scope.row.state === '3'" size="small" disabled>顶置</el-button>
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
            <el-dialog v-model="deleteDialogShow" title="删除提示" width="30%">
                <span>确认删除： {{ deleteMessage }} 这首音乐吗？</span>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="deleteDialogShow = false">取 消</el-button>
                        <el-button type="danger" @click="doDeleteItem()">
                            确 认
                        </el-button>
                    </span>
                </template>
            </el-dialog>

            <!-- 音乐编辑/添加 -->
            <el-dialog v-model="editDialogShow" :title="editTitle" width="30%" :close-on-click-modal="false"
                :close-on-press-escape="false">
                <div class="music-edit-box">
                    <el-form label-width="100px" style="max-width: 460px">
                        <el-form-item label="封面">
                            <el-avatar v-if="music.picId != '' && music.picId != null" shape="square"
                                style="width: 100px; height: 100px" :src="baseUrlImage + '/' + music.picId"
                                class="img-dialog" />
                            <el-upload v-model:file-list="fileList" class="upload-demo" :action="baseUrlImage"
                                :on-success="handleUpImage" :before-remove="beforeRemove" :limit="1">
                                <el-button type="primary">上传封面</el-button>
                                <template #tip>
                                    <div class="el-upload__tip">
                                        上传一张 jpg/png 图片
                                    </div>
                                </template>

                            </el-upload>
                        </el-form-item>
                        <el-form-item label="歌曲">
                            <el-upload v-if="music.url == ''" v-model:file-list="fileList" class="upload-demo"
                                :action="baseUrlMusic" :on-success="handleUpMusic" :before-remove="beforeRemoveMusic"
                                :limit="1">
                                <el-button type="primary">上传音乐</el-button>
                                <template #tip>
                                    <div class="el-upload__tip">
                                        上传一份MP3文件
                                    </div>
                                </template>
                            </el-upload>
                            <el-button type="primary" @click="playMusic">
                                试听
                            </el-button>
                        </el-form-item>
                        <el-form-item label="歌名">
                            <el-input v-model="music.name" />
                        </el-form-item>
                        <el-form-item label="歌手id">
                            <el-input v-model="music.musicianId" />
                        </el-form-item>
                        <el-form-item label="歌手">
                            <el-input v-model="music.singerName" />
                        </el-form-item>
                        <el-form-item label="歌词">
                            <el-input v-model="music.lyric" type="textarea" :row="2" maxlength="128" />
                        </el-form-item>
                        <el-form-item label="状态">
                            <el-select v-model="music.state" placeholder="状态选择">
                                <el-option label="已删除" value="0" />
                                <el-option label="正常" value="1" />
                                <el-option label="顶置" value="3" />
                            </el-select>
                        </el-form-item>
                    </el-form>
                </div>
                <template #footer>
                    <span class="dialog-footer">
                        <el-button @click="dissmiss()" type="danger">取 消</el-button>
                        <el-button type="primary" @click="postMusic()">
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
export default {
    data() {
        return {
            searchKey:'',
            listmusic: [],
            musicState: 'all',
            musicPage: 1,
            totalPage: 20,
            deleteDialogShow: false,
            deleteMessage: '',
            deleteTargetId: '',
            baseUrlImage: 'admin/admin/image',
            baseUrlMusic: 'admin/admin/music',
            music: {
                musicId: '',
                picId: '',
                name: '',
                singerName: '',
                url: '',
                fileHighUrl: '',
                state: '',
                lyric: '',
                musicianId: ''
            },
            editDialogShow: false,
            editorCommitText: '',
            editTitle: '',
            doSearchPage:1
        }
    },
    mounted() {
        this.getMusicList();
    },
    methods: {
        dissmiss() {
            this.editDialogShow = false;
            this.resetMusic();
        },
        showAddMusic() {
            this.resetMusic();
            this.editTitle = '添加音乐';
            this.editorCommitText = '添 加'
            this.editDialogShow = true;
        },
        beforeRemove() {
            this.music.picId = '';
        },
        beforeRemoveMusic() {
            this.music.url = '';
        },
        handleUpImage(res) {
            this.music.picId = res.data.id;
        },
        handleUpMusic(res) {
            this.music.url = res.data.url;
            this.music.fileHighUrl = res.data.highId;
            this.music.musicId = res.data.id;
        },
        edit(item) {
            this.music.musicId = item.musicId;
            this.music.picId = item.picId;
            this.music.name = item.musicName;
            this.music.singerName = item.singerName;
            this.music.state = item.state;
            this.music.lyric = item.lyric;
            this.music.musicianId = item.musicianId;
            this.music.url = item.url;
            this.music.fileHighUrl = item.fileHighUrl;
            // 显示dialog
            this.editDialogShow = true;
            this.editorCommitText = '修改音乐';
            this.editTitle = '编辑音乐';
        },
        resetMusic() {
            this.music.musicId = '';
            this.music.picId = '';
            this.music.name = '';
            this.music.singerName = '';
            this.music.state = '';
            this.music.lyric = '';
            this.music.musicianId = '';
            this.music.url = '';
            this.music.fileHighUrl = '';
        },
        toastE(msg) {
            this.$message({
                message: msg,
                center: true,
                type: 'error'
            })
        },
        getMusicList() {
            api.listMusic(this.musicState, this.musicPage)
                .then(result => {
                    if (result.code === api.success_code) {
                        this.listmusic = result.data.list;
                        this.totalPage = result.data.maxPage;
                    }
                });
        },
        formatDate(dateStr) {
            let date = new Date(dateStr);
            return dateUtils.formatDate(date, 'yyyy-MM-dd hh:mm:ss');
        },
        topItem(item) {
            api.topMusic(item.musicId)
                .then(result => {
                    if (result.code === api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.getMusicList();
                    } else {
                        this.toastE(result.message);
                    }
                })
        },
        deleteItem(item) {
            //给出提示
            this.deleteDialogShow = true;
            this.deleteMessage = item.musicName;
            this.deleteTargetId = item.musicId;
            // console.log(item)
        },
        doDeleteItem() {
            api.deleteMusic(this.deleteTargetId)
                .then(result => {
                    if (result.code === api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.getMusicList();
                    } else {
                        this.toastE(result.message);
                    }
                    this.deleteDialogShow = false;
                })
        },
        postMusic() {
            api.updateMusicInfo(this.music.musicId, this.music)
                .then(result => {
                    if (result.code == api.success_code) {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'success'
                        });
                        this.editDialogShow = false;
                        this.resetMusic();
                        this.getMusicList();
                    } else {
                        this.$message({
                            message: result.message,
                            center: true,
                            type: 'error'
                        });
                    }
                })
        },
        handleCurrentChange(page) {
            this.musicPage = page;
            if(this.musicState == 'search'){
                this.changSearchKey();
            }else{
                 this.getMusicList();
            }
           
        },
        getAllMusic() {
            this.musicState = 'all';
            this.musicPage = 1;
            this.getMusicList();
        },
        getTopMusic() {
            this.musicState = '3';
            this.musicPage = 1;
            this.getMusicList();
        },
        getDeleteMusic() {
            this.musicState = '0';
            this.musicPage = 1;
            this.getMusicList();
        },
        changSearchKey(){
            this.musicState = 'search'
            api.doSearchMusicByName(this.doSearchPage,this.searchKey)
            .then(result=>{
                console.log(result);
                if(result.code===api.success_code){
                    const data = result.data.list;
                    var tempdata=[];
                    for(let i in data){
                        console.log(data[i]);
                        var temp = {
                            musicId:data[i].id,
                            picId:data[i].pic_id,
                            musicName:data[i].name,
                            singerName:data[i].singer_name,
                            state:data[i].state,
                            lyric:data[i].lyric,
                            url:data[i].url,
                            fileHighUrl:data[i].file_high_url
                        };
                        tempdata.push(temp);
                    }
                    this.totalPage = result.data.totalPage;
                    this.listmusic = tempdata;
                    this.doSearchPage=this.doSearchPage+1;
                }
            });
        },
        playMusic(){
            window.open('admin/music/'+this.music.url);
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
  