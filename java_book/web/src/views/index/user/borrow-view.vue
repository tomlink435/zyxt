<template>
  <div class="content-list">
    <div class="list-title">我的申请</div>
    <a-tabs default-active-key="1" @change="onTabChange">
     
    </a-tabs>
    <div class="">
      <div class="order-item-view" v-for="(item, index) in borrowData  " :key="index" v-show="(item.status===1||item.status==0)">
        <div class="header flex-view">
          <div class="left">
            <span class="time">{{getFormatTime(item.orderTime, true)}}</span>
          </div>
          <div class="right">
            <!-- <a-popconfirm
                v-if="item.status==='1'"
                title="确定还书？"
                ok-text="是"
                cancel-text="否"
                @confirm="handleReturn(item)"
            >
              <a-button type="primary" size="small" style="margin-right: 24px;">还书</a-button>
            </a-popconfirm>
            <a-popconfirm
                v-if="item.status==='1'"
                title="确定延期？"
                ok-text="是"
                cancel-text="否"
                @confirm="handleDelay(item)"
            >
              <a-button size="small" style="margin-right: 24px;">延期</a-button>
            </a-popconfirm> -->
            <span class="text">申请状态</span>
            <span class="state">{{item.status===0? '待审批': item.status === 1? '审批通过':'审批未通过'}}</span>
          </div>
        </div>
        <div class="content flex-view" style="height: 260px;">
          <div class="left-list">
            <div class="list-item flex-view">
              <img :src="item.cover" class="thing-img">
              <div class="detail flex-between flex-view">
                <!-- <div class="flex-between flex-top flex-view"> -->
                  <div class="name">{{item.title}}</div>
                  <div class="count">{{item.description}}</div>
                <!-- </div> -->
              </div>
            </div>
          </div>
          <div class="right-info">
            <p class="title" >申请人:{{item.name}}</p>
           
            <p class="title">申请用途:{{item.purpose}}</p>
           
            <p class="title">课题名称:{{item.projectName}}</p>
            
          </div>
        </div>
        <div class="bottom flex-view">
          <div class="left">
            <span class="open" @click="handleDetail(item.thingId)">数据详情</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import {message} from "ant-design-vue";
import {getFormatTime} from '/@/utils/'
import {userBorrowListApi, returnUserBorrowApi, delayUserBorrowApi} from '/@/api/borrow'
import { listApi,listByUserApi } from "/@/api/application";
import {BASE_URL} from "/@/store/constants";
import {useUserStore} from "/@/store";

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const loading = ref(false)
const borrowData = ref([])
const borrowStatus = ref('')

onMounted(()=>{
  getDataList()
})

const onTabChange =(key)=> {
  console.log(key)
  if (key === '1') {
    borrowStatus.value = ''
  }
  if (key === '2') {
    borrowStatus.value = '1'
  }
  if (key === '3') {
    borrowStatus.value = '2'
  }
  getDataList()
}
const getDataList= ()=> {
  loading.value = true
  let userId = userStore.user_id
  console.log(userId)
  listByUserApi(userId)
  .then(res=>{
    res.data.forEach((item, index) => {
      
        // console.log('index',index)
        // item.cover = BASE_URL + '/api/staticfiles/image/' + '1.png'
        // item.cover = BASE_URL + '/api/staticfiles/image/' + '1.png'
        // console.log(item.cover)
      })
    borrowData.value = res.data
    console.log(borrowData)
    // borrowData.value=borrowData.value.slice().reverse()
    // console.log(borrowData)

  })
  // userBorrowListApi({userId: userId, status: borrowStatus.value}).then(res => {
  //   res.data.forEach((item, index) => {
  //     if (item.cover) {
  //       item.cover = BASE_URL + '/api/staticfiles/image/' + item.cover
  //     }
  //   })
  //   borrowData.value = res.data
  //   loading.value = false
  // }).catch(err => {
  //   console.log(err)
  //   loading.value = false
  // })
}
const handleDetail =(thingId) =>{
  // 跳转新页面
  let text = router.resolve({name: 'detail', query: {id: thingId}})
  window.open(text.href, '_blank')
}
const handleReturn =(item)=> {
  returnUserBorrowApi({
    id: item.id
  }).then(res => {
    message.success('还书成功')
    getDataList()
  }).catch(err => {
    message.error(err.msg || '操作失败')
  })
}

const handleDelay =(item)=> {
  delayUserBorrowApi({
    id: item.id
  }).then(res => {
    message.success('延期成功')
    getDataList()
  }).catch(err => {
    message.error(err.msg || '操作失败')
  })
}

</script>
<style scoped lang="less">
.flex-view {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
}

.content-list {
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;

  .list-title {
    color: #152844;
    font-weight: 600;
    font-size: 18px;
    line-height: 24px;
    height: 24px;
    margin-bottom: 4px;
  }
}

.order-item-view {
  background: #f7f9fb;
  border-radius: 4px;
  padding: 16px;
  margin-top: 12px;

  .header {
    border-bottom: 1px solid #cedce4;
    padding-bottom: 12px;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
    font-size: 14px;

    .text {
      color: #6f6f6f;
    }

    .mg-4 {
      margin-left: 4px;
    }

    .num {
      font-weight: 500;
      color: #152844;
    }

    .num {
      font-weight: 500;
      color: #152844;
    }

    .time {
      margin-left: 16px;
      color: #a1adc5;
    }

    .state {
      color: #ff7b31;
      font-weight: 600;
      margin-left: 10px;
    }
  }

  .content {
    padding: 12px 0;
    overflow: hidden;

    .left-list {
      overflow: hidden;
      // height: 132px;
      -webkit-box-flex: 2;
      -ms-flex: 2;
      flex: 2;
      padding-right: 16px;

      .list-item {
        // height: 60px;
        margin-bottom: 12px;
        overflow: hidden;
        cursor: pointer;
      }

      .thing-img {
        width: 40%;
        // height: 100%;
        // margin-right: 12px;
      }

      .detail {
        -webkit-box-flex: 1;
        -ms-flex: 1;
        flex: 1;
        -webkit-box-orient: vertical;
        -webkit-box-direction: normal;
        -ms-flex-direction: column;
        flex-direction: column;
      }

      .flex-between {
        -webkit-box-pack: justify;
        -ms-flex-pack: justify;
        justify-content: space-between;
      }

      .flex-between {
        -webkit-box-pack: justify;
        -ms-flex-pack: justify;
        justify-content: space-between;
      }

      .flex-top {
        -webkit-box-align: start;
        -ms-flex-align: start;
        align-items: flex-start;
      }

      .name {
        color: #152844;
        font-weight: 600;
        // font-size: 14px;
        // line-height: 18px;
        margin: auto auto 10px auto;
      }

      .count {
        color: #484848;
        width: 80%;
        margin: auto;
        font-size: 12px;
        -webkit-line-clamp:3;
          text-overflow: ellipsis; 
          display: -webkit-box;
          -webkit-box-orient: vertical;
          overflow: hidden;
          // white-space: nowrap;
          // -webkit-line-clamp: 3; 
      }

      .flex-between {
        -webkit-box-pack: justify;
        -ms-flex-pack: justify;
        justify-content: space-between;
      }

      .flex-center {
        -webkit-box-align: center;
        -ms-flex-align: center;
        align-items: center;
      }

      .type {
        color: #6f6f6f;
        font-size: 12px;
      }

      .price {
        color: #ff7b31;
        font-weight: 600;
        font-size: 14px;
      }
    }

    .right-info {
      -webkit-box-flex: 1;
      -ms-flex: 1;
      flex: 1;
      border-left: 1px solid #cedce4;
      padding-left: 12px;
      line-height: 22px;
      font-size: 14px;

      .title {
        height: 30%;
        color: #6f6f6f;
        -webkit-line-clamp:3;
          text-overflow: ellipsis; 
          display: -webkit-box;
          -webkit-box-orient: vertical;
          overflow: hidden;
      }

      .name {
        color: #152844;
      }

      .text {
        color: #484848;
      }

      .mg {
        margin-bottom: 4px;
      }
    }
  }

  .bottom {
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    border-top: 1px solid #cedce4;
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
    font-size: 14px;
    padding-top: 14px;

    .text {
      color: #6f6f6f;
    }

    .open {
      color: #4684e2;
      margin-left: 8px;
      cursor: pointer;
    }

    .right {
      -webkit-box-align: center;
      -ms-flex-align: center;
      align-items: center;
    }

    .text {
      color: #6f6f6f;
    }

    .num {
      color: #152844;
      margin: 0 40px 0 8px;
    }

    .money {
      font-weight: 600;
      font-size: 18px;
      color: #ff7b31;
      margin-left: 8px;
    }
  }

}

.order-item-view:first-child {
  margin-top: 16px;
}

</style>
