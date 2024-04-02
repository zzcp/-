<template>
  <div class="footer-container">
    <div class="footer-content">
      <div class="footer-left">
        <div class="footer-logo">
          <img src="../assets/logo.png" alt="">
        </div>
        <ul>
          <li v-for="(item,index) in related_link_list" :key="index">
            <a :href="item.url">{{ item.name }}</a>
          </li>
        </ul>
      </div>
      <div class="footer-right">
        <p>相关链接</p>
        <ul class="icon-list">
          <li v-for="(item,index) in icon_list" :key="index">
            <a :href="item.src" target="_blank">
              <img :src="item.icon" alt="">
              <span>{{ item.name }}</span>
            </a>
          </li>
        </ul>
      </div>
    </div>
    <div class="footer-bottom">
      <p>抵制不良游戏 拒绝盗版游戏 注意自我保护 谨防受骗上当 适度游戏益脑 沉迷游戏伤身 合理安排时间 享受健康生活</p>
      <p>@：{{nowDate}}</p>
    </div>
    <SideBar></SideBar>
  </div>
</template>

<script setup>
import config from "@/utils/config";
import SideBar from "./SideBar.vue"
import { ref,reactive } from 'vue';
const icon_list = reactive([
  {
    name:"bilibili游戏",
    icon:config.url+"/img/footer/bili.png",
    src:"https://game.bilibili.com"
  },
  {
    name:"Github",
    icon:config.url+"/img/footer/github.png",
    src:"https://github.com"
  },
  {
    name:"Gitee",
    icon:config.url+"/img/footer/GITEE.png",
    src:"https://gitee.com/Tsundere-cat/game_center"
  },
  {
    name:"CSDN",
    icon:config.url+"/img/footer/csdn.png",
    src:"https://blog.csdn.net"
  }
])

const nowDate = ref("")


function setDate(){
    setInterval(()=>{
      nowDate.value=new Date().toLocaleString()
    },1000)
  }
setDate()

const related_link_list = reactive([
  {
    name:"游戏中心",
    url:"/"
  },
  {
    name:"加入我们",
    url:"/"
  },
  {
    name:"用户协议",
    url:"/"
  },
  {
    name:"隐私政策",
    url:"/"
  }
])

</script>

<style scoped>
.footer-content{
  display: flex;
  justify-content: space-between;
  width: 1200px;
  margin: 0px auto;
  padding: 20px 0 10px 0;
  border-bottom:1px solid rgba(0, 0, 0, 0.08);
}

.footer-left{
  display: flex;
  flex-wrap: wrap;
}

.footer-left>.footer-logo{
  width: 100%;
}

.footer-left>.footer-logo>img{
  height: 40px;
}

.footer-left>ul{
  display: flex;
  align-items: center;
  margin: 20px 0;
}

.footer-left>ul>li>a{
  margin-right:20px;
  color: rgba(0, 0, 0, 0.6);
  font-size: 14px;
}

.footer-left>ul>li>a:hover{
  color: #24B8F2;
}

.footer-right>p{
  font-size: 16px;
  color: rgba(0, 0, 0, 0.6);
  padding: 0 20px;
  margin-bottom: 20px;
}

.icon-list{
  display: flex;
  align-items: center;
}

.icon-list>li{
  margin:0 20px;
}

.icon-list>li>a{
  display: flex;
  flex-direction: column;
  align-items: center;
  cursor: pointer;
}

.icon-list>li>a>img{
  height: 40px;
  width: 40px;
  border-radius: 50%;
}

.icon-list>li>a>span{
  font-size: 12px;
  margin-top: 5px;
  color: rgba(0, 0, 0, 0.6);
}

.footer-bottom{
  width: 1200px;
  margin: 0px auto;
  padding-top: 10px;
}

.footer-bottom>p{
  color: rgba(0, 0, 0, 0.6);
  font-size: 14px;
  margin: 10px 0;
}
</style>
