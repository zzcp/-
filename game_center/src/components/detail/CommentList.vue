<template>
  <div>
    <ul class="comment-list" v-if="props.commentList.length">
        <li v-for="(item,index) in props.commentList" :key="item.commentId">
        <img :src="config.url+item.imgUrl">
        <div class="commentator-info">
            <div class="name-star-box">
              <span class="name">{{ item.name }}</span>
              <StarsComponent :count="5" :score="item.score"></StarsComponent>
            </div>
            <pre class="content">{{ item.content }}</pre>
            <div class="comment-bottom">
              <span class="time">{{ item.time }}</span>
              <div class="small-fun">
                <div @click="handleLinked" data-type="liked" :data-index="index" :data-id="item.commentId" :class="item.isLiked?'liked':''">
                  <i class="iconfont icon-zan"></i>
                  <span>{{ item.liked }}</span>
                </div>
                <div @click="handleLinked" data-type="unliked" :data-index="index" :data-id="item.commentId" :class="item.isUnliked?'liked':''">
                  <i class="iconfont icon-cai"></i>
                  <span>{{ item.unliked }}</span>
                </div>
                <div :style="(data.userInfo&&data.userInfo.userId!=item.userId)?'margin:0':''" @click.prevent="replyTo=item.name;showReplyBox(item.commentId)">
                  <i class="iconfont icon-xinxi"></i>
                  <span>回复</span>
                </div>
                <div style="margin:0" v-if="data.userInfo&&data.userInfo.userId==item.userId" :data-id="item.commentId" @click.prevent="showDelTips">
                  <i class="iconfont icon-xuanxiangshanchu"></i>
                  <span>删除</span>
                </div>
              </div>
            </div>
            <div class="reply-list">
              <ul>
                <li v-for="(replyItem) in item.replyList" :key="replyItem.commentId">
                  <img :src="config.url+replyItem.imgUrl">
                  <div class="reply-info">
                    <span class="name">{{ replyItem.name }}</span>
                    <pre class="content">{{ replyItem.content }}</pre>
                    <div class="reply-bottom">
                      <span class="time">{{ replyItem.time }}</span>
                      <div class="small-fun">
                        <!-- <div @click="handleLinked" data-type="liked" :style="(data.userInfo&&data.userInfo.userId!=replyItem.userId)?'margin:0':''" :data-index="replyIndex" :data-id="replyItem.commentId" :class="replyItem.isLiked?'liked':''">
                          <i class="iconfont icon-zan"></i>
                          <span>{{ replyItem.liked }}</span>
                        </div> -->
                        <div style="margin:0" v-if="data.userInfo&&data.userInfo.userId==replyItem.userId" :data-id="replyItem.commentId" @click.prevent="showDelTips">
                          <i class="iconfont icon-xuanxiangshanchu"></i>
                          <span>删除</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
        </div>
        </li>
    </ul>
    <div v-else class="no-comment-list">
        暂无更多相关游戏评价
    </div>
    <div class="tips" v-show="showTip">
      <div class="tips-box">
        <span class="tips-title">提示</span>
        <p class="tips-content">是否删除该评论</p>
        <div class="tips-btn">
          <input type="button" value="取 消" class="tips-cancel" @click="isDel=false;showTip=false">
          <input type="button" value="确 定" class="tips-sure" @click="handleDelComment">
        </div>
      </div>
    </div>
    <div class="reply" v-show="showReply">
      <div class="reply-box">
        <span class="reply-title">回复<text>@{{ replyTo }}</text></span>
        <textarea name="reply-text" id="" maxlength="999" v-model="replyText"></textarea>
        <div class="reply-btn">
          <input type="button" value="取 消" @click="showReply=false">
          <input type="button" value="回 复" @click="submitReply">
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import request from "@/utils/request";
import config from "@/utils/config";
import StarsComponent from "../StarsComponent.vue"
import { getUserInfo,handleTips } from "@/utils/tools";
import { defineEmits,defineProps, onMounted, reactive, ref } from 'vue';
import { useRoute } from "vue-router";
import useStore from "@/store/index.js";
const store = useStore()

const emit = defineEmits(['updateComment','getCommentList']);
const props = defineProps({
    commentList:{
        type:Array,
        default:()=>[]
    }
})
const route = useRoute();
const data = reactive({
  userInfo:{},
  gameId:0,
  liked:0,
  unliked:0
})
// 删除
const showTip=ref(false)
const isDel = ref(false)
const delId = ref(-1)
// 回复
const replyText = ref("")
const showReply = ref(false)
const replyId = ref(0)
const replyTo = ref("user")

onMounted(()=>{
  data.userInfo = getUserInfo();
  data.gameId = route.params.id
})

function showDelTips(e){
  showTip.value = true;
  delId.value = e.currentTarget.dataset.id;
}

async function handleDelComment(){
  isDel.value = true
  // showTip.value=false
  await request("/comment/del",{commentId:delId.value,userId:data.userInfo.userId,gameId:data.gameId},"DELETE")
  emit("getCommentList")
  showTip.value=false
  isDel.value = false
}

function showReplyBox(id){
  showReply.value=true
  replyId.value = id;
}

async function submitReply(){
  if(data.userInfo){
    if(replyText.value.trim()==""){
      handleTips("回复内容不为空，请确认回复的内容！")
      return 
    }
    let res = await request("/comment/reply",{userId:data.userInfo.userId,
                                            gameId:data.gameId,
                                            replyId:replyId.value,
                                            content:replyText.value,
                                            imgUrl:data.userInfo.imgUrl,
                                            time:(new Date().toLocaleString()).replaceAll("/","-"),
                                            name:data.userInfo.name
                                          },"POST")
    handleTips(res.data.msg,"success")
    showReply.value = false;
    replyId.value = ""
    replyText.value = ""
    emit("getCommentList")
  }else{
    handleTips("您还未登录，请先登录或注册，再来进行回复！");
    showReply.value = false
    store.handleClose(true)
  }

}

// 评论点赞||点踩
async function handleLinked(e){
  if(data.userInfo){
    // type类型为点赞||点踩
    let type = e.currentTarget.dataset.type;
    let commentId = e.currentTarget.dataset.id;
    let index = e.currentTarget.dataset.index;
    let res = await request("/comment/"+type,{commentId,userId:data.userInfo.userId,gameId:data.gameId},"POST");
    handleTips(res.data.msg,"success");
    type=="liked"?(data.liked = res.data.flag?1:-1):(data.unliked = res.data.flag?1:-1)
    let flag = res.data.flag
    
    emit("updateComment",{type,liked:data.liked,unliked:data.unliked,flag,index})
    data.liked = 0
    data.unliked = 0
  }else{
    handleTips("请先登录或注册，再进行操作！");
    store.handleClose(true)
  }

}
</script>

<style scoped>

.comment-list{
  display: flex;
  flex-direction: column;
  width: 100%;
  /* max-height: 700px; */
  padding-right: 10px;
  /* overflow-y: auto; */
}


.reply-list>ul>li,
.comment-list>li{
  display: flex;
  margin-bottom: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid rgba(0,0,0,.1);
  width: 100%;
}

.reply-list>ul>li>img,
.comment-list>li>img{
  height: 50px;
  width: 50px;
  margin-right: 10px;
}
.no-comment-list{
  height: 100px;
  width: 100%;
  text-align: center;
  line-height: 100px;
  color: rgba(0,0,0,.6);
  font-size: 16px;
}

.reply-info,
.commentator-info{
  display: flex;
  flex-direction: column;
  width: calc(100% - 60px);
}

.commentator-info>.name-star-box{
  display: flex;
  flex-direction: column;
  
}

.commentator-info>.name-star-box>div{
  margin:5px 0 !important;
}


.reply-info .name,
.commentator-info .name{
  font-size: 13px;
  color: #61666D;
  font-family: PingFang SC,HarmonyOS_Regular,Helvetica Neue,Microsoft YaHei,sans-serif;
  font-weight: 500;
  margin-top: 4px;
}


.reply-info>.content,
.commentator-info>.content{
  width:100%;
  overflow: hidden;
  word-wrap: break-word;
  white-space: pre-line;
  font-size: 15px;
  margin: 8px 0;
  color: #18191c;
  font-family: PingFang SC,HarmonyOS_Regular,Helvetica Neue,Microsoft YaHei,sans-serif;
  font-weight: 400;
  line-height: 2;
}


.reply-bottom,
.comment-bottom{
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.reply-bottom>.time,
.comment-bottom>.time{
  font-size: 12px;
  color: rgba(0,0,0,0.6);
}

.small-fun{
  display: flex;
  align-items: center;
}

.small-fun>div{
  display: flex;
  align-items: center;
  margin-right: 15px;
  cursor: pointer;
}

.small-fun>div>i,
.small-fun>div>span{
  color: #999;
  font-size: 12px;
  margin-right: 5px;
}

.liked>i,
.liked>span{
  color: #24B8F2!important;
}

.tips{
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  background-color: rgba(0,0,0,.6);
  z-index: 125;
}

.tips-box{
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100px;
  width: 400px;
  background-color: white;
  border-radius: 10px;
  padding: 20px;
}

.tips-title{
  font-size: 16px;
  font-weight: 500px;
  height: 20px;
  line-height: 20px;
}

.tips-content{
  display: flex;
  flex-grow: 0;
  flex-shrink: 0;
  color: #666;
  margin: 15px 0;
}

.tips-btn{
  display: flex;
  justify-content: right;
}

.tips-btn>input{
  width: 100px;
  height: 40px;
  padding: 10px;
  border: 0;
  border-radius: 10px;
  outline: unset;
  cursor: pointer;
  margin-left: 20px;
}

.tips-sure{
  color: white;
  background-color: #24B8F2;
}

.reply{
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
  width: 100%;
  background-color: rgba(0,0,0,.6);
  z-index: 125;
}

.reply-box{
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 280px;
  width: 400px;
  background-color: white;
  border-radius: 10px;
  padding: 20px;
}

.reply-box>textarea{
  border: 0;
  outline: unset;
  border-radius: 15px;
  background-color: #f2f4f6;
  min-height: 140px;
  min-width: 360px;
  max-height: 140px;
  max-width: 360px;
  padding: 20px;
}

.reply-btn{
  display: flex;
  justify-content: right;
}

.reply-btn>input[type="button"]{
  width: 80px;
  height: 40px;
  line-height: 40px;
  border-radius: 10px;
  background-color: #24B8F2;
  color: white;
  outline: unset;
  border: 0;
  margin-left: 20px;
  cursor: pointer;
}

.reply-list>ul>li{
  padding-top: 10px;
  margin: 10px 0 0 0;
  border-bottom: 0;
  border-top: 1px solid rgba(0,0,0,.1);
}

.reply-title>text{
  width: 240px;
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  color: #24B8F2;
}
</style>