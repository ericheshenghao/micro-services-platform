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
      <template v-slot:status="{ record }">
        <a-switch
          v-model="record.status"
          checked-children="启用"
          un-checked-children="禁用"
          default-checked
        />
      </template>

      <template v-slot:statusForm="{ record }">
        <a-switch
          v-model="record.status"
          checked-children="启用"
          un-checked-children="禁用"
          default-checked
        />
      </template>

      <template v-slot:roleIdsForm="{ record }">
        <a-select
          v-model="record.roleIds"
          :show-arrow="true"
          mode="multiple"
          :defaultValue="record.roleIds"
          style="width: 100%"
          placeholder="请选择"
        >
          <a-select-option
            v-for="role in roleList"
            :key="role.id"
            :value="role.id"
          >
            {{ role.roleName }}
          </a-select-option>
        </a-select>
      </template>
    </TableCrud>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
import { getUserList, saveUser, deleteById, deleteBatch } from '@/api/user'
import { getRoleList } from '@/api/role'
import { generateUserCode } from '@/utils/util'
@Component({})
export default class SysUser extends Vue {
  roleList = []
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
        title: '用户编码',
        dataIndex: 'userCode',
        addHide: true,
        editHide: true,
        search: true,
      },
      {
        title: '登录账号',
        dataIndex: 'userName',
        needTotal: true,
        // customRender: (text) => text + ' 次',
        rules: [
          {
            required: true,
            message: 'Please input Activity userName',
            trigger: 'blur',
          },
        ],
      },
      {
        title: '用户昵称',
        dataIndex: 'nickName',
        search: true,
      },
      {
        title: '密码',
        value: 123456,
        dataIndex: 'password',
        editHide: true,
        ellipsis: true,
        customRender: () => '****',
      },

      {
        title: '用户手机',
        ellipsis: true,
        dataIndex: 'mobile',
      },
      {
        title: '用户角色',
        dataIndex: 'roleIds',
        formSlots: true,
        // scopedSlots: { customRender: 'roleId' },
      },
      {
        title: '用户状态',
        value: true,
        dataIndex: 'status',
        formSlots: true,
        scopedSlots: { customRender: 'status' },
      },
      {
        title: '创建时间',
        dataIndex: 'createTime',
        addHide: true,
        editHide: true,
        ellipsis: true,
      },
      {
        title: '更新时间',
        dataIndex: 'lastUpdateTime',
        addHide: true,
        editHide: true,
        ellipsis: true,
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

  async mounted() {
    const res = await getRoleList()
    this.roleList = res.data
  }

  loadDataFun = async (parameter: any) => {
    const res = await getUserList(parameter)
    return {
      records: res.data.records,
      pagination: {
        total: res.data.total,
      },
    }
  }

  searchFun = async (pageInfo: any, parameter: any) => {
    const res = await getUserList({
      pageSize: pageInfo.pageSize,
      params: parameter,
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
    row.userCode = generateUserCode()
    const res = await saveUser(row)

    done()
  }

  async rowUpdate(row: any, done: any) {
    const res = await saveUser(row)

    done()
  }

  async rowDel(row: any, done: any) {
    await deleteById(row.id)
    done()
  }

  async delBatch(row: any, done: any) {
    const res = await deleteBatch(row)

    done()
  }

  rowRefresh() {}
}
</script>

<style lang="scss" scoped></style>
