* Install MySQL, make sure it listens on 3306 and has got a database named CommentsDB.

* Create a table called comments, and grant access to everyone.
  - CREATE TABLE comments (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(100), message VARCHAR(500), PRIMARY KEY(id));
  - GRANT ALL PRIVILEGES on CommentsDB.* to ''@'%';

* Start the application.
  - mvn jetty:run