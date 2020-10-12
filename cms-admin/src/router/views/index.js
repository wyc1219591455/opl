import Layout from '@/page/index/layout';
import PLayout from '@/page/index/';
// export default [{
//     path: '/wel',
//     component: Layout,
//     redirect: '/wel/index',
//     children: [
//         {
//             path: 'index',
//             name: '首页',
//             meta: {
//                 i18n: 'dashboard',
//                 keepAlive: true,
//                 component: "wel", // 需要写上组件的name属性
//             },
//             component: () =>
//                 import( /* webpackChunkName: "views" */ '@/views/wel')
//         }
//     ]
// }, {
//     path: '/x-info',
//     component: Layout,
//     redirect: '/x-info/index',
//     children: [{
//         path: 'index',
//         name: '个人信息',
//         meta: {
//             i18n: 'info'
//         },
//         component: () =>
//             import( /* webpackChunkName: "views" */ '@/views/user/x-info')
//     }]
// }, {
//     path: '/x-password',
//     component: Layout,
//     redirect: '/x-password/index',
//     children: [{
//         path: 'index',
//         name: '修改密码',
//         meta: {
//             i18n: 'password'
//         },
//         component: () =>
//             import( /* webpackChunkName: "views" */ '@/views/user/x-password')
//     }]
// }]

/**
 * 路由多嵌套一层与动态路由层级相同，方便缓存
 */
export default [
    {
        path: "/",
        component: PLayout,
        children: [{
            path: '/wel',
            component: Layout,
            redirect: '/wel/index',
            children: [
                {
                    path: 'index',
                    name: '首页',
                    meta: {
                        i18n: 'dashboard',
                        keepAlive: true,
                        component: "wel", // 需要写上组件的name属性
                    },
                    component: () =>
                        import( /* webpackChunkName: "views" */ '@/views/wel')
                }
            ]
        }, {
            path: '/x-info',
            component: Layout,
            redirect: '/x-info/index',
            children: [{
                path: 'index',
                name: '个人信息',
                meta: {
                    i18n: 'info'
                },
                component: () =>
                    import( /* webpackChunkName: "views" */ '@/views/user/x-info')
            }]
        }, {
            path: '/x-password',
            component: Layout,
            redirect: '/x-password/index',
            children: [{
                path: 'index',
                name: '修改密码',
                meta: {
                    i18n: 'password'
                },
                component: () =>
                    import( /* webpackChunkName: "views" */ '@/views/user/x-password')
            }]
        },
        // 语言新增
        {
            path: '/langAdd',
            component: Layout,
            redirect: '/langAdd/index',
            children: [{
                path: 'index',
                name: '语言新增',
                meta: { title: '语言新增', noCache: true },
                component: (resolve) => require(['@/views/cms/language/lang-add'], resolve),
            }]
        },
        // banner新增
        {
            path: '/bannerAdd',
            component: Layout,
            redirect: '/bannerAdd/index',
            children: [{
                path: 'index',
                name: 'banner新增',
                meta: { title: 'banner新增', noCache: true },
                component: (resolve) => require(['@/views/cms/website-manager/banner/banner-add'], resolve),
            }]
        },
         // 网站新增
         {
            path: '/websiteSet',
            component: Layout,
            redirect: '/websiteSet/index',
            children: [{
                path: 'index',
                name: '网站新增',
                meta: { title: '网站新增', noCache: true },
                component: (resolve) => require(['@/views/cms/website-manager/website/website-set'], resolve),
            }]
        },
        // 历史大事记
        {
            path: '/historyAdd',
            component: Layout,
            redirect: '/historyAdd/index',
            children: [{
                path: 'index',
                name: '大事记新增',
                meta: { title: '大事记新增', noCache: true },
                component: (resolve) => require(['@/views/cms/website-manager/history/history-add'], resolve),
            }]
        },
        // 导航菜单新增
        {
            path: '/navigationAdd',
            component: Layout,
            redirect: '/navigationAdd/index',
            children: [{
                path: 'index',
                name: '导航菜单新增',
                meta: { title: '导航菜单新增', noCache: true },
                component: (resolve) => require(['@/views/cms/website-manager/navigation/add'], resolve),
            }]
        },
        // 友情链接新增
        {
            path: '/friendsLinkAdd',
            component: Layout,
            redirect: '/friendsLinkAdd/index',
            children: [{
                path: 'index',
                name: '友情链接新增',
                meta: { title: '友情链接新增', noCache: true },
                component: (resolve) => require(['@/views/cms/website-manager/friends-link/friends-link-add'], resolve),
            }]
        },
        // 关键字回复新增
        {
            path: '/keyWordsAdd',
            component: Layout,
            redirect: '/keyWordsAdd/index',
            children: [{
                path: 'index',
                name: '关键字回复新增',
                meta: { title: '关键字回复新增', noCache: true },
                component: (resolve) => require(['@/views/cms/wechat-manager/wechat-keyWords/add'], resolve),
            }]
        },
        // 微信菜单新增
        {
            path: '/wechatMenuAdd',
            component: Layout,
            redirect: '/wechatMenuAdd/index',
            children: [{
                path: 'index',
                name: '微信菜单新增',
                meta: { title: '微信菜单新增', noCache: true },
                component: (resolve) => require(['@/views/cms/wechat-manager/wechat-menu/add'], resolve),
            }]
        },
        // 自定义页面新增
        {
            path: '/selfAdd',
            component: Layout,
            redirect: '/selfAdd/index',
            children: [{    
                path: 'index',
                name: '自定义页面新增',
                meta: { title: '自定义页面新增', noCache: true },
                component: (resolve) => require(['@/views/cms/self-defined/add'], resolve),
            }]
        },
        // 产品匹配分类新增
        {
            path: '/productSortsAdd',
            component: Layout,
            redirect: '/productSortsAdd/index',
            children: [{    
                path: 'index',
                name: '产品匹配分类新增',
                meta: { title: '产品匹配分类新增', noCache: true },
                component: (resolve) => require(['@/views/cms/product-choose/product-sorts/add'], resolve),
            }]
        },
        // 产品匹配属性新增
        {
            path: '/productTypeAdd',
            component: Layout,
            redirect: '/productTypeAdd/index',
            children: [{    
                path: 'index',
                name: '产品匹配属性新增',
                meta: { title: '产品匹配属性新增', noCache: true },
                component: (resolve) => require(['@/views/cms/product-choose/product-type/add'], resolve),
            }]
        },
        // 产品匹配属性新增
        {
            path: '/binding',
            component: Layout,
            redirect: '/binding/index',
            children: [{    
                path: 'index',
                name: '产品匹配属性新增',
                meta: { title: '产品匹配属性新增', noCache: true },
                component: (resolve) => require(['@/views/cms/product-choose/product-binding/add'], resolve),
            }]
        },
        // 匹配产品管理新增
        {
            path: '/matchProductAdd',
            component: Layout,
            redirect: '/matchProductAdd/index',
            children: [{    
                path: 'index',
                name: '匹配产品管理新增',
                meta: { title: '匹配产品管理新增', noCache: true },
                component: (resolve) => require(['@/views/cms/product-choose/product/add'], resolve),
            }]
        },
        // 新闻分类管理新增
        {
            path: '/newsClassAdd',
            component: Layout,
            redirect: '/newsClassAdd/index',
            children: [{    
                path: 'index',
                name: '新闻分类管理新增',
                meta: { title: '新闻分类管理新增', noCache: true },
                component: (resolve) => require(['@/views/cms/content-manager/news-class/news-class-add'], resolve),
            }]
        },
        // 图纸新增
        {   
            path: '/drawAdd',
            component: Layout,
            redirect: '/drawAdd/index',
            children: [{    
                path: 'index',
                name: '图纸新增',
                meta: { title: '图纸新增', noCache: true },
                component: (resolve) => require(['@/views/cms/content-manager/draw-paper/add'], resolve),
            }]
        },
        // 解决方案分类新增
        {
            path: '/schemeAdd',
            component: Layout,
            redirect: '/schemeAdd/index',
            children: [{    
                path: 'index',
                name: '解决方案分类新增',
                meta: { title: '解决方案分类新增', noCache: true },
                component: (resolve) => require(['@/views/cms/content-manager/scheme/scheme-add'], resolve),
            }]
        },
        // 新闻新增
        {
            path: '/newsManageAdd',
            component: Layout,
            redirect: '/newsManageAdd/index',
            children: [{    
                path: 'index',
                name: '新闻新增',
                meta: { title: '新闻新增', noCache: true },
                component: (resolve) => require(['@/views/cms/content-manager/news-manage/news-manage-add'], resolve),
            }]
        },
        // 解决方案新增
        {
            path: '/schemeManageAdd',
            component: Layout,
            redirect: '/schemeManageAdd/index',
            children: [{    
                path: 'index',
                name: '解决方案新增',
                meta: { title: '解决方案新增', noCache: true },
                component: (resolve) => require(['@/views/cms/content-manager/scheme-manage/scheme-manage-add'], resolve),
            }]
        },  
        // 产品分类新增
        {
            path: '/productAdd',
            component: Layout,
            redirect: '/productAdd/index',
            children: [{    
                path: 'index',
                name: '产品分类新增',
                meta: { title: '产品分类新增', noCache: true },
                component: (resolve) => require(['@/views/cms/content-manager/product/add'], resolve),
            }]
        }, 
        // 产品属性新增
        {
            path: '/attributesAdd',
            component: Layout,
            redirect: '/attributesAdd/index',
            children: [{    
                path: 'index',
                name: '产品属性新增',
                meta: { title: '产品属性新增', noCache: true },
                component: (resolve) => require(['@/views/cms/content-manager/product-attributes/add'], resolve),
            }]
        },
        // 产品管理新增
        {
            path: '/productManageAdd',
            component: Layout,
            redirect: '/productManageAdd/index',
            children: [{    
                path: 'index',
                name: '产品属性新增',
                meta: { title: '产品属性新增', noCache: true },
                component: (resolve) => require(['@/views/cms/content-manager/product-manage/add'], resolve),
            }]
        },
        // 图纸格式新增
        {
            path: '/paperAdd',
            component: Layout,
            redirect: '/paperAdd/index',
            children: [{    
                path: 'index',
                name: '图纸格式新增',
                meta: { title: '图纸格式新增', noCache: true },
                component: (resolve) => require(['@/views/cms/content-manager/paper-type/add'], resolve),
            }]
        },
    ],
    }
]
