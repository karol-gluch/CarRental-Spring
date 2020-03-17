<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
                <li><a class="active" href="/index">Strona Główna</a></li>
                <li><a href="/flota">Flota</a></li>
                <li><a href="/ofirmie">O firmie</a></li>
                <li><a href="/kontakt">Kontakt</a></li>
            </ul>
          </div>
          
          <div id = "main">
              <br><h1>Firma:</h1>
            <h3>Wynajem aut w G&G CarRent ma szereg atutów:</h3>
            <ul style="list-style-type:circle; background-color: #f1f1f1">
                <li>- elastyczna oferta najmu krótkoterminowego i średnioterminowego,</li>
                <li>- zróżnicowana flota sprawdzonych marek w różnych klasach wielkościowych (łącznie ponad 1500 modeli),</li>
                <li>- dział samochodów luksusowych specjalnie dla najbardziej wymagających Klientów,</li>
                <li>- możliwość wynajmu samochodu klasy Premium wraz z zawodowym kierowcą,</li>
                <li>- najlepsze ceny w mieście,</li>
                <li>- jasne zasady rozliczeń,</li>
            </ul>
<br><br>         
<h3>Krótkoterminowy wynajem samochodów</h3>
Prowadzimy wynajem samochodów na okres od 1 do 30 dni. Ofertę tę polecamy dla osób, które potrzebują auta zastępczego na czas przeglądu lub naprawy.<br> W naszej flocie dostępne są przestronne modele z nadwoziem typu kombi oraz SUV, które doskonale sprawdzą się na wycieczki czy rodzinne wyjazdy.<br>Wynajem vana to idealna opcja dla firm, które potrzebują środka transportu dla pracowników wyjeżdżających na konferencję, szkolenie lub imprezę integracyjną.<br>Niewielkie i zwrotne samochody kompaktowe klasy B to doskonała opcja dla wszystkich, którzy chcą wygodnie poruszać się po zakorkowanych ulicach,<br>zobaczyć najciekawsze miejsca w mieście lub dojechać na spotkania służbowe.
<br><br><br>
<h3>Średnioterminowy wynajem samochodów</h3>
Przedsiębiorcom polecamy korzystny wynajem średnioterminowy. Umowy podpisujemy na okres od 1 miesiąca do 2 lat.<br> W ramach miesięcznej raty zapewniamy polisy ubezpieczeniowe dla wypożyczonego samochodu, obsługę serwisową,<br>Assistance 24h oraz auto zastępcze na wypadek awarii. Istnieje możliwość wynajmu vana lub aut osobowych wielu różnych klas,<br>dzięki czemu firmy mogą uzupełnić swoją służbową flotę o pojazdy najlepiej dopasowane do ich indywidualnych potrzeb.
              
              
          </div>
          
          <div id = "footer">
              <br>
            Autorzy: Karol Głuch, Michał Galas.<br>
            Copyright &copy 2020 G&G CarRent. Wszelkie prawa zastrzeżone.<br>
          </div>
          
      </body>
</html>