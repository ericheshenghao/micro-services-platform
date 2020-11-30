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
            :cell-style="cellStyle"
            @on-load="onLoad"
            @row-update="rowUpdate"
            @refresh-change="rowRefresh"
            @row-save="rowSave"
            @row-del="rowDel"
            :permission="permission"
            :data="tableData"
            :option="option"
          >
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

  async checkAuth() {
    this.permission.delBtn = await this.$store.dispatch(
      'checkAuth',
      'sys:indices:delete'
    )
    this.permission.addBtn = await this.$store.dispatch(
      'checkAuth',
      'sys:indices:add'
    )
    this.permission.menu = await this.$store.dispatch(
      'checkAuth',
      'sys:indices:edit'
    )
  }

  permission = {
    delBtn: true,
    addBtn: true,
    menu: true,
  }

  mounted() {
    // this.checkAuth()
  }

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
        label: '索引健康',
        prop: 'health',
      },

      {
        label: '索引状态',
        prop: 'status',
      },
      //   {
      //     label: '密钥',
      //     prop: 'clientSecret',
      //   },
      {
        label: '索引名称',
        prop: 'index',
      },
      {
        label: '文档数量',
        prop: 'docsCount',
      },
      {
        label: '删除数量',
        prop: 'docsDeleted',
      },
      {
        label: '存储空间',
        prop: 'storeSize',
      },
    ],
  }

  async rowSave(form: any, done: any, loading: any) {
    // const res = await this.http.post('pri/sysClientDetails/', form, {
    //   prefix: 'core',
    // })
    done(form)
  }

  async rowUpdate(form: any, index: any, done: any, loading: any) {
    done(form)
    this.onLoad()
  }

  async rowDel(form: any, index: any) {}

  async onLoad() {
    this.loading = true

    const { data } = await this.http.post(
      '/admin/indices',
      {
        pageNum: this.page.currentPage,
        pageSize: this.page.pageSize,
        params: {},
      },
      { prefix: 'search' }
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

  cellStyle({ row, column, rowIndex, columnIndex }: any) {
    if (columnIndex == 1) {
      return {
        color: row.health,
        fontWeight: 'bold',
        fontSize: '20',
      }
    }
  }
}
</script>
<style lang="scss" scoped></style>
