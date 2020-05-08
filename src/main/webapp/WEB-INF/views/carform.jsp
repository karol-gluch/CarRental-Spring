<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<% request.setAttribute("isAdmin", request.isUserInRole("ADMIN")); %>
<html>
<head>
    <meta charset="UTF-8">
    <title>G-F-G CarRent - Wypożyczalnia samochodów</title>
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
            <li><a class="active" href="${contextPath}/adminPanel">Panel administratora</a></li>
        </c:if>
        <li><a href="${contextPath}/index">Strona Główna</a></li>
        <li><a href="${contextPath}/flota">Flota</a></li>
        <li><a href="${contextPath}/locations">Lokalizacje</a></li>
        <li><a href="${contextPath}/ofirmie">O firmie</a></li>
        <li><a href="${contextPath}/kontakt">Kontakt</a></li>
        <li><a href="${contextPath}/offer">Oferta</a></li>
    </ul>
    <c:if test="${pageContext.request.userPrincipal.name != null}">
        <form id="logoutForm" method="POST" action="${contextPath}/logout">
        </form>
        <div class="logreg">
            <button type="button" class="btn btn-dark" onclick="window.location.href='/panel/${pageContext.request.userPrincipal.name}'">Otwórz profil</button>
            <button type="button" class="btn btn-dark" onclick="document.forms['logoutForm'].submit()">Wyloguj się
            </button>
        </div>
    </c:if>
</nav>

<header class="header">
    <h3>"Bądź wzorcem jakości. Niektórzy ludzie nie przywykli do środowiska, gdzie oczekuje się doskonałości."</h3>
</header>

<main class="main">

    <center>
        <div class="carform">
            <h4>Dodaj samochód</h4>
            <form class="column" action="${contextPath}/addImage/${id}" enctype="multipart/form-data" method="post">
                <input type="file" accept="image/*" id="photos" name="photos" multiple="multiple">
                <button type="submit" class="btn btn-dark">Dodaj zdjecia</button>
            </form>
        </div>

        <form action="${contextPath}/addCar/${id}" method="post">
            <input type="text" class="form-control mb-2 mr-sm-2" id="mark" placeholder="Podaj marke samochodu"
                   name="mark" required>
            <input type="text" class="form-control mb-2 mr-sm-2" id="model" placeholder="Podaj model samochodu"
                   name="model" required>
            <input type="text" class="form-control mb-2 mr-sm-2" id="yearOfProduction"
                   placeholder="Podaj rok produkcji samochodu" name="yearOfProduction" required>
            <select class="custom-select my-1 mr-sm-2" id="fuelType" name="fuelType" style="width: 50%" required>
                <option selected>Rodzaj paliwa</option>
                <option value="Benzyna">Benzyna</option>
                <option value="Ropa">Ropa</option>
            </select>
            <select class="custom-select my-1 mr-sm-2" id="bodyType" name="bodyType" style="width: 50%" required>
                <option selected>Typ nadwozia</option>
                <option value="Sedan">Sedan</option>
                <option value="Kombi">Kombi</option>
                <option value="Hatchback">Hatchback</option>
            </select>
            <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Podaj pojemność silnika:"
                   id="engineCapacity" name="engineCapacity" required>
            <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Podaj ilość miejsc w samochodzie:"
                   id="numberOfPlaces" name="numberOfPlaces" required>
            </br>
            <button type="submit" class="btn btn-dark">Dodaj samochód</button>
        </form></br>
        <button onclick="window.location.href='/deleteCar/${id}'" class="btn btn-dark">Anuluj</button>
    </center>
</main>

<footer class="footer">
    <p>Autorzy: Karol Głuch, Michał Galas, Sławomir Faron.</p>
    <p>Copyright &copy 2020 G-F-G CarRent. Wszelkie prawa zastrzeżone.</p>
</footer>

</body>
</html>
