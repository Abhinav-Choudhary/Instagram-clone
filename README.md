# Instagram Clone

An Instagram clone built with Spring Boot, MySQL, and Hibernate.

## Table of Contents
- About: [#about](https://github.com/Abhinav-Choudhary/Instagram-clone?tab=readme-ov-file#about)
- Features: [#features](https://github.com/Abhinav-Choudhary/Instagram-clone?tab=readme-ov-file#features)
- Roles: [#roles](https://github.com/Abhinav-Choudhary/Instagram-clone?tab=readme-ov-file#roles)
- Technology Stack: [#technology-stack](https://github.com/Abhinav-Choudhary/Instagram-clone?tab=readme-ov-file#technology-stack)
- Installation: [#installation](https://github.com/Abhinav-Choudhary/Instagram-clone?tab=readme-ov-file#installation)
- Usage: [#usage](https://github.com/Abhinav-Choudhary/Instagram-clone?tab=readme-ov-file#usage)
- Database: [#database](https://github.com/Abhinav-Choudhary/Instagram-clone?tab=readme-ov-file#database)

## About
This project is an Instagram clone developed for the CSYE6220 Enterprise Software Design course at Northeastern University. It focuses on core image and post functionalities, including comments, likes, and following.

## Features
- Guest viewing of public posts
- User registration and login with unique usernames and emails
- Password encryption
- Post creation with images, descriptions, locations, and timestamps
- Commenting and liking on posts
- User profiles with pictures, bios, and privacy settings
- Admin panel for user and post management

## Roles
- Guest:
  - View public posts
- Registered User:
  - View public posts
  - Comment, like, and create posts
  - Set account privacy
  - Follow private users
  - Manage profile
- Admin:
  - View all posts (public and private)
  - Delete users and posts
  - Promote users to admins
  - Perform all registered user actions

## Technology Stack
- Backend: Spring Boot, Hibernate, MySQL, MVC design pattern, DAO pattern
- Frontend: JSP

## Installation
- Clone the repository
- Set up a MySQL database named instagramdb
- Configure database credentials in DAO.java
- Run the Spring Boot application

## Usage
- Access the application in a web browser
- Register or log in to interact with posts and features

## Database
The database schema includes the following tables:
- user
- post
- follow
- comment
- postliked