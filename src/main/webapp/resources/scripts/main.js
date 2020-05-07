$(document).ready(function() {
    $('.offer-button-show').on('click', function() {
        var $wrapper = $(this).closest('section.offers').children('.offer-data');
        var headerText = $wrapper.find('h2.offer-header').text();
        var descriptionText = $wrapper.find('p.offer-description').text();
        var priceText = $wrapper.find('h3.offer-price').text();
        var imageSrc = $wrapper.find('img.offer-image').attr('src');
        var $modal = $('body #mymodal');
        $modal.find('h2#modal-header').text(headerText);
        $modal.find('p#modal-description').text(descriptionText);
        $modal.find('h3#modal-price').text(priceText);
        $modal.find('img#modal-image').attr('src', imageSrc);
    });
});