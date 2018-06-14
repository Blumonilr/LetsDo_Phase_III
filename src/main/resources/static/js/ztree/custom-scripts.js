/*------------------------------------------------------
    Author : www.webthemez.com
    License: Commons Attribution 3.0
    http://creativecommons.org/licenses/by/3.0/
---------------------------------------------------------  */

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

var setting = {
    view: {
        addHoverDom: addHoverDom,
        removeHoverDom: removeHoverDom,
        selectedMulti: false
    },
    edit: {
        enable: true,
        editNameSelectAll: true,
        showRemoveBtn: true,
        showRenameBtn: true,
        removeTitle:"删除",
        renameTitle:"修改"
    },
    data: {
        simpleData: {
            enable: false
        }
    },
    callback: {
        beforeDrag: beforeDrag,
        beforeRemove: beforeRemove,
        beforeRename: beforeRename,
        onRightClick: OnRightClick
    }
};

function beforeDrag(treeId, treeNodes) {
    return false;
}

function beforeRemove(treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj("treeDemo");
    zTree.selectNode(treeNode);
    if (treeNode.children && treeNode.children.length > 0) {
        var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
        return confirm(msg)
    } else {
        var msg = "确认删除 节点 -- " + treeNode.name + " 吗？";
        return confirm(msg)
    }
}

function beforeRename(treeId, treeNode, newName) {
    if (newName.length == 0) {
        setTimeout(function() {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.cancelEditName();
            alert("节点名称不能为空.");
        }, 0);
        return false;
    }
    return true;
}

function addHoverDom(treeId, treeNode) {
    var sObj = $("#" + treeNode.tId + "_span");
    if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
    var addStr = "<span class='button add' id='addBtn_" + treeNode.tId
        + "' title='添加' onfocus='this.blur();'></span>";
    sObj.after(addStr);
    var btn = $("#addBtn_"+treeNode.tId);
    if (btn) btn.bind("click", function(){
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        if (treeNode.level==2){
            alert("最多创建三层标签树！")
            return;
        }
        var newNode=zTree.addNodes(treeNode, {name:"新建标签"});
        zTree.editName(newNode[0]);
        return false;
    });
};

function removeHoverDom(treeId, treeNode) {
    $("#addBtn_"+treeNode.tId).unbind().remove();
};

function OnRightClick(event, treeId, treeNode) {
    if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
        zTree.cancelSelectedNode();
        showRMenu("root", event.clientX, event.clientY);
    } else if (treeNode && !treeNode.noR) {
        zTree.selectNode(treeNode);
        showRMenu("node", event.clientX, event.clientY);
    }
}

function showRMenu(type, x, y) {
    $("#rMenu ul").show();
    if (type=="root") {
        $("#m_del").hide();
        $("#m_modify").hide();
    } else {
        $("#m_del").show();
        $("#m_modify").show();
    }

    y += document.body.scrollTop;
    x += document.body.scrollLeft;
    rMenu.css({"top":y+"px", "left":x+"px", "visibility":"visible"});

    $("body").bind("mousedown", onBodyMouseDown);
}
function hideRMenu() {
    if (rMenu) rMenu.css({"visibility": "hidden"});
    $("body").unbind("mousedown", onBodyMouseDown);
}
function onBodyMouseDown(event){
    if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length>0)) {
        rMenu.css({"visibility" : "hidden"});
    }
}
function addTreeNode() {
    hideRMenu();
    var newNode = { name:"新建标签"};
    var node;
    if (zTree.getSelectedNodes()[0].level==2){
        alert("最多创建三层标签树！")
        return;
    }
    if (zTree.getSelectedNodes()[0]) {
        node=zTree.addNodes(zTree.getSelectedNodes()[0], newNode);
    } else {
        node=zTree.addNodes(null, newNode);
    }
    zTree.editName(node[0]);
}
function modifyTreeNode(){
    hideRMenu();
    var node=zTree.getSelectedNodes();
    if(node&&node.length>0) {
        zTree.editName(node[0]);
    }
}
function removeTreeNode() {
    hideRMenu();
    var nodes = zTree.getSelectedNodes();
    if (nodes && nodes.length>0) {
        if (nodes[0].children && nodes[0].children.length > 0) {
            var msg = "要删除的节点是父节点，如果删除将连同子节点一起删掉。\n\n请确认！";
            if (confirm(msg)){
                zTree.removeNode(nodes[0]);
            }
        } else {
            var msg = "确认删除 节点 -- " + nodes[0].name + " 吗？";
            if (confirm(msg)){
                zTree.removeNode(nodes[0]);
            }
        }
    }
}

function resetTree() {
    hideRMenu();
    $.fn.zTree.init($("#treeDemo"), setting, zNodes);
}

var zNodes, zTree, rMenu;
function initTree(){
    var projectId=getCookie("projectId");
    var formData = new FormData();
    formData.append("projectId",projectId);
    $.ajax({
        url: "/publisherPage/editTagTree",
        type: "POST",
        data: formData,
        dataType: "text",
        processData: false,
        contentType: false,
        success: function (res) {
            zNodes=JSON.parse(res);
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);
            zTree = $.fn.zTree.getZTreeObj("treeDemo");
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest + "///" + textStatus + "///" + errorThrown + "\n" + "发生了预料之外的错误，请稍后再试或联系开发人员");
        }
    });

    rMenu = $("#rMenu");
};

$(document).ready(function () {
    initFunction();
    initTree();
});

function upload() {
    var tagTree=zTree.getNodes();
    var formData = new FormData();
    formData.append("projectId",getCookie("projectId"));
    formData.append("tagTree",tagTree);
    $.ajax({
        url: "/publisherPage/uploadTagTree",
        type: "POST",
        data: formData,
        dataType: "text",
        processData: false,
        contentType: false,
        success: function (res) {
            alert(res);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            alert(XMLHttpRequest + "///" + textStatus + "///" + errorThrown + "\n" + "发生了预料之外的错误，请稍后再试或联系开发人员");
        }
    });
}

