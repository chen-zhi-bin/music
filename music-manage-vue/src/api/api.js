import http from './http.js';

export const success_code=20000;
export const base_url='http://localhost:8090';
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