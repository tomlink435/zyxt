<template>
   <div class="search-entry" style="margin-top: 5%;">
      <img :src="SearchIcon" class="search-icon">
      <input placeholder="输入关键词" ref="keywordRef" @keyup.enter="search" />
    </div>
  <div class="content">
    <div class="content-left">
      <div class="left-search-item">
        <h4>数据分类</h4>
        <a-tree
          :tree-data="contentData.cData" :selected-keys="contentData.selectedKeys" @select="onSelect"
          style="min-height: 220px;" />
      </div>
      
    </div>
    <div class="content-right">
      <div class="top-select-view flex-view">
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
      </div>
      <a-spin :spinning="contentData.loading" style="min-height: 200px;">

        <div class="pc-book-list flex-view">
          <div v-for="item in contentData.pageData" :key="item.id"  class="book-item item-column-3"><!---->
            <div class="img-view">
                <img :src="item.cover"/>
            </div>
            <div class="info-view">
            
              <h3 class="book-name" @click="handleDetail(item)">{{item.title}}   <span :class="{'public': item.id % 2 === 0, 'private': item.id % 2 !== 0}">
                {{ item.id % 2 === 0 ? '公开' : '非公开' }}
              </span>
            </h3>
              
              <p class="authors"> {{item.author}} </p>
              <p class="translators" v-if="item.description"> {{item.description}} </p>
              <div class="button-container">
            <el-button type="success"  @click="addToCart(item)">加入资源车</el-button>
            <el-button type="primary" @click="downloadFile(item)">在线下载</el-button>
            <el-button type="warning">收藏数据集 </el-button>
          </div></div>
              
          </div>
          <div v-if="contentData.pageData.length <= 0 && !contentData.loading" class="no-data" style="">暂无数据</div>
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
import {listApi as listClassificationList} from '/@/api/classification'
import {listApi as listTagList} from '/@/api/tag'
import {listApi as listThingList} from '/@/api/thing'
import {BASE_URL} from "/@/store/constants";
import SearchIcon from '/@/assets/images/search-icon.svg';
import {useUserStore} from "/@/store";
const keywordRef = ref()
const userStore = useUserStore()
const router = useRouter();
const route = useRoute();
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

onMounted(() => {
  initSider()
  getThingList({})
})
const search = () => {
  const keyword = keywordRef.value.value
  if (route.name === 'search') {
    router.push({name: 'search', query: {keyword: keyword}})
  } else {
    let text = router.resolve({name: 'search', query: {keyword: keyword}})
    window.open(text.href, '_blank')
  }
}
const initSider = () => {
  contentData.cData.push({key:'-1', title:'全部'})
  listClassificationList().then(res => {
    res.data.forEach(item=>{
      item.key = item.id
      contentData.cData.push(item)
    })
  })
  listTagList().then(res => {
    contentData.tagData = res.data
  })
}

const getSelectedKey = () => {
  if (contentData.selectedKeys.length > 0) {
    return contentData.selectedKeys[0]
  } else {
    return -1
  }
}
const onSelect = (selectedKeys) => {
  contentData.selectedKeys = selectedKeys
  console.log(contentData.selectedKeys[0])
  if (contentData.selectedKeys.length > 0) {
    getThingList({c: getSelectedKey()})
  }
  contentData.selectTagId = -1
}
const clickTag = (index) => {
  contentData.selectedKeys = []
  contentData.selectTagId = index
  getThingList({tag: contentData.selectTagId})
}

// 最新|最热|推荐
const selectTab = (index) => {

  contentData.selectTabIndex = index
  contentData.tabUnderLeft = 12 + 50 * index
  
  let sort = (index === 0 ? 'recent' : index === 1 ? 'hot' : 'recommend')
  const data = {sort: sort}
  
  if (contentData.selectTagId !== -1) {
    data['tag'] = contentData.selectTagId
  } else {
    data['c'] = getSelectedKey()
  }
  getThingList(data)
}
const handleDetail = (item) => {
  // 跳转新页面
  let text = router.resolve({name: 'detail', query: {id: item.id}})
  // let text = router.resolve({name: 'detail', query: {item: JSON.stringify(item)}})
  window.open(text.href, '_blank')
  console.log('--------',text.href)
}
// 分页事件
const changePage = (page) => {
  contentData.page = page
  let start = (contentData.page - 1) * contentData.pageSize
  contentData.pageData = contentData.thingData.slice(start, start + contentData.pageSize)
  console.log('第' + contentData.page + '页')
}
const getThingList = (data) => {
  contentData.loading = true
  listThingList(data).then(res => {
    contentData.loading = false
    res.data.forEach((item, index) => {
      if (item.cover) {
        // item.cover = BASE_URL + '/api/staticfiles/image/' +  item.cover
        item.cover = item.cover
      
        console.log('item.cover:'+item.cover)
     
      }
    })
    console.log(res)
    contentData.thingData = res.data
    contentData.total = contentData.thingData.length
    changePage(1)
  }).catch(err => {
    console.log(err)
    contentData.loading = false
  })
}
const addToCart=()=>{
  router.push({name: 'cart'})
}

const downloadFile=(item)=> {
  console.log(item)
  // debugger
  //非公开
  if(item.id%2==1){
    let userId = userStore.user_id
  if (userId) {
    router.push({name: 'order',query:{form:JSON.stringify(item)}});
    // router.push({name: 'login'})

    // router.push({ name: 'order',query: {thingId: thingId.value} });
	console.log('下载文件')
  } 
  else {
    alert('请登录后下载！')
    router.push({name: 'login'})
  }
    }
    else{
      let userId = userStore.user_id
      if(userId){
        window.open('https://gitee.com/WananRd/TrainTicketSystem/raw/master/%E3%80%90%E6%95%B0%E6%8D%AE%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3%E3%80%912018-2024%E5%B9%B4%E4%B8%AD%E5%9B%BD10%E7%B1%B3%E7%A9%BA%E9%97%B4%E5%88%86%E8%BE%A8%E7%8E%87%E5%86%AC%E5%B0%8F%E9%BA%A6%E8%AF%86%E5%88%AB%E6%95%B0%E6%8D%AE%E9%9B%86.xls')

      }
      else{
        alert('请登录后下载！')
    router.push({name: 'login'})
      }
      // window.open("src/views/index/resources/【数据说明文档】2010s中国陆地生态系统碳密度数据集.xls")
      // window.open('https://gitee.com/WananRd/TrainTicketSystem/raw/master/%E3%80%90%E6%95%B0%E6%8D%AE%E8%AF%B4%E6%98%8E%E6%96%87%E6%A1%A3%E3%80%912018-2024%E5%B9%B4%E4%B8%AD%E 5%9B%BD10%E7%B1%B3%E7%A9%BA%E9%97%B4%E5%88%86%E8%BE%A8%E7%8E%87%E5%86%AC%E5%B0%8F%E9%BA%A6%E8%AF%86%E5%88%AB%E6%95%B0%E6%8D%AE%E9%9B%86.xl','_blank')
    }
	

    }
</script>

<style scoped lang="less">
.search-entry {
  margin-top: 10px;
  Margin: auto;
  position: relative;
  width: 400px;
  min-width: 200px;
  height: 32px;
  background: #ecf3fc;
  padding: 0px 12px;
  border-radius: 16px;
  font-size: 0;
  cursor: pointer;

  img {
    max-width: 100%;
    height: auto;
  }
  .search-icon {
    width: 18px;
    margin: 7px 8px 0 0;
  }
  input {
    position: absolute;
    top:4px;
    width: 85%;
    height: 24px;
    border: 0px;
    outline:none;
    color: #000;
    background: #ecf3fc;
    font-size: 14px;
  }}
.content {
  display: flex;
  flex-direction: row;
  width: 1100px;
  margin: 80px auto;
}

.content-left {
  width: 220px;
  margin-right: 32px;
}

.left-search-item {
  overflow: hidden;
  border-bottom: 1px solid #cedce4;
  margin-top: 24px;
  padding-bottom: 24px;
}

h4 {
  color: #4d4d4d;
  font-weight: 600;
  font-size: 16px;
  line-height: 24px;
  height: 24px;
}

.category-item {
  cursor: pointer;
  color: #333;
  margin: 12px 0 0;
  padding-left: 16px;
}

ul {
  margin: 0;
  padding: 0;
}

ul {
  list-style-type: none;
}

li {
  margin: 4px 0 0;
  display: list-item;
  text-align: -webkit-match-parent;
}

.child {
  color: #333;
  padding-left: 16px;
}

.child:hover {
  color: #4684e2;
}

.select {
  color: #4684e2;
}

.flex-view {
  -webkit-box-pack: justify;
  -ms-flex-pack: justify;
  //justify-content: space-between;
  display: flex;
}

.name {
  font-size: 14px;
}

.name:hover {
  color: #4684e2;
}

.count {
  font-size: 14px;
  color: #999;
}

.check-item {
  font-size: 0;
  height: 18px;
  line-height: 12px;
  margin: 12px 0 0;
  color: #333;
  cursor: pointer;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
}

.check-item input {
  cursor: pointer;
}

.check-item label {
  font-size: 14px;
  margin-left: 12px;
  cursor: pointer;
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
}

.tag-view {
  -ms-flex-wrap: wrap;
  flex-wrap: wrap;
  margin-top: 4px;
}

.tag-flex-view {
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
}

.tag {
  background: #fff;
  border: 1px solid #a1adc6;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  border-radius: 16px;
  height: 20px;
  line-height: 18px;
  padding: 0 8px;
  margin: 8px 8px 0 0;
  cursor: pointer;
  font-size: 12px;
  color: #152833;
}

.tag:hover {
  background: #4684e3;
  color: #fff;
  border: 1px solid #4684e3;
}

.tag-select {
  background: #4684e3;
  color: #fff;
  border: 1px solid #4684e3;
}

.content-right {
  -webkit-box-flex: 1;
  -ms-flex: 1;
  flex: 1;
  padding-top: 12px;

  .pc-search-view {
    margin: 0 0 24px;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;

    .search-icon {
      width: 20px;
      height: 20px;
      -webkit-box-flex: 0;
      -ms-flex: 0 0 20px;
      flex: 0 0 20px;
      margin-right: 16px;
    }

    input {
      outline: none;
      border: 0px;
      -webkit-box-flex: 1;
      -ms-flex: 1;
      flex: 1;
      border-bottom: 1px solid #cedce4;
      color: #152844;
      font-size: 14px;
      height: 22px;
      line-height: 22px;
      -ms-flex-item-align: end;
      align-self: flex-end;
      padding-bottom: 8px;
    }

    .clear-search-icon {
      position: relative;
      left: -20px;
      cursor: pointer;
    }

    button {
      outline: none;
      border: none;
      font-size: 14px;
      color: #fff;
      background: #288dda;
      border-radius: 32px;
      width: 88px;
      height: 32px;
      line-height: 32px;
      margin-left: 2px;
      cursor: pointer;
    }

    .float-count {
      color: #999;
      margin-left: 24px;
    }
  }

  .flex-view {
    display: flex;
    flex-direction: column;
  }

  .top-select-view {
    -webkit-box-pack: justify;
    -ms-flex-pack: justify;
    justify-content: space-between;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    height: 40px;
    line-height: 40px;

    .type-view {
      position: relative;
      font-weight: 400;
      font-size: 18px;
      color: #5f77a6;

      .type-tab {
        margin-right: 32px;
        cursor: pointer;
      }

      .type-tab-select {
        color: #152844;
        font-weight: 600;
        font-size: 20px;
      }

      .tab-underline {
        position: absolute;
        bottom: 0;
        //left: 22px;
        width: 16px;
        height: 4px;
        background: #4684e2;
        -webkit-transition: left .3s;
        transition: left .3s;
      }
    }

    .order-view {
      position: relative;
      color: #6c6c6c;
      font-size: 14px;

      .title {
        margin-right: 8px;
      }

      .tab {
        color: #999;
        margin-right: 20px;
        cursor: pointer;
      }

      .tab-select {
        color: #152844;
      }

      .tab-underline {
        position: absolute;
        bottom: 0;
        left: 84px;
        width: 16px;
        height: 4px;
        background: #4684e2;
        -webkit-transition: left .3s;
        transition: left .3s;
      }
    }

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


  .pc-thing-list {
    -ms-flex-wrap: wrap;
    flex-wrap: wrap;

    .thing-item {
      min-width: 255px;
      max-width: 255px;
      position: relative;
      flex: 1;
      margin-right: 20px;
      height: fit-content;
      overflow: hidden;
      margin-top: 26px;
      margin-bottom: 36px;
      cursor: pointer;

      .img-view {
        //text-align: center;
        height: 200px;
        width: 255px;

        img {
          height: 200px;
          width: 186px;
          margin: 0 auto;
          background-size: contain;
          object-fit: contain;
        }
      }

      .info-view {
        //background: #f6f9fb;
        overflow: hidden;
        padding: 0 16px;

        .thing-name {
          line-height: 32px;
          margin-top: 12px;
          color: #0F1111 !important;
          font-size: 15px !important;
          font-weight: 400 !important;
          font-style: normal !important;
          text-transform: none !important;
          text-decoration: none !important;
        }

        .price {
          color: #ff7b31;
          font-size: 20px;
          line-height: 20px;
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

  .page-view {
    width: 100%;
    text-align: center;
    margin-top: 48px;
  }
}

.a-price-symbol {
  top: -0.5em;
  font-size: 12px;
}

.a-price {
  color: #0F1111;
  font-size: 21px;
}
.public {
  background-color: rgb(35, 233, 68);
}

.private {
  background-color: rgb(223, 148, 151);
  color: black;
}
</style>
