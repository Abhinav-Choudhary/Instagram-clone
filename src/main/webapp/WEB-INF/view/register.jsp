<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - Instagram</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
        integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
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
                <p class="font-monospace text-secondary">User Successfully Registered !!</p>
            </div>
            <div class="text-center">
                <table class="table table-striped table-bordered">
                    <tbody>
                        <tr>
                            <td><strong>Name</strong></td>
                            <td class="font-monospace">${sessionScope.savedUser.name}</td>
                        </tr>
                        <tr>
                            <td><strong>Email</strong></td>
                            <td class="font-monospace">${sessionScope.savedUser.email}</td>
                        </tr>
                        <tr>
                            <td><strong>Username</strong></td>
                            <td class="font-monospace">${sessionScope.savedUser.username}</td>
                        </tr>
                        <tr>
                            <td><strong>Bio</strong></td>
                            <td class="font-monospace">${sessionScope.savedUser.bio}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="text-center border border-secondary p-3 mt-3">
            <a href="login" class="btn btn-success">Back to Login</a>
        </div>
    </div>
</body>
</html>