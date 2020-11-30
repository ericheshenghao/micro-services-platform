<template>
  <div class="d-flex">
    <el-select
      class="mr-2"
      style="width: 100px !important"
      v-model="cronSelected"
      placeholder=""
      @change="selectChange"
    >
      <el-option
        v-for="option in cronOptions"
        :key="option.label"
        :label="option.label"
        :value="option.value"
      >
      </el-option>
    </el-select>

    <el-select
      v-if="cronShow.showWeekDay.statu"
      class="mr-2"
      style="width: 100px !important"
      v-model="cron.weekday"
      @change="handleChange"
      placeholder=""
    >
      <el-option
        v-for="day in weekDayOptions"
        :key="day.label"
        :label="day.label"
        :value="day.value"
      >
      </el-option>
    </el-select>
    <div class="mr-2" v-if="cronShow.showDay.statu">
      <el-input-number
        style="flex: 0.15"
        v-model="cron.day"
        @change="handleChange"
        :min="0"
        :max="31"
        label="天"
      ></el-input-number>
      <span>天</span>
    </div>

    <div class="mr-2" v-if="cronShow.showMonthDay.statu">
      <el-input-number
        style="flex: 0.15"
        v-model="cron.monthDay"
        @change="handleChange"
        :min="0"
        :max="31"
        label="日"
      ></el-input-number>
      <span>日</span>
    </div>
    <div class="mr-2" v-if="cronShow.showHour.statu">
      <el-input-number
        style="flex: 0.15"
        v-model="cron.hour"
        @change="handleChange"
        :min="0"
        :max="23"
        label="小时"
      ></el-input-number>
      <span>小时</span>
    </div>

    <div class="mr-2" v-if="cronShow.showMinute.statu">
      <el-input-number
        style="flex: 0.15"
        v-model="cron.minute"
        @change="handleChange"
        :min="0"
        :max="59"
        label="分钟"
      ></el-input-number>
      <span>分钟</span>
    </div>

    <div class="mr-2" v-if="cronShow.showSecond.statu">
      <el-input-number
        style="flex: 0.15"
        v-model="cron.second"
        @change="handleChange"
        :min="0"
        :max="59"
        label="秒"
      ></el-input-number>
      <span>秒</span>
    </div>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
@Component({})
export default class CronInput extends Vue {
  cronSelected = 1
  default: any = {
    day: 1,
    hour: 1,
    minute: 30,
    weekday: 1,
    monthDay: 1,
    second: 1,
  }
  cron: any = {
    day: 1,
    hour: 1,
    minute: 30,
    weekday: 1,
    monthDay: 1,
    second: 1,
  }

  cronShow: any = {
    showDay: { alias: 'day', statu: false, content: [2] },
    showHour: { alias: 'hour', statu: true, content: [1, 2, 4, 6, 7] },
    showMinute: {
      alias: 'minute',
      statu: true,
      content: [1, 2, 3, 4, 5, 6, 7],
    },
    showWeekDay: { alias: 'weekday', statu: false, content: [6] },
    showMonthDay: { alias: 'monthDay', statu: false, content: [7] },
    showSecond: { alias: 'second', statu: false, content: [8] },
  }

  cronOptions = [
    { label: '每天', value: 1, template: '0 {minute} {hour} * * ? *' },
    { label: 'N天', value: 2, template: '0 {minute} {hour} 1/{day} * ? *' },
    { label: '每小时', value: 3, template: '0 {minute} * * * ? *' },
    { label: 'N小时', value: 4, template: '0 {minute} 0/{hour}  * * ? *' },
    { label: 'N分钟', value: 5, template: '0 0/{minute} * * * ? *' },
    {
      label: '每星期',
      value: 6,
      template: '0 {minute} {hour} ? * {weekday} *',
    },
    {
      label: '每月',
      value: 7,
      template: '0 {minute} {hour} {monthDay} 1/1 ? *',
    },
    { label: 'N秒', value: 8, template: '0/{second} * * * * ? *' },
  ]

  weekDayOptions = [
    { label: '周一', value: 1 },
    { label: '周二', value: 2 },
    { label: '周三', value: 3 },
    { label: '周四', value: 4 },
    { label: '周五', value: 5 },
    { label: '周六', value: 6 },
    { label: '周日', value: 7 },
  ]

  handleCron() {
    let array = []
    for (const key in this.cronShow) {
      if (this.cronShow[key].statu == false) {
        this.cron[this.cronShow[key].alias] = null
      } else {
        array.push({
          value: this.cron[this.cronShow[key].alias],
          alias: this.cronShow[key].alias,
        })
      }
    }

    var tem: any = this.cronOptions[this.cronSelected - 1].template

    var res = this.format(tem, array)

    // 0 0 9 ? * 1  每周一早上九点
    console.log(res)

    this.$nextTick(() => {
      this.$emit('input', res)
    })
  }

  //var str2 = "js实现用{1}自符串替换占位符{1} {2}  {0} ".format("I","LOVE","YOU");
  format(temp: any, argument: Array<any>) {
    if (argument.length == 0) return temp

    for (var i = 0; i < argument.length; i++) {
      temp = temp.replace(`{${argument[i].alias}}`, argument[i].value)
    }

    return temp
  }

  mounted() {
    this.handleCron()
  }

  handleChange() {
    this.handleCron()
  }

  selectChange(value: any) {
    for (const key in this.cron) {
      this.cron[key] = this.default[key]
    }

    for (const key in this.cronShow) {
      if (this.cronShow[key].content.includes(value))
        this.cronShow[key].statu = true
      else this.cronShow[key].statu = false
    }
    this.handleCron()
  }
}
</script>

<style lang="scss" scoped></style>
