<template>
    <div>
        <basic-block title="修改密码">
            <div class="change-password">
                <div class="change-password-input">
                    <el-form :model="form" status-icon :rules="rules2" ref="form" label-width="100px" class="demo-ruleForm">
                        <el-form-item label="原密码" prop="oldPass">
                            <el-input type="password" v-model="form.oldPass" auto-complete="off" placeholder="请输入现在使用的密码"></el-input>
                        </el-form-item>
                        <el-form-item label="新密码" prop="newPass">
                            <el-input type="password" v-model="form.newPass" auto-complete="off" placeholder="请输入新密码，长度为6-20个字符"></el-input>
                        </el-form-item>
                        <el-form-item label="确认密码" prop="confirmPass">
                            <el-input type="password" v-model="form.confirmPass" auto-complete="off" placeholder="请再次输入新密码"></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-button type="primary" :loading="loading" @click="submitForm('form')">保存</el-button>
                            <el-button @click="goBack()">取消</el-button>
                        </el-form-item>
                    </el-form>
                </div>
            </div>
        </basic-block>
    </div>
</template>

<script>
    import store from '@/store'
    import { modifyPassword } from '@/api/user'
    export default {
        name: "password",
        data() {
            var validatePass1 = (rule, value, callback) => {
                setTimeout(() => {
                    if (value === '') {
                        callback(new Error('请输入密码'));
                    } else {
                        if (this.form.oldPass !== '') {
                            this.$refs.form.validateField('oldPass');
                        }
                        callback();
                    }
                }, 1000);
            };
            var validatePass = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请输入密码'));
                } else {
                    if (this.form.confirmPass !== '') {
                        this.$refs.form.validateField('confirmPass');
                    }
                    callback();
                }
            };
            var validatePass2 = (rule, value, callback) => {
                if (value === '') {
                    callback(new Error('请再次输入密码'));
                } else if (value !== this.form.newPass) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };
            return {
                loading: false,
                form: {
                    oldPass: '',
                    newPass: '',
                    confirmPass: '' // 确认密码
                },
                rules2: {
                    oldPass: [
                        {required: true, validator: validatePass1, trigger: 'blur'}
                    ],
                    newPass: [
                        {required: true, validator: validatePass, trigger: 'blur'},
                        { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
                    ],
                    confirmPass: [
                        {required: true, validator: validatePass2, trigger: 'blur'}
                    ],
                }
            };
        },
        methods: {
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        console.log('submit!');
                        this.loading = true;
                        modifyPassword(this.form).then(() => {
                            this.$message({
                                message: '密码修改成功，请重新登录',
                                type: 'success',
                                duration: 1500
                            });
                            setTimeout(() => {
                                store.dispatch('LogOut').then(() => {
                                    location.reload() // 为了重新实例化vue-router对象 避免bug
                                })
                            }, 1500);
                        }).catch(err => {
                            this.loading = false;
                            console.log(err.response.data.message)
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
            goBack() {
                this.closeCurrentTag(); // 关闭当前页面
            }
        }
    }
</script>

<style lang="scss">
    .change-password{
        background-color: #fff;
        font-size: 14px;
        .change-password-title{
            padding-bottom: 20px;
            border-bottom: 1px solid #E7E7E7;
            color: #000;
            font-weight: 600;
        }
    }
    .change-password-input{
        width: 50%;
        padding: 16px 0;
        .el-form-item__label{
            font-size: 12px;
        }
        input::-webkit-input-placeholder{
            color: #666;
            font-size: 12px;
        }
    }
</style>
