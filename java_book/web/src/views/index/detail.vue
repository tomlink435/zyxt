<template>
  <div class="detail">
    <Header/>

	<div class="container">
    <el-row>
      <el-col :span="24">
        <h1 style="text-align: center; font-size: 24px; margin-top: 20px">{{ detailData.title }}</h1>
      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="8">
        <img :src="detailData.cover" style="width: 70%;padding-left: 10%;"/>
        <!-- <img src="E:\zh\code\zyxt\java_book\server\src\main\resources\static\1.png" alt="map image" class="image" /> -->
      </el-col>
      <el-col :span="16">
        <div class="info">
          <p>CSTR标识符: <a href="#">15732.11.nesdc.ecodb.rs.2023.016</a></p>
          <p>DOI标识符: <a href="#">10.12199/nesdc.ecodb.rs.2023.016</a></p>
          <div class="button-container">
            <el-button type="success" @click="addToCart()">加入资源车</el-button>
            <el-button type="primary" @click="downloadFile">在线下载</el-button>
            <el-button type="warning">收藏数据集</el-button>
          </div>
        </div>
      </el-col>
    </el-row>

    <el-row>
      <el-col :span="24">
        <h2 style="text-align: center; font-size: 24px; margin-top: 20px;">摘要</h2>
        <p style="padding:30px; text-indent: 2em; line-height: 1.5em; font-size: 16px; margin-bottom: 20px">
          <!-- 及时准确的冬小麦空间分布信息对于粮食安全和作物生产管理至关重要。
          由于训练数据的获取成本高、效率低、大尺度、高质量、高空间分辨率的冬小麦分类产品依然匮乏。
          因此，我们提出了一种融合冬小麦物候、光谱和农化信息的训练数据自动化提取方法（Automated training data generation, ATDG），
          用于生成高质量的冬小麦训练样本，从而开展基于机器学习方法的冬小麦遥感制图。
          此外，基于生成的训练数据预训练分类模型，然后结合模型年际迁移方法（Model transfer, MT）实现了冬小麦生长季内制图。
          通过联合ATDG和MT，结合光学和雷达影像，我们生产了2018–2024年10米空间分辨率的中国冬小麦制图数据集（ChinaWheat10）。
          实地调查数据表明ChinaWheat10的总体精度在94%以上，在省内两级与统计数据的相关性（R2）分别在0.95和0.91以上，
          而且ChinaWheat10数据集中冬小麦自动化制图中具有巨大潜力。 -->
          {{detailData.description}}
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
					<div v-if=" PageIndex.value == 1 ">
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
        <div style="width: auto;">摘要：中国作为陆地生态系统的重要组成部分，其碳收支情况将会对全球碳循环
产生深刻影响。本文通过收集整理期刊文献中的碳密度数据，同时结合课题组相
关实验测试数据，构建了一套全面系统的植被和土壤碳密度数据集。本数据集覆
盖了森林、草地、农田、湿地和灌丛等主要生态系统类型，包含了植被地上碳密
度、植被地下碳密度和不同深度（0–20 cm 和 0–100 cm）土壤有机碳密度。2010s
中国陆地生态系统碳密度数据集的建立和共享，为区域植被生物量和生态系统碳
储量评估，生态系统质量评估，以及模型优化提供了重要的基础数据。
关键词：中国；陆地生态系统；碳密度</div>

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
        const item = reactive({
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
import {useUserStore} from "/@/store";
import {getFormatTime} from "/@/utils";

const userStore = useUserStore()
const router = useRouter();
const route = useRoute();


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
    console.log('-----',detailData.value.id)
    // debugger
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
  if(detailData.value.id%2==1){
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
    const addToCart=()=>{
  router.push({name: 'myCart'})
}
</script>
<style scoped lang="less">
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
