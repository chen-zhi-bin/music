<template>
  <div>
    <el-menu default-active="0" :unique-opened="true" class="el-menu-vertical" @open="handleOpen" @close="handleClose">
      <!-- 便利菜单内容 -->
      <!-- 一种是有菜单的，另一种没有 -->
      <template v-for="(item, index) in menuList">
        <router-link :to="item.path" :key="index" v-if="!item.children && !item.hidden">
          <el-menu-item :index="index + ''">
            <component class="icon" :is="item.icon"></component>
            <span>{{ item.name }}</span>
          </el-menu-item>
        </router-link>

        <el-sub-menu v-if="item.children && !item.hidden && isShowItem(item)" :key="index * 2" :index="index + ''">
          <template #title>
            <component class="icon" :is="item.icon"></component>
            <span>{{ item.name }}</span>
          </template>

          <router-link :to="item.path + '/' + subItem.path" v-for="(subItem, subIndex) in item.children" :key="subIndex">
            <el-menu-item :index="index + '-' + subIndex" v-if="!subItem.hidden">
              <component class="icon" :is="subItem.icon"></component>
              <span v-text="subItem.name"></span>
            </el-menu-item>
          </router-link>
        </el-sub-menu>
      </template>

    </el-menu>
  </div>
</template>
  
<script>
import { routes } from "../router";
import * as api from "../api/api";
export default {
  data() {
    return {
      menuList: [],
      admin: {
        roleId: ''
      }
    }
  },
  mounted() {
    this.checkToken();
  },
  methods: {
    isShowItem(item) {
      let isAdmin = false;
      let roles = item.roleIds;
      for (let key in roles) {
        if (this.admin.roleId == roles[key]) {
          isAdmin = true;
          break;
        }
      }
      return isAdmin;
    },
    checkToken() {
      api.checkToken()
        .then(result => {
          this.admin.roleId = result.data.user.roleId;
          let menuList = routes[0];
          this.menuList = menuList.children;
          // console.log(this.roleId);
        });

    }
  }
}
</script>
  
<style>
.el-menu-vertical a {
  text-decoration: none;
}

.icon {
  width: 20px;
  height: 20px;
  margin-right: 10px;
}
</style>
  