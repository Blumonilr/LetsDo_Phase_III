/**
 *获取图片
 */

var picture_id_list = [];

var requirement_hide = false;

var class_name_list = ["猪","牛"];

var selection_str_list = ["肥瘦_肥_瘦,大小_大_小","性别_雄_雌,种类_耕牛_奶牛"];

function getNewPicture(){

   if(picture_id_list.length === 0){
       //没有缓存的了
       get_a_list_of_pictures();
   }

   var pictureId = picture_id_list[0];
   picture_id_list.splice(0,1);
   setCookie("pictureId" , pictureId);
   setCssBackground(pictureId);
}

/**
 *获得一组图片
 */
function get_a_list_of_pictures(){
    var testProjectId = getCookie("testProjectId");
    $.ajax({
        url:  "/answer/getsomeimages",
        type: "get",
        async:false, //同步
        data:{"projectId" : testProjectId},
        success: function (data) {
            var list = data.split("_");
            var len = list.length;
            if(len !== 0){
                picture_id_list = [];
                for(let i=0;i<len;i++){
                    set_picture_url(list[i]);
                    picture_id_list.push(list[i]);
                }
            }
            else {
                //没有图片了
                toastr.info("已完成所有的答案制订！")
                setTimeout("history.back()",3000);//等待3秒后返回上一界面
            }
        }
    });
}

/**
 * 将图片放进url
 * @param pictureId
 */
function set_picture_url(pictureId){
    $.ajax({
        url:  "/answer/getNewPicture/"+pictureId,
        type: "get",
        async:false, //同步
        success: function () {
           //这里可以预加载一下
        }
    });
}


/**
 * 设置背景和背景大小
 * @param pictureId
 */
function setCssBackground(pictureId){
    //图片放入url以后,用css加载为背景
    //alert("begin css background");
    //获得图片大小
    $.ajax({
        url:  "/answer/getPictureSize/"+pictureId,
        type: "get",
        success: function (data) {//width_height
          //  alert(data);
           var size = data.split("_");
           var width = parseInt(size[0]);
           var height = parseInt(size[1]);
           var canvas = document.getElementById("penal");
           canvas.height = height;
           canvas.width = width;
           setCookie("pictureWidth", width);
           setCookie("pictureHeight", height);
        }
    });

    var url = "/answer/getNewPicture/"+pictureId;
    $("#penal").css("background-image", "url('"+url+"')");
}

/**
 * 上传一个答案,两种类型的标注上传接口已经统一了
 */
function submit_tag(){

    var type = getCookie("projectType");
    var userId = getCookie("userId");
    var testProjectId = getCookie("testProjectId");//testProject

    var pictureId = getCookie("pictureId");
    var xml = get_xml_string();

    var canvas = document.getElementById("penal");
    var tag = canvas.toDataURL("image/png");
    var base64 = tag.substring(22);

    var url = "/answer/submit";

    if(xml === ""){
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
                'projectId': testProjectId
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

/**DONE
 * 设置要求区
 */
function set_requirement(){
    var projectId = getCookie("projectId");
    $.ajax({
        url:  "/answer/getRequirement",
        type: "get",
        data:{"projectId" : projectId},
        success: function(data){
            $("#requirementArea").append(data);
        }
    });
}

/**DONE
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

/**
 * 获得文字选项
 */
function get_options(){
    var projectId = getCookie("projectId");
    $.ajax({
        url:  "/answer/getOptions",
        type: "get",
        data:{"projectId" : projectId},
        success: function(data){// 猪:肥瘦_肥_瘦,大小_大_小!牛:性别_雄_雌,种类_耕牛_奶牛
            class_name_list = [];
            selection_str_list = [];
            var classes = data.split("!");
            var num_of_class = classes.length;
            for(let i=0;i<num_of_class;i++){//
                var name_and_selections = classes[i].split(":");
                class_name_list.push(name_and_selections[0]);//猪
                selection_str_list.push(name_and_selections[1]);//肥瘦_肥_瘦,大小_大_小
            }
        }
    });
}


/**
 * 根据classname得到对应的selections
 */
function get_selections_of_a_class(class_name){
    var num_of_class = class_name_list.length;
    for(let i=0;i<num_of_class;i++){
        if(class_name_list[i] === class_name){
            return selection_str_list[i];
        }
    }
    return "";//没有找到返回""
}
