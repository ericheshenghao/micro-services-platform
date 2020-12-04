import createPersistedState from 'vuex-persistedstate'

export default ({ store }) => {
  createPersistedState({
    key: 'current_session',
    paths: ['modules.user', 'modules.app'],
  })(store)
}
