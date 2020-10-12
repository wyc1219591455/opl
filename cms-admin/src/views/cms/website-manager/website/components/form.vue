<template>
  <el-form :model="form" :rules="rules" ref="ruleForm" label-width="100px">
    <el-form-item label="网站logo:" prop="logo">
      <upload
          :action="actionUrl"
          :avatar-type="avatar"
          :image-url="form.logo"
          :limit="1"
          @input="logoDetail">
      </upload>
    </el-form-item>
    <el-form-item label="网站名称" prop="name">
      <el-input v-model="form.name" placeholder="请输入网站名称" style="width: 300px"></el-input>
    </el-form-item>
    <el-form-item label="网站地址" prop="site">
      <el-input v-model="form.site" placeholder="请输入网站地址" style="width: 300px"></el-input>
    </el-form-item>
    <el-form-item label="网站标题" prop="title">
      <el-input v-model="form.title" placeholder="请输入网站标题" style="width: 300px"></el-input>
    </el-form-item>
    <el-form-item label="网站副标题" prop="subTitle">
      <el-input v-model="form.subTitle" placeholder="请输入网站副标题" style="width: 300px"></el-input>
    </el-form-item>
    <el-form-item label="语言" prop="language">
      <el-select v-model="form.language" placeholder="请选择网站语言" style="width: 300px">
        <el-option
            v-for="item in allLanguage"
            :disabled="!item.active"
            :key="item.language"
            :label="item.name"
            :value="item.language">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="SEO描述" prop="seoDescription">
      <el-input
          type="textarea"
          :rows="3"
          style="width: 300px"
          placeholder="请输入SEO描述"
          v-model="form.seoDescription">
      </el-input>
    </el-form-item>
    <el-form-item label="SEO关键字" prop="seoKeywords">
      <el-input v-model="form.seoKeywords" placeholder="请输入SEO关键字" style="width: 300px"></el-input>
    </el-form-item>
    <el-form-item label="网站备案号" prop="ipcNo">
      <el-input v-model="form.ipcNo" placeholder="请输入网站备案号" style="width: 300px"></el-input>
    </el-form-item>
    <el-form-item label="版权信息" prop="copyright">
      <el-input v-model="form.copyright" placeholder="请输入版权信息" style="width: 300px"></el-input>
    </el-form-item>
    <el-form-item v-if="type != 'edit'">
      <el-button type="primary" @click="submitForm('ruleForm')">创建</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {baseUrl} from '@/config/env';
import Upload from '@/components/jst-components/avatar-upload';
import {getLanguageList, edit, getLanguageAll} from '@/api/cms/website';
import {mapGetters} from 'vuex';

export default {
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
  components: {
    Upload
  },
  watch: {
    activeData(val) {
      if (this.type === 'edit') {
        this.form = val;
      }
    }
  },
  data() {
    return {
      avatar: 'avatar',
      actionUrl: baseUrl + 'api/users/updateAvatar',// 头像上传地址
      rules: {
        logo: [
          {required: true, message: '请上传logo', trigger: 'change'},
        ],
        site: [
          {required: true, message: '请输入网站地址', trigger: 'blur'},
        ],
        name: [
          {required: true, message: '请输入网站名称', trigger: 'blur'},
        ],
        title: [
          {required: true, message: '请输入网站标题', trigger: 'blur'},
        ],
        subTitle: [
          {required: true, message: '请输入网站副标题', trigger: 'blur'},
        ],
        language: [
          {required: true, message: '请选择语言', trigger: 'change'},
        ],
        seoKeywords: [
          {required: true, message: '请输入SEO关键字', trigger: 'blur'},
        ],
        seoDescription: [
          {required: true, message: '请输入SEO描述', trigger: 'blur'},
        ],
        ipcNo: [
          {required: true, message: '请输入备案号', trigger: 'blur'},
        ],
        copyright: [
          {required: true, message: '请输入版权信息', trigger: 'blur'},
        ],
      },
      form: {
        logo: '',
        name: '',
        title: '',
        site: '',
        subTitle: '',
        language: '',
        seoKeywords: '',
        seoDescription: '',
        ipcNo: '',
        copyright: ''
      },
      languageList: []
    }
  },
  computed: {
    ...mapGetters([
      'allLanguage'
    ]),
  },
  created() {
    if (this.type === 'edit') {
      this.form = this.activeData;
    }
    this.$store.dispatch("getLanguage");
  },
  methods: {
    // logo上传
    logoDetail(value) {
      this.form.logo = baseUrl + 'avatar' + value.avatarPath.split("avatar")[1];
      this.$refs.ruleForm.validateField('logo');
    },
    submitForm(formName) {
      let message = this.type === 'edit' ? '保存成功' : '新增成功';
      let methodType = this.type === 'edit' ? 'put' : 'post';
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var pattern = /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/;
          if (pattern.test(this.form.site) === true) {
            edit(this.form, methodType).then(() => {
              this.$message({
                message,
                type: 'success'
              });
              if (this.type === 'edit') {
                this.$emit('editSuccess');
              } else {
                this.$router.push({
                  path: '/websiteList'
                })
              }
            });
          }else {
            this.$message.error('请输入正确的网站地址');
          }
        } else {
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 获取未使用的语言
    getLanguage() {
      if (this.type === 'edit') {
        getLanguageAll().then((res) => {
          this.languageList = res.content || [];
        });
      } else {
        getLanguageList().then((res) => {
          this.languageList = res || [];
        });
      }

    },
  }

}
</script>

<style>

</style>