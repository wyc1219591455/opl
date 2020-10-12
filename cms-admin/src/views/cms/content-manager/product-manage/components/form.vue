<template>
    <el-form ref="ruleForm" :model="form" :rules="rules" size="small" label-width="120px">
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="基本信息" name="base">
                <el-form-item label="产品名称" prop="name">
                    <el-input v-model="form.name" style="max-width: 450px" placeholder="请输入产品名称" />
                </el-form-item>
                <el-form-item label="产品简介" prop="summary">
                    <el-input
                        type="textarea"
                        v-model="form.summary"
                        :rows="2"
                        style="max-width: 450px"
                        placeholder="请输入产品简介"
                    />
                </el-form-item>
                <el-form-item label="产品分类" prop="productCategoryId">
                    <!--新增模式start-->
                    <treeselect
                        v-model="form.productCategoryId"
                        :options="menus"
                        noOptionsText="暂无产品分类"
                        noResultsText="暂无结果"
                        style="max-width: 450px;"
                        placeholder="选择产品分类"
                        :normalizer="normalizer"
                    />
                </el-form-item>
                <el-form-item label="产品图纸" prop="drawingId">
                    <!--编辑模式start-->
                    <!-- <el-select
                        v-if="type === 'edit'"
                        v-model="form.drawingDto.id"
                        @change="change"
                        placeholder="请选择产品图纸"
                        style="max-width: 450px"
                    >
                        <el-option
                            v-for="item in drawingList"
                            :key="item.id"
                            :label="item.name"
                            :value="item.id"
                        ></el-option>
                    </el-select> -->
                    <!--新增模式start-->
                    <el-select
                        v-model="form.drawingId"
                        @change="change"
                        placeholder="请选择产品图纸"
                        style="max-width: 450px"
                    >
                        <el-option
                            v-for="item in drawingList"
                            :key="item.id"
                            :disabled="!item.status"
                            :label="item.name"
                            :value="item.id"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="语言" prop="language">
                    <el-select
                        v-model="form.language"
                        :disabled='queryData.flag'
                        placeholder="请选择语言"
                        style="max-width: 450px"
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
                <el-form-item label="排序" prop="sort">
                    <el-input-number v-model.trim="form.sort" :max="999" placeholder="请输入"></el-input-number>
                </el-form-item>
                <el-form-item label="产品缩略图" prop="thumbnail">
                    <upload
                        :action="actionUrl"
                        :avatar-type="avatar"
                        :image-url="form.thumbnail"
                        v-model="form.thumbnail"
                        :limit="1"
                        @input="uploadThumbnail"
                    ></upload>
                </el-form-item>
                <el-form-item label="产品轮播图">
                    <upload-list
                        :action="actionUrl"
                        @input="uploadList"
                    ></upload-list>
                </el-form-item>
                <el-form-item label="SEO描述" prop="metaDescription">
                    <el-input
                        type="textarea"
                        v-model="form.metaDescription"
                        :rows="2"
                        style="max-width: 450px"
                        placeholder="SEO描述"
                    />
                </el-form-item>
                <el-form-item label="SEO关键字" prop="metaKeywords">
                    <el-input v-model="form.metaKeywords" style="max-width: 450px" placeholder="SEO关键字" />
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-radio v-model="form.status" :label="true">激活</el-radio>
                    <el-radio v-model="form.status" :label="false">冻结</el-radio>
                </el-form-item>
            </el-tab-pane>
            <el-tab-pane label="产品属性" name="attribute">
                <el-col :lg="{span: 24}" :md="{span:24}">
                    <div v-for="(item, index) in attributeList" :key="item.id">
                        <el-form-item :label="item.name">
                            <el-input v-model.trim="item.propertyValue" style="max-width: 450px" @change="iptChnage(item, form.productPropertyList[index])" placeholder="请输入属性值" />
                        </el-form-item>
                    </div>
                </el-col>
            </el-tab-pane>
            <el-tab-pane label="产品详情" name="detail">
                <el-col :lg="{span: 24}" :md="{span:24}">
                    <el-form-item label="产品详情" prop="content" label-width="100px">
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
        <el-form-item v-if="type != 'edit' && btnFlag">
            <el-button type="primary" @click="submitForm('ruleForm')">创建</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
    </el-form>
</template>

<script>

    import { baseUrl } from '@/config/env';
    import api from '@/api/cms/content';
    import Upload from '@/components/jst-components/avatar-upload';
    import UploadList from '@/components/jst-components/upload';
    import VueCkeditor from "vue-ckeditor2";
    import editorMixins from "@/mixins/editorMixins";
    import Treeselect from '@riophae/vue-treeselect';
    import '@riophae/vue-treeselect/dist/vue-treeselect.css';
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
            Upload,
            VueCkeditor,UploadList,Treeselect
        },
        watch: {
            activeData(val) {
                if (this.type === 'edit') {
                    this.form = val;
                    this.form.productCategoryId = this.activeData.productCategory ? this.activeData.productCategory.id : null;
                    this.form.drawingId =  this.activeData.drawingDto ? this.activeData.drawingDto.id : null;
                }
            },
        },
        data() {
            return {
                typeValue: '',
                menus: [],
                activeName: "base",
                btnFlag: false,
                form: {
                    name: null,
                    summary: '',
                    productCategoryId: null, // 产品分类id
                    drawingId: "", // 图纸id
                    language: '',
                    metaKeywords: '',
                    metaDescription: '',
                    sort: 1,
                    status: true,
                    thumbnail: "",
                    content: "",
                    productPropertyList: [], // 产品属性集合
                    productCarouselList: [], //  产品轮播图集合
                },
                rules: {
                    name: [
                        { required: true, message: "请输入产品名称", trigger: "blur" }
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
                    // productCategoryId: [
                    //     { required: true, message: "请选择产品分类", trigger: "change" }
                    // ],
                    status: [
                        { required: true, message: "请选择状态", trigger: "change" }
                    ],
                    thumbnail: [
                        { required: true, message: '请上传图片', trigger: 'blur' }
                    ],
                    attributeVal: [
                        { required: true, message: '请输入属性值', trigger: 'blur' }
                    ],
                    content: [
                        { required: true, message: '请输入详情信息', trigger: 'blur' }
                    ],
                },
                avatar: 'avatar',
                imageUrl: '',
                actionUrl: baseUrl + 'api/users/updateAvatar',// 头像上传地址
                avatarUrl:{},
                drawingList: [],
                attributeList: [],
            }
        },
        created(){
            this.queryData = this.$route.query.data ? JSON.parse(this.$route.query.data) : {};
            if (this.type === 'edit') {
                this.queryData.flag = true;
                this.form = this.activeData;
                this.form.productCategoryId = this.activeData.productCategory ? this.activeData.productCategory.id : null;
                this.form.drawingId = this.activeData.drawingDto ? this.activeData.drawingDto.id : null;

            }else if (this.queryData && this.queryData.flag) {
                this.form.language = this.queryData.language;
                this.form.parentId = this.queryData.parentId;
            }
            this.$store.dispatch("getLanguage");
            this.getTreeMenus();
            this.getqueryPaperList();
            this.getAttributeList();
        },
        computed: {
            ...mapGetters([
                'allLanguage'
            ]),
        },
        methods: {
            change(val){
                console.log(this.form.drawingId,'图纸id');
                console.log(val,'hjhjhjj');
                console.log(this.form.drawingId,'图纸id=======');
            },
            iptChnage(item, value) {
                console.log(item, value);
            },
            // 获取树形菜单
            getTreeMenus(){
                api.queryProductList('get').then((res) => {
                    this.menus = res.content;
                })
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
                    label: node.name,
                    children: node.children,
                    isDisabled: !node.active
                }
            },
            // 获取图纸列表
            getqueryPaperList() {
                api.queryDrawingList('get').then((res) => {
                    this.drawingList = res;
                })
            },
            updateData() {
                this.getAttributeList();
            },
            // 获取属性列表
            getAttributeList() {
                api.queryPropertyList('get').then((res) => {
                    let resValue = res.content;
                    let activeList = this.activeData.productPropertyList;
                    // console.log(activeList, 'activeList')
                    if (JSON.stringify(this.activeData) != "{}" && activeList.length && resValue.length) {
                        for (var i = 0; i < resValue.length; i++) {
                            for(var j = 0; j < activeList.length; j++) {
                                if (resValue[i].id === activeList[j].id) {
                                    resValue[i] = activeList[j];
                                }
                            }
                        }
                    }
                    this.attributeList = resValue;
                })
            },
            // 头像上传    
            uploadThumbnail(value) {
                this.form.thumbnail = baseUrl + "avatar" + value.avatarPath.split("avatar")[1];
                this.$refs.ruleForm.validateField('thumbnail');
            },
            uploadList(value){
                this.form.productCarouselList = [];
                for (var i=0; i< value.length; i++) {
                    value[i] = baseUrl + "avatar" + value[i].split("avatar")[1];
                    let src = {
                        src: value[i]
                    };
                    this.form.productCarouselList.push(src)
                }
            },
            // 重置
            resetForm(formName) {
                this.$refs[formName].resetFields();
            },
            // 保存新增
            submitForm(formName) {
                let message = this.type === 'edit' ? '保存成功' : '新增成功';
                let methodType = this.type === 'edit' ? 'put' : 'post';
                this.form.productPropertyList = _.cloneDeep(this.attributeList);
                if (this.queryData.flag) {
                    this.form.slug = this.queryData.slug;
                }
                // if (this.type === 'edit') {
                //     this.form.productCategoryId = 
                // }
                // return;
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        api.queryPropertyCmsList(this.form, methodType).then(()=>{
                            this.$message({
                                message,
                                type: 'success'
                            });
                            if (this.type === 'edit') {
                                this.$emit('editSuccess');
                            } else {
                                this.$router.push({
                                    path: '/productManageList'
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
            handleClick(tab) {
                this.btnFlag = tab.name === 'detail';
            },
        }
    }
</script>

<style scoped>

</style>