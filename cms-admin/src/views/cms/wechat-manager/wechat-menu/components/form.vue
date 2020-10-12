<template>
  <el-form :model="model" :rules="rules" ref="ruleForm" label-width="100px">
    <el-form-item label="微信菜单名称" prop="name">
      <el-input v-model.trim="model.name" style="width: 300px" placeholder="请输入微信菜单名称"></el-input>
    </el-form-item>
    <el-form-item label="菜单级别">
      <el-radio-group v-model="menuType" @change="testChange">
        <el-radio :label="false">一级菜单</el-radio>
        <span v-if="type === 'edit'">
          <el-radio :label="true" v-if="model.parentId === null || model.parentId != 0">二级菜单</el-radio>
        </span>
        <span v-else>
          <el-radio :label="true">一级菜单</el-radio>
        </span>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="菜单分类" prop="parentId" v-if="menuType===true">
      <el-select v-model="model.parentId" style="width: 300px" placeholder="请选择父菜单">
        <el-option
            v-for="item in menuList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="菜单内容">
      <el-radio-group v-model="model.type" @change="change">
        <el-radio label="click">发送消息</el-radio>
        <el-radio label="view">跳转连接</el-radio>
        <el-radio label="media_id">图片上传</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item label="消息内容" prop="content" v-if="model.type==='click'">
      <el-input
          v-model="model.content"
          type="textarea"
          :autosize="{ minRows: 2, maxRows: 4}"
          style="width: 300px" placeholder="请输入消息内容"></el-input>
    </el-form-item>
    <el-form-item label="跳转连接" prop="content" v-if="model.type==='view'">
      <el-input v-model.trim="model.content" style="width: 300px" placeholder="请输入跳转连接"></el-input>
    </el-form-item>
    <el-form-item label="选择素材" prop="content" v-if="model.type==='media_id'">
      <el-select v-model="model.content" style="width: 300px" placeholder="请选择素材">
        <el-option
            v-for="item in MaterialList"
            :key="item.id"
            :label="item.name"
            :value="item.id">
        </el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="排序" prop="sort">
      <el-input-number v-model.trim="model.sort" :max="999" placeholder="请输入"></el-input-number>
    </el-form-item>
    <el-form-item v-if="type != 'edit'">
      <el-button type="primary" @click="submitForm('ruleForm')">创建</el-button>
      <el-button @click="resetForm('ruleForm')">重置</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import api from '@/api/cms/wechat';
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
  components: {},
  watch: {
    activeData(val) {
      if (this.type === 'edit') {
        this.model = val;
        if (this.model.parentId != 0) {
          this.menuType = true;
        } else {
          this.menuType = false;
        }
      }
    },
  },
  data() {
    return {
      menuType: false,
      model: {
        name: '',
        sort: 1,
        parentId: null,// 菜单父级id
        type: 'click', // 菜单内容类型
        content: '', // 回复内容&跳转连接
      },
      queryData: {},
      rules: {
        name: [
          {required: true, message: '请输入微信菜单名称', trigger: 'blur'}
        ],
        parentId: [
          {required: true, message: '请选择父级菜单', trigger: 'change'}
        ],
        content: [
          {required: true, message: '请输入完整内容', trigger: 'change'}
        ],
      },
      menuList: [],// 菜单列表
      MaterialList: [],// 素材列表
    }
  },
  created() {
    if (this.type === 'edit') {
      this.model = this.activeData;
       if (this.model.parentId != 0) {
          this.menuType = true;
        } else {
          this.menuType = false;
        }
      this.queryData.flag = true;
    }
    this.getMenuList();
  },
  methods: {
    testChange(val) {
      if (val) {
        this.model.parentId = null;
      }
    },
    // 获取菜单列表
    getMenuList() {
      api.queryWeChatMenuList('', 'get').then(res => {
        this.menuList = res;
      });
    },
    // 获取图片素材
    getMaterialList() {
      api.queryWeChatMaterial('', 'get').then(res => {
        this.MaterialList = res;
      });
    },
    change(val) {
      if (val === 'media_id') {
        this.getMaterialList();
      }
    },
    // 重置
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    // 保存新增
    submitForm(formName) {
      let message = this.type === 'edit' ? '保存成功' : '新增成功';
      let methodType = this.type === 'edit' ? 'put' : 'post';
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.menuType === false) {
            this.model.parentId = 0;
          }
          // if(this.type === 'edit'){
          //     if(this.menuType === false){
          //         this.model.parentId = null;
          //     }
          // }
          if (this.model.type==='view') {
            var pattern = /^((https|http|ftp|rtsp|mms)?:\/\/)[^\s]+/;
            if(pattern.test(this.model.content) === true){
              api.queryWeChatMenuList(this.model, methodType).then(() => {
                this.$message({
                  message,
                  type: 'success'
                });
                if (this.type === 'edit') {
                  this.$emit('editSuccess');
                } else {
                  this.$router.push({
                    path: '/wechatMenuList'
                  })
                }
              }).catch(error => {
                console.log(error, 'error');
                this.model.parentId = null;
              });
            } else {
              this.$message.error('请输入正确的网站地址');
            }
          } else {
            api.queryWeChatMenuList(this.model, methodType).then(() => {
              this.$message({
                message,
                type: 'success'
              });
              if (this.type === 'edit') {
                this.$emit('editSuccess');
              } else {
                this.$router.push({
                  path: '/wechatMenuList'
                })
              }
            }).catch(error => {
              console.log(error, 'error');
              this.model.parentId = null;
            });
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