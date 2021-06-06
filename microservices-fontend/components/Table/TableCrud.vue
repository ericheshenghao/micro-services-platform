<template>
  <a-card :bordered="false">
    <div class="table-page-search-wrapper">
      <a-form v-if="searchFormShow" layout="inline">
        <a-row :gutter="48">
          <template v-for="column in option.columns">
            <a-col v-if="column.search" :key="column.id" :md="8" :sm="24">
              <a-form-item :label="column.title">
                <a-input
                  v-model="queryParam[column.dataIndex]"
                  placeholder=""
                />
              </a-form-item>
            </a-col>
          </template>

          <slot name="SearchForm" :queryParam="queryParam"></slot>
          <a-col :md="24" :sm="24">
            <span
              class="table-page-search-submitButtons"
              :style="{ 'text-align': 'center', overflow: 'hidden' }"
            >
              <a-button type="primary" @click="handleSearch">查询</a-button>
              <a-button
                style="margin-left: 8px"
                @click="() => (this.queryParam = {})"
                >重置</a-button
              >
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>

    <div class="table-operator d-flex jc-between">
      <div>
        <a-button
          v-if="permissions.addBtn"
          type="primary"
          icon="plus"
          @click="handleAdd"
          >新建</a-button
        >
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <a-menu slot="overlay">
            <a-menu-item @click="handleDelBatch" key="1"
              ><a-icon type="delete" />删除</a-menu-item
            >
            <!-- lock | unlock -->
            <a-menu-item key="2"><a-icon type="lock" />锁定</a-menu-item>
          </a-menu>
          <a-button style="margin-left: 8px">
            批量操作 <a-icon type="down" />
          </a-button>
        </a-dropdown>
        <slot name="TableHeadLeft"></slot>
      </div>

      <div class="table-right-content">
        <a-button
          v-if="permissions.searchBtn"
          @click="searchFormShow = !searchFormShow"
          type="primary"
          shape="circle"
          icon="search"
        />

        <a-button
          v-if="permissions.refetchBtn"
          @click="loadData()"
          type="primary"
          shape="circle"
          icon="reload"
        />
        <slot name="TableHeadRight"></slot>
      </div>
    </div>

    <a-table
      ref="table"
      size="default"
      :rowKey="rowKey"
      :loading="tableLoading"
      :columns="option.columns"
      :data-source="data"
      :pagination="pagination"
      :rowSelection="option.rowSelection ? rowSelection : null"
      :scroll="scroll"
    >
      <!-- 表格插槽，打开scopedSlots和dataIndex即可 -->
      <template
        v-for="column in option.columns"
        :slot="column.scopedSlots ? column.dataIndex : ''"
        slot-scope="text, record, index"
      >
        <template v-if="column.scopedSlots">
          <slot
            :name="column.dataIndex"
            :text="text"
            :record="record"
            :index="index"
          >
          </slot>
        </template>
      </template>
      <!-- 默认的action操作，可以再添加 -->

      <span slot="action" slot-scope="text, record">
        <span v-if="permissions.editBtn">
          <a @click="handleEdit(record)">编辑</a> <a-divider type="vertical"
        /></span>
        <a-popconfirm
          v-if="permissions.delBtn"
          @confirm="handleDel(record)"
          title="确定删除吗？"
        >
          <a-icon slot="icon" type="question-circle-o" style="color: red" />
          <a href="#">删除</a>
        </a-popconfirm>

        <slot name="action" :record="record"> </slot>
      </span>
    </a-table>

    <CreateForm
      ref="createModal"
      :visible="visible"
      :columns="option.columns"
      :loading="confirmLoading"
      :form="mdl"
      :rules="rules"
      :type="type"
      @cancel="handleCancel"
      @ok="handleOk"
    >
      <!-- 表单插槽，打开formSlots和dataIndex即可,命名需加上form -->
      <template
        v-for="column in option.columns"
        :slot="column.formSlots ? column.dataIndex + 'Form' : ''"
        slot-scope="record"
      >
        <!-- 转化为数组 -->
        <template v-if="column.formSlots">
          <slot
            v-if="column.toSet"
            :name="column.dataIndex + `Form`"
            :record="columnToSet(record, column)"
          >
          </slot>
          <slot
            v-else
            :name="column.dataIndex + `Form`"
            :record="record.record"
          >
          </slot>
        </template>
      </template>
    </CreateForm>
    <step-by-step-modal ref="modal" />
  </a-card>
</template>

<script lang="ts">
import { Vue, Component, Prop, Watch } from 'nuxt-property-decorator'
import moment from 'moment'

@Component({})
export default class TableCrud extends Vue {
  @Prop({
    type: Object,
    default: {},
  })
  option: any

  @Prop({
    type: Object,
    default: () => {
      return {}
    },
  })
  permission: any

  defaultPerms = {
    addBtn: true,
    searchBtn: true,
    refetchBtn: true,
    editBtn: true,
    delBtn: true,
  }

  get permissions() {
    return Object.assign(this.defaultPerms, this.permission)
  }

  @Prop({
    type: Function,
  })
  loadDataFun!: Function

  @Prop({
    type: Function,
  })
  searchFun!: Function

  @Prop({
    type: String,
    default: 'id',
  })
  rowKey!: string
  /** 原生属性 */
  @Prop({
    default: () => {
      return { x: 1000 }
    },
  })
  scroll: any

  tableLoading = false

  /** 表格数据 */
  data = []

  searchFormShow = false

  pagination: any = {
    current: 1,
    pageSize: 10,
    defaultCurrent: 1, // 默认当前页数
    defaultPageSize: 10, // 默认当前页显示数据的大小
    showSizeChanger: true,
    total: 0,
    showQuickJumper: true,
    pageSizeOptions: ['10', '20', '40'],
    showTotal: (total: any) => `共 ${total} 条`, // 显示总数
    onShowSizeChange: (current: any, pageSize: any) => {
      this.pagination.current = current
      this.pagination.pageSize = pageSize
      this.loadData()
      // this.searchList.pageNum = current;
      // this.searchList.pageSize = pageSize;
      // var params = JSON.parse(JSON.stringify(this.searchList));
      // params.pageNum = 1;
      // params.pageSize = pageSize;
      //   this.paramsCheck(params);
      //   this.regUserList(params);
      // this.getList()  //显示列表的接口名称
    },
    onChange: (current: any, size: any) => {
      this.pagination.current = current
      this.pagination.pageSize = size
      this.loadData()
      // this.searchList.pageNum = current;
      // this.searchList.pageSize = size;
      // var params = JSON.parse(JSON.stringify(this.searchList));
      // params.pageNum = current;
      // params.pageSize = size;
      //   this.paramsCheck(params);
      //   this.regUserList(params);
      // this.getList()
    }, // 点击页码事件
  }

  mounted() {
    this.loadData()
  }

  async loadData() {
    this.tableLoading = true

    /** 若有查询参数，切换页面始终携带  */
    const res = await this.loadDataFun({
      pageNum: this.pagination.current,
      pageSize: this.pagination.pageSize,
      params: this.queryParam,
    })

    this.handleResult(res)
  }

  async handleSearch() {
    this.tableLoading = true
    const res = await this.searchFun(this.pagination, this.queryParam)
    this.handleResult(res)
  }

  handleResult(res: any) {
    this.tableLoading = false
    if (!res.records) return
    this.data = res.records
    if (res.pagination == false) {
      this.pagination = false
    } else {
      this.pagination.total = res.pagination.total

      if (res.pagination.current) {
        this.pagination.current = res.pagination.current
      }
    }
  }

  /** 默认弹窗表单为添加 */
  type = 'add'

  // create model
  visible = false
  confirmLoading = false
  mdl: any = {}

  // 查询参数
  queryParam = {}
  // 加载数据方法 必须为 Promise 对象

  selectedRowKeys = []
  selectedRows = []

  get rowSelection() {
    return {
      selectedRowKeys: this.selectedRowKeys,
      onChange: this.onSelectChange,
    }
  }

  /** 规则生成 */
  get rules() {
    let rules: any = {}
    this.option.columns.map((e: any) => {
      let rule: any = []
      if (e.rules) {
        e.rules.map((r: any) => {
          rule.push(r)
        })
        if (e.validatorAlias) {
          rules[e.validatorAlias] = rule
        } else {
          rules[e.dataIndex] = rule
        }
      }
    })
    return rules
  }

  /** init参数：外部直接调用handleAdd方法传参 */
  handleAdd(init?: any) {
    /** 表单默认值生成 */

    this.$emit('before-open', 'add')

    let obj: any = {}
    this.option.columns.map((e: any) => {
      if (e.value != undefined) {
        if (typeof e.value === 'function') {
          obj[e.dataIndex] = e.value()
        } else {
          obj[e.dataIndex] = e.value
        }
      }
    })
    /** */

    this.type = 'add'
    this.mdl = { ...obj, ...init }
    this.visible = true
  }
  handleEdit(record: any) {
    this.$emit('before-open', record)
    this.type = 'edit'
    this.visible = true

    this.mdl = { ...record }
  }

  $refs!: {
    createModal: HTMLFormElement
  }

  columnToSet({ record }: any, column: any) {
    const val = record[column.dataIndex]
    if (val != null) {
      if (typeof val == 'string') {
        record[column.dataIndex] = record[column.dataIndex].split(column.toSet)
      }
    }
    return record
  }

  setToString(val: Array<String>, splitType: any) {
    let res = ''

    for (let index = 0; index < val.length; index++) {
      if (index == val.length - 1) res += val[index]
      else res += val[index] + splitType
    }

    return res
  }

  /** 表单验证并提交 */
  handleOk(type: any) {
    const rule = this.$refs.createModal.$refs.ruleForm
    this.confirmLoading = true

    // this.tableLoading = true
    // 表单验证

    rule.validate((valid: any) => {
      if (valid) {
        this.visible = false

        this.option.columns.forEach((e: any) => {
          if (e.saveType) {
            this.mdl[e.dataIndex] = this.setToString(
              this.mdl[e.dataIndex],
              e.toSet
            )
          }
        })
        if (type == 'add') {
          // 属性还原
          this.$emit('row-save', this.mdl, this.successCallBack)
        } else {
          this.$emit('row-update', this.mdl, this.successCallBack)
        }
      } else {
        console.log('error submit!!')
        return false
      }
    })
    setTimeout(() => {
      this.confirmLoading = false
    }, 300)
  }

  successCallBack() {
    setTimeout(() => {
      this.loadData()
    }, 500)
    this.$message.info('操作成功')
  }

  /** 取消操作 */
  handleCancel() {
    this.$emit('before-close', this.type, this.mdl)
    this.visible = false
    this.mdl = {}
    const form = this.$refs.createModal.$refs.ruleForm
    form.resetFields() // 清理表单数据（可不做）
  }
  /** 删除操作 */
  handleDel(record: any) {
    this.$emit('row-del', record, this.successCallBack)
  }
  /** 批量删除 */
  handleDelBatch() {
    this.$confirm({
      title: '确定要删除这些数据吗?',
      content: '',
      okText: '是的',
      okType: 'danger',
      cancelText: '我再想想',
      onOk: () => {
        this.$emit('row-del-batch', this.selectedRowKeys, this.successCallBack)
      },
      onCancel() {
        console.log('Cancel')
      },
    })
  }

  onSelectChange(selectedRowKeys: any, selectedRows: any) {
    this.selectedRowKeys = selectedRowKeys
    this.selectedRows = selectedRows
  }

  resetSearchForm() {
    this.queryParam = {
      date: moment(new Date()),
    }
  }
}
</script>
