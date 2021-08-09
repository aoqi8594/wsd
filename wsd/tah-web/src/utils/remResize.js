/*
 * @Description:
 * @Author:
 * @Date: 
 * @LastEditors: denghong
 * @LastEditTime: 
 */
function fnResize() {
	let designSize = 1920 // 设计图尺寸
	let html = document.documentElement
	let wW = html.clientWidth // 窗口宽度
	let rem = ((wW * 100) / designSize).toFixed(2)
	rem >= 70 && rem <= 100 ? rem = rem : rem > 100 ? rem = 100 : rem = 70
	document.documentElement.style.fontSize = rem + 'px'
}
fnResize()
window.onresize = function () {
	fnResize()
}
