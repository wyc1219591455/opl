<template>
    <el-form :model="model" :rules="rules" ref="ruleForm" label-width="100px">
        <el-form-item label="Banner标题" prop="title">
            <el-input v-model.trim="model.title" style="width: 300px" placeholder="请输入Banner标题"></el-input>
        </el-form-item>
        <el-form-item label="Banner描述" prop="desc">
            <el-input
                    v-model="model.desc"
                    type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4}"
                    style="width: 300px" placeholder="请输入Banner描述"></el-input>
        </el-form-item>
        <el-form-item label="URL地址" prop="url">
            <el-input v-model.trim="model.url" style="width: 300px" placeholder="请输入URL地址"></el-input>
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
        <el-form-item label="图片上传" prop="img">
            <upload
                :action="actionUrl"
                :avatar-type="avatar"
                :image-url="model.img"
                v-model="model.img"
                :limit="1"
                @input="avatarDetail">
            </upload>
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
    import { baseUrl } from '@/config/env';
    import { addBanner, editBanner } from '@/api/cms/website' // 接口
    import Upload from '@/components/jst-components/avatar-upload';
    import { mapGetters } from 'vuex';
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
        components:{
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
                    title: '',
                    language: '',
                    desc: '',
                    url: '',
                    sort: 1,
                    active: true,
                    img: '',
                },
                rules: {
                    title: [
                        {required: true, message: '请输入Banner标题', trigger: 'blur'}
                    ],
                    desc: [
                        {required: true, message: '请输入Banner描述', trigger: 'blur'}
                    ],
                    url: [
                        {required: true, message: '请输入Banner路径', trigger: 'blur'}
                    ],
                    language: [
                        {required: true, message: '请选择语言', trigger: 'change'}
                    ],
                    active: [
                        {required: true, message: '请选择状态', trigger: 'blur'}
                    ],
                    img: [
                        {required: true, message: '请上传图片', trigger: 'change'}
                    ],
                },
                langList: [],
                avatar: 'avatar',
                imageUrl: '',
                actionUrl: baseUrl + 'api/users/updateAvatar',// 头像上传地址
                avatarUrl:{},
            }
        },
        computed: {
            ...mapGetters([
                'allLanguage'
            ]),
        },
        created(){
            if (this.type === 'edit') {
                this.model = this.activeData;
                this.model.img = baseUrl + 'avatar' + this.model.img.split("avatar")[1];
            }
            this.$store.dispatch("getLanguage");
        },
        methods: {
            // 头像上传
            avatarDetail(value) {
                this.avatarUrl = value;
                this.model.img = baseUrl + 'avatar' + this.avatarUrl.avatarPath.split("avatar")[1];
                this.$refs.ruleForm.validateField('img');
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
                          editBanner(this.model).then(() => {
                            this.$message({
                              message,
                              type: 'success'
                            });
                            if (this.type === 'edit') {
                              this.$emit('editSuccess');
                            }
                          })
                        }else {
                          addBanner(this.model).then(() => {
                            this.$message({
                              message,
                              type: 'success'
                            });
                            if (this.type === 'edit') {
                              this.$emit('editSuccess');
                            }
                            this.closeCurrentTag();
                            this.$router.push('/bannerList')
                          })
                        }
                      }else {
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