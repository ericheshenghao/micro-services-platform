<template>
  <div class="bg-white h-100">
    <el-container class="pt-3 px-3">
      <avue-crud
        :cell-style="cellStyle"
        :permission="permission"
        :table-loading="loading"
        :data="tableData"
        :option="option"
        @row-save="rowSave"
        @row-del="rowDel"
        @row-update="rowUpdate"
        @refresh-change="rowRefresh"
      >
        <template slot-scope="scope" slot="status">
          <el-switch
            :active-value="0"
            :inactive-value="1"
            @change="statusChange(scope.row)"
            v-model="scope.row.status"
            active-color="#13ce66"
            inactive-color="#ff4949"
          >
          </el-switch>
        </template>
        <template slot-scope="scope" slot="menu">
          <button-dialog
            icon="el-icon-check"
            size="mini"
            type="text"
            :data="form"
            :option="crudOption"
            @click="initData(scope.row)"
            @submit="submit(scope.row)"
            :title="'授权菜单'"
          >
            <template v-slot:extendField>
              <MenuTree ref="tree"></MenuTree>
            </template>
          </button-dialog>
        </template>

        <!-- <template slot="statusForm" slot-scope="scope">
          <el-radio-group v-model="scope.row.status">
            <el-radio :label="1">禁用</el-radio>
            <el-radio :label="0">正常</el-radio>
          </el-radio-group>
        </template> -->
      </avue-crud>
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
  dialogFormVisible = false
  menuTree = []
  form: any = {}
  loading = true
  //########### 表格按钮权限
  permission = {
    // delBtn: false,
    // addBtn: false,
    // menu: false,
    switch: false,
  }
  crudOption = {
    column: [
      {
        label: '角色编号',
        prop: 'roleCode',
        disabled: true,
      },
      {
        label: '角色名称',
        prop: 'roleName',
        disabled: true,
      },
    ],
  }

  async submit(row: any) {
    this.loading = true
    const ref: any = this.$refs.tree
    let roleMenus: Array<Object> = []
    ref.$refs.tree.getCheckedNodes(false, true).map((e: any) => {
      roleMenus.push({
        roleId: row.id,
        menuId: e.id,
      })
    })

    const res = await this.http.post(
      `/pri/role/saveRoleMenus`,
      { roleMenus, roleId: row.id },
      {
        prefix: 'core',
      }
    )

    setTimeout(() => {
      this.loading = false
      if (res.data.code === 200) {
        this.$notify({
          title: '',
          message: '保存成功',
          position: 'bottom-right',
          type: 'success',
        })
      }
    }, 500)
  }

  async statusChange(row: any) {
    const res = await this.http.post(
      '/pri/role',
      {
        id: row.id,
        roleCode: row.roleCode,
        status: row.status,
      },
      {
        prefix: 'core',
      }
    )

    console.log(res.data)
  }

  async rowSave(form: any, index: any, done: any, loading: any) {
    const res = await this.http.post('/pri/role', form, { prefix: 'core' })
    setTimeout(() => {
      done(form)
    }, 300)
    this.fetchRoleList()
  }

  async rowUpdate(form: any, index: any, done: any, loading: any) {
    this.rowSave(form, index, done, loading)
  }

  rowRefresh() {
    this.fetchRoleList()
  }

  async rowDel(form: any, index: any, done: any, loading: any) {
    this.mixConfirm(async () => {
      await this.http.delete(`/pri/role/${form.id}`, { prefix: 'core' })
    }, this.fetchRoleList)
  }

  async initData(row: any) {
    this.form = row
    const res = await this.http.get(`/pri/role/findRoleMenus/${row.id}`, {
      prefix: 'core',
    })

    setTimeout(() => {
      let checkedKeys: any = []
      res.data.datas.sysMenuList.map((e: any) => {
        if (e.type == 2) {
          checkedKeys.push(e)
        }
      })

      const tree: any = this.$refs.tree
      tree.$refs.tree.setCheckedNodes(checkedKeys)
    }, 500)
  }

  formLabelWidth = '120px'

  tableData: any = []

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
        rules: [
          {
            required: true,
            message: '',
            trigger: 'blur',
          },
        ],
        label: '角色编码',
        editDisabled: true,
        prop: 'roleCode',
      },
      {
        rules: [
          {
            required: true,
            message: '',
            trigger: 'blur',
          },
        ],
        label: '角色名称',

        prop: 'roleName',
      },
      {
        label: '排序',

        prop: 'roleSort',
        value: 30,
      },
      {
        label: '创建时间',
        prop: 'createTime',
        editDisplay: false,
        addDisplay: false,
      },
      {
        label: '更新时间',
        prop: 'lastUpdateTime',
        editDisplay: false,
        addDisplay: false,
      },

      {
        label: '角色状态',
        prop: 'status',
        dicData: [
          { label: '禁用', value: 1 },
          { label: '正常', value: 0 },
        ],
        // formslot: true,
        labelslot: true,
        value: 0,
        slot: true,
        addDisplay: false,
        editDisplay: false,
        // slot: true,
      },
    ],
  }

  cellStyle({ row, column, rowIndex, columnIndex }: any) {
    if (columnIndex == 2) {
      if (row.roleCode !== 'corpAdmin') {
        return {
          color: 'green',
          fontWeight: 'bold',
          fontSize: '20',
        }
      } else {
        return {
          color: 'red',
          fontWeight: 'bold',
          fontSize: '20',
        }
      }
    }
  }

  mounted() {
    this.fetchRoleList()
  }

  async fetchRoleList() {
    this.loading = true
    const res = await this.http.get('pri/role', { prefix: 'core' })

    setTimeout(() => {
      this.loading = false
      this.tableData = res.data.datas
    }, 500)
  }
}
</script>
<style lang="scss"></style>
