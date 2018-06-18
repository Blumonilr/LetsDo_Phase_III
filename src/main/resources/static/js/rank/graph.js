

function display_graphs(){
    display_first();
    display_second();
}


function display_second(){
    var myChart = echarts.init(document.getElementById("second_graph"));
    var userId = getCookie("userId");

    var x_map = ['一月','二月','三月','四月','五月','六月'];
    var all_datas = [1,2,1,3,1];

    $.ajax({
        url: "/user/pjnumbers/"+userId,
        type: "get",
        async:false, //同步
        success: function (data) {
            //temp
            var num = parseInt(data);
            all_datas.push(num);
        }
    });

    var option = {
        xAxis: {
            type: 'category',
            data: x_map,
        },
        yAxis: {
            type: 'value'
        },
        series: [{
            data: all_datas,
            type: 'line',
            smooth: true
        }]
    };

    myChart.setOption(option);
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
        backgroundColor: '#FDF5E6',
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
                            color: 'rgba(128, 128, 128, 0.5)'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        lineStyle: {
                            color: 'rgba(128, 128, 128, 0.7)'
                        }
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#FFA500',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    myChart.setOption(option);
}