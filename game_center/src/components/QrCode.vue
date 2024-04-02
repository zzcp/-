<!-- 局域网使用前后端可模拟扫码支付，扫码设备开热点，前后端连接扫码设备的热点，实现扫码支付 -->
<template>
  <div class="container">
    <div class="box">
      <span>扫码即可支付</span>
      <vue-qr :text="downloadData.url" :margin="0" :logoScale="0.3" :size="100"></vue-qr>

      <div class="expire" @click="handleRefresh" v-if="isExpire">
        <span style="font-size: 12px;">二维码已失效！点击刷新</span>
      </div>
    </div>
    <span style="font-size: 14px!important;margin-left: 10px;">合计：<strong>{{ priceTotal }}</strong>元</span>
  </div>
</template>
 
<script>
import request from '@/utils/request';
import vueQr from 'vue-qr/src/packages/vue-qr.vue'
import config from '@/utils/config';
import { handleTips } from '@/utils/tools';
import useStore from "@/store/index.js";
const store = useStore()
export default {
    components: {
        vueQr
    },
    props:['gameIdList','userId','priceTotal'],
    data() {
        return {
            downloadData: {
                url: config.payUrl+'/game/pay?userId='+this.userId+'&gameIdList='+this.gameIdList.toString()   // 扫码访问的链接地址
            },
            timer:null,
            time:30,
            currentTime:0,
            isExpire :false
        }
    },
    mounted(){
      this.timer = setInterval(()=>{
        this.checkedPay()
      },2000)
    },
    methods:{
        async checkedPay(){
            if (store.showPayDetail == false){
              clearInterval(this.timer)
              this.downloadData.url =config.payUrl+'/game/pay?userId='+this.userId+'&gameIdList='+this.gameIdList.toString()
              this.isExpire = false
              this.currentTime = 0
              return 
            }

            // 检查是否已支付
            let res = await request("/check/pay",{userId:this.userId,gameIdList:this.gameIdList.toString()});
            if(res.data.payCode == 200){
              handleTips(res.data.msg,"success")
              clearInterval(this.timer)
              setInterval(()=>{
                window.open("/wishlist","_self")
              },1000)
            }

            // 每次加2，方便测试
            this.currentTime+=2
            
            // 当当前时间等于二维码显示时间长度，在未支付情况下显示二维码失效
            if(this.currentTime==this.time){
              clearInterval(this.timer)
              handleTips("二维码已失效！")
              this.downloadData.url = "二维码已失效"
              this.isExpire = true
              this.currentTime = 0
            }
            
            // if(this.currentTime == 10 || this.currentTime == 20){
            //   handleTips(res.data.msg)
            // }
        },
        handleRefresh(){
          this.downloadData.url =config.payUrl+'/game/pay?userId='+this.userId+'&gameIdList='+this.gameIdList.toString()
          this.isExpire = false
          this.timer = setInterval(()=>{
            this.checkedPay()
          },2000)
        }
    }
}
</script>
<style scoped>
.container{
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
}

.expire{
  position: absolute;
  top: 0;
  left: 0;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  border-radius: 15px;
  background-color: rgba(0,0,0,.5);
  color: white;
  cursor: pointer;
}

.box{
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px 20px;
  border-radius: 15px;
  border: 1px solid rgba(0,0,0,.1);
  margin-right: 10px;
  margin-bottom: 10px;
}

.box>span{
  font-size: 12px;
  color: #61666D;
  margin-bottom: 10px;
}

.container>span{
  font-size: 16px;
  color: #24B8F2;
}

.container>span>strong{
  font-size: 20px;
  margin: 0 5px;
}

</style>