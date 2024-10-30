import http from './http.js';

export const success_code=20000;
export const base_url='http://localhost:8090';
export const upload_url = base_url + '/admin/music/musics';
//解析token
export const checkToken=()=>{
    return http.requestGet('user/user/check-token');
}
//退出登录
export const logout=()=>{
    return http.requestGet('user/user/logout');
}
//获取音乐数
export const musicCount=()=>{
    return http.requestGet('admin/admin/web_size_info/music_count');
}
//获取用户数
export const userCount=()=>{
    return http.requestGet('/admin/web_size_info/user_count');
}
//获取歌手数
export const musicianCount=()=>{
    return http.requestGet('admin/admin/web_size_info/musician_count');
}
//获取总点击数
export const mtotalCount=()=>{
    return http.requestGet('admin/admin/web_size_info/view_count');
}
//获取七天播放量
export const sevenHistoryCount=()=>{
    return http.requestGet('admin/admin/web_size_info/seven_history');
}
//获取音乐列表
export const listMusic=(state,page)=>{
    return http.requestPost('admin/admin/music/list/'+state+'/'+page+'/10');
}
//顶置音乐
export const topMusic=(musicId)=>{
    return http.requestPost('admin/admin/music/top/'+musicId);
}
//删除音乐
export const deleteMusic=(musicId)=>{
    return http.requestDelete('admin/admin/music/'+musicId);
}
// 更新音乐信息
export const updateMusicInfo=(musicId,data)=>{
    return http.requestPut('admin/admin/music/info/'+musicId,data);
}
//根据名字搜索音乐
export const doSearchMusicByName=(page,key)=>{
    return http.requestPost('admin/admin/music/list/name/'+page+'/10'+'?name='+key);
}
//获取评论列表
export const getCommentList=(page)=>{
    return http.requestGet('admin/admin/comment/list?page='+page+'&size=30')
}
//删除评论
export const deleteComment=(commentId)=>{
    return http.requestDelete('admin/admin/comment/'+commentId);
}
//获取图片列表
export const listImages=(page)=>{
    return http.requestPost('admin/admin/image/list/'+page+'/10');
}
//删除图片
export const deleteImage=(imgId)=>{
    return http.requestDelete('admin/admin/image/'+imgId);
}
//恢复图片
export const reImage=(imgId)=>{
    return http.requestPut('admin/admin/image/recover/'+imgId);
}
//获取歌手列表
export const listMusician=(page)=>{
    return http.requestGet('admin/admin/singer/'+page+'/10');
}
//添加歌手信息
// export const addMusicianInfo=(data)=>{
//     return http.requestPost('admin/admin/singer',data);
// }
//修改歌手信息 
export const updateMusicianInfo=(musicianId,data)=>{
    return http.requestPut('admin/admin/singer/info/'+musicianId,data);
}
//删除音乐人
export const deleteMusician=(musicianId)=>{
    return http.requestDelete('admin/admin/singer/'+musicianId);
}
//恢复音乐人
export const reMusician=(musicianId)=>{
    return http.requestPut('admin/admin/singer/recover/'+musicianId);
}
//获取用户列表
export const listUser=(page)=>{
    return http.requestGet('user/user/list?page='+page+'&size=12')
}
//删除用户
export const deleteUser=(userId)=>{
    return http.requestDelete('user/user/'+userId);
}
//恢复user
export const reUser=(userId)=>{
    return http.requestPost('user/user/re/'+userId);
}
//添加管理员  具体情况看user-list.vue中
export const addAdmin=(captchaKey,captchaCode,roleId,data)=>{
    return http.requestPost('user/user/addAdmin?captcha_code='+captchaCode+'&captcha_key='+captchaKey+'&role_id='+roleId,data);
}
//获取loop信息
export const listLoop=()=>{
    return http.requestPost('admin/admin/looper/list');
}
//添加loop  具体看manage-loop
export const addLoop=(data)=>{
    return http.requestPost('admin/admin/looper',data);
}
//修改loop 
export const updateLoop=(id,data)=>{
    return http.requestPut('admin/admin/looper/'+id,data);
}
//删除loop
export const deleteLoop=(loopId)=>{
    return http.requestDelete('admin/admin/looper/'+loopId);
}
//获取待审核列表
export const assessorList=(page,size)=>{
    return http.requestGet('admin/admin/assessor/list/'+page+'/'+size);
}
//通过审核
export const passAssessor=(musicId)=>{
    return http.requestPost('admin/admin/assessor/pass/'+musicId);
}
//退回
export const refuseAssessor=(musicId)=>{
    return http.requestDelete('admin/admin/assessor/refuse/'+musicId);
}
//获取未通过列表
export const refuseList=(page,size)=>{
    return http.requestGet('admin/admin/assessor/refuseList/'+page+'/'+size);
}
//多文件上传
export const upload=(formData)=>{
    return http.requestPost('admin/admin/music/musics?files='+formData);
}