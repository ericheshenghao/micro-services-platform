import PropTypes from 'ant-design-vue/es/_util/vue-types'

import 'ant-design-vue/es/menu/style'
import Menu from 'ant-design-vue/es/menu'
import 'ant-design-vue/es/icon/style'
import Icon from 'ant-design-vue/es/icon'

const {
  Item: MenuItem,
  SubMenu
} = Menu

export const RouteMenuProps = {
  menus: PropTypes.array,
  theme: PropTypes.string.def('dark'),
  mode: PropTypes.string.def('inline'),
  collapsed: PropTypes.bool.def(false),
  i18nRender: PropTypes.oneOfType([PropTypes.func, PropTypes.bool]).def(false),
}

const renderMenu = (h, item, i18nRender) => {
  if (item && !item.hidden) {
    const bool = item.children && !item.hideChildrenInMenu
    return bool ? renderSubMenu(h, item, i18nRender) : renderMenuItem(h, item, i18nRender)
  }
  return null
}

const renderSubMenu = (h, item, i18nRender) => {
 
  return (
    <SubMenu key={item.path} title={(
      <span>
        {renderIcon(h, item.icon)}
        <span>{renderTitle(h, item.name, i18nRender)}</span>
      </span>
    )}>
      {!item.hideChildrenInMenu && item.children.map(cd => renderMenu(h, cd, i18nRender))}
    </SubMenu>
  )
}

const renderMenuItem = (h, item, i18nRender) => {
  // console.log(item)
  // const meta = Object.assign({}, item.meta)
  // const target = meta.target || null
  const CustomTag = item.target=="_blank" ? 'a' : 'router-link'
  const props = { to:{path: item.url,  query: { label: item.name },}  }
  const attrs = { href: item.url, target: item.target }
  // if (item.children && item.hideChildrenInMenu) {
  //   // 把有子菜单的 并且 父菜单是要隐藏子菜单的
  //   // 都给子菜单增加一个 hidden 属性
  //   // 用来给刷新页面时， selectedKeys 做控制用
  //   item.children.forEach(cd => {
  //     cd.meta = Object.assign(cd.meta || {}, { hidden: true })
  //   })
  // }
  return (
    <MenuItem key={item.url}>
      <CustomTag {...{ props, attrs }}>
        {renderIcon(h, item.icon)}
        {renderTitle(h, item.name, i18nRender)}
      </CustomTag>
    </MenuItem>
  )
}

const renderIcon = (h, icon) => {
  if (icon === undefined || icon === 'none' || icon === null) {
    return null
  }
  const props = {}
  typeof (icon) === 'object' ? (props.component = icon) : (props.type = icon)
  return <Icon {...{ props }} />
}

const renderTitle = (h, title, i18nRender) => {
  return <span>{ i18nRender && i18nRender(title) || title }</span>
}

const RouteMenu = {
  name: 'RouteMenu',
  props: RouteMenuProps,
  data () {
    return {
      openKeys: [],
      selectedKeys: [],
      cachedOpenKeys: []
    }
  },
  render (h) {
    const { mode, theme, menus, i18nRender } = this
    const handleOpenChange = (openKeys) => {
      // 在水平模式下时，不再执行后续
      if (mode === 'horizontal') {
        this.openKeys = openKeys
        return
      }
      const latestOpenKey = openKeys.find(key => !this.openKeys.includes(key))
      if (!this.rootSubmenuKeys.includes(latestOpenKey)) {
        this.openKeys = openKeys
      } else {
        this.openKeys = latestOpenKey ? [latestOpenKey] : []
      }
    }

    const dynamicProps = {
      props: {
        mode,
        theme,
        openKeys: this.openKeys,
        selectedKeys: this.selectedKeys
      },
      on: {
        select: menu => {
          this.selectedKeys = menu.selectedKeys
          this.$emit('select', menu)
        },
        openChange: handleOpenChange
      }
    }

    const menuItems = menus.map(item => {
      if (item.hidden) {
        return null
      }
      return renderMenu(h, item, i18nRender)
    })
    return <Menu {...dynamicProps}>{menuItems}</Menu>
  },
  methods: {
    updateMenu () {
      const routes = this.$route.matched.concat()
      const { hidden } = this.$route.meta
      if (routes.length >= 3 && hidden) {
        routes.pop()
        this.selectedKeys = [routes[routes.length - 1].path]
      } else {
        this.selectedKeys = [routes.pop().path]
      }
      const openKeys = []
      if (this.mode === 'inline') {
        routes.forEach(item => {
          item.path && openKeys.push(item.path)
        })
      }

      // this.collapsed ? (this.cachedOpenKeys = openKeys) : (this.openKeys = openKeys)
    }
  },
  computed: {
    rootSubmenuKeys: vm => {
      const keys = []
      vm.menus.forEach(item => keys.push(item.path))
      return keys
    }
  },
  created () {
    this.$watch('$route', () => {
      this.updateMenu()
    })
    this.$watch('collapsed', val => {
      if (val) {
        this.cachedOpenKeys = this.openKeys.concat()
        this.openKeys = []
      } else {
        this.openKeys = this.cachedOpenKeys
      }
    })
  },
  mounted () {
    this.updateMenu()
  }
}

export default RouteMenu
