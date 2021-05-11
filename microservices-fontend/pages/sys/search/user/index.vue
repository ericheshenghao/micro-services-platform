<template>
  <TableCrud
    :option="option"
    :loadDataFun="loadDataFun"
    :searchFun="searchFun"
    :permission="permission"
    @row-save="rowSave"
    @row-del="rowDel"
    @row-update="rowUpdate"
  >
    <template v-slot:serial="{ index }">
      <span>
        {{ index + 1 }}
      </span>
    </template>

    <template v-slot:status="{ text }">
      <span v-html="text"> </span>
    </template>

    <template v-slot:userCode="{ text }">
      <span v-html="text"> </span>
    </template>

    <template v-slot:loginCode="{ text }">
      <span v-html="text"> </span>
    </template>

    <template v-slot:userName="{ text }">
      <span v-html="text"> </span>
    </template>

    <template v-slot:createTime="{ text }">
      <span v-html="text"> </span>
    </template>

    <template v-slot:SearchForm="{ queryParam }">
      <a-col :md="8" :sm="24">
        <a-form-item label="搜索">
          <a-input v-model="queryParam.queryStr" placeholder="" />
        </a-form-item>
      </a-col>
    </template>
  </TableCrud>
</template>
<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
import { userIndices } from '@/api/search'
@Component({
  components: {},
})
export default class userIndex extends Vue {
  option: any = {
    columns: [
      {
        title: '#',
        dataIndex: 'serial',
        addHide: true,
        editHide: true,
        width: '50px',
        scopedSlots: { customRender: 'serial' },
      },
      {
        title: '用户编码',
        dataIndex: 'userCode',
        width: '80px',
        scopedSlots: { customRender: 'userCode' },
      },
      {
        title: '登录账号',
        dataIndex: 'loginCode',
        width: '80px',
        scopedSlots: { customRender: 'loginCode' },
      },
      {
        title: '用户昵称',
        dataIndex: 'userName',
        width: '80px',
        scopedSlots: { customRender: 'userName' },
      },
      {
        title: '用户状态',
        dataIndex: 'status',
        width: '80px',
        scopedSlots: { customRender: 'status' },
      },
      {
        title: '创建时间',
        dataIndex: 'createTime',
        width: '150px',
        ellipsis: true,
        scopedSlots: { customRender: 'userName' },
      },
      {
        title: '更新时间',
        dataIndex: 'lastUpdateTime',
        width: '150px',
        ellipsis: true,
      },
    ],
  }

  permission = {
    addBtn: false,
    searchBtn: true,
  }

  loadDataFun = async (parameter: any) => {
    const res = await userIndices(parameter)
    return {
      records: res.data.records,
      pagination: {
        total: res.data.total,
      },
    }
  }

  /** pageInfo,包含当前分页信息 */
  searchFun = async (pageInfo: any, parameter: any) => {
    const res = await userIndices({
      queryStr: parameter.queryStr,
    })

    return {
      records: res.data.records,
      pagination: {
        total: res.data.total,
        current: res.data.current,
      },
    }
  }

  async rowSave(row: any, done: any) {
    done()
  }

  async rowUpdate(row: any, done: any) {
    done()
  }

  async rowDel(row: any, done: any) {
    done()
  }
}
</script>
<style lang="less" scoped></style>
