import request from '@/router/request_sys'

export function del(keys) {
  return request({
    url: 'auth/online',
    method: 'delete',
    data: keys
  })
}
