/*
filedrag.js- HTML5 File Drag & Drop demonstration
Featured on SitePoint.com
Developed by Craig Buckler (@craigbuckler) of OptimalWorks.net
*/

var fileselect = $id("fileselect"),
    filedrag = $id("filedrag"),
    fileResult;

window.onload=function () {
    document.getElementById("userId").innerHTML=getCookie("userId")+"&nbsp;";
    $("#profile").attr("href","/user/userDetail/"+getCookie("userId"));
    $.ajax({
        url:"/publisherPage/getLabels",
        type:"POST",
        secureuri : false,
        dataType:"text",
        processData: false,
        contentType: false,
        success:function(res){
            var data=res.split(",");
            $('#tags').select2(
                {
                    data:data,
                    tags:true,
                    placeholder: "至少添加一个标签"
                }
            );
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest+"///"+textStatus+"///"+errorThrown+"\n"+"发生了预料之外的错误，请稍后再试或联系开发人员");
        }
    })


    // call initialization file
    if (window.File && window.FileList && window.FileReader) {
        Init();
    }
}

function uploadDataSet() {
    var projectName=$("#projectName").text();
    var maxNumPerPic=$("#maxNumPerPic").text();
    var minNumPerPic=$("#minNumPerPic").text();
    var endDate=$("#endDate").val();
    var markMode=$("#markMode").text();
    var tagRequirement=$("#note").html();
    var levelLimit=$("#levelLimit").text();
    var testAccuracy=$("#testAccuracy").text();
    var money=$("#payment").text();
    var tags=$('#tags').select2("data");
    var tagResult="";
    for(var i=0;i<tags.length;i++){
        if(i==tags.length-1){
            tagResult+=tags[i].text;
        }else{
            tagResult+=tags[i].text+",";
        }
    }

    var formData = new FormData();
    formData.append("file",fileResult);
    formData.append("projectName",projectName);
    formData.append("maxNumPerPic",maxNumPerPic);
    formData.append("minNumPerPic",minNumPerPic);
    formData.append("endDate",endDate);
    formData.append("markMode",markMode);
    formData.append("tagRequirement",tagRequirement);
    formData.append("levelLimit",levelLimit);
    formData.append("testAccuracy",testAccuracy);
    formData.append("money",money);
    formData.append("tags",tagResult);

    $.ajax({
        url:"/publisherPage/publish",
        type:"POST",
        secureuri : false,
        data:formData,
        dataType:"text",
        processData: false,
        contentType: false,
        success:function(res){
            var flag=res.split("+")[0];
            if(flag==="success"){
                alert("上传成功");
                setCookie('projectId',res.split("+")[1],'1');
                setCookie('publisherId',getCookie("userId"),'1')
                window.location.href="/project/publisher/projectDetail";
            }
            if(flag==="fail"){
                alert("上传失败，数据库出错");
            }
            console.log(res);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest+"///"+textStatus+"///"+errorThrown+"\n"+"发生了预料之外的错误，请稍后再试或联系开发人员");
        }
    })
}

	// getElementById
	function $id(id) {
		return document.getElementById(id);
	}


	// output information
	function Output(msg) {
		var m = $id("messages");
		m.innerHTML = msg;
	}


	// file drag hover
	function FileDragHover(e) {
		e.stopPropagation();
		e.preventDefault();
		e.target.className = (e.type == "dragover" ? "hover" : "");
	}


	// file selection
	function FileSelectHandler(e) {

		// cancel event and hover styling
		FileDragHover(e);

		// fetch FileList object
        var files = e.target.files || e.dataTransfer.files;

		// process all File objects
		for (var i = 0, f; f = files[i]; i++) {
			ParseFile(f);
		}

	}


	// output file information
	function ParseFile(file) {
		fileResult=file;
		Output(
			"<p>File information: <strong>" + file.name +
			"<br/></strong> type: <strong>" + file.type +
			"<br/></strong> size: <strong>" + Math.ceil(file.size/1024) +
			"</strong> KB</p>"
		);

	}


	// initialize
	function Init() {

		// file select
		fileselect.addEventListener("change", FileSelectHandler, false);

		// is XHR2 available?
		var xhr = new XMLHttpRequest();
		if (xhr.upload) {

			// file drop
			filedrag.addEventListener("dragover", FileDragHover, false);
			filedrag.addEventListener("dragleave", FileDragHover, false);
			filedrag.addEventListener("drop", FileSelectHandler, false);
			filedrag.style.display = "block";

			// remove submit button
			// submitbutton.style.display = "none";
		}
	}