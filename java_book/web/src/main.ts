import { createApp } from 'vue';
import App from './App.vue';
import router from './router';
import piniaStore from './store';
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import bootstrap from './core/bootstrap';
import '/@/styles/reset.less';
import '/@/styles/index.less';
import Antd from 'ant-design-vue';

const app = createApp(App);

app.use(Antd);
app.use(router);
app.use(piniaStore);
app.use(bootstrap);
app.use(ElementPlus)
app.mount('#app');
