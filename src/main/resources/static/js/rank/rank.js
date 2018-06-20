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
			    var tip = " <option value='"+labels[i]+"'>"+labels[i]+"</option>"
				$("#selector_label").append(tip);

			}
        }
	});
}

function rankByLabel(){
	var label = $("#selector_label").val();

    $.ajax({
        url: "/rank/rankByAccuracy/"+label,
        type: "get",
        success: function(data){
			alert(data);
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

                $("#table_accuracy_body").empty();
                $("#table_accuracy_body").append(txt);
            }
        }
    });
}

