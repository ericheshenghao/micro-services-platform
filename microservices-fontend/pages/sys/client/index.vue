<template>
  <div>
    <TableCrud
      :option="option"
      :loadDataFun="loadDataFun"
      :searchFun="searchFun"
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

      <template v-slot:authorizedGrantTypesForm="{ record }">
        <a-select
          v-model="record.authorizedGrantTypes"
          :show-arrow="true"
          mode="multiple"
          :defaultValue="record.authorizedGrantTypes"
          style="width: 100%"
          placeholder="请选择"
        >
          <a-select-option v-for="type in grantTypes" :key="type" :value="type">
            {{ type }}
          </a-select-option>
        </a-select>
      </template>

      <template v-slot:scopeForm="{ record }">
        <a-select
          v-model="record.scope"
          :show-arrow="true"
          mode="multiple"
          :defaultValue="record.scope"
          style="width: 100%"
          placeholder="请选择"
        >
          <a-select-option v-for="scope in scopes" :key="scope" :value="scope">
            {{ scope }}
          </a-select-option>
        </a-select>
      </template>
    </TableCrud>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'

import { getClientList } from '@/api/client'
@Component({})
export default class Client extends Vue {
  grantTypes = [
    'authorization_code',
    'refresh_token',
    'implicit',
    'client_credentials',
    'password',
    'password_code',
  ]

  scopes = ['read', 'write']

  /** 配置分页参数 */

  option: any = {
    rowSelection: true,
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
        title: '客户端名称',
        dataIndex: 'clientId',
      },
      {
        title: '客户端密码',
        dataIndex: 'clientSecret',
        ellipsis: true,
      },
      {
        title: '授权类型',
        dataIndex: 'authorizedGrantTypes',
        formSlots: true,
        ellipsis: true,
      },
      {
        title: '回调地址',
        dataIndex: 'redirectUrl',
      },
      {
        title: '续签有效时间',
        dataIndex: 'refreshTokenValiditySeconds',
      },
      {
        title: '授权域',
        dataIndex: 'scope',
        formSlots: true,
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

  async mounted() {}

  loadDataFun = async (parameter: any) => {
    const requestParameters = Object.assign({}, parameter)
    const res = await getClientList(requestParameters)
    return {
      records: res.datas.records,
      pagination: {
        total: res.datas.total,
      },
    }
  }

  searchFun = (pageInfo: any, parameter: any) => {}

  async rowSave(row: any, done: any) {
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

  rowRefresh() {}
}
</script>

<style lang="scss" scoped></style>
