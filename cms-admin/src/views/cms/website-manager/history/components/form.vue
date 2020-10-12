<template>
  <el-form :model="model" :rules="rules" ref="ruleForm" label-width="100px">
    <el-form-item label="事件标题" prop="title">
      <el-input v-model.trim="model.title" style="width: 300px" placeholder="请输入事件标题"></el-input>
    </el-form-item>
    <el-form-item label="事件简介" prop="summary">
      <el-input
          v-model="model.summary"
          type="textarea"
          :autosize="{ minRows: 4}"
          style="width: 300px" placeholder="请输入事件简介"></el-input>
    </el-form-item>
    <el-form-item label="事件时间" prop="date">
      <el-date-picker
          v-model="model.date"
          type="month"
          placeholder="选择月"
          format="yyyy 年 MM 月"
          value-format="yyyy-MM">
      </el-date-picker>
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
import {mapGetters} from 'vuex';
import api from "@/api/cms/website"; // 接口

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
      }
    }
  },
  data() {
    return {
      model: {
        title: '',
        language: '',
        summary: '',
        date: '',
        active: true,
      },
      rules: {
        title: [
          {required: true, message: '请输入历史事记标题', trigger: 'blur'}
        ],
        summary: [
          {required: true, message: '请输入历史事记简介', trigger: 'blur'}
        ],
        language: [
          {required: true, message: '请选择语言', trigger: 'change'}
        ],
        date: [
          {required: true, message: '请选择时事件时间', trigger: 'change'}
        ],
      },
      langList: [],
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
    }
    this.$store.dispatch("getLanguage");
  },
  methods: {
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
          api.queryBigEvent(this.model, methodType).then(()=>{
            this.$message({
              message,
              type: 'success'
            });
            if (this.type === 'edit') {
              this.$emit('editSuccess');
            } else {
              this.$router.push({
                path: '/historyList'
              })
            }
          }).catch(function (error) {
            console.log(error,'error');
          });
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