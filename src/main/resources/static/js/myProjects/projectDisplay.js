/**
 * 展示worker正在进行的项目
 */

function loadProjects(){
	var userId = getCookie("userId");
	$.ajax({
		url: "/myProjects/getList",
		type: "get",
		data: {
			"userId" : userId,
		},
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

				//pjid_pjname_pubid_description
				add_pj(list[i]);

			}
		}
	});
}

function add_pj(pj_data){
	var ids = pj_data.split("_");
    var pubid = ids[2];
    var pjid = ids[0];
    var description = ids[3];
    var pjname =  ids[1];

    //var	finish = "<img id='"+pubid+"_"+pjid+"' src='/pic/projects/push.png' class='pushicon'  onClick='terminate_project(this)'/>";//是否已完成


    var txt = " <div class='single-member effect-3'  id='"+pubid+"_"+pjid+"'>"+
        "<div class='member-image'>"+
        "<img src='/myProjects/getProjectOverview/"+pjid+"'  alt='Member'>"+
        "</div>"+
        "<div class='member-info'>"+
        "<h3>"+pjname+"</h3>"+
        "<h5>"+description+"</h5>"+
        "<p> 发布人："+pubid+"</p>"+
        "<div class='social-touch' onClick='chooseProject(this)' id='"+pubid+"_"+pjid+"' >"+
        "<img class='pjicons' src='/pic/market/more.png'/>"+
        "查看详情"+
        "</div>"+
        "</div>"+
        "</div>";

    $("#projects").append(txt);
}

function chooseProject(that){
	//选择项目开始工作
	var id = that.id;
	var ids = id.split("_");//pubid_pjid
    setCookie("projectId", ids[1]);
    setCookie("publisherId", ids[0]);

    window.location.href= "/myProjects/projects/detail";
}


