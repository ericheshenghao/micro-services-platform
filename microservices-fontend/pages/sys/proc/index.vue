<template>
  <div>
    <a-button type="primary">全部</a-button>
    <a-button type="primary">我发起的</a-button>
    <a-button type="primary">待审批</a-button>
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
import { changeStatus } from '@/api/user'
import { getActivitiList } from '@/api/activiti'
import { mapGetters, mapState } from 'vuex'
@Component({
  computed: mapGetters(['userInfo']),
})
@Component({})
export default class SysProc extends Vue {
  userInfo: any = {}
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
        title: '流程key',
        dataIndex: 'processKey',
        addHide: true,
        editHide: true,
        search: true,
      },
      {
        title: '流程name',
        dataIndex: 'processName',
        needTotal: true,
        // customRender: (text) => text + ' 次',
      },

      {
        title: '流程状态',
        value: true,
        dataIndex: 'processState',
      },
      {
        title: '发起人编码',
        dataIndex: 'userCode',
      },
      {
        title: '发起人',
        dataIndex: 'username',
      },
      {
        title: '审核人编码',
        dataIndex: 'procCurrNodeUserCode',
      },

      {
        title: '当前审核人',
        dataIndex: 'procCurrNodeUserName',
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

  loadDataFun = (parameter: any) => {
    parameter.params = { userCode: this.userInfo.userCode }
    return getActivitiList(parameter)
      .then((res: any) => {
        return {
          records: res.data.records,
          pagination: {
            total: res.data.total,
          },
        }
      })
      .catch(() => {
        return {}
      })
  }

  searchFun = (pageInfo: any, parameter: any) => {
    return getActivitiList({
      pageSize: pageInfo.pageSize,
      params: parameter,
    })
      .then((res: any) => {
        return {
          records: res.data.records,
          pagination: {
            total: res.data.total,
            current: res.data.current,
          },
        }
      })
      .catch(() => {
        return {}
      })
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

  async delBatch(row: any, done: any) {
    done()
  }

  rowRefresh() {}
}
</script>

<style lang="scss" scoped></style>
