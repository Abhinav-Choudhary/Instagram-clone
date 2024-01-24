# Instagram Clone

An Instagram clone built with Spring Boot, MySQL, and Hibernate.

## Table of Contents
1. About: #about
2. Features: #features
3. Roles: #roles
4. Technology Stack: #technology-stack
5. Installation: #installation
6. Usage: #usage
7. Database: #database

## About
This project is an Instagram clone developed for the CSYE6220 Enterprise Software Design course at Northeastern University. It focuses on core image and post functionalities, including comments, likes, and following.

## Features
1. Guest viewing of public posts
2. User registration and login with unique usernames and emails
3. Password encryption
4. Post creation with images, descriptions, locations, and timestamps
5. Commenting and liking on posts
6. User profiles with pictures, bios, and privacy settings
8. Admin panel for user and post management

## Roles
1. Guest:
   1. View public posts
2. Registered User:
   1. View public posts
   2. Comment, like, and create posts
   3. Set account privacy
   4. Follow private users
   5. Manage profile
3. Admin:
   1. View all posts (public and private)
   2. Delete users and posts
   3. Promote users to admins
   4. Perform all registered user actions

## Technology Stack
1. Backend: Spring Boot, Hibernate, MySQL, MVC design pattern, DAO pattern
2. Frontend: JSP

## Installation
1. Clone the repository
2. Set up a MySQL database named instagramdb
3. Configure database credentials in DAO.java
4. Run the Spring Boot application

## Usage
Access the application in a web browser
Register or log in to interact with posts and features

## Database
The database schema includes the following tables:
1. user
2. post
3. follow
4. comment
5. postliked