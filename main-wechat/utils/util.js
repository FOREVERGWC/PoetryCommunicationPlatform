const formatTime = date => {
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  const day = date.getDate()
  const hour = date.getHours()
  const minute = date.getMinutes()
  const second = date.getSeconds()

  return `${[year, month, day].map(formatNumber).join('/')} ${[hour, minute, second].map(formatNumber).join(':')}`
}

export const formatNumber = n => {
  n = n.toString()
  return n[1] ? n : `0${n}`
}
export const baseUrl = 'http://shici.suxitech.cn:8080';

function addPrefix(obj) {
  for (let key in obj) {
    if (typeof obj[key] === 'string' && obj[key].startsWith('/')) {
      obj[key] = baseUrl + obj[key];
    } else if (typeof obj[key] === 'object') {
      obj[key] = addPrefix(obj[key]); // 递归调用处理对象类型的值
    }
  }
  return obj;
}

const request = (url, method, data) => {
  const userInfo = getApp().globalData.userInfo;
  return new Promise((resolve, reject) => {
    wx.request({
      url: baseUrl + url,
      method: method,
      data: data,
      header: {
        Authorization: 'Bearer ' + userInfo.token,
      },
      success(res) {
        let {
          statusCode,
          data
        } = res;
        addPrefix(data)
        if (statusCode >= 200 && statusCode < 300) {
          if (data.code === 401) {
            wx.showToast({
              title: '请先登录！',
            })
            reject(data.message)
          }
          resolve(data);
        } else {
          reject(`网络请求错误，状态码${statusCode}`);
        }
      },
      fail(err) {
        reject(err);
      }
    });
  });
};

export const get = (url, data) => {
  data = {
    ...data,
    pageNum: 1,
    pageSize: 1000,
    orderByColumn: 'create_time',
    isAsc: 'desc'
  }
  return request(url, 'GET', data)
};
export const post = (url, data) => request(url, 'POST', data);
export const put = (url, data) => request(url, 'PUT', data);
export const del = (url, data) => request(url, 'DELETE', data);