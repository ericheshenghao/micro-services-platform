<template>
  <canvas refs="chartcanvas" width="200" height="40" v-loaded-callback="setCanvas"></canvas>
</template>
<script type="text/javascript">
require('chart.js')
export default {
  name: 'components-base-chartjs',
  props: {
    data: {},
    options: {},
    type: {},
  },
  data: function () {
    return {
      canvas: null,
      chart: null,
    }
  },
  watch: {
    canvas: function () {
      // chart对象生成时触发
      this.initChart()
    },
    data: {
      handler: function () {
        // 数据变化时触发
        this.updateChart()
      },
      deep: true,
    },
  },
  destoryed: function () {
    if (this.cahrt) {
      this.cahrt.destroy()
    }
  },
  computed: {
    currentOptions: function () {
      var options = {}
      if (this.options) {
        // 加载自定义配置参数
        for (var i in this.options) {
          options[i] = this.options[i]
        }
      }
      return options
    },
  },
  methods: {
    setCanvas: function (el) {
      // dom生成时触发
      this.canvas = el
    },
    initChart: function () {
      // 更新chart结果
      if (this.data && this.currentOptions) {
        // 保证参数的存在
        this.chart = new Chart(this.canvas.getContext('2d'), {
          type: this.type,
          data: this.data,
          options: this.options,
        })
      }
    },
    updateChart: function () {
      // 更新chart结果
      this.chart.data = JSON.parse(JSON.stringify(this.data))
      this.chart.update()
    },
  },
}
</script>
