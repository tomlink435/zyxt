<template>
  <div class="navbar">

    <nav>
      <ul>
        <img src="/src/views/images/zjn.png " style="width: 3%; margin-right: 2%;" />
        <span style="font-size:20px; color: aliceblue; padding-right: 10%;">生态资源数据共享服务平台</span>
        <li><router-link to="/">首页</router-link></li>
        <li><router-link to="echarts">数据资源</router-link></li>
        <li><router-link to="/physical-resources">实物资源</router-link></li>
        <li><router-link to="/physical-resources">服务案例</router-link></li>
        <li><router-link to="/physical-resources">服务成效</router-link></li>
        <li><router-link to="/physical-resources">标准规范</router-link></li>
        <!-- Add other links as needed -->
      </ul>
    </nav>
    <div class="auth-links">
      <router-link to="/physical-resources" ></router-link>
      <!-- <router-link to="/register">注册</router-link> | <router-link to="/login">登录</router-link> -->
      <template v-if="userStore.user_token">
        <a-dropdown>
          <a class="ant-dropdown-link" @click="e => e.preventDefault()">
            <img :src="AvatarIcon" class="self-img" >
          </a>
          <template #overlay>
            <a-menu>
              <a-menu-item>
                <a @click="goUserCenter('borrowView')">个人中心</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="goUserCenter('userInfoEditView')">个人设置</a>
              </a-menu-item>
              <a-menu-item>
                <a @click="quit()">退出</a>
              </a-menu-item>
            </a-menu>
          </template>
        </a-dropdown>
       
        <!-- <audio ref="audioVo2" controls hidden>
          <source src="@/assets/reminder.mp3" type="audio/mp3" />
        </audio> -->
      </template>
      <template v-else>
        <button class="login btn hidden-sm" @click="goLogin()">登录</button>
      </template>
    </div>
  
  </div>
  <!-- <div class="logo">
      <img :src="logoImage" class="search-icon" @click="$router.push({name:'portal'})">
    </div> -->
     
    <!-- <div class="search-entry" style="margin-top: 5%;">
      <img :src="SearchIcon" class="search-icon">
      <input placeholder="输入关键词" ref="keywordRef" @keyup.enter="search" />
    </div> -->
</template>

<script setup lang="ts">
import {listApi} from '/@/api/notice'
import {useUserStore} from "/@/store";
import logoImage from '/@/assets/images/k-logo.png';
import SearchIcon from '/@/assets/images/search-icon.svg';
import AvatarIcon from '/@/assets/images/avatar.jpg';
import MessageIcon from '/@/assets/images/message-icon.svg';

import { ref, provide } from 'vue';

const audioVo = ref(null);
provide('audioVo', audioVo);

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const keywordRef = ref()

let loading = ref(false)
let msgVisible = ref(false)
let msgData = ref([] as any)

onMounted(()=>{
  getMessageList()
})

const getMessageList = ()=> {
  loading.value = true
  listApi({}).then(res => {
    msgData.value = res.data
    loading.value = false
  }).catch(err => {
    console.log(err)
    loading.value = false
  })
}
const search = () => {
  const keyword = keywordRef.value.value
  if (route.name === 'search') {
    router.push({name: 'search', query: {keyword: keyword}})
  } else {
    let text = router.resolve({name: 'search', query: {keyword: keyword}})
    window.open(text.href, '_blank')
  }
}
const goLogin = () => {
  router.push({name: 'login'})
}

const goUserCenter = (menuName) => {
  router.push({name: menuName})
}
const quit= () => {
  userStore.logout().then(res => {
    router.push({name: 'portal'})
  })
}
const onClose = () => {
  msgVisible.value = false;
}

</script>

<style scoped>
.search-entry {
  margin-top: 10px;
  Margin: auto;
  position: relative;
  width: 400px;
  min-width: 200px;
  height: 32px;
  background: #ecf3fc;
  padding: 0px 12px;
  border-radius: 16px;
  font-size: 0;
  cursor: pointer;

  img {
    max-width: 100%;
    height: auto;
  }
  .search-icon {
    width: 18px;
    margin: 7px 8px 0 0;
  }
  input {
    position: absolute;
    top:4px;
    width: 85%;
    height: 24px;
    border: 0px;
    outline:none;
    color: #000;
    background: #ecf3fc;
    font-size: 14px;
  }}
.navbar {
  display: flex;
  align-items: center;
  background-color: #337ab7;
  padding: 10px 20px;
}

.logo img {
  height: 60px;
  margin-right: 20px;
}

nav ul {
  list-style: none;
  display: flex;
  margin: 0;
  padding: 0;
}

nav li {
  margin-right: 20px;
}

nav a {
  color: white;
  text-decoration: none;
  font-size: 18px;
}

.auth-links {
  margin-left: auto;
  color: white;
}

.auth-links a {
  color: white;
  text-decoration: none;
  /* margin-left: -94px; */
}

nav a.router-link-active {
  font-weight: bold;
}
.self-img {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    vertical-align: middle;
    cursor: pointer;
  }
  .btn {
    background: #4684e2;
    font-size: 14px;
    color: #fff;
    border-radius: 32px;
    text-align: center;
    width: 66px;
    height: 32px;
    line-height: 32px;
    vertical-align: middle;
    margin-left: 32px;
  }
</style>