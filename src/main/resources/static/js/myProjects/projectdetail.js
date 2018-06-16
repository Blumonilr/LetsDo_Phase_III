/**
 * 查看项目详情
 * 包括
 * 1.状态（考试   进行中   结束）
 * 2.进度
 * 3.跳转考试界面或工作界面
 */

var condition = "a";//(a/b/c)
var requirement;
var type = "area";//(square/area)
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
        a_href = "<a href='../../exam/make/"+type+"'> 去考试 </a>";
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

    if(condition === "a"){//考试进行中
        b_info = "尚未开始\n先去考试吧～";
    }
    else if(condition === "b"){//进行中
        b_info = "工作进行中\n";
        b_href = "<a href='../../workspace/"+type+"'> 去工作 </a>";

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

    if(condition === "a"){//考试进行中
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
    var third_txt = "yingyingmonster";

    $("#first_accordion").append(first_txt);
    $("#second_accordion").append(second_txt);
    $("#third_accordion").append(third_txt);
}

function getProjectOverviewPicture(){
    var projectId = getCookie("projectId");
    $.ajax({
        url: "/myProjects/getProjectOverview/"+projectId,
        type: "get",
        success: function(data){
            alert(data);
        }
    });

}