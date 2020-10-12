<template>
    <div class="app-container">
        <basic-container>
            <!--搜索部分-->
            <el-form :inline="true" :model="searchForm" ref="searchForm"  label-width="80px">
                <el-form-item>
                    <el-input v-model.trim="searchForm.name" clearable placeholder="请输入语言名称" class="st-input-w"></el-input>
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
            <addPage path="/langAdd"></addPage> 
            <!--表格部分-->
            <vxe-table
                border="inner"
                show-overflow
                auto-resize
                highlight-hover-row
                ref="table"
                row-id="id"
                :start-index="(tablePage.page - 1) * tablePage.size"
                :data.sync="tableData"
                :loading="loading"
                    >
                <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
                <vxe-table-column field="name" title="语言名称"></vxe-table-column>
                <vxe-table-column field="language" title="语言标识"></vxe-table-column>
                <vxe-table-column field="desc" title="语言简介"></vxe-table-column>
                <vxe-table-column field="active" title="状态">
                    <template v-slot:default="{ row }">
                        <span>{{ row.active | fStatus }}</span>
                    </template>
                </vxe-table-column>
                <vxe-table-column field="sort" title="排序"></vxe-table-column>
                <vxe-table-column field="id" title="操作" width="150" fixed="right" align="center">
                    <template v-slot:default="{ row }">
                        <el-button type="primary" @click="edit(row)" size="mini">编辑</el-button>
                        <!--<el-button @click="showInfo(row)" size="mini">查看</el-button>-->
                        <!--<el-button type="danger" @click="del(row)" size="mini">删除</el-button>-->
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
                <el-form :model="model" :rules="rules" ref="ruleForm" label-width="100px">
                    <el-form-item label="语言名称" prop="name">
                        <el-input v-model.trim="model.name" :disabled="isDisabled" placeholder="请输入"></el-input>
                    </el-form-item>
                    <el-form-item label="语言标识" prop="language">
                        <el-input v-model.trim="model.language" :disabled="true" placeholder="请输入语言标识，如：zh-cn"></el-input>
                    </el-form-item>
                    <el-form-item label="语言简介" prop="desc">
                        <el-input
                                v-model.trim="model.desc"
                                type="textarea"
                                :disabled="isDisabled"
                                :autosize="{ minRows: 2, maxRows: 4}"
                                placeholder="请输入语言简介"></el-input>
                    </el-form-item>
                    <el-form-item label="状态" prop="active">
                        <el-radio-group v-model="model.active">
                            <el-radio :disabled="isDisabled" :label="true">激活</el-radio>
                            <el-radio :disabled="isDisabled" :label="false">冻结</el-radio>
                        </el-radio-group>
                    </el-form-item>
                    <el-form-item label="排序" prop="sort">
                        <el-input-number v-model.trim="model.sort" :max="999" :disabled="isDisabled" placeholder="请输入"></el-input-number>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="langDialog = false">取 消</el-button>
                    <el-button type="primary" @click="submitBtn('ruleForm')">确 定</el-button>
                </div>
            </el-dialog>
            <!--编辑查看弹窗end-->
        </basic-container>
    </div>
</template>

<script>
    import { getLanguageAll, editLanguage, delLanguage } from '@/api/cms/lang'; // 接口
    import addPage from '@/components/addPage';
    import { StatusList } from "@/const/constData";
    export default {
        name: 'language',
        components: {
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
                isDisabled: false, // 表单是否禁用
                title: "编辑语言",
                type: "create", // create 创建 edit 编辑 info 查看
                model: {
                    name: '',
                    language: '',
                    desc: '',
                    sort: 1,
                    active: true,
                },
                rules: {
                    name: [
                        { required: true, message: '请输入语言', trigger: 'blur' }
                    ],
                    language: [
                        { required: true, message: '请选择语言', trigger: 'blur' }
                    ],
                },
            }
        },
        created(){
            this.getLangList()
        },
        methods: {
            getLangList(){
                const sort = 'sort,asc';
                const params = {
                    page: this.tablePage.page - 1,
                    size: this.tablePage.size,
                    name: this.searchForm.name,
                    active: this.searchForm.active,
                    sort: sort
                };
                getLanguageAll(params).then((res) => {
                    this.tableData = res.content;
                    this.tablePage.totalElements = res.totalElements;
                })
            },
            // 搜索
            search(){
                this.getLangList();
            },
            // 搜索重置
            resetSearch(){
                this.searchForm = {
                    name: "",
                    active: "",
                }
              this.getLangList();
            },
            // 分页发生改变事件 totalElements
            handlePageChange ({ currentPage, pageSize }) {
                this.tablePage.page = currentPage;
                this.tablePage.size = pageSize;
                this.getLangList();
            },
            // 编辑
            edit(val){
                this.langDialog = true;
                this.model = _.cloneDeep(val);
                this.title = '编辑语言';
                this.isDisabled = false;
            },
            // 查看
            showInfo(val){
                this.langDialog = true;
                this.model = _.cloneDeep(val);
                this.title = '查看语言';
                this.isDisabled = true;
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
                    delLanguage(ids).then(() => {
                        this.$message({
                            type: 'success',
                            message: '删除成功!'
                        });
                        this.getLangList();
                    });
                }).catch(() => {
                    console.log('已取消')
                });
            },
            // 新增
            add() {
                this.$router.push({
                    path: '/langAdd'
                });
            },
            // 保存编辑
            submitBtn(formName){
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        editLanguage(this.model).then((res) => {
                            this.langDialog = false;
                            this.$message({
                                message: '修改成功',
                                type: 'success'
                            });
                            this.getLangList();
                        })
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
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
