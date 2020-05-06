<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@page import="com.car.rental.project.social.FBConnection"%>
<%
    FBConnection fbConnection = new FBConnection();
%>
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
    <script>
        <c:if test="${pageContext.request.userPrincipal.name == null}">
            $(document).ready(function(){
                $("#myModalLogin").modal('show');
            });
        </c:if>
    </script>
</head>
<body>
    <nav class="navtop">
        <img src="../../resources/images/newlogo.png">
        <ul>
            <c:if test="${isAdmin}">
                <li><a href="${contextPath}/adminPanel">Panel administratora</a></li>
            </c:if>
            <li><a class="active" href="${contextPath}/index">Strona Główna</a></li>
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
        </br>
        <c:if test="${register eq true}">
            <div class="alert alert-success">Konto zostało utworzone. Zostałeś zalogowany do strony.</div>
        </c:if>
        <center>
        </br></br>
            <h2>Znajdz odpowiedni samochod dla siebie!</h2></br>
            <h6>Wypełnij krótki formularz i znajdź wymarzone auto do wypożyczenia.</h6>
            <form action="${contextPath}/searchCar" method="post">
                <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Cena od" id="cenaOd" name="cenaOd" required>
                <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Cena do" id="cenaDo" name="cenaDo" required>
                <select class="custom-select my-1 mr-sm-2" id="rodzajpaliwa" name="rodzajpaliwa" style="width: 50%" required>
                    <option selected>Rodzaj paliwa</option>
                    <option value="Benzyna">Benzyna</option>
                    <option value="Ropa">Ropa</option>
                </select>
                <select class="custom-select my-1 mr-sm-2" id="typnadwozia" name="typnadwozia" style="width: 50%" required>
                    <option selected>Typ nadwozia</option>
                    <option value="Sedan">Sedan</option>
                    <option value="Kombi">Kombi</option>
                    <option value="Hatchback">Hatchback</option>
                </select>
                <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Rok produkcji od" id="rokOd" name="rokOd" required>
                <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Rok produkcji do" id="rokDo" name="rokDo" required>
                <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Pojemnosc od (litry)" id="pojemnoscOd" name="pojemnoscOd" required>
                <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Pojemnosc do (litry)" id="pojemnoscDo" name="pojemnoscDo" required>
                <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Liczba miejsc od" id="liczbaOd" name="liczbaOd" required>
                <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Liczba miejsc do" id="liczbaDo" name="liczbaDo" required>


                <button type="submit" class="btn btn-dark mb-2">Szukaj</button>
            </form>
        </center>
        </br></br>

        <h1>Firma:</h1>
        <h2>Wynajem aut w G&G CarRent ma szereg atutów:</h2>

        <p>- elastyczna oferta najmu krótkoterminowego i średnioterminowego,</p>
        <p>- zróżnicowana flota sprawdzonych marek w różnych klasach wielkościowych (łącznie ponad 1500 modeli),</p>
        <p>- dział samochodów luksusowych specjalnie dla najbardziej wymagających Klientów,</p>
        <p>- możliwość wynajmu samochodu klasy Premium wraz z zawodowym kierowcą,</p>
        <p>- najlepsze ceny w mieście,</p>
        <p>- jasne zasady rozliczeń</p>

        <h2>Krótkoterminowy wynajem samochodów</h2>
        <p>Prowadzimy wynajem samochodów na okres od 1 do 30 dni. Ofertę tę polecamy dla osób, które potrzebują auta zastępczego na czas przeglądu lub naprawy.</p>
        <p>W naszej flocie dostępne są przestronne modele z nadwoziem typu kombi oraz SUV, które doskonale sprawdzą się na wycieczki czy rodzinne wyjazdy.</p>
        <p>Wynajem vana to idealna opcja dla firm,które potrzebują środka transportu dla pracowników wyjeżdżających na konferencję, szkolenie lub imprezę integracyjną.</p>
        <p>Niewielkie i zwrotne samochody kompaktowe klasy B to doskonała opcja dla wszystkich, którzy chcą wygodnie poruszać się po zakorkowanych ulicach,</p>
        <p>zobaczyć najciekawsze miejsca w mieście lub dojechać na spotkania  służbowe.</p>

        <h2>Średnioterminowy wynajem samochodów</h2>
        <p>Przedsiębiorcom polecamy korzystny wynajem średnioterminowy. Umowy podpisujemy na okres od 1 miesiąca do 2 lat.</p>
        <p>W ramach miesięcznej raty zapewniamy polisy ubezpieczeniowe dla wypożyczonego samochodu, obsługę serwisową,</p>
        <p>Assistance 24h oraz auto zastępcze na wypadek awarii. Istnieje możliwość wynajmu vana lub aut osobowych wielu różnych klas,</p>
        <p>dzięki czemu firmy mogą uzupełnić swoją służbową flotę o pojazdy najlepiej dopasowane do ich indywidualnych potrzeb.</p>
    </main>

    <footer class = "footer">
        <p>Autorzy: Karol Głuch, Michał Galas, Sławomir Faron.</p>
        <p>Copyright &copy 2020 G-F-G CarRent. Wszelkie prawa zastrzeżone.</p>
    </footer>


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
                        <label for="username">Nazwa użytkownika:</label>
                        <input type="username" class="form-control" id="username" placeholder="Podaj nazwę użytkownika"
                               name="username" required>
                    </div>
                    <div class="form-group">
                        <label for="password">Hasło:</label>
                        <input type="password" class="form-control" id="password" placeholder="Podaj hasło"
                               name="password" required>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <button class="btn btn-lg btn-dark btn-block" type="submit">Zaloguj się!</button>

            </form>
                    <br />
                    <center>
                    <button class="loginBtn loginBtn--facebook" onclick="window.location.href='<%=fbConnection.getFBAuthUrl()%>'">
                        Login with Facebook
                    </button>
                    </center>
            </c:if>
                </br>
                <center>
                Nie masz konta? Utwórz je!</br>
                <button type="button" data-dismiss="modal" class="btn btn-dark" data-toggle="modal" data-target="#myModalRegister">
                    Rejestracja
                </button>
                </center>
        </div>
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
                        <input type="text" minlength="6" class="form-control" id="RegisterUsername" placeholder="Podaj nazwe uzytkownika"
                               name="username" required>
                        <div class="valid-feedback">Uzupełniono</div>
                        <div class="invalid-feedback">Proszę wypełnić pole</div>
                    </div>
                    <div class="form-group">
                        <label for="password">Haslo:</label>
                        <input type="password" minlength="8" class="form-control" id="registerPassword" placeholder="Podaj haslo"
                               name="password" required>
                        <div class="valid-feedback">Uzupełniono</div>
                        <div class="invalid-feedback">Proszę wypełnić pole</div>
                    </div>
                    <div class="form-group">
                        <label for="passwordConfirm">Podaj ponownie hasło:</label>
                        <input type="password" minlength="8" class="form-control" id="passwordConfirm"
                               placeholder="Podaj ponownie haslo" name="passwordConfirm" required>
                        <div class="valid-feedback">Uzupełniono</div>
                        <div class="invalid-feedback">Proszę wypełnić pole</div>
                    </div>
                    <button type="submit" class="btn btn-lg btn-dark btn-block">Zarejestruj</button>
                </form>
                <br />
                <button class="loginBtn loginBtn--facebook" onclick="window.location.href='<%=fbConnection.getFBAuthUrl()%>'">
                    Login with Facebook
                </button>
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