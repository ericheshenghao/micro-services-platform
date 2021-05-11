export default function ({ route, store, redirect }) {
  route.matched.forEach((item, index) => {
    item.meta.title = route.meta[index].title || ''
  })
  if (route.path !== '/login' && route.path !== '/') {
    const value = route.fullPath

    const meta = route.meta || router.$avueRouter.meta || {}
    const i18n = ''
    console.log(route)
    store.commit('modules/menu/ADD_TAG', {
      label: route.query.label,
      value: value,
      params: route.params,
      query: route.query,
      meta: (() => {
        if (!i18n) {
          return meta
        }
        return {
          i18n: i18n,
        }
      })(),
      // group: router.$avueRouter.group || [],
    })
  }
  // console.log(route)
}
