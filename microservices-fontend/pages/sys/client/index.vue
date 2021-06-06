<template>
  <div>
    <TableCrud
      :option="option"
      :loadDataFun="loadDataFun"
      :scroll="{ x: 1000 }"
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

      <template v-slot:grantTypesForm="{ record }">
        <a-select
          v-model="record.grantTypes"
          :show-arrow="true"
          mode="multiple"
          style="width: 100%"
          placeholder="请选择授权类型"
        >
          <a-select-option v-for="type in grantTypes" :key="type" :value="type">
            {{ type }}
          </a-select-option>
        </a-select>
      </template>

      <template v-slot:scopesForm="{ record }">
        <a-select
          v-model="record.scopes"
          :show-arrow="true"
          mode="multiple"
          :defaultValue="record.scopes"
          style="width: 100%"
          placeholder="请选择"
        >
          <a-select-option
            v-for="scope in DefaultScopes"
            :key="scope"
            :value="scope"
          >
            {{ scope }}
          </a-select-option>
        </a-select>
      </template>

      <template v-slot:action="{ record }">
        <a-divider type="vertical" />
        <a-popconfirm @confirm="handleReset(record)" title="确定要重置密码？">
          <a-icon slot="icon" type="question-circle-o" style="color: red" />
          <a>重置密码</a>
        </a-popconfirm>
      </template>
    </TableCrud>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'

import {
  getClientList,
  saveClient,
  delClient,
  putClient,
  resetSecret,
} from '@/api/client'
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

  DefaultScopes = ['READ', 'WRITE']

  permission = {
    addBtn: true,
    searchBtn: false,
  }

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
        rules: [
          {
            required: true,
            trigger: 'blur',
            validator: this.validateName,
          },
        ],
      },
      {
        title: '客户端密码',
        dataIndex: 'clientSecret',
        editHide: true,
        ellipsis: true,
        value: 123456,
        customRender: () => '****',
      },
      {
        title: '授权类型',
        dataIndex: 'grantTypes',
        toSet: ',',
        saveType: String,
        formSlots: true,
        ellipsis: true,
      },
      {
        title: '回调地址',
        dataIndex: 'redirectUrl',
      },
      {
        title: 'token有效时间',
        value: 12000,
        dataIndex: 'accessTokenValiditySeconds',
      },
      {
        title: '续签有效时间',
        value: 12000,
        dataIndex: 'refreshTokenValiditySeconds',
      },
      {
        title: '授权域',
        toSet: ',',
        saveType: String,
        dataIndex: 'scopes',
        formSlots: true,
      },

      {
        title: '操作',
        fixed: 'right',
        dataIndex: 'action',
        width: 200,
        scopedSlots: { customRender: 'action' },
      },
    ],
  }

  validateName(rule: any, value: any, callback: any) {
    if (value === '' || value == undefined) {
      callback(new Error('请输入客户端名称'))
    } else {
      callback()
    }
  }
  loadDataFun = (parameter: any) => {
    const requestParameters = Object.assign({}, parameter)
    return getClientList(requestParameters)
      .then((res: any) => {
        return {
          records: res.data.records,
          pagination: {
            total: res.data.total,
          },
        }
      })
      .catch((error: any) => {
        return {}
      })
  }

  handleReset(row: any) {
    resetSecret(row.id).then(() => {
      this.$message.success({
        content: '密码重置成功!',
        duration: 2,
      })
    })
  }

  async rowSave(row: any, done: any) {
    saveClient(row)
    done()
  }

  async rowUpdate(row: any, done: any) {
    putClient(row)
    done()
  }

  async rowDel(row: any, done: any) {
    delClient(row.id)
    done()
  }

  async delBatch(row: any, done: any) {
    done()
  }

  rowRefresh() {}
}
</script>

<style lang="scss" scoped></style>
