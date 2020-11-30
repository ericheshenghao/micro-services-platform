<template>
  <div class="bg-white h-100">
    <el-container class="pt-3 px-3 h-100">
      <el-main>
        <div>
          <avue-crud
            :page.sync="page"
            :data="tableData"
            :option="option"
            @on-load="onLoad"
          >
            <template slot="isSysForm" slot-scope="scope">
              <el-radio-group v-model="scope.row.isSys">
                <el-radio label="1"> 是 </el-radio>
                <el-radio label="0"> 否 </el-radio>
              </el-radio-group>
            </template>
          </avue-crud>
        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
@Component({})
export default class Name extends Vue {
  axios = Vue.prototype.$http
  option = {
    height: '450',
    // sortable: true,
    // selection: true,
    menuWidth: 210,
    index: true,
    size: 'mini',
    dialogDrag: true,
    column: [
      {
        label: '字典名',
        prop: 'dictName',
      },
      {
        label: '字典类型',
        prop: 'dictType',
      },
      {
        label: '系统字典',
        prop: 'isSys',
        span: 24,
        formslot: true,
        dicData: [
          {
            label: '是',
            value: '1',
          },
          {
            label: '否',
            value: '0',
          },
        ],
      },
    ],
  }

  tableData = []
  page = { currentPage: 1, pageSize: 10, total: '' }

  async onLoad() {
    const { data } = await this.axios.post(
      '/pri/sysDictType/findPage',
      {
        pageNum: this.page.currentPage,
        pageSize: this.page.pageSize,
        params: {},
      },
      { prefix: 'core' }
    )
    this.page.total = data.data.totalSize
    this.tableData = data.data.content
  }
}
</script>

<style lang="scss" scoped></style>
