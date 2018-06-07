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
				var emptytxt = "<p>您还没有正在进行的项目，快去项目市场挑选心仪的项目吧~</p>";
				$("#projects").append(emptytxt);
				return;
			}
			for(let i=0 ; i<len ; i++){
				var number = list[i].length%10;
				var ids = list[i].split("_");
				//pubid_pjid
				var pubid = ids[0];
				var pjid = ids[1];
				var description = ids[2];
				var progress =  ids[3];
				
				var	finish = "<img id='"+pubid+"_"+pjid+"' src='/pic/projects/push.png' class='pushicon'  onClick='pushProject(this)'/>";//是否已完成
				
				
				var txt = " <div class='single-member effect-3'  id='"+pubid+"_"+pjid+"'>"+
		            	"<div class='member-image'>"+
		                	"<img src='/pic/projects/0"+number+".png' alt='Member'>"+
		                "</div>"+
		                "<div class='member-info'>"+
		                	"<h3>"+pjid+"</h3>"+
		                    "<h5>"+description+"</h5>"+
		                    "<p> 发布人："+pubid+"</p>"+
		                    "<div class='social-touch' onClick='chooseProject(this)' id='"+pubid+"_"+pjid+"' >"+
					             "<img class='pjicons' src='/pic/projects/work.png'></img>"+
					             "查看详情"+
		                    "</div>"+
					        "<h5>"+progress+" %  ";
					
		          
				
				if(progress==="100"){
					
					txt = txt+finish;
				}
				
				txt = txt+"</h5>"+ "</div>"+
		                     "</div>";
				
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

function pushProject(that){
	//提交项目
	var id = that.id;
	var ids = id.split("_");//pubid_pjid
	var userId = getCookie("userId");
	var publisherId = ids[0];
	var projectId = ids[1];
	$.ajax({
		url: "/myProjects/pushProject/" +userId+ "/"+publisherId+"/"+projectId,
		type: "get",
		success: function(){
			alert("提交成功！");
			
		}
	});
}

