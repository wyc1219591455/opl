// 配置编译环境和线上环境之间的切换

let baseUrl = '';
const env = process.env
if (env.NODE_ENV == 'development') {
    // baseUrl = `http://172.16.4.68:8001/`; // 开发环境地址
    // baseUrl = `http://10.65.44.31:8001/`; // 彭鑫
    // baseUrl = `http://172.16.4.52:8001/`; // 季超
    baseUrl = `http://10.66.53.32:8001/`; //测试环境
    // baseUrl = `http://admin.tsuntien.com/tsuntien_api`; // 开    发环境地址
} else {
    baseUrl = `http://10.66.53.32:8001/`; //测试环境
}
export {
    baseUrl,
    env
}
