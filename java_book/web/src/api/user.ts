// 权限问题后期增加
import { get, post } from '/@/utils/http/axios';
import { UserState } from '/@/store/modules/user/types';
import { useUserStore } from '../store';
import VueCookies from 'vue-cookies';

const { cookies } = useCookies();
import { useCookies } from "vue3-cookies";
// import axios from 'axios';
enum URL {
    login = '/api/user/login',
    userList = '/api/admin/list',
    detail = '/api/user/detail',
    create = '/api/user/create',
    update = '/api/user/update',
    delete = '/api/user/delete',
    userLogin = '/api/user/userLogin',
    userRegister = '/api/user/userRegister',
    updateUserPwd = '/api/user/updatePwd',
    updateUserInfo = '/api/user/updateUserInfo',
    sendCode = '/api/user/code',
    userLogout = '/api/user/userLogout',
}
interface LoginRes {
    token: string;
}

export interface LoginData {
    username: string;
    password: string;
}
export interface LoginData2 {
    phone: string;
    // code: string;
}
// let userStore:any = null
// if(userStore===null){
//     userStore=useUserStore()
// }
// VueCookies.set("userInfo",result.data,0);
cookies.set("userInfo2", "test", 30);

const loginApi = async (data: LoginData) => {
    const response = await post<LoginRes>({ url: URL.login, data });

    // if (response?.token) {
    // 获取当前时间
    const now = new Date();
    // 计算到24小时后的剩余时间
    const expires = new Date(now.getTime() + (24 * 60 * 60 * 1000) - (now.getHours() * 60 * 60 * 1000 + now.getMinutes() * 60 * 1000 + now.getSeconds() * 1000));

    // 设置 cookie，有效期为24小时减去当前时间
    document.cookie = `token=${response.token}; expires=${expires.toUTCString()}; path=/`;
    // }

    return response;
};



// const loginApi = async (data: LoginData) => post<any>({ url: URL.login, data,});
const listApi = async (params: any) => get<any>({ url: URL.userList, params: params, data: {}, });
const detailApi = async (params: any) => get<any>({ url: URL.detail, params: params, data: {}, });
const createApi = async (data: any) => post<any>({ url: URL.create, params: {}, data: data, headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' } });
const updateApi = async (data: any) => post<any>({ url: URL.update, data: data, headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' } });
const deleteApi = async (params: any) => post<any>({ url: URL.delete, params: params, headers: {} });
const userLoginApi = async (data: LoginData2) => post<any>({ url: URL.userLogin, data, headers: {} });
const userRegisterApi = async (data: any) => post<any>({ url: URL.userRegister, params: {}, data: data, headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' } });
const updateUserPwdApi = async (params: any) => post<any>({ url: URL.updateUserPwd, params: params });
const updateUserInfoApi = async (data: any) => post<any>({ url: URL.updateUserInfo, data: data, headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' } });
const sendCodeApi = async (data: LoginData2) => post<any>({ url: `${URL.sendCode}/${data.phone}`, data: {}, headers: {} });
const userLogoutApi = async () => post<any>({ url: URL.userLogout, data: {}, headers: {} });
export { userLogoutApi, sendCodeApi, loginApi, listApi, detailApi, createApi, updateApi, deleteApi, userLoginApi, userRegisterApi, updateUserPwdApi, updateUserInfoApi };
