/**
 * 进入考试界面
 */


var publisherId ;
var type ;
var testProjectId;
/**
 * 确认进入项目考试
 * confirm界面使用
 */
function confirm(){
    setCookie("publisherId",publisherId);
    setCookie("testProjectId", testProjectId);
    setCookie("projectType", type);

    toastr.success("即将进入项目考试\n祝您好运～");
    setTimeout(2000);
    window.location.href = "/exam/make/"+type;
}


/**
 * confirm界面使用,显示项目简介
 */
function set_introduction(){
    var trueProjectId = getCookie("projectId");
    $.ajax({
        url: "/exam/getExam",
        data: {
            "projectId": trueProjectId,
        },
        type: "get",
        success: function(data){
            //publisherId+"_"+type+"_"+testProjectId;
            if(data === ""){
                toastr.error("Oooops,好像出了一点问题...请稍后重试");
            }
            else{
                var datas = data.split("_");
                publisherId = datas[0];
                type = datas[1];
                testProjectId = datas[2];

                $("#confirm").empty();
                var txt = "<p>项目名称:  "+trueProjectId+"  </p><br>\n" +
                    "                <p>发布者:    "+publisherId+"   </p><br>\n" +
                    "                <p>标注类型:  "+type+"</p><br>\n" +
                    "\n" +
                    "                <input type='button' onclick='confirm()' value='确认' />";

                $("#confirm").append(txt);

            }
        }
    })

}