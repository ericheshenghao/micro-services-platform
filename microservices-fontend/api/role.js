import vue from 'vue'

const roleApi = {
  roleList: '/core/role',
  saveRole: '/core/role',
  delRole: '/core/role',
  saveRoleMenus: '/core/role/saveRoleMenus',
  findRoleMenus: '/core/role/findRoleMenus',
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

export function delRole(id) {
  return vue.prototype.$http({
    url: roleApi.delRole + '/' + id,
    method: 'delete',
  })
}
