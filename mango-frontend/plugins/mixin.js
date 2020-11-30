import Vue from 'vue'
/* eslint-disable */
Vue.mixin({
  created: function () {},

  methods: {
    mixConfirm(fun, successCallback) {
      this.$confirm('此操作将删除该条记录' + ', 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(async () => {
          await fun()

          this.$message({
            type: 'success',
            message: '删除成功!',
          })
          setTimeout(() => {
            successCallback()
          }, 200)
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
          })
        })
    },
  },
})
