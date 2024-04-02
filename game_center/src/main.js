import { createApp } from 'vue'

import '@/assets/iconfont/iconfont.css'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import App from './App.vue'
import router from './router'

import { createPinia } from "pinia";
const pinia = createPinia();

createApp(App).use(ElementPlus).use(router).use(pinia).mount('#app')
