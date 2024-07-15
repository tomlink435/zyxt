<template>
  <div>
    <!--页面区域-->
    <div class="page-view">
      <div class="table-operations">
        <a-space>
          <!-- <a-button type="primary" @click="handleAdd">新增</a-button> -->
          <!-- <a-button @click="handleBatchDelete">批量删除</a-button> -->
        </a-space>
      </div>
      <a-table
          size="middle"
          rowKey="id"
          :loading="data.loading"
          :columns="columns"
          :data-source="data.noticeList"
          :scroll="{ x: 'max-content' }"
          
          :pagination="{
          size: 'default',
          current: data.page,
          pageSize: data.pageSize,
          onChange: (current) => (data.page = current),
          showSizeChanger: false,
          showTotal: (total) => `共${total}条数据`,
        }"
      >
        <template #bodyCell="{ text, record, index, column }">
          <template v-if="column.key === 'operation'">
            <span>
              <a @click="handleEdit(record)">通过</a>
              <a-divider type="vertical" />
              <a @click="reject(record)">否决</a>
              <!-- <a-popconfirm title="确定删除?" ok-text="是" cancel-text="否" @confirm="confirmDelete(record)"> -->
                <!-- <a href="#">否决</a> -->
              <!-- </a-popconfirm> -->
            </span>
          </template>
        </template>
      </a-table>
    </div>

    <!--弹窗区域-->
    <div>
      <a-modal
          :visible="modal.visile"
          :forceRender="true"
          :title="modal.title"
          ok-text="确认"
          cancel-text="取消"
          @cancel="handleCancel"
          @ok="handleOk"
      >
        <div>
          <a-form ref="myform" :label-col="{ style: { width: '80px' } }" :model="modal.form" :rules="modal.rules">
            <a-row :gutter="24">
              <a-col span="24">
                <a-form-item label="标题" name="title">
                  <a-input placeholder="请输入标题" v-model:value="modal.form.title"></a-input>
                </a-form-item>
              </a-col>
              <a-col span="24">
                <a-form-item label="通知内容" name="content">
                  <a-textarea placeholder="请输入内容" :rows="4" v-model:value="modal.form.content"></a-textarea>
                </a-form-item>
              </a-col>
            </a-row>
          </a-form>
        </div>
      </a-modal>
    </div>
  </div>
</template>

<script setup lang="ts">
import { FormInstance, message } from 'ant-design-vue';
// import { createApi, listApi, updateApi, deleteApi } from '/@/api/notice';
import { listApi ,passApi,rejectApi} from '/@/api/application';
import { forEach } from 'lodash';
import { requiredNumber } from 'element-plus/es/components/table-v2/src/common';

const columns = reactive([
{
    title: '序号',
    dataIndex: 'index',
    key: 'index',
    align: 'center'
  }, {
    title: '姓名',
    dataIndex: 'name',
    key: 'name',
    align: 'center'
  }, {
    title: '电话',
    dataIndex: 'phone',
    key: 'phone',
    align: 'center'
  }, {
    title: '地址',
    dataIndex: 'address',
    key: 'address',
  
    align: 'center'
  }, {
    title: '描述',
    dataIndex: 'description',
    key: 'description',
    align: 'center'
  }, {
    title: '公司',
    dataIndex: 'company',
    key: 'company',
    align: 'center'

  },
  {
    title: '目的',
    dataIndex: 'purpose',
    key: 'purpose',
    align: 'center'
  },

  // {
  //   title: '标题',
  //   dataIndex: 'title',
  //   key: 'title',
  //   align: 'center'
  // },
  // {
  //   title: '内容',
  //   dataIndex: 'content',
  //   key: 'content',
  //   align: 'center',
  //   customRender: ({ text, record, index, column }) => text?.substring(0, 20) + '...',
  // },
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

// 弹窗数据源
const modal = reactive({
  visile: false,
  editFlag: false,
  title: '',
  form: {
    id: undefined,
    title: undefined,
  },
  rules: {
    title: [{ required: true, message: '请输入', trigger: 'change' }],
  },
});

const myform = ref<FormInstance>();

onMounted(() => {
  getDataList();
});

const getDataList = () => {
  data.loading = true;
  listApi({
    // keyword: data.keyword,
  })
      .then((res) => {
        res.data=filterData(res.data)
        data.loading = false;
        console.log('data222222',res.data);
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


const rowSelection = ref({
  onChange: (selectedRowKeys: (string | number)[], selectedRows: DataItem[]) => {
    console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
    data.selectedRowKeys = selectedRowKeys;
  },
});

const handleAdd = () => {
  resetModal();
  modal.visile = true;
  modal.editFlag = false;
  modal.title = '新增';
  // 重置
  for (const key in modal.form) {
    modal.form[key] = undefined;
  }
};
const handleEdit = (record: any) => {
  console.log(record.id)
  passApi({id:record.id})
  .then((res) => {
    getDataList();
  })
  .catch((err) => {
    message.error(err.msg || '操作失败');
  });
  // resetModal();
  // modal.visile = true;
  // modal.editFlag = true;
  // modal.title = '通过';
  // // 重置
  // for (const key in modal.form) {
  //   modal.form[key] = undefined;
  // }
  // for (const key in record) {
  //   modal.form[key] = record[key];
  // }
};
const reject = (record: any) => {
 
  rejectApi({id:record.id})
  .then((res) => {
    getDataList();
  })
  .catch((err) => {
    message.error(err.msg || '操作失败');
  });
  // resetModal();
  // modal.visile = true;
  // modal.editFlag = true;
  // modal.title = '通过';
  // // 重置
  // for (const key in modal.form) {
  //   modal.form[key] = undefined;
  // }
  // for (const key in record) {
  //   modal.form[key] = record[key];
  // }
};
const confirmDelete = (record: any) => {
  console.log('delete', record);
  deleteApi({ ids: record.id })
      .then((res) => {
        getDataList();
      })
      .catch((err) => {
        message.error(err.msg || '操作失败');
      });
};

const handleBatchDelete = () => {
  console.log(data.selectedRowKeys);
  if (data.selectedRowKeys.length <= 0) {
    console.log('hello');
    message.warn('请勾选删除项');
    return;
  }
  deleteApi({ ids: data.selectedRowKeys.join(',') })
      .then((res) => {
        message.success('删除成功');
        data.selectedRowKeys = [];
        getDataList();
      })
      .catch((err) => {
        message.error(err.msg || '操作失败');
      });
};

const handleOk = () => {
  myform.value
      ?.validate()
      .then(() => {
        if (modal.editFlag) {
          updateApi(modal.form)
              .then((res) => {
                hideModal();
                getDataList();
              })
              .catch((err) => {
                console.log(err);
                message.error(err.msg || '操作失败');
              });
        } else {
          createApi(modal.form)
              .then((res) => {
                hideModal();
                getDataList();
              })
              .catch((err) => {
                console.log(err);
                message.error(err.msg || '操作失败');
              });
        }
      })
      .catch((err) => {
        console.log('不能为空');
      });
};

const handleCancel = () => {
  hideModal();
};

// 恢复表单初始状态
const resetModal = () => {
  myform.value?.resetFields();
};

// 关闭弹窗
const hideModal = () => {
  modal.visile = false;
};

/**
 * 过滤对象数组，去除 status 属性不为 0 的对象
 * @param {Array<Object>} dataArray - 要过滤的对象数组
 * @returns {Array<Object>} 过滤后的对象数组
 */
 const filterData = (dataArray) => {
  // forEach(dataArray, (item) => {
  //   if (item.status !== 0) {
  //     console.log(item)
  //     dataArray.push(item)
     
  //   }
  // });
  return dataArray.filter(item => item.status === 0);
};
</script>

<style scoped lang="less">
.page-view {
  min-height: 100%;
  background: #fff;
  padding: 24px;
  display: flex;
  flex-direction: column;
}

.table-operations {
  margin-bottom: 16px;
  text-align: right;
}

.table-operations > button {
  margin-right: 8px;
}
</style>
