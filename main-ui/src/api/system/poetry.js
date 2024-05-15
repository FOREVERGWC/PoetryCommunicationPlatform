import request from '@/utils/request'

// 查询诗词列表
export function listPoetry(query) {
  return request({
    url: '/system/poetry/list',
    method: 'get',
    params: query
  })
}

// 查询诗词详细
export function getPoetry(id) {
  return request({
    url: '/system/poetry/' + id,
    method: 'get'
  })
}

// 新增诗词
export function addPoetry(data) {
  return request({
    url: '/system/poetry',
    method: 'post',
    data: data
  })
}

// 修改诗词
export function updatePoetry(data) {
  return request({
    url: '/system/poetry',
    method: 'put',
    data: data
  })
}

// 阅读诗词
export function readPoetry(id) {
  return request({
    url: `/system/poetry/read/${id}`,
    method: 'put'
  })
}

// 删除诗词
export function delPoetry(id) {
  return request({
    url: '/system/poetry/' + id,
    method: 'delete'
  })
}
