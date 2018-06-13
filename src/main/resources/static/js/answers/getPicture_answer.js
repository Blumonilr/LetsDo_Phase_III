/**
 *获取图片
 */

var picture_id_list = [];

function getNewPicture(){

   if(picture_id_list.length === 0){
       //没有缓存的了
       get_a_list_of_pictures();
   }

   var pictureId = picture_id_list[0];
   picture_id_list.splice(0,1);
   setCssBackground(pictureId);
}

/**
 *获得一组图片
 */
function get_a_list_of_pictures(){
    var inviteCode = getCookie("inviteCode");
    $.ajax({
        url:  "/answer/getsomeimages",
        type: "get",
        async:false, //同步
        data:{"inviteCode" : inviteCode},
        success: function (data) {
            var list = data.split("_");
            var len = list.length;
            picture_id_list = [];
            for(let i=0;i<len;i++){
                set_picture_url(list[i]);
                picture_id_list.push(list[i]);
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

function setCssBackground(pictureId){
    //图片放入url以后,用css加载为背景
    // alert("begin");
    var url = "/answer/getNewPicture/"+pictureId;
    $("#penal").css("background-image", "url('"+url+"')");
    //修改canvas大小
    var canvas = document.getElementById("penal");
    canvas.height = getCookie("pictureHeight");
    canvas.width = getCookie("pictureWidth");
    // alert("Done");
}