<template>
  <page-header-wrapper style="padding-top: 30px">
    <template v-slot:content>
      <div class="page-header-content">
        <div class="avatar">
          <a-avatar size="large" :src="currentUser.avatar" />
        </div>
        <div class="content">
          <div class="content-title">
            {{ timeFix }}，{{ user.name
            }}<span class="welcome-text">，{{ '欢迎' }}</span>
          </div>
          <div>前端工程师 | 蚂蚁金服 - 某某某事业群 - VUE平台</div>
        </div>
      </div>
    </template>
    <template v-slot:extraContent>
      <div class="extra-content">
        <div class="stat-item">
          <a-statistic title="项目数" :value="56" />
        </div>
        <div class="stat-item">
          <a-statistic title="团队内排名" :value="8" suffix="/ 24" />
        </div>
        <div class="stat-item">
          <a-statistic title="项目访问" :value="2223" />
        </div>
      </div>
    </template>

    <!-- <div>
      <a-row :gutter="24">
        <a-col :xl="16" :lg="24" :md="24" :sm="24" :xs="24">
          <a-card
            class="project-list"
            :loading="loading"
            style="margin-bottom: 24px"
            :bordered="false"
            title="进行中的项目"
            :body-style="{ padding: 0 }"
          >
            <a slot="extra">全部项目</a>
            <div>
              <a-card-grid
                class="project-card-grid"
                :key="i"
                v-for="(item, i) in projects"
              >
                <a-card :bordered="false" :body-style="{ padding: 0 }">
                  <a-card-meta>
                    <div slot="title" class="card-title">
                      <a-avatar size="small" :src="item.cover" />
                      <a>{{ item.title }}</a>
                    </div>
                    <div slot="description" class="card-description">
                      {{ item.description }}
                    </div>
                  </a-card-meta>
                  <div class="project-item">
                    <a href="/#/">科学搬砖组</a>
                    <span class="datetime">9小时前</span>
                  </div>
                </a-card>
              </a-card-grid>
            </div>
          </a-card>

          <a-card :loading="loading" title="动态" :bordered="false">
            <a-list>
              <a-list-item :key="index" v-for="(item, index) in activities">
                <a-list-item-meta>
                  <a-avatar slot="avatar" :src="item.user.avatar" />
                  <div slot="title">
                    <span>{{ item.user.nickname }}</span
                    >&nbsp; 在&nbsp;<a href="#">{{ item.project.name }}</a
                    >&nbsp; <span>{{ item.project.action }}</span
                    >&nbsp;
                    <a href="#">{{ item.project.event }}</a>
                  </div>
                  <div slot="description">{{ item.time }}</div>
                </a-list-item-meta>
              </a-list-item>
            </a-list>
          </a-card>
        </a-col>
        <a-col
          style="padding: 0 12px"
          :xl="8"
          :lg="24"
          :md="24"
          :sm="24"
          :xs="24"
        >
          <a-card
            title="快速开始 / 便捷导航"
            style="margin-bottom: 24px"
            :bordered="false"
            :body-style="{ padding: 0 }"
          >
            <div class="item-group">
              <a>操作一</a>
              <a>操作二</a>
              <a>操作三</a>
              <a>操作四</a>
              <a>操作五</a>
              <a>操作六</a>
              <a-button size="small" type="primary" ghost icon="plus"
                >添加</a-button
              >
            </div>
          </a-card>
          <a-card
            title="XX 指数"
            style="margin-bottom: 24px"
            :loading="radarLoading"
            :bordered="false"
            :body-style="{ padding: 0 }"
          >
            <div style="min-height: 400px">
            
              <radar :data="radarData" />
            </div>
          </a-card>
          <a-card :loading="loading" title="团队" :bordered="false">
            <div class="members">
              <a-row>
                <a-col :span="12" v-for="(item, index) in teams" :key="index">
                  <a>
                    <a-avatar size="small" :src="item.avatar" />
                    <span class="member">{{ item.name }}</span>
                  </a>
                </a-col>
              </a-row>
            </div>
          </a-card>
        </a-col>
      </a-row>
    </div> -->

    <div>
      <a-row :gutter="24">
        <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
          <chart-card :loading="loading" title="总销售额" total="￥126,560">
            <a-tooltip title="指标说明" slot="action">
              <a-icon type="info-circle-o" />
            </a-tooltip>
            <div>
              <trend flag="up" style="margin-right: 16px">
                <span slot="term">周同比</span>
                12%
              </trend>
              <trend flag="down">
                <span slot="term">日同比</span>
                11%
              </trend>
            </div>
            <template slot="footer">日均销售额<span>￥ 234.56</span></template>
          </chart-card>
        </a-col>
        <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
          <chart-card :loading="loading" title="访问量" :total="8846">
            <a-tooltip title="指标说明" slot="action">
              <a-icon type="info-circle-o" />
            </a-tooltip>
            <div>
              <mini-area />
            </div>
            <template slot="footer"
              >日访问量<span> {{ '1234' }}</span></template
            >
          </chart-card>
        </a-col>
        <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
          <chart-card :loading="loading" title="支付笔数" :total="6560">
            <a-tooltip title="指标说明" slot="action">
              <a-icon type="info-circle-o" />
            </a-tooltip>
            <div>
              <mini-bar />
            </div>
            <template slot="footer">转化率 <span>60%</span></template>
          </chart-card>
        </a-col>
        <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
          <chart-card :loading="loading" title="运营活动效果" total="78%">
            <a-tooltip title="指标说明" slot="action">
              <a-icon type="info-circle-o" />
            </a-tooltip>
            <div>
              <mini-progress
                color="rgb(19, 194, 194)"
                :target="80"
                :percentage="78"
                height="8px"
              />
            </div>
            <template slot="footer">
              <trend flag="down" style="margin-right: 16px">
                <span slot="term">同周比</span>
                12%
              </trend>
              <trend flag="up">
                <span slot="term">日环比</span>
                80%
              </trend>
            </template>
          </chart-card>
        </a-col>
      </a-row>

      <a-card
        :loading="loading"
        :bordered="false"
        :body-style="{ padding: '0' }"
      >
        <div class="salesCard">
          <a-tabs
            default-active-key="1"
            size="large"
            :tab-bar-style="{ marginBottom: '24px', paddingLeft: '16px' }"
          >
            <div class="extra-wrapper" slot="tabBarExtraContent">
              <div class="extra-item">
                <a>今日</a>
                <a>本周</a>
                <a>本月</a>
                <a>本年</a>
              </div>
              <a-range-picker :style="{ width: '256px' }" />
            </div>
            <a-tab-pane loading="true" tab="销售额" key="1">
              <a-row>
                <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
                  <bar :data="barData" title="销售额排行" />
                </a-col>
                <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                  <rank-list title="门店销售排行榜" :list="rankList" />
                </a-col>
              </a-row>
            </a-tab-pane>
            <a-tab-pane tab="访问量" key="2">
              <a-row>
                <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
                  <bar :data="barData2" title="销售额趋势" />
                </a-col>
                <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                  <rank-list title="门店销售排行榜" :list="rankList" />
                </a-col>
              </a-row>
            </a-tab-pane>
          </a-tabs>
        </div>
      </a-card>

      <div
        class="antd-pro-pages-dashboard-analysis-twoColLayout"
        :class="'desktop'"
      >
        <a-row :gutter="24" type="flex" :style="{ marginTop: '24px' }">
          <a-col :xl="12" :lg="24" :md="24" :sm="24" :xs="24">
            <a-card
              :loading="loading"
              :bordered="false"
              title="线上热门搜索"
              :style="{ height: '100%' }"
            >
              <a-dropdown
                :trigger="['click']"
                placement="bottomLeft"
                slot="extra"
              >
                <a class="ant-dropdown-link" href="#">
                  <a-icon type="ellipsis" />
                </a>
                <a-menu slot="overlay">
                  <a-menu-item>
                    <a href="javascript:;">操作一</a>
                  </a-menu-item>
                  <a-menu-item>
                    <a href="javascript:;">操作二</a>
                  </a-menu-item>
                </a-menu>
              </a-dropdown>
              <a-row :gutter="68">
                <a-col :xs="24" :sm="12" :style="{ marginBottom: ' 24px' }">
                  <number-info :total="12321" :sub-total="17.1">
                    <span slot="subtitle">
                      <span>搜索用户数</span>
                      <a-tooltip title="指标说明" slot="action">
                        <a-icon
                          type="info-circle-o"
                          :style="{ marginLeft: '8px' }"
                        />
                      </a-tooltip>
                    </span>
                  </number-info>
                  <!-- miniChart -->
                  <div>
                    <mini-smooth-area
                      :style="{ height: '45px' }"
                      :dataSource="searchUserData"
                      :scale="searchUserScale"
                    />
                  </div>
                </a-col>
                <a-col :xs="24" :sm="12" :style="{ marginBottom: ' 24px' }">
                  <number-info :total="2.7" :sub-total="26.2" status="down">
                    <span slot="subtitle">
                      <span>人均搜索次数</span>
                      <a-tooltip title="指标说明" slot="action">
                        <a-icon
                          type="info-circle-o"
                          :style="{ marginLeft: '8px' }"
                        />
                      </a-tooltip>
                    </span>
                  </number-info>
                  <!-- miniChart -->
                  <div>
                    <mini-smooth-area
                      :style="{ height: '45px' }"
                      :dataSource="searchUserData"
                      :scale="searchUserScale"
                    />
                  </div>
                </a-col>
              </a-row>
              <div class="ant-table-wrapper">
                <a-table
                  row-key="index"
                  size="small"
                  :columns="searchTableColumns"
                  :dataSource="searchData"
                  :pagination="{ pageSize: 5 }"
                >
                  <span slot="range" slot-scope="text, record">
                    <trend :flag="record.status === 0 ? 'up' : 'down'">
                      {{ text }}%
                    </trend>
                  </span>
                </a-table>
              </div>
            </a-card>
          </a-col>
          <a-col :xl="12" :lg="24" :md="24" :sm="24" :xs="24">
            <a-card
              class="antd-pro-pages-dashboard-analysis-salesCard"
              :loading="loading"
              :bordered="false"
              title="销售额类别占比"
              :style="{ height: '100%' }"
            >
              <div slot="extra" style="height: inherit">
                <!-- style="bottom: 12px;display: inline-block;" -->
                <span class="dashboard-analysis-iconGroup">
                  <a-dropdown :trigger="['click']" placement="bottomLeft">
                    <a-icon type="ellipsis" class="ant-dropdown-link" />
                    <a-menu slot="overlay">
                      <a-menu-item>
                        <a href="javascript:;">操作一</a>
                      </a-menu-item>
                      <a-menu-item>
                        <a href="javascript:;">操作二</a>
                      </a-menu-item>
                    </a-menu>
                  </a-dropdown>
                </span>
                <div class="analysis-salesTypeRadio">
                  <a-radio-group defaultValue="a">
                    <a-radio-button value="a">全部渠道</a-radio-button>
                    <a-radio-button value="b">线上</a-radio-button>
                    <a-radio-button value="c">门店</a-radio-button>
                  </a-radio-group>
                </div>
              </div>
              <h4>销售额</h4>
              <div>
                <!-- style="width: calc(100% - 240px);" -->
                <div>
                  <v-chart
                    :force-fit="true"
                    :height="405"
                    :data="pieData"
                    :scale="pieScale"
                  >
                    <v-tooltip :showTitle="false" dataKey="item*percent" />
                    <v-axis />
                    <!-- position="right" :offsetX="-140" -->
                    <v-legend dataKey="item" />
                    <v-pie position="percent" color="item" :vStyle="pieStyle" />
                    <v-coord type="theta" :radius="0.75" :innerRadius="0.6" />
                  </v-chart>
                </div>
              </div>
            </a-card>
          </a-col>
        </a-row>
      </div>
    </div>
  </page-header-wrapper>
</template>

<script>
import moment from 'moment'
import { timeFix } from '@/utils/util'
import { PageHeaderWrapper } from '@ant-design-vue/pro-layout'
// import { Radar } from '@/components'

import { Vue, Component } from 'nuxt-property-decorator'

const barData = []
const barData2 = []
for (let i = 0; i < 12; i += 1) {
  barData.push({
    x: `${i + 1}月`,
    y: Math.floor(Math.random() * 1000) + 200,
  })
  barData2.push({
    x: `${i + 1}月`,
    y: Math.floor(Math.random() * 1000) + 200,
  })
}

const rankList = []
for (let i = 0; i < 7; i++) {
  rankList.push({
    name: '白鹭岛 ' + (i + 1) + ' 号店',
    total: 1234.56 - i * 100,
  })
}

const searchUserData = []
for (let i = 0; i < 7; i++) {
  searchUserData.push({
    x: moment().add(i, 'days').format('YYYY-MM-DD'),
    y: Math.ceil(Math.random() * 10),
  })
}
const searchUserScale = [
  {
    dataKey: 'x',
    alias: '时间',
  },
  {
    dataKey: 'y',
    alias: '用户数',
    min: 0,
    max: 10,
  },
]

const searchTableColumns = [
  {
    dataIndex: 'index',
    title: '排名',
    width: 90,
  },
  {
    dataIndex: 'keyword',
    title: '搜索关键词',
  },
  {
    dataIndex: 'count',
    title: '用户数',
  },
  {
    dataIndex: 'range',
    title: '周涨幅',
    align: 'right',
    sorter: (a, b) => a.range - b.range,
    scopedSlots: { customRender: 'range' },
  },
]
const searchData = []
for (let i = 0; i < 50; i += 1) {
  searchData.push({
    index: i + 1,
    keyword: `搜索关键词-${i}`,
    count: Math.floor(Math.random() * 1000),
    range: Math.floor(Math.random() * 100),
    status: Math.floor((Math.random() * 10) % 2),
  })
}

const DataSet = require('@antv/data-set')

const sourceData = [
  { item: '家用电器', count: 32.2 },
  { item: '食用酒水', count: 21 },
  { item: '个护健康', count: 17 },
  { item: '服饰箱包', count: 13 },
  { item: '母婴产品', count: 9 },
  { item: '其他', count: 7.8 },
]

const pieScale = [
  {
    dataKey: 'percent',
    min: 0,
    formatter: '.0%',
  },
]

const dv = new DataSet.View().source(sourceData)
dv.transform({
  type: 'percent',
  field: 'count',
  dimension: 'item',
  as: 'percent',
})
const pieData = dv.rows

@Component({
  components: {
    PageHeaderWrapper: PageHeaderWrapper,
  },
})
export default class Workplace extends Vue {
  timeFix = timeFix()
  loading = true
  rankList = rankList
  currentUser = {
    name: 'Serati Ma',
    avatar:
      'https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png',
  }

  user = {}
  // 搜索用户数
  searchUserData = searchUserData
  searchUserScale = searchUserScale
  searchTableColumns = searchTableColumns
  searchData = searchData
  barData = barData
  barData2 = barData2

  //
  pieScale = pieScale
  pieData = pieData
  sourceData = sourceData
  pieStyle = {
    stroke: '#fff',
    lineWidth: 1,
  }
  created() {
    setTimeout(() => {
      this.loading = !this.loading
    }, 1000)
  }
}
</script>

<style lang="less" scoped>
.project-list {
  .card-title {
    font-size: 0;

    a {
      color: rgba(0, 0, 0, 0.85);
      margin-left: 12px;
      line-height: 24px;
      height: 24px;
      display: inline-block;
      vertical-align: top;
      font-size: 14px;

      &:hover {
        color: #1890ff;
      }
    }
  }

  .card-description {
    color: rgba(0, 0, 0, 0.45);
    height: 44px;
    line-height: 22px;
    overflow: hidden;
  }

  .project-item {
    display: flex;
    margin-top: 8px;
    overflow: hidden;
    font-size: 12px;
    height: 20px;
    line-height: 20px;

    a {
      color: rgba(0, 0, 0, 0.45);
      display: inline-block;
      flex: 1 1 0;

      &:hover {
        color: #1890ff;
      }
    }

    .datetime {
      color: rgba(0, 0, 0, 0.25);
      flex: 0 0 auto;
      float: right;
    }
  }

  .ant-card-meta-description {
    color: rgba(0, 0, 0, 0.45);
    height: 44px;
    line-height: 22px;
    overflow: hidden;
  }
}

.item-group {
  padding: 20px 0 8px 24px;
  font-size: 0;

  a {
    color: rgba(0, 0, 0, 0.65);
    display: inline-block;
    font-size: 14px;
    margin-bottom: 13px;
    width: 25%;
  }
}

.members {
  a {
    display: block;
    margin: 12px 0;
    line-height: 24px;
    height: 24px;

    .member {
      font-size: 14px;
      color: rgba(0, 0, 0, 0.65);
      line-height: 24px;
      max-width: 100px;
      vertical-align: top;
      margin-left: 12px;
      transition: all 0.3s;
      display: inline-block;
    }

    &:hover {
      span {
        color: #1890ff;
      }
    }
  }
}

.mobile {
  .project-list {
    .project-card-grid {
      width: 100%;
    }
  }

  .more-info {
    border: 0;
    padding-top: 16px;
    margin: 16px 0 16px;
  }

  .headerContent .title .welcome-text {
    display: none;
  }
}
</style>
