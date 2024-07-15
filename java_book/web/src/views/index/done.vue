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
          <p>您的公开共享数据订单 (ODJC20240704144930638) 已提交，您可以在审核通过后下载数据（15天内有效）。</p>
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
          <el-button type="primary" @click="dialogVisible = true">支付</el-button>
         
        </div>
      </el-card>
      <el-dialog
    v-model="dialogVisible"
    title="请付款"
    width="700"
  
  >
    <span><img style="width: 45%;" src="/src/views/images/IMG_3598.JPG"></span>
    <span><img style="width: 45%; margin-left: 10%;" src="/src/views/images/IMG_3599.JPG"></span>
    <template #footer>
      <div class="dialog-footer">
        <!-- <el-button @click="dialogVisible = false">关闭</el-button> -->
        <el-button type="primary" @click="dialogVisible = false">
          关闭
        </el-button>
      </div>
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
const dialogVisible = ref(false);
import { useRouter, useRoute } from 'vue-router';
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
