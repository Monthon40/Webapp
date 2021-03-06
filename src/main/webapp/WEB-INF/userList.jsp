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
    body {

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
                <i class="fa fa-sign-out"></i> &nbsp; Logout</a>
        </div>
    </nav>
    <div class="row">
        <div class="col-12">
            <h3 class="my-3" style="color:yellowgreen"> Welcome, ${username}</h3>
            <p class="my-2" style="color:lightseagreen"><i class="fa fa-calendar"></i> Currently: ${date1}</p>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <c:if test="${not empty message}">
                <c:choose>
                    <c:when test="${hasError}">
                        <div class="alert alert-danger" role="alert">
                            <a><i class="fa fa-times-circle" style="color: red"></i> ${message} </a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-success" role="alert">
                            <a><i class="fa fa-check-circle-o" style="color: green"></i> ${message} </a>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </div>

    <div class="row mb-2">
        <div class="col-12">
            <a class="btn btn-success px-4 " type="button" href="/user/create">
                <i class="fa fa-user-plus"></i> &nbsp; New User</a>
        </div>
    </div>

    <div class="row">
        <div class="col-12">
            <table class="table table-primary table-striped table-bordered table-hover ">
                <thead>
                <tr class="table-secondary">
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
                            <a class="btn btn-info btn-sm" type="button" href="/user/edit?username=${user.username}"><i class="fa fa-cog fa-spin"></i></a>
                            <a class="btn btn-warning btn-sm" type="button" href="/user/password?username=${user.username}"><i class="fa fa-key"></i></a>


                            <c:if test="${currentUser.username != user.username}">
                                <!-- User Confirmation before deleting -->
                                <!-- Button trigger modal -->
                                <button
                                        class="btn btn-danger btn-sm"
                                        type="button" href="/user/delete?username=${user.username}"
                                        data-bs-toggle="modal"
                                        data-bs-target="#delete-modal-${user.id}"
                                >
                                    <i class="fa fa-trash-o"></i>
                                </button>

                                <!-- Modal -->
                                <div class="modal fade" id="delete-modal-${user.id}" tabindex="-1"
                                     aria-labelledby="exampleModalLabel" aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <h5 class="modal-title" id="exampleModalLabel">Confirm deleting user</h5>
                                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                        aria-label="Close"></button>
                                            </div>
                                            <div class="modal-body my-4">
                                                Do you want to delete user <b>${user.display_name} (${user.username})</b>?
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close
                                                </button>
                                                <a class="btn btn-danger" href="/user/delete?username=${user.username}">
                                                    <i class="fa fa-trash-o"></i>Delete
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </td>

                    </tr>
                </c:forEach>

                </tbody>

            </table>
        </div>
    </div>


</div>

</body>
</html>
