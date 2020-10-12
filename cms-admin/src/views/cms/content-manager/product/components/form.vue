<template>
    <el-form :model="model" :rules="rules" ref="ruleForm" label-width="100px">
        <el-form-item label="分类名称" prop="name">
            <el-input v-model.trim="model.name" style="width: 300px" placeholder="请输入分类名称"></el-input>
        </el-form-item>
        <el-form-item label="分类图标" prop="icon">
            <el-popover
                    placement="bottom-start"
                    width="300"
                    trigger="click"
                    @show="$refs['iconSelect'].reset()"
            >
                <IconChoose ref="iconSelect" @selected="selected" />
                <el-input
                        slot="reference"
                        v-model="model.icon"
                        style="width: 300px;"
                        placeholder="点击选择图标"

                >
                <i v-if="model.icon" slot="prefix" class="iconfont" :class="model.icon"></i>
                <i v-else slot="prefix" class="el-icon-search el-input__icon" />
                </el-input>
            </el-popover>
        </el-form-item>
        <el-form-item label="分类描述" prop="desc">
            <el-input
                    v-model="model.desc"
                    type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4}"
                    style="width: 300px" placeholder="请输入分类描述"></el-input>
        </el-form-item>
        <el-form-item label="SEO描述" prop="metaDescription">
            <el-input
                    v-model="model.metaDescription"
                    type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4}"
                    style="width: 300px" placeholder="请输入SEO描述"></el-input>
        </el-form-item>
        <el-form-item label="SEO关键字" prop="metaKeywords">
            <el-input v-model.trim="model.metaKeywords" style="width: 300px" placeholder="请输入SEO关键字"></el-input>
        </el-form-item>
        <el-form-item label="所属分类" prop="parentId">
            <treeselect
                    v-model="model.parentId"
                    :options="tableData"
                    noOptionsText='暂无所属分类'
                    noResultsText="暂无结果"
                    style="width: 300px"
                    placeholder="选择所属分类"
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
        <el-form-item label="分类banner图" prop="banner">
            <upload
                    :action="actionUrl"
                    :avatar-type="avatar"
                    :image-url="model.banner"
                    v-model="model.banner"
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
        <el-form-item v-if="type != 'edit'">
            <el-button type="primary" @click="submitForm('ruleForm')">创建</el-button>
            <el-button @click="resetForm('ruleForm')">重置</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
    import { baseUrl } from '@/config/env';
    import api from '@/api/cms/content';
    import Upload from '@/components/jst-components/avatar-upload'
    import IconChoose from '@/components/IconChoose';
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
        components:{
            Upload, Treeselect, IconChoose
        },
        watch: {
            activeData(val) {
                if (this.type === 'edit') {
                    this.model = val;
                }
            },
        },
        data() {
            return {
                model: {
                    name: '',
                    language: '',
                    desc: '',
                    metaDescription: '',
                    metaKeywords: '',
                    sort: 1,
                    active: true,
                    banner: '',
                    parentId: null,
                    slug: '',
                    icon: ''
                },
                queryData:{},
                rules: {
                    name: [
                        { required: true, message: '请输入分类名称', trigger: 'blur' }
                    ],
                    desc: [
                        { required: true, message: '请输入分类描述', trigger: 'blur' }
                    ],
                    metaDescription: [
                        { required: true, message: '请输入SEO描述内容', trigger: 'blur' }
                    ],
                    metaKeywords: [
                        { required: true, message: '请输入SEO关键字', trigger: 'blur' }
                    ],
                    language: [
                        { required: true, message: '请选择语言', trigger: 'change' }
                    ],
                    active: [
                        { required: true, message: '请选择状态', trigger: 'blur' }
                    ],
                    banner: [
                        { required: true, message: '请上传图片', trigger: 'blur' }
                    ],
                    icon: [
                        { required: true, message: '请选择icon图标', trigger: 'blur' }
                    ],
                },
                avatar: 'avatar',
                actionUrl: baseUrl + 'api/users/updateAvatar',// 头像上传地址
                avatarUrl:{},
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
                this.model.banner = baseUrl + 'avatar' + this.model.banner.split("avatar")[1];
            } else if (this.queryData && this.queryData.flag) {
                this.model.language = this.queryData.language;
                this.model.parentId = this.queryData.parentId;
            }
            this.$store.dispatch("getLanguage");
            this.getTreeClass();
        },
        methods: {
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
            // 获取树形菜单
            getTreeClass() {
                api.queryProductList('', 'get').then(res => {
                    this.tableData.push({ id: 0, name: '顶级类目', active: true, children: res.content });
                });
            },
            // 选中图标
            selected(name) {
                this.model.icon = name;
                this.$refs.ruleForm.validateField('icon');
            },
            // 头像上传
            avatarDetail(value) {
                this.avatarUrl = value;
                this.model.banner = baseUrl + 'avatar' + this.avatarUrl.avatarPath.split("avatar")[1];
                this.$refs.ruleForm.validateField('banner');
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
                        api.queryProductList(this.model, methodType).then(()=>{
                            this.$message({
                                message,
                                type: 'success'
                            });
                            if (this.type === 'edit') {
                                this.$emit('editSuccess');
                            } else {
                                this.$router.push({
                                    path: '/productList'
                                })
                            }
                        });
                    } else {
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