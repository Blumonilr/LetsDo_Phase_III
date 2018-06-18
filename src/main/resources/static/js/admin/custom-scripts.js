function initFunction() {
    /*MENU
    ------------------------------------*/
    $('#main-menu').metisMenu();

    $(window).bind("load resize", function () {
        if ($(this).width() < 768) {
            $('div.sidebar-collapse').addClass('collapse')
        } else {
            $('div.sidebar-collapse').removeClass('collapse')
        }
    });

    $("#sideNav").click(function(){
        if($(this).hasClass('closed')){
            $('.navbar-side').animate({left: '-0px'});
            $(this).removeClass('closed');
            $('#page-wrapper').animate({'margin-left' : '260px'});

        }
        else{
            $(this).addClass('closed');
            $('.navbar-side').animate({left: '-260px'});
            $('#page-wrapper').animate({'margin-left' : '0px'});
        }
    });
}

/*-----------------------------Tabs--------------------------------*/

function resetTabs(){
    $("#content > div").hide(); //Hide all content
    $("#tabs a").attr("id",""); //Reset id's
}

var myUrl = window.location.href; //get URL
var myUrlTab = myUrl.substring(myUrl.indexOf("#")); // For mywebsite.com/tabs.html#tab2, myUrlTab = #tab2
var myUrlTabName = myUrlTab.substring(0,4); // For the above example, myUrlTabName = #tab

function initTabs(){
    $("#content > div").hide(); // Initially hide all content
    $("#tabs li:first a").attr("id","current"); // Activate first tab
    $("#content > div:first").fadeIn(); // Show first tab content

    $("#tabs a").on("click",function(e) {
        e.preventDefault();
        if ($(this).attr("id") == "current"){ //detection for current tab
            return
        }
        else{
            resetTabs();
            $(this).attr("id","current"); // Activate this
            $($(this).attr('name')).fadeIn(); // Show content for current tab
        }
    });

    for (i = 1; i <= $("#tabs li").length; i++) {
        if (myUrlTab == myUrlTabName + i) {
            resetTabs();
            $("a[name='"+myUrlTab+"']").attr("id","current"); // Activate url tab
            $(myUrlTab).fadeIn(); // Show url tab content
        }
    }
}


/*-----------------------------initOverview--------------------------------*/
var systemInfo;

function initOverview() {
    $("#publisherNum").text(systemInfo.publisherNum);
    $("#workerNum").text(systemInfo.workerNum);
    $("#historyProjectNum").text(systemInfo.historyProjectNum);
    $("#ongoingProjectNum").text(systemInfo.ongoingProjectNum);
}

/*-----------------------------Tables--------------------------------*/


function initTables() {
    function workerFormat ( d ) {
        // `d` is the original data object for the row
        var ability=d.abilities;
        var result='<table cellpadding="5" cellspacing="0" border="0" style="padding-left:50px;">'+
            '<tr>'+
                '<td>余额:</td>'+
                '<td>'+d.money+'</td>'+
            '</tr>'+
            '<tr>'+
                '<td>领域准确度:</td>'+
            '</tr>';
        for(var i=0;i<ability.length;i++){
            result+='<tr>'+
                '<td>'+ability[i].label.name+':</td>'+
                '<td>'+ability[i].accuracy+'%</td>'+
                '<td>'+ability[i].bias+'次</td>'+
                '</tr>';
        }
        result+='</table>';
        return result;
    }

    var optionTop={
        data: systemInfo.workerTop100,
        language: {
            "lengthMenu": "每页显示 _MENU_ 条记录",
            "zeroRecords": "无条目 - sorry",
            "info": "正在显示_PAGES_页中的第 _PAGE_页",
            "infoEmpty": "No records available",
            "infoFiltered": "(filtered from _MAX_ total records)",
            "processing": "正在加载...",
            "infoPostFix": "",
            "search": "搜索:",
            "url": "",
            "paginate": {
                "first":    "首页",
                "previous": "上页",
                "next":     "下页",
                "last":     "末页"
            }
        },
        pagingType:"full_numbers",
        columns: [
            {
                title: "排名",
                orderable:false},
            {
                title: "昵称",
                orderable:false
            },
            {
                title: "擅长领域",
                orderable:false
            },
            {
                title: "总历史准确度",
                orderable:false
            },
            {
                title: "历史项目数",
                orderable:false
            },
            {
                title: "进行中项目数",
                orderable:false
            }
        ]
    };
    var optionWorker={
        data: systemInfo.workerList,
        language: {
            "lengthMenu": "每页显示 _MENU_ 条记录",
            "zeroRecords": "无条目 - sorry",
            "info": "正在显示_PAGES_页中的第 _PAGE_页",
            "infoEmpty": "No records available",
            "infoFiltered": "(filtered from _MAX_ total records)",
            "processing": "正在加载...",
            "infoPostFix": "",
            "search": "搜索:",
            "url": "",
            "paginate": {
                "first":    "首页",
                "previous": "上页",
                "next":     "下页",
                "last":     "末页"
            }
        },
        pagingType:"full_numbers",
        columns: [
            {
                "className":      'details-control',
                "orderable":      false,
                "data":           null,
                "defaultContent": ''
            },
            {
                title: "昵称",
                data:"name",
                orderable:false,
                render: function(data, type, row, meta) {
                    //渲染 把数据源中的标题和url组成超链接
                    return '<a href="#">' + data + '</a>';
                }
            },
            {
                title: "等级",
                data:"level"
            },
            {
                title: "标注总数",
                data:"tagNum"
            }
            // {
            //     title: "邮箱",
            //     data:"email"
            // }
        ]
    };
    var optionPublisher={
        data: systemInfo.publisherList,
        language: {
            "lengthMenu": "每页显示 _MENU_ 条记录",
            "zeroRecords": "无条目 - sorry",
            "info": "正在显示_PAGES_页中的第 _PAGE_页",
            "infoEmpty": "No records available",
            "infoFiltered": "(filtered from _MAX_ total records)",
            "processing": "正在加载...",
            "infoPostFix": "",
            "search": "搜索:",
            "url": "",
            "paginate": {
                "first":    "首页",
                "previous": "上页",
                "next":     "下页",
                "last":     "末页"
            }
        },
        pagingType:"full_numbers",
        columns: [
            {
                title: "昵称",
                orderable:false,
                data:"name",
                render: function(data, type, row, meta) {
                    //渲染 把数据源中的标题和url组成超链接
                    return '<a href="#">' + data + '</a>';
                }
            },
            // {
            //     title: "邮箱",
            //     data:"email"
            // },
            {
                title: "余额",
                data:"money"
            }
        ]
    };


    $('#workerTop100').DataTable(optionTop);
    var workerTable=$('#workers').DataTable(optionWorker);
    $('#publishers').DataTable(optionPublisher);


    // Add event listener for opening and closing details
    $('#workers tbody').on('click', 'td.details-control', function () {
        var tr = $(this).closest('tr');
        var row = workerTable.row( tr );

        if ( row.child.isShown() ) {
            // This row is already open - close it
            row.child.hide();
            tr.removeClass('shown');
        }
        else {
            // Open this row
            row.child( workerFormat(row.data()) ).show();
            tr.addClass('shown');
        }
    } );
}

/*-----------------------------Charts--------------------------------*/

function initCharts() {
    // 基于准备好的dom，初始化echarts实例
    var workerRegChart = echarts.init(document.getElementById('workerRegChart'),"light");
    var publisherRegChart = echarts.init(document.getElementById('publisherRegChart'),"light");


// 指定图表的配置项和数据
    var workerNumOption = {
        tooltip: {
            trigger: 'axis'
        },
        dataset:{
            source:systemInfo.workerRegNum
        },
        legend: {},
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: {
            type: 'category'
        },
        yAxis: [
            {
                type: 'value',
                name: '当月注册人数',
                max: 500
            },
            {
                type: 'value',
                name: '总人数'
            }
        ],
        series: [
            {
                name: '当月注册人数',
                type: 'bar',
                stack: '总量',
                yAxisIndex:0
            },
            {
                name: '当月总人数',
                type: 'line',
                stack: '总量',
                yAxisIndex:1
            }
        ]
    };

// 使用刚指定的配置项和数据显示图表。
    workerRegChart.setOption(workerNumOption);
    workerNumOption.dataset.source=systemInfo.publisherRegNum
    publisherRegChart.setOption(workerNumOption);

}

/*-----------------------------init--------------------------------*/
$(document).ready(function () {
    $.ajax({
        url: "/admin/systemDetail",
        type: "post",
        dataType:"json",
        async:false,
        data:{"projectId":getCookie("projectId")},
        success : function(info){
            systemInfo=info;
        },
        error : function(XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest+"///"+textStatus+"///"+errorThrown);
        },
    });

    initFunction();
    initTabs();
    initTables();
    initOverview();
    initCharts();
});

