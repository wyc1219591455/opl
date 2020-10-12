<template>
    <el-form :model="model" :rules="rules" ref="ruleForm" label-width="100px">
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="匹配基本信息" name="base">
                <el-form-item label="匹配产品名称" prop="name">
                    <el-input v-model.trim="model.name" style="width: 300px" placeholder="请输入匹配产品名称"></el-input>
                </el-form-item>
                <el-form-item label="匹配分类简介" prop="summary">
                    <el-input
                            v-model="model.summary"
                            type="textarea"
                            :autosize="{ minRows: 2, maxRows: 4}"
                            style="width: 300px" placeholder="请输入匹配产品简介"></el-input>   
                </el-form-item>
                <el-form-item label="匹配分类">
                    <treeselect
                        v-model="model.matchProductCategoryId"
                        :options="tableData"
                        noOptionsText='暂无匹配分类'
                        noResultsText="暂无结果"
                        style="width: 300px"
                        placeholder="请选择匹配分类"
                        :normalizer="normalizer"
                    />
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                    <el-input-number v-model.trim="model.sort" :max="999" placeholder="请输入"></el-input-number>
                </el-form-item>
                <el-form-item label="语言" prop="language">
                    <el-select v-model="model.language" :disabled='queryData.flag' placeholder="请选择语言">
                        <el-option
                                v-for="item in allLanguage"
                                :disabled="!item.active"
                                :key="item.language"
                                :label="item.name"
                                :value="item.language">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="产品缩略图" prop="thumbnail">
                    <upload
                            :action="actionUrl"
                            :avatar-type="avatar"
                            :image-url="model.thumbnail"
                            v-model="model.thumbnail"
                            :limit="1"
                            @input="avatarDetail">
                    </upload>
                </el-form-item>
                <el-form-item label="状态" prop="active">
                    <el-radio-group v-model="model.active">
                        <el-radio :label="true">激活</el-radio>
                        <el-radio :label="false">冻结</el-radio>
                    </el-radio-group>
                </el-form-item>
            </el-tab-pane>
            <el-tab-pane label="匹配产品详情" name="detail">
                <el-col :lg="{span: 24}" :md="{span:24}">
                    <el-form-item label="匹配产品详情" prop="content" label-width="100px">
                        <vue-ckeditor
                                v-if="refleshEditor"
                                v-model="model.content"
                                :config="config"
                                @blur="onBlur($event)"
                                @focus="onFocus($event)"
                                @contentDom="onContentDom($event)"
                                @dialogDefinition="onDialogDefinition($event)"
                                @fileUploadRequest="onFileUploadRequest($event)"
                                @fileUploadResponse="onFileUploadResponse($event)"
                        ></vue-ckeditor>
                    </el-form-item>
                </el-col>
            </el-tab-pane>
        </el-tabs>
        <el-form-item v-if="type != 'edit' && btnFlag">
            <el-button type="primary" @click="submitForm('ruleForm')">创建</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import { baseUrl } from '@/config/env';
    import Upload from '@/components/jst-components/avatar-upload'
    import api from '@/api/cms/product-choose';
    import VueCkeditor from "vue-ckeditor2";
    import editorMixins from "@/mixins/editorMixins";
    import Treeselect from '@riophae/vue-treeselect'
    import '@riophae/vue-treeselect/dist/vue-treeselect.css'
    import { mapGetters } from 'vuex';
    export default {
        name: "form-wrap",
        props: {
            activeData: {
                type: Object,
                default: () => {
                    return {};
                }
            },
            type: {
                type: String,
                default: ''
            },
        },
        mixins: [editorMixins],
        components:{
            VueCkeditor, Treeselect,Upload
        },
        watch: {
            activeData(val) {
                if (this.type === 'edit') {
                    this.model = val;
                    this.model.matchProductCategoryId = val.matchProductCategory ? val.matchProductCategory.id : '';
                }
            },
        },
        data() {
            return {
                activeName: "base",
                avatar: 'avatar',
                btnFlag: false,
                model: {
                    name: '',
                    language: '',
                    summary: '',
                    sort: 1,
                    active: true,
                    parentId: null,
                    slug: '',
                    thumbnail: '',
                    matchProductCategoryId: null,// 匹配分类id
                    content: ""
                },
                queryData:{},
                rules: {
                    name: [
                        { required: true, message: '请输入匹配产品名称', trigger: 'blur' }
                    ],
                    summary: [
                        { required: true, message: '请输入匹配产品简介', trigger: 'blur' }
                    ],
                    language: [
                        { required: true, message: '请选择语言', trigger: 'change' }
                    ],
                    active: [
                        { required: true, message: '请选择状态', trigger: 'blur' }
                    ],
                    thumbnail: [
                        { required: true, message: '请添加缩略图', trigger: 'blur' }
                    ],
                    content: [
                        { required: true, message: '请输入详情信息', trigger: 'blur' }
                    ],

                },
                avatarUrl:{},
                actionUrl: baseUrl + 'api/users/updateAvatar',// 头像上传地址
                tableData:[],
            }
        },
        computed: {
            ...mapGetters([
                'allLanguage'
            ]),
        },
        created(){
            this.queryData = this.$route.query.data ? JSON.parse(this.$route.query.data) : {};
            if (this.type === 'edit') {
                this.model = this.activeData;
                this.queryData.flag = true;
                this.model.matchProductCategoryId = this.activeData.matchProductCategory ? this.activeData.matchProductCategory.id : '';
                this.model.thumbnail = baseUrl + 'avatar' + this.model.thumbnail.split("avatar")[1];
            } else if (this.queryData && this.queryData.flag) {
                this.model.language = this.queryData.language;
                this.model.parentId = this.queryData.parentId;
            }
            this.$store.dispatch("getLanguage");
            this.getTreeClass();
        },
        methods: {
            // 获取树形菜单
            getTreeClass() {
                api.queryMatchProductCategoryList('', 'get').then(res => {
                    this.tableData = res.content;
                });
            },
            // 设置树形菜单显示字段
            normalizer(node){
                //去掉children=[]的children属性
                if(node.children && !node.children.length){
                    delete node.children;
                }
                return {
                    id: node.id,
                    //将name转换成必填的label键
                    label:node.name,
                    children:node.children,
                    isDisabled: !node.active
                }
            },
            // 头像上传
            avatarDetail(value) {
                this.avatarUrl = value;
                this.model.thumbnail = baseUrl + 'avatar' + this.avatarUrl.avatarPath.split("avatar")[1];
                this.$refs.ruleForm.validateField('thumbnail');
            },
            handleClick(tab) {
                this.btnFlag = tab.name === 'detail';
            },
            // 重置
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            // 保存新增
            submitForm(formName) {
                let message = this.type === 'edit' ? '保存成功' : '新增成功';
                let methodType = this.type === 'edit' ? 'put' : 'post';
                if (this.queryData.flag) {
                    this.model.slug = this.queryData.slug;
                }
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        api.queryMatchProductList(this.model, methodType).then(()=>{
                            this.$message({
                                message,
                                type: 'success'
                            });
                            if (this.type === 'edit') {
                                this.$emit('editSuccess');
                            } else {
                                this.$router.push({
                                    path: '/matchProductList'
                                })
                            }
                        });
                    } else {
                        this.$message({
                            message: '请完善必填信息！',
                            type: 'warning'
                        });
                        console.log('error submit!!');
                        return false;
                    }
                });
            },
        }
    }
</script>

<style scoped>

</style>