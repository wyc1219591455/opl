/**
 * 用户管理 用户模块 菜单路由
 */
const users = {
        id: "user",
        label: "系统管理",
        path: "/system",
        icon: "iconfont icon-renyuanjieshao",
        parentId: "admin",
        component: "",
        level: 1,
        meta: {
            i18n: "user"
        },
        children: [
            {
                id: "index",
                label: "用户管理",
                path: "/system/user/index",
                icon: "iconfont icon-renyuanjieshao",
                parentId: "user",
                component: "/system/user/index",
                level: 2,
                children: null,
                meta: {
                    i18n: "user-list",
                    keepAlive: true
                },
            },
            {
                id: "index",
                label: "角色管理",
                path: "/system/role/index",
                icon: "iconfont icon-rizhi",
                parentId: "user",
                component: "/system/role/index",
                level: 2,
                children: null,
                meta: {
                    i18n: "user-add",
                    keepAlive: true
                },
            },
            {
                id: "index",
                label: "部门管理",
                path: "/system/dept/index",
                icon: "iconfont icon-rizhi",
                parentId: "user",
                component: "/system/dept/index",
                level: 2,
                children: null,
                meta: {
                    i18n: "user-add",
                    keepAlive: true
                },
            },
            {
                id: "index",
                label: "菜单管理",
                path: "/system/menu/index",
                icon: "iconfont icon-rizhi",
                parentId: "user",
                component: "/system/menu/index",
                level: 2,
                children: null,
                meta: {
                    i18n: "user-add",
                    keepAlive: true
                },
            },
            {
                id: "index",
                label: "字典管理",
                path: "/system/dict/index",
                icon: "iconfont icon-rizhi",
                parentId: "user",
                component: "/system/dict/index",
                level: 2,
                children: null,
                meta: {
                    i18n: "user-add",
                    keepAlive: true
                },
            },
            {
                id: "index",
                label: "岗位管理",
                path: "/system/job/index",
                icon: "iconfont icon-rizhi",
                parentId: "user",
                component: "/system/job/index",
                level: 2,
                children: null,
                meta: {
                    i18n: "user-add",
                    keepAlive: true
                },
            },
            // {
            //     id: "x-password",
            //     label: "修改密码",
            //     path: "/user/x-password",
            //     icon: "iconfont icon-lock",
            //     parentId: "user",
            //     component: "/user/x-password",
            //     level: 2,
            //     children: null,
            //     meta: {
            //         i18n: "password",
            //         keepAlive: true
            //     },
            // }
        ],
    };





// 导出路由
export default {
    id: "admin",
    label: "系统管理",
    path: "/admin",
    icon: "iconfont icon-icon_shezhi",
    parentId: "0",
    component: "",
    level: 0,
    meta: {
        i18n: "admin"
    },
    children: [
        users,
    ]
};
