import {createRouter,createWebHashHistory} from 'vue-router' 
const login =() =>import ("@/page/login/user-login");
// 内容
const musicManage =() =>import ("@/page/content/manage-music");
const imageManage =() =>import ("@/page/content/manage-image");
const commentManage =() =>import ("@/page/content/manage-comment");
// const postMusic =() =>import ("@/page/content/post-music");
//首页
const index =() =>import ("@/page/dashboard/dashboard-index");
//运营
const loopManage =() =>import ("@/page/operation/manage-loop");

//设置
  const friendLink =() =>import ("@/page/settings/friend-link");
  const websizeinfo =() =>import ("@/page/settings/websize-info");
// 用户
const email =() =>import ("@/page/user/user-email");
const info =() =>import ("@/page/user/user-info");
const list =() =>import ("@/page/user/user-list");
const resetPassword =() =>import ("@/page/user/reset-password");
// base view
const baseView =() =>import ("@/layout/base-view.vue");
const rightView =() =>import ("@/layout/right-content");

export const routes = [
    { 
      path: '', 
      component: baseView ,
      redirect: '/index',
      children: [
        {
          path: '/index',
          icon:'House',
          requireLogin:true,//role:admin
          name:'首页',
          hidden:false,
          component: index
        },
        {
          path: '/content',
          icon:'Document',
          requireLogin:true,
          roleIds:[1,3,4],
          name:'内容',
          hidden:false,
          component: rightView,
          children:[
            // {
            //   path:'post-music',
            //   icon:'Files',
            //   name:'发表音乐',
            //   hidden:false,
            //   component: postMusic
            // },
            {
              path:'manage-music',
              icon:'MessageBox',
              name:'音乐管理',
              hidden:false,
              component: musicManage
            },
            {
              path:'manage-comment',
              icon:'Comment',
              name:'评论管理',
              hidden:false,
              component:commentManage
            },
            {
              path:'manage-image',
              icon:'Files',
              name:'图片管理',
              hidden:false,
              component: imageManage
            }
          ]
        },
        {
          path: '/user',
          name:'用户',
          requireLogin:true,
          icon:'User',
          roleIds:[1,2],
          hidden:false,
          component: rightView,
          children:[
            {
              path:'list',
              icon:'List',
              name:'用户列表',
              hidden:false,
              component: list
            },
            {
              path:'reset-password',
              icon:'Unlock',
              name:'密码重置',
              hidden:false,
              component: resetPassword
            },
            {
              path:'email',
              icon:'Message',
              name:'邮箱信息',
              hidden:false,
              component:email
            },
            {
              path:'info',
              icon:'Tickets',
              name:'用户信息',
              hidden:false,
              component: info
            }
          ]
        },
        {
          path: '/operation',
          icon:'Guide',
          requireLogin:true,
          roleIds:[1,3,4],
          name:'运营',
          hidden:false,
          component: rightView,
          children:[
            {
              path:'loop',
              icon:'Rank',
              name:'轮播图管理',
              hidden:false,
              component: loopManage
            }
          ]
        },
        {
          path: '/settings',
          name:'设置',
          icon:'Setting',
          requireLogin:true,
          hidden:false,
          roleIds:[1,2,3,4],
          component: rightView,
          children:[
            {
              path:'web-size-info',
              icon:'InfoFilled',
              name:'网站信息',
              hidden:false,
              component: websizeinfo
            },
            {
              path:'friend-link',
              icon:'Link',
              name:'友情链接',
              hidden:false,
              component: friendLink
            }
          ]
        }
      ]
    },
    {
      path:'/login',
      component:login,
      requireLogin:false
    }
  ];

const router = createRouter({
    //  内部提供了 history 模式的实现。为了简单起见，我们在这里使用 hash 模式。
    history: createWebHashHistory(),
    routes, // `routes: routes` 的缩写
});
export default router;