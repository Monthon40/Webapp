<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login Webapp</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</head>
<style>
    body{

        background-image: url('https://images.hdqwalls.com/wallpapers/toyota-supra-modified-4k-3r.jpg');
        background-repeat: no-repeat;
        background-size: cover;
        background-attachment: fixed;
    }
</style>
<body>
<div class="container ">
    <nav class="navbar navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="#">SSC - Login Webapp</a>
            <a class="btn btn-warning btn-sm pull-right" type="button" href="/logout">
                <i class="fa fa-sign-out" ></i> &nbsp; Logout</a>
        </div>
    </nav>
    <h3 class="my-4" style="color:yellowgreen"> Welcome, ${username}</h3>
    <p class ="my-2" style="color:lightseagreen"><i class="fa fa-calendar"></i> Currently: ${date1}</p>
    <table class="table table-primary table-striped table-bordered table-hover ">
        <thead>
        <tr class="table-secondary" >
            <th class="py-3">Id</th>
            <th class="py-3">Username</th>
            <th class="py-3">Display Name</th>
            <th class="py-3">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td class="py-3">${user.id}</td>
                <td class="py-3">${user.username}</td>
                <td class="py-3">${user.display_name}</td>
                <td class="align-middle">
                    <button class="btn btn-info btn-sm" type="button"><i class="fa fa-cog fa-spin"></i></button>
                    <button class="btn btn-danger btn-sm" type="button"><i class="fa fa-trash-o"></i></button>
                </td>

            </tr>
        </c:forEach>

        </tbody>

    </table>
</div>

</body>
</html>