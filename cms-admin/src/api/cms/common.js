import request from '@/router/request_sys';
export function getLanguageAll(params) {
    return request({
        url: 'api/language',
        method: 'get',
        params
    })
}
export default {
    getLanguageAll,
}