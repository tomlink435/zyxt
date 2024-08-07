// 权限问题后期增加
import { get, post } from '/@/utils/http/axios';
import { UserState } from '/@/store/modules/user/types';
import { useUserStore } from '../store';
// import axios from 'axios';
enum URL {
    login = '/api/user/login',
    userList = '/api/user/list',
    detail = '/api/user/detail',
    create = '/api/user/create',
    update = '/api/user/update',
    delete = '/api/user/delete',
    userLogin = '/api/user/userLogin',
    userRegister = '/api/user/userRegister',
    updateUserPwd = '/api/user/updatePwd',
    updateUserInfo = '/api/user/updateUserInfo',
    sendCode = '/api/user/code',
    userLogout= '/api/user/userLogout',
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

const loginApi = async (data: LoginData) => post<any>({ url: URL.login, data,});
const listApi = async (params: any) => get<any>({ url: URL.userList, params: params, data: {}, });
const detailApi = async (params: any) => get<any>({ url: URL.detail, params: params, data: {},});
const createApi = async (data: any) => post<any>({ url: URL.create, params: {}, data: data, headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' } });
const updateApi = async (data: any) => post<any>({ url: URL.update, data: data, headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' } });
const deleteApi = async (params: any) => post<any>({ url: URL.delete, params: params, headers: {} });
const userLoginApi = async (data: LoginData2) => post<any>({ url: URL.userLogin, data, headers: {} });
const userRegisterApi = async (data: any) => post<any>({ url: URL.userRegister, params: {}, data: data, headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' } });
const updateUserPwdApi = async (params: any) => post<any>({ url: URL.updateUserPwd, params: params });
const updateUserInfoApi = async (data: any) => post<any>({ url: URL.updateUserInfo, data: data, headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' } });
const sendCodeApi = async (data: LoginData2) => post<any>({ url: `${URL.sendCode}/${data.phone}`, data: {}, headers: {} });
const userLogoutApi = async () => post<any>({ url: URL.userLogout, data: {}, headers: {} });
export {userLogoutApi, sendCodeApi, loginApi, listApi, detailApi, createApi, updateApi, deleteApi, userLoginApi, userRegisterApi, updateUserPwdApi, updateUserInfoApi};
