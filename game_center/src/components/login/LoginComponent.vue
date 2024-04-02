<template>
  <div class="login-container">
    <div class="login-content">
      <i class="iconfont icon-close close" @click="handleClose(false)"></i>
      <div class="fun-btn">
        <span id="login" :class="selected=='login'?'selected':''" data-type="login" @click="handleChange">登录</span>
        <span>|</span>
        <span id="register" :class="selected=='register'?'selected':''" data-type="register" @click="handleChange">注册</span>
      </div>
      <!-- 登录 -->
      <form action="/login" v-if="selected=='login'" data-type="login" @submit.prevent="handleLogAndReg">
        <div>
          <input :class="checkObj.checkPhone?'err-input':''" type="text" maxlength="11" placeholder="请输入手机号" v-model="userInfo.phone" data-type="acc" @blur="handleBlur">
          <span class="err-tip" :class="checkObj.checkPhone?'block':'none'">手机号格式输入错误</span>
        </div>
        <div>
          <input :class="checkObj.checkPwd?'err-input':''" type="password" placeholder="请输入密码" v-model="userInfo.pwd" data-type="pwd" @blur="handleBlur">
          <span class="err-tip" :class="checkObj.checkPwd?'block':'none'">密码格式输入错误，任意8-16位数字或字母</span>
        </div>
        <div class="agree">
          <div><input type="checkbox" v-model="userInfo.agree" @click="checkObj.checkAgree=userInfo.agree">已阅读并同意帐号<a href="javascript:;">《用户协议》</a>和<a href="javascript:;">《隐私政策》</a></div>
          <span class="err-tip" :class="checkObj.checkAgree?'block':'none'">请先勾选用户协议</span>
        </div>
        <input type="submit" value="登 录">
      </form>
      <!-- 注册 -->
      <form action="" v-else  data-type="register" @submit.prevent="handleLogAndReg">
        <div>
          <input :class="checkObj.checkPhone?'err-input':''" type="text" placeholder="请输入手机号" maxlength="11" v-model="userInfo.phone" data-type="acc" @blur="handleBlur">
          <span class="err-tip" :class="checkObj.checkPhone?'block':'none'">手机号格式输入错误</span>
        </div>
        <div>
          <input :class="checkObj.checkPwd?'err-input':''" type="password" placeholder="请输入密码" v-model="userInfo.pwd"  data-type="pwd" @blur="handleBlur">
          <span class="err-tip"  :class="checkObj.checkPwd?'block':'none'">密码格式输入错误，任意8-16位数字或字母</span>
        </div>
        <div>
          <input :class="checkObj.checkAgainPwd?'err-input':''" type="password" placeholder="请再次输入密码" v-model="userInfo.again_pwd" data-type="again_pwd" @blur="handleBlur">
          <span class="err-tip"  :class="checkObj.checkAgainPwd?'block':'none'">两次密码输入不一致</span>
        </div>
        <div class="agree">
          <div><input type="checkbox" v-model="userInfo.agree" @click="checkObj.checkAgree=userInfo.agree">已阅读并同意帐号<a href="javascript:;">《用户协议》</a>和<a href="javascript:;">《隐私政策》</a></div>
          <span class="err-tip" :class="checkObj.checkAgree?'block':'none'">请先勾选用户协议</span>
        </div>
        <input type="submit" value="注 册">
      </form>
    </div>
  </div>
</template>

<script setup>
import request from '@/utils/request';
import {reactive, ref ,onMounted } from 'vue'
import { handleTips } from '@/utils/tools';
const selected = ref("login")

import useStore from '@/store/index.js';
const store = useStore()

const handleClose=(val)=>{
  store.handleClose(val)
}

const userInfo = reactive({
  phone:"",
  pwd:"",
  again_pwd:"",
  agree:false
})

const checkObj = reactive({
  checkPhone:false,
  checkPwd:false,
  checkAgainPwd:false,
  checkAgree:false
})

onMounted(()=>{
})


function handleBlur(e){
  // 校验输入的值
  let type = e.currentTarget.dataset.type;
  if(type == "acc"){
    let reg = /^1[3-9]{1}[0-9]{9}$/
    checkObj.checkPhone=reg.test(userInfo.phone)?false:true
  }else if(type == "pwd"){
    let reg = /^[a-zA-Z0-9]{8,16}$/
    checkObj.checkPwd=reg.test(userInfo.pwd)?false:true
  }else if(type == "again_pwd"){
    let reg = /^[a-zA-Z0-9]{8,16}$/
    checkObj.checkAgainPwd=reg.test(userInfo.pwd)?(userInfo.pwd==userInfo.again_pwd?false:true):true
  }
}

function handleChange(e){
  let type = e.currentTarget.dataset.type;
  // 选择另一个时清空已输入
  if(type=="login"){
    selected.value='login';
    userInfo.phone="";
    userInfo.pwd="";
    userInfo.again_pwd="";
    userInfo.agree=false;
    checkObj.checkPhone=false;
    checkObj.checkPwd=false;
    checkObj.checkAgainPwd=false;
    checkObj.checkAgree=false;
  }else if(type=="register"){
    selected.value='register';
    userInfo.phone="";
    userInfo.pwd="";
    userInfo.again_pwd="";
    userInfo.agree=false;
    checkObj.checkPhone=false;
    checkObj.checkPwd=false;
    checkObj.checkAgainPwd=false;
    checkObj.checkAgree=false;
  }
}

async function handleLogAndReg(e){
  let type = e.currentTarget.dataset.type;
  if(type=="login"){
    if(userInfo.agree&&userInfo.pwd&&userInfo.phone&&!checkObj.checkPhone&&!checkObj.checkPwd&&!checkObj.checkAgree){
      let res = await request("/login",{phone:userInfo.phone,pwd:userInfo.pwd},"POST")
      if(res.data.info.code==200){
        localStorage.setItem("userInfo",JSON.stringify(res.data.info.userInfo))
        handleTips(res.data.info.msg,"success")
        setInterval(()=>{window.open(location.href,"_self")},1500)
        
      }else{
        handleTips(res.data.info.msg)
      }
    }else{
      if(!userInfo.agree){
        checkObj.checkAgree=true
      }

      if(!userInfo.phone){
        checkObj.checkPhone=true
      }

      if(!userInfo.pwd){
        checkObj.checkPwd=true
      }
      handleTips("当前输入有误，请按提示完成！")
    }
  }else if(type=="register"){
    if(userInfo.agree&&userInfo.pwd&&userInfo.phone&&userInfo.again_pwd&&!checkObj.checkPhone&&!checkObj.checkPwd&&!checkObj.checkAgree&&userInfo.pwd==userInfo.again_pwd){
      let imgSign = Math.floor(Math.random()*21)
      let imgUrl = "/img/user/default"+imgSign+".png"
      let res = await request("/register",{phone:userInfo.phone,pwd:userInfo.pwd,imgUrl,name:"用户"+(new Date().getTime())},"POST")
      if(res.data.info.code==200){
        handleTips(res.data.info.msg,"success")
        // 仿点击事件的dataset值
        let e = {
          currentTarget:{
            dataset:{
              type:"login"
            }
          }
        }
        handleChange(e)
      }else if(res.data.info.code==404){
        handleTips(res.data.info.msg)
      }
    }else{
      if(!userInfo.agree){
        checkObj.checkAgree=true
      }

      if(!userInfo.phone){
        checkObj.checkPhone=true
      }

      if(!userInfo.pwd){
        checkObj.checkPwd=true
      }

      if(!userInfo.again_pwd){
        checkObj.checkAgainPwd=true
      }
      handleTips("当前输入有误，请按提示完成！")
    }
  }
}
</script>

<style scoped>
.login-container{
  position: fixed;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  background-color: rgba(0,0,0,0.6);
  z-index: 99;
}
.login-content{
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 400px;
  margin: 20px auto;
  padding: 20px;
  border-radius: 15px;
  border: 1px solid rgba(0,0,0,0.1);
  box-shadow: 0 2px 30px 2px rgba(0,0,0,0.1);
  background-color: white;
}

.close{
  position: absolute;
  top: 10px;
  right: 15px;
  font-size: 20px;
  color: rgba(0,0,0,0.6);
  cursor: pointer;
}

.close:hover{
  color: #24B8F2;
}

.fun-btn{
  display: flex;
  justify-content: space-around;
  width: 100%;
  margin-bottom: 20px;
}

.fun-btn>span{
  color: rgba(0, 0, 0,0.6);
  cursor: pointer;
  padding:0 4px 4px 4px;
}

.fun-btn>span:nth-of-type(2){
  color: rgba(0,0,0,0.4)!important;
  cursor:unset;
}

.selected{
  color: #24B8F2!important;
  border-bottom: #24B8F2 4px solid;
}

.login-content>form{
  display: flex;
  flex-direction: column;
  /* align-items: center; */
  margin: 10px 0;
}

.login-content>form>div{
  display: flex;
  flex-direction: column;
  /* position: relative; */
  /* padding-bottom: 20px; */
  margin-bottom: 14px;
}

.err-tip{
  /* position: absolute; */
  /* bottom: 0; */
  /* left: 0; */
  font-size: 12px;
  color: red;
  margin-top: 10px;
}

.login-content>form>div>input[type=text],
.login-content>form>div>input[type=password]{
  width: 290px;
  height: 50px;
  background-color: #f1f2f3;
  border-radius: 10px;
  border: 0;
  outline: unset;
  padding: 0 10px;
}

.agree>div{
  display: flex;
  align-items: center;
  font-size: 14px;
  color: rgba(0, 0, 0,0.6);
}

.agree>div>input{
  height: 14px;
  width: 14px;
  margin-top: 2px;
  margin-right: 2px;
}

.agree>div>a{
  color: #24B8F2;
}

.login-content>form>input[type=submit]{
  width: 310px;
  height: 50px;
  border-radius: 10px;
  border: 0;
  outline: unset;
  background-color: #24B8F2;
  color: white;
  font-size: 16px;
  cursor: pointer;
}

.err-input{
  background-color: rgb(252, 242, 243)!important;
  color: rgb(240, 70, 69)!important; 
}

.err-input::-webkit-input-placeholder{
  color:rgb(240, 70, 69)!important; 
}
</style>