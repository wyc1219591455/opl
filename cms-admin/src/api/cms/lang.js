
import request from '@/router/request_sys'
//查询所有数据
export function getLanguageAll(params) {
    return request({
        url: 'api/language',
        method: 'get',
        params
    })
}

//编辑
export function editLanguage(data) {
    return request({
        url: 'api/language',
        method: 'put',
        data
    })
}

//新增
export function addLanguage(data) {
    return request({
        url: 'api/language',
        method: 'post',
        data
    })
}

//删除
export function delLanguage(data) {
    return request({
        url: 'api/language',
        method: 'delete',
        data
    })
}


