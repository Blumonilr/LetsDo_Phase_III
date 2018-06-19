/**
 * 查看项目详情
 * 包括
 * 1.状态（考试   进行中   结束）
 * 2.进度
 * 3.跳转考试界面或工作界面
 */

var condition;//(a/b/c)
var requirement;
var type ;//(square/area)
var type_disc;
var score;

function get_my_project_detail(){
   var pubid = getCookie("publisherId");
   var pjid = getCookie("projectId");
    var userId = getCookie("userId");

    $.ajax({
        url: "/myProjects/getProjectDetail",
        type: "get",
        data: {
            "userId": userId,
            "projectId" : pjid,
        },
        success: function(data){
            //格式： condition+"_"+type+"_"+requirement+"_"+type_disc+"_"+score
            var datas = data.split("_");
            condition = datas[0];
            type = datas[1];
            requirement = datas[2];
            type_disc = datas[3];
            score = datas[4];

            set_exam();
            set_working();
            set_pay();
            set_accordion();
        }
    });


}

function set_exam(){
    var a_info = "";
    var a_href = "";
    if(condition === "a"){//考试进行中
        a_info = "考试进行中";
        a_href = "<a href='../../exam/entrance' > 去考试 </a>";
    }

    else if(condition === "ax"){
        a_info = "考试已完成，请耐心等待后台评判";
    }

    else{
        a_info = "考试已结束\n考试得分: "+score;

    }

    $("#exam_info").append(a_info);
    $("#exam_href").append(a_href);
}

function  set_working(){
    var b_info = "";
    var b_href = "";

    if(condition === "a" || condition === "ax"){//考试进行中
        b_info = "尚未开始\n先去考试吧～";
    }
    else if(condition === "b"){//进行中
        b_info = "工作进行中\n";
        b_href = "<a href='../../workspace/make/"+type+"'> 去工作 </a>";

    }
    else if(condition === "c"){//已结束
        b_info = "任务已完成";
    }

    $("#working_info").append(b_info);
    $("#working_href").append(b_href);
}

function set_pay(){
    var c_info = "";
    var c_href = "";

    if(condition === "a"|| condition === "ax"){//考试进行中
        c_info = "尚未开始";
        c_href = "<a href='#'> 我的钱包 </a>";
    }
    else if(condition === "b"){//进行中
        c_info = "尚未开始";
        c_href = "<a href='#'> 我的钱包 </a>";
    }
    else if(condition === "c"){//已结束
        c_info = "发工资啦";
        c_href = "<a href='#'> 我的钱包 </a>";
    }

    $("#pay_info").append(c_info);
    $("#pay_href").append(c_href);
}

function set_accordion() {
    var pubid = getCookie("publisherId");
    var pjid = getCookie("projectId");
    var first_txt = "<br>\n项目发布者:" + pubid;
    var second_txt = requirement;


    $("#first_accordion").append(first_txt);
    $("#second_accordion").append(second_txt);

}


function terminate_project(){
    //结束项目
    var confirmRes = confirm("一定要退出该项目吗？");
    if(confirmRes){
        var userId = getCookie("userId");
        var projectId = getCookie("projectId");
        $.ajax({
            url: "/myProjects/terminateProject",
            type: "get",
            data: {
                "userId" : userId,
                "projectId" : projectId,
            },
            success: function(){
                toastr.success("已退出该项目！");
                setTimeout("set_url()",2500);//等待2.5秒后刷新界面
            }
        });
    }
}

function set_url(){
    window.location.href = "/myProjects/projects";
}
