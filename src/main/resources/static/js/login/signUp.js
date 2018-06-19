/**
 * register.html的逻辑
 */
function signUp(type){
    var nickName=$("#name").val();
    var password=$("#password").val();
    var email=$("#email").val();
    if ("" == nickName.trim()) {
        $("#alert").show();
        $("#alertText").text("昵称不得为空");
        $("#name").focus();
        return false;
    } else if ("" == password.trim()) {
        $("#alertText").text("密码不得为空");
        $("#alert").show();
        $("#password").focus();
        return false;
    }else if ("" == $("#email").val().trim()) {
        $("#alertText").text("邮箱不得为空");
        $("#alert").show();
        $("#password").focus();
        return false;
    }else if(password.length < 6){
        $("#alertText").text("密码长度不足");
        $("#alert").show();
        $("#password").focus();
        return false;
    }
	else if($("#passwordAssure").val() != password){
        $("#alertText").text("两次输入密码不相同");
        $("#alert").show();
        return false;
    }
	else{
        var formData = new FormData();
        formData.append("nickName",nickName);
        formData.append("password",password);
        formData.append("email",email);
        $.ajax({
            url: "/user/"+type+"SignUp",
            type: "POST",
            data: formData,
            dataType: "text",
            processData: false,
            contentType: false,
            success: function (res) {
                alert("请牢记:你的用户名是"+res)
                window.location.href="/user/login";
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                alert(XMLHttpRequest + "///" + textStatus + "///" + errorThrown + "\n" + "发生了预料之外的错误，请稍后再试或联系开发人员");
            }
        })
	}
}
