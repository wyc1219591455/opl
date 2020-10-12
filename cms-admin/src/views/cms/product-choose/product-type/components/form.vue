<template>
    <el-form :model="model" :rules="rules" ref="ruleForm" label-width="100px">
        <el-form-item label="属性" prop="name">
            <el-input v-model.trim="model.name" style="width: 300px" placeholder="请输入属性名称"></el-input>
        </el-form-item>
        <el-form-item>
            <vxe-table 
            border
            align='center'
            :data="model.json">
                <vxe-table-column title="选型名称">
                    <template v-slot:default="{ row }">
                        <el-input v-model="row.label" placeholder="请输入选型名称"></el-input>
                    </template>
                </vxe-table-column>
                <vxe-table-column title="选型值" >
                    <template v-slot:default="{ row }">
                        <el-input v-model="row.value" placeholder="请输入选型值"></el-input>
                    </template>
                </vxe-table-column>
                <vxe-table-column title="操作" >
                    <template v-slot:default="{ row }">
                       <el-button type="danger" @click="del(row)" size="mini">删除</el-button>
                    </template>
                </vxe-table-column>
            </vxe-table>
            <div class="btn-box">
                <el-button size="mini" type="primary" @click="add">新增</el-button>
            </div>
        </el-form-item>
        <el-form-item label="属性说明" prop="desc">
            <el-input
                    v-model="model.desc"
                    type="textarea"
                    :autosize="{ minRows: 2, maxRows: 4}"
                    style="width: 300px" placeholder="请输入属性说明"></el-input>
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
    import api from '@/api/cms/product-choose';
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
        watch: {
            activeData(val) {
                if (this.type === 'edit') {
                    console.log(typeof(val, '333'));
                    val = Object.assign(val, {
                        json: JSON.parse(this.activeData.json)
                    });
                    this.model = val;
                }
            },
        },
        data() {
            return {
                model: {
                    name: '',
                    desc: '',
                    active: true,
                    json: [
                        {
                            label: '',
                            value: ''
                        }
                    ],
                },
                rules: {
                    name: [
                        { required: true, message: '请输入属性名称', trigger: 'blur' }
                    ],
                    desc: [
                        { required: true, message: '请输入属性说明', trigger: 'blur' }
                    ],
                    active: [
                        { required: true, message: '请选择状态', trigger: 'blur' }
                    ],
                },
            }
        },
        created(){
            if (this.type === 'edit') {
                console.log(typeof(this.activeData.json));
                if (this.activeData.json) {
                    this.activeData = Object.assign(this.activeData, {
                        json: JSON.parse(this.activeData.json)
                    });
                }
                this.model = this.activeData;
            }
        },
        methods: {
            // 新增
            add() {
                this.model.json.push({
                    label: '',
                    value: ''
                });
            },
            // 删除
            del(data) {
                this.model.json.splice(this.model.json.findIndex(item => item._XID === data._XID), 1);
            },
            // 重置
            resetForm(formName) {
                this.$refs[formName].resetFields();
                this.model.json.forEach(item => {
                    item.label = '';
                    item.value = '';
                });
            },
            // 保存新增
            submitForm(formName) {
                let message = this.type === 'edit' ? '保存成功' : '新增成功';
                let methodType = this.type === 'edit' ? 'put' : 'post';
                this.$refs[formName].validate((valid) => {
                    if (valid) {
                        const parmas = _.cloneDeep(this.model);
                        parmas.json = JSON.stringify(parmas.json);
                        api.queryMatchAttributeList(parmas, methodType).then(()=>{
                            this.$message({
                                message,
                                type: 'success'
                            });
                            if (this.type === 'edit') {
                                this.$emit('editSuccess');
                            } else {
                                this.$router.push({
                                    path: '/productTypeList'
                                })
                            }
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

<style scoped  rel="stylesheet/scss" lang="scss">
.btn-box {
    display: flex;
    justify-content: center;
    padding-top: 10px;
}

</style>