<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Saved User</h1>
        <table border="1">
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Name</th>
                    <th>UserName</th>
                    <th>Password</th>
                    <th>Email</th>
                    <th>Role</th>
                    <th>Bio</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td><c:out value="${requestScope.savedUser.id}"/></td>
                    <td><c:out value="${requestScope.savedUser.name}"/></td>
                    <td><c:out value="${requestScope.savedUser.username}"/></td>
                    <td><c:out value="${requestScope.savedUser.password}"/></td>
                    <td><c:out value="${requestScope.savedUser.email}"/></td>
                    <td><c:out value="${requestScope.savedUser.role}"/></td>
                    <td><c:out value="${requestScope.savedUser.bio}"/></td>
                </tr>
            </tbody>
        </table>
    </body>
</html>
