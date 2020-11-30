<template>
  <span>
    <el-dialog
      :modal-append-to-body="false"
      v-dialogdrag
      :title="$attrs.title || ''"
      :visible.sync="logVisible"
      :width="$attrs.width || `60%`"
    >
      <div slot="title">
        <div class="d-flex jc-between ai-baseline pr-5">
          <div>{{ $attrs.title }}</div>
        </div>
      </div>

      <el-form
        ref="ruleForm"
        :rules="rules"
        label-position="right"
        class="demo-form-inline"
        :model="$attrs.data"
      >
        <el-row>
          <template v-for="(column, index) in $attrs.option.column">
            <el-col :key="index" :span="12">
              <el-form-item
                :label="column.label"
                :prop="column.prop"
                :value="column.value"
                :label-width="formLabelWidth"
              >
                <el-input
                  v-if="!column.formslot"
                  :autocomplete="column.autocomplete || 'off'"
                  :disabled="column.disabled || false"
                  v-model="$attrs.data[column.prop]"
                  :placeholder="`${column.placeholder || ''}`"
                ></el-input>
                <slot
                  v-else
                  :row="$attrs.data"
                  :name="column.prop + `Form`"
                ></slot>
              </el-form-item>
            </el-col>
          </template>
        </el-row>
      </el-form>

      <slot name="extendField"></slot>

      <span slot="footer">
        <el-button @click="resetForm">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </span>
    </el-dialog>
  </span>
</template>
<script lang="ts">
import { Vue, Component } from 'nuxt-property-decorator'
@Component({
  components: {},
})
export default class CrudDialog extends Vue {
  logVisible = false
  formLabelWidth = '80px'
  rules: any = {}

  mounted() {
    const attr: any = this.$attrs.option
    attr.column.forEach((e: any) => {
      if (e.rules) {
        this.rules[e.prop] = e.rules[0]
      }
    })

    console.log(this.rules)
  }

  submit() {
    const ref: any = this.$refs['ruleForm']

    ref.validate((valid: any) => {
      if (valid) {
        this.$emit('submit')
        alert('submit!')
        this.logVisible = false
        // ref.resetFields()
      } else {
        console.log('error submit!!')
        return false
      }
    })
  }

  resetForm() {
    this.logVisible = false
    const ref: any = this.$refs['ruleForm']
    ref.resetFields()
  }
}
</script>
<style lang="scss" scoped></style>
