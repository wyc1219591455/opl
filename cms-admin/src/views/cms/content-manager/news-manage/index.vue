<template>
  <div class="app-container">
    <basic-container>
      <!--搜索部分-->
      <el-form :inline="true" :model="searchForm" ref="searchForm" label-width="80px">
        <el-form-item>
          <el-input v-model.trim="searchForm.title" clearable placeholder="请输入新闻名称" class="st-input-w"></el-input>
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
      <addPage path="/newsManageAdd"></addPage>
      <!--表格部分-->
      <vxe-table
          show-overflow
          auto-resize
          highlight-hover-row
          resizable
          ref="table"
          row-id="id"
          border="inner"
          :start-index="(tablePage.page -1) * tablePage.size"
          :data="tableData">
        <vxe-table-column type="seq" width="60" title="序号"></vxe-table-column>
        <vxe-table-column field="title" title="新闻名称" min-width="100"></vxe-table-column>
        <vxe-table-column field="publisher" title="发布人"></vxe-table-column>
        <!-- <vxe-table-column field="title" title="发布时间" min-width="100"></vxe-table-column> -->
        <vxe-table-column field="language" title="语言" min-width="50">
          <template v-slot:default="{ row }">
            {{ languageText[row.language] }}
          </template>
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
              <el-button type="danger" @click="del(row)" size="mini">删除</el-button>
            </div>
            <div style="display: inline-block;margin-left: 5px">
              <el-dropdown trigger="click">
                <el-button type="primary" size="mini" :disabled="row.buttons | isDisabled">
                  更多操作<i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item v-for="(item, index) in row.buttons" :key="index">
                    <div @click="creat(row, item.language)" v-if="!item.isCreated">新增{{ item.name }}</div>
                  </el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </div>
            <!--<el-button @click="showInfo(row)" size="mini">查看</el-button>-->
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
import api from '@/api/cms/content' // 接口
import {StatusList} from "@/const/constData";
import formWrap from './components/form'
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
        title: "",
        status: "",
      },
      loading: false,
      langDialog: false, //语言编辑弹窗
      title: "编辑新闻",
      type: "edit", // create 创建 edit 编辑 info 查看
      activeData: {}, // 编辑数据
      model: {
        title: null,
        publisher: '',
        publishTime: '',
        sort: 1,
        summary: "",
        status: true,
        thumbnail: "",
        content: "",
        metaKeywords: '',
        metaDescription: '',
        language: '',
        articleCategoryId: null
      },
    }
  },
  created() {
    this.$store.dispatch("getLanguage");
    this.getArticleCategoryList();
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
    // 获取分类列表
    getArticleCategoryList() {
      const sort = 'sort,asc';
      const params = {
        page: this.tablePage.page - 1,
        size: this.tablePage.size,
        title: this.searchForm.title,
        status: this.searchForm.status,
        sort: sort
      };
      api.queryArticleCms(params, 'get').then((res) => {
        this.tableData = res.content;
        this.tablePage.totalElements = res.totalElements;
      })
    },
    // 搜索
    search() {
      this.getArticleCategoryList();
    },
    // 搜索重置
    resetSearch() {
      this.searchForm = {
        name: "",
        active: "",
      }
      this.getArticleCategoryList();
    },
    // 分页发生改变事件 totalElements
    handlePageChange({currentPage, pageSize}) {
      // console.log(`当前页: ${currentPage}`);
      // console.log(`每页 ${pageSize} 条`);
      this.tablePage.page = currentPage;
      this.tablePage.size = pageSize;
      this.getArticleCategoryList();
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
        path: '/newsManageAdd',
        query: {
          data: JSON.stringify(value)
        }
      })
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
        api.queryArticleCms(params, 'delete').then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          });
          this.getArticleCategoryList();
        });
      }).catch(() => {
        console.log('已取消')
      });
    },
    // 保存编辑
    submitBtn() {
      this.$refs.formRef.submitForm('ruleForm');
    },
    editSuccess() {
      this.langDialog = false;
      this.getArticleCategoryList();
    },
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.export {
  float: right;
}
</style>
