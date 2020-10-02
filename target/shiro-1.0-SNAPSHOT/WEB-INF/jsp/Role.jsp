
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%--<jsp:include page="/com/tagg.jsp"/>--%>
<head>
    <title>权限管理</title>

    <link href="../static/hplus/css/bootstrap.min.css" rel="stylesheet">
    <script src="../static/hplus/js/jquery.min.js"></script>
    <script src="../static/hplus/js/bootstrap.min.js"></script>
    <!--bootstraptable的配置-->
    <script src="../static/hplus/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="../static/hplus/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
    <link href="../static/hplus/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <!--验证-->
    <script src="../static/bootstrapvalidator/bootstrapValidator.min.js"></script>
<%--    视图--%>
    <script src="../static/treeview/bootstrap-treeview.min.js"></script>

</head>

<body>
<%--职位信息--%>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">职位列表</h3>
    </div>
    <div class="panel-body">

        <div class="row">
            <div class="col-sm-12">
                <table id="tables"></table>
            </div>
        </div>

    </div>
</div>








<%--分配权限模态框--%>

<div class="modal fade" id="modal-id">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">分配权限</h4>
            </div>
            <div class="modal-body">
                <div id="searchTree"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button onclick="fenpei()" type="button" class="btn btn-primary">分配</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


</body>
<script>
    $(function () {
        <!--校验框架-->
        $('#userform').bootstrapValidator({
            message: 'This value is not valid',
            feedbackIcons: {
                valid: 'glyphicon glyphicon-ok',
                invalid: 'glyphicon glyphicon-remove',
                validating: 'glyphicon glyphicon-refresh'
            },
            fields: {
                roleName: {
                    message: 'The username is not valid',
                    validators: {
                        notEmpty: {
                            message: '职位名称不能为空！'
                        }
                    }
                },
            }
        });

        //表格信息
        $("#tables").bootstrapTable({
            url:'../role/roleList',
            toolbar:'#br',
            height:'400',


            columns:[
                {
                    checkbox:true
                },
                {
                    field:'roleId',
                    title:'序号',
                    align:'center'
                },
                {
                    field:'roleName',
                    title:'职位',
                    align:'center'
                },
                {
                    title:'操作',
                    align:'center',
                    formatter:function (varlue,row,index) {
                        return "<button class='btn btn-info' onclick='dofp("+row.roleId+")'><span class='glyphicon glyphicon-edit'></span>&nbsp;分配权限</button>" +
                            " <button onclick=\"toupdate("+index+")\" class='btn btn-info'><span class='glyphicon glyphicon-pencil'></span>&nbsp;修改</button>"
                            +" <button onclick=\"todel()\" class=\"btn btn-primary\"><span class=\"glyphicon glyphicon-trash\"></span>&nbsp;&nbsp;删除</button>";
                    }
                }
            ]

        })
    });

    var roleid;
    //打开分配权限框
    function dofp(roleId) {
        roleid = roleId;

        $("#modal-id").modal('show');
                //当对话框加载完成以后，展示树形菜单
                $.post('../role/doFenPei',
                    {'roleid':roleId},function (data) {
                        $("#searchTree").treeview({
                            showCheckbox:true,
                            data:data,
                            onNodeChecked: nodeChecked,
                            onNodeUnchecked: nodeUnchecked
                        });
                    })
    }




    function fenpei() {
        var pmid = "";
        //1.
        var cks = $("#searchTree").treeview('getChecked');
        $.each(cks,function (i,n) {
            pmid = pmid + "-" + n.id;
        })

        $.post('../RolePermission/grantPermission',{'roleid':roleid,'pmid':pmid},function (data) {

            if(data.msg == "1"){
                //关闭对话框
                $("#modal-id").modal('hide');
            }



        })
    }



</script>

<script>



    var treeData = [{
        text: "Parent 1",
        nodes: [{
            text: "Child 1",
            nodes: [{
                text: "Grandchild 1"
            }, {
                text: "Grandchild 2",
                nodes: [{
                    text: "Grandchild 2-1",
                    nodes: [{
                        text: "Grandchild 2-1-1"
                    }, {
                        text: "Grandchild 2-2-1",
                    }]
                }, {
                    text: "Grandchild 1-2",
                }]
            }]
        }, {
            text: "Child 2",
            nodes: [{
                text: "Grandchild 2-1"
            }, {
                text: "Grandchild 2-2",
            }]
        }]
    }, {
        text: "Parent 2",
        id:'11111'
    }, {
        text: "Parent 3"
    }, {
        text: "Parent 4"
    }, {
        text: "Parent 5"
    }];



    var nodeCheckedSilent = false;

    function nodeChecked(event, node) {
        if (nodeCheckedSilent) {
            return;
        }
        nodeCheckedSilent = true;
        checkAllParent(node);
        checkAllSon(node);
        nodeCheckedSilent = false;
    }

    var nodeUncheckedSilent = false;

    function nodeUnchecked(event, node) {
        if (nodeUncheckedSilent)
            return;
        nodeUncheckedSilent = true;
        uncheckAllParent(node);
        uncheckAllSon(node);
        nodeUncheckedSilent = false;
    }

    //选中全部父节点
    function checkAllParent(node) {
        $('#searchTree').treeview('checkNode', node.nodeId, {
            silent: true
        });
        var parentNode = $('#searchTree').treeview('getParent', node.nodeId);
        if (!("nodeId" in parentNode)) {
            return;
        } else {
            checkAllParent(parentNode);
        }
    }
    //取消全部父节点
    function uncheckAllParent(node) {
        $('#searchTree').treeview('uncheckNode', node.nodeId, {
            silent: true
        });
        var siblings = $('#searchTree').treeview('getSiblings', node.nodeId);
        var parentNode = $('#searchTree').treeview('getParent', node.nodeId);
        if (!("nodeId" in parentNode)) {
            return;
        }
        var isAllUnchecked = true; //是否全部没选中
        for (var i in siblings) {
            if (siblings[i].state.checked) {
                isAllUnchecked = false;
                break;
            }
        }
        if (isAllUnchecked) {
            uncheckAllParent(parentNode);
        }

    }
    //级联选中所有子节点
    function checkAllSon(node) {
        $('#searchTree').treeview('checkNode', node.nodeId, {
            silent: true
        });
        if (node.nodes != null && node.nodes.length > 0) {
            for (var i in node.nodes) {
                checkAllSon(node.nodes[i]);
            }
        }
    }
    //级联取消所有子节点
    function uncheckAllSon(node) {
        $('#searchTree').treeview('uncheckNode', node.nodeId, {
            silent: true
        });
        if (node.nodes != null && node.nodes.length > 0) {
            for (var i in node.nodes) {
                uncheckAllSon(node.nodes[i]);
            }
        }
    }










</script>

</html>
