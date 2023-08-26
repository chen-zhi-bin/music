<template>
  <div>
    <el-container>
      <el-header id="admin-header-box">
        <topHeader></topHeader>
      </el-header>
      <el-container>
        <el-aside id="left-menu-list-box" width="200px">
              <leftMenu></leftMenu>
        </el-aside>
        <el-container>
          <el-main>
            <div class="content">
              <router-view></router-view>
            </div>
          </el-main>
          <!-- <el-footer>底部内容</el-footer> -->
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
export default{
   mounted(){
    //动态设置侧边菜单高度
    let leftMenuBox = document.getElementById('left-menu-list-box');
    let adminHeaderBox = document.getElementById('admin-header-box');
    if(leftMenuBox){
      leftMenuBox.style.height = (window.innerHeight - adminHeaderBox.offsetHeight) + 'px';
    }
   }
}
</script>
<style>
#left-menu-list-box .el-menu{
  border-right: none;
  background-color: #e6e6e6;
}
.el-header,
.el-footer {
  background-color: dodgerblue;
  /* color: #333; */
  /* text-align: center; */
  line-height: 46px;
  height: 46px;
}

.el-aside {
  /* background-color: #D3DCE6; */
  /* color: #333; */
  /* text-align: center; */
  background-color: #e6e6e6;
  line-height: 200px;
  border-right: solid 1px #e6e6e6;
}

.el-main {
  padding: 0;
  /* background-color: #E9EEF3; */
  /* color: #333; */
  /* text-align: center; */
  /* line-height: 160px; */
}
</style>