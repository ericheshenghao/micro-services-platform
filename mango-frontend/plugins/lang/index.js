import Vue from 'vue'
import VueI18n from 'vue-i18n'
import Avue from '@smallwei/avue'
import elementEnLocale from 'element-ui/lib/locale/lang/en' // element-ui lang
import elementZhLocale from 'element-ui/lib/locale/lang/zh-CN' // element-ui lang
import enLocale from './en'
import zhLocale from './zh'
import ElementLocale from 'element-ui/lib/locale'

Vue.use(VueI18n)

const messages = {
  en: {
    ...enLocale,
    ...elementEnLocale,
    ...Avue.locale.en,
  },
  zh: {
    ...zhLocale,
    ...elementZhLocale,
    ...Avue.locale.zh,
  },
}

const i18n = new VueI18n({
  locale: 'zh',
  messages,
})

export default ({ app, store }) => {
  app.i18n = i18n

  ElementLocale.i18n((key, value) => app.i18n.t(key, value))
}

Vue.use(Avue, {
  i18n: (key, value) => i18n.t(key, value),
})
