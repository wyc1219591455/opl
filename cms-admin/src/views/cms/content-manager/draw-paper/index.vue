<template>
  <div class="app-container">
    <basic-container>
      <!--搜索部分-->
      <el-form :inline="true" :model="searchForm" ref="searchForm" label-width="80px">
        <el-form-item>
          <el-input v-model.trim="searchForm.name" clearable placeholder="请输入图纸名称" class="st-input-w"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="searchForm.status" clearable placeholder="请选择状态" class="st-input-w">
            <el-option v-for="(item, index) in StatusList" :key="index" :label="item.label"
                       :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search()">搜 索</el-button>
          <el-button type="default" @click="resetSearch('searchForm')">重 置</el-button>
        </el-form-item>
      </el-form>
      <addPage path="/drawAdd"></addPage>
      <!--表格部分-->
      <vxe-table
          resizable
          ref="table"
          border="inner"
          row-id="id"
          :start-index="(tablePage.page -1) * tablePage.size"
          :data="tableData">
        <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
        <vxe-table-column field="name" title="图纸名称" min-width="100"></vxe-table-column>
        <vxe-table-column title="缩略图" min-width="100">
          <template v-slot:default="{ row }" style="height: 50px">
            <img style="height: 40px" @click="$showImg($event, row.thumbnail)" :src="row.thumbnail || defaultPic" @error="showDefaultImage"/>
          </template>
        </vxe-table-column>
        <vxe-table-column field="summary" title="简介"></vxe-table-column>
        <vxe-table-column field="language" title="语言" min-width="50">
          <template v-slot:default="{ row }">{{ languageText[row.language] }}</template>
        </vxe-table-column>
        <vxe-table-column field="status" title="状态">
          <template v-slot:default="{ row }">
            <span>{{ row.status | fStatus }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column field="id" title="操作" width="250" fixed="right" align="center">
          <template v-slot:default="{ row }">
            <div style="display: inline-block">
              <el-button type="primary" @click="edit(row)" size="mini">编辑</el-button>
            </div>
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
      <el-dialog :title="title" :visible.sync="langDialog" width="60%">
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
import api from '@/api/cms/content';
import {StatusList} from "@/const/constData";
import formWrap from './componets/form';
import {mapGetters} from 'vuex';
import addPage from '@/components/addPage';

export default {
  name: 'news-manage-list',
  components: {
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
        status: "",
      },
      loading: false,
      langDialog: false,
      title: "编辑图纸",
      type: "edit",
      activeData: {},
      defaultPic: require('@/assets/defaultImg.png')
    }
  },
  created() {
    this.$store.dispatch("getLanguage");
    // this.getArticleCategoryList();
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
    showDefaultImage(event) {
        event.target.src = require('@/assets/defaultImg.png');
    },
    // 获取分类列表
    getList() {
      const params = {
        page: this.tablePage.page - 1,
        size: this.tablePage.size,
        name: this.searchForm.name,
        status: this.searchForm.status
      };
      api.queryPaper(params, 'get').then((res) => {
        this.tableData = res.content;
        this.tablePage.totalElements = res.totalElements;
      })
    },
    // 搜索
    search() {
      this.getList();
    },
    // 搜索重置
    resetSearch() {
      this.searchForm = {
        name: "",
        active: "",
      }
      this.getList();
    },
    // 分页发生改变事件 totalElements
    handlePageChange({currentPage, pageSize}) {
      // console.log(`当前页: ${currentPage}`);
      // console.log(`每页 ${pageSize} 条`);
      this.tablePage.page = currentPage;
      this.tablePage.size = pageSize;
      this.getList();
    },
    // 编辑
    edit(val) {
      this.langDialog = true;
      this.activeData = _.cloneDeep(val);
    },
    // 删除
    del(val) {
      const params = {
        language: val.language,
        slug: val.slug
      };
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        api.queryPaper(params, 'delete').then(() => {
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
    // 保存编辑
    submitBtn() {
      this.$refs.formRef.submitForm('form');
    },
    editSuccess() {
      this.langDialog = false;
      this.getList();
    },
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.export {
  float: right;
}
</style>
