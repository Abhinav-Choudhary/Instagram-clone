<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register - Instagram</title>
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
        .errors-container {
            display: flex;
            flex-direction: column;
        }
    </style>
</head>

<body>
    <div class="form-container">
        <div class="border border-secondary p-3 signup-body">
            <div class="text-center">
                <img src="https://i.imgur.com/zqpwkLQ.png" alt="Instagram logo" class="mb-2">
                <p class="font-monospace text-secondary">Sign up to see photos from your friends.</p>
            </div>
            <form action="/register" method="POST">
                <div class="form-group">
                    <label for="name">Full Name</label>
                    <input type="text" class="form-control" name="name" id="name"
                        placeholder="Enter your full name">
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="text" class="form-control" name="email" id="email"
                        placeholder="Enter your email" required>
                </div>
                <div class="form-group">
                    <label for="username">Username</label>
                    <input type="text" class="form-control" name="username" id="username"
                        placeholder="Enter your username" required>
                </div>
                <div class="form-group">
                    <label for="password">Password</label>
                    <input type="password" class="form-control" name="password" id="password"
                        placeholder="Enter your password" required>
                </div>
                <div class="form-group">
                    <label for="bio">Bio</label>
                    <input type="text" class="form-control" name="bio" id="bio"
                        placeholder="Enter your bio (max 255 characters)"
                        maxlength="255">
                </div>
                <div class="form-group text-center">
                    <button type="submit" class="btn btn-primary">Sign Up</button>
                </div>
                <c:if test="${registerErrors != null}">
                    <div class="form-group">
                        <div class="errors-container">
                            <c:forEach items="${registerErrors}" var="error">
                                <small class="text-danger">${error.getDefaultMessage()}</small>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
            </form>
        </div>
        <div class="text-center border border-secondary p-3 mt-3">
            Have an account? <a href="login" class="link-primary link-offset-2 signup-link">Login</a>
        </div>
    </div>
</body>

</html>