/**
 * 输入内测码进入内测界面
 */
var pjid;
var pubid;
var type;

function enter(){
    var numId = $("#number_input").val();
    var userId = getCookie("userId");

    if(numId != ""){
        $.ajax({
            url: "/answer/getProject",
            data: {"numId" : numId,
                   "userId" : userId },
            type: "get",
            success: function(data){
                //pjid_pubid_type
                if(data === ""){
                    toastr.error("不合法的邀请码");
                }
                else{
                    datas = data.split("_");
                    pjid = datas[0];
                    pubid = datas[1];
                    type = datas[2];
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
    setCookie("projectId",pjid);
    setCookie("publisherId",pubid);

    toastr.success("即将进入项目 "+pjid+" 的答案指定");
    setTimeout(2000);
    window.location.href = "/answer/make/"+type;

}
/**
 * confirm界面使用,显示项目简介
 */
function set_introduction(){
    $("#confirm").empty();
    var txt = "<p>项目名称:  "+pjid+"  </p><br>\n" +
        "                <p>发布者:    "+pubid+"   </p><br>\n" +
        "                <p>标注类型:  "+type+"</p><br>\n" +
        "\n" +
        "                <input type='button' onclick='confirm()' value='确认' />";

    $("#confirm").append(txt);
}