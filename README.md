# Customer Feedback App

A simple app where users register, log in and add or edit their feedback. An admin can view, edit and delete all feedback.

## Tech
Spring Boot (Java 17) + H2 database + plain HTML/JS frontend.

## Where is what
- **Frontend:** `src/main/resources/static/index.html` (login, register, feedback and admin pages in one file)
- **Backend:** `src/main/java/com/example/feedback/` (REST API in `ApiController.java`)
- **Database:** H2 in-memory, tables `USERS` and `FEEDBACK`. Config is in `src/main/resources/application.properties`. View it at http://localhost:8080/h2-console (JDBC URL `jdbc:h2:mem:feedbackdb`, user `sa`, empty password).

## Run
```
mvn spring-boot:run
```
Open http://localhost:8080

## Login
- Admin: `admin` / `admin`
- User: register on the Sign Up page
