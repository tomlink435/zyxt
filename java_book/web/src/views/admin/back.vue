<!-- 被驳回的申请资源请求----就只拥有一个表单 -->
<template>
	<div>
		<div class="page-view">
		  <div class="table-operations">
		    <a-space>
		      <a-button @click="handleBatchDelete">批量驳回</a-button>
		    </a-space>
		  </div>
		  <a-table
		      size="middle"
		      rowKey="id"
		      :loading="data.loading"
		      :columns="columns"
		      :data-source="data.noticeList"
		      :scroll="{ x: 'max-content' }"
		      :row-selection="rowSelection"
		      :pagination="{
		      size: 'default',
		      current: data.page,
		      pageSize: data.pageSize,
		      onChange: (current) => (data.page = current),
		      showSizeChanger: false,
		      showTotal: (total) => `共${total}条数据`,
		    }"
		  >
		    <!-- <template #bodyCell="{ text, record, index, column }">
		      <template v-if="column.key === 'operation'">
		        <span>
		          <a @click="handleEdit(record)">通过</a>
		          <a-divider type="vertical" />
		          <a-popconfirm title="确认驳回?" ok-text="是" cancel-text="否" @confirm="confirmDelete(record)">
		            <a href="#">驳回</a>
		          </a-popconfirm>
		        </span>
		      </template>
		    </template> -->
		  </a-table>
		</div>
	</div>
</template>

<script setup lang="ts">
import { FormInstance, message } from 'ant-design-vue';
import { createApi, listApi, updateApi, deleteApi } from '/@/api/notice';

const columns = reactive([
  {
	title: '序号',
	dataIndex: 'index',
	key: 'index',
	align: 'center'
  },
  {
	title: '申请人',
	dataIndex: 'title',
	key: 'title',
	align: 'center'
  },
  {
	title: '申请权限',
	dataIndex: 'title',
	key: 'title',
	align: 'center'
  },
  {
	title: '待申请数据',
	dataIndex: 'content',
	key: 'content',
	align: 'center',
	customRender: ({ text, record, index, column }) => text?.substring(0, 20) + '...',
  },
  {
	title: '操作',
	dataIndex: 'action',
	key: 'operation',
	align: 'center',
	fixed: 'right',
	width: 140,
  },
]);

// 页面数据
const data = reactive({
  noticeList: [],
  loading: false,
  keyword: '',
  selectedRowKeys: [] as any[],
  pageSize: 10,
  page: 1,
});

onMounted(() => {
  getDataList();
});

const getDataList = () => {
  data.loading = true;
  listApi({
    keyword: data.keyword,
  })
      .then((res) => {
        data.loading = false;
        res.data.forEach((item: any, index: any) => {
          item.index = index + 1;
        });
        data.noticeList = res.data;
        data.loading = false;
      })
      .catch((err) => {
        data.loading = false;
        console.log(err);
      });
};


</script>

<style>
</style>