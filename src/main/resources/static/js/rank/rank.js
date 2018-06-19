/**
 * 为worker排行榜界面提供逻辑
 */
function loadWorkers(){
	rankByExp();
	//以后会实现别的rank方法
	setLabels();
}


function rankByExp(){
	//页面加载时调用
	//加载用户排行榜信息
	$.ajax({
		url: "/rank/rankByExp",
		type: "get",
		success: function(data){
			$("tbody").empty();
			var list = data.split(",");
			var len = list.length;
			for(let i=0 ; i<len ; i++){
				var tip = list[i];//name level money exp
				var infos = tip.split("_");
				var name = infos[0];
				var level = infos[1];
				var money = infos[2];
				var exp = infos[3];

				
				var txt = "<tr  class='odd gradeX'>"+
                                "<td>"+(i+1)+"</td>"+
                                "<td>"+name+"</td>"+
                                "<td>"+level+"</td>"+
                                "<td>"+exp+"</td>"+
							    "<td>"+money+"</td>"+
                           "</tr>";
				$("#table_exp_body").append(txt);
				
			}
		}
	});
}

function setLabels(){
	$.ajax({
        url: "/rank/getLabels",
        type: "get",
		success: function (data) {
			var labels = data.split("_");
			var len = labels.length;
			console.log(labels);

			for(let i=0;i<len&&i<5;i++){
				//添加一个表

				var txt = "<table class='table table-striped table-bordered table-hover table_accuracy' id='table_accuracy_'"+labels[i]+">\n" +
                    "                                <h4> 项目类型技艺高超榜 "+labels[i]+"</h4>\n" +
                    "                                <thead>\n" +
                    "                                <tr>\n" +
                    "                                    <th>名次</th>\n" +
                    "                                    <th>用户名</th>\n" +
                    "                                    <th>准确率</th>\n" +
                    "                                </tr>\n" +
                    "                                </thead>\n" +
                    "                                <tbody id='table_body_accuracy_'"+labels[i]+"'>\n" +
                    "\n" +
                    "\n" +
                    "                                </tbody>\n" +
                    "                            </table>";

				$("#accuracy_tables").append(txt);

				rankByLabel(labels[i]);

			}
        }
	})
}

function rankByLabel(label){
    $.ajax({
        url: "/rank/rankByAccuracy/"+label,
        type: "get",
        success: function(data){
            $("#table_body_accuracy_"+label).empty();
            var list = data.split(",");
            var len = list.length;
            for(let i=0 ; i<len ; i++){
                var tip = list[i];//name level money exp
                var infos = tip.split("_");
                var name = infos[0];
                var accu = infos[1];


                var txt = "<tr  class='odd gradeX'>"+
                    "<td>"+(i+1)+"</td>"+
                    "<td>"+name+"</td>"+
                    "<td>"+accu+"</td>"+
                    "</tr>";
                $("#table_body_accuracy_"+label).append(txt);

            }
        }
    });
}

