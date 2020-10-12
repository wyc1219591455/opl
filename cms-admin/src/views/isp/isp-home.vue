<template>
    <div class="isp-home">
        <basic-block title="常用菜单">
            <el-button type="primary" size="mini" class="edit-btn" @click="editMenu">编辑</el-button>
            <el-row>
                <el-col :span="6" v-for="(item, index) in commonMenuList" :key="item.id">
                    <div class="isp-home-icon-wrap">
                        <div @click="toPage(item)" class="isp-home-menu">
                            <div class="isp-home-icon-1" :class="'icon-bg-' + index">
                                <!-- {{item.icon}} -->
                                <i class="iconfont font" :class="item.icon ? item.icon : 'iconshujuwajue'" ></i>
                            </div>
                            <div class="isp-home-icon-2">
                                {{item.title}}
                            </div>
                        </div>
                    </div>
                </el-col>
            </el-row>
        </basic-block>
        <basic-block>
            <div slot="title">
                <span>操作日志</span>
            </div>
            <!--表格部分-->
            <logs></logs>
        </basic-block>
        <!-- 弹框 -->
        <el-dialog
            title="编辑常用菜单"
            :visible.sync="dialogVisible"
            width="35%"
            >
            <p class="warning">提示:  最多配置四个常用菜单哦!</p>
            <el-transfer
                filterable
                :filter-method="filterMethod"
                filter-placeholder="请输入菜单名称"
                @change='renderFunc'
                :titles="['所有菜单','常用菜单']"
                v-model="value"
                :props="{
                    key: 'id',
                }"
                :data="allMenuList">
            </el-transfer>
            <span slot="footer" class="dialog-footer">
                <el-button @click="dialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="submit">确 定</el-button>
            </span>
        </el-dialog>
    </div>
</template>

<script>
    import tableMixins from "@/mixins/tableMixins";
    import { LanguageList, StatusList } from "@/const/constData";
    import { getMultiConditionalQuery, query, queryAll } from "@/api/isp";
    import logs from '../monitor/log/index';
    export default {
        name: "isp-home",
        mixins: [ tableMixins ],
        components: {
            logs
        },
        data(){
            return {
                value: [],
                filterMethod(query, item) {
                    return item.label.indexOf(query) > -1;
                },
                getList: getMultiConditionalQuery,
                LanguageList: LanguageList,
                StatusList: StatusList,
                dialogVisible: false,
                allMenuList: [],
                commonMenuList: []
            }
        },
        created(){
            this.getMenuList();
            this.getAllList();
        },
        // 组件激活时调用
        activated(){
            // this.findList();
        },
        methods: {
            // 页面跳转
            toPage(data) {
                this.$router.push({
                    path: data.path
                });
            },
            getMenuList() {
                query({}, 'get').then(res => {
                    this.commonMenuList = res.data.data;
                })
            },
            getAllList() {
                queryAll({}, 'get').then(res => {
                    this.allMenuList = res.data.data;
                })
            },
            editMenu() {
                this.value = this.commonMenuList.map(item => {return item.menuId});
                this.dialogVisible = true;
            },
            renderFunc() {
                if (this.value.length > 4) {
                    this.value.splice(4, this.value.length -4);
                    this.$message({
                        message: '最多可配置4个常用菜单哦！',
                        type: 'warning'
                    });
                    return;
                }
            },
            submit() {
                let cmsUserMenuRelateDtoList = [];
                this.allMenuList.forEach(item => {
                    this.value.forEach(itm => {
                        if (item.id === itm) {
                            cmsUserMenuRelateDtoList.push(item)
                        }
                    })
                });
                query(cmsUserMenuRelateDtoList, 'post').then(res => {
                    this.$message({
                        message: '编辑成功！',
                        type: 'success'
                    });
                    this.dialogVisible = false;
                    this.getMenuList();
                })
            }
        }
    }
</script>

<style lang="scss" scoped>
    .isp-home-icon-wrap {
        display: flex;
        align-items: center;
        justify-content: center;
        .isp-home-menu {
            display: flex;
            flex-direction: column;
            align-items: center;
        }
        .isp-home-icon-1 {
            display: flex;
            justify-content: center;
            align-items: center;
            width: 50px;
            height: 50px;
            border-radius: 50%;
            margin-bottom: 10px;
        }
        .isp-home-icon-2 {
            display: block;
            color: #666;
            text-align: center;
            .num {
                font-size: 28px;
                padding-left: 8px;
            }
            .desc {
                margin-top: 10px;
                font-size: 14px;
            }
        }
        .icon-bg-0{
            background-color: #ed5565;
        }
        .icon-bg-1 {
            background-color: #e78c05;
        }
        .icon-bg-2 {
            background-color: #53a568;
        }
        .icon-bg-3 {
            background-color: #1372c9;

        }
    }
    .ips-home-more {
        float: right;
        a {
            color: #3AA1FF;
        }
    }
    .edit-btn {
        position: absolute;
        right: 10px;
        top: 120px
    }
    .warning {
        color: #e6a23c;
    }
.font {
    color:#ffffff;
    font-size: 22px;
}
</style>