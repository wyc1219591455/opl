<template>
    <div class="app-container">
        <basic-container>
            <!--搜索部分-->
            <el-form :inline="true" label-width="80px">
                <el-form-item>
                    <el-input v-model.trim="searchForm.name" clearable placeholder="请输入姓名" class="st-input-w"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-input v-model.trim="searchForm.title" clearable placeholder="请输入标题" class="st-input-w"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="search()">搜 索</el-button>
                    <el-button type="default" @click="resetSearch('searchForm')">重 置</el-button>
                </el-form-item>
                <!--<el-button-->
                        <!--class="filter-item export"-->
                        <!--size="mini"-->
                        <!--:loading="downloadLoading"-->
                        <!--type="warning"-->
                        <!--icon="el-icon-download"-->
                        <!--@click="doExport"-->
                <!--&gt;-->
                    <!--导出-->
                <!--</el-button>-->
            </el-form>
            <!--表格部分-->
            <vxe-table
                show-overflow
                auto-resize
                highlight-hover-row
                ref="table"
                row-id="id"
                border="inner"
                :start-index="(tablePage.page - 1) * tablePage.size"
                :data.sync="tableData"
                :loading="loading"
                >
                <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
                <vxe-table-column field="name" title="姓名"></vxe-table-column>
                <vxe-table-column field="contact" title="联系方式"></vxe-table-column>
                <vxe-table-column field="title" title="留言标题"></vxe-table-column>
                <vxe-table-column field="content" title="留言内容"></vxe-table-column>
                <vxe-table-column field="remark" title="留言备注"></vxe-table-column>
                <vxe-table-column field="isRead" title="状态">
                    <template v-slot:default="{ row }">
                        {{row.isRead ?  '已读': '未读'}}
                    </template>
                </vxe-table-column>
                <vxe-table-column field="id" title="操作" width="250" fixed="right" align="center">
                    <template v-slot:default="{ row }">
                        <!--<el-button v-if="!row.isRead" type="primary" @click="seeMessage(row)" size="mini">标记已读</el-button>-->
                        <el-button type="primary" @click="seeMessage(row)" :disabled="row.isRead ?  true : false"  size="mini">标记已读</el-button>
                        <el-button type="primary" @click="edit(row)" size="mini">备注</el-button>
                        <el-button type="danger" @click="del(row)" size="mini">删除</el-button>
                    </template>
                </vxe-table-column>
            </vxe-table>
            <vxe-pager
                    :loading="loading"
                    :current-page="tablePage.page"
                    :page-size="tablePage.size"
                    :total="tablePage.total"
                    background
                    @page-change="handlePageChange"
            >
            </vxe-pager>
            <!--编辑查看弹窗start-->
            <el-dialog :title="title" :visible.sync="messageDialog" width="40%">
                <el-form :model="model" :rules="rules" ref="ruleForm" label-width="100px">
                    <!--<el-form-item label="姓名" prop="name">-->
                        <!--<el-input v-model.trim="model.name" disabled="false" placeholder="请输入姓名"></el-input>-->
                    <!--</el-form-item>-->
                    <!--<el-form-item label="联系方式" prop="contact">-->
                        <!--<el-input v-model.trim="model.contact" disabled="false" placeholder="请输入联系方式"></el-input>-->
                    <!--</el-form-item>-->
                    <!--<el-form-item label="留言标题" prop="title">-->
                        <!--<el-input v-model.trim="model.title" disabled="false" placeholder="请输入留言标题"></el-input>-->
                    <!--</el-form-item>-->
                    <!--<el-form-item label="留言内容" prop="content">-->
                        <!--<el-input-->
                                <!--v-model="model.content"-->
                                <!--disabled="false"-->
                                <!--type="textarea"-->
                                <!--:autosize="{ minRows: 2, maxRows: 4}"-->
                                <!--placeholder="请输入留言内容">-->
                        <!--</el-input>-->
                    <!--</el-form-item>-->
                    <el-form-item label="留言备注" prop="remark">
                        <!--<el-input v-model.trim="model.remark" placeholder="请输入留言备注"></el-input>-->
                        <el-input
                                v-model="model.remark"
                                type="textarea"
                                :autosize="{ minRows: 4, maxRows: 6}"
                                placeholder="请输入留言备注">
                        </el-input>
                    </el-form-item>
                    <el-form-item class="form-btn">
                        <el-button @click="messageDialog = false">取消</el-button>
                        <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
                    </el-form-item>
                </el-form>
            </el-dialog>
            <!--编辑查看弹窗end-->
        </basic-container>
    </div>
</template>

<script>
    import { query, edit, remove } from '@/api/cms/message' // 接口
    export default {
        name: 'language',
        data() {
            return {
                downloadLoading: false,
                currentPage2: 1,
                tableData: [],
                tablePage: {
                    total: 0,
                    page: 1,
                    size: 10,
                    background: true
                },
                searchForm: {
                    name: "",
                    title: "",
                },
                loading: false,
                messageDialog: false, //语言编辑弹窗
                isDisabled: false,
                title: "添加备注",
                model: {
                    name: '',
                    contact: '',
                    title: '',
                    content: '',
                    remark: '',
                },
                rules: {
                    remark: [
                        { required: true, message: '请输入留言备注', trigger: 'change' }
                    ],
                },
            }
        },
        created(){
            this.getList()
        },
        methods: {
            getList(){
                const parmas = {
                    page: this.tablePage.page - 1,
                    size: this.tablePage.size,
                    name: this.searchForm.name,
                    title: this.searchForm.title
                };
                query(parmas).then((res) => {
                    this.tableData = res.data.data.content;
                    this.tablePage.total = res.data.data.totalElements;
                })
            },
            // 搜索
            search(){
                this.getList();
            },
            // 搜索重置
            resetSearch(){
                this.searchForm = {
                    name: "",
                    title: "",
                }
              this.getList();
            },
            // 分页发生改变事件 totalElements
            handlePageChange ({ currentPage, pageSize }) {
                this.tablePage.page = currentPage;
                this.tablePage.size = pageSize;
                this.getList();
            },
            // 标记已读
            seeMessage(val){
                console.log(val);
                this.model = _.cloneDeep(val);
                this.model.isRead = 1;
                edit(this.model).then(() => {
                    this.messageDialog = false;
                    this.$message({
                        message: '保存成功',
                        type: 'success'
                    });
                    this.getList();
                })
            },
            // 备注
            edit(val){
                this.messageDialog = true;
                this.model = _.cloneDeep(val);
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
            // 保存备注
            submitForm(formName) {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        edit(this.model).then(() => {
                            this.messageDialog = false;
                            this.$message({
                                message: '保存成功',
                                type: 'success'
                            });
                            this.getList();
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
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
