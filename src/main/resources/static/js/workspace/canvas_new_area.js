var point_list_list = [];//记录点集的list，用来支持删除
var is_deleted_list = [];//如果对应的点集被删除，为false
var is_submitted_list = [];//用来记录用户是否提交了文字
var point_list = [];//记录用户描的点
var tip_list = [];//记录用户的输入
var color_r_list = [255,144,255,255,135,255,205,205,255,230];//颜色值
var color_g_list = [127,238,255,181,206,215,205,92,231,230];//颜色值
var color_b_list = [80,144,255,197,255,0,193,92,186,250];//颜色值
var color_list = ["coral","palegreen","yellow","pink","skyblue","gold","ivory","indianred","wheat","lavender"];
var global_mark_ptr;
var place_holder = "--------";


var supervise_delete_time = 0;
var supervise_click_time = 0;
var supervise_start_time;
var supervise_end_time;
var supervise_total_points = 0; //添加的点的数量


//一个标记对应pointlistlist，colorlist,tiplist里相同下表的一组点

/**
 * 画一个点
 * @param x
 * @param y
 */
function add_point(x,y){
    var p = [x,y];
    //alert(p);
    point_list.push(p);
    if(point_list.length === 1){
        //起始点
        global_mark_ptr.ctx.fillStyle="#FF0000";//红色
        global_mark_ptr.ctx.beginPath();
        global_mark_ptr.ctx.arc(x,y,2.5,0,2*Math.PI);

    }else{
        global_mark_ptr.ctx.fillStyle="#225588";//蓝色
        global_mark_ptr.ctx.beginPath();
        global_mark_ptr.ctx.arc(x,y,1.5,0,2*Math.PI);
    }
    global_mark_ptr.ctx.fill();

    //和上一个点连线
    if(point_list.length>1 && global_mark_ptr.type==="point"){
        var p1 = point_list[point_list.length-2];
        draw_a_line(p1[0],p1[1],p[0],p[1]);
    }

    supervise_total_points++;
}


/**
 * 生成一个obj的所有对应值
 * 1.涂色
 * 2.显示输入框，暂不要输入，但是tiplist要有值
 * 3.obj的点集在pointlist里
 */
function produce_an_obj(){
    //1.检查是否可以生成对象
    var len = point_list.length;
    if(len===0 || len===1){return;}//暂时
    var p_h = point_list[0];
    var p_t = point_list[len-1];
    var dif = (p_h[0]-p_t[0])*(p_h[0]-p_t[0]) + (p_h[1]-p_t[1])*(p_h[1]-p_t[1]);//两点欧式距离
    if(dif>=16){return;}//不满足闭合条件

    //满足闭合条件
    var number_of_new = point_list_list.length;//这是新obj的下标，比实际个数少一️
    var str = "名称_牛_羊_猪,年龄_幼_壮_老";
    var new_tip = [];//用来记录用户输入 title1_c1,title2_c2,c不生成，只要title1_,title2_
    var strs = str.split(",");
    for(let i=0;i<strs.length;i++){
        var input = strs[i].split("_")[0]+"_";
        new_tip.push(input);
    }

    tip_list.push(new_tip);//生成输入
    show_a_new_tip(number_of_new);//显示新输入区
    point_list_list.push(point_list);//记录点集
    point_list = [];//清空记录 point_list = [];
    print_all_exist_obj();//画出来
    is_deleted_list.push(false);//生成删除标记
    is_submitted_list.push(false);//生成提交标记


}

/**
 * 画出现在没有被删除的所有obj
 */
function print_all_exist_obj(){
    clear_all();
    var len = point_list_list.length;
    for(let i=0;i<len;i++){
        if(!is_deleted_list[i]){
            print_an_obj(i);

        }    }
}

/**
 * 画出一个obj
 * @param num第几个，点集，颜色值
 */
function print_an_obj(num){
    var the_point_list = point_list_list[num];
    var len = the_point_list.length;
    global_mark_ptr.ctx.beginPath();
    for(let i=0;i<len;i++){
        var p = the_point_list[i];
        global_mark_ptr.ctx.lineTo(p[0],p[1]);
    }
    global_mark_ptr.ctx.closePath();
    global_mark_ptr.ctx.fillStyle = "rgba("+color_r_list[num]+","+color_g_list[num]+","+color_b_list[num]+",0.5)";
    global_mark_ptr.ctx.fill();
}

/**
 * 显示一个新的输入tip的区域
 * @param num第几个，tip_list
 */
function show_a_new_tip(num){
    //格式: name1_opt1_opt2_opt3,name2_opt1_opt2
    var str = "名称_牛_羊_猪,年龄_幼_壮_老";
    var title_list = str.split(",");
    var len = title_list.length;
    var final_txt = " <div class='obj_tips' style='background-color: rgba("+color_r_list[num]+","+color_g_list[num]+","+color_b_list[num]+",0.6)' id='obj_"+num+"'>";
    for(let i=0;i<len;i++){
        var opts = title_list[i].split("_");
        var sub_len = opts.length;
        var obj_txt = opts[0]+": <select id='select_"+num+"_"+opts[0]+"'>" +
            "  <option value='"+place_holder+"'>"+place_holder+"</option>";//首栏默认值
        for(let j=1;j<sub_len;j++){
            var opt_txt = "  <option value='"+opts[j]+"'>"+opts[j]+"</option>";
            obj_txt = obj_txt + opt_txt;
        }
        obj_txt = obj_txt + " </select>\n" +
            "<br>";
        final_txt = final_txt + obj_txt;
    }
    var btn = "<input type='button' id='delete_"+num+"' onclick='delete_a_tip(this)' value='删除'>";
    final_txt = final_txt + btn + " </div>";
    $("#tipInput").append(final_txt);
}

/**
 * 删除一个tip区域
 * @param num第几个(从0开始)
 */
function delete_a_tip(that){
    var num = that.id.split("_")[1];
    num = parseInt(num);
    $("#tipInput").children("#obj_"+num).remove();//删除输入框
    is_deleted_list[num] = true;
    is_submitted_list[num] = true;
    print_all_exist_obj();

    supervise_delete_time++;
}

/**
 * 提交一个区域的输入
 * @param that
 */
function submit_one_obj(that){
    var num = parseInt(that.id.split("_")[1]);
    var str = "名称_牛_羊_猪,年龄_幼_壮_老";
    var strs = str.split(",");
    var result_list = [];
    for(let i=0;i<strs.length;i++){
        var title = strs[i].split("_")[0];
        var select_id = "select_"+num+"_"+title;
        var tip = $("#"+select_id).val();
        if(tip === place_holder){
            alert("请填写完整")
            return false;
        }
        else{
            var result = title+"_"+tip;
            result_list.push(result);
        }
    }
    tip_list[num] = result_list;
    is_submitted_list[num] = true;
}

var Mark = function (){
    'use strict';
    this.type = "";//要自己选择
    this.penal = document.getElementById("penal");
    this.ctx = this.penal.getContext("2d");

    this.tools = document.getElementById("tools");
    this.img = new Image();//动态绘制矩形、圆形

    this.draw_begin = false;//有没有开始画线

}

/**
 * 画一条线
 * @param x1
 * @param y1
 * @param x2
 * @param y2
 */
function draw_a_line(x1,y1,x2,y2){
    global_mark_ptr.ctx.moveTo(x1,y1);
    global_mark_ptr.ctx.lineTo(x2,y2);
    global_mark_ptr.ctx.stroke();
}

/**
 * 删除还没有闭合的正在绘制的区域
 */
function draw_back(){
    point_list = [];

    print_all_exist_obj();

    global_mark_ptr.draw_begin = false;

   // supervise_delete_time++;
}

Mark.prototype.init = function(){//初始化
    'use strict';
    var self = this;
    global_mark_ptr = this;
    var date = new Date();
    supervise_start_time  = date.getTime();

    this.tools.addEventListener('click', function(event){//工具栏点击事件
        $(".tools").css("border-style","hidden");
        draw_back();
        if(event.target.id === "line"){
            self.type = "line";
            $("#line").css("border-style","inset");
            $("#line").css("border-width","medium");
        }
        else if(event.target.id === "point"){
            self.type = "point";
            $("#point").css("border-style","inset");
            $("#point").css("border-width","medium");

        }
    }, false);


    this.penal.addEventListener("mousedown", function(event){//按下鼠标

        if(self.type === "point"){//画点
            var x = event.offsetX;
            var y = event.offsetY;//点击事件相对于事件所属的this的位置

            add_point(x,y);
            produce_an_obj();//这里是尝试，若不满足条件，会被函数拒绝
        }

        else if(self.type === "line" && !self.draw_begin){//画线k开始
            var x = event.offsetX;
            var y = event.offsetY;//点击事件相对于事件所属的this的位置

            add_point(x,y);
            self.draw_begin = true;
        }

        else if(self.type === "line" && self.draw_begin){//画线结束
            var x = event.offsetX;
            var y = event.offsetY;//点击事件相对于事件所属的this的位置
            self.draw_begin = false;
            add_point(x,y);
            produce_an_obj();//这里是尝试，若不满足条件，会被函数拒绝

        }

        supervise_click_time++;

    },false);

    this.penal.addEventListener("mousemove",function(event){//鼠标移动

        if(self.type === "line" && self.draw_begin){//画线
            var x = event.offsetX;
            var y = event.offsetY;//点击事件相对于事件所属的this的位置
            add_point(x,y);
        }
    }, false);
}

/**
 * 清空画板
 */
function clear_all(){
    var height = getCookie("pictureHeight");
    var width = getCookie("pictureWidth");
    global_mark_ptr.ctx.clearRect(0,0,width,height);
}

/**
 * 生成格式化的标注string
 */
function get_xml_string(){

    var res = auto_submit_tips();
    if(!res){
        return "";
    }

    var s1 = "<projectId>"+getCookie("projectId")+"</projectId>\n";
    var s2 = "<publisherId>"+getCookie("publisherId")+"</publisherId>\n";
    var s3 = "<userId>"+getCookie("userId")+"</userId>\n";
    var s4 = "<pictureId>"+getCookie("pictureId")+"</pictureId>\n";
    var s5 = "<size>\n"+
        "<width>"+getCookie("pictureWidth")+"</width>\n"+
        "<height>"+getCookie("pictureHeight")+"</height>\n"+
        "</size>\n";
    var s_objs = "<objects>\n";

    var num = tip_list.length;
    for(let i=0;i<num;i++){
        if(!is_submitted_list[i] && !is_deleted_list[i]){
            //未提交文字
            alert("第"+(i+1)+"个标记未提交描述");
            return "";//未完成
        }
        else{//提交了文字
            if(!is_deleted_list[i]){//obj存在，没有被删除
                var s_obj = "    ";
                var color = "            <color>\n" +
                    "                <R>"+color_r_list[i]+"</R>\n" +
                    "                <G>"+color_g_list[i]+"</G>\n" +
                    "                <B>"+color_b_list[i]+"</B>\n" +
                    "            </color>\n";


                var tag_list = tip_list[i];
                var tags = "            <tags>\n";
                for(let j=0;j<tag_list.length;j++){
                    var tag = "                <tag>\n"+
                        "                    <title>"+tag_list[j].split("_")[0]+"</title>\n"+
                        "                    <value>"+tag_list[j].split("_")[1]+"</value>\n"+
                        "                </tag>\n";
                    tags = tags +tag;
                }

                tags = tags + "            </tags>\n";

                s_obj = "        <object>\n"
                    +color
                    +tags
                    +"        </object>\n";

                s_objs = s_objs + s_obj;
            }

        }
    }

    s_objs = s_objs + "</objects>";

    //supervise  begin
    var date = new Date();
    supervise_end_time = date.getTime();
    var work_time = (supervise_end_time - supervise_start_time) / 1000;//秒数
    var supervise_str = "<supervise>\n"+
                        "    <time>"+work_time+"</time>\n"+
                        "    <click>"+supervise_click_time+"</click>\n"+
                        "    <delete>"+supervise_delete_time+"</delete>\n"+
                        "    <points>"+supervise_total_points+"</points>\n"+
                        "</supervise>\n";


    //supervise   end

    var final_s = s1 + s2 + s3 + s4 + s5 +supervise_str+ s_objs;
    return final_s;
}

function auto_submit_tips(){

    var str = "名称_牛_羊_猪,年龄_幼_壮_老";
    var strs = str.split(",");

    var total_num = tip_list.length;
    for(let index=0;index<total_num;index++){
        if(!is_deleted_list[index]){
            var result_list = [];
            for(let i=0;i<strs.length;i++){
                var title = strs[i].split("_")[0];
                var select_id = "select_"+index+"_"+title;
                var tip = $("#"+select_id).val();
                if(tip === place_holder){
                    //未填写
                    return false;
                }
                else{
                    var result = title+"_"+tip;
                    result_list.push(result);
                }
            }
            tip_list[index] = result_list;
            is_submitted_list[index] = true;
        }
    }
    return true;
}

/**
 * 为下一张图片做清理工作
 */
function prepare_for_next_picture(){
    point_list_list = [];//记录点集的list，用来支持删除
    is_deleted_list = [];//如果对应的点集被删除，为false
    is_submitted_list = [];//用来记录用户是否提交了文字
    point_list = [];//记录用户描的点
    tip_list = [];//记录用户的输入

    supervise_delete_time = 0;
    supervise_click_time = 0;
    supervise_start_time = 0;
    var date = new Date();
    supervise_start_time = date.getTime();
    supervise_total_points = 0; //添加的点的数量


    $("#tipInput").empty();
    clear_all();
}