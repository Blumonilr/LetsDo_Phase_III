/**
 * 为worker排行榜界面提供逻辑
 */
function loadWorkers(){
	rankByExp();
	//以后会实现别的rank方法
}

function rankBy(){
	var way = $("#rankByWhat").val();
//	alert(way);
	if(way==="exp"){
		rankByExp();
	}
	else{
		rankByAccuracy();
	}
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
				var tip = list[i];//name+"_"+level+"_"+money+"_"+exp+"_"+info+accuracy
				var infos = tip.split("_");
				var name = infos[0];
				var level = infos[1];
				var money = infos[2];
				var exp = infos[3];
				var info = infos[4];
				var accuracy = infos[5];
				
				var txt = "<tr  class='odd gradeX'>"+
                                "<td>"+(i+1)+"</td>"+
                                "<td>"+name+"</td>"+
                                "<td>"+level+"</td>"+
                                "<td>"+exp+"</td>"+
					            "<td>"+accuracy+"</td>"+
							    "<td>"+money+"</td>"+
                                "<td>"+info+"</td>"+
                           "</tr>";
//				alert(txt);
				$("tbody").append(txt);
				
			}
		}
	});
}

function rankByAccuracy(){
	//页面加载时调用
	//加载用户排行榜信息
	$.ajax({
		url: "/rank/rankByAccuracy",
		type: "get",
		success: function(data){
			$("tbody").empty();
			var list = data.split(",");
			var len = list.length;
			for(let i=0 ; i<len ; i++){
				var tip = list[i];//name+"_"+level+"_"+money+"_"+exp+"_"+info
				var infos = tip.split("_");
				var name = infos[0];
				var level = infos[1];
				var money = infos[2];
				var exp = infos[3];
				var info = infos[4];
				var accuracy = infos[5];
				
				var txt = "<tr  class='odd gradeX'>"+
                                "<td>"+(i+1)+"</td>"+
                                "<td>"+name+"</td>"+
                                "<td>"+level+"</td>"+
                                "<td>"+exp+"</td>"+
                                "<td>"+accuracy+"</td>"+
							    "<td>"+money+"</td>"+
                                "<td>"+info+"</td>"+
                           "</tr>";
//				alert(txt);
				$("tbody").append(txt);
				
			}
		}
	});
}