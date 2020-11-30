<template>
  <div class="bg-white h-100">
    <el-container class="pt-3 px-3">
      <el-main>
        <div class="px-3 py-2">
          <el-breadcrumb class="py-2" separator-class="el-icon-arrow-right">
            <el-breadcrumb-item>字典管理</el-breadcrumb-item>
          </el-breadcrumb>

          <avue-crud
            :page.sync="page"
            :table-loading="loading"
            :before-open="beforeOpen"
            @refresh-change="rowRefresh"
            @on-load="onLoad"
            @row-update="rowUpdate"
            @row-save="rowSave"
            :permission="permission"
            :data="tableData"
            :option="option"
            v-model="form"
          >
            <template slot="statusForm" slot-scope="scope">
              <el-radio-group v-model="scope.row.status">
                <el-radio label="0"> 正常 </el-radio>
                <el-radio label="1"> 删除 </el-radio>
                <el-radio label="2"> 停用 </el-radio>
              </el-radio-group>
            </template>

            <template slot="isSysForm" slot-scope="scope">
              <el-radio-group v-model="scope.row.isSys">
                <el-radio label="1"> 是 </el-radio>
                <el-radio label="0"> 否 </el-radio>
              </el-radio-group>
            </template>

            <template v-if="type == 'edit'" slot="dictLabelForm">
              <avue-crud
                :table-loading="subloading"
                :option="subOption"
                :data="subData"
              >
                <template slot="menu" slot-scope="scope">
                  <button-dialog
                    class="pl-1"
                    icon="el-icon-check"
                    size="mini"
                    type="text"
                    :data="subform"
                    :option="crudOption"
                    @click="initData(scope.row)"
                    @submit="submit"
                    :title="`新增下级标签`"
                  >
                    <template slot="dictParentForm">
                      <!-- <el-cascader
          
                  v-model="subform.dictParent"
                  collapse-tags
                  :props="casProps"
                  :options="treeData"
                  clearable
                ></el-cascader> -->
                    </template>
                  </button-dialog>
                </template>

                <template slot="isSysForm" slot-scope="scope">
                  <el-radio-group v-model="scope.row.isSys">
                    <el-radio label="1"> 是 </el-radio>
                    <el-radio label="0"> 否 </el-radio>
                  </el-radio-group>
                </template>
              </avue-crud>
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
export default class sysRole extends Vue {
  http = Vue.prototype.$http
  loading = true
  subloading = true
  tableData: any = []
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
    delBtn: '',
    addBtn: '',
    menu: '',
  }

  type = 'add'
  subData = []
  form: any = {}
  subform = {}

  submit() {}
  initData(row: any) {
    console.log(row)
    let obj: any = {}
    obj.dictParent = row.dictLabel
    obj.treeSort = 30
    obj.isSys = '1'
    this.subform = obj
  }
  crudOption = {
    column: [
      {
        label: '上级字典',
        prop: 'dictParent',
        formslot: true,
      },
      {
        label: '字典标签',
        prop: 'dictLabel',
      },

      {
        label: '排序号',

        prop: 'treeSort',
      },
      {
        label: '字典键值',
        prop: 'dictValue',
      },
      {
        label: '系统内置',
        prop: 'isSys',

        formslot: true,
        labelslot: true,
        slot: true,
      },
    ],
  }

  async beforeOpen(done: any, type: any) {
    this.subloading = true
    done()
    if (type === 'add') {
      this.type = 'add'
      this.subData = []
      this.subloading = false
    }
    if (type === 'edit') {
      this.type = 'edit'
      const { data } = await this.http.post(
        'pri/sysDictType/findKeyByType',
        {
          dictType: this.form.dictType,
        },
        { prefix: 'core' }
      )
      setTimeout(() => {
        this.subData = data.data
        this.subloading = false
      }, 500)
    }
  }

  subOption = {
    // defaultExpandAll: true,
    rowKey: 'dictCode',
    column: [
      {
        label: '字典标签',
        prop: 'dictLabel',
      },
      {
        label: '字典键值',
        prop: 'dictValue',
      },
      {
        label: '排序号',
        value: 30,
        prop: 'treeSort',
      },
      {
        label: '系统内置',
        prop: 'isSys',
        value: '1',
        formslot: true,
        labelslot: true,
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
      {
        label: '更新时间',
        editDisplay: false,
        prop: 'updateDate',
      },
    ],
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
        label: 'Id',
        overHidden: true,
        addDisplay: false,
        editDisplay: false,
        prop: 'id',
      },
      {
        label: '字典名称',

        prop: 'dictName',
      },
      {
        label: '字典类型',
        prop: 'dictType',
        editDisabled: true,
      },
      {
        label: '系统字典',
        prop: 'isSys',
        formslot: true,

        value: '1',
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
      {
        label: '状态',
        prop: 'status',
        formslot: true,

        value: '0',
        dicData: [
          {
            label: '正常',
            value: '0',
          },
          {
            label: '失效',
            value: '1',
          },
        ],
      },
      {
        label: '创建人',
        editDisplay: false,
        addDisplay: false,
        prop: 'createBy',
      },
      {
        label: '创建时间',
        editDisplay: false,
        addDisplay: false,
        prop: 'createDate',
      },
      {
        label: '更新人',
        editDisplay: false,
        addDisplay: false,
        prop: 'updateBy',
      },
      {
        label: '更新时间',
        editDisplay: false,
        addDisplay: false,
        prop: 'updateDate',
      },
      {
        label: '',
        prop: 'dictLabel',
        labelWidth: 0,

        span: 24,
        hide: true,
        formslot: true,
      },
    ],
  }

  mounted() {
    this.checkAuth()
  }

  async checkAuth() {
    this.permission.delBtn = await this.$store.dispatch(
      'checkAuth',
      'sys:dict:edit'
    )
    this.permission.addBtn = await this.$store.dispatch(
      'checkAuth',
      'sys:dict:add'
    )
    this.permission.menu = await this.$store.dispatch(
      'checkAuth',
      'sys:dict:edit'
    )
  }

  rowRefresh() {
    this.onLoad()
  }

  async onLoad() {
    this.loading = true
    const { data } = await this.http.post(
      '/pri/sysDictType/findPage',
      {
        pageNum: this.page.currentPage,
        pageSize: this.page.pageSize,
        params: {},
      },
      { prefix: 'core' }
    )
    this.page.total = data.data.totalSize

    setTimeout(() => {
      this.loading = false
      this.tableData = data.data.content
    }, 500)
    console.log(data)
  }

  async rowSave(form: any, done: any, loading: any) {
    setTimeout(() => {
      done(form)
    }, 500)

    console.log(form)
    const res = await this.http.post('pri/sysDictType', form, {
      prefix: 'core',
    })
    console.log(res.data)
  }

  async rowUpdate(form: any, index: any, done: any, loading: any) {
    setTimeout(() => {
      done(form)
      this.onLoad()
    }, 500)
    if (form.sysDept.deptList !== null) {
      form.deptTree = form.sysDept.deptList.toString()
      form.deptId = form.sysDept.deptList[form.sysDept.deptList.length - 1]
    }

    const res = await this.http.post('pri/user/save', form, { prefix: 'core' })
  }
}
</script>
<style lang="scss"></style>
