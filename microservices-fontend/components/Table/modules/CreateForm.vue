<template>
  <a-modal
    :title="type == 'add' ? '添加' : '编辑'"
    :width="500"
    :maskClosable="false"
    :visible="visible"
    :confirmLoading="loading"
    @ok="
      () => {
        $emit('ok', type)
      }
    "
    @cancel="
      () => {
        $emit('cancel')
      }
    "
  >
    <a-spin :spinning="loading">
      <a-form-model
        ref="ruleForm"
        :rules="rules"
        v-if="form != null"
        :model="form"
        v-bind="formLayout"
        @submit.native.prevent
      >
        <!-- 添加表单 -->
        <template v-if="type == 'add'">
          <template v-for="column in columns">
            <a-form-model-item
              v-if="!column.addHide && column.dataIndex != 'action'"
              :key="column.id"
              :label="column.title"
              :prop="
                column.validatorAlias ? column.validatorAlias : column.dataIndex
              "
            >
              <a-input
                v-if="!column.formSlots"
                :disabled="column.disabled"
                v-model="form[column.dataIndex]"
              ></a-input>
              <slot
                v-else
                :name="column.dataIndex + 'Form'"
                :record="form"
              ></slot>
            </a-form-model-item>
          </template>
        </template>
        <!-- 修改表单 -->
        <template v-else v-for="column in columns">
          <a-form-model-item
            v-if="!column.editHide && column.dataIndex != 'action'"
            :key="column.id"
            :label="column.title"
            :prop="column.dataIndex"
          >
            <a-input
              v-if="!column.formSlots"
              :disabled="column.disabled"
              v-model="form[column.dataIndex]"
            ></a-input>
            <slot
              v-else
              :name="column.dataIndex + `Form`"
              :record="form"
            ></slot>
          </a-form-model-item>
        </template>
      </a-form-model>
    </a-spin>
  </a-modal>
</template>

<script lang="ts">
// import pick from 'lodash.pick'
import { Vue, Component, Prop } from 'nuxt-property-decorator'

const fields = ['description', 'id']
@Component({})
export default class CreateForm extends Vue {
  @Prop({
    type: Boolean,
    required: true,
  })
  visible!: boolean

  @Prop({
    type: Boolean,
    default: () => false,
  })
  loading!: boolean

  /** 表单 */
  @Prop({
    type: Object,
    default: () => null,
  })
  form!: Object

  /** 列属性，用来生成input框 */
  @Prop({
    type: Array,
    default: [],
  })
  columns!: []

  /** 表单类型 */
  @Prop({
    type: String,
  })
  type!: String

  /** 表单规则 */
  @Prop({
    type: Object,
  })
  rules!: Object

  formLayout = {
    labelCol: {
      xs: { span: 24 },
      sm: { span: 6 },
    },
    wrapperCol: {
      xs: { span: 24 },
      sm: { span: 15 },
    },
  }
}
</script>
