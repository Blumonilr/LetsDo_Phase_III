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
    var projectId=getCookie("projectId")
    $("#projectId").text(projectId);
    projectInfo(projectId);

    // call initialization file
    if (window.File && window.FileList && window.FileReader) {
        Init();
    }

}

function projectInfo(projectId) {
    $.ajax({
        url: "/project/publisher/"+projectId,
        type: "post",
        dataType:"text",
        success : function(data){
            var detail=data.split("*");
            var project=JSON.parse(detail[0]);
            $("#projectNameDisp").text(project.projectName);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest+"///"+textStatus+"///"+errorThrown);
        },
    });
}

function uploadTestSet(){
    var formData = new FormData();
    formData.append("file",fileResult);
    formData.append("projectId",getCookie("projectId"))
    $.ajax({
        url:"/publisherPage/publishTest",
        type:"POST",
        secureuri : false,
        data:formData,
        dataType:"text",
        processData: false,
        contentType: false,
        success:function(res){
            if(res==="fail"){
                alert("上传失败，数据库出错");
            }else{
                alert("项目邀请码是:"+res);
            }
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