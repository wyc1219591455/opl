import request from '@/router/request';
import { menuBottons, menuSource, navMenuList, systemRoutes }  from "../mock/routesData";
import { encrypt } from '@/util/rsaEncrypt'


/**
 * 获取验证码
 */
export function getCode() {
    return request({
        url: 'auth/code',
        method: 'get',
    })
}


// 登录
export const loginByUsername = (username, password, code, uuid) => {

    // return new Promise((resolve, reject) => {
    //     let user = {
    //         "id": 1,
    //         "username": "admin",
    //         "email": "admin@justech.com",
    //         "nick": "管理员",
    //         "password": "e10adc3949ba59abbe56e057f20f883e",
    //         "avatar": null,
    //         "role": "super",
    //         "openid": null,
    //         "sign": null,
    //         "phone": null,
    //         "sex": 1,
    //         "address": null,
    //         "active": true,
    //         "delete": false,
    //         "createTime": "2020-04-07 17:24:45",
    //         "updateTime": "2020-04-07 17:24:49",
    //         "lastLogin": "2020-04-07 17:24:51"
    //     };
    //     let token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1ZWR0c3VudGllbiIsImlkIjoxLCJlbWFpbCI6ImFkbWluQGp1c3RlY2guY29tIiwidXNlcm5hbWUiOiJhZG1pbiIsImFjdGl2ZSI6dHJ1ZSwiZGVsZXRlIjpmYWxzZSwiaWF0IjoxNTkwNjU0MTY2LCJleHAiOjE1OTEyNTg5NjZ9.Kfjl7nCAbpfrZG91nioNLxLk2aRI1z6QRFUzGlBgarw";
    //
    //     localStorage.setItem("user", JSON.stringify(user));
    //     localStorage.setItem("token", token);
    //
    //     resolve({
    //         data: {
    //             code: 1,
    //             data: {
    //                 user: user,
    //                 token: token
    //             }
    //         }
    //     })
    // });

    // 正式环境请使用下面代码
    return new Promise((resolve, reject) => {
        return request({
            url: 'auth/login',
            method: 'post',
            data: {
                username,
                password,
                code,
                uuid
            }
        }).then(res=>{
            resolve(res)
        }).catch((err) => {
            reject(err);
        });
    })
};

export const getPermissions = () => {
    // request({
    //     url: '/user/permissions',   
    //     method: 'get'
    // });
    return new Promise((resolve, reject)=>{
        let data = {
            "code": 200,
            "success": true,
            "msg": "操作成功",
            "data": [
                "user-list", "user-edit", "user-add"
            ]
        };
        resolve({data})
    })
}

export const getUserInfo = () => request({
    url: 'auth/info',
    method: 'get'
});

export const refeshToken = () => request({
    url: 'user/refesh',
    method: 'post'
})


export const getTopMenu = () => {
    return new Promise((resolve, reject)=>{
        // let data = {
        //     "code": 200,
        //     "success": true,
        //     "msg": "操作成功",
        //     "data": navMenuList
        // }
        return request({
            url: 'api/menus/build',
            method: 'get',
        }).then(res=>{
            // console.log(res, '获取菜单')
            resolve(res)
        });
        // resolve({data});
    })
};

export const sendLogs = (list) => request({
    url: 'user/logout',
    method: 'post',
    data: list
})

export const logout = () => {
    return new Promise((resolve, reject)=>{
        let data = true;
        resolve({ data })
    })

}

export const getRoutes = ()=>{
    return request({
        url: 'api/menus/build',
        method: 'get',
    });

    // return new Promise((resolve, reject)=>{
    //     let data = {
    //         "code": 200,
    //         "success": true,
    //         "msg": "操作成功",
    //         "data": systemRoutes
    //     }
    //     resolve({ data })
    // })
};

/**
 * 获取用户列表 分页
 * @param data
 */
export function getUserPage(data) {
    return request({
        url: 'admin/user/list',
        method: 'post',
        data: data,
    });
}

/**
 * 根据 id 取用户
 * @param id
 */
export function getUserById(id) {
    return request({
        url: 'admin/user/get?id=' + id,
        method: 'get',
    });
}

/**
 * 新建用户
 * @param data
 */
export function insertUser(data) {
    return request({
        url: 'admin/user/add',
        method: 'post',
        data: data
    });
}

/**
 * 更新用户
 * @param data
 */
export function updateUser(data) {
    return request({
        url: 'admin/user/edit',
        method: 'post',
        data: data
    });
}

/**
 * 删除用户
 * @param id
 */
export function deleteUser(id) {
    return request({
        url: 'admin/user/delete',
        method: 'post',
        data: id
    });
}

/**
 * 管理员重置密码
 * @param data
 */
export function resetPassword(data) {
    return request({
        url: 'admin/user/resetPassword',
        method: 'post',
        data: data
    });
}

/**
 * 修改密码
 */
export function modifyPassword(user) {
    const data = {
        oldPass: encrypt(user.oldPass),
        newPass: encrypt(user.newPass)
    };
    return request({
        url: 'api/users/updatePass',
        method: 'post',
        data
    })
}

/**
 * 获取用户个人信息
 */
export function userDetail() {
    return request({
        url: 'auth/info',
        method: 'get'
    })
}

/**
 * 修改用户信息
 */
export function updateUserInfo(data) {
    return request({
        url: 'api/users/updateCustInfo',
        method: 'put',
        data:data
    })
}
/**
 * 获取管理员信息
 */
export function getUsers() {
    return request({
        url: 'api/users',
        method: 'get'
    })
}

/**
 * 退出登录
 */
export function apiLogout() {
    return request({
        url: 'auth/logout',
        method: 'delete'
    })
}



