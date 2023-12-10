<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    <!DOCTYPE html>
    <html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Admin - Instagram</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
            integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
            integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
            crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            .navigation {
                justify-content: center;
            }

            body,
            html {
                height: 100%;
            }

            .center-nav-buttons {
                display: flex;
                flex-direction: column;
                align-items: center;
                justify-content: space-evenly;
                height: 80%;
            }

            .post-image {
                width: 60%;
            }

            .scrollable-col {
                height: 100%;
                overflow-y: auto;
            }

            .like-comment-container {
                display: flex;
            }

            .view-item {
                text-decoration: none;
                cursor: pointer;
                color: black;
            }

            .view-item:hover {
                color: blue;
            }

            .nav-buttons {
                    display: flex;
                    align-items: center;
                }
        </style>
    </head>

    <body>
        <div class="container-fluid h-100">
            <div class="row h-100">
                <div class="col-2">
                    <div class="row mt-4">
                        <img src="https://i.imgur.com/zqpwkLQ.png" alt="instagram logo">
                    </div>
                    <div class="center-nav-buttons">
                        <div class="row mt-3">
                            <div class="d-flex align-items-center navigation">
                                <a class="btn btn-outline-dark nav-buttons" href="/home">
                                    <i class="fas fa-home fa-2x me-2" aria-hidden="true"></i>
                                    <span>Home</span>
                                </a>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="d-flex align-items-center navigation">
                                <a class="btn btn-outline-dark nav-buttons" href="search">
                                    <i class="fa-solid fa-magnifying-glass fa-2x me-2" aria-hidden="true"></i>
                                    <span>Search</span>
                                </a>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="d-flex align-items-center navigation">
                                <a class="btn btn-outline-dark nav-buttons" href="create">
                                    <i class="fa-regular fa-square-plus fa-2x me-2" aria-hidden="true"></i>
                                    <span>Create</span>
                                </a>
                            </div>
                        </div>
                        <div class="row mt-3">
                            <div class="d-flex align-items-center navigation">
                                <a class="btn btn-outline-dark nav-buttons" href="profile">
                                    <i class="fa-solid fa-user fa-2x me-2" aria-hidden="true"></i>
                                    <c:choose>
                                            <c:when test="${sessionScope.currentUser != null}">
                                                <span>${sessionScope.currentUser.username}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span>Profile</span>
                                            </c:otherwise>
                                        </c:choose>
                                </a>
                            </div>
                        </div>
                        <c:if test="${currentUser.role == 'ADMIN'}">
                            <div class="row mt-3">
                                <div class="d-flex align-items-center navigation">
                                    <a class="btn btn-outline-primary nav-buttons" href="admin">
                                        <i class="fa-solid fa-user-gear fa-2x me-2" aria-hidden="true"></i>
                                        <span>Admin</span>
                                    </a>
                                </div>
                            </div>
                        </c:if>
                        <div class="row mt-3">
                            <div class="d-flex align-items-center navigation">
                                <a class="btn btn-outline-dark nav-buttons" href="logout">
                                    <i class="fa-solid fa-arrow-right-to-bracket fa-2x me-2" aria-hidden="true"></i>
                                    <span>Logout</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-1 border-end border-2 border-secondary"></div>
                <div class="col-9 scrollable-col">
                    <div class="border border-secondary p-3 my-2">
                        <div class="text-center">
                            <img src="https://i.imgur.com/zqpwkLQ.png" alt="Instagram logo" class="mb-2">
                            <p class="font-monospace text-secondary">Admin Console</p>
                            <div class="text-center border border-secondary p-3 mt-3">
                                <strong>Users</strong>
                                <div>
                                    <table class="table table-striped table-bordered">
                                        <thead>
                                            <tr class="table-warning">
                                                <th scope="col">#</th>
                                                <th scope="col">Username</th>
                                                <th scope="col">Name</th>
                                                <th scope="col">Admin</th>
                                                <th scope="col">Delete</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${adminAllUsers}" var="user">
                                                <tr>
                                                    <th scope="row">${user.id}</th>
                                                    <td><a href="user_${user.id}" class="view-item">${user.username}</a></td>
                                                    <td>${user.name}</td>
                                                    <c:choose>
                                                        <c:when test="${user.role == 'USER'}">
                                                            <td><a class="btn btn-primary"
                                                                    href="/makeadmin/${user.id}">Make Admin</a></td>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <td><a class="btn btn-secondary"
                                                                    href="/makeuser/${user.id}">Make User</a></td>
                                                        </c:otherwise>
                                                    </c:choose>
                                                    <td><a class="btn btn-danger"
                                                            href="/confirmdeleteuser/${user.id}">Delete</a></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div class="text-center border border-secondary p-3 mt-3">
                                <strong>Posts</strong>
                                <table class="table table-striped table-bordered">
                                    <thead>
                                        <tr class="table-info">
                                            <th scope="col">#</th>
                                            <th scope="col">Description</th>
                                            <th scope="col">Location</th>
                                            <th scope="col">Created At</th>
                                            <th scope="col">Delete</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach items="${adminAllPosts}" var="post">
                                            <tr>
                                                <th scope="row">${post.id}</th>
                                                <td><a href="post_${post.id}" class="view-item">${post.description}</a></td>
                                                <td>${post.location}</td>
                                                <td>${post.createdAt}</td>
                                                <td><a class="btn btn-danger" href="/confirmdeletepost/${post.id}/admin">Delete</a></td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </body>

    </html>