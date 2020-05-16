<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Base64" %>
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
    <script src="${contextPath}/resources/scripts/main.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.1.1/css/all.css">
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
                <button type="button" class="btn btn-dark" onclick="window.location.href='/panel/${pageContext.request.userPrincipal.name}'">Otwórz profil</button>
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

    <div class="modal-offer modal" id="mymodal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h2 id="modal-header" class="modal-title"></h2>
                    <img src="../../resources/images/cross.png"  data-dismiss="modal">
                </div>
                <div class="modal-body">
                    <div id="modal-gallery" class="modal-gallery"></div>
                    <button id="modal-gallery-arrow-prev" class="button-prev"><i class="fas fa-arrow-left"></i></button>
                    <button id="modal-gallery-arrow-next" class="button-next"><i class="fas fa-arrow-right"></i></button>
                    <p id="modal-description"></p>
                    <div class="informations">
                        <div class="info">
                            <p id="modal-price"></p>
                            <p id="modal-year"></p>
                            <p id="modal-body"></p>
                        </div>
                        <div class="info">
                            <p id="modal-fuel"></p>
                            <p id="modal-engine"></p>
                            <p id="modal-places"></p>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <form id="modal-id" method="post">
                        <button class="buy" type="submit">Wypożycz</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <main class="main">
        <c:forEach items="${car_photoList}" var="offer">
        </c:forEach>
        <c:forEach items="${offerList}" var="offer">
            <section class="offers">
                <div class="offer offer-data offer-button-show" data-toggle="modal" data-target="#mymodal">
                    <c:forEach var="img" items="${offer.car.carPhoto}" end="0">
                        <img class="offer-image" src="data:image/*;base64,${Base64.getEncoder().encodeToString(img.photo)}"/>
                    </c:forEach>
                    <div class="offer-gallery display-none">
                        <c:forEach var="img" items="${offer.car.carPhoto}">
                                 <img src="data:image/*;base64,${Base64.getEncoder().encodeToString(img.photo)}"/>
                        </c:forEach>
                    </div>
                    <div class="description">
                        <h2 class="offer-header">${offer.car.mark} ${offer.car.model}</h2>
                        <p class="offer-description">${offer.description}</p>
                        <h3 class="offer-price">Cena: ${offer.price}zł/doba</h3>
                        <div class="display-none">
                            <p class="offer-year">${offer.car.yearOfProduction}</p>
                            <p class="offer-body">${offer.car.bodyType}</p>
                            <p class="offer-fuel">${offer.car.fuelType}</p>
                            <p class="offer-engine">${offer.car.engineCapacity}</p>
                            <p class="offer-places">${offer.car.numberOfPlaces}</p>
                            <form class="offer-id" action="${contextPath}/wypozycz/${offer.id}" method="post"> </form>
                        </div>
                    </div>
                </div
            </section>
        </c:forEach>
    </main>

    <footer class = "footer">
        <p>Autorzy: Karol Głuch, Michał Galas, Sławomir Faron.</p>
        <p>Copyright &copy 2020 G-F-G CarRent. Wszelkie prawa zastrzeżone.</p>
    </footer>
</body>
</html>