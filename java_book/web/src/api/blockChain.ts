// 权限问题后期增加
import { get, post } from '/@/utils/http/axios';
import { UserState } from '/@/store/modules/user/types';
// import axios from 'axios';
enum URL {
    list = 'http://59.110.93.238:30880/kapis/blockcontroller.kubesphere.io/v1alpha1/GetBlockByTxID',
    create = '/api/thing/create',
    update = '/api/thing/update',
    delete = '/api/thing/delete',
    detail = '/api/thing/detail',
}

const listApi = async (params: any) => get<any>({ url: URL.list, params: params, data: {}, });
const createApi = async (data: any) =>
    post<any>({ url: URL.create, params: {}, data: data, headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' } });
const updateApi = async (data: any) =>
    post<any>({ url: URL.update, data: data, headers: { 'Content-Type': 'multipart/form-data;charset=utf-8' } });
const deleteApi = async (params: any) => post<any>({ url: URL.delete, params: params, headers: {} });
const detailApi = async (params: any) => get<any>({ url: URL.detail, params: params, headers: {} });

export { listApi, createApi, updateApi, deleteApi, detailApi };
