<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Instagram</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <style>
            .navigation{
                justify-content: center;
            }
            body, html {
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
            .like-comment-comment {
                text-decoration: none;
                cursor: pointer;
                color: black;
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
                        <img src="https://i.imgur.com/zqpwkLQ.png" alt="instagram logo" >
                    </div>
                    <div class="center-nav-buttons">
                        <div class="row mt-3">
                            <div class="d-flex align-items-center navigation">
                                <a class="btn btn-outline-dark nav-buttons" href="login">
                                    <i class="fa-solid fa-arrow-right-to-bracket fa-2x me-2" aria-hidden="true"></i>
                                    <span>Login</span>
                                </a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-1 border-end border-2 border-secondary"></div>
                <div class="col-9 scrollable-col">
                    <c:forEach items="${userPosts}" var="post">
                        <div class="form-container p-3">
                            <div class="border border-secondary rounded-3 p-3 signup-body">
                                <div>
                                    <strong>${post.username}</strong>
                                    <img 
                                    src="data:image/jpeg;base64,${post.base64string}"
                                    width="100%"/>
                                    <p>${post.description}</p>
                                    <p>${post.location}</p>
                                    <p><strong>Created</strong> ${post.createdAt}</p>
                                    <div class="like-comment-container">
                                        <a class="like-comment-comment" href="post_${post.postid}"><i class="fa-solid fa-circle-plus"></i> View Post</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </body>
</html>
