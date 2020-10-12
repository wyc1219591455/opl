<template>
    <div class="app-container">
        <basic-container>
            <div class="head-container">
                <!-- 搜索 -->
                <el-input v-model="searchForm.name" clearable size="small" placeholder="请输入姓名" style="width: 200px;" class="filter-item" />
                <el-input v-model="searchForm.contact" clearable size="small" placeholder="请输入联系方式" style="width: 200px;" class="filter-item" />
                <el-select v-model="searchForm.status" placeholder="请选择状态">
                    <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                    </el-option>
                </el-select>
                <el-button type="primary" @click="search" style="margin-left:5px">搜 索</el-button>
                <el-button type="default" @click="resetSearch">重 置</el-button>
            </div>
            <!--弹框组件-->
            <el-dialog  title="添加备注" :visible.sync="dialogFormVisible">
                <el-form :model="form" label-width="100px" :rules="rules" ref="ruleForm">
                    <el-form-item label="信息备注" prop="remark">
                        <el-input v-model.trim="form.remark"
                            type="textarea"
                            :autosize="{ minRows: 4, maxRows: 8}" >
                        </el-input>
                    </el-form-item>
                </el-form>
                <div slot="footer" class="dialog-footer">
                    <el-button @click="dialogFormVisible = false">取 消</el-button>
                    <el-button type="primary" @click="addRemark">确 定</el-button>
                </div>
            </el-dialog>
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
                    <vxe-table-column field="title" title="标题" width="100" fixed="left"></vxe-table-column>
                    <vxe-table-column field="customer.name" title="姓名" min-width="100"></vxe-table-column>
                    <vxe-table-column field="customer.contact" title="联系方式" min-width="120"></vxe-table-column>
                    <vxe-table-column field="customer.email" title="邮箱" min-width="150"></vxe-table-column>
                    <vxe-table-column field="customer.wechat" title="微信" min-width="100"></vxe-table-column>
                    <vxe-table-column field="customer.qq" title="QQ" min-width="80"></vxe-table-column>
                    <vxe-table-column field="customer.gender" title="性别" width="50"></vxe-table-column>
                    <vxe-table-column field="customer.status" title="状态" width="100">
                        <template v-slot:default="{ row }">
                           <p>{{row.status? '已处理' : '未处理'}}</p>
                        </template>
                    </vxe-table-column>
                    <vxe-table-column field="content" title="咨询内容" min-width="200"></vxe-table-column>
                    <vxe-table-column field="remark" title="备注" min-width="200"></vxe-table-column>
                    <vxe-table-column field="id" title="操作" width="200" fixed="right" align="center">
                        <template v-slot:default="{ row }">
                            <el-button type="primary" @click="remark(row)" size="mini">备注</el-button>
                            <el-button v-if="row.status" :disabled="row.status" type="default" size="mini">状态已改</el-button>
                            <el-button v-else type="default" @click="changeStatus(row)" size="mini">更改状态</el-button>
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
        </basic-container>
    </div>
</template>

<script>
    import { query, edit } from '@/api/cms/counsel';
    export default {
        name: 'counsel',
        data() {
            return {
                dialogFormVisible: false,
                rules: {
                    remark: [
                        { required: true, message: '请输入备注信息', trigger: 'change' },
                    ],
                },
                form: {
                    remark: ''
                },
                tableData: [],
                tablePage: {
                    total: 0,
                    page: 1,
                    size: 10,
                    background: true
                },
                searchForm: {
                    name: '',
                    contact: '',
                    status: ''
                },
                options: [
                    {
                        label: '全部',
                        value: ''
                    },
                    {
                        label: '未处理',
                        value: 0
                    },
                    {
                        label: '已处理',
                        value: 1
                    }
                ]
            }
        },
        mounted() {
            this.getList();
        },
        methods: {
            // 获取列表
            getList() {
                const parmas = {
                    page: this.tablePage.page - 1,
                    size: this.tablePage.size,
                    name: this.searchForm.name,
                    contact: this.searchForm.contact,
                    status: this.searchForm.status
                }
                query(parmas).then((res)=>{
                    this.tableData = res.content;
                    this.tablePage.total = res.totalElements;
                });
            },
            // 备注
            addRemark(){
                this.$refs["ruleForm"].validate((valid) => {
                    if (valid) {
                        this.dialogFormVisible = false;
                        edit(this.form).then(()=>{
                            this.$message({
                                message: '备注成功',
                                type: 'success'
                            });
                            this.getList();
                        });
                    } else {
                        return false;
                    }
                });
            },
            // 搜索
            search() {
                this.getList();
            },
            resetSearch() {
                this.searchForm = {
                    name: '',
                    contact: ''
                }
            },
            changeStatus(parmas) {
                this.form = _.cloneDeep(parmas);
                this.form.status = true;
                this.$confirm("您确定要将此条信息标记为已处理, 是否继续?", "提示", {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    center: true,
                }).then(() => {
                        edit(this.form).then(()=>{
                            this.$message({
                                message: '更改成功',
                                type: 'success'
                            });
                            this.getList();
                        });
                       
                    }).catch(() => {

                    });
            },
             // 分页发生改变事件 totalElements
            handlePageChange ({ currentPage, pageSize }) {
                this.tablePage.page = currentPage;
                this.tablePage.size = pageSize;
                this.getList();
            },
            remark(parmas) {
                this.dialogFormVisible = true;
                this.form = _.cloneDeep(parmas);
            }
        }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  /deep/ .vue-treeselect__control, /deep/ .vue-treeselect__placeholder, /deep/ .vue-treeselect__single-value {
    height: 30px;
    line-height: 30px;
  }
</style>
<style rel="stylesheet/scss" lang="scss" scoped>
  /deep/ .el-input-number .el-input__inner {
    text-align: left;
  }
.export {
    float: right;
}
</style>
