import request from '@/router/request_sys';

// 新闻分类
export function query(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/articleCategory',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/articleCategory',
            method: methodType,
            data
        })
    }
}

// 解决方案分类
export function querySolution(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/solutionCategory',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/solutionCategory',
            method: methodType,
            data
        })
    }
}

// 新闻列表
export function queryArticleCms(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/articleCms',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/articleCms',
            method: methodType,
            data
        })
    }
}

// 解决方案列表
export function querySolutionList(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/articleCms/solution',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/articleCms/solution',
            method: methodType,
            data
        })
    }
}
// 图纸
export function queryPaper(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/CmsDrawing',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/CmsDrawing',
            method: methodType,
            data
        })
    }
}
// 图纸类型
export function paperType(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/DrawingType',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/DrawingType',
            method: methodType,
            data
        })
    }
}
// 不分页查询图纸类型
export function paperTypeNoPage(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/DrawingType/NoPaging',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/DrawingType/NoPaging',
            method: methodType,
            data
        })
    }
}
// 所有图纸（不分页）
export function queryDrawingList(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/CmsDrawing/findAllDrawings',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/CmsDrawing/findAllDrawings',
            method: methodType,
            data
        })
    }
}

// 产品分类管理列表
export function queryProductList(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/productCategory',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/productCategory',
            method: methodType,
            data
        })
    }
}

// 产品属性管理列表
export function queryPropertyList(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/productProperty',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/productProperty',
            method: methodType,
            data
        })
    }
}

// 产品管理列表
export function queryPropertyCmsList(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/productCms',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/productCms',
            method: methodType,
            data
        })
    }
}

export default {
    query,
    querySolution,
    queryArticleCms,
    queryPaper,
    querySolutionList,
    queryProductList,
    queryPropertyList,
    paperType,
    queryPropertyCmsList,
    queryDrawingList,
    paperTypeNoPage
}