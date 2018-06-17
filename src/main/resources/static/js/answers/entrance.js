/**
 * 输入内测码进入内测界面
 */
var trueProjectId;
var testProjectId;
var type;
var publisherId;
var invite_code;

function enter(){
    var numId = $("#number_input").val();
    invite_code = numId;
    var userId = getCookie("userId");

    if(numId != ""){
        $.ajax({
            url: "/answer/getProject",
            data: {"numId" : numId,
                   "userId" : userId },
            type: "get",
            success: function(data){
                //trueProjectId+"_"+publisherId+"_"+type+"_"+testProjectId;
                if(data === ""){
                    toastr.error("不合法的邀请码");
                }
                else{
                    datas = data.split("_");
                    trueProjectId = datas[0];
                    publisherId = datas[1];
                    type = datas[2];
                    testProjectId = datas[3];

                    set_introduction();
                }
            }
        })
    }
    else{
        //输入为空
        toastr.error("请输入内测码");
    }
}


/**
 * 确认进入项目内测
 * confirm界面使用
 */
function confirm(){
    setCookie("projectId",trueProjectId);
    setCookie("publisherId",publisherId);
    setCookie("inviteCode", invite_code);
    setCookie("testProjectId", testProjectId);
    setCookie("projectType", type);

    toastr.success("即将进入项目 "+trueProjectId+" 的答案制定");
    setTimeout(2000);
    window.location.href = "/answer/make/"+type;

}
/**
 * confirm界面使用,显示项目简介
 */
function set_introduction(){
    $("#confirm").empty();
    var txt = "<p>项目名称:  "+trueProjectId+"  </p><br>\n" +
        "                <p>发布者:    "+publisherId+"   </p><br>\n" +
        "                <p>标注类型:  "+type+"</p><br>\n" +
        "\n" +
        "                <input type='button' onclick='confirm()' value='确认' />";

    $("#confirm").append(txt);
}