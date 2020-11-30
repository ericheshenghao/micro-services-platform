<template>
  <div>
    <el-tabs v-model="activeTab" type="card" @tab-remove="removeTab" @tab-click="clickTab">
      <el-tab-pane
        v-for="t in worktabs"
        :key="t.path"
        :label="t.path"
        :name="t.path"
        :closable="t.path !== '/'"
      ></el-tab-pane>
    </el-tabs>
  </div>
</template>
<script lang='ts'>
import { Vue, Component } from 'nuxt-property-decorator'
@Component({
  components: {},
})
export default class WorkTab extends Vue {
  activeTab = ''

  get worktabs() {
    return this.$store.state.worktab.list
  }

  created() {
    setTimeout(() => {
      this.activeTab = this.$store.state.worktab.current.name
    }, 500)
  }

  clickTab(tab: any) {
    this.$router.push(this.worktabs[1 * tab.index].path)
  }

  removeTab(path: any) {
    console.log(path)
    this.$store.dispatch('worktabRemove', path)
  }
}
</script>
<style lang='scss' scoped>
</style>