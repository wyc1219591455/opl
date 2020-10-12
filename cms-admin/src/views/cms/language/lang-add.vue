<template>
    <div class="app-container">
        <basic-block title="语言新增">
            <el-form :model="model" :rules="rules" ref="ruleForm" label-width="100px">
                <el-form-item label="语言名称" prop="name">
                    <el-input v-model.trim="model.name" style="width: 300px" placeholder="请输入语言名称"></el-input>
                </el-form-item>
                <el-form-item label="语言标识" prop="language">
                    <el-input v-model.trim="model.language" style="width: 300px" placeholder="请输入语言标识，如：zh-cn"></el-input>
                </el-form-item>
                <el-form-item label="语言简介" prop="desc">
                    <el-input
                            v-model.trim="model.desc"
                            type="textarea"
                            :autosize="{ minRows: 2, maxRows: 4}"
                            style="width: 300px" placeholder="请输入语言简介"></el-input>
                </el-form-item>
                <el-form-item label="状态" prop="active">
                    <el-radio-group v-model="model.active">
                        <el-radio :label="true">激活</el-radio>
                        <el-radio :label="false">冻结</el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                    <el-input-number v-model.trim="model.sort" :max="999" placeholder="请输入"></el-input-number>
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
    import { addLanguage } from '@/api/cms/lang' // 接口
    export default {
        name: 'lang-add',
        data() {
            return {
                model: {
                    name: '',
                    language: '',
                    desc: '',
                    sort: 1,
                    active: true,
                },
                rules: {
                    name: [
                        { required: true, message: '请输入语言', trigger: 'blur' }
                    ],
                    language: [
                        { required: true, message: '请输入语言标识', trigger: 'blur' }
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
                        addLanguage(_.cloneDeep(this.model)).then(() => {
                            this.$message({
                                message: '新增成功',
                                type: 'success'
                            });
                            this.closeCurrentTag();
                            this.$router.push('/language')
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
