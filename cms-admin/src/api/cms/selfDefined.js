import request from '@/router/request_sys';

// 新闻分类
export function query(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/CmsSinglePage',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/CmsSinglePage',
            method: methodType,
            data
        })
    }
}

export default {
    query
}
