<template>
  <div style="height: 150vh;" class="h-100">
    <!-- <template>
      <chartjs :options="options" type="pie" :data="data"></chartjs>
    </template>-->
    <!-- <div>{{$t('mango.data.column1')}}</div> -->

    <div class="basic-container">
      <avue-data-tabs :option="option"></avue-data-tabs>
    </div>
    <div class="basic-container">
      <avue-data-icons :option="iconsOption"></avue-data-icons>
    </div>
    <div class="px-2 pt-2 fs-6 text-primary">{{ $t('mango.data5.title') }}</div>
    <el-row class="pt-2 px-2" :gutter="12">
      <el-col :span="8">
        <el-card shadow="always">
          <div>
            <el-link :href="config.NACOS_SERVICE" target="_blank">{{
              $t('mango.data5.column1')
            }}</el-link>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="always">
          <div>
            <el-link :href="config.SENTINEL_SERVICE" target="_blank">{{
              $t('mango.data5.column2')
            }}</el-link>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="always">
          <div>
            <el-link :href="config.SWAGGER_SERVICE" target="_blank">{{
              $t('mango.data5.column3')
            }}</el-link>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <div class="px-2 pt-2 fs-6 text-primary">{{ $t('mango.data6.title') }}</div>
    <el-row class="py-2 px-2" :gutter="12">
      <el-col :span="8">
        <el-card shadow="always">
          <div>
            <el-link :href="config.MONITOR_SERVICE" target="_blank">{{
              $t('mango.data6.column1')
            }}</el-link>
          </div>
        </el-card>
      </el-col>
      <el-col :span="8">
        <el-card shadow="always">
          <div>
            <el-link :href="config.DRUID_SERVICE" target="_blank">{{
              $t('mango.data6.column2')
            }}</el-link>
          </div>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card shadow="always">
          <div>
            <el-link :href="config.RTBOARD_SERVICE" target="_blank">{{
              $t('mango.data6.column3')
            }}</el-link>
          </div>
        </el-card>
      </el-col>
    </el-row>
    <div class="basic-container">
      <avue-data-progress :option="progressOption"></avue-data-progress>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue, Component, Watch } from 'nuxt-property-decorator'
import config from '~/plugins/config/website.js'
@Component({
  components: {},
})
export default class index extends Vue {
  options = {}
  config = config

  data = {
    datasets: [
      {
        data: [10, 20, 30],
      },
    ],

    // These labels appear in the legend and in the tooltips when hovering different arcs
    labels: ['Red', 'Yellow', 'Blue'],
  }
  get iconsOption() {
    return {
      span: 4,
      data: [
        {
          click: function (item: any) {
            alert(JSON.stringify(item))
          },
          title: this.$t('mango.data2.column1'),
          count: 12678,
          icon: 'el-icon-tickets',
          href: '',
          target: '_blank',
        },
        {
          click: function (item: any) {
            alert(JSON.stringify(item))
          },
          title: this.$t('mango.data2.column2'),
          count: 22139,
          icon: 'el-icon-success',
          href: '',
          target: '_blank',
        },
        {
          click: function (item: any) {
            alert(JSON.stringify(item))
          },
          title: this.$t('mango.data2.column3'),
          count: 35623,
          icon: 'el-icon-info',
          href: '',
          target: '_blank',
        },
        {
          click: function (item: any) {
            alert(JSON.stringify(item))
          },
          title: this.$t('mango.data2.column4'),
          count: 16826,
          icon: 'el-icon-message',
          href: '',
          target: '_blank',
        },
        {
          click: function (item: any) {
            alert(JSON.stringify(item))
          },
          title: this.$t('mango.data2.column4'),
          count: 16826,
          icon: 'el-icon-message',
          href: '',
          target: '_blank',
        },
        {
          click: function (item: any) {
            alert(JSON.stringify(item))
          },
          title: this.$t('mango.data2.column4'),
          count: 16826,
          icon: 'el-icon-message',
          href: '',
          target: '_blank',
        },
      ],
    }
  }

  // 签到转化
  get progressOption() {
    return {
      span: 6,
      data: [
        {
          click: function (item: any) {
            alert(JSON.stringify(item))
          },
          title: this.$t('mango.data3.column1'),
          color: 'rgb(178, 159, 255)',
          count: 32,
          href: '',
          target: '_blank',
        },
        {
          click: function (item: any) {
            alert(JSON.stringify(item))
          },
          title: this.$t('mango.data3.column2'),
          color: 'rgb(230, 71, 88)',
          count: 32,
          href: '',
          target: '_blank',
        },
        {
          click: function (item: any) {
            alert(JSON.stringify(item))
          },
          title: this.$t('mango.data3.column3'),
          color: 'rgb(27, 201, 142)',
          count: 56,
          href: '',
          target: '_blank',
        },
        {
          click: function (item: any) {
            alert(JSON.stringify(item))
          },
          title: this.$t('mango.data3.column4'),
          color: 'red',
          count: 56,
          href: '',
          target: '_blank',
        },
      ],
    }
  }

  get option() {
    return {
      data: [
        {
          click: function (item: any) {
            alert(JSON.stringify(item))
          },
          title: this.$t('mango.data.column1'),
          subtitle: this.$t('mango.data.subtitle'),
          count: 7993,
          allcount: 10222,
          text: this.$t('mango.data.text1'),
          color: 'rgb(27, 201, 142)',
          key: this.$t('mango.data.key1'),
        },
        {
          title: this.$t('mango.data.column2'),
          subtitle: this.$t('mango.data.subtitle'),
          count: 3112,
          allcount: 10222,
          text: this.$t('mango.data.text2'),
          color: 'rgb(230, 71, 88)',
          key: this.$t('mango.data.key2'),
        },
        {
          title: this.$t('mango.data.column3'),
          subtitle: this.$t('mango.data.subtitle'),
          count: 908,
          allcount: 10222,
          text: this.$t('mango.data.text3'),
          color: 'rgb(56, 161, 242)',
          key: this.$t('mango.data.key3'),
        },
      ],
    }
  }
}
</script>

<style>
.container {
  margin: 0 auto;
  min-height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
}

.title {
  font-family: 'Quicksand', 'Source Sans Pro', -apple-system, BlinkMacSystemFont,
    'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
  display: block;
  font-weight: 300;
  font-size: 100px;
  color: #35495e;
  letter-spacing: 1px;
}

.subtitle {
  font-weight: 300;
  font-size: 42px;
  color: #526488;
  word-spacing: 5px;
  padding-bottom: 15px;
}

.links {
  padding-top: 15px;
}
</style>
