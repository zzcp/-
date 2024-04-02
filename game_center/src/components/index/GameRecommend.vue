<template>
  <div class="gameList-content">
    <h3 class="title">{{props.title}}</h3>
    <ul>
        <li v-for="(item,index) in data.recommendList" :key="index" :data-id="item.gameId" @click="toDetail">
            <div class="top-img">
                <img :src="item.gameBg">
            </div>
            <div class="game-info">
                <img :src="item.imgUrl" alt="">
                <div class="introduction-info">
                    <span class="name">{{ item.gameName }}</span>
                    <span class="introduction">{{ item.introduction }}</span>
                </div>
                <div class="score-box">
                    <span>{{ item.score }}</span>
                    <StarsComponent :score="item.score"></StarsComponent>
                </div>
            </div>
        </li>
    </ul>
  </div>
</template>

<script setup>
import request from '@/utils/request';
import StarsComponent from '../StarsComponent.vue';
import { toDetail } from '@/utils/tools';
import { reactive,defineProps, onMounted } from 'vue';
const props = defineProps({
  title: {
    type:String, // 参数类型
    default: "精品推荐", //默认值
  },
})

const data = reactive({
    recommendList:[]
})

onMounted(()=>{
    getRecommendList()
})

async function getRecommendList(){
    let res = await request("/recommend");
    data.recommendList=res.data.recommendList;
}
</script>

<style scoped>

.gameList-content{
    display: flex;
    flex-direction: column;
    /* height: 500px; */
    width: 820px;
    padding: 10px 0;
}

.title{
    font-size: 20px;
    margin-bottom: 10px;
    height: 30px;
    line-height: 30px;
}

.gameList-content>ul{
    width: 750px;
}

.gameList-content>ul>li{
    width: 100%;
    margin-bottom: 20px;
    border:1px solid rgba(0,0,0,0.1);
    border-radius: 15px;
    transition: .8s;
    cursor: pointer;
}

.top-img{
    height: 292px;
    border-top-right-radius: 10px;
    border-top-left-radius: 10px;
    overflow: hidden;
}

.top-img>img{
    width: 100%;
    margin-bottom: 10px;
    transition: .8s;
}

.top-img>img:hover{
    /* height: 110%; */
    transform: scale(1.2);
}

.game-info{
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 0 20px 10px 20px;
}

.game-info>img{
    height: 60px;
    width: 60px;
    border-radius: 15px;
}

.game-info>.introduction-info{
    display: flex;
    flex-direction: column;
    width: calc(100% - 260px);
}

.game-info>.introduction-info>.name{
    margin-bottom: 6px;
}

.game-info>.introduction-info>.introduction{
    font-size: 14px;
    color: rgba(0,0,0,0.6);
}

.score-box{
 display: flex;
 align-items: center;
}

.score-box>span{
    width: 80px;
    height: 80px;
    line-height: 80px;
    text-align: center;
    font-size: 36px;
    /* color: #24B8F2; */
}
</style>