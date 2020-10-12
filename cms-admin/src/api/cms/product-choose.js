import request from '@/router/request_sys';

// 匹配产品分类列表
export function queryMatchProductCategoryList(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/matchProductCategory',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/matchProductCategory',
            method: methodType,
            data
        })
    }
}

// 匹配产品管理列表
export function queryMatchProductList(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/matchProduct',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/matchProduct',
            method: methodType,
            data
        })
    }
}

// 匹配产品属性列表
export function queryMatchAttributeList(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/cmsMatchProperty',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/cmsMatchProperty',
            method: methodType,
            data
        })
    }
}
// 匹配产品选型绑定
export function queryBind(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/matchCms',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/matchCms',
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
// 产品管理列表
export function productList(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/productCms/byCategoryId',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/productCms/byCategoryId',
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
// 通过匹配分类id查询 匹配产品
export function queryMatchingProductList(data, methodType) {
    if (methodType === 'get') {
        return request({
            url: 'api/matchProduct/byCategoryId',
            method: 'get',
            params: data
        })
    } else {
        return request({
            url: 'api/matchProduct/byCategoryId',
            method: methodType,
            data
        })
    }
}
// 查询图纸不分页
export function queryPaperList(data, methodType) {
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

export default {
    queryMatchProductCategoryList, 
    queryMatchProductList,
    queryMatchAttributeList, 
    queryBind, 
    queryProductList, 
    queryPaper, 
    productList, 
    queryMatchingProductList, 
    queryPaperList
}
