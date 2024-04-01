const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  publicPath: '/vue2-project/',    //gitee远程库名
  outputDir: 'dist/',
  assetsDir: 'static/'
})
