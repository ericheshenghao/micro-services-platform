import Vue from 'vue'
import Element from 'element-ui'
import VuePlyr from 'vue-plyr'
import '@smallwei/avue/lib/index.css'
import Router from 'vue-router'
import dayjs from 'dayjs'
import VueAMap from 'vue-amap'

import './util/confrim'

Vue.use(VueAMap)
VueAMap.initAMapApiLoader({
  key: '20bc75bcb6199e0a85986de691a4e5d6',
  plugin: [
    'AMap.Scale',
    'AMap.OverView',
    'AMap.ToolBar',
    'AMap.MapType',
    'AMap.PlaceSearch',
    'AMap.Geolocation',
    'AMap.Geocoder',
    'AMap.MarkerClusterer',
  ],
  v: '1.4.4',
  uiVersion: '1.0',
})

const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location)
}
Vue.use(Element)
Vue.prototype.$dayjs = dayjs

Vue.use(VuePlyr, {
  plyr: {
    fullscreen: { enabled: false },
  },
  emit: ['ended'],
})

// Vue.use(Avue)
