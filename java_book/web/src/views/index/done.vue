<template>
  <div>
    <Header />
    
    <el-main>
      <el-card class="box-card">
        <div class="success-message">
          <el-icon class="success-icon">
            <Check />
          </el-icon>
          <!-- <p>您的公开共享数据订单 (ODJC20240704144930638) 已提交，您可以到 <a href="#"><router-link to="usercenter/borrowView">个人中心</router-link></a> 里下载数据（15天内有效）。</p> -->
          <p>您的公开共享数据订单 (ODJC20240704144930638) 已通过，您可以下载数据（15天内有效）。点击跳转<a :href="'usercenter/borrowView'">个人中心</a></p>
        </div>
        <el-steps :active="2" finish-status="success" class="steps">
          <el-step title="选择数据"></el-step>
          <el-step title="填写数据用途"></el-step>
          <el-step title="下载数据"></el-step>
        </el-steps>
        <div class="usage-info">
          <h3>使用目的</h3>
          <el-form label-position="left" label-width="140px">
            <el-form-item label="申请用途">
              <el-input v-model="form.purpose" disabled></el-input>
            </el-form-item>
            <el-form-item label="拟支持项目/课题名称">
              <el-input v-model="form.projectName" disabled></el-input>
            </el-form-item>
            <el-form-item label="项目/课题主持人">
              <el-input v-model="form.projectLeader" disabled></el-input>
            </el-form-item>
            <el-form-item label="项目/课题类型">
              <el-input v-model="form.projectType" disabled></el-input>
            </el-form-item>
          </el-form>
          <!-- <el-button type="primary" @click="dialogVisible = true">支付</el-button> -->
         
        </div>
      </el-card>
      <!-- <div id="message">
  </div> -->
      <el-dialog
    v-model="dialogVisible"
    title="请付款"
    width="700"
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :show-close="false"
  
  >
    <!-- <span><img style="width: 45%;" src="/src/views/images/IMG_3598.JPG"></span>
    <span><img style="width: 45%; margin-left: 10%;" src="/src/views/images/IMG_3599.JPG"></span>
     -->
    <p> 支付功能待开发...正在审核中（请等待{{ `${time}秒`  }}） </p>
    <template #footer>
      <div class="dialog-footer">
        <!-- <el-button type="primary" @click="dialogVisible = false">
          关闭
        </el-button> -->
      </div>
      <audio id="audio" controls hidden>
          <source src='/@/assets/audio/application.mp3' type='audio/mp3' />
        </audio>
    </template>
  </el-dialog>

    </el-main>
   
    <Footer />
  </div>
 
</template>

<script setup>
import { ref, onMounted } from 'vue';

import Header from '/@/views/index/components/header.vue'
import Footer from '/@/views/index/components/footer.vue'
import { Check } from '@element-plus/icons-vue';
import { reactive } from 'vue';
const dialogVisible = ref(true);
import { useRouter, useRoute } from 'vue-router';
import { inject } from 'vue';
import { h } from 'vue'
import { ElNotification } from 'element-plus'

const open1 = () => {
  ElNotification({
    title: '恭喜！',
    message: h('i', { style: 'color: teal' }, '审核通过啦！'),
  })
}

const audioVo = inject('audioVo');

const isOn=ref(false);
const router = useRouter();
const route = useRoute();
const form = reactive({
      name: '',
        email: '',
        phone: '',
        company: '',
        purpose: '',
        projectName: '',
        projectLeader: '',
        projectType: '',
     })

      onMounted(() => {
        goToList()
      
     
    });
    //倒计时
    const handleTimeChange = () => {
      if (time.value <= 0) {
        isDisposed.value = false;
        time.value = 20;
      } else {
        setTimeout(() => {
          time.value--;
          handleTimeChange();
        }, 1000);
      }
    };

    const time = ref(20);
    const goToList = () => {

      const formQuery = route.query.form;
      if (formQuery) {
        Object.assign(form, JSON.parse(formQuery));
      }
      console.log('-----', form);



    
    };
  
  const pay = () => {
    
    dialogVisible.value=true;
    console.log('-----', dialogVisible.value);
  };


  var websocket = null;
  var clientId = Math.random().toString(36).substr(2);

  //判断当前浏览器是否支持WebSocket
  if ('WebSocket' in window) {
    //连接WebSocket节点
    websocket = new WebSocket("ws://localhost:9100/api/ws/" + clientId);
  }
  else {
    alert('Not support websocket')
  }

  //连接发生错误的回调方法
  websocket.onerror = function () {
    setMessageInnerHTML("error");
  };

  //连接成功建立的回调方法
  websocket.onopen = function () {
    setMessageInnerHTML("连接成功");
    handleTimeChange();
    console.log("连接成功！！")
  }

  //接收到消息的回调方法
  websocket.onmessage = function (event) {
    setMessageInnerHTML(event.data);
    console.log(isOn)
    if(isOn.value==false){
    open1();
    document.getElementById("audio").currentTime = 0
    document.getElementById("audio").play()
    dialogVisible.value=false;
    // 
  }
    isOn.value=true;

    // alert("审核通过");
  
    // audioVo.value.play();
    // audioVo.play();


  }

  //连接关闭的回调方法
  websocket.onclose = function () {
    setMessageInnerHTML("close");
  }

  //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
  window.onbeforeunload = function () {
    websocket.close();
  }

  //将消息显示在网页上
  function setMessageInnerHTML (innerHTML) {
    // document.getElementById('message').innerHTML += innerHTML + '<br/>';
  }

  //发送消息
  // function send () {
  //   var message = document.getElementById('text').value;
  //   websocket.send(message);
  // }

  //关闭连接
  function closeWebSocket () {
    websocket.close();
  }


// window.AudioContext = window.AudioContext || window.webkitAudioContext;
//  var audioCtx = new AudioContext();
//  var oscillator = audioCtx.createOscillator();
//  var gainNode = audioCtx.createGain();
//  oscillator.connect(gainNode);
//  gainNode.connect(audioCtx.destination);
//  oscillator.type = 'sine';
//  oscillator.frequency.value = 196.00;
//  gainNode.gain.setValueAtTime(0, audioCtx.currentTime);
//  gainNode.gain.linearRampToValueAtTime(1, audioCtx.currentTime + 0.01);
//  oscillator.start(audioCtx.currentTime);
//  gainNode.gain.exponentialRampToValueAtTime(0.001, audioCtx.currentTime + 1);
//  oscillator.stop(audioCtx.currentTime + 1);
 


</script>

<style scoped>
.success-message {
  display: flex;
  align-items: center;
  font-size: 16px;
  color: #606266;
  margin-bottom: 20px;
}

.success-icon {
  font-size: 36px;
  color: #67c23a;
  margin-right: 10px;
}

.steps {
  margin: 20px 0;
}

.usage-info {
  margin-top: 20px;
}

.box-card {
  max-width: 800px;
  margin: 40px auto;
  padding: 20px;
}
</style>
