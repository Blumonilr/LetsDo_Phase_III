/**
 *获取图片
 */


function getNewPicture(){

    var pictureId = getNewPictureId();
    getPictureSize(pictureId);
    getNewPicture_(pictureId);

}

/**
 * 得到下一张图片对id
 * @returns {string}
 */
function getNewPictureId(){
    var userId = getCookie("userId");
    var projectId =getCookie("projectId");
    var publisherId = getCookie("publisherId");
//	alert(userId+" "+projectId+" "+publisherId);
    var id = "";
    $.ajax({
        url: "/workspace/getNewPictureId/"+userId+"/"+projectId+"/"+publisherId,
        type: "get",
        async:false, //同步
        success: function(data){
            id = data;

        }
    });
    return id;
}