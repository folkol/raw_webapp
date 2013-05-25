<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>Simple chat</title>
    <link rel="stylesheet" href="css/style.css">
  </head>
  <body>
    <div class="container">
      <div class="header">
        <form action="service/comment" method="POST">
          <span>Alias: <input type="text" name="alias"></span>
          <span>Message: <input type="text" name="message"></span>
          <span><input type="submit"></span>
        </form>
      </div>
      <div class="board">
          <c:forEach var="comment" items="${comments}">
            <div>[${comment.alias}] ${comment.message}</div>
          </c:forEach>
      </div>
    </div>
  </body>
</html>
