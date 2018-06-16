/**
 * 展示worker正在进行的项目
 */

function loadProjects(){
	var userId = getCookie("userId");
	$.ajax({
		url: "/myProjects/getList/"+userId,
		type: "get",
		success: function(data){
			
			var list = data.split(",");
			var len = list.length;
			if(len===0||list[0]===""){
				//没有project
				var emptytxt = "<p>这里空空哒！</p>";
				$("#projects").append(emptytxt);
				toastr.info("您还没有正在进行的项目，快去项目市场挑选心仪的项目吧~");
				return;
			}
			for(let i=0 ; i<len ; i++){
				var number = list[i].length%10;
				var ids = list[i].split("_");
				//pjid_pjname_pubid_description
				var pubid = ids[2];
				var pjid = ids[0];
				var description = ids[3];
				var pjname =  ids[1];
				
				//var	finish = "<img id='"+pubid+"_"+pjid+"' src='/pic/projects/push.png' class='pushicon'  onClick='terminate_project(this)'/>";//是否已完成
				
				
				var txt = " <div class='single-member effect-3'  id='"+pubid+"_"+pjid+"'>"+
		            	"<div class='member-image'>"+
		                	"<img src='/pic/projects/0"+number+".png' alt='Member'>"+
		                "</div>"+
		                "<div class='member-info'>"+
		                	"<h3>"+pjname+"</h3>"+
		                    "<h5>"+description+"</h5>"+
		                    "<p> 发布人："+pubid+"</p>"+
		                    "<div class='social-touch' onClick='chooseProject(this)' id='"+pubid+"_"+pjid+"' >"+
					             "<img class='pjicons' src='/pic/projects/work.png'></img>"+
					             "查看详情"+
		                    "</div>"+
					        "<h5> 结束项目   "+
                    "<img id='"+pubid+"_"+pjid+"' src='/pic/projects/push.png' class='pushicon'  onClick='terminate_project(this)'/>"+
                    "</h5>"+ "</div>"+
                    "</div>";

				//	txt = txt+finish;

			//	txt = txt+"</h5>"+ "</div>"+
		                 //    "</div>";
				
				$("#projects").append(txt);
			}
		}
	});
}

function chooseProject(that){
	//选择项目开始工作
	var id = that.id;
	var ids = id.split("_");//pubid_pjid
    setCookie("projectId", ids[1]);
    setCookie("publisherId", ids[0]);

    window.location.href= "/myProjects/projects/detail";
}

//抛弃
function viewDone(that){
	//进入已经完成的图片界面
	var id = that.id;
	var ids = id.split("_");//pubid_pjid
	var userId = getCookie("userId");
	
	$.ajax({
		url: "/myProjects/getProject/"+ids[0]+"/"+ids[1],
		type: "get",
		success: function(data){
			//alert(data);
			//alert(data);
			setCookie("projectId", ids[1]);
			setCookie("publisherId", ids[0]);
			
			var datas = data.split(":");//type:requirement
			setCookie("projectType",datas[0]);
			if(datas[0]==="tips"){
				setCookie("tipList", datas[1]);
			}
			else{
				setCookie("tipList", "");
				setCookie("projectRequirement",data[1]);
			}
			
		}
	});
	
	window.location.href = "/workspace/viewDone/"+userId+"/"+ids[1]+"/"+ids[0];
}

//DONE
function terminate_project(that){
	//提交项目
	var id = that.id;
	var ids = id.split("_");//pubid_pjid
	var userId = getCookie("userId");
	var projectId = ids[1];
	$.ajax({
		url: "/myProjects/terminateProject",
		type: "get",
		data: {
			"userId" : userId,
			"projectId" : projectId,
		},
		success: function(){
            toastr.success("已退出该项目！");
            setTimeout("window.location.reload()",2500);//等待2.5秒后刷新界面
		}
	});
}
