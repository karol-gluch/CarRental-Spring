$(document).ready(function() {
    $('.offer-button-show').on('click', function() {
        var $wrapper = $(this).closest('section.offers').children('.offer-data');
        var headerText = $wrapper.find('h2.offer-header').text();
        var descriptionText = $wrapper.find('p.offer-description').text();
        var priceText = $wrapper.find('h3.offer-price').text();
        var imageSrc = $wrapper.find('img.offer-image').attr('src')
        var yearText = $wrapper.find('p.offer-year').text();
        var bodyText = $wrapper.find('p.offer-body').text();
        var fuelText = $wrapper.find('p.offer-fuel').text();
        var engineText = $wrapper.find('p.offer-engine').text();
        var placesText = $wrapper.find('p.offer-places').text();
        var idattr = $wrapper.find('form.offer-id').attr('action');
        var $modal = $('body #mymodal');
        $modal.find('h2#modal-header').text(headerText);
        $modal.find('p#modal-description').text(descriptionText);
        $modal.find('p#modal-price').text(priceText);
        $modal.find('img#modal-image').attr('src', imageSrc);
        $modal.find('p#modal-year').text("Rok produkcji: " + yearText);
        $modal.find('p#modal-body').text("Typ nadwozia: " + bodyText);
        $modal.find('p#modal-fuel').text("Rodzaj paliwa: " + fuelText);
        $modal.find('p#modal-engine').text("Pojemność silnika: " + engineText);
        $modal.find('p#modal-places').text("Liczba miejsc: " + placesText);
        $modal.find('form#modal-id').attr('action', idattr);
    });
});