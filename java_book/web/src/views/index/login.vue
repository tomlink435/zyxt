<template>
  <div class="container">
    <div class="login-page pc-style">
      <!-- <img :src="LogoIcon" alt="logo" class="logo-icon"> -->
      <div class="login-tab">
        <div class="tab-selected">
          <span>手机号登录</span>
          <span class="tabline tabline-width"></span>
        </div>
      </div>
      <div class="mail-login" type="login">
        <div class="common-input">
          <img :src="MailIcon" class="left-icon">
          <div class="input-view">
            <input placeholder="请输入手机号" v-model="pageData.loginForm.phone" type="text" class="input">
            <p class="err-view">
            </p>
          </div>
        </div>
        <div class="common-input">
          <img :src="PwdIcon" class="left-icon">
          <div class="input-view">
            <input placeholder="请输入验证码" v-model="pageData.loginForm.code" type="text" class="input">
            <p class="err-view">
            </p>
          </div>
          <div class="input-group mb-3 px-5">
      <!-- <span class="input-group-text">验证码</span> -->
      <!-- <input
        type="text"
        v-model="pageData.loginForm.code"
        class="form-control"
        aria-label="Amount (to the nearest dollar)"
        required
      /> -->
      <button
        type="button"
        @click="getCode"
        :disabled="isDisposed"
        class="btn btn-primary"
      >
        {{ isDisposed ? `${time}s后重新获取` : "获取验证码" }}
      </button>
    </div>

          <!-- <button type="primary" class="right-btn" @click="getCode">获取验证码</button> -->
       
          <!-- <img src="@/assets/pwd-hidden.svg" class="right-icon"> -->
          <!---->
        </div>
        <div class="next-btn-view">
          <button class="next-btn btn-active" style="margin: 16px 0px;" @click="handleLogin">登录</button>
        </div>
      </div>
      <div class="operation">
        <!-- <a @click="handleCreateUser" class="forget-pwd" style="text-align: left;">注册新帐号</a> -->
        <!-- <a class="forget-pwd" style="text-align: right;">忘记密码？</a> -->
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {useUserStore} from '/@/store';
import {message} from "ant-design-vue";
import LogoIcon from '/@/assets/images/k-logo.png';
import MailIcon from '/@/assets/images/mail-icon.svg';
import PwdIcon from '/@/assets/images/pwd-icon.svg';


const router = useRouter();
const userStore = useUserStore();

const pageData = reactive({
  loginForm: {
    phone: '',
    code: ''
  }
})
const handleTimeChange = () => {
      if (time.value <= 0) {
        isDisposed.value = false;
        time.value = 60;
      } else {
        setTimeout(() => {
          time.value--;
          handleTimeChange();
        }, 1000);
      }
    };

    const time = ref(60);
    const isDisposed = ref(false);

const getCode = () => {
  userStore.getCode({
    phone: pageData.loginForm.phone
  }).then(res=> {
    message.success('验证码已发送')

    isDisposed.value = true;
    handleTimeChange();

  }).catch(err => {
    console.log(pageData.loginForm.phone)
    message.warn(err.msg || '获取验证码失败')
  })

}

const handleLogin = ()=> {
  userStore.login({
    phone: pageData.loginForm.phone,
    code: pageData.loginForm.code
  }).then(res=> {
    loginSuccess()
    console.log('success==>', userStore.user_name)
    console.log('success==>', userStore.user_id)
    console.log('success==>', userStore.user_token)
  }).catch(err => {
    message.warn(err.msg || '登录失败')
  })
}
// const getCode = ()=> {
//   userStore.getCode({
//     phone: pageData.loginForm.phone
//   }).then(res=> {
//     message.success('验证码已发送')
//   }).catch(err => {
//     console.log(pageData.loginForm.phone)
//     message.warn(err.msg || '获取验证码失败')
//   })
// }

const handleCreateUser = () => {
  router.push({name:'register'})
}

const loginSuccess= ()=> {
  router.push({ name: 'portal' })
  message.success('登录成功！')
}


</script>
<style scoped lang="less">
div {
  display: block;
}

.container {
  //background-color: #f1f1f1;
  background-image: url('../images/admin-login-bg.jpg');
  background-size: cover;
  object-fit: cover;
  height: 100%;
  max-width: 100%;
  display:flex;
  justify-content: center;
  align-items:center;
}

.new-content {
  position: absolute;
  left: 0;
  right: 0;
  margin: 80px auto 0;
  width: 980px;
}

.logo-img {
  width: 125px;
  display: block;
  margin-left: 137.5px;
}

.login-page {
  overflow: hidden;
  background: #fff;

  .logo-icon {
    margin-top: 20px;
    margin-left: 175px;
    width: 48px;
    height: 48px;
  }
}

.pc-style {
  position: relative;
  width: 400px;
  height: 464px;
  background: #fff;
  border-radius: 4px;
  -webkit-box-shadow: 2px 2px 6px #aaa;
  box-shadow: 2px 2px 6px #aaa;
}

.login-tab {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  color: #1e1e1e;
  font-size: 14px;
  color: #1e1e1e;
  font-weight: 500;
  height: 46px;
  line-height: 44px;
  margin-bottom: 40px;
  border-bottom: 1px solid #c3c9d5;

  div {
    position: relative;
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;
    text-align: center;
    cursor: pointer;
  }

  .tabline {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    margin: 0 auto;
    display: inline-block;
    width: 0;
    height: 2px;
    background: #3d5b96;
    -webkit-transition: width .5s cubic-bezier(.46, 1, .23, 1.52);
    transition: width .5s cubic-bezier(.46, 1, .23, 1.52);
  }

  tab-selected {
    color: #1e1e1e;
    font-weight: 500;
  }

  .mail-login, .tel-login {
    padding: 0 28px;
  }

}

.mail-login {
  margin: 0px 24px;
}

.common-input {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-align: start;
  -ms-flex-align: start;
  align-items: flex-start;

  .left-icon {
    margin-right: 12px;
    width: 24px;
  }

  .input-view {
    -webkit-box-flex: 1;
    -ms-flex: 1;
    flex: 1;

    .input {
      font-weight: 500;
      font-size: 14px;
      color: #1e1e1e;
      height: 26px;
      line-height: 26px;
      border: none;
      padding: 0;
      display: block;
      width: 100%;
      letter-spacing: 1.5px;
    }

    err-view {
      margin-top: 4px;
      height: 16px;
      line-height: 16px;
      font-size: 12px;
      color: #f62a2a;
    }
  }
}

.next-btn {
  background: #3d5b96;
  border-radius: 4px;
  color: #fff;
  font-size: 14px;
  font-weight: 500;
  height: 40px;
  line-height: 40px;
  text-align: center;
  width: 100%;
  outline: none;
  cursor: pointer;
}

button {
  background: transparent;
  padding: 0;
  border-width: 0px;
}

button, input, select, textarea {
  margin: 0;
  padding: 0;
  outline: none;
}

.operation {
  display: flex;
  flex-direction: row;
  margin: 0 24px;
}

.forget-pwd {
  //text-align: center;
  display: block;
  overflow: hidden;
  flex:1;
  margin: 0 auto;
  color: #3d5b96;
  font-size: 14px;
  cursor: pointer;
}

</style>
