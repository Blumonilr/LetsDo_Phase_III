/**
 * 查看已经完成的图片的逻辑
 * 方法1:  根据projectId和userId拿到图片的id列表，调用方法2，将图片放进组件里
 *方法2先调用ajax把图片放到url里，再用jQuery操作html, 动态生成组件
 */



function getPictureIdList(){
	var userId = getCookie("userId");
	var projectId = getCookie("projectId");
	var publisherId = getCookie("publisherId");
	$.ajax({
		url: "/workspace/viewDone/"+userId+"/"+projectId+"/"+publisherId+"/getPictureIdList",
		type: "get",
		success: function(data){
			//alert(data);
			var list = data.split(",");
			var len = list.length;
			if(list[0]!==""){
				for(let i=0 ; i<len; i++){
					setPicture_bg(list[i]);
				}
		    }
		}
	});
}




function setPicture_bg(pictureId){
	var userId = getCookie("userId");
	var projectId =getCookie("projectId");
	var publisherId = getCookie("publisherId");
	//放入url
	var url = "/workspace/getOldPicture/"+userId+"/"+projectId+"/"+publisherId+"/"+pictureId;
	$.ajax({
			url: "/workspace/getOldPicture/"+userId+"/"+projectId+"/"+publisherId+"/"+pictureId,
			type:"get",
			success: function(data){
				
	
			}
		});
	
	preHtml(pictureId ,function (){
		setCssBackground(url,pictureId);
	});
}
			
			
function setCssBackground(url,pictureId){
	//图片放入url以后,用css加载为背景
	$("#canvas_"+pictureId).css("background-image", "url('"+url+"')");
}


function preHtml(pictureId, callback){
	
     var txt = "<div class='item' >	"+		
					"<div class='item_image'>"+
						"<canvas  id = 'canvas_"+pictureId+"' class='canvas_image' ></canvas>"+
						"</div>"+
					"<div class='image_hover' onMouseMove='showHover(this)' onMouseOut='hideHover(this)' id='hover_"+pictureId+"'>"+
			           "<input class='btn_edit' type='button' value='修改' hidden='hidden' id='"+pictureId+"' onClick='editPicture(this)' />"+
					"</div>"+
					"</div>";
	
	$("items").append(txt);
	
	callback();
	
}

function editPicture(that){
	//进入修改界面

	var pictureId  = that.id;
	setCookie("pictureId", pictureId);
	var projectType = getCookie("projectType");
	window.location.href = "/workspace/editPreviousTag/"+projectType;
//	alert(pictureId);
}


function preImage(url, callback){//使用回调函数保证图片加载到url后才可以被使用
	var img = new Image();
	img.src = url;
	if(img.complete){
		callback.call(img);
		return;
	}
	img.onload = function(){
		callback.call(img);
	};
}


function showHover(that) {
	$("#"+that.id).children().show();
}

function hideHover(that) {
	$("#"+that.id).children().hide();
}