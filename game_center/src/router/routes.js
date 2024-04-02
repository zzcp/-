const routes = [
    {
        // 首页
        path: "/",
        name: 'index',
        meta:{
            title:'首页 - 趣玩游戏'
        },
        component: () => import("../components/index/IndexComponent.vue"),
    },
    {
        // 搜索
        path:"/search",
        name:"search",
        meta:{
            title:"搜索"
        },
        component: () =>import("../components/search/SearchComponent.vue"),
        children:[
            {
                path:"/search",
                redirect:"/404"
            },
            {
                path:"game/:keyword",
                component:()=>import("@/components/search/GameList.vue")
            },
            {
                path:"user/:keyword",
                component:()=>import("@/components/search/UserList.vue")
            }
        ]
    },
    {
        // 标签相关游戏
        path:"/discover",
        name:"discover",
        meta:{
            title:"游戏列表 | 趣玩游戏"
        },
        component: () =>import("../components/search/SearchComponent.vue"),
        children:[
            {
                path:"/discover",
                redirect:"/404"
            },
            {
                path:"tag/:keyword",
                component:()=>import("@/components/search/GameList.vue")
            },
        ]
    },
    {
        // 详情页面
        path:"/detail/:id",
        name:"detail",
        meta:{
            title:"详情页面"
        },
        component: () =>import("../components/detail/DetailComponent.vue")
    },
    {
        //心愿单
        path:"/wishlist",
        name:"wishlist",
        meta:{
            title:"心愿单"
        },
        component:()=>import("../components/wishlist/WishListComponent.vue")
    },
    {
        path:"/test",
        name:"test",
        component:()=>import("@/components/QrCode.vue")
    },
    {
        //404
        path:"/404",
        name:"notFound",
        meta:{
            title:"页面未找到 - 趣玩游戏"
        },
        component:()=>import("../components/notFound/NotFound.vue")
    },
    {
        // 所有未定义路由，全部重定向到404页
        path:"/:catchAll(.*)",
        redirect:"/404"
    }
]

export default routes
