<template>
  <span>
    <el-button
      :icon="$attrs.icon || ''"
      :size="$attrs.size || 'small'"
      :type="$attrs.type || 'text'"
      @click="buttonTapped"
      >{{ $attrs.title || '测试' }}</el-button
    >

    <el-dialog
      :close-on-click-modal="
        $attrs['close-on-click-modal'] == false ? false : true
      "
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
        label-position="right"
        class="demo-form-inline"
        :model="$attrs.data"
      >
        <el-row v-if="$attrs.option">
          <template v-for="(column, index) in $attrs.option.column">
            <el-col :key="index" :span="column.col || 12">
              <el-form-item
                :label="column.label"
                :prop="column.prop"
                :value="column.value"
                :label-width="formLabelWidth"
              >
                <el-input
                  v-if="!column.formslot"
                  :disabled="column.disabled || false"
                  :autocomplete="column.autocomplete || 'off'"
                  :value="column.value"
                  v-model="$attrs.data[column.prop]"
                  :placeholder="column.placeholder || ''"
                ></el-input>
                <!-- 具名插槽，row返回之前传进来的行数据-->
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
        <el-button @click="logVisible = false">取 消</el-button>
        <el-button type="primary" @click="submit">确 定</el-button>
      </span>
    </el-dialog>
  </span>
</template>
<script lang="ts">
import { Vue, Component, Prop } from 'nuxt-property-decorator'

@Component({
  components: {},
})
export default class ButtonDialog extends Vue {
  form = {}
  formLabelWidth = '80px'
  logVisible = false
  // @Prop() form: any

  buttonTapped() {
    this.$emit('click')
    this.logVisible = true
  }

  submit() {
    this.$emit('submit')
    this.logVisible = false
  }
}
</script>
<style lang="scss" scoped></style>
