<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <img src="<c:url value="/resources/images/logo.png" />" alt="logo"/>
</div>

<div id="aside">
    <ul>
        <c:if test="${isAdmin}">
            <li><a href="${contextPath}/adminPanel">Panel administratora</a></li>
        </c:if>
        <li><a class="active" href="${contextPath}/index">Strona Główna</a></li>
        <li><a href="${contextPath}/flota">Flota</a></li>
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

<div id="main">
    <br>
    <h1>Firma:</h1>
    <h3>Wynajem aut w G&G CarRent ma szereg atutów:</h3></br>

    - elastyczna oferta najmu krótkoterminowego i średnioterminowego, </br>
    - zróżnicowana flota sprawdzonych marek w różnych klasach wielkościowych (łącznie ponad 1500 modeli),</br>
    - dział samochodów luksusowych specjalnie dla najbardziej wymagających Klientów,</br>
    - możliwość wynajmu samochodu klasy Premium wraz z zawodowym kierowcą,</br>
    - najlepsze ceny w mieście,</br>
    - jasne zasady rozliczeń

    <br><br>
    <h3>Krótkoterminowy wynajem samochodów</h3>
    Prowadzimy wynajem samochodów na okres od 1 do 30 dni. Ofertę tę polecamy dla osób, które potrzebują auta
    zastępczego na czas przeglądu lub naprawy.<br> W naszej flocie dostępne są przestronne modele z nadwoziem typu kombi
    oraz SUV, które doskonale sprawdzą się na wycieczki czy rodzinne wyjazdy.<br>Wynajem vana to idealna opcja dla firm,
    które potrzebują środka transportu dla pracowników wyjeżdżających na konferencję, szkolenie lub imprezę
    integracyjną.<br>Niewielkie i zwrotne samochody kompaktowe klasy B to doskonała opcja dla wszystkich, którzy chcą
    wygodnie poruszać się po zakorkowanych ulicach,<br>zobaczyć najciekawsze miejsca w mieście lub dojechać na spotkania
    służbowe.
    <br><br><br>
    <h3>Średnioterminowy wynajem samochodów</h3>
    Przedsiębiorcom polecamy korzystny wynajem średnioterminowy. Umowy podpisujemy na okres od 1 miesiąca do 2 lat.<br>
    W ramach miesięcznej raty zapewniamy polisy ubezpieczeniowe dla wypożyczonego samochodu, obsługę serwisową,<br>Assistance
    24h oraz auto zastępcze na wypadek awarii. Istnieje możliwość wynajmu vana lub aut osobowych wielu różnych klas,<br>dzięki
    czemu firmy mogą uzupełnić swoją służbową flotę o pojazdy najlepiej dopasowane do ich indywidualnych potrzeb.


</div>

<div id="footer">
    <br>
    Autorzy: Karol Głuch, Michał Galas.<br>
    Copyright &copy 2020 G&G CarRent. Wszelkie prawa zastrzeżone.<br>
</div>


<!-- Logowanie -->
<div class="modal fade" id="myModalLogin">
    <div class="modal-dialog">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Logowanie</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                <form method="POST" action="${contextPath}/login" class="form-signin">

                    <div class="form-group ${error != null ? 'has-error' : ''}">
                        <!--<span>${message}</span>
                            <span>${error}</span>
                            <input name="username" type="text" placeholder="Username"
                                   autofocus="true"/>
                            <input name="password" type="password" placeholder="Password"/>
                            <div class="form-group">-->
                        <label for="username">Nazwa użytkownika:</label>
                        <input type="username" class="form-control" id="username" placeholder="Podaj nazwę użytkownika"
                               name="username">
                    </div>
                    <div class="form-group">
                        <label for="password">Hasło:</label>
                        <input type="password" class="form-control" id="password" placeholder="Podaj hasło"
                               name="password">
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <button class="btn btn-lg btn-primary btn-block" type="submit">Zaloguj się!</button>
            </div>
            </form>
            </c:if>


            <!-- Modal footer -->
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Zamknij</button>
            </div>

        </div>
    </div>
</div>


<!-- Rejestracja -->
<div class="modal fade" id="myModalRegister">
    <div class="modal-dialog">
        <div class="modal-content">

            <!-- Modal Header -->
            <div class="modal-header">
                <h4 class="modal-title">Rejestracja</h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>

            <!-- Modal body -->
            <div class="modal-body">

                <form action="/registration" method="post">
                    <div class="form-group">
                        <label for="username">Nazwa użytkownika:</label>
                        <input type="text" minlength="6" class="form-control" id="username" placeholder="Podaj nazwe uzytkownika"
                               name="username" required>
                        <div class="valid-feedback">Uzupełniono</div>
                        <div class="invalid-feedback">Proszę wypełnić pole</div>
                    </div>
                    <div class="form-group">
                        <label for="password">Haslo:</label>
                        <input type="password" minlength="8" class="form-control" id="password" placeholder="Podaj haslo"
                               name="password" required>
                        <div class="valid-feedback">Uzupełniono</div>
                        <div class="invalid-feedback">Proszę wypełnić pole</div>
                    </div>
                    <div class="form-group">
                        <label for="passwordConfirm">Tresc:</label>
                        <input type="password" minlength="8" class="form-control" id="passwordConfirm"
                               placeholder="Podaj ponownie haslo" name="passwordConfirm" required>
                        <div class="valid-feedback">Uzupełniono</div>
                        <div class="invalid-feedback">Proszę wypełnić pole</div>
                    </div>
                    <button type="submit" class="btn btn-primary">Zarejestruj</button>
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