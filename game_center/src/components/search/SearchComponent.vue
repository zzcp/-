<template>
  <div>
    <div class="search-content">
      <p v-if="type=='tag'" class="tag-title">
        {{ keyword }}
      </p>
      <div v-else>
        <div>
          <form class="search-form" @submit.prevent="handleSearch">
            <i class="iconfont icon-sousuo"></i>
            <input type="text" placeholder="请输入关键词" v-model="keyword">
            <input type="submit" value="搜索">
          </form>
        </div>
        <ul class="nav-ul">
            <li v-for="item in typeList" :key="item.id" :data-type="item.type" :class="type==item.type?'selected':''" @click="changeType">{{ item.name }}</li>
        </ul>
      </div>
      <div v-if="searchList.length">
        <router-view  :type="type" :resultList="searchList"  v-loading="loading" element-loading-text="Loading..."></router-view>
        <!-- <GameList v-if="type=='game' || type=='tag'" :resultList="data.searchList" :type="type" v-loading="loading" element-loading-text="Loading..."></GameList> -->
        <!-- <UserList v-else-if="type == 'user'" :resultList="data.searchList"  v-loading="loading" element-loading-text="Loading..."></UserList> -->
        <el-pagination background layout="prev, pager, next" :total="total" :page-size="limit" :hide-on-single-page="total<=limit?true:false" @current-change="handleCurrentChange"/>
      </div>
      <div v-else class="notResult">暂无相关搜索结果</div>
    </div>
  </div>
</template>

<script>
// import { useRoute,useRouter } from "vue-router" 
// import {  onMounted,reactive,ref, watch } from 'vue';
// import GameList from "./GameList.vue";
// import UserList from "./UserList.vue"
import request from "@/utils/request";
export default{
  data(){
    return{
      keyword:"",
      type:"game",
      loading:false,
      typeList:[
        {
            id:0,
            name:"游戏",
            type:"game"
        },{
            id:1,
            name:"用户",
            type:"user"
        },
      ],
      searchList:[],
      limit:40,
      page:1,
      total:0,
      val:[40,80]
    }
  },
  created(){
    this.init()
  },
  watch:{
    $route(){
      this.init()
    }
  },
  methods:{
    init(){
      this.type = this.$route.path.split("/")[2]
      if(this.type =="tag"){
        document.querySelector("title").text = this.$route.params.keyword+"游戏列表 | 趣玩游戏"
      }else{
        document.querySelector("title").text=this.$route.params.keyword+' - 搜索'
      }
        this.keyword = this.$route.params.keyword;
        this.getSearchRes()
    },
    handleSearch(){
      let url = window.location.href
      let urlArr = url.split("/")
      urlArr.splice(urlArr.length-1,1,this.keyword)
      // 修改浏览器历史记录当前状态，同时改变状态栏url地址，而不会刷新页面
      history.pushState(null,null,urlArr.join("/"))

      document.querySelector("title").text=this.keyword+' - 搜索'
      this.getSearchRes()
    },
    formatTooltip(val){
      return val / 100
    },
    async getSearchRes(){
      this.loading = true
      let res = {};
      let page = this.page
      if (this.type=="game"){
        res = await request("/search",{keyword:this.keyword,type:"game",offset:(page-1)*this.limit,limit:this.limit})
      }else if(this.type=="tag"){
        res = await request("/search",{keyword:this.keyword,type:"tag",offset:(page-1)*this.limit,limit:this.limit})
      }else if(this.type=="user"){
        res = await request("/search",{keyword:this.keyword,type:"user",offset:(page-1)*this.limit,limit:this.limit})
        
      }

      this.searchList=res.data.searchList
      this.total = res.data.total

      setTimeout(()=>{this.loading = false},1000)
      
    },
    changeType(e){
      this.type=e.currentTarget.dataset.type

      this.$router.push("/search/"+this.type+"/"+this.keyword)

      this.searchList = []
      this.limit = 40
      this.page = 1
      this.total = 0
      
      // this.getSearchRes(this.page)
    },
    handleCurrentChange(val){
      this.page = val
      this.getSearchRes(this.page)
      document.documentElement.scrollTop=0
    }
  }
}
// const route = useRoute()
// const router = useRouter()
// const keyword = ref("")

// const type = ref("game")
// const loading = ref(false)

// const typeList = reactive([
//     {
//         id:0,
//         name:"游戏",
//         type:"game"
//     },{
//         id:1,
//         name:"用户",
//         type:"user"
//     },
// ])

// const data = reactive({
//   searchList:[],
//   limit:40,
//   page:1,
//   total:0
// })

// onMounted (()=>{
// })
// function init(){
//   type.value = route.path.split("/")[2]
//   if(type.value=="tag"){
//     document.querySelector("title").text = route.params.keyword+"游戏列表 | 趣玩游戏"
//   }else{
//     document.querySelector("title").text=route.params.keyword+' - 搜索'
//   }
//     keyword.value = route.params.keyword;
//     getSearchRes(data.page)
// }
// init()



// function handleSearch(){
//   let url = window.location.href
//   let urlArr = url.split("/")
//   urlArr.splice(urlArr.length-1,1,keyword.value)
//   // 修改浏览器历史记录当前状态，同时改变状态栏url地址，而不会刷新页面
//   history.pushState(null,null,urlArr.join("/"))

//   document.querySelector("title").text=keyword.value+' - 搜索'
//   getSearchRes(data.page)
// }

// async function getSearchRes(page){
//   loading.value = true
//   let res = {};
//   if (type.value=="game"){
//     res = await request("/search",{keyword:keyword.value,type:"game",offset:(page-1)*data.limit,limit:data.limit})
//   }else if(type.value=="tag"){
//     res = await request("/search",{keyword:keyword.value,type:"tag",offset:(page-1)*data.limit,limit:data.limit})
//   }else if(type.value=="user"){
//     res = await request("/search",{keyword:keyword.value,type:"user",offset:(page-1)*data.limit,limit:data.limit})
    
//     for(let i in res.data.searchList){
//       res.data.searchList[i].imgUrl = require(`@/${res.data.searchList[i].imgUrl}`)
//     }
//   }

//   data.searchList=res.data.searchList
//   data.total = res.data.total

//   setTimeout(()=>{loading.value = false},1000)
  
// }

// function changeType(e){
//   type.value=e.currentTarget.dataset.type

//   router.push("/search/"+type.value+"/"+keyword.value)

//   data.searchList = []
//   data.limit = 40
//   data.page = 1
//   data.total = 0
  
//   getSearchRes(data.page)
// }

// function handleCurrentChange(val){
//   data.page = val
//   getSearchRes(data.page)
//   document.documentElement.scrollTop=0
// }



</script>

<style scoped>
:deep(.el-pagination.is-background){
  justify-content: center;
  margin: 20px 0!important;
}
.search-content{
  width: 1200px;
  margin:0 auto;
}

.tag-title{
  font-size: 22px;
  font-family: -apple-system,BlinkMacSystemFont,Helvetica Neue,Helvetica,Arial,PingFang SC,Hiragino Sans GB,Microsoft YaHei,sans-serif;
  margin: 22px 0 17px 0;
}

.search-form{
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 600px;
  height: 40px;
  margin:20px auto;
  padding: 10px;
  border-radius: 10px;
  border: 1px solid #f6f7f8;
  background-color: #f6f7f8;
}

.search-form:hover {
  background-color: white;
  border: 1px solid #24B8F2;

}

.search-form>i{
  display: flex;
  align-items: center;
  justify-content: center;
  color: #24B8F2;
  height: 40px;
  width: 40px;
  line-height: 40px;
  font-size: 18px;
}

.search-form>input[type=text]{
  margin: 0 10px 0 5px;
  width: calc(100% - 190px);
  outline: unset;
  border: 0;
  padding: 10px 5px;
  font-size: 16px;
  background-color: unset;
}

.search-form>input[type=submit]{
  /* width: 100px; */
  color: white;
  background-color: #24B8F2;
  padding: 12px 50px;
  border-radius: 8px;
  border: 0;
  outline: unset;
  cursor: pointer;
  font-size: 16px;
}

.notResult{
  text-align: center;
  margin: 40px 0;
  font-size: 18px;
  color: rgba(0,0,0,.6);
}

.nav-ul{
  display: flex;
  justify-content: center;
  border-bottom: 1px solid rgba(0,0,0,.1);
  margin-bottom: 10px;
}

.nav-ul>li{
  padding: 10px 14px;
  margin-right: 20px;
  cursor: pointer;
}

.selected,
.nav-ul>li:hover{
    color: #24B8F2;
    border-bottom:2px solid #24B8F2;
}

</style>