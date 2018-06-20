var prompt_value = "";//用来显示隐藏

function display_graphs(){
    display_first();
    display_second();
    display_third();
}


function display_second(){
    var myChart = echarts.init(document.getElementById("second_graph"));
    var userId = getCookie("userId");

    var x_map = ['一月','二月','三月','四月','五月','六月'];
    var all_datas = [1,2,1,2,1];

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

    myChart.on('click', function(params){
        prompt_graph(params.name);
    });
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
        backgroundColor: '#F7F7F7',

        title: {
            text: '我参与了哪些些项目',
            left: 'center',
            top: 20,
            textStyle: {
                color: '#ccc'
            }
        },

        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },

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
                name:'项目数量',
                type:'pie',
                radius : '55%',
                center: ['50%', '50%'],
                data: data_map.sort(function (a, b) { return a.value - b.value; }),
                roseType: 'radius',
                label: {
                    normal: {
                        textStyle: {
                            color: 'rgba(128, 128, 128, 0.9)'
                        }
                    }
                },
                labelLine: {
                    normal: {
                        lineStyle: {
                            color: 'rgba(128, 128, 128, 0.7)'
                        },
                        smooth: 0.2,
                        length: 10,
                        length2: 20
                    }
                },
                itemStyle: {
                    normal: {
                        color: '#FFE4B5',
                        shadowBlur: 200,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                },

                animationType: 'scale',
                animationEasing: 'elasticOut',
                animationDelay: function (idx) {
                    return Math.random() * 200;
                }
            }
        ]
    };

    myChart.setOption(option);//
    myChart.on('click', function(params){
        alert(params.name);
    });


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
            name: '数量Vs准确度',
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
    myChart.on('click', function(params){
    	 alert(params.name);
    });
}


/**
 * 显示一张饼图
 */
function prompt_graph(month){

    if(month === prompt_value){
        $("#prompt_area").hide();
        prompt_value = "";
    }
    else{

        prompt_value = month;
        $("#prompt_area").show();

        var month_num = 1;
        if(month === "六月"){
            month_num = 6;
        }
        else if(month === "一月"){
            month_num = 1;
        }
        else if(month === "二月"){
            month_num = 2;
        }
        else if(month === "三月"){
            month_num = 3;
        }
        else if(month === "四月"){
            month_num = 4;
        }
        else if(month === "五月"){
            month_num = 5;
        }


        var myChart = echarts.init(document.getElementById("prompt_graph"));
        var userId = getCookie("userId");

        var data_map = [];
        $.ajax({
            url: "/user/monthlabels/"+userId,
            type: "get",
            data:{
                "month" : month_num,
            },
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
            backgroundColor: '#F7F7F7',

            title: {
                text: '该月项目类型',
                left: 'center',
                top: 20,
                textStyle: {
                    color: '#ccc'
                }
            },

            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },

            visualMap: {
                show: false,
                min: 80,
                max: 600,
                inRange: {
                    colorLightness: [0.2, 0.8]
                }
            },
            series : [
                {
                    name:'该月项目数量',
                    type:'pie',
                    radius : '55%',
                    center: ['50%', '50%'],
                    data: data_map.sort(function (a, b) { return a.value - b.value; }),
                    roseType: 'radius',
                    label: {
                        normal: {
                            textStyle: {
                                color: 'rgba(128, 128, 128, 0.9)'
                            }
                        }
                    },
                    labelLine: {
                        normal: {
                            lineStyle: {
                                color: 'rgba(255, 255, 255, 0.3)'
                            },
                            smooth: 0.2,
                            length: 10,
                            length2: 20,
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#b0e2ff',
                            shadowBlur: 200,
                            shadowColor: 'rgba(255, 255, 255, 0.3)'
                        }
                    },

                    animationType: 'scale',
                    animationEasing: 'elasticOut',
                    animationDelay: function (idx) {
                        return Math.random() * 200;
                    }
                }
            ]
        };

        myChart.setOption(option);


    }
}