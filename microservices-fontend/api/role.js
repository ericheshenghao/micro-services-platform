import vue from 'vue'

const roleApi = {
  roleList: '/core/pri/role',
  saveRole: '/core/pri/role',
  saveRoleMenus: '/core/pri/role/saveRoleMenus',
  findRoleMenus: '/core/pri/role/findRoleMenus',
}

export function getRoleList() {
  return vue.prototype.$http({
    url: roleApi.roleList,
    method: 'get',
  })
}

export function saveRoleMenus(id, parameter) {
  return vue.prototype.$http({
    url: roleApi.saveRoleMenus + `/${id}`,
    method: 'post',
    data: parameter,
  })
}

export function findRoleMenus(id) {
  return vue.prototype.$http({
    url: roleApi.findRoleMenus + `/${id}`,
    method: 'get',
  })
}

export function saveRole(parameter) {
  return vue.prototype.$http({
    url: roleApi.saveRole,
    method: 'post',
    data: parameter,
  })
}
