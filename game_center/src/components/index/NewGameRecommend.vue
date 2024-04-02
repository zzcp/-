<template>
  <div>
    <div class="ngr-content">
        <h3 class="title">新游推荐</h3>
        <div style="transition: .4s;cursor: pointer;" @click="toDetail" :data-id="data.currentGame.gameId">
            <img :src="data.currentGame.gameBg" class="show-img">
            <div class="game-introduction">
                <h3>{{ data.currentGame.gameName }}</h3>
                <p>{{ data.currentGame.introduction }}</p>
            </div>
        </div>
        <ul>
            <li v-for="(item,index) in data.newGameList" @click="toDetail" :data-id="item.gameId" :style="active!=index?'border: 2px rgba(0,0,0,0.08) solid':''" :key="index" :data-index="index" @mouseenter.prevent="changeGame">
                <div :class="active!=index?'mask':''"  :data-index="index"></div>
                <img :src="item.imgUrl" alt="">
                <div class="game-name-box">
                    <p :title="item.gameName">{{ item.gameName }}</p>
                    <span>{{ item.tag }}</span>
                </div>
            </li>
        </ul>
    </div>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import {toDetail} from "../../utils/tools.js"
import { ref,reactive, onMounted } from 'vue';
// 设置移动的位置
const active = ref(0)

const data = reactive({
    newGameList:[],
    currentGame:{}
})

onMounted(()=>{
    getNewGameList()
})

async function getNewGameList(){
    let res = await request("/newgame");
    data.newGameList = res.data.newGameOrder;
    data.currentGame.gameId=res.data.newGameOrder[0].gameId
    data.currentGame.gameBg=res.data.newGameOrder[0].gameBg
    data.currentGame.introduction=res.data.newGameOrder[0].introduction
    data.currentGame.gameName=res.data.newGameOrder[0].gameName
}
// 设置定时
let timer = ref("")

// 设置自动点击下一个
function setAutoNext(){
    timer = setInterval(()=>{
        active.value++
        if(active.value>data.newGameList.length-1){
            active.value=0
        }
        data.currentGame.gameId=data.newGameList[active.value].gameId
        data.currentGame.gameBg=data.newGameList[active.value].gameBg
        data.currentGame.introduction=data.newGameList[active.value].introduction
        data.currentGame.gameName=data.newGameList[active.value].gameName
    },5000)
}

// 改变当前轮播游戏信息
function changeGame(e){
    clearInterval(timer)
    active.value=e.currentTarget.dataset.index
    data.currentGame.gameId=data.newGameList[active.value].gameId
    data.currentGame.gameBg=data.newGameList[active.value].gameBg
    data.currentGame.introduction=data.newGameList[active.value].introduction
    data.currentGame.gameName=data.newGameList[active.value].gameName
    setAutoNext()
}

onMounted(()=>{
    setAutoNext()
})
</script>

<style scoped>
.ngr-content{
    position: relative;
    width: 1200px;
    margin:0 auto;
}

.title{
    position: absolute;
    top: 0;
    left: 0;
    height: 40px;
    line-height: 40px;
    font-size: 20px;
}

.ngr-content>div{
    position: relative;
}

.show-img{
    width: 100%;
    transition: all .6s;
}

.game-introduction{
    position: absolute;
    top: 130px;
    left: 50px;
}

.game-introduction>h3{
    color: white;
    font-size: 18px;
    margin-bottom: 5px;
}

.game-introduction>p{
    color: white;
    font-size: 14px;
}

.ngr-content>ul{
    display: flex;
    align-items: center;
    justify-content: space-between;
    width:100%;
    margin-top: 5px;
}

.ngr-content>ul>li{
    position: relative;
    display: flex;
    align-items: center;
    width: 16%;
    padding: 10px 20px ;
    border-radius: 10px;
    cursor: pointer;
    border: 2px solid #24B8F2;
}

.ngr-content>ul>li>img{
    border-radius: 10px;
    height: 60px;
    width: 60px;
    margin-right:20px;
}

.game-name-box{
    display: flex;
    flex-direction: column;
}

.game-name-box>p{
    width: 120px;
    text-overflow: ellipsis;
    overflow: hidden;
    white-space: nowrap;
}

.game-name-box>span{
    font-size: 12px;
    margin-top: 5px;
    color: rgba(0,0,0,0.6);
}

.mask{
    position: absolute;
    top: 0;
    left: 0;
    height: 100%;
    width: 100%;
    background-color: rgba(255,255,255,0.6);
    border-radius: 10px;
    z-index: 2;
}

/* .active-li{
    border: 2px solid #24B8F2!important;
} */
</style>