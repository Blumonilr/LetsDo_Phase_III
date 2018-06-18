

function display_graphs(){
    display_first();
}


function display_first(){
    var myChart = echarts.init(document.getElementById("first_graph"));
    var userId = getCookie("userId");

    var data_map = [];
    $.ajax({
        url: "/user/labels/"+userId,
        type: "get",
        async:false, //同步
        success: function (data) {
            console.log(data);
            var datas = data.split(",");
            var len = datas.length;
            for(let i=0;i<len;i++){
                var tip = {value:parseInt(datas[i].split("_")[1]), name: datas[i].split("_")[0]};
                data_map.push(tip);
            }
        }
    });

    var option = {
        backgroundColor: '#FFF0F5',
        visualMap: {
            show: false,
            min: 80,
            max: 600,
            inRange: {
                colorLightness: [0.5, 1]
            }
        },
        series : [
            {
                name: '访问来源',
                type: 'pie',
                radius: '55%',
                data:data_map,
                roseType: 'angle',
                label: {
                    normal: {
                        textStyle: {
                            color: 'rgba(255, 150, 180, 0.3)'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        lineStyle: {
                            color: 'rgba(255, 150, 180, 0.8)'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#87CEFA',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    myChart.setOption(option);
}