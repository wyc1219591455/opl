<template>
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="120px">
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="基本信息" name="base">
                <el-form-item label="自定义页面名称" prop="title">
                    <el-input v-model="form.title" style="width: 450px" placeholder="自定义页面名称" />
                </el-form-item>
                 <el-form-item label="发布人" prop="publisher">
                    <el-input v-model="form.publisher" style="width: 450px" placeholder="发布人" />
                </el-form-item>
                <el-form-item label="简介" prop="summary">
                    <el-input
                        type="textarea"
                        v-model="form.summary"
                        :rows="2"
                        style="width: 450px"
                        placeholder="简介"
                    />
                </el-form-item>
                <el-form-item label="缩略图" prop="thumbnail">
                    <upload
                        :action="actionUrl"
                        avatar-type="avatar"
                        :image-url="form.thumbnail"
                        :limit="1"
                        @input="uploadThumbnail"
                    ></upload>
                </el-form-item>
                <el-form-item label="排序" prop="sort">
                    <el-input-number
                        v-model.number="form.sort"
                        :min="0"
                        :max="999"
                        controls-position="right"
                        style="width: 178px;"
                    />
                </el-form-item>
                <!-- <el-form-item label="布局" prop="layout">
                    <el-select
                        v-model="form.layout"
                        placeholder="请选择布局"
                        style="width: 450px"
                    >
                        <el-option
                            v-for="item in allLanguage"
                            :disabled="!item.active"
                            :key="item.language"
                            :label="item.name"
                            :value="item.language"
                        ></el-option>
                    </el-select>
                </el-form-item> -->
                <el-form-item label="语言" prop="language">
                    <el-select
                        v-model="form.language"
                        :disabled="queryData.flag"
                        placeholder="请选择语言"
                        style="width: 450px"
                        
                    >
                        <el-option
                            v-for="item in allLanguage"
                            :disabled="!item.active"
                            :key="item.language"
                            :label="item.name"
                            :value="item.language"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="SEO关键字" prop="metaKeywords">
                    <el-input v-model="form.metaKeywords" style="width: 450px" placeholder="SEO关键字" />
                </el-form-item>
                <el-form-item label="SEO描述" prop="metaDescription">
                    <el-input
                        type="textarea"
                        v-model="form.metaDescription"
                        :rows="2"
                        style="width: 450px"
                        placeholder="SEO描述"
                    />
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-radio v-model="form.status" :label='true'>激活</el-radio>
                    <el-radio v-model="form.status" :label='false'>冻结</el-radio>
                </el-form-item>
            </el-tab-pane>
            <el-tab-pane label="自定义详情" name="content">
                <el-col :lg="{span: 24}" :md="{span:24}">
                    <el-form-item label="自定义详情" prop="content" label-width="100px">
                        <vue-ckeditor
                            v-if="refleshEditor"
                            v-model="form.content"
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
        <el-form-item v-if="btnFlag && type != 'edit'">
            <el-button type="primary" @click="submitForm('form')">创建</el-button>
            <el-button @click="resetForm('form')">重置</el-button>
        </el-form-item>
</el-form>
</template>
<script>
import { baseUrl } from "@/config/env";
import Upload from "@/components/jst-components/avatar-upload";
import VueCkeditor from "vue-ckeditor2";
import editorMixins from "@/mixins/editorMixins";
import { mapGetters } from 'vuex';
import { query } from '@/api/cms/selfDefined';
export default {
    components: {
        Upload,
        VueCkeditor,
    },
    mixins: [editorMixins],
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
    data() {
        return {
            avatar: "avatar",
            actionUrl: baseUrl + "api/users/updateAvatar", // 头像上传地址
            activeName: "base",
            btnFlag: false,
            form: {
                title: '',
                publisher: '',
                sort: 1,
                summary: "",
                status: true,
                layout: '',
                thumbnail: "",
                content: "",
                metaKeywords: '',
                metaDescription: '',
                language: '',
            },
            rules: {
                title: [
                    { required: true, message: "请输入自定义页面名称", trigger: "blur" }
                ],
                desc: [
                    { required: true, message: "请输入简介", trigger: "blur" }
                ],
                publisher: [
                    { required: true, message: "请输入发布人", trigger: "blur" }
                ],
                language: [
                    { required: true, message: "请选择语言", trigger: "change" }
                ],
                metaKeywords: [
                    { required: true, message: "请输入SEO关键字", trigger: "blur" }
                ],
                metaDescription: [
                    { required: true, message: "请输入SEO描述", trigger: "blur" }
                ],
                status: [
                    { required: true, message: "请选择状态", trigger: "change" }
                ],
                thumbnail: [
                    { required: true, message: "请上传缩略图", trigger: "blur" }
                ]
            }
        };
    },
    computed: {
        ...mapGetters([
            'allLanguage'
        ])
    },
    created() {
        this.queryData = this.$route.query.data ? JSON.parse(this.$route.query.data) : {};
        if (this.type === 'edit') {
            this.queryData.flag = true;
            this.form = this.activeData;
            this.form.thumbnail = baseUrl + 'avatar' + this.form.thumbnail.split("avatar")[1];
        } else if (this.queryData && this.queryData.flag) {
            this.form.language = this.queryData.language;
            this.form.articleCategoryId = this.queryData.articleCategoryId;
        }
        this.$store.dispatch("getLanguage");
    },
    watch: {
        activeData(val) {
            if (this.type === 'edit') {
                this.form = val;
            }
        }
    },
    methods: {
        uploadThumbnail(value) {
            this.form.thumbnail = baseUrl + "avatar" + value.avatarPath.split("avatar")[1];
        },
        handleClick(tab) {
            this.btnFlag = tab.name === 'content';
        },
        submitForm(form) {
            let message = this.type === 'edit' ? '保存成功' : '新增成功';
            let methodType = this.type === 'edit' ? 'put' : 'post';
            if (this.queryData.flag) {
                this.form.slug = this.queryData.slug;
            }
            this.$refs[form].validate((valid) => {
                if (valid) {
                    query(this.form, methodType).then(()=>{
                        this.$message({
                            message,
                            type: 'success'
                        });
                        if (this.type === 'edit') {
                            this.$emit('editSuccess');
                        } else {
                            this.$router.push({
                                path: '/selfDefinedList'
                            })
                        }
                    });
                } else {
                    this.$message({
                        message: '请完善必填信息！',
                        type: 'warning'
                    });
                    return false;
                }
            });
        },
        resetForm(form) {
            this.$refs[form].resetFields();
        }
    }
};
</script>
<style>
</style>