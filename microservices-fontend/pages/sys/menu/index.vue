<template>
  <div>
    <TableCrud
      ref="table"
      :option="option"
      :loadDataFun="loadDataFun"
      @before-open="beforeOpen"
      @row-save="rowSave"
      @row-del="rowDel"
      @row-update="rowUpdate"
      @refresh-change="rowRefresh"
    >
      <!-- 表单插槽 -->
      <template v-slot:typeForm="{ record }">
        <a-radio-group
          v-model="record.type"
          :options="radioOpt"
          @change="onChange(record)"
        />
      </template>

      <template v-slot:parentNameForm="{ record }">
        <a-cascader
          :disabled="record.type == 0"
          expand-trigger="hover"
          :options="data"
          :fieldNames="fieldNames"
          v-model="record.parentArray"
          change-on-select
        />
      </template>

      <template v-slot:nameForm="{ record }">
        <a-select
          mode="combobox"
          option-filter-prop="children"
          v-model="record.name"
        >
          <a-select-option value="查看"> 查看 </a-select-option>
          <a-select-option value="新增"> 新增 </a-select-option>
          <a-select-option value="修改"> 修改 </a-select-option>
          <a-select-option value="删除"> 删除 </a-select-option>
        </a-select>
      </template>

      <template v-slot:urlForm="{ record }">
        <a-input :disabled="record.type != 1" v-model="record.url"></a-input>
      </template>

      <template v-slot:iconForm="{ record }">
        <IconSelector
          @off="visible = false"
          :visible="visible"
          v-model="record.icon"
        >
          <a-input
            :disabled="record.type == 2"
            @click="visible = !visible"
            v-model="record.icon"
          >
            <a-icon slot="addonAfter" :type="`${record.icon || ''}`"
          /></a-input>
        </IconSelector>
      </template>

      <template v-slot:permsForm="{ record }">
        <a-input :disabled="record.type != 2" v-model="record.perms"></a-input>
      </template>

      <!-- 表格插槽 -->
      <template v-slot:type="{ text }">
        <a-badge :status="swithStatus(text)" :text="swithText(text)" />
      </template>
      <template v-slot:icon="{ text }">
        <a-icon v-if="text" :type="text" />
      </template>

      <!-- action 插槽 -->
      <template v-slot:action="{ record }">
        <a-divider type="vertical" />
        <a @click="handleSub(record)">新增下级</a>
      </template>
    </TableCrud>
  </div>
</template>

<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
import { findMenuTree, saveOrUpdataMenu, delMenu } from '@/api/menu'
@Component({})
export default class SysMenu extends Vue {
  option: any = {
    columns: [
      {
        title: '菜单名称',
        dataIndex: 'name',
        formSlots: true,
        width: '130px',
      },
      {
        title: '类型',
        dataIndex: 'type',
        width: '100px',
        formSlots: true,

        scopedSlots: { customRender: 'type' },
      },

      {
        title: '上级菜单',
        width: '100px',
        dataIndex: 'parentName',
        formSlots: true,
      },
      {
        title: '权限标识',
        dataIndex: 'perms',
        width: '100px',
        formSlots: true,
        rules: [
          {
            required: true,
            trigger: 'blur',
            validator: this.validatePerms,
          },
        ],
      },
      {
        title: '菜单图标',
        dataIndex: 'icon',
        width: '80px',
        align: 'center',
        formSlots: true,
        scopedSlots: { customRender: 'icon' },
      },
      {
        title: '链接',
        dataIndex: 'url',
        width: '100px',
        ellipsis: true,
        formSlots: true,
        rules: [
          {
            required: true,
            trigger: 'blur',
            validator: this.validateUrl,
          },
        ],
      },
      {
        title: '目标',
        width: '80px',
        dataIndex: 'target',
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

  radioOpt = [
    { label: '目录', value: 0 },
    { label: '菜单', value: 1 },
    { label: '权限', value: 2 },
  ]

  fieldNames = { label: 'name', value: 'id', children: 'children' }

  //菜单数据
  data = []

  visible = false
  // 目录：0 菜单: 1 权限: 2
  formType = 1
  onChange(record: any) {
    this.formType = record.type
    if (record.type != 1) record.url = null
    if (record.type != 2) record.perms = null
    if (record.type == 2) record.icon = null
    if (record.type == 0) record.parentArray = []
  }
  validateUrl(rule: any, value: any, callback: any) {
    if (this.formType === 1) {
      if (value === '') {
        callback(new Error('请输入链接'))
      } else {
        callback()
      }
    } else {
      callback()
    }
  }

  validatePerms(rule: any, value: any, callback: any) {
    if (this.formType === 2) {
      if (value === '') {
        callback(new Error('请输入权限信息'))
      } else {
        callback()
      }
    } else {
      callback()
    }
  }

  swithText(text: any) {
    switch (text) {
      case 0:
        return '目录'

      case 1:
        return '菜单'

      case 2:
        return '权限'
    }
  }

  swithStatus(text: any) {
    switch (text) {
      case 0:
        return 'processing'

      case 1:
        return 'warning'

      case 2:
        return 'error'
    }
  }

  $refs!: {
    table: HTMLFormElement
  }

  loadDataFun = async (parameter: any) => {
    const res = await findMenuTree()
    return {
      records: res.data,
      pagination: false,
    }
  }

  beforeOpen(type: any, form: any) {
    this.data = this.$refs.table.data
    if (type == 'edit') {
      this.onChange(form)
    }
  }

  /** 增加下级菜单 */
  handleSub(row: any) {
    row.parentArray.push(row.id)
    this.$refs.table.handleAdd({
      icon: row.icon,
      parentArray: row.parentArray,
      type: 1,
    })
  }

  async rowSave(row: any, done: Function) {
    const res = await saveOrUpdataMenu(row)
    done()
  }

  async rowUpdate(row: any, done: Function) {
    const res = await saveOrUpdataMenu(row)
    console.log(res)
    done()
  }

  async rowDel(row: any, done: Function) {
    const res = await delMenu(row.id)
    console.log(res)
    done()
  }

  rowRefresh() {}
}
</script>

<style lang="scss" scoped></style>
