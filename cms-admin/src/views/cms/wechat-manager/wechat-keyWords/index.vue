<template>
    <div class="app-container">
        <basic-container>
            <div class="head-container">
                <!-- 搜索 -->
                <el-input v-model="searchForm.keyword" clearable size="small" placeholder="请输入关键字名称" style="width: 200px;" class="filter-item" />
                <el-button type="primary" @click="search">搜 索</el-button>
                <el-button type="default" @click="resetSearch">重 置</el-button>
            </div>
            <addPage path="/keyWordsAdd"></addPage> 
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
                    <vxe-table-column field="keyword" title="关键字名称" min-width="100"></vxe-table-column>
                    <vxe-table-column field="content" title="回复内容" min-width="150"></vxe-table-column>
                    <vxe-table-column title="类型" min-width="120">
                        <template v-slot:default="{ row }">
                            {{row.type === 1 ? '收到消息回复' : row.type === 2 ? '关键词回复':'被关注回复'}}
                        </template>
                    </vxe-table-column>
                    <vxe-table-column field="createBy" title="创建人" min-width="100"></vxe-table-column>
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
    import api from '@/api/cms/wechat';
    import formWrap from './components/form';
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
                    keyword: ''
                },
                activeData: {},
            }
        },
        mounted() {
            this.getList();
        },
        computed: {
        },
        methods: {
            // 新增
            add() {
                this.dialogFormVisible = true;
            },
            // 删除
            del(data) {
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    api.queryWeChatKeyWords({id: data.id}, 'delete').then(() => {
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
                    keyword: this.searchForm.keyword
                }
                api.queryWeChatKeyWords(parmas, 'get').then((res)=>{
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
