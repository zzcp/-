<template>
  <div class="header-container">
    <div class="header-content">
        <ul class="nav-list">
            <li>
                <img class="logo" src="../assets/logo.png"/>
            </li>
            <li v-for="(item,index) in navList" :key="index">
                <a :href="item.url">{{ item.name }}</a>
            </li>
        </ul>
        <ul class="nav-list-right">
            <li>
                <form action="javascript:;" class="search-form"  @keydown.enter.stop="toSearch(keyword.length==0?default_ky:keyword)">
                    <input type="text" :placeholder="default_ky" v-model="keyword">
                    <i class="iconfont icon-sousuo" style="cursor: pointer;" @click="toSearch(keyword.length==0?default_ky:keyword)"></i>
                </form>
            </li>
            <li style="position: relative;width: 72px;" @mouseover="showInfo=true" @mouseleave="showInfo=false" v-if="data.userInfo&&data.userInfo.userId">
                <div style="height: 32px;">
                    <img :src="config.url+data.userInfo.imgUrl" class="user-img" :class="showInfo?'img-big':''">
                </div>
                <div class="user-function" :class="showInfo?'block':'none'">
                    <div style="height: 40px;width: 100%;"></div>
                    <div class="user-name" :title="data.userInfo.name">{{ data.userInfo.name }}</div>
                    <ul>
                        <li>
                            <a href="/mine">个人中心<i class="iconfont icon-mono-right-full"></i></a>
                        </li>
                        <li>
                            <a href="/wishlist">心愿单<i class="iconfont icon-mono-right-full"></i></a>
                        </li>
                        <li>
                            <a href="/mine/comment">我的评价<i class="iconfont icon-mono-right-full"></i></a>
                        </li>
                        <li>
                            <a href="" @click.prevent="handleLogout">退出登录</a>
                        </li>
                    </ul>
                </div>
            </li>
            <li style="position: relative;width: 72px;" @mouseover="showInfo=true" @mouseleave="showInfo=false" v-else>
                <div style="height: 32px;">
                    <img :src="config.url+'/img/user/default0.png'" class="user-img" @click="handleClose(true)">
                </div>
            </li>
        </ul>
    </div>
    <!--  -->
    <LoginComponent v-if="isClose"></LoginComponent>
  </div>
</template>

<script setup>
import config from '@/utils/config';
import { toSearch } from '@/utils/tools';
import LoginComponent from './login/LoginComponent.vue';
import { ref,reactive, onMounted} from 'vue';
import { getUserInfo } from '@/utils/tools';

import useStore from '@/store/index.js'
import { storeToRefs } from 'pinia';

const store = useStore()
const {isClose} = storeToRefs(store)

function handleClose(val){
    store.handleClose(val)
}

const keyword = ref("")
const default_ky = ref("原神")

const showInfo = ref(false)

const navList = reactive([{
    name:"首页",
    url:"/"
},{
    name:"排行榜",
    url:"/toplist/2001"
},{
    name:"分类",
    url:"/sort"
}])

const data = reactive({
    userInfo:{}
})

function handleLogout(){
    localStorage.clear();
    window.open("/","_self")
}

onMounted(()=>{
    data.userInfo = getUserInfo()
})

</script>

<style scoped>
/* .header-container{
   
} */

.header-content{
    display: flex;
    justify-content: space-between;
    width: 1200px;
    margin: 0px auto;
    padding: 8px 0;
}

.nav-list{
    display: flex;
    flex-wrap: nowrap;
    justify-content: space-around;
    align-items: center;
}

.nav-list>li{
    display: flex;
    align-items: center;
}

.nav-list>li>a{
    display: block;
    margin: 5px;
    padding: 0 15px;
    color: black;
    font-size: 14px;
    -webkit-font-smoothing: antialiased;
    font-family: -apple-system, BlinkMacSystemFont, "Microsoft YaHei", "Helvetica Neue", Helvetica, Arial, "PingFang SC", "Hiragino Sans GB", sans-serif;
}

.nav-list>li>a:hover{
    color: #24B8F2;
}

.logo{
    width: 95px;
    height: 23px;
}

.nav-list-right{
    display: flex;
    flex-wrap: nowrap;
    justify-content: space-around;
    align-items: center;
}

.nav-list-right>li{
    display: flex;
    align-items: center;
}

.search-form{
    display: flex;
    align-items: center;
    padding: 8px 20px;
    background-color: #f2f4f6;
    border-radius: 20px;
}

.search-form>input{
    border: 0;
    outline: none;
    margin-right: 10px;
    background-color: unset;
    color: rgba(0, 0, 0, 0.4)
}

.search-form>i{
    color: rgba(0, 0, 0, 0.4)
}

.user-img{
    position: relative;
    top: 0px;
    height: 32px;
    width: 32px;
    border-radius: 50%;
    margin: 0 20px;
    transition: .2s;
    cursor: pointer;
}

.img-big{
    position: relative;
    top: 20px;
    height: 60px;
    width: 60px;
    z-index: 12;
}

.user-function{
    display: none;
    position: absolute;
    top: 45px;
    left: -50px;
    /* height: 360px; */
    width: 200px;
    background-color: white;
    box-shadow:0 3px 12px rgb(98 107 181 / 25%);
    border-radius: 10px;
    z-index: 11;
}

.user-function>.user-name{
    height: 30px;
    width: calc(100% - 40px);
    padding: 0 20px;
    color: rgba(0, 0, 0, 0.85);
    font-size: 16px;
    font-weight: 600;
    text-align: center;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
}

.user-function>ul{
    display: flex;
    flex-direction: column;
    align-items: center;
    height: calc(100% - 90px);
    width: 100%;
    padding-bottom: 10px;
}

.user-function>ul>li{
    flex: 1;
    width: 100%;
}

.user-function>ul>li>a{
    display: flex;
    align-items: center;
    justify-content: space-between;
    width: 60%;
    margin:0 auto;
    color: rgba(0, 0, 0, 0.6);
    font-size: 14px;
    padding: 10px 30px;
}

.user-function>ul>li>a:hover{
    background-color: #f5f7fa;
    border-radius: 20px;
}

.user-function>ul>li>a>i{
    font-size: 10px;
}

.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
