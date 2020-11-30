export default function ({ route, store, redirect }) {
  if (route.path !== '/sys/login') {
    const value = route.fullPath
    const label = route.name
    const meta = route.meta || router.$avueRouter.meta || {}
    const i18n = ''

    store.commit('ADD_TAG', {
      label: label,
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
