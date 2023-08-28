const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer:{
    proxy:{
      '/user':{
        target:'http://localhost:8090',
        changOrigin:true,
        ws: true,
        pathRewrite: {
            '^/user': '' //不改变
        }
      },
      '/admin':{
        target:'http://localhost:8090',
        changOrigin:true,
        ws: true,
        pathRewrite: {
            '^/admin': '' //不改变
        }
      }
    }
  }
})