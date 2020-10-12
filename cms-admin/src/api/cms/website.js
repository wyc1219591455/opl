import request from '@/router/request_sys';

export function query(params) {
    return request({
        url: 'api/webConfig',
        method: 'get',
        params
    })
}
export function edit(data, type) {
    return request({
        url: 'api/webConfig',
        method: type,
        data
    })
}
export function remove(data) {
    return request({
        url: 'api/webConfig',
        method: 'delete',
        data
    })
}
export function getLanguageList(params) {
    return request({
        url: 'api/webConfig/language',
        method: 'get',
        params
    })
}

export function getLanguageAll(params) {
    return request({
        url: 'api/language/activeLanguage',
        method: 'get',
        params
    })
}

// 获取Banner列表
export function getBannerAll(params) {
    return request({
        url: 'api/banner',
        method: 'get',
        params
    })
}

// Banner新增
export function addBanner(data) {
    return request({
        url: 'api/banner',
        method: 'post',
        data
    })
}
// Banner编辑
export function editBanner(data) {
    return request({
        url: 'api/banner',
        method: 'put',
        data
    })
}
// 删除Banner
export function delBanner(data) {
    return request({
        url: 'api/banner',
        method: 'delete',
        data
    })
}

// 获取友情链接分类
export function getCategoryAll() {
    return request({
        url: 'api/friendLink/category',
        method: 'get'
    })
}

// 新增友情链接
export function addCategory(data) {
    return request({
        url: 'api/friendLink',
        method: 'post',
        data
    })
}
// 获取所有友情链接
export function friendLinkAll(params) {
    return request({
        url: 'api/friendLink',
        method: 'get',
        params
    })
}

// 删除友情链接
export function delFriendLink(data) {
    return request({
        url: 'api/friendLink',
        method: 'delete',
        data
    })
}
// 编辑友情链接
export function editFriendLink(data) {
    return request({
        url: 'api/friendLink',
        method: 'put',
        data
    })
}

// 鑫鑫直线大事记
export function queryBigEvent(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/bigEvent',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/bigEvent',
            method: methodType,
            data
        })
    }
}

export default {
    query,
    edit,
    getLanguageList,
    remove,
    getLanguageAll,queryBigEvent
}