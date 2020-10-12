<template>
    <el-form :model="model" label-width="130px" ref="ruleForm" :inline="true" :rules="rules">
        <el-form-item label="选择产品参数：">
            <!-- <el-form label-width="90px" :inline="true"> -->
            <el-form-item label="产品分类" prop="value1">
                <treeselect
                    v-model="model.value1"
                    :options="productSortList"
                    noOptionsText="暂无匹配分类"
                    style="width: 200px"
                    placeholder="请选择产品分类"
                    @select="treeChoose"
                    :normalizer="normalizer"
                />
            </el-form-item>
            <el-form-item label="产品名称" prop="productId">
                <el-select v-model="model.productId" placeholder="选择产品" style="width: 200px">
                    <el-option
                        v-for="item in productList"
                        :disabled="!item.status"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    ></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="匹配基本属性">
                <el-select v-model="model.showProperty" style="width: 200px">
                    <el-option
                        v-for="(item, index) in options3"
                        :key="index"
                        :label="item.label"
                        :value="item.value"
                    ></el-option>
                </el-select>
            </el-form-item>
            <div v-if="model.showProperty">
                <div v-for="(item, index) in model.cmsMatchPropertyDtoList" :key="item.id" style="display: inline-block">
                    <el-form-item
                        :label="item.name"
                        v-if="item.active"              
                    >
                    <el-select
                        v-model="model.cmsMatchPropertyDtoList[index].matchPropertyValue"
                        style="width: 200px"
                    >
                        <el-option
                            v-for="(itm, idx) in item.json"
                            :key="idx"
                            :label="itm.label"
                            :value="itm.value"
                        ></el-option>
                    </el-select>
                </el-form-item>
                </div>
            </div>
            <!-- </el-form> -->
        </el-form-item>

        <div>
            <el-form-item label="匹配产品分类:" prop="value2">
                <treeselect
                    v-model="model.value2"
                    :options="matchingSortList"
                    noOptionsText="暂无匹配分类"
                    style="width: 330px"
                    placeholder="请选择匹配分类"
                    @select="matchingSortChoose"
                    :normalizer="normalizer"
                />
            </el-form-item>
            <div>
                <el-form-item label="选择要绑定的产品：" prop="matchProductIdList">
                    <el-transfer
                        style="text-align: left; display: inline-block;"
                        v-model="model.matchProductIdList"
                        filterable
                        :titles="['源列表', '目标列表']"
                        :props="{
                    key: 'id',
                    label: 'name'
                }"
                        :format="{
                    noChecked: '${total}',
                    hasChecked: '${checked}/${total}'
                }"
                        @change="handleChange"
                        :filter-method="filterMethod"
                        :data="matchingList"
                    >
                        <span slot-scope="{ option }">{{ option.name }}</span>
                    </el-transfer>
                </el-form-item>
            </div>
            <el-form-item label="选择图纸" prop="drawingId">
                <el-select v-model="model.drawingId" placeholder="选择图纸" style="width: 330px">
                    <el-option
                        v-for="item in paperList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    ></el-option>
                </el-select>
            </el-form-item>
        </div>
        <el-form-item v-if="type != 'edit'" style="margin-left: 150px">
            <el-button type="primary" @click="submitForm('ruleForm')">保存</el-button>
            <!-- <el-button v-if="type == 'create'" @click="resetForm('ruleForm')">重置</el-button> -->
        </el-form-item>
    </el-form>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import api from "@/api/cms/product-choose";
export default {
    components: {
        Treeselect,
    },
    props: {
        activeData: {
            type: Object,
            default: () => {
                return {};
            },
        },
        type: {
            type: String,
            default: "",
        },
    },
    data() {
        return {
            rules: {
                value1: [
                    { required: true, message: "请选择产品分类", trigger: "change", }
                ],
                productId: [
                    { required: true, message: "请选择产品", trigger: "change", }
                ],
                value2: [
                    { required: true, message: "请选择匹配产品分类", trigger: "change", }
                ],
                matchProductIdList: [
                    { required: true, message: "请选择绑定的产品", trigger: "change", }
                ]
            },
            motors: [],
            productSortList: [],
            productList: [],
            matchingSortList: [],
            matchingList: [],
            paperList: [],
            chooseId: "",
            matchProductList: [],
            options3: [
                {
                    label: "是",
                    value: true,
                },
                {
                    label: "否",
                    value: false,
                },
            ],
            model: {
                id: "",
                value1: null,
                value2: null,
                productId: "",
                drawingId: "",
                showProperty: false,
                cmsMatchPropertyDtoList: [],
                matchProductIdList: [],
            },
            filterMethod(query, item) {
                return item.name.indexOf(query) > -1;
            },
        };
    },
    watch: {
        activeData(val) {
            if (this.type === "edit") {
                // this.getArticleCategoryList();
                this.getProductList(val.productCms.productCategory.id);
                this.matchingSortChoose(
                    this.getMatchSort(val.matchProductDtoList)
                );
                this.model = Object.assign(this.model, {
                    id: val.id || "",
                    value1: val.productCms.productCategory ? val.productCms.productCategory.id : '',
                    drawingId: val.drawingDto ? val.drawingDto.id : '',
                    productId: val.productCms.id,
                    showProperty: val.showProperty,
                    cmsMatchPropertyDtoList: this.jsonParse(
                        val.cmsMatchPropertyDtoList
                    ),
                    value2: this.getMatchSort(val.matchProductDtoList),
                    matchProductIdList: this.getMatchProductIdList(
                        val.matchProductDtoList
                    ),
                });
            }
        },
    },
    created() {
        this.getProductSort();
        this.getMatchingSort();
        this.getMatchingSort();
        this.getPaperList();
        this.getArticleCategoryList();
        if (this.type === "edit") {
            this.getProductList(this.activeData.productCms.productCategory.id);
            this.matchingSortChoose(
                this.getMatchSort(this.activeData.matchProductDtoList)
            );
            this.model = Object.assign(this.model, {
                id: this.activeData.id || "",
                value1: this.activeData.productCms.productCategory ? this.activeData.productCms.productCategory.id : '',
                drawingId: this.activeData.drawingDto ? this.activeData.drawingDto.id : '',
                productId: this.activeData.productCms ? this.activeData.productCms.id : '',
                showProperty: this.activeData.showProperty,
                cmsMatchPropertyDtoList: this.jsonParse(
                    this.activeData.cmsMatchPropertyDtoList
                ),
                value2: this.getMatchSort(this.activeData.matchProductDtoList),
                matchProductIdList: this.getMatchProductIdList(
                    this.activeData.matchProductDtoList
                ),
            });
        }
    },
    methods: {
        // 获取匹配产品id列表
        getMatchProductIdList(list) {
            const ids = [];
            list.forEach((item) => {
                ids.push(item.id);
            });
            return ids;
        },
        // 获取匹配产品分类id
        getMatchSort(list) {
            return list.length ? list[0].matchProductCategory.id : [];
        },
        jsonParse(data) {
            data.forEach((item) => {
                item.json = JSON.parse(item.json);
            });
            return data;
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
                isDisabled: !node.active,
            };
        },
        handleChange() {},
        // 选择树形菜单某个值
        treeChoose(node) {
            this.getProductList(node.id);
        },
        // 获取产品分类列表
        getProductSort() {
            api.queryProductList({}, "get").then((res) => {
                this.productSortList = res.content;
            });
        },
        // 根据产品分类获取产品列表
        getProductList(categoryId) {
            const params = {
                categoryId,
            };
            api.productList(params, "get").then((res) => {
                this.productList = res;
            });
        },
        // 获取图纸列表
        getPaperList() {
            const params = {};
            api.queryPaperList(params, "get").then((res) => {
                this.paperList = res;
            });
        },
        // 获取产品匹配属性列表
        getArticleCategoryList() {
            const params = {
                page: 0,
                size: 100,
            };
            api.queryMatchAttributeList(params, "get").then((res) => {
                let resValue = res.content;
                let activeList = this.activeData.cmsMatchPropertyDtoList || [];
                if (JSON.stringify(this.activeData) != "{}" && activeList.length && resValue.length) {
                    for (var i = 0; i < resValue.length; i++) {
                        resValue[i].matchPropertyValue = '';
                        for(var j = 0; j < activeList.length; j++) {
                            if (resValue[i].id === activeList[j].id) {
                                resValue[i].matchPropertyValue = activeList[j].matchPropertyValue;
                            }
                        }
                    }
                }
                resValue.forEach((item) => {
                    item.json = JSON.parse(item.json);
                });
                this.model.cmsMatchPropertyDtoList = resValue;
            });
        },
        // 获取匹配产品分类树形菜单
        getMatchingSort() {
            api.queryMatchProductCategoryList({}, "get").then((res) => {
                this.matchingSortList = res.content;
            });
        },
        matchingSortChoose(node) {
            const params = {
                categoryId: node.id || node,
            };
            api.queryMatchingProductList(params, "get").then((res) => {
                this.matchingList = res;
            });
        },
        // 提交数据
        submitForm(formName) {
            // console.log(this.model);
            let message = this.type === "edit" ? "保存成功" : "新增成功";
            let methodType = this.type === "edit" ? "put" : "post";
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    const parmas = _.cloneDeep(this.model);
                    if (!parmas.showProperty) {
                        parmas.cmsMatchPropertyDtoList = [];
                    } else {
                        parmas.cmsMatchPropertyDtoList.forEach(item => {
                            item.json = JSON.stringify(item.json);
                        });
                    }
                    // return;
                    api.queryBind(parmas, methodType).then(() => {
                        this.$message({
                            message,
                            type: "success",
                        });
                        if (this.type === "edit") {
                            this.$emit("editSuccess");
                        } else {
                            this.$router.push({
                                path: "/productBindList",
                            });
                        }
                    });
                } else {
                    console.log("error submit!!");
                    return false;
                }
            });
        },
    },
};
</script>

<style lang="scss">
.el-transfer-panel {
    width: 330px;
}
.el-transfer-panel__body {
    height: 330px;
}
</style>