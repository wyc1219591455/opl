<template>
  <div class="btn-box">
    <el-button class="edit-btn" v-permission="permission.edit" :loading="crud.status.cu === 2" :disabled="disabledEdit" size="mini" type="primary" @click="crud.toEdit(data)">编辑</el-button>
    <el-popover v-model="pop" v-permission="permission.del" placement="top" width="180" trigger="manual" @show="onPopoverShow" @hide="onPopoverHide">
      <p>{{ msg }}</p>
      <div style="text-align: right; margin: 0">
        <el-button size="mini" type="text" @click="doCancel">取消</el-button>
        <el-button :loading="crud.dataStatus[crud.getDataId(data)].delete === 2" type="primary" size="mini" @click="crud.doDelete(data)">确定</el-button>
      </div>
      <el-button slot="reference" :disabled="disabledDle" type="danger" size="mini" @click="toDelete(data)">删除</el-button>
    </el-popover>
  </div>
</template>
<script>
import CRUD, { crud } from '@crud/crud'
export default {
  mixins: [crud()],
  props: {
    data: {
      type: Object,
      required: true
    },
    permission: {
      type: Object,
      required: true
    },
    disabledEdit: {
      type: Boolean,
      default: false
    },
    disabledDle: {
      type: Boolean,
      default: false
    },
    msg: {
      type: String,
      default: '确定删除本条数据吗？'
    }
  },
  data() {
    return {
      pop: false
    }
  },
  methods: {
    doCancel() {
      this.pop = false
      this.crud.cancelDelete(this.data)
    },
    toDelete(datas) {
      this.pop = false;
      this.$confirm('此操作将永久删除该数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.crud.doDelete(datas)
      }).catch(() => {
        console.log('取消')
      });
    },
    [CRUD.HOOK.afterDelete](crud, data) {
      if (data === this.data) {
        this.pop = false
      }
    },
    onPopoverShow() {
      setTimeout(() => {
        document.addEventListener('click', this.handleDocumentClick)
      }, 0)
    },
    onPopoverHide() {
      document.removeEventListener('click', this.handleDocumentClick)
    },
    handleDocumentClick(event) {
      this.pop = false
    }
  }
}
</script>
<style lang="scss" scoped>
.btn-box {
  display: flex;
  justify-content: center
}
</style>
