<template>
    <div class="main-content">
        <div class="title-box">
            <div class="title-left">文件管理</div>
            <div class="title-right">
                <el-input class="search" style="width:200px" v-model="searchValue" @input="submitFun" placeholder="请输入文件名称"></el-input>
                <el-button v-if="path != '/'" type="primary" icon="el-icon-circle-plus-outline" @click="addFile">新文件夹</el-button>
                <el-button v-if="path != '/'" type="primary" icon="el-icon-upload" @click="upload">上传文件</el-button>
            </div>
        </div>
        <el-row class="tree-box">
            <el-col :span="6" class="left">
                <el-tree
                    class="tree"
                    :data="data"
                    :props="defaultProps"
                    @node-click="handleNodeClick"
                    node-key="lastFolderPath"
                    :default-expanded-keys="[defaultExpanded]"
                >
                </el-tree>
            </el-col>
            <el-col :span="18" class="right">
                <div class="parentBox">
                    <ul class="content-box">
                        <li class="list-item" v-for="(item, index) in contentList" :key="index" @dblclick="doubleClick($event, item)" @click="singleClick($event, item)" @contextmenu.prevent="show($event, item)">
                            <div class="item-bg" @click="chooseActive(item)" :class="[activeFlag === item.name ? 'active': '']" :title="item.name">
                                <svg-icon v-if="item.type === 'dir'" icon-class="flieBox" />
                                <svg-icon v-else icon-class="file-icon" />
                                <p class="file-type" v-if="item.type !='dir'">{{item.fileType}}</p>
                                <p v-if="item.name && item.name.length > 11">{{item.name.substring(0, 11)}}...</p>
                                <p v-else>{{item.name}}</p>
                            </div>
                            <ul v-if="showFlag && activeData.name === item.name" ref="ulBox" class="slef-menu" v-clickoutside="handleClose">
                                <li v-if="item.type !='dir'" @click="download(item)">下载</li>
                                <li v-if="item.fileType ==='png' || item.fileType ==='jpg' || item.fileType ==='gif'" @click="preview($event, item)">预览</li>
                                <li v-if="path != '/'" @click="rename(item)">重命名</li>
                                <li v-if="path != '/'" @click="move(item)">移动</li>
                                <li v-if="item.type !='dir'" @click="copy(item)">复制</li>
                                <li v-if="path != '/'" @click="remove(item)">删除</li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </el-col>
        </el-row>
        <!-- 弹框 -->
        <el-dialog
        :title="dialogTitle"
        :visible.sync="dialogVisible"
        width="40%"
        :before-close="close">
            <div v-if="uploadType ==='upload'" class="test">
                <el-upload
                    ref="upload"
                    class="upload-demo"
                    :action="actionUrl"
                    :headers="{ 'Authorization': token }"
                    multiple
                    :limit="1"
                    :on-success="handleSuccess"
                    :data='uploadData'
                    :auto-upload="false"
                    :file-list="fileList">
                    <!-- <el-button size="small" type="primary">点击上传</el-button> -->
                    <el-button slot="trigger" size="small" type="primary" style="width: 100%">选取文件</el-button>
                </el-upload>
                <!-- <Upload :data='uploadData' :action="actionUrl" :limit="1"></Upload> -->
            </div>
            <div v-else>
                <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
                    <el-form-item label="文件夹名称" prop="fileName">
                        <el-input v-model="ruleForm.fileName" placeholder="请输入文件夹名称"></el-input>
                    </el-form-item>
                </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="confirm('ruleForm')">确 定</el-button>
            </span>
        </el-dialog>
        <!-- 移动 复制弹框 -->
        <el-dialog
            title="提示"
            :visible.sync="moveDialogVisible"
            width="40%"
            :before-close="close">
            <div class="show-choose">选择: <span>{{activeData.name}}</span></div>
            <el-form :model="moveRuleForm" :rules="moveRules" ref="moveRuleForm" label-width="100px">
                <el-form-item label="目的地：" prop="newPath">
                    <treeselect
                        v-model="moveRuleForm.newPath"
                        :options="treeData"
                        noOptionsText='暂无'
                        noResultsText="暂无"
                        style="width: 300px"
                        placeholder="选择文件夹"
                        @input='treeSelectFun'
                        :normalizer="normalizer"
                    />
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button @click="moveDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="optionsFun('moveRuleForm')">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>
<script>
import api from "@/api/cms/file";
import { baseUrl } from '@/config/env';
import { mapGetters } from "vuex";
import Treeselect from '@riophae/vue-treeselect';
import '@riophae/vue-treeselect/dist/vue-treeselect.css';
const clickoutside = {
    // 初始化指令
    // eslint-disable-next-line no-unused-vars
    bind(el, binding, vnode) {
        function documentHandler(e) {
            // 这里判断点击的元素是否是本身，是本身，则返回
            if (el.contains(e.target)) {
                return false;
            }
            // 判断指令中是否绑定了函数
            if (binding.expression) {
                // 如果绑定了函数 则调用那个函数，此处binding.value就是handleClose方法
                binding.value(e);
            }
        }
        // 给当前元素绑定个私有变量，方便在unbind中可以解除事件监听
        el.__vueClickOutside__ = documentHandler;
        document.addEventListener("click", documentHandler);
    },
    update() {},
    // eslint-disable-next-line no-unused-vars
    unbind(el, binding) {
        // 解除事件监听
        document.removeEventListener("click", el.__vueClickOutside__);
        delete el.__vueClickOutside__;
    },
};
let time = null;  //  在这里定义time 为null
export default {
    components: {
        Treeselect
    },
    data() {
        return {
            searchValue:'',
            dialogTitle: '',
            optionsType: '',
            expandPath: '/', //中间值设置默认展开
            defaultExpanded: '/', //默认展开
            activeFlag: '',
            eventFlag: '',
            fileList: [],
            dialogVisible: false,
            moveDialogVisible: false,
            showFlag: false,
            data: [],
            defaultProps: {
                children: "children",
                label: "folderName",
            },
            content: "",
            path: '/',
            contentList: [],
            ruleForm: {
                fileName: '',
            },
            moveRuleForm: {
                newPath: null,
            },
            oldFileName: '',
            uploadType: '',
            actionUrl: baseUrl + 'api/staticFileManage/upload',
            uploadData: {
                destination: ''
            },
            rules: {
                fileName: [
                    { required: true, message: '请输入文件夹名称', trigger: 'blur' }
                ]
            },
            moveRules: {
                newPath: [
                    { required: true, message: '请选择目的地', trigger: 'change' }
                ]
            },
            activeData: {
            },
            treeData: [],
            copyContentList: []
        };
    },
    computed: {
        ...mapGetters(["token"]),
    },
    created() {
        this.uploadData.destination = this.path;
        this.getFileList();
        this.getFileData();
    },
    watch: {
        path(val) {
            this.uploadData.destination = val;
        },
    },
    directives: { clickoutside },
    methods: {
        // 搜索功能
        submitFun() {
            let search = this.searchValue;
            // if (search === '' || search == null) {
            //     // alert(1)
            //     this.contentList = this.copyContentList;
            //     return;
            // }
            this.contentList = this.copyContentList;
            this.contentList = this.contentList.filter((item)=> {
                let searchField = { name: item.name};
                return Object.keys(searchField).some((key)=> {
                    return String(item[key]).toLowerCase().indexOf(search) > -1;
                });
            });
        },
        // 设置树形菜单显示字段
        normalizer(node){
        //去掉children=[]的children属性
            if (node.children && !node.children.length) {
                delete node.children;
            }
            return {
                id: node.lastFolderPath,
                //将name转换成必填的label键
                label: node.folderName,
                children: node.children,
            }
        },
        treeSelectFun() {
            this.$refs.moveRuleForm.validateField('newPath');
        },
        // 获取树形菜单
        getFileList() {
            const parmas = {
                action: "list",
                path: '/',
            };
            api.getFile(parmas, "get").then((res) => {
                this.data = res;
            });
        },
        // 获取文件夹下的内容
        getFileData() {
            const parmas = {
                action: "list",
                path: this.path,
            };
            api.getFileContent(parmas, "post").then((res) => {
                this.contentList = res.result;
                this.copyContentList = res.result;
            });
        },
        // 树形菜单点击事件
        handleNodeClick(data) {
            this.defaultExpanded = '/';
            if (data.lastFolderPath === '/') {
                this.path = `${data.lastFolderPath}`;
            } else {
                this.path = `/${data.lastFolderPath}`;
            }
            this.expandPath = data.lastFolderPath;
            this.getFileData();
        },
        // 新建文件夹
        addFile() {
            this.ruleForm.fileName = '';
            this.dialogTitle = '新建文件夹';
            this.uploadType = 'file';
            this.dialogVisible = true;
        },
        // 上传文件
        upload() {
            this.dialogTitle = '上传文件';
            this.uploadType = 'upload';
            this.dialogVisible = true;
        },
        // 鼠标右键弹框事件
        show(e, item) {
            this.activeData = item;
            this.eventFlag = 'rightClick';
            this.showFlag = true;
            this.moveRuleForm.newPath = null;
            e.stopPropagation();
        },
        // 鼠标点击除鼠标右键弹框以外事件
        handleClose() {
            this.showFlag = false;
        },
         // 单击
        singleClick(e) {
            if (this.eventFlag === 'rightClick') {
                e.stopPropagation();
                clearTimeout(time);  //首先清除计时器
                time = setTimeout(() => {
                    e.stopPropagation();
                    // alert('单击');
                }, 300);   //大概时间300ms
            }
        },
        // 双击
        doubleClick($event, item) {
            // console.log(item);
            clearTimeout(time);  //清除
            // alert('双击');
            if (item.type === 'dir') {
                this.path = item.filePath === '/' ? `${item.filePath}` : `/${item.filePath}`;
                this.getFileData();
            } else if(item.fileType === 'jpg' || item.fileType === 'png' || item.fileType === 'gif') {
                this.$showImg($event, baseUrl + item.filePath);
            }
        },
        // 文件内容单击选中事件
        chooseActive(item) {
            this.activeFlag = item.name;
        },
        // 确定提交
        confirm(formName) {
            if (this.uploadType === 'file') {
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const parmas = {
                            action: "createFolder",
                            newPath: `/${this.path}/${this.ruleForm.fileName}`,
                        };
                        api.creatFile(parmas, "post").then(() => {
                            this.dialogVisible = false;
                            this.getFileList();
                            this.getFileData();
                            // this.$refs[formName].resetFields();
                            // 设置新建文件夹后 展示到当前文件夹
                            this.defaultExpanded = this.expandPath;
                        });
                    } else {
                        console.log('error submit!!');
                        return false;
                    }
                });
                
            } else if(this.uploadType === 'rename') {
                const parmas = {
                    action: "rename",
                    item: this.handlePath(this.oldFileName),
                    newItemPath: this.handlePath(this.ruleForm.fileName),
                };
                // console.log(this.handlePath(this.oldFileName));
                // console.log(parmas);
                // return;
                api.renameFile(parmas, "post").then(() => {
                    this.dialogVisible = false;
                    this.defaultExpanded = this.expandPath;
                    this.$message.success('修改成功！');
                    this.getFileList();
                    this.getFileData();
                });
            }
            else {
                this.$refs.upload.submit();
            }
        },
        // 上传成功
        handleSuccess(res) {
            if(res.code === 200){
                this.$message.success('上传成功！');
                this.$refs['upload'].clearFiles();
                this.dialogVisible = false;
                this.getFileData();
            }else {
                this.$message.error('上传失败！');
            }
        },
        // 下载
        download(data) {
            this.eventFlag = 'click';
            var request = new XMLHttpRequest();
            request.responseType = "blob";
            request.open("GET", baseUrl + data.filePath);
            request.onload = function() {
                var url = window.URL.createObjectURL(this.response);
                var a = document.createElement("a");
                document.body.appendChild(a);
                a.href = url;
                a.download = data.name;
                a.click();
            }
            request.send();
        },
        // 预览
        preview($event, item) {
            this.eventFlag = 'click';
            this.$showImg($event, baseUrl + item.filePath);
        },
        // 重命名
        rename(item) {
            this.showFlag = false;
            this.eventFlag = 'click';
            this.uploadType = 'rename';
            this.dialogVisible = true;
            this.dialogTitle = '重命名';
            this.oldFileName = item.name;
            this.ruleForm.fileName = item.name;
        },
        // 移动
        move() {
            this.optionsType = 'move';
            this.showFlag = false;
            this.moveDialogVisible = true;
            this.treeData = JSON.parse(JSON.stringify(this.data[0].children));
            this.getTreeData(this.treeData);
        },
        // 复制
        copy() {
            this.optionsType = 'copy';
            this.showFlag = false;
            this.moveDialogVisible = true;
            this.treeData = JSON.parse(JSON.stringify(this.data[0].children));
            this.getTreeData(this.treeData);
        },
        getTreeData(treeData) {
            // let value = treeData[0].children;
            console.log(treeData);
            treeData.forEach((ele, index) => {
                if (this.activeData.type === "dir" && ele.lastFolderPath === this.activeData.filePath) {
                    treeData.splice(index, 1);
                }
                // 判断有子元素,并且子元素的长度大于0就再次调用自身
                if (ele.children && ele.children.length > 0) {
                    this.getTreeData(ele.children);
                }
            });
            this.treeData = treeData;
        },
        optionsFun(formName) {
            let parmas = {};
            let apiUrl = '';
            if (this.optionsType === 'move') {
                parmas = {
                    action: "move",
                    items: [this.handlePath(this.activeData.name)],
                    newPath: this.moveRuleForm.newPath
                };
                apiUrl = api.moveFile;
            } else if (this.optionsType === 'copy') {
                parmas = {
                    action: "copy",
                    items: [this.handlePath(this.activeData.name)],
                    newPath: this.moveRuleForm.newPath,
                    singleFilename: this.activeData.name
                };
                apiUrl = api.copyFile;
            }
            console.log(parmas);
            // return;
            // const parmas = {
            //     action: "move",
            //     items: [this.handlePath(this.activeData.name)],
            //     newPath: this.moveRuleForm.newPath
            // };
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    apiUrl(parmas, "post").then(() => {
                        this.moveDialogVisible = false;
                        this.defaultExpanded = this.expandPath;
                        this.getFileList();
                        this.getFileData();
                        let message = this.optionsType === 'move' ? '移动成功！' : '复制成功！';
                        this.$message.success(message);
                    });
                } else {
                    console.log('error submit!!');
                    return false;
                }
            });
        },
        // 删除
        remove(item) {
            this.eventFlag = 'click';
            const parmas = {
                action: "remove",
                items: [this.handlePath(item.name)]
            };
            // return;
            this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(() => {
                api.removeFile(parmas, "post").then((res) => {
                // this.data = res;
                    this.showFlag = false;
                    // 删除文件夹后 展示到当前文件夹
                    this.defaultExpanded = this.expandPath;
                    this.getFileList();
                    this.getFileData();
                    this.$message({
                        type: 'success',
                        message: '删除成功!'
                    });
                });
            }).catch(() => {
                this.$message({
                    type: 'info',
                    message: '已取消删除'
                });          
            });
        },
        // 弹框关闭事件
        close() {
            this.dialogVisible = false;
        },
        // 处理文件路径
        handlePath(data) {
            let value = '';
            if (this.path === '/') {
                value = `${this.path}${data}`;
            } else {
                value = `${this.path}/${data}`;
            }
            return value;
        }
    },
};
</script>

<style lang="scss" scoped>
/deep/.el-upload {
    width: 100%!important;
}
.show-choose {
    font-size: 12px;
    padding-left: 41px;
    margin-bottom: 15px;
    span {
        margin-left: 30px;
    }
}
ul {
    margin: 0;
    padding: 0;
    li {
        list-style: none;
    }
}
.main-content {
    height: 100%;
    .tree-box {
        height: 100%;
        &:after{
            display:block;
            content:'';
            clear: both;
            height:0;
            overflow:hidden
        }
    }
}
.right {
    padding-left: 15px;
    height: 90%;
    overflow-y: scroll;
    .parentBox {
        height: 80% ;
        .content-box {
            position: relative;
            width: 100%;
            display: flex;
            display: -webkit-flex;
            flex-direction: row;
            flex-wrap: wrap;
            .list-item {
                position: relative;
                margin: 10px;
                width: 150px;
                height: 120px;
                text-align: center;
                cursor: pointer;
                //firefox 
                -moz-user-select: none;
                //chrome、safari
                -webkit-user-select: none;
                //ie
                -ms-user-select: none;
                .item-bg {
                    padding: 5px 20px;
                    .svg-icon {
                        width: 5em;
                        height: 5em;
                    }
                }
                .file-type {
                    color: #ffffff;
                    position: absolute;
                    top: 16px;
                    left: 50%;
                    transform: translateX(-50%);
                    text-transform: uppercase;
                }
                .slef-menu {
                    z-index: 999;
                    margin: 0;
                    padding: 0;
                    position: absolute;
                    top: 40px;
                    left: 40px;
                    width: 160px;
                    // height: 220px;
                    box-shadow: 0 1px 4px rgba(0, 0, 0, 0.3);
                    background: #ffffff;
                    border-radius: 6px;
                    li {
                        height: 37px;
                        line-height: 37px;
                        padding: 6px 20px;
                        cursor: pointer;
                        transition: all 0.3s;
                        text-align: left;
                        &:hover {
                            background: #eee;
                        }
                    }
                }
            }
            .active {
                background: #e6f7ff;
                color: #3AA1FF!important
            }
        }
    }
}
.main-content {
    box-sizing: border-box;
    width: 99%;
    margin: 10px;
    background: #ffffff;
    .title-box {
        display: flex;
        align-items: center;
        height: 46px;
        border-bottom: 1px solid #ccc;
        justify-content: space-between;
        padding: 0 10px;
        .title-right {
            display: flex;
            .search {
                margin-right: 20px;
            }
        }
    }
    .tree-box {
        padding: 0 10px;
    }
}
.left {
    border-right: 1px solid #ccc;
    height: 90%;
}
.svg-icon {
    margin-right: 0;
}
.tree {
    /*/ /默认图旋转90度 动画取消*/
    /deep/ .el-tree-node__expand-icon.expanded {
        -webkit-transform: rotate(0deg);
        transform: rotate(0deg);
    }
    /*/ / 收起*/
    /deep/ .el-icon-caret-right:before {
        font-size: 22px;
        content: "\e78a"; /*具体的图标*/
    }
    /*/ / 展开*/
    /deep/ .el-tree-node__expand-icon.expanded.el-icon-caret-right:before {
        font-size: 22px;
        content: "\e784"; /*具体的图标*/
    }
    /deep/.el-tree-node__expand-icon.is-leaf {
        color: #666!important;
    }
}
</style>