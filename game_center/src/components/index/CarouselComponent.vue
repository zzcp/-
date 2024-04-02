<template>
  <div class="carousel-container">
    <el-carousel :interval="4000" type="card" height="300px" trigger="click">
        <el-carousel-item v-for="(item,index) in data.list" :key="index">
            <!-- <div class="banner"> -->
                <img class="carousel-img" :src="item.url" @click.prevent="toDetail" :data-id="item.gameId"/>
                <!-- <div class="typeTitle" :class="item.titleColor">{{ item.typeTitle }}</div> -->
            <!-- </div> -->
        </el-carousel-item>
    </el-carousel>
  </div>
</template>

<script setup>
import { onMounted, reactive } from 'vue';
import request from '@/utils/request';
import { toDetail } from '@/utils/tools';

const data = reactive({
    list:[]
})

onMounted(()=>{
    getCarousel()
})

async function getCarousel(){
    let res = await request("/carousel")
    data.list = res.data.carouselList
}

</script>

<style scoped>
.el-carousel{
    width: 1200px;
    margin: 0 auto;
    border-radius: 15px;
    /* overflow-y: unset; */
}

.el-carousel__item{
    width: 50%;
    height: 80%;
    left: 0%;
    top: 10%;
    border-radius: 10px!important;
}

.el-carousel__item:nth-child(2n) {
    background-color: #99a9bf;
}

.el-carousel__item:nth-child(2n+1) {
    background-color: #d3dce6;
}


:deep(.el-carousel__item--card.is-active){
    width: 70%;
    height: 100%;
    left: -10%;
    top: 0;
}

/* 小圆点 */
:deep(.el-carousel__indicators--horizontal) {
    /* bottom: -40px; */
    position: relative;
    /* margin: auto; */
    display: flex;
    justify-content: center;
    align-items: center;
}
:deep(.el-carousel__indicator--horizontal .el-carousel__button){
    background-color: #333;
    height: 10px;
    width: 10px;
    border-radius: 50%;
}
:deep(.el-carousel__indicator--horizontal.is-active .el-carousel__button){
    background-color: #24B8F2;
    height: 10px;
    width: 10px;
    border-radius: 50%;
}

.carousel-container{
  display: flex;
  align-items: center;
  justify-content: center;
  height: 500px;
  /* width: 100%; */
  background: url("https://s1.hdslb.com/bfs/static/game-web/duang/home/asserts/home_bg.e09be3a.png") no-repeat;
  background-size: 1800px 500px;
  background-position: center;
}

.el-carousel__item .carousel-img{
    width: 100%!important;
    height: 100%!important;
    box-shadow: 0 12px 24px 0 rgba(5,45,75,.2);
}
</style>