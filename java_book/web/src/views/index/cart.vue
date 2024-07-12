<template>
  <div>
    <Header />
    <el-main>
      <el-card class="box-card">
        <div class="success-message">
          <el-icon class="success-icon">
            <Check />
          </el-icon>
          <p>* 资源车目前共有 3 个资源，请您选好所需资源后再生成订单，以免多次填写数据用途等信息
公开共享数据:填写数据用途等信息后可直接下载; 协议共享数据:签署下载和使用协议后可进行下载</p>
        </div>
        <el-steps :active="1" finish-status="success" class="steps">
          <el-step title="选择数据"></el-step>
          <el-step title="填写数据用途"></el-step>
          <el-step title="下载数据"></el-step>
        </el-steps>
        <div class="usage-info">
          <el-table :data="tableData" style="width: 100%">
      <el-table-column prop="date" label="资源类型" width="180">
      </el-table-column>
      <el-table-column prop="name" label="资源名称" width="180">
      </el-table-column>
      <el-table-column prop="address" label="提供者">
      </el-table-column>
    </el-table>
          <!-- <el-form label-position="left" label-width="140px"> -->
           
              <!-- <img style="width: 758px" src="/src/views/images/bj.jpg"> -->

          
            
          <!-- </el-form> -->
          <!-- <img src="src/views/images/map.png"> -->
        </div>
      <el-button type="primary" @click="router.push('/index/order')" class="next-button" style="float: right;">提交</el-button>

      </el-card>
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
const tableData = ref([
      { date: '数据', name: '2001-2023年中国玉米种植分布数据集', address: '彭琼艳；沈若缺；李向前；' },
      { date: '数据', name: '2010s中国陆地生态系统碳密度数据集', address: '徐丽；何念鹏；于贵瑞' },
      { date: '数据', name: '2000-2022年中国30米年最大NDVI数据集', address: '董金玮；周岩；尤南山；程曹' },
    ]);

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
