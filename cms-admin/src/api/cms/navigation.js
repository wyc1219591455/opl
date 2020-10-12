import request from '@/router/request_sys'
export function query(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/menu',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/menu',
            method: methodType,
            data
        })
    }

}
// 产品管理列表
export function queryProductList(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/productCategory/activeList',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/productCategory/activeList',
            method: methodType,
            data
        })
    }
}

export default {
    query,
    queryProductList
}