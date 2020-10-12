<template>
  <el-form :model="model" :rules="rules" ref="ruleForm" label-width="100px">
    <el-form-item label="logo" prop="logo">
      <upload
          :action="actionUrl"
          :avatar-type="avatar"
          :image-url="model.logo"
          :limit="1"
          @input="avatarDetail">
      </upload>
    </el-form-item>
    <el-form-item label="名称" prop="name">
      <el-input v-model.trim="model.name" style="width: 300px" placeholder="请输入名称"></el-input>
    </el-form-item>
    <el-form-item label="URL地址" prop="url">
      <el-input v-model.trim="model.url" style="width: 300px" placeholder="请输入URL地址"></el-input>
    </el-form-item>
    <el-form-item label="友情链接分类" prop="category">
      <el-select
          v-model="model.category"
          filterable
          allow-create
          default-first-option
          clearable
          placeholder="请选择友情链接分类">
        <el-option
            v-for="value in categoryList"
            :key="value"
            :label="value"
            :value="value">
        </el-option>
      </el-select>
    </el-form-item>

    <el-form-item label="排序" prop="sort">
      <el-input-number v-model.trim="model.sort" :max="999" placeholder="请输入"></el-input-number>
    </el-form-item>
    <el-form-item label="语言" prop="language">
      <el-select v-model="model.language" placeholder="请选择语言">
        <el-option
            v-for="item in allLanguage"
            :disabled="!item.active"
            :key="item.language"
            :label="item.name"
            :value="item.language">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="状态" prop="active">
      <el-radio-group v-model="model.active">
        <el-radio :label="true">激活</el-radio>
        <el-radio :label="false">冻结</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item v-if="type != 'edit'">
      <el-button type="primary" @click="submitForm('ruleForm')">创建</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import {baseUrl} from '@/config/env';
import {addCategory, editFriendLink, getCategoryAll} from '@/api/cms/website' // 接口
import Upload from '@/components/jst-components/avatar-upload';
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
  components: {
    Upload
  },
  watch: {
    activeData(val) {
      if (this.type === 'edit') {
        this.model = val;
      }
    }
  },
  data() {
    return {
      model: {
        logo: '',
        name: '',
        url: '',
        category: '',
        language: '',
        sort: 1,
        active: true,
      },
      rules: {
        logo: [
          {required: true, message: '请上传logo', trigger: 'blur'}
        ],
        name: [
          {required: true, message: '请输入名称', trigger: 'blur'}
        ],
        url: [
          {required: true, message: '请输入URL地址', trigger: 'blur'}
        ],
        language: [
          {required: true, message: '请选择语言', trigger: 'blur'}
        ],
        active: [
          {required: true, message: '请选择状态', trigger: 'blur'}
        ],
      },
      categoryList: [],// 友情链接分类列表
      avatar: 'avatar',
      // imageUrl: '',
      actionUrl: baseUrl + 'api/users/updateAvatar',// 头像上传地址
      avatarUrl: {},
    }
  },
  computed: {
    ...mapGetters([
      'allLanguage'
    ]),
  },
  created() {
    if (this.type === 'edit') {
      this.model = this.activeData;
      this.model.logo = baseUrl + 'avatar' + this.model.logo.split("avatar")[1];
    }
    this.$store.dispatch("getLanguage");
    this.getCategoryList();
  },
  methods: {
    // 获取友情链接分类
    getCategoryList() {
      getCategoryAll().then((res) => {
        this.categoryList = res;
      })
    },
    // 头像上传
    avatarDetail(value) {
      this.avatarUrl = value;
      this.model.logo = baseUrl + 'avatar' + this.avatarUrl.avatarPath.split("avatar")[1];
      this.$refs.ruleForm.validateField('logo');
    },
    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 保存新增
    submitForm(formName) {
      let message = this.type === 'edit' ? '保存成功' : '新增成功';
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var pattern = /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/;
          if (pattern.test(this.model.url) === true) {
            if(this.type === 'edit'){
              editFriendLink(this.model).then(() => {
                this.$message({
                  message,
                  type: 'success'
                });
                if (this.type === 'edit') {
                  this.$emit('editSuccess');
                }
              })
            }else {
              addCategory(this.model).then(() => {
                this.$message({
                  message,
                  type: 'success'
                });
                if (this.type === 'edit') {
                  this.$emit('editSuccess');
                }
                this.closeCurrentTag();
                this.$router.push('/friendsLinkList')
              })
            }
          } else {
            this.$message.error('请输入正确的网站地址');
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
  }
}
</script>

<style scoped>

</style>