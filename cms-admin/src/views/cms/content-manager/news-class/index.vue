<template>
  <div class="app-container">
    <basic-container>
      <!--搜索部分-->
      <el-form :inline="true" :model="searchForm" ref="searchForm" label-width="80px">
        <el-form-item>
          <el-input v-model.trim="searchForm.name" clearable placeholder="请输入分类名称" class="st-input-w"></el-input>
        </el-form-item>
        <el-form-item>
          <el-select v-model="searchForm.active" clearable placeholder="请选择状态" class="st-input-w">
            <el-option v-for="(item, index) in StatusList" :key="index" :label="item.label"
                       :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="search()">搜 索</el-button>
          <el-button type="default" @click="resetSearch('searchForm')">重 置</el-button>
        </el-form-item>
      </el-form>
      <addPage path="/newsClassAdd"></addPage>
      <!--表格部分-->
      <vxe-table
          highlight-hover-row
          resizable
          border="inner"
          :tree-config="{iconOpen: 'el-icon-arrow-down', iconClose: 'el-icon-arrow-right'}"
          ref="xTree"
          :data="tableData"
          @toggle-tree-expand="toggleExpandChangeEvent">
        <vxe-table-column field="name" title="分类名称" min-width="100" tree-node></vxe-table-column>
        <vxe-table-column field="desc" title="分类描述" min-width="100"></vxe-table-column>
        <vxe-table-column field="metaDescription" title="SEO描述" min-width="100"></vxe-table-column>
        <vxe-table-column field="language" title="语言" min-width="50">
          <template v-slot:default="{ row }">{{ languageText[row.language] }}</template>
        </vxe-table-column>
        <vxe-table-column field="metaKeywords" title="SEO关键字"></vxe-table-column>
        <vxe-table-column field="sort" title="排序"></vxe-table-column>
        <vxe-table-column field="active" title="状态">
          <template v-slot:default="{ row }">
            <span>{{ row.active | fStatus }}</span>
          </template>
        </vxe-table-column>
        <vxe-table-column field="id" title="操作" width="200" fixed="right" align="center">
          <template v-slot:default="{ row }">
            <div style="display: inline-block">
              <el-button type="primary" @click="edit(row)" size="mini">编辑</el-button>
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
          </template>
        </vxe-table-column>
      </vxe-table>
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
import api from '@/api/cms/content' // 接口
import {StatusList} from "@/const/constData";
import formWrap from './components/form'
import {mapGetters} from 'vuex';
import addPage from '@/components/addPage';

export default {
  name: 'news-class-list',
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
        background: true
      },
      searchForm: {
        name: "",
        active: "",
      },
      loading: false,
      langDialog: false, //语言编辑弹窗
      title: "编辑新闻分类",
      type: "edit", // create 创建 edit 编辑 info 查看
      activeData: {}, // 编辑数据
      model: {
        name: '',
        language: '',
        desc: '',
        metaDescription: '',
        metaKeywords: '',
        sort: 1,
        active: true,
        banner: '',
        parentId: null,
        slug: ''
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
        name: this.searchForm.name,
        active: this.searchForm.active,
        sort: sort
      };
      api.query(params, 'get').then((res) => {
        this.tableData = res.content;
        this.tablePage.totalElements = res.totalElements;
      })
    },
    toggleExpandChangeEvent({row, expanded}) {
      // console.log(row, expanded);
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
    // 新增
    creat(data, language) {
      const value = {
        flag: true,
        language,
        slug: data.slug,
        parentId: data.parentId
      };
      this.$router.push({
        path: '/newsClassAdd',
        query: {
          data: JSON.stringify(value)
        }
      })
    },
    // 编辑
    edit(val) {
      console.log(val);
      this.langDialog = true;
      this.activeData = _.cloneDeep(val);
    },
    // 删除
    // del(val){
    //     const ids = [];
    //     ids.push(val.id);
    //     this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
    //         confirmButtonText: '确定',
    //         cancelButtonText: '取消',
    //         type: 'warning'
    //     }).then(() => {
    //         articleCategoryAll(ids,'del').then(() => {
    //             this.$message({
    //                 type: 'success',
    //                 message: '删除成功!'
    //             });
    //             this.getArticleCategoryList();
    //         });
    //     }).catch(() => {
    //         console.log('已取消')
    //     });
    // },
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

</style>
