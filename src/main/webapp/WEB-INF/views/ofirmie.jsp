<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
              <li><a class="active"  href="${contextPath}/ofirmie">O firmie</a></li>
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

      <div class = "main">
          <h2>Informacje o firmie:</h2>
          <p>- Wypożyczając samochód na okres powyżej 14 dni otrzymujesz 10% rabatu. </p>
          <p>- Wypożyczając samochód na okres powyżej 30 dni otrzymujesz 20% rabatu. </p>
          <p>- Zachęcamy do zostawiania opinii na temat naszej wypożyczalni po zakończeniu usługi. </p>
          <p>- Jeśli podczas wypożyczenia zauważyłeś usterkę zgłoś ją w swoim profilu. </p>
          <p>- Podczas wypożyczenia jeśli w Twoim samochodzie powstała jakaś usterka, zgłoś ją  </p>
          <p> w swoim profilu. </p>
          <p></p><p></p>
          <h2>Wynajem aut w G-F-G CarRent ma szereg atutów:</h2>

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

      </div>

      <footer class = "footer">
          <p>Autorzy: Karol Głuch, Michał Galas, Sławomir Faron.</p>
          <p>Copyright &copy 2020 G-F-G CarRent. Wszelkie prawa zastrzeżone.</p>
      </footer>
</body>
</html>