<template>
  <div class="detail">
    <Header/>

	<div class="container">
    <el-row>
      <el-col :span="24">
        <h1>2018–2024年中国10米空间分辨率冬小麦识别数据集</h1>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="8">
        <!-- <img src="E:\zh\code\zyxt\java_book\server\src\main\resources\static\1.png" alt="map image" class="image" /> -->
      </el-col>
      <el-col :span="16">
        <div class="info">
          <p>CSTR标识符: <a href="#">15732.11.nesdc.ecodb.rs.2023.016</a></p>
          <p>DOI标识符: <a href="#">10.12199/nesdc.ecodb.rs.2023.016</a></p>
          <div class="button-container">
            <el-button type="success" icon="el-icon-shopping-cart">加入资源车</el-button>
            <el-button type="primary" @click="downloadFile">在线下载</el-button>
            <el-button type="warning">收藏数据集</el-button>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="24">
        <h2>摘要</h2>
        <p>
          及时准确的冬小麦空间分布信息对于粮食安全和作物生产管理至关重要。
          由于训练数据的获取成本高、效率低、大尺度、高质量、高空间分辨率的冬小麦分类产品依然匮乏。
          因此，我们提出了一种融合冬小麦物候、光谱和农化信息的训练数据自动化提取方法（Automated training data generation, ATDG），
          用于生成高质量的冬小麦训练样本，从而开展基于机器学习方法的冬小麦遥感制图。
          此外，基于生成的训练数据预训练分类模型，然后结合模型年际迁移方法（Model transfer, MT）实现了冬小麦生长季内制图。
          通过联合ATDG和MT，结合光学和雷达影像，我们生产了2018–2024年10米空间分辨率的中国冬小麦制图数据集（ChinaWheat10）。
          实地调查数据表明ChinaWheat10的总体精度在94%以上，在省内两级与统计数据的相关性（R2）分别在0.95和0.91以上，
          而且ChinaWheat10数据集中冬小麦自动化制图中具有巨大潜力。
        </p>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="8">
        <div class="icon-text"><el-icon><Share/></el-icon>公开共享</div>
      </el-col>
      <el-col :span="8">
        <div class="icon-text"><el-icon><View/></el-icon>访问量：749</div>
      </el-col>
      <el-col :span="8">
        <div class="icon-text"><el-icon><Download/></el-icon>下载量：191</div>
      </el-col>
    </el-row>
  </div>

		<div>
			<!-- <div class="content-head"> -->
				<div class="content-title">
					<!-- 内容的详细说明 -->
					<!-- 内容标题  和按钮在一行'-->
					<!-- <div class="first-hang">
						<div class="title">
							<h1>数据信息标题</h1>
							<div class="first-second">
								<label>下载量: {{10000}}</label>
								<label class="label">浏览量: {{1000}}</label>
							</div>
							<div>
								<p>说明</p>
							</div>
							<div class="resource">
								<p>资源格式</p>
								<label for=""></label>
								<label for=""></label>
								<label for=""></label>
							</div>
						</div>
						四个按钮存在的位置
						 <div class="content-button-group">
							<el-button type="success" icon="el-icon-shopping-cart">加入资源车</el-button>
    <el-button type="primary">在线下载</el-button>
    <el-button type="warning">收藏数据集</el-button>
							
						</div>
					</div> -->
				</div>
			<!-- </div> -->
			<div class="content-body">
				<div class="navbar">
					<el-menu
					    :default-active="activeIndex"
					    class="el-menu-demo"
					    mode="horizontal"
					    @select="handleSelect"
					  >
					    <el-menu-item index="1">基本信息</el-menu-item>
					    <el-menu-item index="2">数据项</el-menu-item>
						<el-menu-item index="3">数据预览</el-menu-item>
						<el-menu-item index="4">数据下载</el-menu-item>
						<el-menu-item index="5">数据分析</el-menu-item>
						<el-menu-item index="6">关联推荐</el-menu-item>
						<el-menu-item index="7">资源评价</el-menu-item>
					  </el-menu>
					  <br />
					<!-- 数据项 -->
					<div v-if=" PageIndex == 1 ">
					     <el-table :data="resourceInfo" border style="width: 100%">
					        <el-table-column true prop="name" label="name" width="180" />
					        <el-table-column prop="application" label="application" width="180" />
					        <el-table-column prop="updateCycle" label="updateCycle" />
							<el-table-column prop="unit" label="unit" />
							<el-table-column prop="version" label="version" />
							<el-table-column prop="industry" label="industry" />
							<el-table-column prop="topic" label="topic" />
							<el-table-column prop="updateDate" label="updateDate" />
							<el-table-column prop="description" label="description" />
					      </el-table>
				   </div>
					<div v-if=" PageIndex == 2 ">
						<el-table :data="tableData" border style="width: 100%">
						    <el-table-column prop="date" label="Date" width="180" />
						    <el-table-column prop="name" label="Name" />
						    <el-table-column prop="address" label="Address" />
							<el-table-column prop="name" label="Name" width="" />
							<el-table-column prop="address" label="Address" />
						  </el-table>
					</div>
					<div v-if=" PageIndex == 3 ">
						<!-- 数据预览 -->
						<el-table :data="tableData" border style="width: 100%">
						    <el-table-column prop="date" label="Date" width="180" />
						    <el-table-column prop="name" label="Name" />
						    <el-table-column prop="address" label="Address" />
							<el-table-column prop="name" label="Name" width="" />
							<el-table-column prop="address" label="Address" />
						  </el-table>
					</div>
					<div v-if=" PageIndex == 4 ">
						<el-table :data="files" style="width: 100%">
						    <el-table-column type="selection" width="55"></el-table-column>
						    <el-table-column prop="format" label="文件格式" width="120">
						      <template slot-scope="scope">
						        <el-icon>
						          <img :src="getIcon(scope.row.format)" alt="icon" />
						        </el-icon>
						        {{ scope.row.format }}
						      </template>
						    </el-table-column>
						    <el-table-column prop="name" label="文件名称"></el-table-column>
						    <el-table-column prop="size" label="文件大小" width="120"></el-table-column>
						    <el-table-column prop="updateDate" label="更新时间" width="180"></el-table-column>
						  </el-table>
					</div>
					<div v-if=" PageIndex == 5 ">
						数据分析
					</div>
					<div v-if=" PageIndex == 6 ">
						关联推荐
					</div>
					<div v-if=" PageIndex == 7 ">
						资源评价
					</div>
				</div>
			</div>
		</div>
    <Footer/>
  </div>
</template>
<script setup>
	
	const PageIndex = ref('');
	
	const handleSelect = (index)=>{
		// 动态渲染页面--根据index
		PageIndex.value = index;
	}
	const files = ref([
	        { format: 'CSV', name: '药品生产许可证信息查询_20240622085343.csv', size: '274B', updateDate: '2024-06-22' },
	        { format: 'DOCX', name: '药品生产许可证信息查询_20240622085343.json', size: '18KB', updateDate: '2024-06-22' },
	        { format: 'XLS', name: '药品生产许可证信息查询_20240622085343.xls', size: '5KB', updateDate: '2024-06-22' },
	        { format: 'XLSX', name: '药品生产许可证信息查询_20240622085343.xlsx', size: '4KB', updateDate: '2024-06-22' },
	      ])
	const resourceInfo = ref( {
	        name: '药品生产许可证信息查询信息',
	        application: '政府办事',
	        openType: '无条件开放',
	        updateCycle: '实时',
	        unit: '江苏省药品监督管理局',
	        version: 'v3.0',
	        industry: '公共管理、社会保障和社会组织',
	        topic: '行业准营',
	        updateDate: '2024-06-22',
	        description: '药品生产许可证信息查询'
	      })

import {message} from "ant-design-vue";
import Header from '/@/views/index/components/header.vue'
import Footer from '/@/views/index/components/footer.vue'
import AddIcon from '/@/assets/images/add.svg';
import WantIcon from '/@/assets/images/want-read-hover.svg';
import RecommendIcon from '/@/assets/images/recommend-hover.svg';
import ShareIcon from '/@/assets/images/share-icon.svg';
import WeiboShareIcon from '/@/assets/images/wb-share.svg';
import AvatarIcon from '/@/assets/images/avatar.jpg';
import {
  detailApi,
  listApi as listThingList,
} from '/@/api/thing'
import {listThingCommentsApi, createApi as createCommentApi, likeApi} from '/@/api/comment'
import {createApi as createBorrowApi} from '/@/api/borrow'
import {wishApi} from '/@/api/thingWish'
import {collectApi} from '/@/api/thingCollect'
import {BASE_URL} from "/@/store/constants";
import {useRoute, useRouter} from "vue-router/dist/vue-router";
import {useUserStore} from "/@/store";
import {getFormatTime} from "/@/utils";

const router = useRouter()
const route = useRoute()
const userStore = useUserStore();


let thingId = ref('')
let detailData = ref({})
let tabUnderLeft = ref(6)
let tabData = ref(['简介', '评论'])
let selectTabIndex = ref(0)

let commentData = ref([])
let recommendData = ref([])
let sortIndex = ref(0)
let order = ref('recent') // 默认排序最新

let commentRef = ref()

onMounted(()=>{
  thingId.value = route.query.id.trim()
  getThingDetail()
  getRecommendThing()
  getCommentList()
})

const selectTab =(index)=> {
  selectTabIndex.value = index
  tabUnderLeft.value = 6 + 54 * index
}

const getThingDetail =()=> {
  detailApi({id: thingId.value}).then(res => {
    detailData.value = res.data
    detailData.value.cover = BASE_URL + '/api/staticfiles/image/' + detailData.value.cover
  }).catch(err => {
    message.error('获取详情失败')
  })
}
const addToWish =()=> {
  let userId = userStore.user_id
  if (userId) {
    wishApi({thingId: thingId.value, userId: userId}).then(res => {
      message.success(res.msg)
      getThingDetail()
    }).catch(err => {
      console.log('操作失败')
    })
  } else {
    message.warn('请先登录')
  }
}
const collect =()=> {
  let userId = userStore.user_id
  if (userId) {
    collectApi({thingId: thingId.value, userId: userId}).then(res => {
      message.success(res.msg)
      getThingDetail()
    }).catch(err => {
      console.log('收藏失败')
    })
  } else {
    message.warn('请先登录')
  }
}
const share =()=> {
  let content = '分享一个非常好玩的网站 ' + window.location.href
  let shareHref = 'http://service.weibo.com/share/share.php?title=' + content
  window.open(shareHref)
}
const handleBorrow =(detailData)=> {
  console.log(detailData)
  const userId = userStore.user_id
  createBorrowApi({
    thingId: detailData.id,
    userId: userId
  }).then(res => {
    message.success("借阅成功")
    getThingDetail()
  }).catch(err => {
    message.error(err.msg || '操作失败s')
  })
}
const getRecommendThing =()=> {
  listThingList({sort: 'recommend'}).then(res => {
    res.data.forEach((item, index) => {
      if (item.cover) {
        item.cover = BASE_URL + '/api/staticfiles/image/' + item.cover
      }
    })
    console.log(res)
    recommendData.value = res.data.slice(0, 6)
  }).catch(err => {
    console.log(err)
  })
}
const handleDetail =(item)=> {
  // 跳转新页面
  let text = router.resolve({name: 'detail', query: {id: item.id}})
  window.open(text.href, '_blank')
}
const sendComment =()=> {
  console.log(commentRef.value)
  let text = commentRef.value.value.trim()
  console.log(text)
  if (text.length <= 0) {
    return
  }
  commentRef.value.value = ''
  let userId = userStore.user_id
  if (userId) {
    createCommentApi({content: text, thingId: thingId.value, userId: userId}).then(res => {
      getCommentList()
    }).catch(err => {
      console.log(err)
    })
  } else {
    message.warn('请先登录！')
    router.push({name: 'login'})
  }
}
const like =(commentId)=> {
  likeApi({id: commentId}).then(res => {
    getCommentList()
  }).catch(err => {
    console.log(err)
  })
}
const getCommentList =()=> {
  listThingCommentsApi({thingId: thingId.value, order: order.value}).then(res => {
    res.data.forEach(item => {
      item.commentTime = getFormatTime(item.commentTime, true)
    })
    commentData.value = res.data
  }).catch(err => {
    console.log(err)
  })
}
const sortCommentList =(sortType)=> {
  if (sortType === 'recent') {
    sortIndex.value = 0
  } else {
    sortIndex.value = 1
  }
  order.value = sortType
  getCommentList()
}
const downloadFile=()=> {
	let userId = userStore.user_id
  if (userId) {
    router.push({ name: 'order' });
	console.log('下载文件')
  } else {
    message.warn('请先登录！')
    router.push({name: 'login'})
  }

    }

</script>
<style scoped lang="less">
// .hide {
//   display: none;
// }

// .detail-content {
//   display: flex;
//   flex-direction: column;
//   width: 1100px;
//   margin: 4px auto;
// }

// .flex-view {
//   display: -webkit-box;
//   display: -ms-flexbox;
//   display: flex;
// }

// .hidden-lg {
//   display: none !important;
// }

// .thing-infos-view {
//   display: flex;
//   margin: 89px 0 40px;
//   overflow: hidden;

//   .thing-infos {
//     -webkit-box-flex: 1;
//     -ms-flex: 1;
//     flex: 1;
//     display: flex;
//   }

//   .mobile-share-box {
//     height: 38px;
//     background: transparent;
//     padding: 0 16px;
//     margin: 12px 0;
//     font-size: 0;
//     -webkit-box-align: center;
//     -ms-flex-align: center;
//     align-items: center;
//     -webkit-box-pack: justify;
//     -ms-flex-pack: justify;
//     justify-content: space-between;

//     .state {
//       width: 64px;
//       height: 24px;
//       line-height: 24px;
//       background: rgba(70, 132, 226, .1);
//       border-radius: 2px;
//       font-weight: 500;
//       font-size: 12px;
//       color: #4684e2;
//       text-align: center;
//     }

//     .share-img {
//       background: #fff;
//       width: 38px;
//       height: 38px;
//       border-radius: 50%;
//       text-align: center;

//       img {
//         position: relative;
//         top: 4px;
//         width: 24px;
//       }
//     }
//   }

//   .thing-img-box {
//     -webkit-box-flex: 0;
//     -ms-flex: 0 0 235px;
//     flex: 0 0 235px;
//     margin: 0 40px 0 0;

//     img {
//       width: 200px;
//       height: 186px;
//       display: block;
//     }
//   }

//   .thing-info-box {
//     text-align: left;
//     padding: 0;
//     margin: 0;
//   }

//   .thing-state {
//     height: 26px;
//     line-height: 26px;

//     .state {
//       font-weight: 500;
//       color: #4684e2;
//       background: rgba(70, 132, 226, .1);
//       border-radius: 2px;
//       padding: 5px 8px;
//       margin-right: 16px;
//     }

//     span {
//       font-size: 14px;
//       color: #152844;
//     }
//   }

//   .thing-name {
//     line-height: 32px;
//     margin: 16px 0;
//     color: #0F1111!important;
//     font-size: 24px!important;
//     font-weight: 400!important;
//     font-style: normal!important;
//     text-transform: none!important;
//     text-decoration: none!important;
//   }

//   .translators, .authors {
//     line-height: 18px;
//     font-size: 14px;
//     margin: 8px 0;
//     -webkit-box-align: start;
//     -ms-flex-align: start;
//     align-items: flex-start;
//     -webkit-box-pack: start;
//     -ms-flex-pack: start;
//     justify-content: flex-start;

//     .name {
//       color: #315c9e;
//       white-space: normal;
//     }
//   }

//   .tags {
//     position: absolute;
//     bottom: 20px;
//     margin-top: 16px;

//     .category-box {
//       color: #152844;
//       font-size: 14px;

//       .title {
//         color: #787878;
//       }
//     }
//   }

//   .thing-counts {
//     -webkit-box-flex: 0;
//     -ms-flex: 0 0 235px;
//     flex: 0 0 235px;
//     margin-left: 20px;
//   }

//   .pointer {
//     cursor: pointer;
//   }

//   .count-item {
//     height: 64px;
//     -webkit-box-align: center;
//     -ms-flex-align: center;
//     align-items: center;
//     cursor: pointer;
//   }

//   .count-img {
//     -webkit-box-flex: 0;
//     -ms-flex: 0 0 32px;
//     flex: 0 0 32px;
//     margin-right: 24px;
//     font-size: 0;

//     img {
//       width: 100%;
//       display: block;
//     }
//   }

//   .count-box {
//     position: relative;
//     border-bottom: 1px solid #cedce4;
//     -webkit-box-align: center;
//     -ms-flex-align: center;
//     align-items: center;
//     -webkit-box-pack: justify;
//     -ms-flex-pack: justify;
//     justify-content: space-between;
//     -webkit-box-flex: 1;
//     -ms-flex: 1;
//     flex: 1;
//     height: 100%;
//   }

//   .count-text-box {
//     font-size: 0;

//     .count-title {
//       color: #152844;
//       font-weight: 600;
//       font-size: 16px;
//       line-height: 18px;
//       display: block;
//       height: 18px;
//     }
//   }

//   .count-num-box {
//     font-weight: 600;
//     font-size: 20px;
//     line-height: 24px;
//     color: #152844;
//   }
// }

// .buy-btn {
//   cursor: pointer;
//   display: block;
//   background: #4684e2;
//   border-radius: 4px;
//   text-align: center;
//   color: #fff;
//   font-size: 14px;
//   height: 36px;
//   line-height: 36px;
//   width: 110px;
//   outline: none;
//   border: none;
//   margin-top: 18px;
// }

// .buy-btn img {
//   width: 12px;
//   margin-right: 2px;
//   vertical-align: middle;
// }

// .buy-btn span {
//   vertical-align: middle;
// }


// .buy-way {
//   overflow: hidden;

//   .title {
//     font-weight: 600;
//     font-size: 18px;
//     height: 26px;
//     line-height: 26px;
//     color: #152844;
//     margin-bottom: 12px;
//   }

//   .buy-way-item {
//     background: #fbfeff;
//     border: 1px solid #cedce4;
//     border-radius: 4px;
//     -webkit-box-flex: 0;
//     margin-right: 20px;
//     -ms-flex: 0 0 255px;
//     flex: 0 0 255px;
//     padding: 10px;

//     .name {
//       font-weight: 500;
//       font-size: 16px;
//       line-height: 24px;
//       color: #152844;
//       height: 24px;
//       margin-bottom: 12px;
//     }

//     .price {
//       position: relative;
//       line-height: 24px;
//     }

//     .price-text {
//       color: #ff7b31;
//       font-size: 18px;
//       font-weight: 700;
//     }

//     .buy-btn {
//       cursor: pointer;
//       display: block;
//       background: #4684e2;
//       border-radius: 4px;
//       text-align: center;
//       color: #fff;
//       font-size: 14px;
//       height: 32px;
//       line-height: 32px;
//       width: 76px;
//       outline: none;
//       border: none;
//       margin-top: 12px;
//     }

//     .buy-btn img {
//       width: 12px;
//       margin-right: 2px;
//       vertical-align: middle;
//     }

//     .buy-btn span {
//       vertical-align: middle;
//     }
//   }
// }


// .thing-content-view {
//   margin-top: 40px;
//   padding-bottom: 50px;
// }

// .main-content {
//   -webkit-box-flex: 1;
//   -ms-flex: 1;
//   flex: 1;

//   .text {
//     color: #484848;
//     font-size: 16px;
//     line-height: 26px;
//     padding-left: 12px;
//     margin: 11px 0;
//     white-space: pre-wrap;
//   }
// }

// .main-tab {
//   border-bottom: 1px solid #cedce4;
// }

// .order-view {
//   position: relative;
//   color: #6c6c6c;
//   font-size: 14px;
//   line-height: 40px;

//   .title {
//     margin-right: 8px;
//   }

//   .tab {
//     margin-right: 20px;
//     cursor: pointer;
//     color: #5f77a6;
//     font-size: 16px;
//     cursor: pointer;
//   }

//   .tab-select {
//     color: #152844;
//     font-weight: 600;
//   }

//   .tab-underline {
//     position: absolute;
//     bottom: 0;
//     left: 84px;
//     width: 16px;
//     height: 4px;
//     background: #4684e2;
//     -webkit-transition: left .3s;
//     transition: left .3s;
//   }
// }

// .recommend {
//   -webkit-box-flex: 0;
//   -ms-flex: 0 0 235px;
//   flex: 0 0 235px;
//   margin-left: 20px;

//   .title {
//     font-weight: 600;
//     font-size: 18px;
//     line-height: 26px;
//     color: #152844;
//     margin-bottom: 12px;
//   }

//   .things {
//     border-top: 1px solid #cedce4;

//     .thing-item {
//       min-width: 255px;
//       max-width: 255px;
//       position: relative;
//       flex: 1;
//       margin-right: 20px;
//       height: fit-content;
//       overflow: hidden;
//       margin-top: 26px;
//       margin-bottom: 36px;
//       padding-bottom: 24px;
//       border-bottom: 1px #e1e1e1 solid;
//       cursor: pointer;

//       .img-view {
//         //background: #f3f3f3;
//         //text-align: center;
//         height: 200px;
//         width: 255px;
//         //border: 1px #f3f3f3 solid;

//         img {
//           height: 200px;
//           width: 186px;
//           overflow: hidden;
//           margin: 0 auto;
//           background-size: contain;
//           object-fit: contain;
//         }
//       }

//       .info-view {
//         //background: #f6f9fb;
//         overflow: hidden;
//         padding: 0 16px;
//         .thing-name {
//           line-height: 32px;
//           margin-top: 12px;
//           color: #0F1111!important;
//           font-size: 15px!important;
//           font-weight: 400!important;
//           font-style: normal!important;
//           text-transform: none!important;
//           text-decoration: none!important;
//         }

//         .price {
//           color: #ff7b31;
//           font-size: 20px;
//           line-height: 20px;
//           margin-top: 4px;
//           overflow: hidden;
//           text-overflow: ellipsis;
//           white-space: nowrap;
//         }

//         .translators {
//           color: #6f6f6f;
//           font-size: 12px;
//           line-height: 14px;
//           margin-top: 4px;
//           overflow: hidden;
//           text-overflow: ellipsis;
//           white-space: nowrap;
//         }
//       }
//     }

//   }
// }

// .flex-view {
//   display: flex;
// }

// .thing-comment {
//   .title {
//     font-weight: 600;
//     font-size: 14px;
//     line-height: 22px;
//     height: 22px;
//     color: #152844;
//     margin: 24px 0 12px;
//   }

//   .publish {
//     -webkit-box-align: center;
//     -ms-flex-align: center;
//     align-items: center;

//     .mine-img {
//       -webkit-box-flex: 0;
//       -ms-flex: 0 0 40px;
//       flex: 0 0 40px;
//       margin-right: 12px;
//       border-radius: 50%;
//       width: 40px;
//       height: 40px;
//     }

//     .content-input {
//       -webkit-box-flex: 1;
//       -ms-flex: 1;
//       flex: 1;
//       background: #f6f9fb;
//       border-radius: 4px;
//       height: 32px;
//       line-height: 32px;
//       color: #484848;
//       padding: 5px 12px;
//       white-space: nowrap;
//       outline: none;
//       border: 0px;
//     }

//     .send-btn {
//       margin-left: 10px;
//       background: #4684e2;
//       border-radius: 4px;
//       -webkit-box-flex: 0;
//       -ms-flex: 0 0 80px;
//       flex: 0 0 80px;
//       color: #fff;
//       font-size: 14px;
//       text-align: center;
//       height: 32px;
//       line-height: 32px;
//       outline: none;
//       border: 0px;
//       cursor: pointer;
//     }
//   }

//   .tab-view {
//     -webkit-box-pack: justify;
//     -ms-flex-pack: justify;
//     justify-content: space-between;
//     font-size: 14px;
//     -webkit-box-align: center;
//     -ms-flex-align: center;
//     align-items: center;
//     margin: 24px 0;

//     .count-text {
//       color: #484848;
//       float: left;
//     }

//     .tab-box {
//       color: #5f77a6;
//       -webkit-box-align: center;
//       -ms-flex-align: center;
//       align-items: center;

//       .tab-select {
//         color: #152844;
//       }

//       span {
//         cursor: pointer;
//       }
//     }

//     .line {
//       width: 1px;
//       height: 12px;
//       margin: 0 12px;
//       background: #cedce4;
//     }
//   }
// }


// .comments-list {
//   .comment-item {
//     .flex-item {
//       -webkit-box-align: center;
//       -ms-flex-align: center;
//       align-items: center;
//       padding-top: 16px;

//       .avator {
//         -webkit-box-flex: 0;
//         -ms-flex: 0 0 40px;
//         flex: 0 0 40px;
//         width: 40px;
//         height: 40px;
//         margin-right: 12px;
//         border-radius: 50%;
//         cursor: pointer;
//       }

//       .person {
//         -webkit-box-flex: 1;
//         -ms-flex: 1;
//         flex: 1;
//       }

//       .name {
//         color: #152844;
//         font-weight: 600;
//         font-size: 14px;
//         line-height: 22px;
//         height: 22px;
//         cursor: pointer;
//       }

//       .time {
//         color: #5f77a6;
//         font-size: 12px;
//         line-height: 16px;
//         height: 16px;
//         margin-top: 2px;
//       }

//       .float-right {
//         color: #4684e2;
//         font-size: 14px;
//         float: right;

//         span {
//           margin-left: 19px;
//           cursor: pointer;
//         }

//         .num {
//           color: #152844;
//           margin-left: 6px;
//           cursor: auto;
//         }
//       }
//     }
//   }
// }

// .comment-content {
//   margin-top: 8px;
//   color: #484848;
//   font-size: 14px;
//   line-height: 22px;
//   padding-bottom: 16px;
//   border-bottom: 1px dashed #cedce4;
//   margin-left: 52px;
//   overflow: hidden;
//   word-break: break-word;
// }

// .infinite-loading-container {
//   clear: both;
//   text-align: center;
// }

// .a-price-symbol {
//   top: -0.5em;
//   font-size: 12px;
// }
// .a-price {
//   color: #0F1111;
//   font-size:21px;
// }
.detail {
    div {
        .content-head {
		  margin-top:20px;
          margin-left: auto;
          height: 200px;
          display:flex;
		  background-color: #ffffff;
          justify-content: flex-start;
          box-shadow: 0px 0px 0px #ffffff;
          border-style: none;
          border-bottom-style: none;
            .content-title {
                .first-hang {
					height: 300px;
					display:flex;
					justify-content:;
                    .title {
                        h1 {
							// 水平
							float: left; /* 元素靠右对齐 */
							// 水平摆放
							display: flex; /* 元素靠右对齐 */
                            justify-content: flex-start; /* 元素靠右对齐 */
                        }
                        .first-second {
                            label {
                            }
                            .label {
								margin-left: 10px;
                            }
                        }
                        div {
                            p {
                            }
                        }
                        div {
                            label {
								
                            }
                        }
                    }
                    .content-button-group {
						margin-top: 5px;
						display:flex;  
						margin-left: auto;
						margin-right:auto;
						position: relative;
						// 放置在左边
                        .content-button-item {
                            width: 100px; /* 设置按钮宽度 */
                            height: 40px; /* 设置按钮高度 */
						    background-color: #ffffff;
							font-size: 16px;
							text-align: center;
							margin-right: 2px; /* 调整间距 */
							cursor: pointer; /* 鼠标悬停时显示为手型 */
							&:hover { /* 鼠标悬停时的样式 */
							    color: #ffffff; /* 鼠标悬停时的文字颜色 */
								background-color: #55ffff;
							}
                        }
                        .content-button-item {
                            button {
                            }
                        }
                        .content-button-item {
                            button {
                            }
                        }
                        .content-button-item {
                            button {
                            }
                        }
                        .content-button-item {
                            button {
                            }
                        }
                      
                    }
                }
            }     
        }
       
    }
}


</style>
