import {
  get,
} from '../utils/util'

export function listNotices(query) {
  return get('/system/notice/list', query)
}
