<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import="java.util.Base64" %>
<%@ page import="com.car.rental.project.payment.PayUConnection" %>
<%@ page import="com.car.rental.project.payment.Order" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<% request.setAttribute("isAdmin", request.isUserInRole("ADMIN"));
    PayUConnection payUConnection = new PayUConnection();
    String url = payUConnection.payUUrl((Order)request.getAttribute("order"));
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>G&G CarRent - Wypożyczalnia samochodów</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <link href="${contextPath}/resources/css/style.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body>
<nav class="navtop">
    <img src="../../resources/images/newlogo.png">
    <ul>
        <c:if test="${isAdmin}">
            <li><a href="${contextPath}/adminPanel">Panel administratora</a></li>
        </c:if>
        <li><a href="${contextPath}/index">Strona Główna</a></li>
        <li><a href="${contextPath}/flota">Flota</a></li>
        <li><a href="${contextPath}/locations">Lokalizacje</a></li>
        <li><a href="${contextPath}/ofirmie">O firmie</a></li>
        <li><a href="${contextPath}/kontakt">Kontakt</a></li>
        <li><a class="active" href="${contextPath}/offer">Oferta</a></li>
    </ul>

    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
        </form>
        <div class="logreg">
            <h6>Zalogowany: <b>${pageContext.request.userPrincipal.name}</b></h6>
            <button type="button" class="btn btn-dark" onclick="document.forms['logoutForm'].submit()">Wyloguj się
            </button>
        </div>
    </c:if>

    <c:if test="${pageContext.request.userPrincipal.name == null}">
        <div class="logreg">
            <button type="button" class="btn btn-light" data-toggle="modal" data-target="#myModalLogin">
                Zaloguj się
            </button>
            <button type="button" class="btn btn-dark" data-toggle="modal" data-target="#myModalRegister">
                Rejestracja
            </button>
        </div>
    </c:if>
</nav>

<header class="header">
    <h3>"Bądź wzorcem jakości. Niektórzy ludzie nie przywykli do środowiska, gdzie oczekuje się doskonałości."</h3>
</header>

<main class="main">

<center>
    <br>
    <h4>Podsumowanie wypożyczenia: </h4>
    <b>Samochód: </b>${nameCar}<br>
    <b>Kwota za wypożyczenie: </b>${kwota} zł<br>
    <b>Miejsce wypożyczenia: </b>${rentLocation}<br>
    <b>Data wypożyczenia: </b>${rentDate}<br>
    <b>Miejsce oddania: </b>${returnLocation}<br>
    <b>Data oddania: </b>${returnDate}<br><br>

    <b>Informacja!</b><br>
    Przed dokonaniem zakupu otrzymujesz rezerwację na 24h. Jeśli w tym czasie nie wykonasz płatności Twoje zamówienie zostaje usunięte.
    <br><br>
    <button type="button" class="btn btn-success" onclick="window.location.href='<%=url%>'">Przejdź do płatności</button>
</center>

</main>

<footer class="footer">
    <p>Autorzy: Karol Głuch, Michał Galas, Sławomir Faron.</p>
    <p>Copyright &copy 2020 G-F-G CarRent. Wszelkie prawa zastrzeżone.</p>
</footer>
</body>
</html>