

function display_graphs(){
    display_first();
    display_second();
    display_third();
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

function display_third(){
    var myChart = echarts.init(document.getElementById("third_graph"));
    var userId = getCookie("userId");

    var indicators = [];
    var value_counts = [];
    var value_accu = [];
    var value_bias = [];
    var counts_unit = 0;
    $.ajax({
        url: "/user/abilities/"+userId,
        type: "get",
        async:false, //同步
        success: function (data) {//labels+","+accu+","+count+","+bias;
            console.log("THIRD "+data);
            var label = data.split(",")[0];
            var accu = data.split(",")[1];//0~1
            var count = data.split(",")[2];//number of pic
            var bias = data.split(",")[3];//number of pj

            var label_s = label.split("_");
            var accu_s = accu.split("_");
            var count_s = count.split("_");
            var bias_s = bias.split("_");

            var num_of_labels = label_s.length;

            var max_count=0;
            for(let i=0;i<num_of_labels;i++) {
                var count_tip = parseInt(count_s[i]);
                if(count_tip > max_count){
                    max_count = count_tip;
                }
            }

            var dif = max_count/100;
            counts_unit = 1;

            for(let i=0;i<num_of_labels;i++) {
                var indicator_tip = {name: label_s[i], max: 100};
                indicators.push(indicator_tip);
                var count_tip = parseInt(count_s[i]);
                value_counts.push(count_tip*dif);
                var accu_tip = parseFloat(accu_s[i]) * 100;
                value_accu.push(accu_tip);
            }
        }
    });


    var option = {
        title: {
            text: ''
        },
        tooltip: {},
        legend: {
            data: ["标注图片总数 (单位 "+counts_unit+" 张) ", '准确率(%) ']
        },
        radar: {
            // shape: 'circle',
            name: {
                textStyle: {
                    color: '#fff',
                    backgroundColor: '#999',
                    borderRadius: 3,
                    padding: [3, 5]
                }
            },
            indicator: indicators,
        },
        series: [{
            name: '预算 vs 开销（Budget vs spending）',
            type: 'radar',
            // areaStyle: {normal: {}},
            data : [
                {
                    value : value_counts,
                    name : "标注图片总数 (单位 "+counts_unit+" 张) "
                },
                {
                    value : value_accu,
                    name : '准确率(%) '
                }
            ]
        }]
    };

    myChart.setOption(option);
}