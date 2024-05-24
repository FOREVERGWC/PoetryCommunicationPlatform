import {
  get,
  put,
  post
} from '../utils/util'

export const getReplyList = (data) => {
  return get("/system/reply/list", data)
}