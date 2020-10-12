<template>
    <div class="app-container">
        <basic-container>
            <!--搜索部分-->
            <el-form :inline="true" :model="searchForm" ref="searchForm"  label-width="80px">
                <el-form-item label="产品分类" prop="value1">
                    <treeselect
                        v-model="searchForm.productCategoryId"
                        :options="productSortList"
                        noOptionsText="暂无匹配分类"
                        style="width: 200px"
                        placeholder="请选择产品分类"
                        @select="treeChoose"
                        :normalizer="normalizer"
                    />
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="search()">搜 索</el-button>
                    <el-button type="default" @click="resetSearch('searchForm')">重 置</el-button>
                </el-form-item>
            </el-form>
            <addPage path="/binding"></addPage> 
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
                <vxe-table-column field="productCms.productCategory.name" title="产品分类" min-width="100" tree-node></vxe-table-column>
                <vxe-table-column field="productCms.name" title="产品名称" min-width="100"></vxe-table-column>
                <vxe-table-column field="matchProductDtoList[0].matchProductCategory.name" title="匹配产品分类" min-width="100" tree-node></vxe-table-column>
                <!-- <vxe-table-column field="matchProductDtoList[0].name" title="匹配产品名称" min-width="100"></vxe-table-column> -->
                <vxe-table-column field="drawingDto.name" title="图纸名称" min-width="100" tree-node></vxe-table-column>
                <vxe-table-column title="是否匹配基本属性" min-width="100">
                    <template v-slot:default="{ row }">
                        <span>{{ row.showProperty ? '是' : '否'}}</span>
                    </template>
                </vxe-table-column>
                <vxe-table-column field="id" title="操作" width="200" fixed="right" align="center">
                    <template v-slot:default="{ row }">
                        <div style="display: inline-block"><el-button type="primary" @click="edit(row)" size="mini">编辑</el-button></div>
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
            <el-dialog :title="title" :visible.sync="langDialog" width="50%">
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
    import Treeselect from "@riophae/vue-treeselect";
    import "@riophae/vue-treeselect/dist/vue-treeselect.css";
    import api from '@/api/cms/product-choose' // 接口
    import { StatusList } from "@/const/constData";
    import formWrap from './components/form';
    import { mapGetters } from 'vuex';
    import addPage from '@/components/addPage';
    export default {
        name: 'product-type',
        components:{
            formWrap,
            Treeselect,
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
                productSortList: [],
                searchForm: {
                    productCategoryId: null,
                },
                loading: false,
                langDialog: false, //语言编辑弹窗
                title: "编辑匹配产品绑定",
                type: "edit", // create 创建 edit 编辑 info 查看
                activeData: {}, // 编辑数据
            }
        },
        created(){
            this.$store.dispatch("getLanguage");
            this.getProductSort();
            this.getList();
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
            treeChoose(node) {
                console.log(node);
                this.searchForm.productCategoryId = node.id;
            },
            // 获取产品分类列表
            getProductSort() {
                api.queryProductList({}, "get").then((res) => {
                    this.productSortList = res.content;
                });
            },
            // 获取分类列表
            getList(){
                const params = {
                    page: this.tablePage.page - 1,
                    size: this.tablePage.size,
                    productCategoryId: this.searchForm.productCategoryId,
                };
                api.queryBind(params,'get').then((res) => {
                    this.tableData = res.content;
                    this.tablePage.totalElements = res.totalElements;
                })
            },
            toggleExpandChangeEvent({ row, expanded }) {
                // console.log(row, expanded);
            },
            // 设置树形菜单显示字段
            normalizer(node) {
            //去掉children=[]的children属性
                if (node.children && !node.children.length) {
                    delete node.children;
                }
                return {
                    id: node.id,
                    //将name转换成必填的label键
                    label: node.name,
                    children: node.children,
                    isDisabled: !node.active,
                };
            },
            // 搜索
            search(){
                this.getList();
            },
            // 搜索重置
            resetSearch(){
                this.searchForm = {
                    productCategoryId: null,
                }
              this.getList();
            },
            // 分页发生改变事件 totalElements
            handlePageChange ({ currentPage, pageSize }) {
                this.tablePage.page = currentPage;
                this.tablePage.size = pageSize;
                this.getList();
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
                        data: JSON.stringify(value),
                    }
                })
            },
            // 编辑
            edit(val){
                this.langDialog = true;
                this.activeData = _.cloneDeep(val);
                this.$nextTick(() =>{
                    this.$refs.formRef.getArticleCategoryList();
                });
                // this.$refs.formRef.getArticleCategoryList();
            },
            // 保存编辑
            submitBtn(){
                this.$refs.formRef.submitForm('ruleForm');
            },
            editSuccess() {
                this.langDialog = false;
                this.getList();
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
                        // this.getList();
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
