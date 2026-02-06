import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import vueDevTools from 'vite-plugin-vue-devtools'
import VueSetupExtend from 'vite-plugin-vue-setup-extend'

// https://vite.dev/config/
export default defineConfig({
  plugins: [
    vue(),
    vueDevTools(),
    VueSetupExtend(),
  ],
  server: {
    host: '0.0.0.0',    // 允许所有网络访问
    port: 8888,         // 端口号
    open: false,         // 自动打开浏览器
    strictPort: true,   // 端口被占用时自动退出
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    },
  },
})
