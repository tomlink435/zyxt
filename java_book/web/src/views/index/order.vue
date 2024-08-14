<template>
    <Header/>
  <div class="form-container">
    <el-form  :model="form" label-width="200px" label-position="left">
      <!-- 基本信息 -->
      <el-card class="box-card" header="基本信息">
        <el-form-item
          label="真实姓名"
          :rules="[{ required: true, message: '请输入真实姓名', trigger: 'blur' }]"
        >
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item
          label="E-mail"
          :rules="[{ required: true, message: '请输入有效的E-mail', trigger: 'blur', type: 'email' }]"
        >
          <el-input v-model="form.email"></el-input>
        </el-form-item>
        <el-form-item
          label="电话"
          :rules="[{ required: true, message: '请输入电话号码', trigger: 'blur' }]"
        >
          <el-input v-model="form.phone"></el-input>
        </el-form-item>
        <el-form-item
          label="工作单位"
          :rules="[{ required: true, message: '请输入工作单位', trigger: 'blur' }]"
        >
          <el-input v-model="form.company"></el-input>
        </el-form-item>
      </el-card>

      <!-- 用途信息 -->
      <el-card class="box-card" header="用途信息" style="padding-bottom: 5%;">
        <el-form-item
          label="数据使用用途"
          :rules="[{ required: true, message: '请输入数据使用用途（不少于20字）', trigger: 'blur', min: 20 }]"
        >
          <el-input type="textarea" v-model="form.purpose"></el-input>
        </el-form-item>
        <el-form-item
          label="拟支持项目/课题名称"
          :rules="[{ required: true, message: '请输入拟支持项目/课题名称', trigger: 'blur' }]"
        >
          <el-input v-model="form.projectName"></el-input>
        </el-form-item>
        <el-form-item
          label="项目/课题主持人"
          :rules="[{ required: true, message: '请输入项目/课题主持人', trigger: 'blur' }]"
        >
          <el-input v-model="form.projectLeader"></el-input>
        </el-form-item>
        <el-form-item
          label="项目/课题类型"
          :rules="[{ required: true, message: '请选择项目/课题类型', trigger: 'change' }]"
        >
          <el-select v-model="form.projectType" placeholder="请选择">
            <el-option label="科技部-国家科技重大专项" value="科技部-国家科技重大专项"></el-option>
            <!-- 可添加更多选项 -->
          </el-select>
        </el-form-item>
        <el-button type="primary" @click="onSubmit" style="float: right;">提交</el-button>

      </el-card>

      <!-- 按钮区域 -->
      <div class="button-container">
        <!-- <el-button type="warning" @click="onReset">清空</el-button> -->
      </div>
    </el-form>
  </div>
  <Footer/>
</template>

<script setup>
import Header from '/@/views/index/components/header.vue'
import Footer from '/@/views/index/components/footer.vue'
import { saveApi } from "/@/api/application";
import { USER_ID } from '/@/store/constants';
const router = useRouter();
const route = useRoute();
  onMounted(() => {
    const formQuery = route.query.form;
      if (formQuery) {
        Object.assign(formQuery, JSON.parse(formQuery));
      }
      console.log('-----', formQuery);
      debugger


  });
     const form = reactive({
      name: '',
        email: '',
        phone: '',
        company: '',
        purpose: '',
        projectName: '',
        projectLeader: '',
        projectType: '',
        userId:localStorage.getItem(USER_ID),
        thingId:route.query.thingId,
     })
        


    // // 提交表单
    const onSubmit=()=> {
      saveApi(form)
          alert('提交成功！');
          console.log('提交的数据：', form);
          // this.$router.push({ name: 'done', params: { form: this.form } });
          router.push({ name: 'done', query: { form: JSON.stringify(form) } });
      }
    

</script>

<style scoped>
.form-container {
  max-width: 600px;
  margin: 0 auto;
}

.box-card {
  margin-bottom: 20px;
}

.button-container {
  display: flex;
  justify-content: space-between;
  padding: 10px;
}
</style>
