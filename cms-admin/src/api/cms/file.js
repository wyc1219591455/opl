import request from '@/router/request_sys';

// 获取文件列表
export function getFile(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/staticFileManage/treeList',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/staticFileManage/treeList',
            method: methodType,
            data
        })
    }
}

// 获取文件夹下面内容
export function getFileContent(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/staticFileManage/list',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/staticFileManage/list',
            method: methodType,
            data
        })
    }
}
// 创建文件夹
export function creatFile(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/staticFileManage/createFolder',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/staticFileManage/createFolder',
            method: methodType,
            data
        })
    }
}
// 删除文件目录
export function removeFile(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/staticFileManage/remove',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/staticFileManage/remove',
            method: methodType,
            data
        })
    }
}
// 重命名
export function renameFile(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/staticFileManage/rename',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/staticFileManage/rename',
            method: methodType,
            data
        })
    }
}
// 移动
export function moveFile(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/staticFileManage/move',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/staticFileManage/move',
            method: methodType,
            data
        })
    }
}
// 复制
export function copyFile(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/staticFileManage/copy',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/staticFileManage/copy',
            method: methodType,
            data
        })
    }
}

export default {
    getFile,
    getFileContent,
    creatFile,
    removeFile,
    renameFile,
    moveFile,
    copyFile
}
