<template>
  <div class="bg-white h-100">
    <el-container class="pt-3 px-3">
      <el-main>
        <div class="px-3 py-2">
          <el-breadcrumb class="py-2" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item></el-breadcrumb-item>
          </el-breadcrumb>

          <avue-crud
            :page.sync="page"
            :table-loading="loading"
            @on-load="onLoad"
            @refresh-change="rowRefresh"
            :permission="permission"
            :data="tableData"
            :option="option"
            :search.sync="search"
            @search-change="searchChange"
          >
            <template slot="searchMenu" slot-scope="{ row, size }">
              <el-button :size="size" @click="searchSubmit(row)"
                >自定义提交</el-button
              >
            </template>

            <template slot-scope="{ disabled, size }" slot="userCodeSearch">
              <el-input
                placeholder="自定义输入框"
                :disabled="disabled"
                :size="size"
                style="width: 200px"
                v-model="search.age"
              ></el-input>
            </template>
          </avue-crud>
        </div>
      </el-main>
    </el-container>
  </div>
</template>
<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
@Component({
  components: {},
})
export default class searchIndex extends Vue {
  loading = true
  tableData = []
  form = {}
  search = {}
  http = Vue.prototype.$http
  page: any = {
    total: 10,
    pagerCount: 5,
    currentPage: 1,
    pageSize: 10,
    pageSizes: [5, 10, 20],
    layout: 'total, sizes,prev, pager, next, jumper',
    background: true,
  }

  permission = {
    delBtn: false,
    addBtn: false,
    menu: false,
  }

  mounted() {}

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
        label: '用户编码',
        prop: 'userCode',
        searchslot: true,
        search: true,
      },

      {
        label: '登录账号',
        prop: 'loginCode',
      },

      {
        label: '用户名',
        prop: 'userName',
      },
      {
        label: '创建时间',
        prop: 'createTime',
      },
      {
        label: '更新时间',
        prop: 'lastUpdateTime',
      },
      {
        label: '状态',
        prop: 'status',
      },
    ],
  }
  searchSubmit() {
    console.log('submit')
  }

  searchChange() {}
  async onLoad() {
    this.loading = true
    const {
      data,
    } = await this.http.get(
      `/pri/user/search?page=${this.page.currentPage}&limit=${this.page.pageSize}`,
      { prefix: 'core' }
    )
    this.page.total = data.totalSize

    setTimeout(() => {
      this.loading = false
      this.tableData = data.content
    }, 500)
  }

  rowRefresh() {
    this.onLoad()
  }
}
</script>
<style lang="scss" scoped></style>
