/**
 * 为fork（market）界面提供逻辑
 */

function getProjectList(){
	//获得market中的项目列表
	
	var userId = getCookie("userId");
	$.ajax({
		url: "/market/getList/"+userId,
		type: "get",
		success: function(data){
//			alert(data);
			var list = data.split(",");//pubid+"_"+pjid+"_"+startDate+"_"+endDate+"_"+type+"_"+workerLevel
			var len = list.length;
			for(let i=0 ; i<len ; i++){
			
				var ids = list[i].split("_");
				addProject(ids[0],ids[1],ids[2],ids[3],ids[4],ids[5]);//调用方法
			}
		}
	});
}


function addProject(publisherId, projectId,startDate,endDate,type,workerLevel){
	var number = 1;//随机确定一张图片
	number = (publisherId.length*projectId.length)%10;//随机数，共10张
	var txt = " <div class='single-member effect-2'>"+
		            	"<div class='member-image'>"+
		                	"<img src='/pic/market/0"+number+".png' alt='Member'>"+
		                "</div>"+
		                "<div class='member-info'>"+
		                	"<h3>"+projectId+"</h3>"+
		                    "<h5>"+type+"</h5>"+
		                    "<p>"+"等级要求: 达到"+workerLevel+"级"+"</p>"+
							"<p>"+"开始时间: "+startDate+"</p>"+
							"<p>"+"结束时间: "+endDate+"</p>"+
		                    "<div class='social-touch'>"+
		                    	"<img class='moreicon' src='/pic/market/more.png' id='"+publisherId+"_"+projectId+"' alt='了解更多' onClick='view_detail(this)'>"+
		                    "</div>"+
		                "</div>"+
		           "</div>";
	
	 $("#projects").append(txt);

	
}

function view_detail(that){
	var id = that.id;
	var pubid = id.split("_")[0];
	var pjid = id.split("_")[1];

	setCookie("publisherId", pubid);
	setCookie("projectId", pjid);

	window.location.href = "market/detail";
   
}







