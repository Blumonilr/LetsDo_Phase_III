/**
 * 
 */
var type;
var requirement;
var begin_time;
var end_time;
var people_in;
var payment;
var lowest_level;


function get_info(){
    var publisherId = getCookie("publisherId");
    var projectId = getCookie("projectId");
    var userId = getCookie("userId");

    $.ajax({
        url: "/market/getdetail/"+publisherId+"/"+projectId,
        type: "get",
        success: function(data){
           // type+"_"+start_time+"_"+end_time+"_"+lowest_level+"_"+payment"_"+people_in+"_"+requirement+;
            var datas = data.split("_");
            type = datas[0];
            begin_time = datas[1];
            end_time = datas[2];
            lowest_level = datas[3];
            payment = datas[4];
            people_in = datas[5];
            requirement = datas[6];

            set_info();
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

function set_info(){
    var publisherId = getCookie("publisherId");
    var projectId = getCookie("projectId");

    var txt1;
    var txt2;
    var txt3;

    txt1 = "<p>发布者: "+publisherId+"</p><br>"+
           "<p> 项目名: "+projectId+"</p><br>"+
           "<p> 报酬: "+payment+"</p><br>"+
           "<p>开始时间: "+begin_time+"</p><br>"+
           "<p>结束时间: "+end_time+"</p><br>";

    txt2 = "<p>等级要求: "+lowest_level+"</p><br>"+
           "<p>标注要求:"+"</p><br>"+
           "<p>"+requirement+"</p><br>";

    txt3 = "<p>当前参与人数: "+people_in+"</p><br>";


    $("#first_info").append(txt1);
    $("#second_info").append(txt2);
    $("#third_info").append(txt3);
}