import {
  get,
  put,
  post,
  del,
} from '../utils/util'
export function listFavorite(query) {
  return get('/system/favorite/list', query)
}

export function deleteFavorite(id) {
  return del(`/system/favorite/${id}`)
}