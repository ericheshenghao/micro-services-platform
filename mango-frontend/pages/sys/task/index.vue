<template>
  <div class="bg-white h-100">
    <el-container class="pt-3 px-3">
      <el-main>
        <div class="px-3 py-2">
          <el-breadcrumb class="py-2" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item></el-breadcrumb-item>
          </el-breadcrumb>

          <avue-crud
            :cell-style="cellStyle"
            :page.sync="page"
            :table-loading="loading"
            @on-load="onLoad"
            @row-update="rowUpdate"
            @row-save="rowSave"
            @row-del="rowDel"
            @refresh-change="rowRefresh"
            :permission="permission"
            :data="tableData"
            :option="option"
          >
            <template slot-scope="{ type, size, row }" slot="menu">
              <el-button
                icon="el-icon-check"
                :size="size"
                :type="type"
                @click="pause(row)"
                >暂停</el-button
              >
              <el-button
                icon="el-icon-check"
                :size="size"
                :type="type"
                @click="resume(row)"
                >恢复</el-button
              >
            </template>

            <template slot="jobClassNameForm" slot-scope="scope">
              <el-select v-model="scope.row.jobClassName" placeholder="">
                <el-option
                  v-for="(item, index) in JobList"
                  :key="index"
                  :label="item.alias"
                  :value="item.jobClassName"
                >
                </el-option>
              </el-select>
            </template>

            <template slot="cronExpressionForm" slot-scope="scope">
              <cron-input v-model="scope.row.cronExpression"></cron-input>
            </template>
          </avue-crud>
        </div>
      </el-main>
    </el-container>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Watch } from 'nuxt-property-decorator'
import { confirm, msg } from '~/plugins/util/confrim'
@Component({
  components: {},
})
export default class Task extends Vue {
  loading = true
  tableData = []
  http = Vue.prototype.$http
  JobList: any = []
  page: any = {
    total: 10,
    pagerCount: 5,
    currentPage: 1,
    pageSize: 10,
    pageSizes: [5, 10, 20],
    layout: 'total, sizes,prev, pager, next, jumper',
    background: false,
  }

  permission = {
    delBtn: true,
    addBtn: true,
    menu: true,
  }

  option = {
    height: '450',
    // sortable: true,
    // selection: true,
    menuWidth: 210,
    labelWidth: 100,
    index: true,
    size: 'mini',
    dialogDrag: true,
    column: [
      // {
      //   label: '任务名称',
      //   prop: 'jobName',
      //   editDisplay: false,
      //   addDisplay: false,
      // },
      {
        label: '任务名称',
        prop: 'description',
        editDisplay: false,
        addDisplay: false,
      },
      {
        label: '任务类名',
        prop: 'jobClassName',
        formslot: true,
        labelslot: true,
        editDisplay: false,
        rules: [
          {
            required: true,
            trigger: 'blur',
          },
        ],
      },
      {
        label: '定时任务组',
        prop: 'jobGroup',
        editDisplay: false,
        rules: [
          {
            required: true,
            trigger: 'blur',
          },
        ],
      },

      {
        label: '触发器名称',
        prop: 'triggerName',
        editDisplay: false,
        addDisplay: false,
      },
      {
        label: '触发器组',
        prop: 'triggerGroup',
        editDisplay: false,
        addDisplay: false,
      },
      // {
      //   label: '重复间隔',
      //   prop: 'repeatInterval',
      //   editDisplay: false,
      //   addDisplay: false,
      // },
      // {
      //   label: '触发次数',
      //   prop: 'timesTriggered',
      //   editDisplay: false,
      //   addDisplay: false,
      // },
      {
        label: 'cron 表达式',
        prop: 'cronExpression',
        formslot: true,
        span: 24,
        rules: [
          {
            required: true,
            trigger: 'blur',
          },
        ],
      },
      {
        label: '时区',
        prop: 'timeZoneId',
        editDisplay: false,
        addDisplay: false,
      },
      {
        label: '定时任务状态',
        prop: 'triggerState',
        editDisplay: false,
        addDisplay: false,
        dicData: [
          { label: '运行中', value: 'ACQUIRED' },
          { label: '等待中', value: 'WAITING' },
          { label: '暂停', value: 'PAUSED' },
        ],
      },
    ],
  }

  cellStyle({ row, column, rowIndex, columnIndex }: any) {
    if (columnIndex == 8) {
      if (row.triggerState !== 'ACQUIRED') {
        return {
          color: 'red',
          fontWeight: 'bold',
          fontSize: '20',
        }
      } else {
        return {
          color: 'green',
          fontWeight: 'bold',
          fontSize: '20',
        }
      }
    }
  }

  mounted() {
    this.getJobList()
  }
  async getJobList() {
    const res = await this.http.get('pri/job/jobList', { prefix: 'task' })
    this.JobList = res.data.data
  }

  async rowSave(form: any, done: any, loading: any) {
    const index = this.JobList.findIndex(
      (e: any) => e.jobClassName === form.jobClassName
    )

    await this.http.post(
      '/pri/job',
      {
        jobClassName: form.jobClassName,
        cronExpression: form.cronExpression,
        jobGroupName: form.jobGroup,
        alias: this.JobList[index].alias,
      },
      {
        prefix: 'task',
      }
    )
    done(form)
    msg(this, 'success', '保存成功')
    this.onLoad()
  }

  async rowUpdate(form: any, index: any, done: any, loading: any) {
    await this.http.put(
      '/pri/job/cron',
      {
        jobClassName: form.jobClassName,
        cronExpression: form.cronExpression,
        jobGroupName: form.jobGroup,
      },
      {
        prefix: 'task',
      }
    )
    done(form)
    msg(this, 'success', '更新成功')
    this.onLoad()
  }

  rowDel(form: any, index: any) {
    confirm(
      this,
      () => {
        this.http.delete(
          'pri/job?jobClassName=' +
            form.jobClassName +
            '&jobGroupName=' +
            form.jobGroup,
          { prefix: 'task' }
        )
      },
      () => {
        this.onLoad()
      }
    )
  }

  async pause(row: any) {
    await this.http.put(
      'pri/job/pause',
      {
        jobClassName: row.jobClassName,
        jobGroupName: row.jobGroup,
      },

      { prefix: 'task' }
    )
    msg(this, 'success', '暂停成功')
    this.onLoad()
  }

  async resume(row: any) {
    await this.http.put(
      'pri/job/resume',
      {
        jobClassName: row.jobClassName,
        jobGroupName: row.jobGroup,
      },

      { prefix: 'task' }
    )
    msg(this, 'success', '恢复成功')
    this.onLoad()
  }

  rowRefresh() {
    this.onLoad()
  }

  async onLoad() {
    this.loading = true

    const { data } = await this.http.post(
      'pri/job/findPage',
      {
        pageNum: this.page.currentPage,
        pageSize: this.page.pageSize,
        params: {},
      },
      { prefix: 'task' }
    )
    this.page.total = data.data.totalSize

    setTimeout(() => {
      this.loading = false
      this.tableData = data.data.content
    }, 500)
  }
}
</script>
<style lang="scss" scoped></style>
