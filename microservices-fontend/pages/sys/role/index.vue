<template>
  <div>
    <TableCrud
      :option="option"
      :loadDataFun="loadDataFun"
      :scroll="{ x: 1000 }"
      :permission="permission"
      @row-save="rowSave"
      @row-del="rowDel"
      @row-update="rowUpdate"
      @refresh-change="rowRefresh"
    >
      <template v-slot:serial="{ index }">
        <span>
          {{ index + 1 }}
        </span>
      </template>

      <template v-slot:action="{ record }">
        <a-divider type="vertical" />
        <a @click="handlePerms(record)">授权菜单</a>
      </template>
    </TableCrud>

    <a-modal
      title="授权菜单"
      :visible="visible"
      :maskClosable="false"
      :confirm-loading="confirmLoading"
      @ok="handleOk"
      @cancel="handleCancel"
    >
      <a-spin :spinning="confirmLoading">
        <a-tree
          ref="tree"
          checkable
          v-model="checkedKeys"
          :show-line="true"
          :auto-expand-parent="autoExpandParent"
          :replaceFields="replaceFields"
          :tree-data="menuTree"
          @check="onCheck"
        />
      </a-spin>
    </a-modal>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
import { getRoleList } from '@/api/role'
import { findMenuTree } from '@/api/menu'
import { saveRoleMenus, findRoleMenus, saveRole } from '@/api/role'
@Component({})
export default class SysRole extends Vue {
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
        title: '角色编码',
        dataIndex: 'roleCode',
        width: '150px',
      },
      {
        title: '角色名称',
        dataIndex: 'roleName',
        width: '150px',
        rules: [
          {
            required: true,
            message: 'Please input Activity loginCode',
            trigger: 'blur',
          },
        ],
        value: 'xx人员',
      },
      {
        title: '角色排序',
        dataIndex: 'roleSort',
        width: '150px',
        value: 0,
      },

      {
        title: '角色描述',
        ellipsis: true,
        dataIndex: 'description',
        value: '这个角色很懒，没有留下任何描述',
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

  permission = {
    addBtn: true,
    searchBtn: false,
  }

  visible = false
  confirmLoading = false

  loadDataFun = async (parameter: any) => {
    const res = await getRoleList()
    return {
      records: res.data,
      pagination: false,
    }
  }
  replaceFields = { children: 'children', title: 'name', key: 'id' }
  autoExpandParent = true
  menuTree = []
  checkedKeys: any = []
  submitKey: any = []
  roleId = ''

  /**打开授权框 */
  async handlePerms(row: any) {
    this.visible = true
    this.confirmLoading = true

    const res = await findRoleMenus(row.id)
    let menuSet: any = []
    // 只设置最小节点
    res.data.sysMenuList.forEach((e: any) => {
      // 类型为按钮 或者 目标是外链
      if (e.type == 2 || e.target == '_blank') {
        menuSet.push(e.id)
      }
    })
    this.checkedKeys = menuSet
    this.roleId = row.id
    this.confirmLoading = false
  }

  async mounted() {
    const res = await findMenuTree()
    this.menuTree = res.data
  }

  /** 没有选中所有子节点时也带上父节点 */
  onCheck(checkedKeys: any, info: any) {
    this.submitKey = [...checkedKeys, ...info.halfCheckedKeys]
  }

  async handleOk() {
    this.visible = false
    if (this.checkedKeys.length != 0 && this.submitKey.length == 0) {
      return
    }
    console.log(this.submitKey)
    this.mixMessage(saveRoleMenus(this.roleId, this.submitKey), this.roleId)

    this.submitKey = []
  }
  handleCancel() {
    this.visible = false
  }

  async rowSave(row: any, done: Function) {
    await saveRole(row)
    done()
  }

  async rowUpdate(row: any, done: Function) {
    await saveRole(row)
    done()
  }

  rowDel() {}

  rowRefresh() {}
}
</script>

<style lang="scss" scoped></style>
