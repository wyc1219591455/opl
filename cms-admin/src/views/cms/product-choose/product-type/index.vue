<template>
    <div class="app-container">
        <basic-container>
            <!--搜索部分-->
            <el-form :inline="true" :model="searchForm" ref="searchForm"  label-width="80px">
                <el-form-item>
                    <el-input v-model.trim="searchForm.name" clearable placeholder="请输入属性名称" class="st-input-w"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-select v-model="searchForm.active" clearable placeholder="请选择状态" class="st-input-w">
                        <el-option v-for="(item, index) in StatusList" :key="index" :label="item.label" :value="item.value"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="search()">搜 索</el-button>
                    <el-button type="default" @click="resetSearch('searchForm')">重 置</el-button>
                </el-form-item>
            </el-form>
            <addPage path="/productTypeAdd"></addPage> 
            <!--表格部分-->
            <vxe-table
                    highlight-hover-row
                    resizable
                    tree-config
                    border="inner"
                    ref="xTree"
                    :start-index="(tablePage.page -1) * tablePage.size"
                    :data="tableData"
                    @toggle-tree-expand="toggleExpandChangeEvent">
                <vxe-table-column field="name" title="属性名称" min-width="100" tree-node></vxe-table-column>
                <vxe-table-column field="desc" title="属性描述" min-width="100"></vxe-table-column>
                <vxe-table-column field="active" title="状态">
                    <template v-slot:default="{ row }">
                        <span>{{ row.active | fStatus }}</span>
                    </template>
                </vxe-table-column>
                <vxe-table-column field="id" title="操作" width="200" fixed="right" align="center">
                    <template v-slot:default="{ row }">
                        <div style="display: inline-block"><el-button type="primary" @click="edit(row)" size="mini">编辑</el-button></div>
                        <el-button type="danger" @click="del(row)" size="mini">删除</el-button>
                    </template>
                </vxe-table-column>
            </vxe-table>
            <vxe-pager
                    :loading="loading"
                    :current-page="tablePage.page"
                    :page-size="tablePage.size"
                    :total="tablePage.totalElements"
                    background
                    @page-change="handlePageChange"
            >
            </vxe-pager>
            <!--编辑查看弹窗start-->
            <el-dialog :title="title" :visible.sync="langDialog" width="40%">
                <form-wrap ref="formRef" @editSuccess='editSuccess' :activeData='activeData' :type="type"></form-wrap>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="langDialog = false">取 消</el-button>
                    <el-button type="primary" @click="submitBtn">确 定</el-button>
                </div>
            </el-dialog>
            <!--编辑查看弹窗end-->
        </basic-container>
    </div>
</template>

<script>
    import api from '@/api/cms/product-choose' // 接口
    import { StatusList } from "@/const/constData";
    import formWrap from './components/form';
    import addPage from '@/components/addPage';
    import { mapGetters } from 'vuex';
    export default {
        name: 'product-type',
        components:{
            formWrap,
            addPage
        },
        data() {
            return {
                StatusList: StatusList,
                tableData: [],
                tablePage: {
                    totalElements: 0,
                    page: 1,
                    size: 10,
                    background: true
                },
                searchForm: {
                    name: "",
                    active: "",
                },
                loading: false,
                langDialog: false, //语言编辑弹窗
                title: "编辑匹配产品属性",
                type: "edit", // create 创建 edit 编辑 info 查看
                activeData: {}, // 编辑数据
            }
        },
        created(){
            this.$store.dispatch("getLanguage");
            this.getArticleCategoryList();
        },
        computed: {
            ...mapGetters([
                'allLanguage'
            ]),
            languageText() {
                let obj = {};
                if (this.allLanguage.length) {
                    this.allLanguage.forEach(item => {
                        obj[item.language] = item.name;
                    });
                }
                return obj;
            },
        },
        methods: {
            // 获取分类列表
            getArticleCategoryList(){
                const sort = 'sort,asc';
                const params = {
                    page: this.tablePage.page - 1,
                    size: this.tablePage.size,
                    name: this.searchForm.name,
                    active: this.searchForm.active,
                    sort: sort
                };
                api.queryMatchAttributeList(params,'get').then((res) => {
                    this.tableData = res.content;
                    this.tablePage.totalElements = res.totalElements;
                })
            },
            toggleExpandChangeEvent({ row, expanded }) {
                // console.log(row, expanded);
            },
            // 搜索
            search(){
                this.getArticleCategoryList();
            },
            // 搜索重置
            resetSearch(){
                this.searchForm = {
                    name: "",
                    active: "",
                }
              this.getArticleCategoryList();
            },
            // 分页发生改变事件 totalElements
            handlePageChange ({ currentPage, pageSize }) {
                this.tablePage.page = currentPage;
                this.tablePage.size = pageSize;
                this.getArticleCategoryList();
            },
            // 新增
            creat(data, language) {
                const value = {
                    flag: true,
                    language,
                    slug: data.slug,
                    parentId: data.parentId
                };
                this.$router.push({
                    path: '/productTypeAdd',
                    query: {
                        data: JSON.stringify(value)
                    }
                })
            },
            // 编辑
            edit(val){
                // console.log(val);
                this.langDialog = true;
                this.activeData = _.cloneDeep(val);
            },
            // 保存编辑
            submitBtn(){
                this.$refs.formRef.submitForm('ruleForm');
            },
            editSuccess() {
                this.langDialog = false;
                this.getArticleCategoryList();
            },
            // 删除
            del(data) {
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    api.queryMatchAttributeList({id: data.id}, 'delete').then(() => {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.getArticleCategoryList();
                    });
                }).catch(() => {
                });
            },
        }
    }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
    .export {
        float: right;
    }
</style>
