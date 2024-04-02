import { ElMessage } from "element-plus";

// 跳转到详细页
export function toDetail(e){
    let id = e.currentTarget.dataset.id
    // router.push("/detail/"+id)
    window.open("/detail/"+id,"_blank")
}

// 跳转到搜索页||标签发现页
export function toSearch(keyword,type = "game"){
    // router.push("/search/"+keyword.value);
    if(type == "tag"){
        window.open("/discover/"+type+"/"+keyword,"_blank")
    }else{
        window.open("/search/"+type+"/"+keyword,"_blank")
    }
    
}

// 修改后缀
export function modifySuffix(val,sign){
    return val.replace(sign,"")
}
// 转为两位小数
export function handleToFixed(val,count){
    return parseFloat(val).toFixed(count)
}
// 检验|获取用户登录信息
export function getUserInfo() {    
    try {  
      const userInfo = localStorage.getItem("userInfo");    
      return userInfo ? JSON.parse(userInfo) : null;    
    } catch (error) {  
      console.error("Error :", error);  
      return null;  
    }  
}  

// 提示框
export function handleTips(message,type = "error"){
    ElMessage({
        showClose: true,
        message,
        type,
    })
}