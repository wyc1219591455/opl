import request from '@/router/request'
export function query(params) {
    return request({
        url: 'api/message',
        method: 'get',
        params
    })
}
export function edit(data) {
    return request({
        url: 'api/message',
        method: 'put',
        data
    })
}
export function remove(data) {
    return request({
        url: 'api/message',
        method: 'delete',
        data
    })
}
export function add(data) {
    return request({
        url: 'api/message',
        method: 'post',
        data
    })
}
export function download() {
    return request({
        url: 'api/message/download',
        method: 'get'
    })
}
export default {
    query,
    edit,
    remove,
    add,
    download
}