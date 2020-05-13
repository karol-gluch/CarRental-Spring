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

</head>
<body>
<header class="header">
    <h3>"Bądź wzorcem jakości. Niektórzy ludzie nie przywykli do środowiska, gdzie oczekuje się doskonałości."</h3>
</header>
<main class="main">
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
    <center>
        <form action="${contextPath}/save" method="post" modelAttrubite="car">
            <table border="0" cellpadding="5">
                <tr>
                    <td>ID:</td>
                    <td>${car.id}
                        <form:hidden path="car.id"/>
                    </td>
                </tr>
                <tr>
                    <td>Marka:</td>
                    <td><form:input class="form-control mb-2 mr-sm-2" path="car.mark"/></td>
                </tr>
                <tr>
                    <td>Model:</td>
                    <td><form:input class="form-control mb-2 mr-sm-2" path="car.model"/></td>
                </tr>
                <tr>
                    <td>Rok produkcji:</td>
                    <td><form:input class="form-control mb-2 mr-sm-2" path="car.yearOfProduction"/></td>
                </tr>
                <tr>
                    <td>Rodzaj paliwa:</td>
                    <td>
                        <form:select class="custom-select my-1 mr-sm-2" path="car.fuelType">
                            <form:option value="Benzyna" label="Benzyna"/>
                            <form:option value="Ropa" label="Ropa"/>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td>Pojemność silnika:</td>
                    <td><form:input class="form-control mb-2 mr-sm-2" path="car.engineCapacity"/></td>
                </tr>
                <tr>
                    <td>Rodzaj nadwozia:</td>
                    <td>
                        <form:select class="custom-select my-1 mr-sm-2" path="car.bodyType">
                            <form:option value="Sedan" label="Sedan"/>
                            <form:option value="Kombi" label="Kombi"/>
                            <form:option value="Hatchback" label="Hatchback"/>
                        </form:select>
                    </td>
                </tr>
                <tr>
                    <td>Liczba miejsc:</td>
                    <td><form:input class="form-control mb-2 mr-sm-2" path="car.numberOfPlaces"/></td>
                </tr>
            </table>
            <button type="submit" class="btn btn-dark btn-lg">Zapisz</button>
        </form>
        </br>
        <a href="${contextPath}/cars" class="btn btn-dark">Anuluj</a>

    </center>
</main>

<footer class="footer">
    <p>Autorzy: Karol Głuch, Michał Galas, Sławomir Faron.</p>
    <p>Copyright &copy 2020 G-F-G CarRent. Wszelkie prawa zastrzeżone.</p>
</footer>

</body>
</html>