<template>
	 <div>
		 
		 <div class="block" >
		      <el-date-picker
		            v-model="value2"
		            type="datetimerange"
		            start-placeholder="2024-07-01"
		            end-placeholder="2024-07-05"
		            :default-time="defaultTime2"
		          />
		  </div>
		   <div ref="chart" style="width: 100%; height: 400px;"></div>
	 </div>
</template>

<script setup >
import * as echarts from 'echarts';
import { onMounted, ref } from 'vue';
 const chart = ref(null);

    onMounted(() => {
      const myChart = echarts.init(chart.value);
      const option = {
        title: {
          text: '收入折线图',
        },
        tooltip: {
          trigger: 'axis',
        },
        xAxis: {
          type: 'category',
          data: ['2024-07-01', '2024-07-02', '2024-07-03', '2024-07-04', '2024-07-05'],
        },
        yAxis: {
          type: 'value',
        },
        series: [
          {
            name: '收入',
            type: 'line',
            data: [1000, 2000, 1500, 3000, 2500],
          },
        ],
      };

      myChart.setOption(option);
    });
</script>

<style>
	.block{
		
		   display: flex;
		   justify-content: center;
		   width: 50%;
		   height: 50%;
		   margin: 0 auto;
	}
	.demo-datetime-picker {
	  display: flex;
	  width: 100%;
	  padding: 0;
	  flex-wrap: wrap;
	  justify-content: space-around;
	  align-items: stretch;
	}
	.demo-datetime-picker .block {
	  padding: 30px 0;
	  text-align: center;
	}
	.line {
	  width: 1px;
	  background-color: var(--el-border-color);
	}
</style>



