<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: root
  Date: 2020/8/27
  Time: 14:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<a  style="font-size: 12px" href="../logout">安全退出</a>

登录名:${sessionScope.user.username}
职务:${sessionScope.user.role.roleName}
<br>



<c:forEach items="${list2}" var="per">
        ${per.pName}
    <br>
    <c:forEach var="per1" items="${list1}">
        <ul class="nav nav-second-level">
            <c:if test="${per1.parentid == per.permissionId}">

                <a class="J_menuItem" href="${per1.url}">${per1.pName}</a>
            </c:if>
        </ul>

    </c:forEach>
</c:forEach>










</body>
</html>
