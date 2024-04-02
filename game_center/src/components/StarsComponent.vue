<template>
    <div class="star" v-if="currentScore!='not'" :style="'width:'+count*16.11+'px'">
        <div class="no-color">
            <i v-for="index in props.count" :key="index" class="iconfont icon-star"></i>
        </div>
        <div class="have-color" :style="'width:'+currentScore+'%'">
            <i v-for="index in props.count" :key="index" class="iconfont icon-star"></i>
        </div>
    </div>
</template>

<script setup>
import { defineProps, onMounted, ref } from 'vue';
const props = defineProps({
    // 星星数
    count:{
        type:Number,
        default:5
    },
    // 评分数
    score:{
        type:String,
        default:"2"
    }
})

const currentScore = ref(2)
onMounted(()=>{
    let index = props.score.indexOf("分")
    // x.x分
    if(index!=-1&&props.score.indexOf("评分")==-1){
        currentScore.value = parseFloat(props.score.slice(0,index))*10
    }
    // x.x
    else if(index==-1&&props.score.indexOf("评分")==-1){
        currentScore.value = parseFloat(props.score)*10
    }
    // 评分过少、暂无评分
    else{
        currentScore.value='not';
    }
})

</script>

<style scoped>

.star{
  position: relative;
  width: 80.55px;
}

.star .no-color>i{
  width: 20px;
  /* margin-right: 2px; */
  color: #e7edf3;
}

.star .have-color{
  position: absolute;
  top: 0;
  left: 0;
  overflow: hidden;
}

.star .have-color>i{
  width: 20px;
  /* margin-right: 2px; */
  color: #7dd6fa;
}

</style>