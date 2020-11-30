const oss = require('ali-oss')

const store = oss({
  accessKeyId: 'LTAI4GDTDQm93qwQFzu9nz8a',
  accessKeySecret: 'IoTPd2UEd7Sx9i1USc1cfrT44C1VZQ',
  bucket: 'mango-sound',
  region: 'oss-cn-hangzhou',
})

export default store
