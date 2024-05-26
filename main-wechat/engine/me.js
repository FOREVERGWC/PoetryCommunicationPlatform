import {
  get,
  put,
  post
} from '../utils/util'


// 登陆
export function login(data) {
  return post('/login/wechat', data)
}

// 登陆
export function loginByUserName(data) {
  return post('/login', data)
}

// 登陆
export function getUserInfo() {
  return get('/getInfo')
}
export function updateUserPhone(data) {
  return put(`/system/user`, data)
}