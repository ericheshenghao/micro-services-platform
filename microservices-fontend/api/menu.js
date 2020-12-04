import vue from 'vue'

const menuApi = {
  navTree: '/core/pri/menu/findNavTree',
  MenuTree: '/core/pri/menu/findMenuTree',
  saveOrUpdataMenu: '/core/pri/menu',
  delMenu: '/core/pri/menu',
}

export function findNavTree() {
  return vue.prototype.$http({
    url: menuApi.navTree,
    method: 'get',
  })
}

export function findMenuTree() {
  return vue.prototype.$http({
    url: menuApi.MenuTree,
    method: 'get',
  })
}

export function saveOrUpdataMenu(parameter) {
  return vue.prototype.$http({
    url: menuApi.saveOrUpdataMenu,
    method: 'post',
    data: parameter,
  })
}

export function delMenu(parameter) {
  console.log(parameter)
  return vue.prototype.$http({
    url: menuApi.delMenu + `/${parameter}`,
    method: 'delete',
  })
}
