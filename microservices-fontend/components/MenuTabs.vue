<template>
  <div>
    <a-tabs
      v-model="active"
      tab-position="top"
      :hideAdd="true"
      @change="openTag"
      @edit="menuTag"
      type="editable-card"
    >
      <!-- :closable="tagLen !== 1" -->
      <a-tab-pane
        :key="item.value"
        v-for="item in tagList"
        :tab="generateTitle(item)"
        :closable="item.label !== '首页'"
      ></a-tab-pane>
    </a-tabs>
  </div>
</template>
<script lang="ts">
import { Vue, Component, Watch, Prop } from 'nuxt-property-decorator'
import { mapGetters, mapState } from 'vuex'
@Component({
  components: {},
  computed: mapGetters(['tagList', 'tag']),
})
export default class MenuTabs extends Vue {
  active = ''

  tagList: any
  tag: any
  mounted() {
    this.setActive()
    console.log(this.tagList)
  }

  @Watch('tag')
  tagChanged() {
    this.setActive()
  }

  generateTitle(item: any) {
    return item.label == null ? '首页' : item.label
  }

  //激活当前选项
  setActive() {
    this.active = this.tag.value
  }
  menuTag(value: any, action: any) {
    if (action === 'remove') {
      let { tag, key }: any = this.findTag(value)
      this.$store.commit('modules/menu/DEL_TAG', tag)

      console.log(this.tag.value)
      if (tag.value === this.tag.value) {
        tag = this.tagList[key === 0 ? key : key - 1] //如果关闭本标签让前推一个
        console.log(tag)
        this.$router.push(tag.value)
      }
    }
  }
  openTag(item: any) {
    this.$router.push(item)
  }
  closeOthersTags() {
    this.$store.commit('DEL_TAG_OTHER')
  }
  findTag(value: any) {
    let tag, key
    this.tagList.map((item: any, index: any) => {
      if (item.value === value) {
        tag = item
        key = index
      }
    })
    return { tag: tag, key: key }
  }
  closeAllTags() {
    this.$store.commit('DEL_ALL_TAG')
    // this.$router.push({
    //   path: this.$router.$avueRouter.getPath({
    //     src: this.tagWel.value,
    //   }),
    //   query: this.tagWel.query,
    // })
  }
}
</script>
