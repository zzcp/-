<template>
  <div  class="detail-container">
    <div class="background-img" v-if="data.gameInfo.gameBg" :style="data.gameInfo.gameBg?`background-image:url(`+data.gameInfo.gameBg+`)`:''">
      <div class="mask" ></div>
    </div>
    <div class="detail-content">
      <div class="logo" v-if="data.gameInfo.gameLogo || data.gameInfo.gameBg" :style="'background-image:url('+data.gameInfo.gameLogo+')'"></div>
      <div class="content-top">
        <div class="content-top-left">
          <video class="video" :src="data.gameInfo.videoUrl" controls></video>
        </div>
        <div class="content-top-right">
          <div class="game-info-top">
            <img :src="data.gameInfo.imgUrl">
            <h2 class="game-name">{{ data.gameInfo.gameName }}</h2>
            <div class="score-box" v-if="data.gameInfo.score">
              <StarsComponent :score="data.gameInfo.score"></StarsComponent>
              <span class="game-score">{{ data.gameInfo.score }}</span>
            </div>
          </div>
          <div class="game-tags">
            <span v-for="(item,index) in data.gameInfo.tags" :key="index" @click="toSearch(item,'tag')">{{ item }}</span>
          </div>
          <div class="game-info-bottom">
            <div>
              <span class="price">{{ data.gameInfo.price == 0?"免费":"￥"+handleToFixed(data.gameInfo.price,2) }}</span>
              <div class="add-game" :class="wishlistObj.flag?'remove-game':''" @click="addWishList">{{ wishlistObj.title }}</div>
            </div>
            <div class="scan-code">
              <span>扫码即可了解更多</span>
              <img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAGEAAABhCAIAAABJZFj0AAADKklEQVR4nO2c0W7DIAxFk6n//8vdQyU0mZpzbaimTfe8TMsIUO86gO3mfj6fl1ny9dsT+AM8Xj/u+2538VLi3MNQ6PhT1nK+JRBuyXoujYWMPq0jxjZiHuF3/RGe6X9mdoRqn/sLS/tzXdaRgm3ERF8bZH6BS89i0cm6ysbKeliMhVQ/12UdKdhGTOprVRpLD7pMw5U+gXXEHNOR8q8WtTY3wxs/ejK3jhjbiEl9rarexlkcfQp3WI0QQsMrrSPGNmKir7V3IspZ5HVFb5CNsnBSPNY0sI4Y24i5T+2+dqLU2XKGHS6C6Aexjpiyjhr/Otzm7N+YzaqxWZuvWEeMbcTcVU0O8EE7Owhuf94OPXeuPLOzvRjOf56edcTYRky6rlXl3ciL6IcVHAvnr3yQrE/riLGNmFhbo8eSM9HqTtceS1mt9mtrBtYRYxsx5dzRTsWYuNZgmY5eM7dokF33ea0D6wjrprLnaOO4r29/sklWs5VKCYp1xNhGTDyL4PZ/Bs8ioeWbScilxdmNWYxhMbTPIiexjRj2tcCOaPFGcfujHPdF91TSOdYRYxsxvIcMmmw4YwAXo52VUY8Q6EFE64hJ8yKxXf052oiWVmuxjkR1EeuIsY2YY3mR1RhywFQ/1qx7aExmcaN1xNhGjFoPuV/msug83Iux2sVYep3zeg6XzyIlbCOmX+ffSO/pR+0wRDVKPY/STv9f1pEC57Kx8DxcHzQqrDDAmt04/6ldDzljHTG2EcPfXc8etKGBsqmp5rLbRVyLoRspFuuIsY0YNd+P+h8sWlYdoXHu1+vB9GlbR4xtxMR49kAPV+vVKtXSgY+i74GtI8Y2Ys5/DxKT8Y0GehC6mgRTerCOmGPvh1RqObKxDmaosYcwqDIZ64ixjZhj74dUntnV7U/mU0dSknon1hFjGzHH3g+5UxCgd45DtGvJFl9PsI4Y24g59l6/mezcr6811UD4fAXzVNne8ifWEfNBHTVKUPYP2PthXz+zO9hGzLH3QzZ6aJQmihsoZVZibcFlHSnYRkyaF9FpR8jSOZ0o09n//sLAOmJsI+ZYXuQfYx0xthHzDclyLbJnpcixAAAAAElFTkSuQmCC">
            </div>
          </div>
        </div>
      </div>
      <div class="content-bottom">
        <div class="content-bottom-left">
          <div>
            <ul class="nav-ul">
              <li>详情</li>
            </ul>
            <div class="detail-recommend">
              <template v-if="data.gameInfo.introduction">
                <ParagraphComponent :paragraph="data.gameInfo.introduction"></ParagraphComponent>
              </template>
              <ul class="screenshot">
                <li v-for="(item,index) in data.gameInfo.screenshot" :key="index" :style="index==data.gameInfo.screenshot.length-1?'margin-right:0':''" :data-index="index" @click.prevent="showBSs">
                  <img :src="item">
                </li>
              </ul>
              <template v-if="data.gameInfo.bintroduction">
                <ParagraphComponent title="游戏简介" :paragraph="data.gameInfo.bintroduction"></ParagraphComponent>
              </template>
              <template v-if="data.gameInfo.updateInfo&&data.gameInfo.updateInfo.length">
                <ParagraphComponent title="最近更新" :paragraph="data.gameInfo.updateInfo.length==2?data.gameInfo.updateInfo[1]:data.gameInfo.updateInfo[0]" :updateInfo="data.gameInfo.updateInfo.length==2?data.gameInfo.updateInfo[0]:''"></ParagraphComponent>
              </template>
              <div class="detail-info-box" v-if="data.gameInfo.gameInfo && data.gameInfo.gameInfo.length">
                <span class="title">详细信息</span>
                <ul>
                  <li class="" v-for="(item,index) in data.gameInfo.gameInfo" :key="index">{{ item }}</li>
                </ul>
              </div>
            </div>
          </div>
          <div class="comment-box">
            <span class="title">游戏评价</span>
            <div class="take-comment">
              <textarea cols="8" rows="10" maxlength="999" placeholder="请输入您的评论" v-model="commentText"></textarea>
              <input type="submit" value="发 表" @click="submitCommend">
              <div class="score-star" v-if="data.userInfo&&data.userInfo.userId">
                <div>
                  <img :src="config.url+data.userInfo.imgUrl">
                  <span class="name">{{ data.userInfo.name }}</span>
                  <div class="no-color">
                      <i v-for="index in 5" :key="index" class="iconfont icon-star" :class="data.scoreIndex>=index?'have-color':''" :data-index="index" @mouseover="handleSelectScore"></i>
                  </div>
                </div>
              </div>
            </div>
            <CommentList :commentList="data.commentList" @updateComment="updateComment" @getCommentList="getCommentList"></CommentList>
            <div style="display: flex;justify-content: center;margin: 20px 0;" v-if="data.commentList.length">
              <el-pagination background layout="prev, pager, next" :total="data.cTotal" :page-size="data.cLimit" :hide-on-single-page="data.cTotal<=data.cLimit?true:false" @current-change="handleCurrentChange"/>
            </div>
          </div>
        </div>
        <div class="content-bottom-right">
          <div v-if="data.developerGames.length!=1">
            <span class="title">厂商更多游戏推荐</span>
            <RelatedList :relatedList="data.developerGames"></RelatedList>
          </div>
          <div>
            <span class="title">相关游戏推荐</span>
            <RelatedList :relatedList="data.relatedList"></RelatedList>
          </div>
          
        </div>
      </div>
    </div>
    <div class="showBigImg" v-show="showBigScreenshot">
      <div v-if="data.gameInfo.screenshot&&data.gameInfo.screenshot.length">
        <i class="iconfont icon-close i-close" @click="showBigScreenshot=false"></i>
        <i class="iconfont icon-mono-left-full i-left" @click="handlePre"></i>
        <i class="iconfont icon-mono-right-full i-right" @click="handleNext"></i>
        <div id="screenshotBox">
          <ul>
            <li v-for="(item,index) in data.gameInfo.screenshot" :key="index">
              <img :src="modifySuffix(item,'@302h.webp')" class="bigScreenshot" >
            </li>
          </ul>
        </div>
        <span class="num">{{ currentBSs.index+1+' / '+data.gameInfo.screenshot.length }}</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import ParagraphComponent from "./ParagraphComponent.vue";
import StarsComponent from "../StarsComponent.vue"
import CommentList from "./CommentList.vue";
import RelatedList from './RelatedList.vue';

import {modifySuffix,handleToFixed,toSearch,getUserInfo,handleTips} from "@/utils/tools";
import { useRoute } from 'vue-router';

import {  onMounted, reactive, ref } from 'vue';
import config from "@/utils/config";
import request from '@/utils/request';

import useStore from "@/store/index.js";
const store = useStore()

const route = useRoute()

const showBigScreenshot = ref(false)
const wishlistObj = reactive({
  title:"添加到心愿单",
  flag:false,
  isPurchase:false
})
const commentText = ref("")
const currentBSs = reactive({
  index:0,
  width:0
})

const data = reactive({
  gameInfo:{},
  userInfo:{},
  relatedList:[],
  commentList:[],
  developerGames:[],
  scoreIndex:0,
  cPage:1,
  cLimit:5,
  cTotal:0
})

const gameId = ref(0)


onMounted(()=>{
  data.userInfo = getUserInfo()
  gameId.value=route.params.id;
  getGameInfo();
  data.userInfo && isInOrder();
  getCommentList();
})

// 评论更新本次点击 喜欢or踩 次数
function updateComment(e){
  data.commentList[e.index].liked += e.liked
  data.commentList[e.index].unliked += e.unliked
  e.type=="liked"?(data.commentList[e.index].isLiked = e.flag):(data.commentList[e.index].isUnliked = e.flag)
  console.log(data.commentList);
}

// 获取游戏详细信息
async function getGameInfo(){
  let res =await  request("/game",{gameId:gameId.value})
  data.gameInfo = res.data.gameInfo

  document.querySelector("title").text = res.data.gameInfo.gameName + " - 趣玩游戏"

  getRecommendList();
  getDeveloperGames();
}

// 获取相关推荐
async function getRecommendList(){
  let tag = data.gameInfo.tags.length>=1 ? data.gameInfo.tags[0] : ''
  // let res = await request("/related",{tlId:2001+gameId.value%4})
  let res = await request("/related",{tag})
  data.relatedList = res.data.relatedList;
}

// 获取厂商相关游戏
async function getDeveloperGames(){
  let res = await request("/developer/games",{name:data.gameInfo.developer})
  data.developerGames = res.data.games;
}

// 查询该游戏是否在心愿单中
async function isInOrder(){
  let res = await request("/wishlist/find",{gameId:gameId.value,userId:data.userInfo.userId});
  wishlistObj.flag = res.data.isInOrder
  wishlistObj.title = res.data.title
  wishlistObj.isPurchase = wishlistObj.flag?res.data.isPurchase:false
}

// 获取评论
async function getCommentList(){
  let res = await request("/comment/list",{gameId:gameId.value,userId:(data.userInfo&&data.userInfo.userId)||0,limit:data.cLimit,offset:(data.cPage-1)*data.cLimit})
  data.commentList = res.data.commentList
  data.cTotal = res.data.total
  data.commentList;
    console.log(res.data);
}

// 评论分页获取
function handleCurrentChange(val){
  data.cPage = val
  getCommentList()
}

// 评论选评分
function handleSelectScore(e){
  data.scoreIndex = e.currentTarget.dataset.index;
}

// 上一张截图
function handlePre(){
  currentBSs.index--;
  if(currentBSs.index<0){
    currentBSs.index=data.gameInfo.screenshot.length-1
  }
  document.querySelector("#screenshotBox>ul").style.left=(currentBSs.width*currentBSs.index)+"px"
}

// 下一张截图
function handleNext(){
  currentBSs.index++;
  if(currentBSs.index>data.gameInfo.screenshot.length-1){
    currentBSs.index=0
  }
  document.querySelector("#screenshotBox>ul").style.left=(currentBSs.width*currentBSs.index)+"px"
}

// 显示大截图
function showBSs(e){
  let index = parseInt(e.currentTarget.dataset.index);
  showBigScreenshot.value=true
  let height = (document.querySelector(".bigScreenshot").height)
  let width=0;
  let bslist = document.querySelectorAll(".bigScreenshot")
  if (height>720){
    width=-400
  }else{
    width=-800
  }

  currentBSs.index = parseInt(e.currentTarget.dataset.index);
  currentBSs.width = width

  document.querySelector("#screenshotBox").style.width=-width+"px"
  for(let i =0;i<bslist.length;i++){
    bslist[i].setAttribute('style', 'width:'+-width+"px")
  }
  document.querySelector("#screenshotBox>ul").style.left=(width*index)+"px"
}

// 评论提交
async function submitCommend(){
  if(!data.userInfo){
    handleTips("您还未登录，请先登录或注册，再来进行评价！")
    store.handleClose(true)
  }else{
    if(commentText.value.trim()==""){
      handleTips("评论内容不为空，请确认评论的内容！")
      return 
    }    
    await request("/comment/add",{gameId:data.gameInfo.gameId,userId:data.userInfo.userId,name:data.userInfo.name,imgUrl:data.userInfo.imgUrl,content:commentText.value,score:data.scoreIndex*2+"",time:(new Date().toLocaleString()).replaceAll("/","-")},"POST")

    let res = await request("/user/new/comment",{gameId:data.gameInfo.gameId,userId:data.userInfo.userId})
    let tempComment = {
      userId:data.userInfo.userId,
      commentId:res.data.commentId,
      name:data.userInfo.name,
      imgUrl:data.userInfo.imgUrl,
      content:commentText.value,
      time:"刚刚",
      score:data.scoreIndex*2+"",
      liked:0,
      unliked:0
    }
    data.commentList.unshift(tempComment)
    commentText.value=""
    data.scoreIndex = 0
  }
}

// 添加到心愿单
async function addWishList(){
  if(!localStorage.getItem("userInfo")){
    handleTips("您还未登录，请先登录或注册，再来添加您的心愿单！");
    store.handleClose(true)
  }else if(wishlistObj.isPurchase){
    handleTips("已购买该商品，请勿重复添加！")
  }else if(!wishlistObj.isPurchase){
    if(!wishlistObj.flag){
      let res = await request("/wishlist/add",{userId:data.userInfo.userId,gameId:gameId.value},"POST")
      console.log(res);
      if (res.data.info.code==200){
        handleTips(res.data.info.msg,"success")
        wishlistObj.title = res.data.info.title
      }else if(res.data.info.code==404){
        handleTips(res.data.info.msg)
      }
      wishlistObj.flag=!wishlistObj.flag
    }else if(wishlistObj.flag){
      let res = await request("/wishlist/remove",{userId:data.userInfo.userId,gameId:gameId.value},"POST")
        if (res.data.info.code==200){
          handleTips(res.data.info.msg,"success")
          wishlistObj.title = res.data.info.title
        }else if(res.data.info.code==404){
          handleTips(res.data.info.msg)
        }
      wishlistObj.flag=!wishlistObj.flag
    }
  }
  
}
</script>

<style scoped>
.detail-container{
  position: relative;
  background-repeat: no-repeat;
  /* background-image: ; */
  /* background-size: 1; */
  padding: 10px 0;
}
.logo{
  position: relative;
  background-repeat: no-repeat;
  height: 160px;
  width: 340px;
  z-index: 3;
}

.mask{
  position: absolute;
  /* 渐变色 */
  background: linear-gradient(to bottom,rgba(255,255,255,.1),#fff);
  width: 100%;
  height: 620px;
  left: 0;
  top: 0;
  z-index: 2;
}

.background-img{
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 620px;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center;
  /* opacity: .5; */
  /* background: linear-gradient(to bottom,rgba(255,255,255,.1),#fff); */
  z-index: 1;
}

.detail-content{
  width:1200px;
  margin: 0px auto;
}

.content-top{
  display: flex;
  justify-content: space-between;
  height: 440px;
}

.content-top-left{
  height: 420px;
  z-index: 8;
}

.video{
  width: 745px;
  height: 420px;
  background-color: black;
}

.content-top-right{
  display: flex;
  flex-direction: column;
  justify-content:space-between;
  width: 360px;
  height: 380px;
  padding: 20px;
  background-color: white;
  border-radius: 15px;
  box-shadow: 0 2px 6px rgba(0,0,0,.1);
  z-index: 8;
}

.content-top-right>.game-info-top{
  display: flex;
  flex-direction: column;
  align-items: center;
}

.content-top-right>.game-info-top>img{
  height: 80px;
  width: 80px;
  border-radius: 15px;
}

.game-name{
  width: 100%;
  font-size: 18px;
  margin:8px 0 8px 0;
  text-align: center;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.score-box{
  display: flex;
  align-items: center;
}

.game-score{
  font-size: 14px;
  color: rgba(0,0,0,0.4);
  margin-left: 5px;
}

.game-tags{
    display: flex;
    align-items: center;
    justify-content: center;
    height: 70px;
    flex-wrap: wrap;
    flex-grow: 0;
    flex-shrink: 0;
    padding: 5px 0;
    overflow: hidden;
}

.game-tags>span{
    font-size: 14px;
    color: rgba(0,0,0,0.4);
    margin: 2px 5px 2px 0;
    padding: 2px 5px;
    border-radius: 4px;
    border: 1px solid rgba(0,0,0,0.1);
    cursor: pointer;
}

.game-tags>span:hover{
  color: #24B8F2;
  border: 1px solid #24B8F2;
}

.content-top-right>.game-info-bottom{
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.content-top-right>.game-info-bottom>div:nth-last-of-type(0){
  display: flex;
  flex-direction: column;
  align-items: center;
  width: calc(100% - 170px);
}

.price{
  display: block;
  width: 100%;
  font-size: 24px;
  text-align: center;
  margin-bottom: 20px;
  color: #FFA726;
}

.add-game{
  width: 96px;
  height: 20px;
  padding: 10px 30px;
  text-align: center;
  line-height: 20px;
  background-color: #24B8F2;
  color: white;
  border-radius: 10px;
  cursor: pointer;
}

.remove-game{
  background-color: #333!important;
}

.scan-code{
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 120px;
  padding: 10px;
  border-radius: 15px;
  border: 1px solid rgba(0,0,0,.1);
}

.scan-code>span{
  font-size: 12px;
  color: #61666D;
  margin-bottom: 10px;
}
.scan-code>img{
  height: 100px;
  width: 100px;
}

.content-bottom{
  position: relative;
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.content-bottom-left{
  width: 745px;
}

.nav-ul{
  display: flex;
  justify-content: center;
  border-bottom: 1px solid rgba(0,0,0,.1);
  /* padding-bottom: 10px; */
}

.nav-ul>li{
  padding: 10px 14px;
  border-bottom:2px solid #24B8F2;
}

.detail-recommend{
  display: flex;
  flex-direction: column;
  margin-top: 20px;
  width: 745px;
}

.title{
  display: block;
  border-left:4px solid #24B8F2;
  padding: 0 15px;
  margin-top: 20px;
  margin-bottom: 20px;
}

.detail-info-box>ul>li{
  margin-bottom: 10px;
  white-space:pre-line;
  font-family: "Microsoft Yahei","PingFang SC",Helvetica,Arial,sans-serif;
  font-size: 14px;
  color: rgba(0,0,0,0.6);
}
.hidden{
  text-overflow: ellipsis;
  overflow: hidden;
  display: -webkit-box;
  -webkit-box-orient: vertical;
  -webkit-line-clamp: 5;
}

.detail-recommend>div{
  /* position: relative; */
  display: flex;
  flex-direction: column;
}

.show{
  /* position: absolute; */
  bottom:0px;
  right: 0px;
  text-align: right;
  font-size: 14px;
  color: #24B8F2;
  cursor: pointer;
}

.screenshot{
  display: flex;
  width: 100%;
  margin-top: 20px;
  overflow: auto;
}

.screenshot>li{
  margin-right: 20px;
}

.screenshot>li>img{
  cursor: pointer;
}

.take-comment{
  display: flex;
  flex-direction: column;
  align-items: self-end;
  width:100%;
  margin: 20px 0;
  padding-bottom: 10px;
  border-bottom: 1px solid rgba(0,0,0,.1);
}

.take-comment>textarea{
  width: calc(100% - 20px);
  max-height: 100px;
  max-width: calc(100% - 20px);
  min-height: 100px;
  min-width: calc(100% - 20px);
  padding: 10px;
  margin-bottom: 10px;
  border-radius: 10px;
  border: 0;
  background-color: #f1f2f3;
  outline: unset;
}

.take-comment>input[type=submit]{
  width: 120px;
  height: 40px;
  color: white;
  background-color: #24B8F2;
  border: 0;
  border-radius: 10px;
  outline: unset;
  cursor: pointer;
}
.content-bottom-right{
  /* position: fixed; */
  top: 10px;
  right: 12%;
  width: 400px;
  background-color: white;
}

.showBigImg{
  position: fixed;
  background-color: rgba(0,0,0,.6);
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 901;
}

.showBigImg>div{
  position: relative;
}

.showBigImg #screenshotBox{
  position: relative;
  overflow: hidden;
}

.showBigImg ul{
  position: relative;
  display: flex;
  left: 0;
  transition: .5s;
}

.showBigImg ul>li{
  display: flex;
  justify-content: center;
  align-items: center;
}

.i-close{
  position: absolute;
  top: -40px;
  right: -40px;
  width:40px;
  height: 40px;
  line-height: 40px;
  text-align: center;
  font-size: 20px;
  border-radius: 50%;
  background-color: rgba(0,0,0,0.4);
  color: rgba(255,255,255,.5);
  z-index: 99;
  cursor: pointer;
}

.i-left,
.i-right{
  position: absolute;
  top:45%;
  height: 60px;
  width: 60px;
  text-align: center;
  line-height: 60px;
  font-size: 20px;
  background-color: rgba(0,0,0,0.4);
  color: rgba(255,255,255,.5);
  border-radius: 50%;
  z-index: 99;
  cursor: pointer;
}

.i-left{
  left: -70px;
}

.i-right{
  right: -70px;
}

.i-close:hover,
.i-left:hover,
.i-right:hover{
  background-color: #24B8F2;
  color: white;
}

.showBigImg ul>li>img{
  /* width: 450px; */
  /* height: 600px; */
  object-fit: contain;
}

.num{
  position: absolute;
  bottom: -40px;
  left: 45%;
  padding: 5px 20px;
  border-radius: 10px;
  background-color: rgba(0,0,0,0.4);
  color: rgba(255,255,255,.5);
}

.no-comment-list{
  height: 100px;
  width: 100%;
  text-align: center;
  line-height: 100px;
  color: rgba(0,0,0,.6);
  font-size: 16px;
}


.score-star{
  position: relative;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 200px;
  margin:20px auto;
}

.score-star>div{
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
}

.score-star>div>.name{
  font-size: 18px;
  margin-bottom: 15px;
  color: #333;
}

.score-star>div>img{
  height: 60px;
  width: 60px;
  margin-bottom: 10px;
}

.score-star .no-color>i{
  width: 20px;
  margin-right: 5px;
  color: #e7edf3;
  font-size: 25px;
  cursor: pointer;
}

.score-star .have-color{
  color: #7dd6fa!important;
}

</style>