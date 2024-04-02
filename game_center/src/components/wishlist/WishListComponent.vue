<template>
  <div class="wishlist-container">
    <div class="wishlist-content">
        <table class="wishlist-table">
          <thead>
            <tr>
              <th style="justify-content: unset;width: 100px;"><input type="checkbox" name="selectAll" :checked="allSelect" id="selectAll" @click="selectAll"><label for="selectAll">全选</label></th>
              <th class="orderId">心愿单号</th>
              <th class="blank_img"></th>
              <th class="nameBox">商品名称</th>
              <th style="width: 240px;">标签</th>
              <th class="th-price">单价</th>
              <th style="justify-content: right;width: 100px;">操作</th>
            </tr>
          </thead>
          <tbody>
            <template v-if="data.wishlist.length">
              <tr v-for="(item,index) in data.wishlist" class="wishlit-item" :key="item.orderId" :style="index==data.wishlist.length-1?'margin-bottom:0px':''">
                <td style="justify-content: unset;width: 100px;">
                  <input v-if="!item.isPurchase" type="checkbox" :checked="item.checkbox" :data-index="index" @click="changeSelect" class="checkbox">
                </td>
                <td class="orderId" style="color: #666;">{{ item.wishlistId }}</td>
                <td class="game-img-box" :data-id="item.gameId" @click="toDetail">
                  <img :src="item.imgUrl" class="game-img">
                </td>
                <td class="nameBox" :data-id="item.gameId" @click="toDetail">
                  <span class="game-name">{{ item.gameName }}</span>
                </td>
                <td style="width: 240px;">
                  <TagsComponent :tags="item.tags" :limit="3"></TagsComponent>
                </td>
                <td class="price" :style="item.isPurchase?'color:#24b8f2!important':(item.price==0?'color:#666':'')">{{ item.isPurchase?'已购买':(item.price==0?"免费":"￥"+item.price) }}</td>
                <td class="delete">
                  <i v-if="!item.isPurchase" class="iconfont icon-close" @click.prevent="dialogVisible = true;delType='delGoods';delIndex=index"></i>
                </td>
              </tr>
            </template>
            <template v-else>
              <tr>
                <td clospan="5" class="not-goods">请先将商品添加至心愿单</td>
              </tr>
            </template>
          </tbody>
        </table>
        <el-affix position="bottom" :offset="20">
          <div class="toPay">
            <span>已选择<strong>{{count}}</strong>件商品 <span class="delSel" @click="dialogVisible = true;delType='delSel'">删除选中</span></span>
            <div>
              <span>合计：<strong>{{ priceTotal }}</strong>元</span>
              <input type="button" value="去结算" @click="handleToPay">
            </div>
          </div>
        </el-affix>

    </div>
    <div class="pay-detail" v-if="store.showPayDetail"  style="z-index: 999;">
      <div class="pay-detail-content">
        <span style="display:block;height: 40px;line-height: 40px;text-align: center;margin-bottom: 20px;">订单详情</span>
        <ul class="pay-list">
          <li v-for="(item) in data.selectList" :key="item.orderId">
            <img :src="item.imgUrl">
            <span class="game-name">{{ item.gameName }}</span>
            <span class="price" :style="item.price==0?'color:#666':'color:#f40'">{{ item.price==0?"免费":"￥"+item.price }}</span>
          </li>
        </ul>
        <div class="pay-info">
          <!-- <span style="font-size: 14px!important;margin-left: 10px;">合计：<strong>{{ priceTotal }}</strong>元</span> -->
          <!-- <input type="button" value="支 付" style="border-bottom-right-radius: 20px;margin-left: 0;font-size: 14px;" @click="handlePay">
        -->
          <QrCode :gameIdList="data.gameIdList" :userId = "data.userInfo.userId" :priceTotal="priceTotal"></QrCode>
        </div> 
        <i class="iconfont icon-close close" @click="handleShowPay"></i>
      </div>
    </div>
    <el-dialog
      v-model="dialogVisible"
      title="提示"
      width="30%"
      :before-close="handleClose"
    >
      <span>是否删除选定的商品</span>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="dialogVisible = false">取消</el-button>
          <el-button type="primary" @click="showTip">
            确定
          </el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import request from '@/utils/request';
import TagsComponent from '../TagsComponent.vue';
import QrCode from '../QrCode.vue';
import {  onMounted, reactive, ref } from 'vue';
import { toDetail,handleTips } from '@/utils/tools';
import useStore from "@/store/index.js";
const store = useStore()

const count = ref(0)
const allSelect = ref(false)
const priceTotal =ref((0).toFixed(2))
const data = reactive({
  userInfo:{},
  wishlist:[],
  selectList:[],
  gameIdList:[]
})

const dialogVisible = ref(false)
const agreeDel = ref(false)
const delType = ref("delSel")
const delIndex = ref(0)

onMounted(()=>{
  data.userInfo = localStorage.getItem("userInfo")?JSON.parse(localStorage.getItem("userInfo")):{}
  getWishList();
})

function handleShowPay(){
  store.handleShowPay(false)
}

// 全选
function selectAll(){
  allSelect.value=!allSelect.value 
  let t_count = 0
  for(let i=0;i<data.wishlist.length;i++){
    if(!data.wishlist[i].isPurchase){
      data.wishlist[i]['checkbox']=allSelect.value
      t_count++
    }
  }

  if (allSelect.value) count.value=t_count
  else count.value = 0
  getpriceTotal()
}

// 单选
function changeSelect(e){
  let index = e.currentTarget.dataset.index
  data.wishlist[index]['checkbox']=e.currentTarget.checked
  if(e.currentTarget.checked){
    count.value++
  }else{
    count.value--
  }

  let trueCount = data.wishlist.filter(obj => obj.isPurchase === false).length; 
  if(count.value==trueCount) allSelect.value=true
  else allSelect.value=false
  getpriceTotal()
}

// 删除心愿单的商品
async function deleteGoods(){
  let index = delIndex.value
  if(agreeDel.value == true){
      if(data.wishlist[index]['checkbox']){
      count.value--
    }
    await request("/wishlist/remove",{userId:data.userInfo.userId,gameId:data.wishlist[index].gameId},"POST")
    data.wishlist.splice(index,1)
    getpriceTotal()
    let trueCount = data.wishlist.filter(obj => obj.isPurchase === false).length; 
    if(count.value==trueCount) allSelect.value=true
    else allSelect.value=false
  }
  dialogVisible.value = false
  agreeDel.value = false

}

// 获取总价
function getpriceTotal(){
  data.selectList = []
  data.gameIdList = []

  priceTotal.value=0
  for(let i = 0;i<data.wishlist.length;i++){
    if(data.wishlist[i]['checkbox'] && !data.wishlist[i].isPurchase){
      priceTotal.value+=parseFloat(data.wishlist[i].price)
      data.selectList.push(data.wishlist[i])
      data.gameIdList.push(data.wishlist[i].gameId)
    }
  }
  priceTotal.value = priceTotal.value.toFixed(2)
}

// 清空
async function delSel(){
  let selLen = data.selectList.length 
  if(selLen == 0){
    handleTips("请先选中要从心愿单删除的商品！")
    handleClose()
  }else if(agreeDel.value==true){
    let i=0
    let len = data.wishlist.length 
    while(i<len){
      if(data.wishlist[i]['checkbox']){
        await request("/wishlist/remove",{userId:data.userInfo.userId,gameId:data.wishlist[i].gameId},"POST")
        data.wishlist.splice(i,1)
        count.value--
      }else {
        i++
      }
      //动态更新长度 
      len = data.wishlist.length
    }
    getpriceTotal()
  }
  dialogVisible.value = false
  agreeDel.value = false
}

// 显示支付
function handleToPay(){
  if(!data.selectList.length){
    handleTips("请先选中要付款的商品！")
  }else{
    store.handleShowPay(true)
  }
}

// 支付发请求
// async function handlePay(){
//   handleTips("正在支付...","success");      
//   let res = await request("/wishlist/pay",{userId:data.userInfo.userId,gameIdList:data.gameIdList.toString()},"POST")
//   setInterval(()=>{
//     handleTips(res.data.payMsg,"success")
//   },1200)
//   setInterval(()=>{
//     window.open("/wishlist","_self")
//   },1500)

// }

async function getWishList(){
  let res = await request("/wishlist",{userId:data.userInfo.userId})
  data.wishlist=res.data.wishList;
  
  for(let i =0;i<data.wishlist.length;i++){
    data.wishlist[i]['checkbox']=false
  }

}

const handleClose = () => {
  dialogVisible.value = false
}

function showTip(){
  agreeDel.value = true
  if(delType.value=="delSel"){
    delSel()
  }else{
    // let index = e.currentTarget.dataset.index;
    deleteGoods()
  }
}
</script>

<style scoped>
.wishlist-content{
    position: relative;
    width:1200px;
    margin:20px auto;
}

.wishlist-table{
  width: 100%;
  padding:20px 30px;
  border-radius: 10px;
  box-shadow: 0 2px 30px 2px rgba(0,0,0,0.1);
}
.wishlist-table tr{
  display: flex;
  justify-content:space-between;
  margin-bottom: 20px;
  padding: 10px;
}

.wishlit-item:hover{
  background-color: #f2f4f6;
  border-radius: 10px;
}

.wishlist-table th,
.wishlist-table td{
  display: flex;
  align-items: center;
  justify-content: center;
  flex-grow: 0;
  flex-shrink: 0;
  width: 200px;
}

.game-img-box{
  width: 70px!important;
}

.wishlist-table th{
  width: 200px;
  text-align: center;
}

.th-price{
  width: 100px!important;
}

.blank_img{
  height: 50px!important;
  width: 70px!important;
}

input[type=checkbox]{
  margin-top: 2px;
  margin-right: 10px;
}

.orderId{
  width: 160px!important;
  text-align: center;
}

.nameBox{
  justify-content: unset!important;
  width: 200px!important;
  text-align: left!important;
}

.game-img{
  height: 50px;
  width: 50px;
  margin-right: 20px;
  border-radius: 14px;
  cursor: pointer;
}

.wishlist-table .game-name{
  width: 200px;
  font-size: 14px;
  text-overflow: ellipsis;
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
  cursor: pointer;
}

.wishlist-table .price{
  color: #f40;
  width: 100px!important;
}

.delete{
  justify-content: right!important;
  width: 100px!important;
}

.delete>i{
  width: 30px!important;
  height: 30px;
  line-height: 30px;
  text-align: center;
  border-radius: 50%;
  font-size: 20px;
  color: rgba(0,0,0,0.6);
  cursor: pointer;
}

.delete:hover>i{
  background-color: #24B8F2;
  color: white;
}

.toPay{
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
  width: 1200px;
  background-color: white;
  box-shadow: 0 2px 30px 2px rgba(0,0,0,0.1);
  /* border-radius: 10px; */

}

.toPay>span{
  height: 50px;
  line-height: 50px;
  margin-left: 20px;
  font-size: 16px;
}

.toPay>span>strong{
  color: #24B8F2;
  margin:0 3px;
}

.pay-info,
.toPay>div{
  display: flex;
  align-items: center;
}

.pay-info>span,
.toPay>div>span{
  font-size: 16px;
  color: #24B8F2;
}

.pay-info>span>strong,
.toPay>div>span>strong{
  font-size: 20px;
  margin: 0 5px;
}

.pay-info>input[type=button],
.toPay>div>input[type=button]{
  margin-left: 20px;
  background-color: #24B8F2;
  color: white;
  width: 200px;
  height: 50px;
  outline: unset;
  border: 0;
  font-size: 16px;
  cursor: pointer;
}

.not-goods{
  height: 200px;
  width: 100%!important;
  line-height: 300px;
  text-align: center;
  font-size: 20px;
  color: rgba(0,0,0,0.6);
}

.delSel{
  font-size: 12px;
  color: rgba(0,0,0,0.6);
  margin-left: 10px;
  cursor: pointer;
}

.pay-detail{
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.6);;
}

.pay-detail-content{
  position: relative;
  height: 550px;
  width: 360px;
  padding-top: 10px;
  background-color: white;
  border-radius: 20px;
}

.pay-detail-content>.close{
  position: absolute;
  top: -20px;
  right: -20px;
  font-size: 20px;
  color: rgba(0,0,0,0.6);
  cursor: pointer;
  border-radius: 50%;
  background-color: white;
}

.pay-detail-content>.close:hover{
  color: white;
  background-color: #24B8F2;
}

.pay-list{
  height: calc(100% - 240px);
  overflow-x: hidden;
  overflow-y:auto;
  margin: 10px;
  margin-bottom: 20px;
}

.pay-list li {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 10px;
  padding: 10px;
}

.pay-list li:hover{
  background-color: #f2f4f6;
}

.pay-list img{
  height: 40px;
  width: 40px;
  border-radius: 10px;
}

.pay-list li .game-name{
  margin: 0 10px;
  width: calc(100% - 140px);
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 2;
}

.pay-list li .price{
  display: block;
  width: 60px!important;
  font-size: 14px;
  text-align: center;
}

.pay-info{
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 150px;
  width: 100%;
}
</style>