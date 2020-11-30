import Vue from 'vue'

export const confirm = (that, fun, successCallback) => {
  that
    .$confirm('此操作将删除该条记录' + ', 是否继续?', '提示', {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning',
    })
    .then(async () => {
      await fun()

      that.$message({
        type: 'success',
        message: '删除成功!',
      })
      setTimeout(() => {
        successCallback()
      }, 200)
    })
    .catch(() => {
      that.$message({
        type: 'info',
        message: '已取消删除',
      })
    })
}

export const msg = (that, type, msg) => {
  that.$message[type](msg)
}

export const notify = (that) => {
  that.$notify({
    title: '',
    message: '保存成功',
    position: 'bottom-right',
    type: 'success',
  })
}
