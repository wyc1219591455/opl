<template>
  <el-form ref="ruleForm" :model="form" :rules="rules" size="small" label-width="120px">
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="基本信息" name="base">
        <el-form-item label="新闻名称" prop="title">
          <el-input v-model="form.title" style="max-width: 450px" placeholder="请输入新闻名称"/>
        </el-form-item>
        <el-form-item label="发布人" prop="publisher">
          <el-input v-model="form.publisher" style="max-width: 450px" placeholder="请输入发布人"/>
        </el-form-item>
        <el-form-item label="新闻简介" prop="summary">
          <el-input
              type="textarea"
              v-model="form.summary"
              :rows="2"
              style="max-width: 450px"
              placeholder="请输入新闻简介"
          />
        </el-form-item>
        <el-form-item label="发布时间" prop="publishTime">
          <el-date-picker
              v-model="form.publishTime"
              type="datetime"
              value-format="yyyy-MM-dd HH:mm:ss"
              placeholder="选择日期时间"
              :picker-options="pickerOptions"
              default-time="12:00:00"
              style="max-width: 450px"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number v-model.trim="form.sort" :max="999" placeholder="请输入"></el-input-number>
        </el-form-item>
        <el-form-item label="语言" prop="language">
          <el-select
              v-model="form.language"
              placeholder="请选择语言"
              style="max-width: 450px"
              :disabled="queryData.flag"
          >
            <el-option
                v-for="item in allLanguage"
                :disabled="!item.active"
                :key="item.language"
                :label="item.name"
                :value="item.language"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="分类" prop="articleCategoryId">
          <treeselect
              v-model="form.articleCategoryId"
              :options="menus"
              noOptionsText="暂无分类"
              noResultsText="暂无结果"
              style="max-width: 450px;"
              placeholder="选择分类"
              :normalizer="normalizer"
          />
        </el-form-item>
        <el-form-item label="新闻缩略图" prop="thumbnail">
          <upload
              :action="actionUrl"
              :avatar-type="avatar"
              :image-url="form.thumbnail"
              v-model="form.thumbnail"
              :limit="1"
              @input="uploadThumbnail"
          ></upload>
        </el-form-item>
        <el-form-item label="视频上传" prop="Video">  
            <!-- action必选参数, 上传的地址 -->
          <div class="uploader-video">  
            <span @click="delVideo" v-if="form.video !='' && videoFlag == false">
              <i class="el-icon-close del-icon"></i>
            </span>
            <el-upload
              v-if="form.video =='' && videoFlag == false"
              :headers="{ 'Authorization': token }" 
              :action="actionUrl" 
              accept="video/*"
              :show-file-list="false" 
              :on-success="handleVideoSuccess" 
              :on-progress="uploadVideoProcess">
              <i class="el-icon-plus video-icon"></i>
            </el-upload>
            <el-progress v-if="videoFlag == true" type="circle" :percentage="videoUploadPercent" style="margin-top:30px;"></el-progress>
            <video v-if="form.video !='' && videoFlag == false" :src="form.video" class="video" controls="controls">您的浏览器不支持视频播放</video>
            <P class="text">请保证视频格式正确，且不超过10M</P>
          </div>
        </el-form-item>
        <el-form-item label="SEO描述" prop="metaDescription">
          <el-input
              type="textarea"
              v-model="form.metaDescription"
              :rows="2"
              style="max-width: 450px"
              placeholder="SEO描述"
          />
        </el-form-item>
        <el-form-item label="SEO关键字" prop="metaKeywords">
          <el-input v-model="form.metaKeywords" style="max-width: 450px" placeholder="SEO关键字"/>
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio v-model="form.status" :label="true">激活</el-radio>
          <el-radio v-model="form.status" :label="false">冻结</el-radio>
        </el-form-item>
      </el-tab-pane>
      <el-tab-pane label="新闻详情" name="detail">
        <el-col :lg="{span: 24}" :md="{span:24}">
          <el-form-item label="新闻详情" prop="content" label-width="100px">
            <vue-ckeditor
                v-if="refleshEditor"
                v-model="form.content"
                :config="config"
                @blur="onBlur($event)"
                @focus="onFocus($event)"
                @contentDom="onContentDom($event)"
                @dialogDefinition="onDialogDefinition($event)"
                @fileUploadRequest="onFileUploadRequest($event)"
                @fileUploadResponse="onFileUploadResponse($event)"
            ></vue-ckeditor>
          </el-form-item>
        </el-col>
      </el-tab-pane>
    </el-tabs>
    <el-form-item v-if="type != 'edit' && btnFlag">
      <el-button type="primary" @click="submitForm('ruleForm')">创建</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>

import {baseUrl} from '@/config/env';
import api from '@/api/cms/content';
import Upload from '@/components/jst-components/avatar-upload';
import VueCkeditor from "vue-ckeditor2";
import Treeselect from '@riophae/vue-treeselect';
import '@riophae/vue-treeselect/dist/vue-treeselect.css';
import editorMixins from "@/mixins/editorMixins";
import {mapGetters} from 'vuex';

export default {
  name: "form-wrap",
  props: {
    activeData: {
      type: Object,
      default: () => {
        return {};
      }
    },
    type: {
      type: String,
      default: ''
    },
  },
  mixins: [editorMixins],
  components: {
    Upload,
    VueCkeditor,
    Treeselect
  },
  watch: {
    activeData(val) {
      if (this.type === 'edit') {
        this.form = val;
      }
    },
  },
  data() {
    return {
      videoFlag: false,
      uploadType: 'video/*',
      videoUploadPercent: 0,
      menus: [],
      activeName: "base",
      btnFlag: false,
      uploadUrl: baseUrl + 'admin/file/upload',
      form: {
        id: null,
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
        articleCategoryId: null,
        slug: '',
        video: ''
      },
      queryData: {},
      rules: {
        title: [
          {required: true, message: "请输入新闻名称", trigger: "blur"}
        ],
        publisher: [
          {required: true, message: "请输入发布人", trigger: "blur"}
        ],
        publishTime: [
          {required: true, message: "请输入发布时间", trigger: "blur"}
        ],
        language: [
          {required: true, message: "请选择语言", trigger: "change"}
        ],
        metaKeywords: [
          {required: true, message: "请输入SEO关键字", trigger: "blur"}
        ],
        metaDescription: [
          {required: true, message: "请输入SEO描述", trigger: "blur"}
        ],
        status: [
          {required: true, message: "请选择状态", trigger: "change"}
        ],
        thumbnail: [
          {required: true, message: '请上传图片', trigger: 'blur'}
        ],
        content: [
          {required: true, message: '请输入详情信息', trigger: 'blur'}
        ],
      },
      avatar: 'avatar',
      imageUrl: '',
      actionUrl: baseUrl + 'api/users/updateAvatar',// 头像上传地址
      avatarUrl: {},
      pickerOptions:{
        disabledDate(time) {
          return time.getTime() > Date.now();
        },
      }
    }
  },
  created() {
    this.queryData = this.$route.query.data ? JSON.parse(this.$route.query.data) : {};
    if (this.type === 'edit') {
      this.queryData.flag = true;
      this.form = this.activeData;
      this.form.thumbnail = baseUrl + 'avatar' + this.form.thumbnail.split("avatar")[1];
    } else if (this.queryData && this.queryData.flag) {
      this.form.language = this.queryData.language;
      this.form.articleCategoryId = this.queryData.articleCategoryId;
    }
    this.$store.dispatch("getLanguage");
    this.getTreeMenus();
  },
  computed: {
    ...mapGetters([
      'allLanguage'
    ]),
    ...mapGetters(["token"])
  },
  methods: {
    uploadVideoProcess(event, file, fileList){
        this.videoFlag = true;
        this.videoUploadPercent = parseInt(file.percentage.toFixed(0));
    },
    handleVideoSuccess(res, file) {             
        this.videoFlag = false;
        this.videoUploadPercent = 0;
        if(res.code == 200){
           this.form.video = baseUrl + "avatar" + res.data.avatarPath.split("avatar")[1];
        }else{
            this.$message.error('视频上传失败，请重新上传！');
        }
    },
    // 删除视频
    delVideo() {
      this.videoFlag = false;
      this.form.video = '';
    },
    // 获取树形菜单
    getTreeMenus() {
      api.query('', 'get').then(res => {
        this.menus = res.content;
      });
    },
    // 设置树形菜单显示字段
    normalizer(node) {
      //去掉children=[]的children属性
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.id,
        //将name转换成必填的label键
        label: node.name,
        children: node.children,
        isDisabled: !node.active
      }
    },
    // 选中图标
    selected(name) {
      this.form.icon = name;
      this.$refs.ruleForm.validateField('icon');
    },
    // 头像上传
    uploadThumbnail(value) {
      console.log(value, '头像')
      this.form.thumbnail = baseUrl + "avatar" + value.avatarPath.split("avatar")[1];
      this.$refs.ruleForm.validateField('thumbnail');
    },
    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 保存新增
    submitForm(formName) {
      let message = this.type === 'edit' ? '保存成功' : '新增成功';
      let methodType = this.type === 'edit' ? 'put' : 'post';
      if (this.queryData.flag && this.type != 'edit') {
        this.form.slug = this.queryData.slug;
      }
      this.$refs[formName].validate((valid) => {
        if (valid) {
          api.queryArticleCms(this.form, methodType).then(() => {
            this.$message({
              message,
              type: 'success'
            });
            if (this.type === 'edit') {
              this.$emit('editSuccess');
            } else {
              this.$router.push({
                path: '/newsManageList'
              })
            }
          });
        } else {
          this.$message({
            message: '请完善必填信息！',
            type: 'warning'
          });
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleClick(tab) {
      this.btnFlag = tab.name === 'detail';
    },
  }
}
</script>

<style lang="scss">
.cke_bottom  {
  display: none!important;
}
.video{
   width: 200px;
   height: 200px;
}
.uploader-video {
  width: 200px;
  height: 200px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  position: relative;
  span{
    background: #ccc;
    display: inline-block;
    width: 24px;
    height: 24px;
    border-radius: 50%;
    position: absolute;
    right: -10px;
    top: -10px;
    .del-icon {
      color: #ffffff;
      font-size: 24px;
    }
  }
}
.video-icon {
    font-size: 28px;
    color: #8c939d;
    width: 200px;
    height: 200px;
    line-height: 200px;
    text-align: center;
}
</style>