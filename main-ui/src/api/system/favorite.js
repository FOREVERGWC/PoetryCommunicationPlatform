import request from '@/utils/request'

// 查询用户诗词收藏列表
export function listFavorite(query) {
  return request({
    url: '/system/favorite/list',
    method: 'get',
    params: query
  })
}

// 查询用户诗词收藏详细
export function getFavorite(id) {
  return request({
    url: '/system/favorite/' + id,
    method: 'get'
  })
}

// 查询用户诗词收藏详细
export function getFavoriteByPoetryId(poetryId) {
  return request({
    url: '/system/favorite/info/' + poetryId,
    method: 'get'
  })
}

// 新增用户诗词收藏
export function addFavorite(data) {
  return request({
    url: '/system/favorite',
    method: 'post',
    data: data
  })
}

// 修改用户诗词收藏
export function updateFavorite(data) {
  return request({
    url: '/system/favorite',
    method: 'put',
    data: data
  })
}

// 删除用户诗词收藏
export function delFavorite(id) {
  return request({
    url: '/system/favorite/' + id,
    method: 'delete'
  })
}

// 用户诗词收藏、取消
export function doFavorite(poetryId) {
  return request({
    url: '/system/favorite/do/' + poetryId,
    method: 'put'
  })
}
