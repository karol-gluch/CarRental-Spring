<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<% request.setAttribute("isAdmin", request.isUserInRole("ADMIN")); %>
<!DOCTYPE html>
<html>
      <head>
          <meta charset="UTF-8">
          <title>G&G CarRent - Wypożyczalnia samochodów</title>
          <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
          <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
          <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
          <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
          <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
      </head>
      <body>
          <div id="header">
                <img src="<c:url value="/resources/images/logo.png" />" alt="logo" />
          </div>

          <div id = "aside">
              <ul>
                  <c:if test="${isAdmin}">
                      <li><a href="${contextPath}/adminPanel">Panel administratora</a></li>
                  </c:if>
                  <li><a href="${contextPath}/index">Strona Główna</a></li>
                  <li><a class="active" href="${contextPath}/flota">Flota</a></li>
                  <li><a href="${contextPath}/ofirmie">O firmie</a></li>
                  <li><a href="${contextPath}/kontakt">Kontakt</a></li>

                  <c:if test="${pageContext.request.userPrincipal.name != null}">
                      <form id="logoutForm" method="POST" action="${contextPath}/logout">
                      </form>
                      <center>
                          <h6 style = "color: white">Jesteś zalogowany jako: <b>${pageContext.request.userPrincipal.name}</b></h6>
                          <button type="button" class="btn btn-dark" onclick="document.forms['logoutForm'].submit()">Wyloguj się
                          </button>
                      </center>
                  </c:if>

              </ul>
              <c:if test="${pageContext.request.userPrincipal.name == null}">
              <center>
                  <button type="button" class="btn btn-light" data-toggle="modal" data-target="#myModalLogin">
                      Zaloguj się
                  </button>
                  <button type="button" class="btn btn-dark" data-toggle="modal" data-target="#myModalRegister">
                      Rejestracja
                  </button>
              </center>
              </c:if>
          </div>


          
          <div id = "main">
              <br><h1>Nasza flota:</h1>
              <table id="cars">
              <tr>
                <th>Marka</th>
                <th>Rok produkcji</th>
                <th>Cena za dobę</th>
              </tr>
              <tr>
                <td>Audi A6</td>
                <td>2018</td>
                <td>440zł</td>
              </tr>
              <tr>
                <td>Mercedes C63</td>
                <td>2016</td>
                <td>320zł</td>
              </tr>
              <tr>
                <td>Volkswagen Arteon</td>
                <td>2018</td>
                <td>280zł</td>
              </tr>
              <tr>
                <td>Audi S3</td>
                <td>2019</td>
                <td>400zł</td>
              </tr>
              <tr>
                <td>Audi RS4</td>
                <td>2019</td>
                <td>520zł</td>
              </tr>
              <tr>
                <td>BMW M3</td>
                <td>2016</td>
                <td>430zł</td>
              </tr>
              <tr>
                <td>Toyota Avensis</td>
                <td>2018</td>
                <td>220zł</td>
              </tr>
              <tr>
                <td>Audi S6</td>
                <td>2017</td>
                <td>320zł</td>
              </tr>
              <tr>
                <td>BMW M4</td>
                <td>2018</td>
                <td>550zł</td>
              </tr>
              <tr>
                <td>Nissan GTR</td>
                <td>2018</td>
                <td>600zł</td>
              </tr>
              <tr>
                <td>BMW 1</td>
                <td>2019</td>
                <td>300zł</td>
              </tr>
              <tr>
                <td>Mercedes A45</td>
                <td>2019</td>
                <td>400zł</td>
              </tr>
              <tr>
                <td>Volkswagen Golf R</td>
                <td>2019</td>
                <td>450zł</td>
              </tr>
              <tr>
                <td>Audi TT</td>
                <td>2018</td>
                <td>480zł</td>
              </tr>
              
            </table>
              
              
          </div>
          
          <div id = "footer">
            <br>
            Autorzy: Karol Głuch, Michał Galas.<br>
            Copyright &copy 2020 G&G CarRent. Wszelkie prawa zastrzeżone.<br>
          </div>
          
      </body>
</html>