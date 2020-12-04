import vue from 'vue'

const api = {
  user: '/user',
  role: '/role',
  service: '/service',
  permission: '/permission',
  permissionNoPager: '/permission/no-pager',
  orgTree: '/org/tree',
}

export default api

export function getUserList(parameter) {
  return vue.prototype.$http({
    url: api.user,
    method: 'get',
    params: parameter,
  })
}

export function getRoleList(parameter) {
  return vue.prototype.$http({
    url: api.role,
    method: 'get',
    params: parameter,
  })
}

export function getServiceList(parameter) {
  return vue.prototype.$http({
    url: api.service,
    method: 'get',
    params: parameter,
  })
}

export function getPermissions(parameter) {
  return vue.prototype.$http({
    url: api.permissionNoPager,
    method: 'get',
    params: parameter,
  })
}

export function getOrgTree(parameter) {
  return vue.prototype.$http({
    url: api.orgTree,
    method: 'get',
    params: parameter,
  })
}

// id == 0 add     post
// id != 0 update  put
export function saveService(parameter) {
  return vue.prototype.$http({
    url: api.service,
    method: parameter.id === 0 ? 'post' : 'put',
    data: parameter,
  })
}

export function saveSub(sub) {
  return vue.prototype.$http({
    url: '/sub',
    method: sub.id === 0 ? 'post' : 'put',
    data: sub,
  })
}
