<template>
    <el-form class="login-form"
             :rules="loginRules"
             ref="loginForm"
             :model="loginForm"
             status-icon
             label-width="0">
        <el-form-item prop="username">
            <el-input size="small"
                      @keyup.enter.native="handleLogin"
                      v-model="loginForm.username"
                      auto-complete="off"
                      :placeholder="$t('login.username')">
            </el-input>
        </el-form-item>
        <el-form-item prop="password">
            <el-input size="small"
                      @keyup.enter.native="handleLogin"
                      :type="passwordType"
                      v-model="loginForm.password"
                      auto-complete="off"
                      :placeholder="$t('login.password')">
                <i class="el-icon-view el-input__icon"
                   slot="suffix"
                   @click="showPassword"></i>
            </el-input>
        </el-form-item>
        <el-form-item prop="code">
            <el-row>
                <el-col :span="15" style="margin: 0">
                    <el-input
                            v-model="loginForm.code"
                            size="small"
                            @keyup.enter.native="handleLogin"
                            auto-complete="off"
                            :placeholder="$t('login.code')">
                    </el-input>
                </el-col>
                <el-col :span="9" style="margin: 0">
                    <div class="login-codes">
                        <img style="display: inline-block" @click="getCode()" :src="code.img" alt="">
                    </div>
                </el-col>
            </el-row>
        </el-form-item>
        <!--<div class="login-from-flex">-->
            <!--<el-checkbox v-model="checked">{{$t('login.loginAuto')}}</el-checkbox>-->
        <!--</div>-->
        <el-form-item>
            <el-button type="primary"
            :disabled="loginStatus"
            size="small"
            @click.native.prevent="handleLogin"
            class="login-submit">{{$t('login.submit')}}</el-button>
        </el-form-item>

    </el-form>
</template>

<script>
    import { mapGetters } from "vuex";
    import website from '@/config/website';
    import { mapRoutesSource } from "@/util/util";
    import { encrypt } from '@/util/rsaEncrypt'
    import { getCode } from '@/api/user'
    import Cookies from 'js-cookie'

    export default {
        name: "jstUserlogin",
        data() {
            return {
                loginStatus: false,
                checked: true,
                tenantMode: website.tenantMode,
                loginForm: {
                    username: "",
                    password: "", 
                    rememberMe: false,
                    type: "account",
                    code: '',
                },
                loginRules: {
                    username: [
                        { required: true, message: "请输入账号", trigger: "blur" }
                    ],
                    password: [
                        { required: true, message: "请输入密码", trigger: "blur" },
                        { min: 1, message: "密码长度最少为6位", trigger: "blur" }
                    ],
                    code: [
                        { required: true, message: "请输入验证码", trigger: "blur" }
                    ],
                },
                passwordType: "password",
                code: {},
                cookiePass: '',
                uId: null,
                time: 3,
            };
        },
        created() {
            this.getCode();
            // 获取用户名密码等Cookie
            // this.getCookie()
            // token 过期提示
            this.point()
        },
        mounted() {},
        computed: {
            ...mapGetters(["tagWel"])
        },
        props: [],
        methods: {
            // 获取验证码
            debounce(fn, delay) {
                // 维护一个 timer
                console.log(fn, delay)
                var timer = null;
                return function () {
                    var args = arguments; //参数集合
                    clearTimeout(timer);
                    timer = setTimeout(function () {
                        fn.apply(this, args);
                    }, delay);
                }
            },
            getCode() {
                getCode().then((res) => {
                    let data = res.data;
                    if (data.code == 200 && !data.isError) {
                        this.code = data.data;
                    }
                }).catch(() => {
                });
            },
            getCookie() {
                const username = Cookies.get('username');
                let password = Cookies.get('password');
                const rememberMe = Cookies.get('rememberMe');
                // 保存cookie里面的加密后的密码
                this.cookiePass = password === undefined ? '' : password;
                password = password === undefined ? this.loginForm.password : password;
                this.loginForm = {
                    username: username === undefined ? this.loginForm.username : username,
                    password: password,
                    rememberMe: rememberMe === undefined ? false : Boolean(rememberMe),
                    code: ''
                };
            },
            showPassword() {
                this.passwordType == ""
                    ? (this.passwordType = "password")
                    : (this.passwordType = "");
            },
            submit() {
                let that = this;
                this.$refs.loginForm.validate(valid => {
                    const user = {
                        username: that.loginForm.username,
                        password: that.loginForm.password,
                        rememberMe: that.loginForm.rememberMe,
                        code: that.loginForm.code,
                        uuid: that.code.uuid
                    };
                    if (user.password !== this.cookiePass) {
                        user.password = encrypt(user.password)
                    }
                    if (valid) {
                        this.$store.dispatch("LoginByUsername", user).then(() => {
                            const loading = this.$loading({
                                lock: true,
                                text: '登录中,请稍后。。。',
                                spinner: "el-icon-loading"
                            });
                            let redirect = this.$route.query.redirect;
                            this.$router.push({ path: redirect || this.tagWel.value });
                            loading.close();
                            // 添加动态路由、
                            this.$store.dispatch('GetRoutes').then((data)=> {
                                let temp = mapRoutesSource(data); // 处理路由
                                this.$router.$avueRouter.addRoutesDynamic(temp); // 添加动态路由
                            }); // 获得系统的路由

                            this.$store.dispatch('GetPermissions'); // 获得Permissions权限 用于判断按钮的权限 其他按钮

                            this.$store.dispatch("GetTopMenu").then(res => {
                                // 处理菜单数据
                                let menus = mapRoutesSource(res); // 处理菜单
                                this.$store.commit('NAV_MENU_LIST', menus);
                                let parentId = res.length>0 ? res[0].id : 0;
                                if(parentId) this.$store.dispatch("GetMenu", parentId); // 更新左侧菜单
                            });
                        }).catch(() => {
                            this.getCode();
                        });
                    }
                });
            },
            handleLogin() {
                this.submit();
                // this.time = 3;
                // let login = this.debounce(this.submit, 1500);   
                // login();
            },
            point() {
                const point = sessionStorage.getItem('token') !== null;
                if (point) {
                    this.$notify({
                        title: '提示',
                        message: '当前登录状态已过期，请重新登录！',
                        type: 'warning',
                        duration: 5000
                    });
                    window.sessionStorage.removeItem('token')
                }
            },
            goPassword(){
                this.$router.push({ path: "/forgetPassword" })
            },
            run() {
                var s = document.getElementById("dd");
                if(s.innerHTML == 0){
                window.location.href='regform.shtml';
                    return false;
                }
                s.innerHTML = s.innerHTML * 1 - 1;
            }
        }
    }
</script>

<style scoped>
    .login-codes {
        height: 40px;
        position: relative;
        top: 2px;
    }
    .login-codes img{
        cursor: pointer;
    }
</style>
