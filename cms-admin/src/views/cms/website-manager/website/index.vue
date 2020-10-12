<template>
    <div class="app-container">
        <basic-container>
            <div class="head-container">
                <!-- 搜索 -->
                <el-input v-model="searchForm.name" clearable size="small" placeholder="请输入网站名称" style="width: 200px;" class="filter-item" />
                <el-input v-model="searchForm.site" clearable size="small" placeholder="请输入网站地址" style="width: 200px;" class="filter-item" />
                <el-button type="primary" @click="search">搜 索</el-button>
                <el-button type="default" @click="resetSearch">重 置</el-button>
            </div>
            <addPage path="/websiteSet"></addPage> 
            <div>
                <vxe-table
                    border="inner"
                    :show-overflow="false"
                    auto-resize
                    highlight-hover-row
                    ref="table"
                    row-id="id"
                    :start-index="(tablePage.page -1) * tablePage.size"
                    :data.sync="tableData"
                >
                    <vxe-table-column type="seq" title="序号" width="50" fixed="left"></vxe-table-column>
                    <vxe-table-column field="name" title="网站名称" min-width="100"></vxe-table-column>
                    <vxe-table-column field="site" title="网站地址" min-width="150"></vxe-table-column>
                    <vxe-table-column field="title" title="网站标题" min-width="120"></vxe-table-column>
                    <vxe-table-column field="subTitle" title="网站副标题" min-width="150"></vxe-table-column>
                    <vxe-table-column field="language" title="语言" min-width="100">
                        <template v-slot:default="{ row }">{{languageText[row.language]}}</template>
                    </vxe-table-column>
                    <vxe-table-column field="seoKeywords" title="SEO关键字" min-width="100"></vxe-table-column>
                    <vxe-table-column field="ipcNo" title="网站备案号" min-width="100"></vxe-table-column>
                    <vxe-table-column field="copyright" title="版权信息" min-width="100"></vxe-table-column>
                    <vxe-table-column field="id" title="操作" width="200" fixed="right" align="center">
                        <template v-slot:default="{ row }">
                            <el-button  type="primary" @click="editWebsite(row)" size="mini">编辑</el-button>
                            <el-button  type="danger" @click="del(row)" size="mini">删除</el-button>
                        </template>
                    </vxe-table-column>
                </vxe-table>
                <vxe-pager
                    :current-page="tablePage.page"
                    :page-size="tablePage.size"
                    :total="tablePage.total "
                    background
                    @page-change="handlePageChange"
                >
                </vxe-pager>
            </div>
            <!--弹框组件-->
            <el-dialog  title="编辑网站" :visible.sync="dialogFormVisible" width="35%">
                <form-wrap ref="formRef" @editSuccess='editSuccess' :activeData='activeData' :type="type"></form-wrap>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="confirm">确 定</el-button>
                </div>
            </el-dialog>
        </basic-container>
    </div>
</template>

<script>
    import { query, remove } from '@/api/cms/website';
    import formWrap from './components/form';
    import { mapGetters } from 'vuex';
    import addPage from '@/components/addPage';

    export default {
        name: 'customer',
        components:{
            formWrap,
            addPage
        },
        data() {
            return {
                type: 'edit',
                dialogFormVisible: false,
                tableData: [],
                tablePage: {
                    total: 0,
                    page: 1,
                    size: 10,
                    background: true
                },
                searchForm: {
                    name: '',
                    site: ''
                },
                activeData: {},
            }
        },
        mounted() {
            this.$store.dispatch("getLanguage");
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
            // 新增
            add() {
                this.dialogFormVisible = true;
            },
            // 删除
            del(data) {
                const ids = [];
                ids.push(data.id);
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    remove(ids).then(() => {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.getList();
                    });
                }).catch(() => {
                    console.log('已取消')
                });
            },
            // 获取列表
            getList() {
                const parmas = {
                    page: this.tablePage.page - 1,
                    size: this.tablePage.size,
                    name: this.searchForm.name,
                    site: this.searchForm.site,
                }
                query(parmas).then((res)=>{
                    this.tableData = res.content;
                    this.tablePage.total = res.totalElements;
                });
            },
            //编辑
            editWebsite(data) {
                this.activeData = _.cloneDeep(data);
                this.dialogFormVisible = true;
            },
            confirm() {
                this.$refs.formRef.submitForm('ruleForm');
            },
            // 搜索
            search() {
                this.getList();
            },
            // 分页发生改变事件 totalElements
            handlePageChange ({ currentPage, pageSize }) {
                // console.log(`当前页: ${currentPage}`);
                // console.log(`每页 ${pageSize} 条`);
                this.tablePage.page = currentPage;
                this.tablePage.size = pageSize;
                this.getList();
            },
            editSuccess() {
                this.dialogFormVisible = false;
                this.getList();
            },
            resetSearch() {
                this.searchForm = {
                    name: '',
                    site: ''
                }
              this.getList();
            },
        }
}
</script>
<style rel="stylesheet/scss" lang="scss" scoped>
.add-btn {
    float: right;
}
</style>
