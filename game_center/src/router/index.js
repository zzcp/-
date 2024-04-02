// 导入router所需的方法
import { createRouter, createWebHistory } from 'vue-router'
import { getUserInfo } from '@/utils/tools'
// 导入路由页面的配置
import routes from './routes'

// 路由参数配置
const router = createRouter({
    // 使用hash(createWebHashHistory)模式，(createWebHistory是HTML5历史模式，支持SEO)
    history: createWebHistory(),
    routes: routes,
    scrollBehavior() {
        return { top: 0 } //路径更换时，自动跳转至顶
    }
})

// 全局前置守卫，控制跳转
router.beforeEach((to, from, next) => {    
    const userInfo = getUserInfo();    

    if ((to.name === "wishlist"||to.name === "mine"||to.name === "mymessage"||to.name === "mygame"||to.name === "mycom" )&& !userInfo) {    
        alert("请先登录或注册！");    
        next("/");    
        return; 
    } 

    next();
});


// 全局后置钩子，这里改变页面标题等操作
router.afterEach((to) => {
    let _title = ""
    _title = to.meta.title
    if (_title) {
        window.document.title = _title
    }
})

// 导出默认值
export default router
