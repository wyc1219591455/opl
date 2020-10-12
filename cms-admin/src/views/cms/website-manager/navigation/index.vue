<template>
    <div class="app-container">
        <basic-container>
            <div class="head-container">
                <!-- 搜索 -->
                <el-input v-model="searchForm.name" clearable size="small" placeholder="请输入菜单名称" style="width: 200px;" class="filter-item" />
                <el-button type="primary" @click="search">搜 索</el-button>
                <el-button type="default" @click="resetSearch">重 置</el-button>
            </div>
            <addPage path="/navigationAdd"></addPage> 
            <vxe-table
                border="inner"
                resizable
                :tree-config="{iconOpen: 'el-icon-arrow-down', iconClose: 'el-icon-arrow-right'}"
                ref="xTree"
                :data="tableData"
                @toggle-tree-expand="toggleExpandChangeEvent">
                <vxe-table-column field="name" title="菜单名称" tree-node></vxe-table-column>
                <vxe-table-column field="type" title="菜单类型"></vxe-table-column>
                <vxe-table-column field="isFrame" title="是否外链" align="center">
                    <template v-slot:default="{ row }" >{{row.isFrame ? '是' : '否'}}</template>
                </vxe-table-column>
                <vxe-table-column field="language" title="语言" width="100">
                    <template v-slot:default="{ row }">{{languageText[row.language]}}</template>
                </vxe-table-column>
                 <vxe-table-column field="isFrame" title="状态" width="80">
                    <template v-slot:default="{ row }">{{row.active | fStatus}}</template>
                </vxe-table-column>
                <vxe-table-column field="createTime" title="创建时间" ></vxe-table-column>
                <vxe-table-column field="id" title="操作" width="260" fixed="right" align="center">
                    <template v-slot:default="{ row }">
                        <div style="display: inline-block">
                            <el-button type="primary" @click="edit(row)" size="mini">编辑</el-button>
                            <el-button  type="danger" @click="del(row)" size="mini">删除</el-button>
                        </div>
                        <div style="display: inline-block;margin-left: 5px">
                            <el-dropdown trigger="click">
                                <el-button type="primary" size="mini" :disabled="row.buttons | isDisabled">
                                    更多操作<i class="el-icon-arrow-down el-icon--right"></i>
                                </el-button>
                                <el-dropdown-menu slot="dropdown">
                                    <el-dropdown-item v-for="(item, index) in row.buttons" :key="index">
                                        <div @click="creat(row, item.language)" v-if="!item.isCreated">新增{{item.name}}</div>
                                    </el-dropdown-item>
                                </el-dropdown-menu>
                            </el-dropdown>
                        </div>
                    </template>
                </vxe-table-column>
            </vxe-table>
            <!--弹框组件-->
            <el-dialog  title="编辑网站" :visible.sync="dialogFormVisible" width="36%">
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
import api from '@/api/cms/navigation';
import formWrap from './componets/form';
import { mapGetters } from 'vuex';
import addPage from '@/components/addPage';
export default {
    components: {
        formWrap,
        addPage
    },
    data () {
        return {
            searchForm: {
                name: '',
            },
            tableData: [],
            dialogFormVisible: false,
            activeData: {},
            type: 'edit',
        }
    },
    mounted() {
        console.log('navigation');
        this.$store.dispatch("getLanguage");
        this.getList();
    },
    computed: {
        ...mapGetters([
            'allLanguage'
        ]),
        languageText() {
            let obj = {};
            this.allLanguage.forEach(item => {
                obj[item.language] = item.name;
            });
            return obj;
        }
    },
    methods: {
        getList() {
            const parmas = {
                name: this.searchForm.name
            }
            api.query(parmas, 'get').then(res => {
                this.tableData = res;
            })
        },
        search() {
            this.getList();
        },
        resetSearch() {
            this.searchForm.name = '';
          this.getList();
        },
        toggleExpandChangeEvent({ row, expanded }) {
            // console.log(row, expanded);
        },
        //创建
        creat(data, language) {
            const value = {
                flag: true,
                language,
                slug: data.slug,
                parentId: data.parentId
            }
            this.$router.push({
                path: '/navigationAdd',
                query: {
                    data: JSON.stringify(value)
                }
            })
        },
        // 编辑
        edit(data) {
            this.activeData = _.cloneDeep(data);
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
                api.query(ids, 'delete').then(() => {
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                    this.getList();
                });
            }).catch(() => {
            });
        },
        editSuccess() {
            this.dialogFormVisible = false;
            this.getList();
        },
        confirm() {
            this.$refs.formRef.submitForm('form');
        }
    }
}
</script>

<style>
</style>
