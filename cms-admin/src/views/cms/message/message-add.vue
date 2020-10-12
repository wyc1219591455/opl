<template>
    <div class="app-container">
        <basic-block title="留言新增">
            <el-form :model="model" :rules="rules" ref="ruleForm" label-width="100px">
                <el-form-item label="姓名" prop="name">
                    <el-input v-model.trim="model.name" style="width: 300px" placeholder="请输入姓名"></el-input>
                </el-form-item>
                <el-form-item label="联系方式" prop="contact">
                    <el-input v-model.trim="model.contact" style="width: 300px" placeholder="请输入联系方式"></el-input>
                </el-form-item>
                <el-form-item label="留言标题" prop="title">
                    <el-input v-model.trim="model.title" style="width: 300px" placeholder="请输入留言标题"></el-input>
                </el-form-item>
                <el-form-item label="留言内容" prop="content">
                    <el-input
                            v-model="model.content"
                            type="textarea"
                            :autosize="{ minRows: 2, maxRows: 4}"
                            placeholder="请输入留言内容" style="width: 300px">
                    </el-input>
                </el-form-item>
                <el-form-item label="留言备注" prop="remark">
                    <el-input v-model.trim="model.remark" style="width: 300px" placeholder="请输入留言备注"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
                    <el-button @click="resetForm('ruleForm')">重置</el-button>
                </el-form-item>
            </el-form>
        </basic-block>
    </div>
</template>

<script>
    import { add } from '@/api/cms/message' // 接口
    import {isMobile} from '@/util/validate'
    export default {
        name: 'lang-add',
        data() {
            const validPhone = (rule, value, callback) => {
                if (!isMobile(value)) {
                    callback(new Error('请输入正确的11位手机号码'))
                } else {
                    callback()
                }
            };
            return {
                model: {
                    name: '',
                    contact: '',
                    title: '',
                    content: '',
                    remark: '',
                },
                rules: {
                    name: [
                        { required: true, message: '请输入姓名', trigger: 'blur' }
                    ],
                    contact: [
                        { required: true, trigger: 'blur', validator: validPhone }
                    ],
                    title: [
                        { required: true, message: '请输入留言标题', trigger: 'blur' }
                    ],
                    content: [
                        { required: true, message: '请输入留言内容', trigger: 'blur' }
                    ],
                    remark: [
                        { required: true, message: '请输入留言备注', trigger: 'blur' }
                    ],
                },
            }
        },
        created(){

        },
        methods: {
            // 重置
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            // 保存新增
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        add({
                            resources: this.model
                        }).then(() => {
                            this.$message({
                                message: '新增成功',
                                type: 'success'
                            });
                            this.closeCurrentTag();
                            this.$router.push('/message')
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>

</style>
