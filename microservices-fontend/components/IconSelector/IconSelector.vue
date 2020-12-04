<template>
  <div>
    <slot></slot>
    <a-modal
      :footer="null"
      :width="640"
      title="图标选择"
      :visible="visible"
      class="icon-selector"
      @cancel="$emit('off')"
    >
      <div :class="prefixCls">
        <a-tabs v-model="currentTab" @change="handleTabChange">
          <a-tab-pane v-for="v in icons" :tab="v.title" :key="v.key">
            <ul style="height: 350px">
              <li
                v-for="(icon, key) in v.icons"
                :key="`${v.key}-${key}`"
                :class="{ active: selectedIcon == icon }"
                @click="handleSelectedIcon(icon)"
              >
                <a-icon :type="icon" :style="{ fontSize: '36px' }" />
              </li>
            </ul>
          </a-tab-pane>
        </a-tabs>
      </div>
    </a-modal>
  </div>
</template>

<script>
import icons from './icons'

export default {
  name: 'IconSelect',
  props: {
    prefixCls: {
      type: String,
      default: 'ant-pro-icon-selector',
    },
    // eslint-disable-next-line
    value: {
      type: String,
    },
    visible: {
      type: Boolean,
      default: false,
    },
  },
  data() {
    return {
      selectedIcon: this.value || '',
      currentTab: 'directional',
      icons,
    }
  },
  watch: {
    value(val) {
      this.selectedIcon = val
      this.autoSwitchTab()
    },
  },
  created() {
    if (this.value) {
      this.autoSwitchTab()
    }
  },
  methods: {
    handleSelectedIcon(icon) {
      this.selectedIcon = icon
      console.log(this.selectedIcon)
      this.$emit('input', icon)
      this.$emit('off')
    },
    handleTabChange(activeKey) {
      this.currentTab = activeKey
    },
    autoSwitchTab() {
      icons.some(
        (item) =>
          item.icons.some((icon) => icon === this.value) &&
          (this.currentTab = item.key)
      )
    },
  },
}
</script>

<style lang="less" scoped></style>
