// 权限问题后期增加
import { get, post } from '/@/utils/http/axios';
import { UserState } from '/@/store/modules/user/types';
import { USER_ID } from '../store/constants';
// import axios from 'axios';
enum URL {
    save = '/api/application/save',
    list = '/api/application/list',
    pass = '/api/application/pass',
    reject = '/api/application/reject',

    // wish = '/api/thingWish/wish',
    // unWish = '/api/thingWish/unWish',
}

export interface userId {
    userId: string;
    // code: string;
}

// const saveApi = async (data: any) => post<any>({ url: URL.save, params: {}, data: data, headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' } });
const saveApi = async (data: any) =>
    post<any>({
        url: URL.save,
        params: {},
        data: data,
        headers: {}
    });
const listApi = async (data: any) =>
    get<any>({
        url: URL.list,
        params: {},
        data: {},
        headers: {}
    });
    const listByUserApi = async (params: any) =>
        get<any>({
            url: `${URL.list}/${params}`,
            params: {},
            data: {},
            headers: {}
        });
const passApi = async (params: any) =>
    post<any>({
        url: URL.pass,
        params: params,
        data: {},
        headers: {}
    });
const rejectApi = async (params: any) =>
    post<any>({
        url: URL.reject,
        params: params,
        // data: {},
        headers: {}
    });
export { saveApi, listApi ,listByUserApi,passApi,rejectApi };
