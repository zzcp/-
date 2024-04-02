<template>
  <div class="gameList-content">
    <ul class="result-list" v-if="currentType != 'user' && props.resultList.length">
        <li v-for="(item,index) in props.resultList" :key="item.gameId" :class="(index+1)%4!=0?'right':''">
            <img :src="item.imgUrl" alt="">
            <div class="game-info">
                <p class="game-name" :data-id="item.gameId" @click="toDetail">{{item.gameName}}</p>
                <TagsComponent :tags="item.tags" :limit="2"></TagsComponent>
                <span class="score">评分：{{ item.score }}</span>
            </div>
        </li>
    </ul>
    <ul v-else-if="currentType == 'user' && props.resultList.length"></ul>
  </div>
</template>

<script setup>
import { toDetail } from '@/utils/tools';
import {  ref , defineProps , onMounted} from 'vue';
import TagsComponent from '../TagsComponent.vue';
const props = defineProps({
    resultList:{
        type:Array,
        default:()=>[]
  },
    type:{
        type:String,
        default:"game"
  }
})

// const gameList = reactive([])
const currentType = ref("game")

onMounted(()=>{
    currentType.value=props.type
})

// function changeType(e){
//     currentType.value=e.currentTarget.dataset.type
// }
</script>

<style scoped>
.gameList-content{
    display: flex;
    flex-direction: column;
    /* height: 500px; */
    width: 1200px;
    padding: 10px 0;
}

.title{
    font-size: 20px;
    margin-bottom: 10px;
    height: 30px;
    line-height: 30px;
}

.not-result{
    height: 400px;
    width:1200px;
    text-align:center;
    line-height: 400px;
    font-size: 20px;
    color: rgba(0,0,0,0.6);
}

.result-list{
    display: flex;
    flex-wrap: wrap;
    margin-top: 10px;
}

.result-list>li{
    display: flex;
    width: 280px;
    margin-bottom: 30px;
    cursor: pointer;
}

.result-list>li>img{
    width: 100px;
    height: 100px;
    border-radius: 20px;
    margin-right: 10px;
}


.game-info{
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    height: 100px;
}

.right{
    margin-right: 20px;
}

.game-name{
    font-size: 16px;
    margin-top: 5px;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
}
.game-info>.game-name:hover{
    color: #24B8F2;
}

.score{
    font-size: 12px;
    color: rgba(0,0,0,0.35);
    margin-bottom: 5px;
}
</style>