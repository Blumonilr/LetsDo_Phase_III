/**
 * 为方框标注提供加载requirement和提交标记的服务
 */

var requirement_hide = false;

function set_requirement(){
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

function submit_tag(){
	//提交标签
    var remark = get_xml_string();


	var type = "square";
	var userId = getCookie("userId");
	var projectId = getCookie("projectId");
	var publisherId = getCookie("publisherId");
	var pictureId = getCookie("pictureId");
	var width = getCookie("pictureWidth");
	var height = getCookie("pictureHeight");
	var canvas = document.getElementById("penal");
	var tag = canvas.toDataURL("image/png");
	var base64 = tag.substring(22);

	var url = "/workspace/submit/"+type+"/"+userId+"/"+projectId+"/"+publisherId+"/"+pictureId;

	if(remark === ""){
		//未填写完成
		alert("填写不完整！")
	}
	else{
        console.log(remark);
        $.ajax({
            url: url,
            type: "post",
            data: {
                'base64' : base64,
                'remark' : remark,
                'width' : width,
                'height' : height,
            },
            success: function(){
                alert("上传成功");
                getNewPicture();//这个方法在getPicture.js里面
                prepare_for_next_picture();//这个方法在canvas_new_area.js里
            }
        });
	}
}

function submit_tag_edit(){
	//提交修改的标记
	var type = "square";
	var userId = getCookie("userId");
	var projectId = getCookie("projectId");
	var publisherId = getCookie("publisherId");
	var pictureId = getCookie("pictureId");
	var remark = get_xml_string();
	var width = getCookie("pictureWidth");
	var height = getCookie("pictureHeight");
	var canvas = document.getElementById("penal");
	var tag = canvas.toDataURL("image/png");
	var base64 = tag.substring(22);

	$.ajax({
		url: "/workspace/submit/"+type+"/"+userId+"/"+projectId+"/"+publisherId+"/"+pictureId,
		type: "post",
		data: {
			'base64' : base64,
			'remark' : remark,
			'width' : width,
			'height' : height,
		},
		success: function(){
			alert("修改成功");
			history.back();//返回上个界面
		}
	});
}

function set_previous_tag(){
	//设置以前的图片
	var userId = getCookie("userId");
	var projectId =getCookie("projectId");
	var publisherId = getCookie("publisherId");
	var projectType = getCookie("projectType");
	var pictureId = getCookie("pictureId");
	$.ajax({
		url: "/workspace/getPictureTag/"+projectType+"/"+userId+"/"+projectId+"/"+publisherId+"/"+pictureId,
			type:"post",
		    async:true, //同步
	    	cache:false,
			success: function(data){
				
			}
	     });
	
	var canvas = document.getElementById("penal");
	var ctx = canvas.getContext("2d");
	var u = "/workspace/getPictureTag/"+projectType+"/"+userId+"/"+projectId+"/"+publisherId+"/"+pictureId;
	preImage(u, function(){
		ctx.drawImage(this,0,0);
	})
}

function preImage(url , callback){
	var img = new Image();
	img.src = url;
	if(img.complete){
		callback.call(img);
		return;
	}
	img.onload = function(){
		callback.call(img);
	}
}

/**
 * 显示要求
 */
function hide_show_requirement(){

	if(requirement_hide){
        $("#requirementArea").show();
        requirement_hide = false;
	}
	else{
        $("#requirementArea").hide();
        requirement_hide = true;
	}
}
