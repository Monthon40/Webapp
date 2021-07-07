<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<title>User Page</title>
<body>
<h2> Welcome, ${username}</h2>
<p>
    ${users}
        <c:forEach var = "user" items ="${users}">
        Item <c:out value = "${user.username}"/><p>
    </c:forEach>
</p>
</body>
</html>