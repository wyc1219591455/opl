<!-- @author zhengjie -->
<template>
  <div class="icon-body">
    <!-- <el-input v-model="name" style="position: relative;" clearable placeholder="请输入图标名称" @clear="filterIcons" @input.native="filterIcons">
      <i slot="suffix" class="el-icon-search el-input__icon" />
    </el-input> -->
    <div class="icon-list">
      <div v-for="(item, index) in iconList" :key="index" @click="selectedIcon(item)">
        <!-- <svg-icon :icon-class="item" style="height: 30px;width: 16px;" /> -->
          <i class="iconfont font" :class="item" ></i>
          <span>{{ item }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import icons from './requireIcons'
export default {
  name: 'IconChoose',
  data() {
    return {
      name: '',
      iconList: icons
    }
  },
  created() {
    // console.log(icons);
  },
  methods: {
    filterIcons() {
      this.iconList = icons;
      if (this.name) {
        this.iconList = this.iconList.filter(item => item.includes(this.name))
      }
    },
    selectedIcon(name) {
      this.$emit('selected', name)
      document.body.click()
    },
    reset() {
      this.name = ''
      this.iconList = icons
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .icon-body {
    width: 100%;
    padding: 10px;
    .icon-list {
      width: 100%;
      height: 200px;
      overflow-y:scroll ;
      display: flex;
      flex-wrap: wrap;
      // justify-content: center;
      div {
        display: flex;
        flex-direction: column;
        margin:10px 5px;
        text-align: center;
        cursor: pointer;
        width: 20%;
        
        // float: left;
        span {
          width: 100%;
          // overflow: hidden;
          // text-overflow:ellipsis;
          // word-break: break-all;
          // white-space: nowrap;
          font-size: 14px ;
          display: inline-block;
          vertical-align: -0.15em;
          fill: currentColor;
          overflow: hidden;
        }
      }
      
    }
  }
  .font {
    font-size: 20px;
    margin-bottom: 5px
  }
</style>
