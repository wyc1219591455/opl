/**
 * 全站权限配置
 *
 */
import router from './router/router'
import store from './store'
import { validatenull } from '@/util/validate'
import { getToken } from '@/util/auth'
import { getStore, getsessionStore } from '@/util/store'
import { mapRoutesSource } from "@/util/util";
import NProgress from 'nprogress' // progress bar
import 'nprogress/nprogress.css' // progress bar style
NProgress.configure({ showSpinner: false });
const lockPage = store.getters.website.lockPage; //锁屏
router.beforeEach((to, from, next) => {
    //缓冲设置
    // if (to.meta.keepAlive === true && store.state.tags.tagList.some(ele => {
    //     return ele.value === to.fullPath;
    // })) {
    //     to.meta.$keepAlive = true;
    // } else {
    //     NProgress.start()
    //     if (to.meta.keepAlive === true && validatenull(to.meta.$keepAlive)) {
    //         to.meta.$keepAlive = true;
    //     } else {
    //         to.meta.$keepAlive = false;
    //     }
    // }

    // 缓存处理
    if (to.meta && to.meta.keepAlive && to.meta){
        let temp = _.cloneDeep(store.getters.include) || [];
        let include = _.uniq([...temp, to.meta.component]);
        store.commit("SET_INCLUDE", include);
    }

    const meta = to.meta || {};

    // 白名单中的路由，不要登录等其他操作 直接通过
    if(meta.isWhite){
        next();
        return false;
    }

    let token = getsessionStore('token');

    if (token) {
        if (store.getters.isLock && to.path != lockPage) { //如果系统激活锁屏，全部跳转到锁屏页
            next({ path: lockPage })
            
        } else if (to.path === '/login') { //如果登录成功访问登录页跳转到主页
            next({ path: '/wel/index' })
        } else {
            //如果用户信息为空则获取用户信息，获取用户信息失败，跳转到登录页
            if (store.getters.token.length === 0) {
                store.dispatch('FedLogOut').then(() => {
                    next({
                        path: `/login?redirect=${to.path}`,
                    })
                    location.reload() // 为了重新实例化vue-router对象 避免bug
                })
            } else {
                const value = to.query.src || to.fullPath;
                const label = to.query.name || to.name;
                // const label = to.meta.title;
                const meta = to.meta || router.$avueRouter.meta || {};
                const i18n = to.query.i18n;
                if (meta.isTab !== false && !validatenull(value) && !validatenull(label)) {
                    store.commit('ADD_TAG', {
                        label: label,
                        value: value,
                        params: to.params,
                        query: to.query,
                        meta: (() => {
                            if (!i18n) {
                                return meta
                            }
                            return {
                                i18n: i18n
                            }
                        })(),
                        group: router.$avueRouter.group || []
                    });
                }
                // store.dispatch('GetUserInfo').then(() => {
                //    // 添加动态路由、
                //     // store.dispatch('GetRoutes').then((data)=> {
                //
                //     // }); // 获得系统的路由
                //     store.dispatch('GetPermissions'); // 获得Permissions权限 用于判断按钮的权限 其他按钮
                //
                //     store.dispatch("GetTopMenu").then(res => {
                //         let temp = mapRoutesSource(res); // 处理路由
                //         console.log(temp, 'temp')
                //         router.$avueRouter.addRoutesDynamic(temp); // 添加动态路由
                //         // 处理菜单数据
                //         let menus = mapRoutesSource(res); // 处理菜单
                //         console.log(menus, 'menus')
                //
                //         store.commit('NAV_MENU_LIST', menus);
                //         let parentId = res.length>0 ? res[0].id : 0;
                //         console.log(parentId, 'parentId')
                //         if(!parentId) store.dispatch("GetMenu", parentId); // 更新左侧菜单
                //     });
                // })
                store.dispatch('GetUserInfo').then(() => {
                    next()
                })
                next()
            }
        }
    } else {
        //判断是否需要认证，没有登录访问去登录页
        if (meta.isAuth === false) {
            next()
        } else {
            next({
                path: `/login?redirect=${to.path}`
            })
        }
    }
})


router.afterEach((to, from) => {
    NProgress.done();
    let title = store.getters.tag.label;
    let i18n = store.getters.tag.meta.i18n;
    title = router.$avueRouter.generateTitle(title, i18n)
    //根据当前的标签也获取label的值动态设置浏览器标题
    router.$avueRouter.setTitle(title);
});
