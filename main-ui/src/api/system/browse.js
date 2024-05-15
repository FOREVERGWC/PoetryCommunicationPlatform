import request from '@/utils/request'

// 查询帖子浏览记录列表
export function listBrowse(query) {
  return request({
    url: '/system/browse/list',
    method: 'get',
    params: query
  })
}

// 查询帖子浏览记录详细
export function getBrowse(id) {
  return request({
    url: '/system/browse/' + id,
    method: 'get'
  })
}

// 新增帖子浏览记录
export function addBrowse(data) {
  return request({
    url: '/system/browse',
    method: 'post',
    data: data
  })
}

// 修改帖子浏览记录
export function updateBrowse(data) {
  return request({
    url: '/system/browse',
    method: 'put',
    data: data
  })
}

// 删除帖子浏览记录
export function delBrowse(id) {
  return request({
    url: '/system/browse/' + id,
    method: 'delete'
  })
}
