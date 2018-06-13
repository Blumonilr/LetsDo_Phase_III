var red_obj_list = [];//[[x1,y1],[x2,y2]]
var blue_obj_list = [];//[[x1,y1],[x2,y2]]
var yellow_obj_list = [];//[[x1,y1],[x2,y2]]
var green_obj_list = [];//[[x1,y1],[x2,y2]]
var color_r_list = [255,255,0,0];//颜色值
var color_g_list = [0,255,0,255];//颜色值
var color_b_list = [0,0,255,0];//颜色值
var color_list = ["red","yellow","blue","green"];
var red_tip = [];
var yellow_tip = [];
var blue_tip = [];
var green_tip = [];
var global_mark_ptr;
var current_color = -1;//0-3下标
var place_holder = "--------";

var global_color_selected = -1;//被选中的矩形的颜色
var global_obj_num_selected = -1;//被选中的矩形在相应颜色里的下标

var line_width = 3;


var supervise_delete_time = 0;
var supervise_click_time = 0;
var supervise_start_time;
var supervise_end_time;
var supervise_total_points = 0; //添加的点的数量


//1.选择颜色后画一个矩形，对应到一个输入，如果已经有，提示一下，颜色只有四种
//如果没有，显示输入框

//2，删除一个矩形，如果没有这个颜色的矩形了，就删除输入框，否则保留输入框


/**
 * 生成一个obj的所有对应值
 * 1.涂色
 * 2.显示输入框，暂不要输入，但是tiplist要有值
 * 3.obj的点集在pointlist里
 */
function produce_an_obj(x1,y1,x2,y2){

    if(Math.abs(x1-x2) <= 4 || Math.abs(y1-y2) <=4){return;}//太小

    var points = [[x1,y1],[x2,y2]];//点集，放到对应颜色里去
    var str = "名称_牛_羊_猪,年龄_幼_壮_老";//共用的，但是不一定会用到
    var strs = str.split(",");

    if(current_color === 0){
        if(red_obj_list.length === 0){
            //之前没有这种颜色的矩形
            //显示输入
            //用来记录用户输入 title1_c1,title2_c2,c不生成，只要title1_,title2_
            for(let i=0;i<strs.length;i++){
                var input = strs[i].split("_")[0]+"_";
                red_tip.push(input);
            }
            show_a_new_tip(current_color);
        }
        red_obj_list.push(points);
       
    }
    else if(current_color === 1){
        if(yellow_obj_list.length === 0){
            //之前没有这种颜色的矩形
            //显示输入
            for(let i=0;i<strs.length;i++){
                var input = strs[i].split("_")[0]+"_";
                yellow_tip.push(input);
            }
            show_a_new_tip(current_color);
        }
        yellow_obj_list.push(points);
    }
    else if(current_color === 2){
        if(blue_obj_list.length === 0){
            //之前没有这种颜色的矩形
            //显示输入
            for(let i=0;i<strs.length;i++){
                var input = strs[i].split("_")[0]+"_";
                blue_tip.push(input);
            }
            show_a_new_tip(current_color);
        }
        blue_obj_list.push(points);
    }
    else if(current_color === 3){
        if(green_obj_list.length === 0){
            //之前没有这种颜色的矩形
            //显示输入
            for(let i=0;i<strs.length;i++){
                var input = strs[i].split("_")[0]+"_";
                green_tip.push(input);
            }
            show_a_new_tip(current_color);
        }
        green_obj_list.push(points);
    }


    supervise_total_points++;

    print_all_exist_obj();//显示所有矩形

}

/**
 * 画出现在没有被删除的所有obj
 */
function print_all_exist_obj(){
    clear_all();
    for(let i=0;i<4;i++){
        print_an_color_obj(i);
    }
}

/**
 * 画出一个obj
 * @param num第几个，点集，颜色值
 */
function print_an_color_obj(color_num){
   //打印一个颜色的所有矩形
    var the_obj_list = [];//这种颜色的矩形
    if(color_num === 0){
        the_obj_list = red_obj_list;
    }
    else if(color_num === 1){
        the_obj_list = yellow_obj_list;
    }
    else if(color_num === 2){
        the_obj_list = blue_obj_list;
    }
    else if(color_num === 3){
        the_obj_list = green_obj_list;
    }
    else{
        //do nothing
    }

    global_mark_ptr.ctx.lineWidth = line_width;
    global_mark_ptr.ctx.strokeStyle = "rgb("+color_r_list[color_num]+","+color_g_list[color_num]+","+color_b_list[color_num]+")";
    for(let i=0;i<the_obj_list.length;i++){
        //[[x1,y1],[x2,y2]]
        var the_points = the_obj_list[i];
        var x1 = the_points[0][0];
        var y1 = the_points[0][1];
        var x2 = the_points[1][0];
        var y2 = the_points[1][1];
        global_mark_ptr.ctx.strokeRect(x1,y1,x2-x1,y2-y1);
    }
}

/**
 * 显示一个新的输入tip的区域
 * @param num第几个，tip_list
 */
function show_a_new_tip(color_num){
    
    //新显示某种颜色的tip
	 //格式: name1_opt1_opt2_opt3,name2_opt1_opt2
    var str = "名称_牛_羊_猪,年龄_幼_壮_老";
    var title_list = str.split(",");
    var len = title_list.length;
    var final_txt = " <div class='obj_tips' style='background-color: rgba("+color_r_list[color_num]+","+color_g_list[color_num]+","+color_b_list[color_num]+",0.4)' id='obj_"+color_num+"'>";
    for(let i=0;i<len;i++){//id是 obj_num
        var opts = title_list[i].split("_");
        var sub_len = opts.length;
        var obj_txt = opts[0]+": <select id='select_"+color_num+"_"+opts[0]+"'>" +
            "  <option value='"+place_holder+"'>"+place_holder+"</option>";//首栏默认值
        for(let j=1;j<sub_len;j++){
            var opt_txt = "  <option value='"+opts[j]+"'>"+opts[j]+"</option>";
            obj_txt = obj_txt + opt_txt;
        }
        obj_txt = obj_txt + " </select>\n" +
            "<br>";
        final_txt = final_txt + obj_txt;
    }
    var btn2 = "<input type='button' id='delete_"+color_num+"' onclick='delete_one_color(this)' value='删除'>";
    final_txt = final_txt + btn2 + " </div>";
    $("#tipInput").append(final_txt);
}

/**
 * 删除一整个颜色的矩形
 * @param that
 */
function delete_one_color(that){
    var color_num = that.id.split("_")[1];
    if(color_num === "0"){
        red_obj_list = [];
        red_tip = [];
        $("#tipInput").children("#obj_0").remove();//删除输入框
    }
    else if(color_num === "1"){
        yellow_obj_list = [];
        yellow_tip = [];
        $("#tipInput").children("#obj_1").remove();//删除输入框
    }
    else if(color_num === "2"){
        blue_obj_list = [];
        blue_tip = [];
        $("#tipInput").children("#obj_2").remove();//删除输入框
    }
    else if(color_num === "3"){
        green_obj_list = [];
        green_tip = [];
        $("#tipInput").children("#obj_3").remove();//删除输入框
    }

    supervise_delete_time++;

    print_all_exist_obj();//重新显示
}

var Mark = function (){
    'use strict';
    this.type = "";//要自己选择
    this.penal = document.getElementById("penal");
    this.ctx = this.penal.getContext("2d");

    this.colors = document.getElementById("colors");
    this.img = new Image();//动态绘制矩形、圆形

    this.draw_begin = false;//有没有开始画线

}


Mark.prototype.init = function(){//初始化
    'use strict';
    var self = this;
    global_mark_ptr = this;

    var date = new Date();
    supervise_start_time  = date.getTime();

    self.ctx.lineWidth = line_width;
    
    this.colors.addEventListener('click', function(event){//工具栏点击事件
        $(".tools").css("border-style","hidden");
        if(event.target.id === "red"){
            self.type = "line";
            $("#red").css("border-style","inset");
            $("#red").css("border-width","medium");
            current_color = 0;
        }
        else if(event.target.id === "yellow"){
            self.type = "point";
            $("#yellow").css("border-style","inset");
            $("#yellow").css("border-width","medium");
            current_color = 1;

        }
        else if(event.target.id === "blue"){
            self.type = "point";
            $("#blue").css("border-style","inset");
            $("#blue").css("border-width","medium");
            current_color = 2;

        }
        else if(event.target.id === "green"){
            self.type = "point";
            $("#green").css("border-style","inset");
            $("#green").css("border-width","medium");
            current_color = 3;
        }
    }, false);



    this.penal.addEventListener("mousedown", function(event){//按下鼠标
        if(current_color !== -1){

            self.ctx.strokeStyle = "rgb("+color_r_list[current_color]+","+color_g_list[current_color]+","+color_b_list[current_color]+")";
            self.ctx.lineWidth = line_width;
            self.img.src = self.penal.toDataURL('image/png');//为了动态绘图
            self.originX = event.offsetX;
            self.originY  = event.offsetY;
            self.ctx.beginPath();

            self.draw_begin = true;

            global_obj_num_selected = -1;
            global_color_selected = -1;//没有被选中

            supervise_click_time++;
        }

    },false);


    this.penal.addEventListener("mousemove",function(event){//鼠标移动

       if(self.draw_begin){
           clear_all();//清空画板

           var x = event.offsetX;
           var y = event.offsetY;//点击事件相对于事件所属的this的位置

           self.ctx.drawImage(self.img, 0, 0);//增加代码
           self.ctx.beginPath();//增加代码

           self.ctx.strokeRect(self.originX,self.originY,x-self.originX,y-self.originY);

           self.ctx.closePath();//增加代码
       }

    }, false);


    this.penal.addEventListener("mouseup", function(event){//松开鼠标

        if(self.draw_begin){
            var x = event.offsetX;
            var y = event.offsetY;//点击事件相对于事件所属的this的位置

            self.draw_begin = false;
            self.ctx.closePath();

            //此处产生一个新的矩形
            produce_an_obj(self.originX,self.originY,x,y);

        }

    },false);

    this.penal.addEventListener("dblclick", function(event){//松开鼠标

        var x = event.offsetX;
        var y = event.offsetY;//点击事件相对于事件所属的this的位置

        supervise_click_time++;

        select_a_square(x,y);

    },false);

}

/**
 * 通过一个点，找到一个矩形,若有多个，返回面积较小的那个
 * @param x
 * @param y
 */
function select_a_square(x,y){

    clear_all();
    print_all_exist_obj();//清空上一次查找的痕迹

    var point_record_list = [];
    var color_record_list = [];
    var smallest_area = getCookie("pictureHeight") * getCookie("pictureWidth");
    var smallest_area_num = 0;//recordlist里第几个

    for(let i=0;i<red_obj_list.length;i++){//red
        var the_points = red_obj_list[i];
        var x1 = the_points[0][0];
        var y1 = the_points[0][1];
        var x2 = the_points[1][0];
        var y2 = the_points[1][1];

        if(is_point_in_square(x1,y1,x2,y2,x,y)){
            point_record_list.push(i);
            color_record_list.push(0);
            if(get_area(x1,y1,x2,y2) < smallest_area){
                smallest_area = get_area(x1,y1,x2,y2);
                smallest_area_num = point_record_list.length-1;//下标
            }
        }
    }

    for(let i=0;i<yellow_obj_list.length;i++){//yellow
        var the_points = yellow_obj_list[i];
        var x1 = the_points[0][0];
        var y1 = the_points[0][1];
        var x2 = the_points[1][0];
        var y2 = the_points[1][1];

        if(is_point_in_square(x1,y1,x2,y2,x,y)){
            point_record_list.push(i);
            color_record_list.push(1);
            if(get_area(x1,y1,x2,y2) < smallest_area){
                smallest_area = get_area(x1,y1,x2,y2);
                smallest_area_num = point_record_list.length-1;//下标
            }
        }
    }

    for(let i=0;i<blue_obj_list.length;i++){//blue
        var the_points = blue_obj_list[i];
        var x1 = the_points[0][0];
        var y1 = the_points[0][1];
        var x2 = the_points[1][0];
        var y2 = the_points[1][1];

        if(is_point_in_square(x1,y1,x2,y2,x,y)){
            point_record_list.push(i);
            color_record_list.push(2);
            if(get_area(x1,y1,x2,y2) < smallest_area){
                smallest_area = get_area(x1,y1,x2,y2);
                smallest_area_num = point_record_list.length-1;//下标
            }
        }
    }

    for(let i=0;i<green_obj_list.length;i++){//red
        var the_points = green_obj_list[i];
        var x1 = the_points[0][0];
        var y1 = the_points[0][1];
        var x2 = the_points[1][0];
        var y2 = the_points[1][1];

        if(is_point_in_square(x1,y1,x2,y2,x,y)){
            point_record_list.push(i);
            color_record_list.push(3);
            if(get_area(x1,y1,x2,y2) < smallest_area){
                smallest_area = get_area(x1,y1,x2,y2);
                smallest_area_num = point_record_list.length-1;//下标
            }
        }
    }

    //返回一个面积最小的矩形
    if(point_record_list.length === 0){
        //没有
    }
    else{
    	var color_num = color_record_list[smallest_area_num];
    	var points_num = point_record_list[smallest_area_num];//在对应颜色list里的下标
        var select_points = [];
        global_color_selected = color_num;
        global_obj_num_selected = points_num;
        if(color_num === 0){
            select_points = red_obj_list[points_num];
        }
        else if(color_num === 1){
            select_points = yellow_obj_list[points_num];
        }
        else if(color_num === 2){
            select_points = blue_obj_list[points_num];
        }
        else if(color_num === 3){
            select_points = green_obj_list[points_num];
        }

        var s_x1 = select_points[0][0];
        var s_y1 = select_points[0][1];
        var s_x2 = select_points[1][0];
        var s_y2 = select_points[1][1];
        alert_the_square(s_x1,s_y1,s_x2,s_y2);
    }
}

/**
 * 检查点(x,y)是否在矩形里
 * @param x1
 * @param y1
 * @param x2
 * @param y2
 * @param x
 * @param y
 */
function is_point_in_square(x1,y1,x2,y2,x,y){
    if((x-x1)*(x-x2)>0){
        return false;
    }
    if((y-y1)*(y-y2)>0){
        return false;
    }

    return true;
}

/**
 * 把一个矩形加粗
 */
function alert_the_square(x1,y1,x2,y2){

    global_mark_ptr.ctx.lineWidth = line_width + 2;
    global_mark_ptr.ctx.strokeStyle = "rgb(255,235,235)";
    global_mark_ptr.ctx.strokeRect(x1,y1,x2-x1,y2-y1);
}

/**
 * 删除被选中的矩形
 */
function delete_selected_square(){
    if(global_obj_num_selected === -1){return;}
    if(global_color_selected === 0){
        red_obj_list.splice(global_obj_num_selected,1);
        if(red_obj_list.length === 0){
            $("#tipInput").children("#obj_0").remove();//删除输入框
            red_tip = [];
        }
    }
    if(global_color_selected === 1){
        yellow_obj_list.splice(global_obj_num_selected,1);
        if(yellow_obj_list.length === 0){
            $("#tipInput").children("#obj_1").remove();//删除输入框
            yellow_tip = [];
        }
    }
    if(global_color_selected === 2){
        blue_obj_list.splice(global_obj_num_selected,1);
        if(blue_obj_list.length === 0){
            $("#tipInput").children("#obj_2").remove();//删除输入框
            blue_tip = [];
        }
    }
    if(global_color_selected === 3){
        green_obj_list.splice(global_obj_num_selected,1);
        if(green_obj_list.length === 0){
            $("#tipInput").children("#obj_3").remove();//删除输入框
            green_tip = [];
        }
    }
    global_obj_num_selected = -1;
    global_color_selected = -1;

    //检查 如果一个颜色不存在了，就删除对应对输入框

    supervise_delete_time++;
    print_all_exist_obj();
}

/**
 * 计算返回一个矩形的面积
 * @param x1
 * @param y1
 * @param x2
 * @param y2
 */
function get_area(x1,y1,x2,y2){
    var res = Math.abs((x1-x2)*(y1-y2));
    return res;
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

    //object
    if(red_tip !== []){//red begin
        //red
        var tags = "            <tags>\n";
        for(let j=0;j<red_tip.length;j++){
            var tag = "                <tag>\n"+
                "                    <title>"+red_tip[j].split("_")[0]+"</title>\n"+
                "                    <value>"+red_tip[j].split("_")[1]+"</value>\n"+
                "                </tag>\n";
            tags = tags +tag;
        }
        tags = tags + "            </tags>\n";

        for(let i=0;i<red_obj_list.length;i++){
            var temp_points = red_obj_list[i];
            var x1 = temp_points[0][0];
            var y1 = temp_points[0][1];
            var x2 = temp_points[1][0];
            var y2 = temp_points[1][1];

            var points = "            <points>\n"+
                         "                <x1>"+x1+"</x1>\n"+
                         "                <y1>"+y1+"</y1>\n"+
                         "                <x2>"+x2+"</x2>\n"+
                         "                <y2>"+y2+"</y2>\n"+
                         "            </points>\n";

            var obj = "        <object>\n"+points+tags+
                      "        </object>\n";

            s_objs = s_objs + obj;
        }
    }//red end

    if(yellow_tip !== []){//yellow begin
        //yellow
        var tags = "            <tags>\n";
        for(let j=0;j<yellow_tip.length;j++){
            var tag = "                <tag>\n"+
                "                    <title>"+yellow_tip[j].split("_")[0]+"</title>\n"+
                "                    <value>"+yellow_tip[j].split("_")[1]+"</value>\n"+
                "                </tag>\n";
            tags = tags +tag;
        }
        tags = tags + "            </tags>\n";

        for(let i=0;i<yellow_obj_list.length;i++){
            var temp_points = yellow_obj_list[i];
            var x1 = temp_points[0][0];
            var y1 = temp_points[0][1];
            var x2 = temp_points[1][0];
            var y2 = temp_points[1][1];

            var points = "            <points>\n"+
                "                <x1>"+x1+"</x1>\n"+
                "                <y1>"+y1+"</y1>\n"+
                "                <x2>"+x2+"</x2>\n"+
                "                <y2>"+y2+"</y2>\n"+
                "            </points>\n";

            var obj = "        <object>\n"+points+tags+
                "        </object>\n";

            s_objs = s_objs + obj;
        }
    }//yellow end

    if(blue_tip !== []){//blue begin
        //blue
        var tags = "            <tags>\n";
        for(let j=0;j<blue_tip.length;j++){
            var tag = "                <tag>\n"+
                "                    <title>"+blue_tip[j].split("_")[0]+"</title>\n"+
                "                    <value>"+blue_tip[j].split("_")[1]+"</value>\n"+
                "                </tag>\n";
            tags = tags +tag;
        }
        tags = tags + "            </tags>\n";

        for(let i=0;i<blue_obj_list.length;i++){
            var temp_points = red_obj_list[i];
            var x1 = temp_points[0][0];
            var y1 = temp_points[0][1];
            var x2 = temp_points[1][0];
            var y2 = temp_points[1][1];

            var points = "            <points>\n"+
                "                <x1>"+x1+"</x1>\n"+
                "                <y1>"+y1+"</y1>\n"+
                "                <x2>"+x2+"</x2>\n"+
                "                <y2>"+y2+"</y2>\n"+
                "            </points>\n";

            var obj = "        <object>\n"+points+tags+
                "        </object>\n";

            s_objs = s_objs + obj;
        }
    }//blue end

    if(green_tip !== []){//green begin
        //green
        var tags = "            <tags>\n";
        for(let j=0;j<green_tip.length;j++){
            var tag = "                <tag>\n"+
                "                    <title>"+green_tip[j].split("_")[0]+"</title>\n"+
                "                    <value>"+green_tip[j].split("_")[1]+"</value>\n"+
                "                </tag>\n";
            tags = tags +tag;
        }
        tags = tags + "            </tags>\n";

        for(let i=0;i<green_obj_list.length;i++){
            var temp_points = red_obj_list[i];
            var x1 = temp_points[0][0];
            var y1 = temp_points[0][1];
            var x2 = temp_points[1][0];
            var y2 = temp_points[1][1];

            var points = "            <points>\n"+
                "                <x1>"+x1+"</x1>\n"+
                "                <y1>"+y1+"</y1>\n"+
                "                <x2>"+x2+"</x2>\n"+
                "                <y2>"+y2+"</y2>\n"+
                "            </points>\n";

            var obj = "        <object>\n"+points+tags+
                "        </object>\n";

            s_objs = s_objs + obj;
        }
    }//green end


    //object
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

    var final_s = s1 + s2 + s3 + s4 + s5 + supervise_str + s_objs;
    return final_s;

}

/**
 * 将用户输入放入tiplist里，若有未完成返回false
 */
function auto_submit_tips(){
    var str = "名称_牛_羊_猪,年龄_幼_壮_老";
    var strs = str.split(",");


    if(red_tip !== []){
        //red
        var result_list = [];
        for(let i=0;i<strs.length;i++){
            var title = strs[i].split("_")[0];
            var select_id = "select_0_"+title;
            var tip = $("#"+select_id).val();
            if(tip === place_holder){
                return false;
            }
            else{
                var result = title+"_"+tip;
                result_list.push(result);
            }
        }

        red_tip = [];
        for(let j=0;j<result_list.length;j++){
            red_tip.push(result_list[j]);
        }
        //red end
    }

    if(yellow_tip !== []){
        //yellow
        var result_list = [];
        for(let i=0;i<strs.length;i++){
            var title = strs[i].split("_")[0];
            var select_id = "select_1_"+title;
            var tip = $("#"+select_id).val();
            if(tip === place_holder){
                return false;
            }
            else{
                var result = title+"_"+tip;
                result_list.push(result);
            }
            
            
        }

        yellow_tip = [];
        for(let j=0;j<result_list.length;j++){
            yellow_tip.push(result_list[j]);
        }
        //yellow end
    }

    if(blue_tip !== []){
        //blue
        var result_list = [];
        for(let i=0;i<strs.length;i++){
            var title = strs[i].split("_")[0];
            var select_id = "select_2_"+title;
            var tip = $("#"+select_id).val();
            if(tip === place_holder){
                return false;
            }
            else{
                var result = title+"_"+tip;
                result_list.push(result);
            }
        }

        blue_tip = [];
        for(let j=0;j<result_list.length;j++){
            blue_tip.push(result_list[j]);
        }
        //blue end
    }


    if(green_tip !== []){
        //green
        var result_list = [];
        for(let i=0;i<strs.length;i++){
            var title = strs[i].split("_")[0];
            var select_id = "select_3_"+title;
            var tip = $("#"+select_id).val();
            if(tip === place_holder){
                return false;
            }
            else{
                var result = title+"_"+tip;
                result_list.push(result);
            }
        }

        green_tip = [];
        for(let j=0;j<result_list.length;j++){
            green_tip.push(result_list[j]);
        }
        //green end
    }

    return true;
}

/**
 * 为下一张图片做清理工作
 */
function prepare_for_next_picture(){
    red_obj_list = [];//[[x1,y1],[x2,y2]]
    blue_obj_list = [];//[[x1,y1],[x2,y2]]
    yellow_obj_list = [];//[[x1,y1],[x2,y2]]
    green_obj_list = [];//[[x1,y1],[x2,y2]]
    red_tip = [];
    yellow_tip = [];
    blue_tip = [];
    green_tip = [];
    global_color_selected = -1;//被选中的矩形的颜色
    global_obj_num_selected = -1;//被选中的矩形在相应颜色里的下标

    supervise_delete_time = 0;
    supervise_click_time = 0;
    supervise_start_time = 0;
    var date = new Date();
    supervise_start_time = date.getTime();
    supervise_total_points = 0; //添加的点的数量

    $("#tipInput").empty();
    clear_all();
}