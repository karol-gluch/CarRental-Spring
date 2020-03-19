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
      </head>
      <body>
          <div id="header">
                <img src="<c:url value="/resources/images/logo.png" />" alt="logo" />
          </div>

          <div id = "aside">
              <ul>
                  <c:if test="${isAdmin}">
                      <li><a href="${contextPath}/index">Panel administratora</a></li>
                  </c:if>
                  <li><a href="${contextPath}/index">Strona Główna</a></li>
                  <li><a class="active" href="${contextPath}/flota">Flota</a></li>
                  <li><a href="${contextPath}/ofirmie">O firmie</a></li>
                  <li><a href="${contextPath}/kontakt">Kontakt</a></li>
                  <c:if test="${pageContext.request.userPrincipal.name == null}">
                      <form method="POST" action="${contextPath}/login" class="form-signin">
                          <h2 class="form-heading">Zaloguj się</h2>

                          <div class="form-group ${error != null ? 'has-error' : ''}">
                              <span>${message}</span>
                              <span>${error}</span>
                              <input name="username" type="text" placeholder="Username"
                                     autofocus="true"/>
                              <input name="password" type="password" placeholder="Password"/>
                              <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                              <button class="btn btn-lg btn-primary btn-block" type="submit">Zaloguj się!</button>
                              <h4 ><a href="${contextPath}/registration">Stwórz konto</a></h4>
                          </div>
                      </form>
                  </c:if>
                  <c:if test="${pageContext.request.userPrincipal.name != null}">
                      <form id="logoutForm" method="POST" action="${contextPath}/logout">
                      </form>
                      <button onclick="document.forms['logoutForm'].submit()">Logout</button>
                  </c:if>
              </ul>
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