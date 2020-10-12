<template>
    <el-form :model="form" :rules="rules" ref="ruleForm" label-width="100px">
        <el-form-item label="图纸格式名称" prop="name">
            <el-input v-model="form.name" placeholder="请输入图纸格式名称" style="width: 300px"></el-input>
        </el-form-item>
         <el-form-item label="图纸格式" prop="name">
            <el-input v-model="form.type" placeholder="请输入图纸格式" style="width: 300px"></el-input>
        </el-form-item>
        <el-form-item label="图纸格式描述" prop="desc">
            <el-input
                type="textarea"
                :rows="3"
                style="width: 300px"
                placeholder="请输入图纸格式描述"
                v-model="form.desc">
            </el-input>
        </el-form-item>
        <el-form-item label="状态" prop="active">
            <el-radio v-model="form.active" :label='true'>激活</el-radio>
            <el-radio v-model="form.active" :label='false'>冻结</el-radio>
        </el-form-item>
        <el-form-item v-if="type != 'edit'">
            <el-button type="primary" @click="submitForm('ruleForm')">创建</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
        </el-form>
</template>

<script>
import { paperType } from '@/api/cms/content';
export default {
    props: {
        activeData: {
            type: Object,
            default: () => {
                return {};
            }
        },
        type: {
            type: String,
            default: '',
        },
    },
    watch: {
        activeData(val) {
            if (this.type === 'edit') {
                this.form = val;
            }
        }
    },
    data() {
        return {
            rules: {
                name: [
                    { required: true, message: '请输入图纸格式名称', trigger: 'blur' },
                ],
                type: [
                    { required: true, message: '请选择图纸格式类型', trigger: 'change' }
                ],
                active: [
                    { required: true, message: '请选择状态', trigger: 'change' }
                ]
            },
            form: {
                name:'',
                type: '',
                desc: '',
                active: true
            },
        }
    },
    created() {
        if (this.type === 'edit') {
            this.form = this.activeData;
        }
    },
    methods: {
        submitForm(formName) {
            let message = this.type === 'edit' ? '保存成功' : '新增成功';
            let methodType = this.type === 'edit' ? 'put' : 'post';
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    paperType(this.form, methodType).then(()=>{
                        this.$message({
                            message,
                            type: 'success'
                        });
                        if (this.type === 'edit') {
                            this.$emit('editSuccess');
                        } else {
                            this.$router.push({
                                path: '/paperType'
                            })
                        }
                    });
                } else {
                    return false;
                }
            });
        },
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
    }

}
</script>

<style>

</style>