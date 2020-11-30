<template>
  <div class="bg-white h-100">
    <el-container class="pt-3 px-3">
      <el-aside
        width="200px"
        class="pr-2"
        style="border-right: 1px solid #ececec"
      >
        <avue-tree
          ref="tree"
          :option="treeOption"
          :data="treeData"
          @node-click="nodeClick"
        ></avue-tree>
      </el-aside>
      <el-main>
        <avue-crud
          :table-loading="loading"
          :page.sync="page"
          :permission="permission"
          @on-load="onLoad"
          :data="tableData"
          :option="option"
          @refresh-change="rowRefresh"
          @row-update="rowUpdate"
          @row-save="rowSave"
          @row-del="rowDel"
        >
          <template slot-scope="scope" slot="parentIdForm">
            <el-cascader
              v-model="scope.row.parentId"
              :props="casProps"
              :options="treeData"
            ></el-cascader>
          </template>

          <template slot="orderNum" slot-scope="scope">
            <el-input v-model="scope.row.orderNum" placeholder="请输入内容">{{
              scope.row.orderNum
            }}</el-input>
          </template>
        </avue-crud>
      </el-main>
    </el-container>
  </div>
</template>
<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
@Component({
  components: {},
})
export default class sysOffice extends Vue {
  // vue挂载
  http = Vue.prototype.$http

  //########### 请求数据

  tableData: any = []
  treeData: any = []
  // 权限控制
  permission = {
    delBtn: false,
    addBtn: false,
    menu: false,
  }

  //########### 弹出窗口选项
  crudOption = {
    column: [
      {
        label: '姓名',
        prop: 'loginCode',
        placeholder: '请输入姓名',
        disabled: true,
      },
      {
        label: '昵称',
        prop: 'userNmae',
        disabled: true,
      },
    ],
  }

  //########### 展示表格选项
  option = {
    height: '450',
    // sortable: true,
    // selection: true,
    menuWidth: 210,
    // index: true,
    size: 'mini',
    dialogDrag: true,
    column: [
      {
        rules: [
          {
            required: true,
            message: '',
            trigger: 'blur',
          },
        ],
        label: '机构名称',
        prop: 'officeName',
      },
      {
        label: '全节点名',
        prop: 'treeNames',
        editDisplay: false,
        addDisplay: false,
      },

      {
        label: '机构树',
        prop: 'parentIds',
        editDisplay: false,
        addDisplay: false,
      },

      {
        label: '上级机构',
        prop: 'parentId',

        formslot: true,
        labelslot: true,
      },

      {
        label: '创建时间',
        prop: 'createTime',
        editDisplay: false,
        overHidden: true,
        addDisplay: false,
      },
      {
        label: '创建人',
        prop: 'createBy',
        editDisplay: false,
        addDisplay: false,
      },
      {
        label: '排序',
        prop: 'orderNum',
        value: 80,
        slot: true,
      },
      {
        label: '机构状态',
        prop: 'status',
        value: 0,
        addDisplay: false,
        editDisplay: false,
        // slot: true,
      },
    ],
  }
  //########### 机构树选项 右键属性
  treeOption = {
    defaultExpandAll: true,
    dialogDrag: true,
    props: {
      label: 'officeName',
    },
    formOption: {
      labelWidth: 100,
      column: [
        {
          label: '上级机构',
          prop: 'parentId',
        },
      ],
    },
  }

  //########### 级联选择器属性
  casProps = {
    value: 'id',
    label: 'officeName',
    checkStrictly: true,
    expandTrigger: 'hover',
  }

  //########### data
  loading = true

  form: any = {}
  // 分页属性
  page: any = {
    total: 40,
    pagerCount: 5,
    currentPage: 1,
    pageSize: 10,
    pageSizes: [5, 10, 20],
    layout: 'total, sizes,prev, pager, next, jumper',
    background: true,
  }
  // 请求方法变更

  //########### 生命周期初始化
  mounted() {
    this.init()
    this.checkAuth()
  }

  async checkAuth() {
    this.permission.delBtn = await this.$store.dispatch(
      'checkAuth',
      'sys:office:edit'
    )
    this.permission.addBtn = await this.$store.dispatch(
      'checkAuth',
      'sys:office:add'
    )
    this.permission.menu = await this.$store.dispatch(
      'checkAuth',
      'sys:office:edit'
    )
  }

  async init() {
    const { data: data } = await this.http.get('/pri/office/findOfficeTree', {
      prefix: 'core',
    })
    this.treeData = data.datas
  }
  //########### 方法

  async rowSave(form: any, done: any, loading: any) {
    if (form.parentId !== null) {
      form.parentIds = '0,' + form.parentId.toString()
      form.parentId = form.parentId[form.parentId.length - 1]
      if (form.parentId == null) form.parentId = 0
    }

    await this.http.post('/pri/office', form, {
      prefix: 'core',
    })
    done(form)
    this.onLoad()
  }

  async rowUpdate(form: any, index: any, done: any, loading: any) {
    done(form)
    this.onLoad()
  }

  async rowDel(form: any, index: any, done: any, loading: any) {
    this.mixConfirm(async () => {
      await this.http.delete(`/pri/office/${form.id}`, { prefix: 'core' })
    }, this.onLoad)
  }

  //################### 封装的弹窗按钮的弹出数据初始化

  //################### 封装的弹窗按钮的属性

  officeId = ''
  // 表格加载
  async onLoad() {
    this.loading = true

    // 查找所有机构
    let url = 'pri/office/findPage/' + this.officeId

    const { data: data } = await this.http.post(
      url,
      {
        pageNum: this.page.currentPage,
        pageSize: this.page.pageSize,
        params: {},
      },
      { prefix: 'core' }
    )

    this.page.total = data.datas.total

    setTimeout(() => {
      this.loading = false
      this.tableData = data.datas.records
    }, 300)
  }

  // 节点点击
  nodeClick(data: any) {
    this.page.currentPage = 1
    this.page.pageSize = 10
    this.officeId = data.id
    this.onLoad()
  }

  rowRefresh() {
    this.onLoad()
  }
}
</script>
<style lang="scss"></style>
