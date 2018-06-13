/**
 * 
 */

var requirement_hide = false;

/**
 * 上传一个答案
 */
function submit_tag(){

    var type = getCookie("type");
    var userId = getCookie("userId");
    var projectId = getCookie("testProjectId");//testProject

    var pictureId = getCookie("pictureId");
    var xml = get_xml_string();

    var canvas = document.getElementById("penal");
    var tag = canvas.toDataURL("image/png");
    var base64 = tag.substring(22);

    var url = "/answer/submit";

    if(remark === ""){
        //未填写完成
        toastr.error("标注填写不完整！");

    }
    else{
        $.ajax({
            url: url,
            type: "post",
            data: {
                'base64' : base64,
                'xml' : xml,
                'userId': userId,
                'pictureId': pictureId,
                'projectId': projectId
            },
            success: function(){
                toastr.success("提交成功!");
                //这个方法在getPicture.js里面
                setTimeout("getNewPicture()",1000);
                prepare_for_next_picture();//这个方法在canvas_new_area.js里
            }
        });
    }
}


/**
 * 设置要求区
 */
function set_requirement(){
    //加载要求
    var projectId = getCookie("projectId");
    var publisherId = getCookie("publisherId");
    $.ajax({
        url:  "/myProjects/getProjectRequirement/"+publisherId+"/"+projectId,
        type: "get",
        success: function(data){
            $("#requirementArea").append(data);
        }
    });
}
/**
 * 显示隐藏要求
 */
function hide_show_requirement(){

    if(requirement_hide){
        $("#requirementArea").show();
        requirement_hide = false;
    }
    else{
        $("#requirementArea").hide();
        requirement_hide = true;
    }
}

