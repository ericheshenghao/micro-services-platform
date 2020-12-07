import vue from 'vue'

const clientApi = {
  clientList: '/uaa/client/findPage',
}

export function getClientList(parameter) {
  return vue.prototype.$http({
    url: clientApi.clientList,
    method: 'post',
    data: parameter,
  })
}
