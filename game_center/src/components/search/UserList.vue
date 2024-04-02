<template>
  <div>
    <div>
        <ul class="user-list">
            <li v-for="(item,index) in props.resultList" :key="item.userId" :class="(index+1)%3!=0?'right':''">
                <img :src="config.url+item.imgUrl">
                <div class="info">
                    <span class="name">{{ item.name }}</span>
                    <div class="signature-box">
                        <span>{{ item.signature }}</span>
                    </div>
                </div>
            </li>
        </ul>
    </div>
  </div>
</template>

<script setup>
import config from "@/utils/config";
import { defineProps, onMounted } from 'vue';
const props = defineProps({
    resultList:{
        type:Array,
        default:()=>[]
    }
})

onMounted(()=>{
})
</script>

<style scoped>
.user-list{
    display: flex;
    /* justify-content: space-between; */
    flex-wrap: wrap;
}

.user-list>li{
    display: flex;
    padding: 10px;
    width: calc(32.2% - 20px);
    margin-bottom: 10px;
}

.user-list>li>img{
    height: 80px;
    width: 80px;
    margin-right: 10px;
}

.info{
    display: flex;
    justify-content: center;
    flex-direction: column;
    width: calc(100% - 90px);
}

.info>.name{
    width: 100%;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-bottom: 10px;
    font-size: 16px;
    color: #666;
}

.signature-box{
    display: flex;
    align-items: center;
}

.signature-box>span{
    margin-right: 20px;
    font-size: 12px;
    color: #666;
    overflow: hidden;
    text-overflow: ellipsis;
    display: -webkit-box;
    -webkit-line-clamp: 2;
    -webkit-box-orient: vertical;
}

.right{
    margin-right: 20px;
}
</style>