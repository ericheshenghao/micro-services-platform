import vue from 'vue'

const tableApi = {
  tableList: '/core/pri/codeGen/findPage',
  generate: '/core/pri/codeGen/generate',
  dbList: '/core/pri/codeGen/dblist',
  sourceList: '/core/pri/codeGen/changeSource',
}

export function getTableList(parameter) {
  return vue.prototype.$http({
    url: tableApi.tableList,
    method: 'post',
    data: parameter,
  })
}

export function generate(parameter) {
  return vue.prototype.$http({
    url: tableApi.generate,
    method: 'post',
    data: parameter,
  })
}

export function getDbList() {
  return vue.prototype.$http({
    url: tableApi.dbList,
    method: 'get',
  })
}

export function changeSource(parameter) {
  return vue.prototype.$http({
    url: tableApi.sourceList,
    method: 'post',
    data: parameter,
  })
}
