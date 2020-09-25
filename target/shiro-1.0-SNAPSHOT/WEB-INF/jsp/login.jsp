<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 2020/8/27
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath() + "/";
%>
<html>
<head>
    <title>Title</title>
    <base href="<%=basePath%>">
   <script src="static/js/3.1.4.js"></script>
</head>
<body>




<form action="user/login" method="post"  id="myForm">
    <input type="text" name="uname" id="uname">
    <input type="password" name="upass" id="upass">
    <input type="checkbox" name="rememberMe" id="rememberMe">记住我
  <input type="submit"  value="登录">


<button type="button" class="s">登 入</button>
</form>
</body>
<script>
  $(function () {
        $('.s').click(function () {
            var username = $('#username').val();
            var password = $('#password').val();
        /*    alert(username)
            alert(password)*/
            $.ajax({
                url:'user/login',
                type:'post',
                data:$('#myForm').serialize(),
                   /* {
                        username:username,
                        password:password

                    },*/

                dataType:'json',
                success:function (data) {
                    if(data.code==1){
                        alert("登录成功");
                        window.location = 'user/main';
                    }else if(data.code==2){
                        alert("用户名不存在")
                    }else if(data.code==3){
                        alert("密码错误")
                    }
                }
            })
        })
    })


</script>
</html>
