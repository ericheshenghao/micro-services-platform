<template>
  <TableCrud
    :option="option"
    :loadDataFun="loadDataFun"
    :rowKey="'index'"
    @row-save="rowSave"
    @row-del="rowDel"
    @row-update="rowUpdate"
  >
    <template v-slot:serial="{ index }">
      <span>
        {{ index + 1 }}
      </span>
    </template>

    <template v-slot:health="{ text }">
      <a-badge :status="getStatus(text)" />{{ text }}
    </template>
  </TableCrud>
</template>
<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
import { searchIndices } from '@/api/search'
@Component({
  components: {},
})
export default class searchIndex extends Vue {
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
        title: '索引健康',
        dataIndex: 'health',

        scopedSlots: { customRender: 'health' },
      },
      {
        title: '索引状态',
        dataIndex: 'status',
      },
      {
        title: '索引名称',
        dataIndex: 'index',
      },
      {
        title: '文档数',
        dataIndex: 'docsCount',
      },
      {
        title: '索引大小',
        dataIndex: 'storeSize',
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

  getStatus(status: any) {
    switch (status) {
      case 'green':
        return 'success'
        break
      case 'yellow':
        return 'warning'
        break
      case 'red':
        return 'error'
        break
      default:
        break
    }
  }

  loadDataFun = (parameter: any) => {
    const requestParameters = Object.assign({}, parameter)
    return searchIndices(requestParameters)
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
}
</script>
<style lang="less" scoped></style>
