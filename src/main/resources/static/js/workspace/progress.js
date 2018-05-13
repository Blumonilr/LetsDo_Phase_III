/**
 * 管理进度条的逻辑
 */

function setProgress(num){
	//num为整数，表示百分数
	$(".progress-bar").css("width", num+"%");
	$(".progress-value").html(num+"%");
}