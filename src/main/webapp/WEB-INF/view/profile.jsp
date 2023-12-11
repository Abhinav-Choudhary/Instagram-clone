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
                    height: 100%;
                    overflow-y: auto;
                }

                .profile-pic {
                    border-radius: 50%;
                    width: 70%;
                    height: 70%;
                    object-fit: cover;
                    /* aspect-ratio: 1; */
                }

                .profile-pic-container {
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }

                .posts-banner {
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }

                .image-container {
                    position: relative;
                    overflow: hidden;
                    padding: 5px;
                }

                .image-container img {
                    width: 100%;
                    height: auto;
                    display: block;
                    transition: filter 0.3s ease;
                }

                .image-overlay {
                    position: absolute;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                    justify-content: center;
                    opacity: 0;
                    background: rgba(0, 0, 0, 0.7);
                    color: white;
                    transition: opacity 0.3s ease;
                }

                .image-container:hover .image-overlay {
                    opacity: 1;
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
                                    <a class="btn btn-outline-dark nav-buttons" href="home">
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
                                    <a class="btn btn-outline-primary nav-buttons" href="profile">
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
                                        <i class="fa-solid fa-arrow-right-to-bracket fa-2x me-2" aria-hidden="true"></i>
                                        <span>Logout</span>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-1 border-end border-2 border-secondary"></div>
                    <div class="col-9 scrollable-col">
                        <div class="row mt-3 border-bottom">
                            <div class="col-4 profile-pic-container">
                                <img src="/users/${sessionScope.currentUser.username}.jpg" alt="Profile Picture"
                                    width="100%" class="profile-pic img-thumbnail"
                                    onerror="this.src='/default/user.png'; this.alt='Default Profile Picture';">
                            </div>
                            <div class="col-8">
                                <div class="row">
                                    <div class="col-6">
                                        <strong>${sessionScope.currentUser.username}</strong>
                                    </div>
                                    <div class="col-6">
                                        <a class="btn btn-secondary" href="editProfile">Edit Profile</a>
                                    </div>
                                </div>
                                <div class="row mt-4">
                                    <div class="col">
                                        <strong>${UserPostCount} Posts</strong>
                                    </div>
                                    <div class="col">
                                        <strong>${UserFollowerCount} Followers</strong>
                                    </div>
                                    <div class="col">
                                        <strong>${UserFollowingCount} Following</strong>
                                    </div>
                                </div>
                                <div class="row mt-4">
                                    <strong>${sessionScope.currentUser.name}</strong>
                                </div>
                                <div class="row mt-4">
                                    <p>${sessionScope.currentUser.bio}</p>
                                </div>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="row">
                                <div class="posts-banner">
                                    <span class="border-top border-dark border-3">
                                        <i class="fa-solid fa-table-cells"></i>
                                        <strong> Posts</strong>
                                    </span>
                                </div>
                            </div>
                            <div class="row mt-3 justify-content-evenly">
                                <c:forEach items="${profilePosts}" var="post">
                                    <div class="col-4 image-container">
                                        <a href="post_${post.id}">
                                            <img src="/posts/${sessionScope.currentUser.username}${post.id}.jpg"
                                                alt="Profile Post" width="100%">
                                            <div class="image-overlay">
                                                <p>${post.description}</p>
                                                <p>${post.location}</p>
                                            </div>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </body>

        </html>