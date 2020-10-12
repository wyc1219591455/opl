import request from '@/router/request_sys';
import qs from 'qs';

export function getCustomerList(params) {
    return request({
        url: 'api/customer',
        method: 'get',
        params
    })
}
export function edit(data) {
    return request({
        url: 'api/customer',
        method: 'put',
        data
    })
}
export function download(params) {
    return request({
        url: 'api/customer/download?' + qs.stringify(params, { indices: false }),
        method: 'get',
        responseType: 'blob'
    })
}

export default {
    getCustomerList,
    edit,
    download
}