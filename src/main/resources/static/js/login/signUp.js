/**
 * register.html的逻辑
 */
function signUp(){
    if ("" == $("#name").val().trim()) {
        $("#alert").show();
        $("#alertText").text("昵称不得为空");
        $("#name").focus();
        return false;
    }else if ("" == $("#username").val().trim()) {
        $("#alert").show();
        $("#alertText").text("用户名不得为空");
        $("#username").focus();
        return false;
    }else if($("#username").val().length<3){
		$("#alertText").text("用户名长度不足");
        $("#alert").show();
        $("#username").focus();
        return false;
    }
	else if ("" == $("#password").val().trim()) {
        $("#alertText").text("密码不得为空");
        $("#alert").show();
        $("#password").focus();
        return false;
    }else if($("#password").val().length < 6){
        $("#alertText").text("密码长度不足");
        $("#alert").show();
        $("#password").focus();
        return false;
    }
	else if($("#passwordAssure").val() != $("#password").val()){
        $("#alertText").text("两次输入密码不相同");
        $("#alert").show();
        return false;
    }
	else{
		$("#signUpForm").submit();
	}
}
