/**
 * 为fork（market）界面提供逻辑
 */

function getProjectList(){
	//获得market中的项目列表
	
	var userId = getCookie("userId");
	$.ajax({
		url: "/market/getList",
		type: "get",
		data: {
			"userId": userId,
		},
		success: function(data){
//			alert(data);
			if(data !== ""){
                var list = data.split(",");//pjName+"_"+type_disc+"_"+minLevel+"_"+endTime+"_"+pjId
                var len = list.length;
                for(let i=0 ; i<len ; i++){

                    var ids = list[i].split("_");
                    addProject(ids[0],ids[1],ids[2],ids[3],ids[4]);//调用方法
                }
			}
		}
	});
}


function addProject(pjName,type_disc,minLevel,endTime,projectId){

	var txt = " <div class='single-member effect-2'>"+
		            	"<div class='member-image'>"+
		                	"<img src='/market/getProjectOverview/"+projectId+"' alt='Member'>"+
		                "</div>"+
		                "<div class='member-info'>"+
		                	"<h3>"+pjName+"</h3>"+
		                    "<h5>"+type_disc+"</h5>"+
		                    "<p>"+"等级要求: 达到"+minLevel+"级"+"</p>"+
							"<p>"+"结束时间: "+endTime+"</p>"+
		                    "<div class='social-touch'>"+
		                    	"<img class='moreicon' src='/pic/market/more.png' id='viewMore_"+projectId+"' alt='了解更多' onClick='view_detail(this)'>"+
		                    "</div>"+
		                "</div>"+
		           "</div>";
	
	 $("#projects").append(txt);

	
}

function view_detail(that){
	var id = that.id;
	var pjid = id.split("_")[1];

	setCookie("projectId", pjid);

	window.location.href = "market/detail";
   
}







