<template>
  <div class="block">
    <el-tree
      :data="data"
      show-checkbox
      :default-expand-all="false"
      node-key="id"
      ref="tree"
      :indent="20"
      highlight-current
      :props="defaultProps"
    >
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <span>
          <el-button type="text" size="mini">{{ data.url }}</el-button>
          <el-button type="text" size="mini">{{ data.perms }}</el-button>
        </span>
      </span>
    </el-tree>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Watch, Prop } from 'nuxt-property-decorator'
@Component({
  components: {},
})
export default class MenuTree extends Vue {
  form: any = {}
  data = []
  http = Vue.prototype.$http

  defaultProps = {
    children: 'children',
    label: 'name',
  }
  mounted() {
    this.init()
  }

  async init() {
    const res = await this.http.get('/pri/menu/findMenuTree', {
      prefix: 'core',
    })
    this.data = res.data.datas
  }
}
</script>
<style lang="scss">
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px !important;
}
.el-checkbox {
  padding-right: 8px !important;
}
</style>
