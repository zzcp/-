<template>
  <div class="toplist-content">
    <div style="display: flex;justify-content: space-between;align-items: center;">
        <h3 class="title">{{props.title}}</h3>
        <span class="more" @click="toTopList">更多<i class="iconfont icon-mono-right-full"></i></span>
    </div>
    <ul>
        <li v-for="(item,index) in data.toplist" :key="index">
            <span>{{ index+1 }}</span>
            <img :src="item.imgUrl" alt="" @click="toDetail" :data-id="item.gameId">
            <div class="game-info">
                <p class="game-name" @click="toDetail" :data-id="item.gameId" :title="item.gameName">{{ item.gameName }}</p>
                <TagsComponent :tags="item.tags" :limit="2" :isBig="false"></TagsComponent>
                <p class="score">评分：{{ item.score }}</p>
            </div>
        </li>
    </ul>
  </div>
</template>

<script setup>
import request from '@/utils/request';
import { toDetail } from '@/utils/tools';
import { defineProps, onMounted, reactive } from 'vue';
import TagsComponent from '../TagsComponent.vue';
import { useRouter } from 'vue-router';
const router = useRouter()

const props = defineProps({
  title: {
    type:String, // 参数类型
    default: "热度排行榜", //默认值
  },
})

const data = reactive({
    toplist:[]
})

onMounted(()=>{
    getTopList()
})

async function getTopList(){
    let res = await request("/toplist",{tlId:2001,limit:15})
    data.toplist = res.data.toplist.splice(0,20)
}

function toTopList(){
    router.push("/toplist/2001")
}
</script>

<style scoped>
.toplist-content{
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    /* height: 500px; */
    width: 350px;
    padding: 10px 0;
}

.title{
    font-size: 20px;
    height: 30px;
    line-height: 30px;
}

.more{
    font-size: 12px;
    color: rgba(0,0,0,0.6);
    border-radius: 6px;
    border: 1px solid rgba(0,0,0,0.2);
    padding: 2px 8px;
    cursor: pointer;
}

.more:hover{
    background-color: #f2f4f6;
}

.more>i{
    font-size: 10px;
    margin-left: 8px;
}

ul{
    display: flex;
    flex-wrap: wrap;
    height: calc(100% - 30px);
    width: 100%;
}

ul>li{
    display: flex;
    align-items: center;
    width: 100%;
    /* margin: 10px 0; */
    padding:10px 0;
    border-bottom: 1px solid rgba(0,0,0,0.1);
}

ul>li>span{
    width: 22px;
    text-align: center;
    font-weight: 600;
    font-size: 16px;
    color: rgba(0,0,0,0.6);
}

ul>li>img{
    height: 50px;
    width: 50px;
    border-radius: 10px;
    margin: 0 10px;
    cursor: pointer;
}

.game-info{
    display: flex;
    flex-direction: column;
}

.game-info>.game-name{
    font-size: 14px;
    cursor: pointer;
    width: 260px;
    overflow: hidden;
    text-overflow: ellipsis;
    /* display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical; */
    white-space: nowrap;
}

.game-info>.game-name:hover{
    color: #24B8F2;
}

.game-info>.tags{
    display: flex;
}

.game-info>.tags>span{
    font-size: 10px;
    color: rgba(0,0,0,0.4);
    margin: 8px 5px 8px 0;
    padding: 2px 5px;
    border-radius: 4px;
    border: 1px solid rgba(0,0,0,0.1);
    cursor: pointer;
}

.game-info>.tags>span:hover{
    color: #24B8F2;
    border: 1px solid #24B8F2;
}

.game-info>.score{
    font-size: 12px;
    color: rgba(0,0,0,0.6);
}
</style>