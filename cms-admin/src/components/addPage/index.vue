<template>
    <div>
         <el-button
            class="add-btn"
            size="mini"
            type="primary"
            icon="el-icon-plus"
            @click="add"
        >
            新增
        </el-button>
    </div>
</template>

<script>
export default {
    name: 'addPage',
    props: {
        path: {
            type: String,
            default: "",
        },
    },
    data() {
        return {

        }
    },
    methods: {
        add() {
            this.$router.push({
                path: this.path
            });
        }
    }

}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.add-btn {
    margin-bottom: 10px;
}
</style>