* Vad behövs?
  - git
  - maven
  - JavaEE-container (T.ex. JBoss AS 7.1)
  - MySQL Server (Listening at :3306) med en databas som heter CommentsDB
  - Eclipse, med m2eclipseplugin samt JBoss Tools

* Ladda ner koden
  - git clone https://github.com/folkol/raw_webapp.git

* Skapa en databas som heter CommentsDB 
  - CREATE TABLE comments (id INT NOT NULL AUTO_INCREMENT, alias VARCHAR(100), message VARCHAR(500), PRIMARY KEY(id));
  - GRANT ALL PRIVILEGES on CommentsDB.* to ''@'%';

* Importera projektet i Eclipse
  - Import existing maven project

* Starta webappen
  - Run on Server -> JBoss AS 7.1

* Om Webappen
  - Grundbulten i en java-webapp är en "Servlet". En Servlet är ett container managed object (Alltså ett objekt som inte skapas i din kod via något "new MyClass"-anrop).
  - När containern för ett HTTP-request så letar den upp vilken servlet som ska hantera anropet genom att titta i appens "Deployment descriptor", där finns deklarationer av servlets samt url-mappings för dessa. Appens deployment descriptor utörs av xml-configuration (T.ex. web.xml) samt annotationer pä servletklasserna (@WebServlet). Raw-webapp använder sig bara av web.xml.
  - Om ingen user defined servlet finns inmappad för den efterfrågade url:en så kommer containern att tillhandahålla en "Default Servlet" att hantera fallet, oftast så försöker den bara hitta en fil med motsvarande namn och returnera till browsern.
  - En Servlet definieras av att man skapar en klass som ärver från javax.servlet.http.HttpServlet och sedan överrider någon av metoderna doGet, doPost o.s.v.
  - En Servlet kan antingen skriva HTTP-responsen själv (response.getWriter().println("Hello world");) eller överlåta renderingen av responsen till t.ex. en annan servlet/JSP (request.getRequestDispatcher("WEB-INF/page.jsp").forward(request, response);).
  - Servleten gör information tillgänglig för vyn genom att lägga in java Objects som attribut i requestet (request.setAttribute("identifier", object);). Dessa objekt är hädanefter tillgängliga i request-objektet genom antingen request.getAttribute("identifier") i servlets och filter, eller genom EL-uttryck (${identifier}) i JSP:er. 
  - Appen pratar med MySQL-servern genom en "JDBC-driver". Se koden i CommentsDAO
 
* Ett exempelanrop
  - JBoss lyssnar på TCP-port :8080 samt har raw-webapp.war deployad under web contexten (/raw-webapp)
  - En browser gör ett HTTP GET-request till http://localhost:8080/raw-webapp/index.html
  - JBoss får HTTP-requestet, läser innehållet och letar i appens deployment descriptor efter en resurs som matchar den efterfrågade URL:en.
  - Servleten "Page" har ett urlpattern som matchar. JBoss ser till att "Page" är skapad (Annars skapas ett objekt av typen servlets.PageServlet och dess "init"-metod anropas).
  - Jboss skapar ett java-object av typen javax.servlet.http.HttpServletRequest och fyller i information från HTTP-requestet
  - JBoss skapar ett java-object av typen javax.servlet.http.HttpServletResponse.
  - JBoss gör ett javaanrop till Page-servletens metod "service". HttpServlet-implementationen kommer att titta på anropets METHOD och delegera till någon av doGet, doPost o.s.v.
  - Vår servlet-kod får "doGet" anropad med Request- / Responseobjekten som skapades ovan som parametrar.
  - Vår doGet kommer att fråga CommentDAO-objektet som vi skapade i init-metoden efter en lista på comments, spara denna lista i request-attributet under namnet "comments" och sedan delegera anropet till JSP-sidan "page.jsp".
  - När en jsp-sida efterfrågas så översätter JBoss innehållet i jsp-sidan till Servlet-kod, och kompilerar den sedan. D.v.s. en jsp som innehåller raden "Hello World" kommer att översöttas till en Servlet vars doGet-metod innehåller "response.getWriter.println("Hello World");.
  - Vår jsp renderar outputen, till stor del genom getWriter.println():s, men även genom att anropa kod i TagLibraries (c:forEach till /c:forEach).
  - När jsp-sidan har renderat klart och returnerat så kommer JBoss att skicka HTTP-responsen till browsern genom TCP-socketen.

* Avslutande kommentarer
  - Koden är skriven för att lätt förstå vad som händer, detta på bekostnad av snabbhet och även i vissa fall på bekostnad av robusthet. Koden är inte ett exempel på vad jag anser vara bra JavaEE-kod.
  - Anledningen till att vår page.jsp ligger under "WEB-INF" är att den inte ska vara tillgänglig via browsern, utan bara via request.getRequestDispatcher(). 