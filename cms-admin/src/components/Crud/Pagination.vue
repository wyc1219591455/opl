<!--分页-->
<template>
  <div style="overflow: hidden">
    <el-pagination
            :page-size.sync="page.size"
            :total="page.total"
            :current-page.sync="page.page"
            style="margin-top: 8px;"
            background
            layout="prev, pager, next, sizes, total"
            @size-change="crud.sizeChangeHandler($event)"
            @current-change="crud.pageChangeHandler"
    />
  </div>
  <!--<vxe-pager-->
          <!--:current-page="page.page"-->
          <!--:page-size="page.size"-->
          <!--:total="page.total"-->
          <!--@page-change="handlePageChange"-->
  <!--&gt;-->
  <!--</vxe-pager>-->
</template>
<script>
import { pagination } from '@crud/crud'
export default {
  mixins: [pagination()],
  // data(){
  //   return{
  //     page:{
  //       size: 10,
  //       page: 1
  //     },
  //   }
  // },
  // methods:{
  //   // 分页发生改变事件
  //   handlePageChange ({ currentPage, pageSize }) {
  //     this.page.page = currentPage
  //     this.page.size = pageSize
  //   },
  // }
}
</script>
