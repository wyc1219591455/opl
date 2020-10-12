<template>
  <div>
    <basic-container>
      <!--个人信息页面-->
      <div class="info-body">
        <!--左侧导航-->
        <el-row>
          <el-col :span="4">
            <div class="left-nav">
              <el-menu
                      default-active="1"
                      class="el-menu-vertical-demo"
                      @select="selectIndex">
                <el-menu-item index="1">
                  <span class="spot"></span>
                  <span slot="title">基本信息</span>
                </el-menu-item>
                <el-menu-item index="2">
                  <span class="spot"></span>
                  <span slot="title">修改密码</span>
                </el-menu-item>
              </el-menu>
            </div>
          </el-col>
          <el-col :span="20">
            <!--右侧内容-->
            <div class="right-content">
              <!--基本信息start-->
              <div class="basic-msg" v-if="basicShow==1">
                <basic-block title="基本信息">
                  <div class="right-content-list">
                    <el-form ref="userForm" :model="userForm" :rules="rules" label-width="80px">
                      <el-form-item label="用户名:">
                        <!--<el-input v-model="form.userName"></el-input>-->
                        <div>{{ userForm.username }}</div>
                      </el-form-item>
                      <el-form-item label="用户头像:">
                        <upload
                                :action="actionUrl"
                                :avatar-type="avatar"
                                :image-url="imageUrl"
                                :limit="1"
                                @input="avatarDetail">
                        </upload>
                      </el-form-item>
                      <el-form-item label="昵称:" prop="nickName">
                        <el-input v-model="userForm.nickName"></el-input>
                      </el-form-item>
                      <el-form-item label="性别:" prop="gender">
                        <el-radio-group v-model="userForm.gender">
                          <el-radio label="男">男</el-radio>
                          <el-radio label="女">女</el-radio>
                        </el-radio-group>
                      </el-form-item>
                      <!--<el-form-item label="个人简介:">-->
                      <!--<el-input type="textarea" rows="5" v-model="userForm.personal"></el-input>-->
                      <!--</el-form-item>-->
                      <el-form-item label="所属部门:">
                        <div>{{ userForm.dept.name }}</div>
                      </el-form-item>
                      <el-form-item>
                        <el-button type="primary" @click="onSubmit('userForm')">保存</el-button>
                        <el-button @click="onReset">重置</el-button>
                      </el-form-item>
                    </el-form>
                  </div>
                </basic-block>
              </div>
              <!--基本信息end-->
              <!--安全设置start-->
              <div class="security-setting" v-if="basicShow==2">
                <Xpassword></Xpassword>
              </div>
              <!--安全设置end-->
            </div>
          </el-col>
        </el-row>
      </div>
    </basic-container>
  </div>
</template>

<script>
  // import option from "@/const/user/info";
  // import { isMobile, isEmail } from '@/util/validate'
  // import { mapGetters } from "vuex";
  import { baseUrl } from '@/config/env';
  import { userDetail,updateUserInfo } from '@/api/user'
  import Upload from '../../components/jst-components/avatar-upload'
  import Xpassword from './x-password'

  export default {
    components:{
      Upload,Xpassword
    },
    data() {
      return {
        // type: "info",
        loading: false,
        formLabelWidth: '120px',
        userForm: {
          dept: {}
        },// 用户信息
        basicShow:1,
        avatar: 'avatar',
        imageUrl: '',
        actionUrl: baseUrl + 'api/users/updateAvatar',// 头像上传地址
        avatarUrl:{},
        rules: {
          nickName: [
            { required: true, message: '请填写昵称', trigger: 'blur' }
          ],
          gender: [
            { required: true, message: '请选择性别', trigger: 'blur' }
          ],
        }
      };
    },
    created() {
      // this.userForm = this.userInfo.user;
      this.getUserInfo();
    },
    // 获取用户信息
    computed: {
      // ...mapGetters([
      //   "userInfo",
      // ])
    },
    methods: {
      // 获取用户信息
      getUserInfo(){
        userDetail().then((res) => {
          this.userForm = res.data.data.user;
          this.userForm.dept.name = res.data.data.user.dept.name;
          this.imageUrl = baseUrl + 'avatar' + this.userForm.avatarPath.split("avatar")[1];
          this.$store.dispatch('updateUserInfo', this.userForm);
        })
      },
      // 头像上传
      avatarDetail(value) {
        this.avatarUrl = value;
        // this.userForm.avatarPath = this.avatarUrl.avatarPath;
        this.imageUrl = baseUrl + 'avatar' + this.avatarUrl.avatarPath.split("avatar")[1];

      },
      selectIndex(index) {
        this.basicShow = index;
      },
      // 保存用户信息
      onSubmit(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            let data = {
              id: this.userForm.id,
              avatarName: this.avatarUrl.avatarName,
              avatarPath: this.avatarUrl.avatarPath,
              nickName: this.userForm.nickName,
              gender: this.userForm.gender
            };
            updateUserInfo(data).then(() => {
              this.$message({
                message: '修改成功！',
                type: 'success'
              });
              this.getUserInfo();
            })
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      // 重置用户信息
      onReset(){
        this.getUserInfo()
      },
      goPassword(){
        this.$router.push({ path: "/x-password" });
      },
    }
  }
</script>

<style lang="scss">
   @import "../../styles/jst-info";
</style>
