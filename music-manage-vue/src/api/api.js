import http from './http.js';

export const success_code=20000;
export const base_url='http://localhost:8090';
//解析token
export const checkToken=()=>{
    return http.requestGet('user/user/check-token');
}

