import request from '@/router/request_sys';

// 微信菜单
export function queryWeChatMenuList(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/WeChatMenu',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/WeChatMenu',
            method: methodType,
            data
        })
    }
}
// 微信关键字
export function queryWeChatKeyWords(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/WeChatReply',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/WeChatReply',
            method: methodType,
            data
        })
    }
}

// 微信图文素材获取
export function queryWeChatMaterial(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'web/wxMaterial',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'web/wxMaterial',
            method: methodType,
            data
        })
    }
}

export default {
    queryWeChatMenuList,
    queryWeChatKeyWords,
    queryWeChatMaterial
}
