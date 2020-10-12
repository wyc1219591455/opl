import request from '@/router/request_sys';
import qs from 'qs';

export function query(params) {
    return request({
        url: 'api/consult',
        method: 'get',
        params
    })
}
export function edit(data) {
    return request({
        url: 'api/consult',
        method: 'put',
        data
    })
}
export function download(params) {
    return request({
        url: 'api/consult/download?' + qs.stringify(params, { indices: false }),
        method: 'get',
        responseType: 'blob'
    })
}

export default {
    query,
    edit,
    download
}