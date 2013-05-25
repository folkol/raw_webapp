* Vad beh�vs?
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
  - Grundbulten i en java-webapp �r en "Servlet". En Servlet �r ett container managed object (Allts� ett objekt som inte skapas i din kod via n�got "new MyClass"-anrop).
  - N�r containern f�r ett HTTP-request s� letar den upp vilken servlet som ska hantera anropet genom att titta i appens "Deployment descriptor", d�r finns deklarationer av servlets samt url-mappings f�r dessa. Appens deployment descriptor utg�rs av xml-configuration (T.ex. web.xml) samt annotationer p� servletklasserna (@WebServlet). Raw-webapp anv�nder sig bara av web.xml.
  - Om ingen user defined servlet finns inmappad f�r den efterfr�gade url:en s� kommer containern att tillhandah�lla en "Default Servlet" att hantera fallet, oftast s� f�rs�ker den bara hitta en fil med motsvarande namn och returnera till browsern.
  - En Servlet definieras av att man skapar en klass som �rver fr�n javax.servlet.http.HttpServlet och sedan �verrider n�gon av metoderna doGet, doPost o.s.v.
  - En Servlet kan antingen skriva HTTP-responsen sj�lv (response.getWriter().println("Hello world");) eller �verl�ta renderingen av responsen till t.ex. en annan servlet/JSP (request.getRequestDispatcher("WEB-INF/page.jsp").forward(request, response);).
  - Servleten g�r information tillg�nglig f�r vyn genom att l�gga in java Objects som attribut i requestet (request.setAttribute("identifier", object);). Dessa objekt �r h�danefter tillg�ngliga i request-objektet genom antingen request.getAttribute("identifier") i servlets och filter, eller genom EL-uttryck (${identifier}) i JSP:er. 
  - Appen pratar med MySQL-servern genom en "JDBC-driver". Se koden i CommentsDAO
 
* Ett exempelanrop
  - JBoss lyssnar p� TCP-port :8080 samt har raw-webapp.war deployad under web contexten (/raw-webapp)
  - En browser g�r ett HTTP GET-request till http://localhost:8080/raw-webapp/index.html
  - JBoss f�r HTTP-requestet, l�ser inneh�llet och letar i appens deployment descriptor efter en resurs som matchar den efterfr�gade URL:en.
  - Servleten "Page" har ett urlpattern som matchar. JBoss ser till att "Page" �r skapad (Annars skapas ett objekt av typen servlets.PageServlet och dess "init"-metod anropas).
  - Jboss skapar ett java-object av typen javax.servlet.http.HttpServletRequest och fyller i information fr�n HTTP-requestet
  - JBoss skapar ett java-object av typen javax.servlet.http.HttpServletResponse.
  - JBoss g�r ett javaanrop till Page-servletens metod "service". HttpServlet-implementationen kommer att titta p� anropets METHOD och delegera till n�gon av doGet, doPost o.s.v.
  - V�r servlet-kod f�r "doGet" anropad med Request- / Responseobjekten som skapades ovan som parametrar.
  - V�r doGet kommer att fr�ga CommentDAO-objektet som vi skapade i init-metoden efter en lista p� comments, spara denna lista i request-attributet under namnet "comments" och sedan delegera anropet till JSP-sidan "page.jsp".
  - N�r en jsp-sida efterfr�gas s� �vers�tter JBoss inneh�ller i jsp-sidan till Servlet-kod, och kompilerar den sedan. D.v.s. en jsp som inneh�ller raden "Hello World" kommer att �vers�ttas till en Servlet vars doGet-metod inneh�ller "response.getWriter.println("Hello World");.
  - V�r jsp renderar outputen, till stor del genom getWriter.println():s, men �ven genom att anropa TagLib-kod (c:forEach till /c:forEach), som i sin tur bara �r javakod.
  - N�r jsp-sidan har renderat klart och returnerat s� kommer JBoss att skicka HTTP-responsen till browsern genom TCP-socketen.

* Avslutande kommentarer
  - Koden �r skriven f�r att l�tt f�rst� vad som h�nder, detta p� bekostnad av snabbhet och �ven i vissa fall p� bekostnad av robusthet. Koden �r inte ett exempel p� vad jag anser vara bra JavaEE-kod.
  - Anledningen till att v�r page.jsp ligger under "WEB-INF" �r att den inte ska vara tillg�nglig via browsern, utan bara via request.getRequestDispatcher(). 