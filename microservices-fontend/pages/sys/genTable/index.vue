<template>
  <div>
    <TableCrud
      ref="table"
      :option="option"
      :loadDataFun="loadDataFun"
      :rowKey="'tableName'"
      :permission="permission"
      @row-save="rowSave"
      @row-del="rowDel"
      @row-del-batch="delBatch"
      @row-update="rowUpdate"
      @refresh-change="rowRefresh"
    >
      <template v-slot:serial="{ index }">
        <span>
          {{ index + 1 }}
        </span>
      </template>

      <template v-slot:TableHeadLeft>
        <a-dropdown-button v-if="dbList.length > 0">
          {{ dbList[0].url }}
          <a-menu slot="overlay" @click="handleMenuClick">
            <a-menu-item v-for="(db, index) in dbList" :key="index">
              {{ db.url }}
            </a-menu-item>
          </a-menu>
          <a-icon slot="icon" type="database" />
        </a-dropdown-button>
      </template>

      <template v-slot:action="{ record }">
        <a @click="handleSub(record)">代码生成</a>
      </template>
    </TableCrud>

    <LazyCreateForm
      :visible="visible"
      :columns="columns"
      :loading="confirmLoading"
      :form="mdl"
      :rules="rules"
      :type="type"
      @cancel="handleCancel"
      @ok="handleOk"
    >
    </LazyCreateForm>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'

import { getTableList, generate, getDbList, changeSource } from '@/api/tableGen'
@Component({})
export default class Client extends Vue {
  permission = {
    addBtn: false,
    editBtn: false,
    delBtn: false,
    searchBtn: false,
  }
  dbList = []

  mounted() {
    this.getDbList()
  }
  async getDbList() {
    const res = await getDbList()

    this.dbList = res.datas
  }

  option: any = {
    columns: [
      {
        title: '#',
        dataIndex: 'serial',
        addHide: true,
        editHide: true,
        width: 50,
        scopedSlots: { customRender: 'serial' },
      },
      {
        title: '表名',
        dataIndex: 'tableName',
      },
      {
        title: '引擎',
        dataIndex: 'engine',
      },
      {
        title: '创建时间',
        dataIndex: 'createTime',
      },

      {
        title: '操作',
        fixed: 'right',
        dataIndex: 'action',
        width: 150,
        scopedSlots: { customRender: 'action' },
      },
    ],
  }

  mdl: any = {}
  rules = {}
  type = '生成代码'
  confirmLoading = false
  visible = false
  columns = [
    {
      title: '表名',
      dataIndex: 'tableName',
      disabled: true,
    },
    // {
    //   label: '作者',
    //   autocomplete: 'on',
    //   prop: 'author',
    // },
    {
      title: '描述',
      dataIndex: 'comments',
    },
    {
      title: '模块名',
      dataIndex: 'moduleName',
    },
    {
      title: '包名',
      dataIndex: 'packageName',
    },
    {
      title: '表前缀',
      dataIndex: 'tablePrefix',
    },
    // {
    //   title: '生成路径',
    //   dataIndex: 'path',
    // },
  ]

  handleSub(row: any) {
    Object.assign(this.mdl, row)
    this.visible = true
  }

  async handleOk() {
    const data = { tableName: this.mdl.tableName, path: this.mdl.path }
    const res = generate()

    let blob: any = new Blob([res.data], { type: 'application/zip' })
    let filename = this.mdl.tableName + '.zip'
    let link = document.createElement('a')
    link.href = URL.createObjectURL(blob)
    link.download = filename
    document.body.appendChild(link)
    link.click()
    window.setTimeout(function () {
      URL.revokeObjectURL(blob)
      document.body.removeChild(link)
    }, 0)
  }

  handleCancel() {
    this.visible = false
  }

  async handleMenuClick(e: any) {
    const res = await changeSource(this.dbList[e.key])
  }

  loadDataFun = async (parameter: any) => {
    const res = await getTableList(parameter)
    return {
      records: res.datas.records,
      pagination: {
        total: res.datas.total,
      },
    }
  }

  async rowSave(row: any, done: any) {
    console.log(row)
    done()
  }

  async rowUpdate(row: any, done: any) {
    done()
  }

  async rowDel(row: any, done: any) {
    done()
  }

  async delBatch(row: any, done: any) {
    done()
  }

  $refs!: {
    table: HTMLFormElement
  }

  rowRefresh() {}
}
</script>

<style lang="less">
.table-operator button {
  margin-right: 0px;
}
</style>
