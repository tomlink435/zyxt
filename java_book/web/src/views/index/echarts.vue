<template>
    <div>
      <!-- 头部 -->
      <header>
        <h1>数据可视化-ECharts</h1>
        <div class="show-time"></div>
      </header>
  
      <!-- !-- 页面主体 --> 
    <section class="mainbox">
      <!-- 左侧盒子 -->
      <div class="column">
        <div class="panel bar">
          <h2>柱形图-就业行业</h2>
          <!-- 图表放置盒子 -->
          <div class="chart"></div>
          <!-- 伪元素绘制盒子下边角 -->
          <div class="panel-footer"></div>
        </div>
        <div class="panel line">
          <h2>折线图-人员变化
            <a class="a-active" href="javascript:;">2020</a>
            <a href="javascript:;">2021</a>
          </h2>
          <div class="chart"></div>
          <div class="panel-footer"></div>
        </div>
        <div class="panel pie">
          <h2>饼形图-年龄分布</h2>
          <div class="chart"></div>
          <div class="panel-footer"></div>
        </div>
      </div>
      <!-- 中间盒子 -->
      <div class="column">
        <!-- 头部 no模块 -->
        <div class="no">
          <div class="no-hd">
            <ul>
              <li>12434</li>
              <li>10983</li>
            </ul>
          </div>
          <div class="no-bd">
            <ul>
              <li>前端需求人数</li>
              <li>市场供应人数</li>
            </ul>
          </div>
        </div>
        <!-- map模块 -->
        <div class="map">
          <div class="map1"></div>
          <div class="map2"></div>
          <div class="map3"></div>
          <div class="chart"></div>
        </div>
      </div>
      <!-- 右侧盒子 -->
      <div class="column">
        <div class="panel bar2">
          <h2>柱形图-就业行业</h2>
          <div class="chart"></div>
          <div class="panel-footer"></div>
        </div>
        <div class="panel line2">
          <h2>折线图-播放量</h2>
          <div class="chart"></div>
          <div class="panel-footer"></div>
        </div>
        <div class="panel pie2">
          <h2>饼形图-地区分布</h2>
          <div class="chart"></div>
          <div class="panel-footer"></div>
        </div>
      </div>
    </section>
    </div>
  </template>
  
  <script>
  export default {
    data() {
      return {};
    },
    mounted() {
      this.startClock();
    },
    methods: {
      startClock() {
        let t = null;
        const time = () => {
          clearTimeout(t);
          const dt = new Date();
          const y = dt.getFullYear();
          const mt = dt.getMonth() + 1;
          const day = dt.getDate();
          const h = dt.getHours();
          const m = dt.getMinutes();
          const s = dt.getSeconds();
          document.querySelector(".show-time").innerHTML =
            `当前时间：${y}年${mt}月${day}日-${h}时${m}分${s}秒`;
          t = setTimeout(time, 1000);
        };
        time();
      }
    }
  };
  </script>
  
  <style scoped>
  /* Your existing CSS styles */
  @import url('../css/index.css');
  </style>