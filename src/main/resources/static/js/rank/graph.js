var prompt_value = "";//用来显示隐藏
var prompt_2 = false;

function display_graphs(){
    display_first();
    display_second();
    display_radar();
}


function display_second(){
    var myChart = echarts.init(document.getElementById("second_graph"));
    var userId = getCookie("userId");

    var x_map = ['一月','二月','三月','四月','五月','六月'];
    var finish_counts = [];

    $.ajax({
        url: "/user/endNumbers/"+userId,
        type: "get",
        async:false, //同步
        success: function (data) {//labels+","+accu+","+count+","+bias;
           // alert("FINISH "+data);
            var list = data.split("_");
            for(let i=0 ;i<list.length;i++){
                finish_counts.push(parseInt(list[i]));
            }
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
            data: finish_counts,
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
        prompt_graph_2();
    });


}

function display_radar(){
    var myChart = echarts.init(document.getElementById("third_graph"));
    var userId = getCookie("userId");

    var join_counts = [];
    var finish_counts = [];

    //finish
    $.ajax({
        url: "/user/endNumbers/"+userId,
        type: "get",
        async:false, //同步
        success: function (data) {//labels+","+accu+","+count+","+bias;
           // alert("FINISH "+data);
            var list = data.split("_");
            for(let i=0 ;i<list.length;i++){
                finish_counts.push(parseInt(list[i]));
            }
        }
    });

    //join
    $.ajax({
        url: "/user/joinNumbers/"+userId,
        type: "get",
        async:false, //同步
        success: function (data) {//labels+","+accu+","+count+","+bias;
           // alert("JOIN "+data);
            var list = data.split("_");
            for(let i=0 ;i<list.length;i++){
                join_counts.push(parseInt(list[i]));
            }
        }
    });


    var option = {
        title: {
            text: '加入 Vs 完成'
        },
        tooltip: {},
        legend: {
            data: ['当月加入数量 ', '当月完成数量 ']
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
            indicator: [
                { name:  '一月', max: 10},
                { name: '二月 ', max: 10},
                { name: '三月 ', max: 10},
                { name: '四月 ', max: 10},
                { name: '五月 ', max: 10},
                { name: '六月 ', max: 10}
            ]
        },
        series: [{
            name: '加入项目数 vs 完成项目数 ',
            type: 'radar',
            // areaStyle: {normal: {}},
            data : [
                {
                    value : join_counts,
                    name : '当月加入数量 '
                },
                {
                    value : finish_counts,
                    name : '当月完成数量 '
                }
            ]
        }]
    };

    myChart.setOption(option);
    myChart.on('click', function(params){
    	// alert(params.name);
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

function prompt_graph_2(){
    var userId = getCookie("userId");
    if(prompt_2){//showing,hide
        $("#prompt_area_2").hide();
        prompt_2 = false;
    }
    else{
        $("#prompt_area_2").show();
        prompt_2 = true;

        var indicators = [];
        var accuracys = [];

        $.ajax({
            url: "/user/abilities/"+userId,
            type: "get",
            async:false, //同步
            success: function (data) {
                var labels = data.split(",")[0].split("_");
                var accus = data.split(",")[1].split("_");
                for(let i=0;i<labels.length;i++){
                    var tip = { name: labels[i], max: 100};
                    indicators.push(tip);
                    accuracys.push(parseFloat(accus[i]*100));
                }
            }
        });

        var myChart = echarts.init(document.getElementById("prompt_graph_2"));

        var option = {
            title: {
                text: '准确度能力图'
            },
            tooltip: {},
            legend: {
                data: ['准确度 ']
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
                indicator: indicators
            },
            series: [{
                name: '方向准确度 ',
                type: 'radar',
                // areaStyle: {normal: {}},
                data : [
                    {
                        value : accuracys,
                        name : '准确度 '
                    }
                ]
            }]
        };

        myChart.setOption(option);
    }
}