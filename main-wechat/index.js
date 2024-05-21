import {
  get,
  put,
  post
} from '../utils/util'
// 查询诗词列表
export function listPoetry(query) {
  return get('/system/poetry/list', query)
}

// 查询诗词详细
export function getPoetry(id) {
  return get(`/system/poetry/${id}`)
}

// 阅读诗词
export function readPoetry(id) {
  return put(`/system/poetry/read/${id}`)
}


export function sendComment(data) {
  return post(`/system/comment`, data)
}

export function favoriteChange(poetryId){
   return put(`/system/favorite/do/${poetryId}`)
}