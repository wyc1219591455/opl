<template>
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="120px">
        <el-form-item label="菜单类型" prop="type">
            <el-radio-group v-model="form.type" size="mini" style="max-width: 300px">
                <el-radio-button label="主菜单">主菜单</el-radio-button>
                <el-radio-button label="顶部菜单">顶部菜单</el-radio-button>
                <el-radio-button label="底部菜单">底部菜单</el-radio-button>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单图标" prop="icon">
            <el-popover
                placement="bottom-start"
                width="500"
                trigger="click"
                @show="$refs['iconSelect'].reset()"
            >
                <IconChoose ref="iconSelect" @selected="selected" />
                <el-input
                    slot="reference"
                    v-model="form.icon"
                    style="max-width: 450px;"
                    placeholder="点击选择图标"
                >
                    <i v-if="form.icon" slot="prefix" class="iconfont" :class="form.icon"></i>
                    <i v-else slot="prefix" class="el-icon-search el-input__icon" />
                </el-input>
            </el-popover>
        </el-form-item>
        <el-form-item  label="外链菜单" prop="iframe">
            <el-radio-group v-model="form.isFrame" size="mini">
                <el-radio-button :label=true>是</el-radio-button>
                <el-radio-button :label=false>否</el-radio-button>
            </el-radio-group>
        </el-form-item>
        <el-form-item label="链接地址" prop="url">
            <el-input
                v-model="form.url"
                style="max-width: 450px"
                placeholder="链接地址"
            />
        </el-form-item>
        <el-form-item  label="菜单标题" prop="name">
            <el-input
                v-model="form.name"
                style="max-width: 450px"
                placeholder="菜单标题"
            />
        </el-form-item>
        <el-form-item  label="菜单描述" prop="desc">
            <el-input
                type="textarea"
                v-model="form.desc"
                :rows="2"
                style="max-width: 450px"
                placeholder="菜单描述"
            />
        </el-form-item>
        <!-- <el-form-item label="路由地址" prop="path">
            <el-input v-model="form.path" placeholder="路由地址" style="width: 178px;" />
        </el-form-item> -->
        <el-form-item label="排序" prop="sort">
            <el-input-number
                v-model.number="form.sort"
                :min="0"
                :max="999"
                controls-position="right"
                style="max-width: 178px;"
            />
        </el-form-item>
        <el-form-item label="语言" prop="language">
            <el-select v-model="form.language" placeholder="请选择语言" style="max-width: 450px" :disabled='queryData.flag'>
                <el-option
                v-for="item in allLanguage"
                :disabled='!item.active'
                :key="item.language"
                :label="item.name"
                :value="item.language">
                </el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="active">
            <el-radio v-model="form.active" :label=true>激活</el-radio>
            <el-radio v-model="form.active" :label=false>冻结</el-radio>
        </el-form-item>
        <el-form-item label="PC端Banner图" prop="thumbnail">
            <upload
                :action="actionUrl"
                avatar-type="avatar"
                :image-url="form.banner"
                :limit="1"
                @input="uploadThumbnail"
            ></upload>
        </el-form-item>
        <el-form-item label="移动端Banner图" prop="bannerMobile">
            <upload
                :action="actionUrl"
                avatar-type="avatar"
                :image-url="form.bannerMobile"
                :limit="1"
                @input="uploadThumbnail2"
            ></upload>
        </el-form-item>
        <el-form-item label="上级类目" prop="parentId">
            <treeselect
                v-model="form.parentId"
                :options="menus"
                :load-options="loadMenus"
                noOptionsText="暂无"
                noResultsText="暂无结果"
                style="max-width: 450px;"
                placeholder="选择上级类目"
                :normalizer="normalizer"
                :show-count="true"
            />
        </el-form-item>
        <el-form-item label="产品分类" prop="parentId">
            <treeselect
                v-model="form.productCategoryId"
                :options="productMenus"
                noOptionsText="暂无"
                noResultsText="暂无结果"
                style="max-width: 450px;"
                placeholder="选择产品分类"
                :normalizer="normalizer"
                :show-count="true"
            />
        </el-form-item>
        <el-form-item v-if="type != 'edit'">
            <el-button type="primary" @click="submitForm('form')">创建</el-button>
            <el-button @click="resetForm('form')">重置</el-button>
        </el-form-item>
    </el-form>
</template>

<script>
import { baseUrl } from "@/config/env";
import IconChoose from '@/components/IconChoose';
import Treeselect from '@riophae/vue-treeselect';
import '@riophae/vue-treeselect/dist/vue-treeselect.css';
import { LOAD_CHILDREN_OPTIONS } from '@riophae/vue-treeselect';
import api from '@/api/cms/navigation';
import Upload from "@/components/jst-components/avatar-upload";
import { mapGetters } from 'vuex';


export default {
    components: {
        IconChoose,
        Treeselect,
        Upload
    },
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
            actionUrl: baseUrl + "api/users/updateAvatar", // 头像上传地址
            productId: null,
            form: {
                id: null,
                name: null,
                sort: 1,
                isFrame: false,
                parentId: 0,
                icon: null,
                type: '主菜单',
                language: null,
                url: '',
                desc: '',
                active: true,
                banner: '',
                productCategoryId: null,
                bannerMobile: ''
            },
            queryData: {},
            menus: [],
            productMenus: [],
            rules: {
                type: [
                    { required: true, message: '请选择菜单类型', trigger: 'change' }
                ],
                name: [
                    { required: true, message: '请输入菜单标题', trigger: 'change' }
                ],
                url: [
                    { required: true, message: '请输入链接地址', trigger: 'change' }
                ],
                desc: [
                    { required: true, message: '请输入菜单描述', trigger: 'change' }
                ],
                language: [
                    { required: true, message: '请选择语言', trigger: 'change' }
                ],
                active: [
                    { required: true, message: '请选择状态', trigger: 'change' }
                ],
                icon: [
                    { required: true, message: '请选择图标', trigger: 'change' }
                ]
            },
            languageData: [],
        };
    },
    watch: {
        activeData(val) {
            if (this.type === 'edit') {         
                this.form = val;
                if (val && val.productCategory &&Object.keys(val.productCategory).length !=0 ) {
                    this.form.productCategoryId = val.productCategory.id;
                }
            }
        }
    },
    computed: {
        ...mapGetters([
            'allLanguage'
        ]),
    },
    created() {
        this.queryData = this.$route.query.data ? JSON.parse(this.$route.query.data) : {};
        this.$store.dispatch("getLanguage"); 
        this.getTreeMenus();
        this.getProductList();
        if (this.type === 'edit') {
            this.form = this.activeData;
            this.queryData.flag = true;
            if (this.activeData && this.activeData.productCategory && Object.keys(this.activeData.productCategory).length != 0) {
                this.form.productCategoryId = this.activeData.productCategory.id;
            }
        } else if (this.queryData && this.queryData.flag) {
            this.form.language = this.queryData.language;
        }
        
    },
    methods: {
        uploadThumbnail(value) {
            this.form.banner = baseUrl + "avatar" + value.avatarPath.split("avatar")[1];
        },
        uploadThumbnail2(value) {
            this.form.bannerMobile = baseUrl + "avatar" + value.avatarPath.split("avatar")[1];
        },
        // 设置树形菜单显示字段
        normalizer(node){
            //去掉children=[]的children属性
            if(node.children && !node.children.length) {
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
        // 获取树形菜单
        getTreeMenus() {
            api.query('', 'get').then(res => {
                this.menus.push({ id: 0, name: '顶级类目',active: true, children: res });
            });
        },
        // 获取产品分类列表
        getProductList() {
            api.queryProductList('', 'get').then(res => {
                this.productMenus = res;
            });
        },
        loadMenus({ action, parentNode, callback }) {
            if (action === LOAD_CHILDREN_OPTIONS) {
                api.getMenus(parentNode.id).then(res => {
                    parentNode.children = res.map(function(obj) {
                        if (!obj.leaf) {
                            obj.children = null;
                        }
                        return obj;
                    })
                    setTimeout(() => {
                        callback()
                    }, 100)
                })
            }
        },
        // 选中图标
        selected(name) {
            this.form.icon = name;
        },
        // 提交
        submitForm(form) {
            let message = this.type === 'edit' ? '保存成功' : '新增成功';
            let methodType = this.type === 'edit' ? 'put' : 'post';
            if (this.queryData.flag) {
                this.form.slug = this.queryData.slug;
            }
            if (this.type ==='edit' && this.form.productCategory && this.form.productCategory.id) {
                delete this.form.productCategory;
            }
            this.$refs[form].validate((valid) => {
                if (valid) {
                    api.query(this.form, methodType).then(() => {
                        this.$message({
                            message,
                            type: 'success'
                        });
                        if (this.type === 'edit') {
                            this.$emit('editSuccess');
                        } else {
                            this.$router.push({
                                path: '/navigation'
                            })
                        }
                    });
                } else {
                    return false;
                }
            });
        },
        // 重置
        resetForm(formName) {
            this.$refs[formName].resetFields();
        },
    }
};
</script>

<style>
</style>