<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
        <!DOCTYPE html>
        <html>

        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            <title>Instagram</title>
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
                integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3"
                crossorigin="anonymous">
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
                    height: 70%;
                    overflow-y: auto;
                }

                .like-comment-container {
                    display: flex;
                }

                .like-post {
                    text-decoration: none;
                    cursor: pointer;
                    color: black;
                }

                .like-post:hover {
                    color: red;
                }

                .guest-go-home {
                    display: flex;
                    justify-content: end;
                    margin-top: 1em;
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
                            <c:choose>
                                <c:when test="${sessionScope.currentUser != null}">
                                    <div class="row mt-3">
                                        <div class="d-flex align-items-center navigation">
                                            <a class="btn btn-outline-dark nav-buttons" href="home">
                                                <i class="fas fa-home fa-2x me-2" aria-hidden="true"></i>
                                                <span>Home</span>
                                            </a>
                                        </div>
                                    </div>
                                    <div class="row mt-3">
                                        <div class="d-flex align-items-center navigation">
                                            <a class="btn btn-outline-dark nav-buttons" href="search">
                                                <i class="fa-solid fa-magnifying-glass fa-2x me-2"
                                                    aria-hidden="true"></i>
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
                                                <a class="btn btn-outline-dark nav-buttons" href="admin">
                                                    <i class="fa-solid fa-user-gear fa-2x me-2" aria-hidden="true"></i>
                                                    <span>Admin</span>
                                                </a>
                                            </div>
                                        </div>
                                    </c:if>
                                    <div class="row mt-3">
                                        <div class="d-flex align-items-center navigation">
                                            <a class="btn btn-outline-dark nav-buttons" href="logout">
                                                <i class="fa-solid fa-arrow-right-to-bracket fa-2x me-2"
                                                    aria-hidden="true"></i>
                                                <span>Logout</span>
                                            </a>
                                        </div>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="row mt-3">
                                        <div class="d-flex align-items-center navigation">
                                            <a class="btn btn-outline-dark nav-buttons" href="login">
                                                <i class="fa-solid fa-arrow-right-to-bracket fa-2x me-2"
                                                    aria-hidden="true"></i>
                                                <span>Login</span>
                                            </a>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="col-1 border-end border-2 border-secondary"></div>
                    <div class="col-9 scrollable-col">
                        <div class="form-container p-3 h-100">
                            <div class="border border-secondary rounded-3 p-3 signup-body h-100">
                                <div class="row h-50">
                                    <div class="col-6">
                                        <img src="/posts/${detailedUserPost.postimagename}" width="100%"
                                            alt="Post Image" />
                                        <c:if test="${detailedUserPost.userid == sessionScope.currentUser.id}">
                                            <a href="confirmdeletepost/${detailedUserPost.postid}/${postDeleteRedirectPath}"
                                                class="like-post">
                                                <i class="fa-solid fa-trash fa-2x mt-3" style="color: red;"></i></a>
                                        </c:if>
                                    </div>
                                    <div class="col-6 h-100">
                                        <strong>${detailedUserPost.username}</strong>
                                        <p>${detailedUserPost.description}</p>
                                        <p>${detailedUserPost.location}</p>
                                        <p><strong>Created</strong> ${detailedUserPost.createdAt}</p>
                                        <div class="like-comment-container">
                                            <c:choose>
                                                <c:when test="${sessionScope.currentUser == null}">
                                                    <p><i class="fa-solid fa-heart"></i> ${likeCount} Likes</p>
                                                </c:when>
                                                <c:when test="${currentUserLiked}">
                                                    <p><a class="like-post"
                                                            href="like/${detailedUserPost.postid}/${sessionScope.currentUser.id}/post_${detailedUserPost.postid}"><i
                                                                class="fa-solid fa-heart"></i> ${likeCount} Likes</a>
                                                    </p>
                                                </c:when>
                                                <c:otherwise>
                                                    <p><a class="like-post"
                                                            href="like/${detailedUserPost.postid}/${sessionScope.currentUser.id}/post_${detailedUserPost.postid}"><i
                                                                class="fa-regular fa-heart"></i> ${likeCount} Likes</a>
                                                    </p>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="scrollable-col">
                                            <c:forEach items="${postComments}" var="comment">
                                                <div class="border-bottom border-secondary">
                                                    <p class="m-0 mt-2"><strong>${comment.username}</strong>
                                                        ${comment.comment}</p>
                                                    <small class="text-secondary">${comment.commentedAt}</small>
                                                </div>
                                            </c:forEach>
                                        </div>
                                        <c:if test="${sessionScope.currentUser != null}">
                                            <div>
                                                <form action="/post" method="POST">
                                                    <div class="row">
                                                        <div class="col-md-8">
                                                            <div class="form-group">
                                                                <input type="text" class="form-control" name="comment"
                                                                    id="comment" placeholder="Comment on Post...">
                                                            </div>
                                                        </div>
                                                        <div class="col-md-4">
                                                            <div class="form-group text-center">
                                                                <button type="submit"
                                                                    class="btn btn-success">Comment</button>
                                                            </div>
                                                        </div>
                                                        <div class="form-group">
                                                            <input type="hidden" class="form-control" name="postid"
                                                                id="postid" value="${detailedUserPost.postid}">
                                                            <input type="hidden" class="form-control" name="userid"
                                                                id="userid" value="${detailedUserPost.userid}">
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                        </c:if>
                                        <c:if test="${sessionScope.currentUser == null}">
                                            <div class="guest-go-home">
                                                <a class="btn btn-success" href="/">Go Home</a>
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </body>

        </html>