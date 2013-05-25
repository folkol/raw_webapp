* Prerequisites
  - git
  - maven
  - JavaEE-container (Tested with JBoss AS 7.1)
  - MySQL Server (Listening at :3306)

* Create a table called comments, and grant access to everyone.
  - CREATE TABLE comments (id INT NOT NULL AUTO_INCREMENT, name VARCHAR(100), message VARCHAR(500), PRIMARY KEY(id));
  - GRANT ALL PRIVILEGES on CommentsDB.* to ''@'%';

