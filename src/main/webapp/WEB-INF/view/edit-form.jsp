<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit Profile - Instagram</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css"
        integrity="sha512-DTOQO9RWCH3ppGqcWaEA1BIZOC6xxalwEsw9c2QQeAIftl+Vegovlnee1c9QX4TctnWMn13TZye+giMm8e2LwA=="
        crossorigin="anonymous" referrerpolicy="no-referrer" />
    <style>
        body {
            display: flex;
            align-items: center;
            justify-content: center;
            height: 100vh;
            margin: 0;
        }

        .form-container {
            width: 50%;
            margin: 0 auto;
            padding: 2em;
        }

        .form-group {
            margin-bottom: 1em;
        }

        .form-control {
            border-radius: 4px;
            box-shadow: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
        }

        .btn-primary {
            background-color: #385899;
            border-color: #385899;
            width: 100%;
        }

        .btn-primary:hover {
            background-color: #283e7c;
            border-color: #283e7c;
        }
        .signup-link {
            text-decoration: none;
        }
        .signup-body {
            margin-top: 6em;
        }
    </style>
</head>

<body>
    <div class="form-container">
        <div class="border border-secondary p-3 signup-body">
            <div class="text-center">
                <img src="https://i.imgur.com/zqpwkLQ.png" alt="Instagram logo" class="mb-2">
                <p class="font-monospace text-secondary">Update your details</p>
            </div>
            <form action="/profile" method="POST" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" class="form-control" name="name" id="name"
                        placeholder="Enter your full name"
                        value="${sessionScope.currentUser.name}">
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="text" class="form-control" name="email" id="email"
                        placeholder="Enter your email"
                        value="${sessionScope.currentUser.email}" disabled>
                </div>
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" name="username" id="username"
                        placeholder="Enter your username"
                        value="${sessionScope.currentUser.username}" disabled>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" name="password" id="password"
                        placeholder="Enter your password"
                        value="${sessionScope.currentUser.password}" required>
                </div>
                <div class="form-group">
                    <label for="bio">Bio</label>
                    <input type="text" class="form-control" name="bio" id="bio"
                        placeholder="Enter your bio (max 255 characters)"
                        value="${sessionScope.currentUser.bio}"
                        maxlength="255">
                </div>
                <div class="form-group">
                    <label for="profilepicture">Profile Picture</label>
                    <input type="file" class="form-control" path="profilepicture" name="profilepicture"
                                                id="profilepicture" placeholder="Profile Picture" accept="image/png, image/jpeg"/>
                </div>
                <div class="form-group">
                    <label for="visibility" class="form-label">Visibility</label>
                    <select class="form-select" id="visibility" name="visibility">
                        <c:choose>
                            <c:when test="${sessionScope.currentUser.visibility eq 'PRIVATE'}">
                                <option value="PUBLIC">Public</option>
                                <option value="PRIVATE" selected>Private</option>
                            </c:when>
                            <c:otherwise>
                                <option value="PUBLIC" selected>Public</option>
                                <option value="PRIVATE">Private</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </div>
                <div class="form-group text-center">
                    <button type="submit" class="btn btn-success">Save</button>
                </div>
                <c:if test="${editUserErrors != null}">
                    <div class="form-group">
                        <c:forEach items="${editUserErrors}" var="error">
                            <small class="text-danger">${error.getDefaultMessage()}</small>
                        </c:forEach>
                    </div>
                </c:if>
            </form>
        </div>
        <div class="text-center border border-secondary p-3 mt-3">
            <a href="profile" class="btn btn-secondary">Back to Profile</a>
        </div>
    </div>
</body>

</html>