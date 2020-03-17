<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
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
                <li><a href="/index">Strona Główna</a></li>
                <li><a href="/flota">Flota</a></li>
                <li><a href="/ofirmie">O firmie</a></li>
                <li><a class="active" href="/kontakt">Kontakt</a></li>
            </ul>
          </div>
          
          <div id = "main">
              <br><h1>Kontakt:</h1>
                  <form action="index.jsp">
    <input type="text" id="imie" name="imie" placeholder="Twoje imie..">
    <br>
    <input type="text" id="nazwisko" name="nazwisko" placeholder="Twoje nazwisko..">

        <textarea id="temat" name="temat" placeholder="Twoj temat.." style="height:200px"></textarea>
<br>
    <input type="submit" value="Wyslij wiadomosc">
  </form>
              
              
          </div>
          
          <div id = "footer">
              <br>
            Autorzy: Karol Głuch, Michał Galas.<br>
            Copyright &copy 2020 G&G CarRent. Wszelkie prawa zastrzeżone.<br>
          </div>
          
      </body>
</html>