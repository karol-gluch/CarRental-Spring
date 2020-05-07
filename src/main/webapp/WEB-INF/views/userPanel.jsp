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
        <li><a class="active" href="${contextPath}/flota">Flota</a></li>
        <li><a href="${contextPath}/locations">Lokalizacje</a></li>
        <li><a href="${contextPath}/ofirmie">O firmie</a></li>
        <li><a href="${contextPath}/kontakt">Kontakt</a></li>
        <li><a href="${contextPath}/offer">Oferta</a></li>
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

<main class = "main">
    <c:if test="${changed eq true}">
        <div class="alert alert-success">Hasło zostało zmienione!</div>
    </c:if>
    <c:if test="${notchanged eq true}">
        <div class="alert alert-danger">Nie zmieniono hasła!</div>
    </c:if>

    <button class="btn btn-danger" onclick="window.location.href='${contextPath}/deletebyname/${pageContext.request.userPrincipal.name}'">Usuń konto</button>
    <button class="btn btn-success" data-toggle="modal" data-target="#changePasswordModal">Zmień hasło</button>
    <h2>Twoje wypożyczenia:</h2>
    <table class="table table-hover">
        <thead class="thead-light">
        <tr>
            <th>Samochód </th>
            <th>Miejsce wypożyczenia</th>
            <th>Miejsce oddania</th>
            <th>Data wypożyczenia</th>
            <th>Data oddania</th>
            <th>Status</th>
            <th>Kwota</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${rents}" var="rent">
            <tr>
                <td>${rent.offer.car.mark} ${rent.offer.car.model}</td>
                <td>${rent.miejsceWypozyczenia}</td>
                <td>${rent.miejsceOddania}</td>
                <td>${rent.dataWypozyczenia}</td>
                <td>${rent.dataOddania}</td>
                <td>${rent.status}</td>
                <td>${rent.kwota}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>

<footer class = "footer">
    <p>Autorzy: Karol Głuch, Michał Galas, Sławomir Faron.</p>
    <p>Copyright &copy 2020 G-F-G CarRent. Wszelkie prawa zastrzeżone.</p>
</footer>

<div class="modal fade" id="changePasswordModal">
    <div class="modal-dialog">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Zmiana hasła</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                    <form method="POST" action="${contextPath}/changepassword" class="form-signin">

                        <input type="hidden" id="username" name="username" value="${pageContext.request.userPrincipal.name}" required>

                        <div class="form-group ${error != null ? 'has-error' : ''}">
                            <label for="oldPassword">Stare hasło:</label>
                            <input type="password" class="form-control" id="oldPassword" placeholder="Podaj stare hasło"
                                   name="oldPassword" required>
                        </div>
                        <div class="form-group">
                            <label for="newPassword">Hasło:</label>
                            <input type="password" minlength="8" class="form-control" id="newPassword" placeholder="Podaj nowe hasło"
                                   name="newPassword" required>
                        </div>
                        <div class="form-group">
                            <label for="passwordConfirm">Podaj ponownie nowe hasło:</label>
                            <input type="password" minlength="8" class="form-control" id="passwordConfirm"
                                   placeholder="Podaj ponownie nowe haslo" name="passwordConfirm" required>
                            <div class="valid-feedback">Uzupełniono</div>
                            <div class="invalid-feedback">Proszę wypełnić pole</div>
                        </div>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                        <button class="btn btn-lg btn-dark btn-block" type="submit">Zmień hasło</button>

                    </form>
            </div>
            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Zamknij</button>
            </div>

        </div>
    </div>
</div>
</body>
</html>