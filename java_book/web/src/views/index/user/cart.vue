<template>
  <div class="content-list">
    <div class="list-title">我的资源车</div>
    <!-- <a-tabs default-active-key="1" @change="onTabChange">
    </a-tabs> -->
    <div class="content-right">
      <!-- <div class="top-select-view flex-view">
        <div class="order-view">
          <span class="title"></span>
          <span
            class="tab"
            :class="contentData.selectTabIndex===index? 'tab-select':''"
            v-for="(item,index) in contentData.tabData"
            :key="index"
            @click="selectTab(index)">
            {{ item }}
          </span>
          <span :style="{left: contentData.tabUnderLeft + 'px'}" class="tab-underline"></span>
        </div>
      </div> -->
      <a-spin :spinning="contentData.loading" style="min-height: 200px;">

        <div class="pc-book-list flex-view">
          <div v-for="item in contentData.thingData" :key="item.id"  class="book-item item-column-3" ><!---->
            <div class="img-view">
                <img :src="item.cover"/>
            </div>
            <div class="info-view">
            
              <h3 class="book-name" @click="handleDetail(item.id)">{{item.title}} 
                  <!-- <span :class="{'public': item.id % 2 === 0, 'private': item.id % 2 !== 0}">
                {{ item.id % 2 === 0 ? '公开' : '非公开' }}
              </span> -->
            </h3>
              
              <p class="authors"> {{item.author}} </p>
              <p class="translators" v-if="item.description"> {{item.description}} </p>
              <div class="button-container">
            <!-- <el-button type="success" icon="el-icon-shopping-cart" @click="addToCart(item)">加入资源车</el-button> -->
            <el-button type="primary" @click="downloadFile(item)">在线下载</el-button>
            <el-button type="warning">收藏数据集 </el-button>
          </div></div>
              
          </div>
          <!-- <div v-if="contentData.pageData.length <= 0 && !contentData.loading" class="no-data" style="">暂无数据</div> -->
        </div>


      </a-spin>
      <div class="page-view" style="">
        <a-pagination
          v-model="contentData.page" size="small" @change="changePage" :hideOnSinglePage="true"
          :defaultPageSize="contentData.pageSize" :total="contentData.total" :showSizeChanger="false" />
      </div>
    </div>
        
  </div>
</template>

<script setup>
import {message} from "ant-design-vue";
import {getFormatTime} from '/@/utils/'
import {userBorrowListApi, returnUserBorrowApi, delayUserBorrowApi} from '/@/api/borrow'
import { listApi } from "/@/api/application";
import {BASE_URL} from "/@/store/constants";
import {useUserStore} from "/@/store";
import {listApi as listThingList} from '/@/api/thing'

const router = useRouter();
const route = useRoute();
const userStore = useUserStore();

const loading = ref(false)
const borrowData = ref([])
const borrowStatus = ref('')
const contentData = reactive({
  selectX: 0,
  selectTagId: -1,
  cData: [],
  selectedKeys: [],
  tagData: [],
  loading: false,

  tabData: ['最新', '最热', '推荐'],
  selectTabIndex: 0,
  tabUnderLeft: 12,

  thingData: [],
  pageData: [],

  page: 1,
  total: 0,
  pageSize: 12,
})
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
  listApi().then(res=>{
    
    borrowData.value = res.data
    console.log(borrowData)

  })
  listThingList().then(res => {
    contentData.loading = false
    res.data.forEach((item, index) => {
      if (item.cover) {
        // item.cover = BASE_URL + '/api/staticfiles/image/' +  item.cover
        item.cover =  item.cover
        console.log('item.cover:'+item.cover)
     
      }
    })
    console.log(res)
    contentData.thingData = res.data
    contentData.total = contentData.thingData.length
    // changePage(1)
  }).catch(err => {
    console.log(err)
    contentData.loading = false
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

const downloadFile=(item)=> {
  console.log(item)
  // debugger
  if(item.id%2==1){
    let userId = userStore.user_id
  if (userId) {
    router.push({name: 'order'});
    // router.push({name: 'login'})

    // router.push({ name: 'order',query: {thingId: thingId.value} });
	console.log('下载文件')
  } 
  else {
    message.warn('请先登录！')
    router.push({name: 'login'})
  }
    }
    else{
      // window.open("src/views/index/resources/【数据说明文档】2010s中国陆地生态系统碳密度数据集.xls")
      // window.open('https://gitee.com/WananRd/TrainTicketSystem/raw/master/%E3%80%90%E6%95%B0%E6%8D%AE%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3%E3%80%912018-2024%E5%B9%B4%E4%B8%AD%E 5%9B%BD10%E7%B1%B3%E7%A9%BA%E9%97%B4%E5%88%86%E8%BE%A8%E7%8E%87%E5%86%AC%E5%B0%8F%E9%BA%A6%E8%AF%86%E5%88%AB%E6%95%B0%E6%8D%AE%E9%9B%86.xl','_blank')
    window.open('https://gitee.com/WananRd/TrainTicketSystem/raw/master/%E3%80%90%E6%95%B0%E6%8D%AE%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3%E3%80%912018-2024%E5%B9%B4%E4%B8%AD%E5%9B%BD10%E7%B1%B3%E7%A9%BA%E9%97%B4%E5%88%86%E8%BE%A8%E7%8E%87%E5%86%AC%E5%B0%8F%E9%BA%A6%E8%AF%86%E5%88%AB%E6%95%B0%E6%8D%AE%E9%9B%86.xls')
    }
	

    }

</script>
<style scoped lang="less">
.flex-view {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  flex-direction: column;
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
      height: 132px;
      -webkit-box-flex: 2;
      -ms-flex: 2;
      flex: 2;
      padding-right: 16px;

      .list-item {
        height: 60px;
        margin-bottom: 12px;
        overflow: hidden;
        cursor: pointer;
      }

      .thing-img {
        width: 48px;
        height: 100%;
        margin-right: 12px;
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
        font-size: 14px;
        line-height: 18px;
      }

      .count {
        color: #484848;
        font-size: 12px;
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
        color: #6f6f6f;
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
.pc-book-list {
    -ms-flex-wrap: wrap;
    flex-wrap: wrap;

    .book-item {
      width: 800px;
      position: relative;
      flex: 1;
      margin-right: 20px;
      height: fit-content;
      border-radius: 4px;
      overflow: hidden;
      margin-top: 16px;
      cursor: pointer;

      .img-view {
        width: 30%;
        background: #eaf1f5;
        font-size: 0;
        text-align: center;
        height: 156px;
        padding: 8px 0;
        float: left;
    

        img {
          height: 100%;
          display: block;
          margin: auto;
          border-radius: 4px;
          -webkit-box-sizing: border-box;
          box-sizing: border-box;
        }
      }

      .info-view {
        background: #f6f9fb;
        text-align: center;
        height: 156px;
        overflow: hidden;
        padding: 0 16px;

        h3 {
          color: #1c355a;
          font-weight: 500;
          font-size: 16px;
          line-height: 20px;
          overflow: hidden;
          text-overflow: ellipsis;
          display: -webkit-box;
          -webkit-line-clamp: 2;
          -webkit-box-orient: vertical;
          margin: 12px 0 8px;
        }

        .authors {
          color: #6f6f6f;
          font-size: 12px;
          line-height: 14px;
          margin-top: 4px;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }

        .translators {
          color: #6f6f6f;
          font-size: 12px;
          line-height: 14px;
          margin-top: 4px;
          overflow: hidden;
          width: auto;
          height: auto;
          // white-space:normal;
          -webkit-line-clamp:3;
          text-overflow: ellipsis; 
          display: -webkit-box;
          -webkit-box-orient: vertical;
          -webkit-line-clamp: 3; 
       
        }

        .publisher {
          color: #6f6f6f;
          font-size: 12px;
          line-height: 14px;
          margin-top: 4px;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }
      }
    }

    .no-data {
      height: 200px;
      line-height: 200px;
      text-align: center;
      width: 100%;
      font-size: 16px;
      color: #152844;
    }
  }

</style>
