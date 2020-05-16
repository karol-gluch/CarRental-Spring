<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<% request.setAttribute("isAdmin", request.isUserInRole("ADMIN")); %>
<!DOCTYPE html>
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
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css">
    <style>
        .table tr {
            text-align: center;
        }

        .table td {
            text-align: center;
        }
    </style>
</head>
<body>
<header class="header">
    <h3>"Bądź wzorcem jakości. Niektórzy ludzie nie przywykli do środowiska, gdzie oczekuje się doskonałości."</h3>
</header>
<main class="main padding-bottom35">
    <div class="adminMenu">
        <a href="${contextPath}/index"><i class="fas fa-home"></i>Strona główna</a>
        <a href="${contextPath}/adminPanel"><i class="fas fa-user-cog"></i>Panel administratora</a>
        <a href="${contextPath}/cars"><i class="fas fa-car"></i>Samochody</a>
        <a href="${contextPath}/users"><i class="fas fa-users"></i>Użytkownicy</a>
        <a href="${contextPath}/faults"><i class="fas fa-list-ol"></i>Usterki</a>
        <a href="${contextPath}/offerform"><i class="fas fa-donate"></i>Oferty</a>
        <a href="${contextPath}/locationform"><i class="fas fa-map-marker-alt"></i>Lokalizacje</a>
    </div>
    </br>
    <c:if test="${deletecar eq true}">
        <div class="alert alert-success">Usunięto samochód!</div>
    </c:if>

    <c:if test="${modifycar eq true}">
        <div class="alert alert-success">Zmodyfikowano samochód!</div>
    </c:if>
    <c:if test="${car eq true}">
        <div class="alert alert-success">Dodano nowy samochód!</div>
    </c:if>

    <h2>Lista samochodów:</h2>
    <table class="table table-hover">
        <thead class="thead-light">
        <tr>
            <th>Samochód</th>
            <th>Działania</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${cars}" var="cars">
            <tr>
                <td>${cars.mark} ${cars.model}</td>
                <td>
                    <a href="${contextPath}/deleteCarPanel/${cars.getId()}" class="btn btn-outline-danger btn-sm">Usuń</a>
                    <a href="${contextPath}/modifyCar/${cars.getId()}" class="btn btn-outline-warning btn-sm">Modyfikuj</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form class="row cars" action="${contextPath}/carform" method="get">
        <button type="submit" class="btn btn-outline-success">Dodaj samochód</button>
    </form>

</main>

<footer class="footer">
    <p>Autorzy: Karol Głuch, Michał Galas, Sławomir Faron.</p>
    <p>Copyright &copy 2020 G-F-G CarRent. Wszelkie prawa zastrzeżone.</p>
</footer>

</body>
</html>