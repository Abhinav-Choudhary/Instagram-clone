<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Search - Instagram</title>
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
                <p class="font-monospace text-secondary">Search for Users</p>
            </div>
            <form action="/search" method="POST">
                <div class="form-group">
                    <label for="username">Username or Email</label>
                    <input type="text" 
                    class="form-control" 
                    name="username" 
                    id="username"
                    placeholder="Search by Username or Email"
                    required>
                </div>
                <div class="form-group text-center">
                    <button type="submit" class="btn btn-success">Search</button>
                </div>
                <c:if test="${searchErrors != null}">
                    <div class="form-group">
                        <c:forEach items="${searchErrors}" var="error">
                            <small class="text-danger">${error.getDefaultMessage()}</small>
                        </c:forEach>
                    </div>
                </c:if>
            </form>
        </div>
        <div class="text-center border border-secondary p-3 mt-3">
            <a href="home" class="btn btn-secondary">Back to Home</a>
        </div>
    </div>
</body>

</html>