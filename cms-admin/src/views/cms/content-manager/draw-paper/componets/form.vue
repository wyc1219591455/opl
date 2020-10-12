<template>
    <el-form ref="form" :model="form" :rules="rules" size="small" label-width="120px">
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="基本信息" name="base">
                <el-form-item label="图纸名称" prop="name">
                    <el-input v-model="form.name" style="width: 450px" placeholder="图纸名称" />
                </el-form-item>
                <el-form-item label="图纸简介" prop="summary">
                    <el-input
                        type="textarea"
                        v-model="form.summary"
                        :rows="2"
                        style="width: 450px"
                        placeholder="图纸简介"
                    />
                </el-form-item>
<!--                <el-form-item label="视频地址" prop="video">-->
<!--                    <el-input v-model="form.video" style="width: 450px" placeholder="视频地址" />-->
<!--                </el-form-item>-->
                <el-form-item label="图纸缩略图">
                    <Upload
                        :action="actionPicUrl"
                        avatar-type="avatar"
                        :image-url="form.thumbnail"
                        :limit="1"
                        @input="uploadThumbnail"
                    ></Upload>
                </el-form-item>
<!--                <el-form-item label="视频图片">-->
<!--                    <upload-->
<!--                        :action="actionPicUrl"-->
<!--                        avatar-type="videoCover"-->
<!--                        :image-url="form.videoCover"-->
<!--                        :limit="1"-->
<!--                        @input="uploadPic"-->
<!--                    ></upload>-->
<!--                </el-form-item>-->
                <el-form-item label="排序" prop="sort">
                    <el-input-number
                        v-model.number="form.sort"
                        :min="0"
                        :max="999"
                        controls-position="right"
                        style="width: 178px;"
                    />
                </el-form-item>
                <el-form-item label="语言" prop="language">
                    <el-select
                        v-model="form.language"
                        :disabled="type==='edit'"
                        placeholder="请选择语言"
                        style="width: 450px"
                    >
                        <el-option
                            v-for="item in allLanguage"
                            :disabled="!item.active"
                            :key="item.language"
                            :label="item.name"
                            :value="item.language"
                        ></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="SEO描述" prop="metaDescription">
                    <el-input
                        type="textarea"
                        v-model="form.metaDescription"
                        :rows="2"
                        style="width: 450px"
                        placeholder="SEO描述"
                    />
                </el-form-item>
                <el-form-item label="SEO关键字" prop="metaKeywords">
                    <el-input v-model="form.metaKeywords" style="width: 450px" placeholder="SEO关键字" />
                </el-form-item>
                <el-form-item label="状态" prop="status">
                    <el-radio v-model="form.status" :label='true'>激活</el-radio>
                    <el-radio v-model="form.status" :label='false'>冻结</el-radio>
                </el-form-item>
            </el-tab-pane>
            <el-tab-pane label="图纸" name="detail">
                <el-col :lg="{span: 24}" :md="{span:24}">
                    <div v-for="item in testList" :key="item.id">
                        <el-form-item :label="item.name" label-width="130px" v-if="item.active">
                            <!-- {{form.drawingTypeMappingList[index] && form.drawingTypeMappingList[index].type}} -->
                            <!-- <Upload2 :value="form.drawingTypeMappingList[index] && form.drawingTypeMappingList[index].url" @input='uploadPaper' :limit="1" :accept="fun(item.type)" :item='item' :action="actionUrl" list-type="paper" ></Upload2> -->
                            <Upload2 v-model="item.url" :limit="1" :accept="fun(item.type)" :item='item' :action="actionUrl" list-type="paper" ></Upload2>
                        </el-form-item>
                    </div>
                </el-col>
            </el-tab-pane>
            <el-tab-pane label="图纸详情" name="content">
                <el-col :lg="{span: 24}" :md="{span:24}">
                    <el-form-item label="图纸详情" prop="content" label-width="100px">
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
        <el-form-item v-if="btnFlag && type != 'edit'">
            <el-button type="primary" @click="submitForm('form')">创建</el-button>
            <el-button @click="resetForm('form')">重置</el-button>
        </el-form-item>
</el-form>
</template>
<script>
import { baseUrl } from "@/config/env";
import Upload from "@/components/jst-components/avatar-upload";
import Upload2 from "@/components/jst-components/paper-upload";
import VueCkeditor from "vue-ckeditor2";
import editorMixins from "@/mixins/editorMixins";
import { mapGetters } from 'vuex';
import { queryPaper, paperTypeNoPage } from '@/api/cms/content';
export default {
    components: {
        Upload,
        VueCkeditor,
        Upload2
    },
    mixins: [editorMixins],
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
    data() {
        return {
            avatar: "avatar",
            actionPicUrl: baseUrl + "api/users/updateAvatar",
            actionUrl: baseUrl + "api/fileUpload/file", // 头像上传地址
            activeName: "base",
            btnFlag: false,
            form: {
                id: null,
                name: null,
                sort: 1,
                summary: "",
                status: true,
                thumbnail: "",
                content: "",
                metaKeywords: '',
                metaDescription: '',
                language: '',
                pdf: '',
                dwg: '',
                step: '',
                drawingTypeMappingList: [
                ]
            },
            paperTypeList: [],
            rules: {
                name: [
                    { required: true, message: "请输入图纸名称", trigger: "blur" }
                ],
                desc: [
                    { required: true, message: "请输入简介", trigger: "blur" }
                ],
                // video: [
                //     { required: true, message: "请输入地址", trigger: "blur" }
                // ],
                language: [
                    { required: true, message: "请选择语言", trigger: "change" }
                ],
                metaKeywords: [
                    { required: true, message: "请输入SEO关键字", trigger: "blur" }
                ],
                metaDescription: [
                    { required: true, message: "请输入SEO描述", trigger: "blur" }
                ],
                status: [
                    { required: true, message: "请选择状态", trigger: "change" }
                ],
                content: [
                    { required: true, message: '请输入详情信息', trigger: 'blur' }
                ],
            },
            cloneList: []
        };
    },
    computed: {
        ...mapGetters([
            'allLanguage'
        ]),
        testList() {
            const paperTypeList = this.paperTypeList.filter((item) => {
                return item.active === true;
            });
            paperTypeList.forEach((item, index) => {
                // item['url'] = '';
                this.$set(item, 'url', '');
                this.form.drawingTypeMappingList.forEach((itm, idx) => {
                    if (item.type === itm.type) {
                        // item['url'] = itm.url || '';
                        this.$set(item, 'url', itm.url);
                    }
                })
            });
            return paperTypeList;
        }
    },
    watch: {
        activeData(val) {
            if (this.type === 'edit') {
                this.form = val;
            }
        }
    },
    created() {
        if (this.type === 'edit') {
           this.form = this.activeData;
        }
        this.getPaperType();
        this.$store.dispatch("getLanguage"); 
    },
    methods: {
        // 视频图片上传
        uploadPic(value) {
            this.form.videoCover = baseUrl + "avatar" + value.avatarPath.split("avatar")[1];
        },
        uploadThumbnail(value) {
            this.form.thumbnail = baseUrl + "avatar" + value.avatarPath.split("avatar")[1];
        },
        handleClick(tab) {
            this.btnFlag = tab.name === 'content';
        },
        // 查询图纸类型
        getPaperType() {
            paperTypeNoPage('', 'get').then((res)=>{
                this.paperTypeList = res;
            });
        },
        fun(type) {
            // 以逗号分隔 上传多种格式图片
            let arr = type.split(',');
            let newData = [];
            arr.map((item) => {
                newData.push(`.${item}`);
            });
            let resultData = newData.join(',');
            return resultData;
        },
        submitForm(form) {
            let message = this.type === 'edit' ? '保存成功' : '新增成功';
            let methodType = this.type === 'edit' ? 'put' : 'post';
            this.form.drawingTypeMappingList = _.cloneDeep(this.testList);
            console.log(this.form, 'form');
            this.$refs[form].validate((valid) => {
                if (valid) {
                    queryPaper(this.form, methodType).then(()=>{
                        this.$message({
                            message,
                            type: 'success'
                        });
                        if (this.type === 'edit') {
                            this.$emit('editSuccess');
                        } else {
                            this.$router.push({
                                path: '/drawList'
                            })
                        }
                    });
                } else {
                    this.$message({
                        message: '请完善必填信息！',
                        type: 'warning'
                    });
                    return false;
                }
            });
        },
        resetForm(form) {
            this.$refs[form].resetFields();
        }
    }
};
</script>
<style>
</style>