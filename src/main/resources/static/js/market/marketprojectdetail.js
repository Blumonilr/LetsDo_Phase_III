/**
 * 
 */

function get_info(){

    var projectId = getCookie("projectId");

    $.ajax({
        url: "/market/getdetail,
        type: "get",
        data: {
            "projectId" : projectId,
        },
        success: function(data){
           // type_disc+"_"+start_time+"_"+end_time+"_"+lowest_level+"_"+payment+"_"+people_in+"_"+requirement+"_"+type+"_+pubid

            set_info(data);
        }
    });
}

function fork(){
    var publisherId = getCookie("publisherId");
    var projectId = getCookie("projectId");
    var userId = getCookie("userId");
    $.ajax({
        url: "/market/fork/"+userId+"/"+publisherId+"/"+projectId,
        type: "get",
        success: function(data){
            toastr.info(data);//需要保留和替换
        }
    });
}

function set_info(data){

    var projectId = getCookie("projectId");
    var datas = data.split("_");
    var type_disc = datas[0];
    var begin_time = datas[1];
    var end_time = datas[2];
    var lowest_level = datas[3];
    var payment = datas[4];
    var people_in = datas[5];
    var requirement = datas[6];
    var type = datas[7];
    var publisherId = datas[8];

    var txt1;
    var txt2;
    var txt3;

    txt1 = "<p>发布者: "+publisherId+"</p><br>"+
           "<p> 项目名: "+projectId+"</p><br>"+
           "<p> 报酬: "+payment+"</p><br>"+
           "<p> 标注类型: "+type_disc+"</p><br>";


    txt2 = "<p>等级要求: "+lowest_level+"</p><br>"+
           "<p>标注要求:"+"</p><br>"+
           "<p>"+requirement+"</p><br>"+
           "<p>开始时间: "+begin_time+"</p><br>"+
           "<p>结束时间: "+end_time+"</p><br>";

    txt3 = "<p>当前参与人数: "+people_in+"</p><br>";


    $("#first_info").append(txt1);
    $("#second_info").append(txt2);
    $("#third_info").append(txt3);
}