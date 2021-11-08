Welcome to my Demo SportKing API 
This demo application runs on springboot technology (JAVA) . It consists of: 

1. Spring Security for user authentication and authorization
2. JWT Authentication
3. MySql Database connection
4. Flyway for database version control
5. File upload and view capabilities 



Step 1:
Run the application on your compiler or using java -jar <ApplicationName>



Step 2 : 
Define  Roles to the roles table

SQL Query: 

DELETE FROM roles;

INSERT INTO roles(name) VALUES('ROLE_USER');
INSERT INTO roles(name) VALUES('ROLE_MODERATOR');
INSERT INTO roles(name) VALUES('ROLE_ADMIN');


Step 3: 
Check the endpoints on swagger using the url 

http://localhost:8080/swagger-ui.html




[![screenshot.png](https://i.postimg.cc/cLRJKxnG/screenshot.png)](https://postimg.cc/bZvPWjgm)


  
  

