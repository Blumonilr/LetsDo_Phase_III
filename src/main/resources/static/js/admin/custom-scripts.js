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

    var dataSet = [
        ["Tiger Nixon", "System Architect", "Edinburgh", "5421", "2011/04/25", "$320,800"],
        ["Garrett Winters", "Accountant", "Tokyo", "8422", "2011/07/25", "$170,750"],
        ["Ashton Cox", "Junior Technical Author", "San Francisco", "1562", "2009/01/12", "$86,000"],
        ["Cedric Kelly", "Senior Javascript Developer", "Edinburgh", "6224", "2012/03/29", "$433,060"],
        ["Airi Satou", "Accountant", "Tokyo", "5407", "2008/11/28", "$162,700"],
        ["Brielle Williamson", "Integration Specialist", "New York", "4804", "2012/12/02", "$372,000"],
        ["Herrod Chandler", "Sales Assistant", "San Francisco", "9608", "2012/08/06", "$137,500"],
        ["Rhona Davidson", "Integration Specialist", "Tokyo", "6200", "2010/10/14", "$327,900"],
        ["Colleen Hurst", "Javascript Developer", "San Francisco", "2360", "2009/09/15", "$205,500"],
        ["Sonya Frost", "Software Engineer", "Edinburgh", "1667", "2008/12/13", "$103,600"],
        ["Jena Gaines", "Office Manager", "London", "3814", "2008/12/19", "$90,560"],
        ["Quinn Flynn", "Support Lead", "Edinburgh", "9497", "2013/03/03", "$342,000"],
        ["Charde Marshall", "Regional Director", "San Francisco", "6741", "2008/10/16", "$470,600"],
        ["Haley Kennedy", "Senior Marketing Designer", "London", "3597", "2012/12/18", "$313,500"],
        ["Tatyana Fitzpatrick", "Regional Director", "London", "1965", "2010/03/17", "$385,750"],
        ["Michael Silva", "Marketing Designer", "London", "1581", "2012/11/27", "$198,500"],
        ["Paul Byrd", "Chief Financial Officer (CFO)", "New York", "3059", "2010/06/09", "$725,000"],
        ["Gloria Little", "Systems Administrator", "New York", "1721", "2009/04/10", "$237,500"],
        ["Bradley Greer", "Software Engineer", "London", "2558", "2012/10/13", "$132,000"],
        ["Dai Rios", "Personnel Lead", "Edinburgh", "2290", "2012/09/26", "$217,500"],
        ["Jenette Caldwell", "Development Lead", "New York", "1937", "2011/09/03", "$345,000"],
        ["Yuri Berry", "Chief Marketing Officer (CMO)", "New York", "6154", "2009/06/25", "$675,000"],
        ["Caesar Vance", "Pre-Sales Support", "New York", "8330", "2011/12/12", "$106,450"],
        ["Doris Wilder", "Sales Assistant", "Sidney", "3023", "2010/09/20", "$85,600"],
        ["Angelica Ramos", "Chief Executive Officer (CEO)", "London", "5797", "2009/10/09", "$1,200,000"],
        ["Gavin Joyce", "Developer", "Edinburgh", "8822", "2010/12/22", "$92,575"],
        ["Jennifer Chang", "Regional Director", "Singapore", "9239", "2010/11/14", "$357,650"],
        ["Brenden Wagner", "Software Engineer", "San Francisco", "1314", "2011/06/07", "$206,850"],
        ["Fiona Green", "Chief Operating Officer (COO)", "San Francisco", "2947", "2010/03/11", "$850,000"],
        ["Shou Itou", "Regional Marketing", "Tokyo", "8899", "2011/08/14", "$163,000"],
        ["Michelle House", "Integration Specialist", "Sidney", "2769", "2011/06/02", "$95,400"],
        ["Suki Burks", "Developer", "London", "6832", "2009/10/22", "$114,500"],
        ["Prescott Bartlett", "Technical Author", "London", "3606", "2011/05/07", "$145,000"],
        ["Gavin Cortez", "Team Leader", "San Francisco", "2860", "2008/10/26", "$235,500"],
        ["Martena Mccray", "Post-Sales support", "Edinburgh", "8240", "2011/03/09", "$324,050"],
        ["Unity Butler", "Marketing Designer", "San Francisco", "5384", "2009/12/09", "$85,675"]
    ];

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
                    return '<a href="#" target="_blank">' + data + '</a>';
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
                    return '<a href="#" target="_blank">' + data + '</a>';
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
});

