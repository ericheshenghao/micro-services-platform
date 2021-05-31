import vue from 'vue'

const clientApi = {
  clientList: '/uaa/client/findPage',
  client: '/uaa/client',
  resetSecret: '/uaa/client/secret',
}

export function getClientList(parameter) {
  return vue.prototype.$http({
    url: clientApi.clientList,
    method: 'post',
    data: parameter,
  })
}

export function resetSecret(id) {
  return vue.prototype.$http({
    url: clientApi.resetSecret + '/' + id,
    method: 'put',
  })
}

/**
 *
 * @param { clientId, clientSecret, } parameter
 * @returns
 */
export function saveClient(parameter) {
  return vue.prototype.$http({
    url: clientApi.client,
    method: 'post',
    data: parameter,
  })
}

export function delClient(id) {
  return vue.prototype.$http({
    url: clientApi.client + '/' + id,
    method: 'delete',
  })
}

export function putClient(parameter) {
  return vue.prototype.$http({
    url: clientApi.client + '/' + parameter.id,
    method: 'put',
    data: parameter,
  })
}
