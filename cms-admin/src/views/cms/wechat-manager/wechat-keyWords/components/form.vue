<template>
    <el-form ref="ruleForm" :model="form" :rules="rules" size="small" label-width="120px">
        <el-form-item label="关键字名称" prop="keyword">
            <el-input v-model="form.keyword" style="max-width: 450px" placeholder="请输入关键字名称" />
        </el-form-item>
        <el-form-item label="回复类型" prop="type">
            <el-radio v-model="form.type" :label="1">收到消息回复</el-radio>
            <el-radio v-model="form.type" :label="2">关键词回复</el-radio>
            <el-radio v-model="form.type" :label="3">被关注回复</el-radio>
        </el-form-item>
        <el-form-item label="回复内容" prop="content">
            <el-input type="textarea" :rows="4" placeholder="请输入回复内容" style="max-width: 450px" v-model="form.content">
        </el-input>
        </el-form-item>
        <el-form-item v-if="type != 'edit'">
            <el-button type="primary" @click="submitForm('ruleForm')">创建</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import api from "@/api/cms/wechat";
export default {
    name: "form-wrap",
    props: {
        activeData: {
            type: Object,
            default: () => {
                return {};
            },
        },
        type: {
            type: String,
            default: "",
        },
    },
    watch: {
        activeData(val) {
            if (this.type === "edit") {
                this.form = val;
            }
        },
    },
    data() {
        return {
            menus: [],
            activeName: "base",
            btnFlag: false,
            form: {
                keyword: '',
                content: '',
                type: 1
            },
            queryData: {},
            rules: {
                keyword: [
                    {
                        required: true,
                        message: "请输入关键字名称",
                        trigger: "blur",
                    },
                ],
                content: [
                    {
                        required: true,
                        message: "请输入回复内容",
                        trigger: "blur",
                    },
                ],
                type: [
                    {
                        required: true,
                        message: "请选择类型",
                        trigger: "change",
                    },
                ],
            }
        };
    },
    created() {
        if (this.type === "edit") {
            this.form = this.activeData;
        }
    },
    methods: {
        // 重置
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
        // 保存新增
        submitForm(formName) {
            let message = this.type === "edit" ? "保存成功" : "新增成功";
            let methodType = this.type === "edit" ? "put" : "post";
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    api.queryWeChatKeyWords(this.form, methodType).then(() => {
                        this.$message({
                            message,
                            type: "success",
                        });
                        if (this.type === "edit") {
                            this.$emit("editSuccess");
                        } else {
                            this.$router.push({
                                path: "/keyWordsList",
                            });
                        }
                    });
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        }
    },
};
</script>

<style scoped>
</style>