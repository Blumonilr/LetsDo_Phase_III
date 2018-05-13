/**
 * 为区域标注提供加载requirement和提交标记的服务
 */

function setRequirement(){
	//加载要求
	var projectId = getCookie("projectId");
	var publisherId = getCookie("publisherId");
	$.ajax({
		url:  "/myProjects/getProjectRequirement/"+publisherId+"/"+projectId,
		type: "get",
		success: function(data){
			$("#requirementArea").append(data);
		}
	});
}

function submitTag(){
	//提交标签
	//这种标记只有标签
	var type = "total";
	var userId = getCookie("userId");
	var projectId = getCookie("projectId");
	var publisherId = getCookie("publisherId");
	var pictureId = getCookie("pictureId");
	var width = getCookie("pictureWidth");
	var height = getCookie("pictureHeight");
	var remark = $("#userInput").val();
	if(remark===""){
		alert("请输入标注后再提交哦");
	}
	else{
		
		$.ajax({
			url: "/workspace/submit/"+type+"/"+userId+"/"+projectId+"/"+publisherId+"/"+pictureId,
			type: "post",
			data: {
				'base64' : "",
				'remark' : remark,
				'width' : width,
				'height' : height,
			},
			success: function(){
				alert("上传成功");
				clearTips();//清空tip
				getNewPicture();//这个方法在getPicture.js里面
			}
		});
	}
}

function submitTag_edit(){
	//提交修改的标记
	var type = "total";
	var userId = getCookie("userId");
	var projectId = getCookie("projectId");
	var publisherId = getCookie("publisherId");
	var pictureId = getCookie("pictureId");
	var width = getCookie("pictureWidth");
	var height = getCookie("pictureHeight");
	var remark = $("#userInput").val();
	if(remark===""){
		alert("请输入标注后再提交哦");
	}
	else{
		
		$.ajax({
			url: "/workspace/submit/"+type+"/"+userId+"/"+projectId+"/"+publisherId+"/"+pictureId,
			type: "post",
			data: {
				'base64' : "",
				'remark' : remark,
				'width' : width,
				'height' : height,
			},
			success: function(){
				alert("上传成功");
				clearTips();//清空tip
				history.back();//返回上个界面
			}
		});
	}
}

function setPreviousTag(){
	//设置以前的输入
	var userId = getCookie("userId");
	var projectId =getCookie("projectId");
	var publisherId = getCookie("publisherId");
	var projectType = getCookie("projectType");
	var pictureId = getCookie("pictureId");
	$.ajax({
		url: "/workspace/getStringTag/"+projectType+"/"+userId+"/"+projectId+"/"+publisherId+"/"+pictureId,
			type:"get",
		    async:false, //同步
			success: function(data){
				//data就是内容本容
				$("#userInput").val(data);
				}
	     });
     }


function clearTips(){
	$("#userInput").val("");
}






