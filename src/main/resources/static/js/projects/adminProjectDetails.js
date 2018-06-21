function initFunction() {
    /*MENU
    ------------------------------------*/
    $('#main-menu').metisMenu();

    $(window).bind("load resize", function () {
        if ($(this).width() < 768) {
            $('div.sidebar-collapse').addClass('collapse')
        } else {
            $('div.sidebar-collapse').removeClass('collapse')
        }
    });

    $("#sideNav").click(function(){
        if($(this).hasClass('closed')){
            $('.navbar-side').animate({left: '-0px'});
            $(this).removeClass('closed');
            $('#page-wrapper').animate({'margin-left' : '260px'});

        }
        else{
            $(this).addClass('closed');
            $('.navbar-side').animate({left: '-260px'});
            $('#page-wrapper').animate({'margin-left' : '0px'});
        }
    });
}

function loadDetail(){
    var projectId=getCookie("projectId");
    $.ajax({
        url: "/project/publisher/"+projectId,
        type: "post",
        dataType:"text",
        success : function(data){
            var detail=data.split("*");
            var project=JSON.parse(detail[0]);
            var markMode=project.markMode;
            if(markMode==="SQUARE") {
                $("#markMode").text("框选标注项目");
            }else if(markMode==="AREA"){
                $("#markMode").text("区域覆盖标注项目");
            }
            $("#publisherId").text(project.publisherName);
            $("#projectState").text(project.projectState);
            $("#labels").text(project.labels);
            $("#currWorkerNum").text(project.currWorkerNum);
            $("#picNum").text(project.picNum);
            $("#maxNumPerPic").text(project.maxNumPerPic);
            $("#minNumPerPic").text(project.minNumPerPic);
            $("#startDate").text(project.startDate);
            $("#endDate").text(project.endDate);
            $("#levelLimit").text(project.levelLimit);
            $("#testAccuracy").text(project.testAccuracy);
            $("#money").text(project.money);
            $("#inviteCode").text(project.inviteCode);
            $("#tagRequirement").html(detail[1]);
            $("#progressBar").attr("style","width: "+project.progress+"%;");
            $("#progressValue").text(project.progress+"% 完成");

            $("#projectName1").text(project.projectName);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest+"///"+textStatus+"///"+errorThrown);
        },
    });
    $("#header").css("background-image","url(/project/projectOverview/"+getCookie("projectId")+")");
}

function closeProject(){
    $.ajax({
        url: "/project/close",
        type: "post",
        dataType:"text",
        data:{"projectId":getCookie("projectId")},
        success : function(msg){
            alert(msg);
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest+"///"+textStatus+"///"+errorThrown);
        },
    });
}

function download(){
    $.ajax({
        url: "/publisherPage/downloadCheck",
        type: "post",
        dataType:"text",
        data:{"projectId":getCookie("projectId")},
        success : function(re){
            if(re==="success"){
                var form=$("<form>");//定义一个form表单
                form.attr("style","display:none");
                form.attr("method","get");
                form.attr("action","/publisherPage/download");
                var input1=$("<input>");
                input1.attr("type","hidden");
                input1.attr("name","projectId");
                input1.attr("value",getCookie("projectId"));
                $("body").append(form);//将表单放置在web中
                form.append(input1);
                form.submit();//表单提交
            }else {
                alert("项目未完成");
            }
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest+"///"+textStatus+"///"+errorThrown);
        },
    });
}

$(function () {
    initFunction();

    var id = getCookie("userId");
    document.getElementById("userId").innerHTML=id+"&nbsp;";
    loadDetail();
});