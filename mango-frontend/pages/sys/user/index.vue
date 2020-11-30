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
          :cell-style="cellStyle"
          :table-loading="loading"
          :page.sync="page"
          @on-load="onLoad"
          :permission="permission"
          :data="tableData"
          :option="option"
          @refresh-change="rowRefresh"
          @row-del="rowDel"
          @row-update="rowUpdate"
          @search-change="searchChange"
          @row-save="rowSave"
          :search.sync="search"
        >
          <template slot="searchMenu" slot-scope="{ row, size }">
            <el-button :size="size" @click="searchSubmit(row)"
              >自定义提交</el-button
            >
          </template>

          <template slot-scope="scope" slot="status">
            <el-switch
              :active-value="1"
              :inactive-value="0"
              @change="delFlagChange(scope.row)"
              v-model="scope.row.status"
              active-color="#13ce66"
              inactive-color="#ff4949"
            >
            </el-switch>
          </template>

          <!-- <template slot="search" slot-scope="{row,size}">
            <el-input placeholder="自定义输入框" :size="size" style="width:200px" v-model="search.slot"></el-input>
          </template>-->
          <!-- TODO 有问题 -->
          <template slot-scope="scope" slot="sysOfficeForm">
            <el-cascader
              v-if="scope.row.sysOffice"
              v-model="scope.row.sysOffice.officeList"
              :props="casProps"
              :options="treeData"
              clearable
            ></el-cascader>
            <el-cascader
              v-else
              v-model="scope.row.officeTree"
              :props="casProps"
              :options="treeData"
              clearable
            ></el-cascader>
          </template>

          <template slot="orderNum" slot-scope="scope">
            <el-input v-model="scope.row.orderNum" placeholder="请输入内容">
              {{ scope.row.orderNum }}
            </el-input>
          </template>

          <template slot="statusForm" slot-scope="scope">
            <el-radio-group v-model="scope.row.status">
              <el-radio :label="0">禁用</el-radio>
              <el-radio :label="1">正常</el-radio>
            </el-radio-group>
          </template>

          <template slot="sysOffice" slot-scope="scope">
            {{ scope.row.sysOffice ? scope.row.sysOffice.name : '' }}
          </template>
          <template
            v-if="!(scope.row.name === 'admin')"
            slot-scope="scope"
            slot="menu"
          >
            <button-dialog
              class="pl-1"
              icon="el-icon-check"
              size="mini"
              type="text"
              :data="form"
              :option="crudOption"
              @click="initData(scope.row)"
              @submit="submit(scope.row)"
              :title="$t('mango.user.menu.auth')"
            >
              <!-- 具名插槽 -->
              <template v-slot:extendField>
                <avue-crud
                  :table-loading="roleLoading"
                  ref="roleList"
                  @selection-change="handleSelectionChange"
                  :permission="roleTablePermission"
                  :data="roleData"
                  :option="roleOption"
                ></avue-crud>
              </template>
            </button-dialog>
            <el-dropdown @command="handleCommand">
              <span class="el-dropdown-link">
                <i class="el-icon-arrow-down el-icon--right"></i>
              </span>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item :command="scope.row">{{
                  $t('mango.user.menu.resetPass.title')
                }}</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
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
export default class sysUser extends Vue {
  // vue挂载
  http = Vue.prototype.$http

  //########### 请求数据
  roleData: any = []
  search: any = {}
  tableData: any = []
  treeData: any = []
  //########### 弹出窗口角色表格的选项
  roleOption = {
    border: true,
    selection: true,
    selectionFixed: false,
    title: '角色分配',
    size: 'mini',
    align: 'center',
    selectable: (row: any, index: any) => {
      return index !== 0
    },
    menuAlign: 'center',
    column: [
      // { label: '上级菜单', prop: 'parentMe' },
      {
        label: '角色名称',
        prop: 'roleName',
      },
      {
        label: '角色编码',
        prop: 'roleCode',
      },
    ],
  }
  //########### 弹出窗口选项
  crudOption = {
    column: [
      {
        label: '用户名',
        prop: 'loginCode',
        placeholder: '请输入姓名',
        disabled: true,
      },
      {
        label: '昵称',
        prop: 'userName',
        disabled: true,
      },
    ],
  }
  //########### 表格按钮权限

  async checkAuth() {
    this.permission.delBtn = await this.$store.dispatch(
      'checkAuth',
      'sys:user:delete'
    )
    this.permission.addBtn = await this.$store.dispatch(
      'checkAuth',
      'sys:user:add'
    )
    this.permission.menu = await this.$store.dispatch(
      'checkAuth',
      'sys:user:edit'
    )
  }
  // 弹窗默认没有任何按钮
  roleTablePermission = {
    delBtn: false,
    addBtn: false,
    menu: false,
  }

  permission = {
    delBtn: false,
    addBtn: false,
    menu: false,
  }
  //########### 展示表格选项
  option = {
    excelBtn: true,
    viewBtn: true,
    dialogClickModal: false,
    height: '60vh',
    // sortable: true,
    // selection: true,
    menuWidth: 210,
    index: true,
    size: 'mini',
    dialogDrag: true,
    searchShow: false,
    column: [
      {
        rules: [
          {
            required: true,
            message: '',
            trigger: 'blur',
          },
        ],
        label: '用户编码',
        prop: 'userCode',
        editDisplay: false,
      },
      {
        rules: [
          {
            required: true,
            message: '',
            trigger: 'blur',
          },
        ],
        label: '登陆账号',
        prop: 'loginCode',
        value: 'user',
        searchSpan: 8,
        search: true,
        searchRules: [
          {
            required: true,
            message: '请输入账号名称',
            trigger: 'blur',
          },
        ],
      },
      {
        rules: [
          {
            required: true,
            message: '',
            trigger: 'blur',
          },
        ],
        value: '123456',
        label: '登陆密码',
        overHidden: true,
        editDisplay: false,
        showColumn: false,
        viewDisplay: false,
        prop: 'password',
      },
      {
        rules: [
          {
            required: true,
            message: '',
            trigger: 'blur',
          },
        ],
        label: '用户昵称',
        prop: 'userName',
      },
      {
        label: '手机号',
        overHidden: true,
        prop: 'mobile',
        searchSpan: 8,
        search: true,
        searchRules: [
          {
            required: true,
            message: '请输入手机号',
            trigger: 'blur',
          },
        ],
      },
      {
        label: '邮箱',
        overHidden: true,
        prop: 'email',
      },
      {
        label: '创建时间',
        prop: 'createTime',
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
        label: '账号状态',
        prop: 'status',
        formslot: true,
        labelslot: true,
        value: 1,
        slot: true,
        addDisplay: false,
        dicData: [
          { label: '禁用', value: 0 },
          { label: '正常', value: 1 },
        ],
        // slot: true,
      },
    ],
  }
  //########### 机构树选项 右键属性
  treeOption = {
    defaultExpandAll: true,
    props: {
      label: 'officeName',
    },
    formOption: {
      labelWidth: 100,
      column: [
        {
          label: 'officeName',
          prop: 'label',
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
  id = ''
  roleLoading = true
  multipleSelection: any = []
  form: any = {}
  i18Pre = 'mango.user.menu.'
  // 分页属性
  page: any = {
    total: 0,
    pageCount: 5,
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
  async init() {
    const { data } = await this.http.get('/pri/role/', {
      prefix: 'core',
    })
    this.roleData = data.datas

    const res = await this.http.get('/pri/office/findOfficeTree', {
      prefix: 'core',
    })
    this.treeData = res.data.datas
  }
  //########### 方法

  handleCommand(row: any) {
    this.$confirm(
      `${this.$t(
        this.i18Pre + 'resetPass.contentPre'
      )}<strong style="color:red;"> <i>${row.userName}</i> </strong>${this.$t(
        this.i18Pre + 'resetPass.contentPro'
      )}`,
      `${this.$t(this.i18Pre + 'resetPass.title')}`,
      {
        confirmButtonText: `${this.$t(this.i18Pre + 'resetPass.confirm')}`,
        cancelButtonText: `${this.$t(this.i18Pre + 'resetPass.cancel')}`,
        type: 'warning',
        dangerouslyUseHTMLString: true,
      }
    )
      .then(async () => {
        await this.http.put(
          `pri/user/password/${row.id}`,
          {},
          { prefix: 'core' }
        )
      })
      .catch(() => {})
  }

  async rowSave(form: any, done: any, loading: any) {
    // 如果机构树存在要转化下格式
    // if (form.officeTree) {
    //   form.officeId = form.officeTree[form.officeTree.length - 1]
    //   form.officeTree = form.officeTree.toString()
    // }

    const res = await this.http.post('pri/user', form, { prefix: 'core' })
    done(form)
  }

  async rowUpdate(form: any, index: any, done: any, loading: any) {
    const res = await this.http.post('pri/user', form, { prefix: 'core' })
    done(form)
    this.onLoad()
  }

  async rowDel(form: any, index: any, done: any, loading: any) {
    this.mixConfirm(async () => {
      await this.http.delete(`pri/user/${form.id}`, { prefix: 'core' })
      done(form)
    }, this.onLoad)
  }

  $refs!: {
    roleList: HTMLFormElement
  }

  toggleSelection(val?: any) {
    this.$refs.roleList.toggleSelection()
    setTimeout(() => {
      if (val) {
        this.$refs.roleList.toggleSelection(val)
        this.roleLoading = false
      }
    }, 500)
  }

  handleSelectionChange(val: any) {
    this.multipleSelection = val
    // console.log(val)
  }

  cellStyle({ row, column, rowIndex, columnIndex }: any) {
    if (columnIndex == 1) {
      if (row.userCode === 'corpAdmin') {
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

  async delFlagChange(row: any) {
    await this.http.post(
      'pri/user',
      { id: row.id, status: row.status },
      { prefix: 'core' }
    )
  }

  //################### 封装的弹窗按钮的弹出数据初始化
  async initData(obj: any) {
    this.roleLoading = true
    this.form = obj

    const { data } = await this.http.get('pri/user/findUserRoles/' + obj.id, {
      prefix: 'core',
    })
    let array: any = []
    data.datas.forEach((el: any) => {
      let ind = this.roleData.findIndex((e: any) => e.roleName === el.roleName)
      if (ind !== -1) {
        array.push(this.roleData[ind])
      }
    })
    // 初始选中按钮
    this.toggleSelection(array)
  }

  //################### 封装的弹窗按钮的属性

  rowRefresh() {
    this.onLoad()
  }
  // 表格加载
  async onLoad() {
    this.loading = true

    let data: any = []
    if (this.id) {
      data = await this.findOfficeUser(this.id)
    } else {
      data = await this.findUser()
    }

    this.page.total = data.datas.total

    setTimeout(() => {
      this.loading = false
      this.tableData = data.datas.records
    }, 200)
  }

  async findUser() {
    const { data } = await this.http.post(
      'pri/user/findPage',
      {
        pageNum: this.page.currentPage,
        pageSize: this.page.pageSize,
        params: {},
      },
      { prefix: 'core' }
    )

    return data
  }

  async findOfficeUser(id: any) {
    const { data } = await this.http.post(
      'pri/user/findOfficeUser/' + id,
      {
        pageNum: this.page.currentPage,
        pageSize: this.page.pageSize,
        params: {},
      },
      { prefix: 'core' }
    )
    return data
  }
  // 角色授权表单提交
  async submit(row: any) {
    const data: any = []
    this.multipleSelection.forEach((e: any) => {
      data.push({
        roleId: e.id,
        userId: this.form.id,
      })
    })
    const res = await this.http.post(`/pri/user/saveRole/${row.id}`, data, {
      prefix: 'core',
    })
  }

  searchSubmit(row: any) {}

  searchChange(params: any, done: any) {
    done()
    this.$message.success(JSON.stringify(params))
  }

  // 节点点击
  nodeClick(data: any) {
    this.id = data.id

    this.onLoad()
  }
}
</script>
<style lang="scss"></style>
