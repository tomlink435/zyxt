// import { Local, Session } from "./storage"
import { USER_TOKEN } from '../store/constants';
import router from '/@/router';
const timeOut = 15 * 60 * 1000; //设置超时时间： 15分

function updateActiveTime() {
  localStorage.setItem('lastActiveTime', new Date().getTime() + '');
}

window.onload = function () {
  window.document.onmousedown = updateActiveTime;
};

let checkActiveInterval: any = null

export const startActiveCheck = () => {
  if (checkActiveInterval !== null) return
  updateActiveTime();
  checkActiveInterval = setInterval(() => {
    const currentTime = new Date().getTime();
    const lastActiveTime = localStorage.getItem('lastActiveTime');
    if (currentTime - Number(lastActiveTime) > timeOut) {
      localStorage.removeItem('lastActiveTime')
      localStorage.removeItem(USER_TOKEN)
      console.log(localStorage)
      alert('已超时，请重新登陆')
      debugger
      clearInterval(checkActiveInterval)
      checkActiveInterval = null
      router.push({ name: 'login' }).catch((err) => {
        console.error('路由跳转失败:', err);
    });
    }
  }, 30000);
}

export const endActiveCheck = () => {
  clearInterval(checkActiveInterval)
  checkActiveInterval = null
}
