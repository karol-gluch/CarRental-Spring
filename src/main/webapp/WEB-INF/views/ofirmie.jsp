<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                  <li><a href="${contextPath}/flota">Flota</a></li>
                  <li><a class="active" href="${contextPath}/ofirmie">O firmie</a></li>
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
              <br><h1>Wypożyczanie samochodów:</h1>
<iframe width="560" height="315" src="https://www.youtube.com/embed/a32wCCeaERo" frameborder="0" allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>
              
              
          </div>
          
          <div id = "footer">
              <br>
            Autorzy: Karol Głuch, Michał Galas.<br>
            Copyright &copy 2020 G&G CarRent. Wszelkie prawa zastrzeżone.<br>
          </div>
          
      </body>
</html>