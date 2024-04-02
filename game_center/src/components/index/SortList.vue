<template>
  <div class="sortlist-content">
    <div style="display: flex;justify-content: space-between;align-items: center;">
        <h3 class="title">分类</h3>
        <span class="more" @click="toSort">更多<i class="iconfont icon-mono-right-full"></i></span>
    </div>
    <ul class="sort-list-ul">
        <li v-for="item in data.sortList" :key="item.sortId" @click="toSearch(item.sortName,'tag')">{{ item.sortName }}</li>
    </ul>
  </div>
</template>

<script setup>
import { onMounted, reactive } from 'vue';
import { toSearch } from '@/utils/tools';
import request from '@/utils/request';
import { useRouter } from 'vue-router';

const router = useRouter();
const data = reactive({
    sortList:[]
})

onMounted(()=>{
    getSort();
})

async function getSort(){
    let res = await request("/sortlist",{limit:10})
    data.sortList = res.data.sortlist 
}

function toSort(){
    router.push("sort")
}
</script>

<style scoped>
.sortlist-content{
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

.sort-list-ul{
    display: flex;
    flex-wrap: wrap;
    margin-top: 10px;
}

.sort-list-ul>li{
    font-size: 14px;
    padding: 10px 20px;
    background-color:#f2f4f6;
    color:  rgba(0,0,0,0.85);
    border-radius: 8px;
    margin-right: 10px;
    margin-bottom: 10px;
    cursor: pointer;
}

.sort-list-ul>li:hover{
    background-color: rgb(229, 247, 255);
    color: #24B8F2;
}
</style>