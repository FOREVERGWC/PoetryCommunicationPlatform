import {
  get,
  put,
  post
} from '../utils/util'
// 查询帖子列表
export function getPost(id) {
  return get(`/system/posts/${id}`)
}
export function listPosts(query) {
  return get("/system/posts/list", query)
}

export function listMyPosts(query, userId) {
  query = {
    ...query,
    userId
  }
  return get("/system/posts/list", query)
}

export function addPost(data) {
  return post("/system/posts", data);
}

export const reply = (data) => {
  return post('/system/reply', data)
}