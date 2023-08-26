import { createApp } from 'vue'
import App from './App.vue'
import router from './router/index'
import ElementUI from 'element-plus';
import 'element-plus/theme-chalk/index.css'
import leftMenu from './layout/left-menu.vue'
import topHeader from './layout/top-header.vue'
import * as ElIconModules from '@element-plus/icons'

const app = createApp(App)
for (let iconName in ElIconModules) {
    app.component(iconName, ElIconModules[iconName])
  }
  
//   import { checkToken } from './api/api';
//   router.beforeEach((to, from, next) => {
//     //如果是登录界面，则放行
//     if (to.path === '/login') {
//       //当前已经登录了，就没必要再到登录接秒，除非用户点击退出登录
//       // 如果已经登录，根据角色判断页面跳转
//       next();
//     } else {
//       // 否则就检查用户角色
//       checkToken().then(result => {
//         if (result.code === 20000) {
//           if (result.data.roles === 'role_admin') {
//             next();
//           } else {
//             location.href = 'https://www.sunofbeach.net/';
//           }
//         } else {
//           //跳转登录
//           next({
//             path: '/login'
//           });
//         }
//       })
//     }
//   })
  //整个应用支持路由。
  app.use(router)
  app.use(ElementUI)
  app.component('leftMenu', leftMenu)
  app.component('topHeader', topHeader)
  app.mount('#app')