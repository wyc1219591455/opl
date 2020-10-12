<template>
    <div class="app-container">
        <basic-container>
            <!--搜索部分-->
            <el-form :inline="true" :model="searchForm" ref="searchForm"  label-width="80px">
                <el-form-item>
                    <el-input v-model.trim="searchForm.name" clearable placeholder="请输入名称搜索" class="st-input-w"></el-input>
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
            <addPage path="/friendsLinkAdd"></addPage> 
            <!--表格部分-->
            <vxe-table
                    show-overflow
                    auto-resize
                    highlight-hover-row
                    border="inner"
                    ref="table"
                    row-id="id"
                    :start-index="(tablePage.page - 1) * tablePage.size"
                    :data.sync="tableData"
                    :loading="loading"
                    :data="tableData">
                <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
                <vxe-table-column field="name" title="名称" min-width="100"></vxe-table-column>
                <vxe-table-column field="url" title="URL地址" min-width="150"></vxe-table-column>
                <vxe-table-column field="category" title="友情链接分类" min-width="150"></vxe-table-column>
                <vxe-table-column field="language" title="语言">
                    <template v-slot:default="{ row }">
                        {{languageText[row.language]}}
                    </template>
                </vxe-table-column>
                <vxe-table-column field="sort" title="排序"></vxe-table-column>
                <vxe-table-column field="active" title="状态">
                    <template v-slot:default="{ row }">
                        <span>{{ row.active | fStatus }}</span>
                    </template>
                </vxe-table-column>
                <vxe-table-column field="id" title="操作" width="150" fixed="right" align="center">
                    <template v-slot:default="{ row }">
                        <el-button type="primary" @click="edit(row)" size="mini">编辑</el-button>
                        <!--<el-button @click="showInfo(row)" size="mini">查看</el-button>-->
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
    import { friendLinkAll,delFriendLink } from '@/api/cms/website' // 接口
    import { StatusList } from "@/const/constData";
    import formWrap from './components/form'
    import { mapGetters } from 'vuex';
    import addPage from '@/components/addPage';
    export default {
        name: 'friends-link-list',
        components:{
            formWrap,
            addPage
        },
        data() {
            return {
                StatusList: StatusList,
                downloadLoading: false,
                currentPage2: 1,
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
                title: "编辑语言",
                type: "edit", // create 创建 edit 编辑 info 查看
                activeData: {}, // 编辑数据
                model: {
                    name: '',
                    language: '',
                    category: '',
                    url: '',
                    sort: 1,
                    active: true,
                },
                rules: {
                    title: [
                        { required: true, message: '请输入语言', trigger: 'blur' }
                    ],
                    language: [
                        { required: true, message: '请选择语言', trigger: 'blur' }
                    ],
                },
            }
        },
        created(){
            this.$store.dispatch("getLanguage");
            this.getFriendLinkList();
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
            getFriendLinkList(){
                const sort = 'sort,asc';
                const params = {
                    page: this.tablePage.page - 1,
                    size: this.tablePage.size,
                    name: this.searchForm.name,
                    active: this.searchForm.active,
                    sort: sort
                };
                friendLinkAll(params).then((res) => {
                    this.tableData = res.content;
                    this.tablePage.totalElements = res.totalElements;
                })
            },
            // 搜索
            search(){
                this.getFriendLinkList();
            },
            // 搜索重置
            resetSearch(){
                this.searchForm = {
                    name: "",
                    active: "",
                }
              this.getFriendLinkList();
            },
            // 分页发生改变事件 totalElements
            handlePageChange ({ currentPage, pageSize }) {
                this.tablePage.currentPage = currentPage;
                this.tablePage.pageSize = pageSize;
                this.getFriendLinkList();
            },
            // 编辑
            edit(val){
                this.langDialog = true;
                this.activeData = _.cloneDeep(val);
            },
            // 删除
            del(val){
                const ids = [];
                ids.push(val.id);
                this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(() => {
                    delFriendLink(ids).then(() => {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.getFriendLinkList();
                    });
                }).catch(() => {
                    console.log('已取消')
                });
            },
            // 保存编辑
            submitBtn(){
                this.$refs.formRef.submitForm('ruleForm');
            },
            editSuccess() {
                this.langDialog = false;
                this.getFriendLinkList();
            },
            //导出
            doExport() {
                this.downloadLoading = true;
                download().then(() => {
                    this.downloadLoading = false;
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
