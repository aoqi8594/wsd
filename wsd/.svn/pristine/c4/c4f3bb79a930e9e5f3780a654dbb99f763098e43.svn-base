/**
 * 邮箱
 * @param {string} email
 * @returns {Boolean}
 */
export function isEmail(email) {
  const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
  return reg.test(email);
}

/**
 * URL地址
 * @param {string} url
 * @returns {Boolean}
 */
export function isURL(url) {
  const reg = /^(https?|ftp):\/\/([a-zA-Z0-9.-]+(:[a-zA-Z0-9.&%$-]+)*@)*((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9][0-9]?)(\.(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[1-9]?[0-9])){3}|([a-zA-Z0-9-]+\.)*[a-zA-Z0-9-]+\.(com|edu|gov|int|mil|net|org|biz|arpa|info|name|pro|aero|coop|museum|[a-zA-Z]{2}))(:[0-9]+)*(\/($|[a-zA-Z0-9.,?'\\+&%$#=~_-]+))*$/;
  return reg.test(url);
}
/**
 * 手机号码
 * @param {string} mobile
 * @returns {Boolean}
 */
export function isMobile(mobile) {
  return /^1[0-9]{10}$/.test(mobile);
}

/**
 * 电话号码
 * @param {string} phone
 * @returns {Boolean}
 */
export function isPhone(phone) {
  return /^([0-9]{3,4}-)?[0-9]{7,8}$/.test(phone);
}
