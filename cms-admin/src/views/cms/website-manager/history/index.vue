<template>
  <div class="app-container">
    <basic-container>
      <!--搜索部分-->
      <el-form :inline="true" :model="searchForm" ref="searchForm"  label-width="80px">
        <el-form-item>
          <el-input v-model.trim="searchForm.title" clearable placeholder="请输入事件标题" class="st-input-w"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search()">搜 索</el-button>
          <el-button type="default" @click="resetSearch('searchForm')">重 置</el-button>
        </el-form-item>
      </el-form>
      <addPage path="/historyAdd"></addPage> 
      <!--表格部分-->
      <vxe-table
          highlight-hover-row
          resizable
          border="inner"
          tree-config
          ref="xTree"
          :start-index="(tablePage.page -1) * tablePage.size"
          :data="tableData">
        <vxe-table-column field="title" title="事件标题" show-overflow min-width="100"></vxe-table-column>
        <vxe-table-column field="summary" title="事件简介" show-overflow min-width="200"></vxe-table-column>
        <vxe-table-column field="date" title="事件时间" min-width="50"></vxe-table-column>
        <vxe-table-column field="year" title="事件年份" min-width="50"></vxe-table-column>
        <vxe-table-column field="language" title="语言" min-width="50">
          <template v-slot:default="{ row }">{{languageText[row.language]}}</template>
        </vxe-table-column>
        <vxe-table-column field="active" title="状态">
          <template v-slot:default="{ row }">
            <span>{{ row.active | fStatus }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column field="id" title="操作" width="200" fixed="right" align="center">
          <template v-slot:default="{ row }">
            <el-button type="primary" @click="edit(row)" size="mini">编辑</el-button>
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
import api from '@/api/cms/website' // 接口
import { StatusList } from "@/const/constData";
import formWrap from './components/form'
import { mapGetters } from 'vuex';
import addPage from '@/components/addPage';
export default {
  name: 'product-sorts',
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
        title: "",
      },
      loading: false,
      langDialog: false, //语言编辑弹窗
      title: "编辑大事记",
      type: "edit", // create 创建 edit 编辑 info 查看
      activeData: {}, // 编辑数据
    }
  },
  created(){
    this.$store.dispatch("getLanguage");
    this.getHistoryList();
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
    // 获取大事记列表
    getHistoryList(){
      const sort = 'sort,asc';
      const params = {
        page: this.tablePage.page - 1,
        size: this.tablePage.size,
        title: this.searchForm.title,
        sort: sort
      };
      api.queryBigEvent(params,'get').then((res) => {
        this.tableData = res.content;
        this.tablePage.totalElements = res.totalElements
      })
    },
    // 搜索
    search(){
      this.getHistoryList();
    },
    // 搜索重置
    resetSearch(){
      this.searchForm = {
        title: "",
      }
      this.getHistoryList();
    },
    // 分页发生改变事件 totalElements
    handlePageChange ({ currentPage, pageSize }) {
      this.tablePage.page = currentPage;
      this.tablePage.size = pageSize;
      this.getHistoryList();
    },
    // 新增
    creat(data) {
      const value = {
        flag: true,
        slug: data.slug,
        parentId: data.parentId
      };
      this.$router.push({
        path: '/historyAdd',
        query: {
          data: JSON.stringify(value)
        }
      })
    },
    // 删除
    del(val){
      const ids = {"id":val.id};
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.queryBigEvent(ids,'delete').then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.getHistoryList();
        });
      }).catch(() => {
        console.log('已取消')
      });
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
      this.getHistoryList();
    },
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.export {
  float: right;
}
</style>
