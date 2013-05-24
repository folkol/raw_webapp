<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>title</title>
    <link rel="stylesheet" href="style.css">
    <script src="script.js"></script>
  </head>
  <body>
    Message board!
    <form action="service/comment" method="POST">
      Alias: <input type="text" name="alias">
      Message: <input type="text" name="message"><br>
      <input type="submit">
    </form>
    <c:forEach var="comment" items="${comments}">
      Pisskorv ${comment.alias} sa ${comment.message}<br />
    </c:forEach>
  </body>
</html>
