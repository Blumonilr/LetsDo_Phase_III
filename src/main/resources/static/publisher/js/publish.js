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
    // call initialization file
    if (window.File && window.FileList && window.FileReader) {
        Init();
    }
}

function uploadDataSet() {
    var userId=getCookie("userId");
    var projectId=$("#projectName").text();
    var maxWorkerNum=$("#workerNum").text();
    var packageNum=$("#packageNum").text();
    var picNum=$("#pictureAmount").text();
    var startDate=$("#startDate").val();
    var endDate=$("#endDate").val();
    var tags=$("#tags").text();
    var markMode=$("#markMode").text();
    var tagRequirement=$("#note").html();
    var levelLimit=$("#levelLimit").text();
    var gradesLimit=$("#gradesLimit").text();
    var money=$("#payment").text();

    var formData = new FormData();
    formData.append("file",fileResult);
    formData.append("userId",userId);
    formData.append("projectId",projectId);
    formData.append("maxWorkerNum",maxWorkerNum);
    formData.append("packageNum",packageNum);
    formData.append("picNum",picNum);
    formData.append("startDate",startDate);
    formData.append("endDate",endDate);
    formData.append("tags",tags);
    formData.append("markMode",markMode);
    formData.append("tagRequirement",tagRequirement);
    formData.append("levelLimit",levelLimit);
    formData.append("gradesLimit",gradesLimit);
    formData.append("money",money);

    $.ajax({
        url:"/publisherPage/publish",
        type:"POST",
        secureuri : false,
        data:formData,
        dataType:"text",
        processData: false,
        contentType: false,
        success:function(res){
            if(res==="success"){
                alert("上传成功");
            }
            if(res==="fail"){
                alert("上传失败，数据库出错");
            }
            if(res==="repetitive"){
                alert("上传失败，该项目名已被使用");
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