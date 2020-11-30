<template>
  <div class="bg-white h-100">
    <el-container class="pt-3 px-3">
      <avue-crud
        @row-update="rowUpdate"
        :cell-style="cellStyle"
        :row-style="rowStyle"
        @row-save="rowSave"
        @row-del="rowDel"
        @refresh-change="rowRefresh"
        :before-close="beforeClose"
        :table-loading="loading"
        :option="option"
        :data="tableData"
      >
        <template slot="icon" slot-scope="scope">
          <i :class="scope.row.icon" style="font-size: 24px"></i>
        </template>

        <template slot="iconForm" slot-scope="scope">
          <avue-input-icon
            :disabled="scope.row.type == 2"
            v-model="scope.row.icon"
            placeholder="请选择图标"
            :icon-list="iconList"
          ></avue-input-icon>
        </template>

        <template slot="permsForm" slot-scope="scope">
          <el-input
            :disabled="scope.row.type != 2"
            v-model="scope.row.perms"
            placeholder
          ></el-input>
        </template>

        <template slot="urlForm" slot-scope="scope">
          <el-input
            :disabled="scope.row.type != 1"
            v-model="scope.row.url"
          ></el-input>
        </template>

        <template slot="typeForm" slot-scope="scope">
          <el-radio-group
            @change="radioChange(scope.row)"
            v-model="scope.row.type"
          >
            <el-radio :label="0">父菜单</el-radio>
            <el-radio :label="1">菜单</el-radio>
            <el-radio :label="2">权限</el-radio>
          </el-radio-group>
        </template>

        <template slot="parentIdForm" slot-scope="scope">
          <el-cascader
            :disabled="scope.row.type == 0"
            v-model="scope.row.parentId"
            collapse-tags
            :props="casProps"
            :options="treeData"
            clearable
          ></el-cascader>
        </template>

        <template slot-scope="{ size, type, row }" slot="menu">
          <el-button :size="size" :type="type" @click="initData(row)"
            >新增下级菜单</el-button
          >
        </template>
      </avue-crud>

      <!-- 自己的弹窗 -->
      <crud-dialog
        ref="dialog"
        :data="form"
        :option="crudOption"
        @submit="submit"
        :title="'新增下级菜单'"
      >
        <template slot="urlForm" slot-scope="scope">
          <el-input
            :disabled="scope.row.type != 1"
            v-model="scope.row.url"
            placeholder="链接形式：***"
          >
            <template slot="prepend">/sys/</template></el-input
          >
        </template>

        <template slot="typeForm" slot-scope="scope">
          <el-radio-group
            @change="radioChange(scope.row)"
            v-model="scope.row.type"
          >
            <el-radio :label="1">菜单</el-radio>
            <el-radio :label="2">权限</el-radio>
          </el-radio-group>
        </template>

        <template slot="nameForm" slot-scope="scope">
          <el-autocomplete
            class="h-100"
            v-model="scope.row.name"
            :fetch-suggestions="querySearch"
            placeholder="请输入内容"
            :trigger-on-focus="true"
          ></el-autocomplete>
        </template>

        <template slot="parentNameForm" slot-scope="scope">
          <el-cascader
            class="h-100"
            v-model="scope.row.parentId"
            collapse-tags
            :props="casProps"
            :options="treeData"
          ></el-cascader>
        </template>

        <template slot="iconForm" slot-scope="scope">
          <avue-input-icon
            :disabled="scope.row.type == 2"
            v-model="scope.row.icon"
            placeholder="请选择图标"
            :icon-list="iconList"
          ></avue-input-icon>
        </template>

        <template slot="permsForm" slot-scope="scope">
          <el-input
            :disabled="scope.row.type != 2"
            v-model="scope.row.perms"
            placeholder
          ></el-input>
        </template>
      </crud-dialog>
    </el-container>
  </div>
</template>
<script lang="ts">
import config from '~/plugins/config/website.js'
import { confirm } from '~/plugins/util/confrim'
import { Vue, Component, Watch } from 'nuxt-property-decorator'
@Component({
  components: {},
})
export default class MenuIndex extends Vue {
  http = Vue.prototype.$http
  title = '修改菜单信息'
  tableData = []
  treeData = []
  formType = 0
  iconList = [
    {
      label: '基本图标',
      list: config.icon,
    },
    {
      label: '方向图标',
      list: [
        'el-icon-info',
        'el-icon-back',
        'el-icon-arrow-left',
        'el-icon-arrow-down',
        'el-icon-arrow-right',
        'el-icon-arrow-up',
      ],
    },
    {
      label: '符号图标',
      list: ['el-icon-plus', 'el-icon-minus', 'el-icon-close', 'el-icon-check'],
    },
  ]
  form: any = { type: '' }
  loading = true

  casProps = {
    value: 'id',
    label: 'name',
    emitPath: false,
    checkStrictly: true,
    expandTrigger: 'hover',
    // multiple: true,
  }

  operation = [{ value: '查看' }, { value: '编辑' }, { value: '新增' }]

  querySearch(queryString: any, cb: any) {
    var operation = this.operation
    var results = queryString
      ? operation.filter(this.createFilter(queryString))
      : operation
    // 调用 callback 返回建议列表的数据
    cb(results)
  }

  createFilter(queryString: any) {
    return (e: any) => {
      return e.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0
    }
  }

  radioChange(row: any) {
    this.formType = row.type

    if (row.type == 0) {
      row.parentId = ''
    }
    row.url = ''

    row.icon = ''
    row.perms = ''
  }

  initData(row: any) {
    console.log(row)
    const ref: any = this.$refs.dialog
    ref.logVisible = true
    let dto = {
      type: row.type == 0 ? 1 : row.type,
      parentId: row.id,
      orderNum: 30,
      parentIds: row.parentIds + row.id + ',',
      url: '',
      perms: '',
    }
    this.form = dto
  }

  async submit() {
    const res = await this.http.post('/pri/menu/save', this.form, {
      prefix: 'core',
    })

    setTimeout(() => {
      this.fetchMenu()
    }, 300)
  }

  beforeClose(done: any) {
    this.formType = 0
    done()
  }

  validateParentId(rule: any, value: any, callback: any) {
    console.log(this.formType)
    if (this.formType == 1 || this.formType == 2) {
      if (value == '') {
        callback(new Error('请选择上级菜单'))
      } else {
        callback()
      }
    } else {
      callback()
    }
  }

  validatePermission(rule: any, value: any, callback: any) {
    console.log(value)
    if (this.formType == 2) {
      if (value == '') {
        callback(new Error('请输入权限信息'))
      } else {
        callback()
      }
    } else {
      callback()
    }
  }

  crudOption = {
    column: [
      {
        rules: [
          {
            required: true,
            message: '',
            trigger: 'blur',
            validator: this.validateParentId,
          },
        ],
        label: '上级菜单',
        prop: 'parentName',
        formslot: true,
      },
      {
        label: '类型',
        prop: 'type',
        formslot: true,
      },
      {
        rules: [
          {
            required: true,
            message: '请添加菜单名称',
            trigger: 'blur',
          },
        ],
        formslot: true,
        label: '菜单名称',
        prop: 'name',
      },

      {
        label: '链接',
        prop: 'url',
        formslot: true,
      },

      {
        rules: [
          {
            required: true,
            message: '',
            trigger: 'blur',
            validator: this.validatePermission,
          },
        ],
        label: '权限标识',
        prop: 'perms',
        formslot: true,
      },
      {
        label: '选择标签',
        prop: 'icon',
        formslot: true,
      },
    ],
  }

  option = {
    headerAlign: 'center',
    align: 'center',
    border: true,
    height: '70vh',
    size: 'mini',
    dialogDrag: true,
    defaultExpandAll: false,
    column: [
      {
        label: '菜单名称',
        prop: 'name',
        align: 'left',
        width: 200,
        rules: [
          {
            required: true,
            message: '',
            trigger: 'blur',
          },
        ],
      },
      {
        label: '类型',
        prop: 'type',
        formslot: true,
        value: 0,
        editDisplay: false,
        labelslot: true,
        dicData: [
          {
            label: '一',
            value: 0,
          },
          {
            label: '菜单',
            value: 1,
          },
          {
            label: '权限',
            value: 2,
          },
        ],
      },
      {
        label: '链接',
        prop: 'url',
        overHidden: true,
        formslot: true,
        labelslot: true,
      },
      {
        rules: [
          {
            required: true,
            message: '',
            trigger: 'blur',
            validator: this.validateParentId,
          },
        ],
        label: '上级菜单',
        prop: 'parentId',
        formslot: true,
        labelslot: true,
        showColumn: false,
      },
      {
        label: '排序',
        prop: 'orderNum',
        value: 60,
      },
      {
        label: '自定义图标',
        prop: 'icon',
        formslot: true,
        labelslot: true,
        slot: true,
      },
      {
        rules: [
          {
            required: true,
            message: '',
            trigger: 'blur',
            validator: this.validatePermission,
          },
        ],
        label: '权限标识',
        prop: 'perms',
        overHidden: true,
        formslot: true,
        labelslot: true,
      },
    ],
  }

  mounted() {
    this.fetchMenu()
  }

  async fetchMenu() {
    this.loading = true
    const res = await this.http.get('/pri/menu/findMenuTree', {
      prefix: 'core',
    })
    setTimeout(() => {
      this.loading = false
      this.tableData = res.data.datas
      this.treeData = res.data.datas
    }, 300)
  }

  async rowSave(form: any, done: any, loading: any) {
    const res = await this.http.post('/pri/menu/save', form, {
      prefix: 'core',
    })
    done(form)
    this.fetchMenu()
  }

  rowRefresh() {
    this.fetchMenu()
  }

  rowDel(form: any, index: any) {
    this.mixConfirm(async () => {
      const res = await this.http.delete(`/pri/menu/${form.id}`, {
        prefix: 'core',
      })
    }, this.fetchMenu)
  }

  async rowUpdate(form: any, index: any, done: any, loading: any) {
    const res = await this.http.post('/pri/menu/save', form, {
      prefix: 'core',
    })
    setTimeout(() => {
      done(form)
    }, 300)
    this.fetchMenu()
  }

  cellStyle({ row, column, rowIndex, columnIndex }: any) {
    if (columnIndex == 1 || columnIndex == 5) {
      switch (row.type) {
        case 2:
          return {
            color: 'red',
            fontWeight: 'bold',
            fontSize: '20',
          }
          break
        case 1:
          return {
            color: 'green',
            fontWeight: 'bold',
            fontSize: '20',
          }
        default:
          break
      }
    }
  }

  rowStyle({ row, column, rowIndex }: any) {
    // if (row.type == 2) {
    //   return {
    //     backgroundColor: 'rgba(103, 194, 58, 0.07)',
    //   }
    // }
  }
}
</script>
<style lang="scss">
.tableBox {
  th {
    padding: 0 !important;
    height: 15px;
    line-height: 15xp;
  }
  td {
    padding: 0 !important;
    height: 15px;
    line-height: 15xp;
  }
}
</style>
