import request from '@/utils/request'

// 查询帖子回复列表
export function listReply(query) {
  return request({
    url: '/system/reply/list',
    method: 'get',
    params: query
  })
}

// 查询帖子回复详细
export function getReply(id) {
  return request({
    url: '/system/reply/' + id,
    method: 'get'
  })
}

// 新增帖子回复
export function addReply(data) {
  return request({
    url: '/system/reply',
    method: 'post',
    data: data
  })
}

// 修改帖子回复
export function updateReply(data) {
  return request({
    url: '/system/reply',
    method: 'put',
    data: data
  })
}

// 删除帖子回复
export function delReply(id) {
  return request({
    url: '/system/reply/' + id,
    method: 'delete'
  })
}
