$(document).ready(function() {
    showModal();
    changeModalImage();
});

/*
 * Show modal on button click
 */
function showModal() {
    $('.offer-button-show').on('click', function() {
        var offer = getOfferData($(this));

        setDataInModal(offer);
    });
}

/*
 * Get data from offer
 *
 * @param {HTMLElement} $this
 * @return {Offer}
 */
function getOfferData($this) {
    var $wrapper = $this.closest('section.offers').children('.offer-data');

    var offer = {
        headerText: $wrapper.find('h2.offer-header').text(),
        descriptionText: $wrapper.find('p.offer-description').text(),
        priceText: $wrapper.find('h3.offer-price').text(),
        imageSrc: $wrapper.find('img.offer-image').attr('src'),
        imageGallery: getOfferGallery($wrapper),
        yearText: $wrapper.find('p.offer-year').text(),
        bodyText: $wrapper.find('p.offer-body').text(),
        fuelText: $wrapper.find('p.offer-fuel').text(),
        engineText: $wrapper.find('p.offer-engine').text(),
        placesText: $wrapper.find('p.offer-places').text(),
        idattr: $wrapper.find('form.offer-id').attr('action')
    };

    return offer;
}

/*
 * Get data from offer gallery
 *
 * @param {HTMLElement} $wrapper
 * @return {String[]}
 */
function getOfferGallery($wrapper) {
    var gallery = [];

    $wrapper.find('.offer-gallery img').each(function() {
        gallery.push($(this).attr('src'));
    });

    return gallery;
}

/*
 * Set offer data in modal
 *
 * @param {Offer} offer
 */
function setDataInModal(offer) {
    var $modal = $('body #mymodal');

    $modal.find('h2#modal-header').text(offer.headerText);
    $modal.find('p#modal-description').text(offer.descriptionText);
    $modal.find('p#modal-price').text(offer.priceText);
    $modal.find('img#modal-image').attr('src', offer.imageSrc);
    $modal.find('p#modal-year').text("Rok produkcji: " + offer.yearText);
    $modal.find('p#modal-body').text("Typ nadwozia: " + offer.bodyText);
    $modal.find('p#modal-fuel').text("Rodzaj paliwa: " + offer.fuelText);
    $modal.find('p#modal-engine').text("Pojemność silnika: " + offer.engineText);
    $modal.find('p#modal-places').text("Liczba miejsc: " + offer.placesText);
    $modal.find('form#modal-id').attr('action', offer.idattr);
    $modal.find('div#modal-gallery').empty().append(setGalleryInModal(offer.imageGallery));
}

/*
 * Set gallery offer data in modal
 *
 * @param {String[]} gallery
 */
function setGalleryInModal(gallery) {
    var $html = '';
    var className = '';
    $.each(gallery, function(i, val) {
        if(i == 0) {
            className = 'active';
        } else {
            className = 'display-none';
        }
        var id = i + 1;

        $html += '<img class="' + className + '" src="' + val + '" data-image-id="' + id + '">';
    });

    return $html;
}

/*
 * Show modal on button click
 */
function changeModalImage() {
    $('#modal-gallery-arrow-prev').on('click', function() {
        changeGalleryImage($(this), false);
    });

    $('#modal-gallery-arrow-next').on('click', function() {
        changeGalleryImage($(this), true);
    });
}

/*
 * Change image in modal
 *
 * @param {HTMLElement} $this
 * @param {boolean} direction
 */
function changeGalleryImage($this, direction) {
    var $activeItem = $('#modal-gallery img.active');
    var itemsNr = $('#modal-gallery img').length;
    var activeItemId = parseInt($activeItem.attr('data-image-id'));
    var nextItemId = 0;
    $activeItem.removeClass('active').addClass('display-none');

    if (direction == false) { // Show next image
        if (itemsNr == activeItemId) {
            nextItemId = 1;
        } else {
            nextItemId = activeItemId + 1;
        }
    } else { // Show prev image
        if (activeItemId == 1) {
            nextItemId = itemsNr;
        } else {
            nextItemId = activeItemId - 1;
        }
    }

    $('#modal-gallery img[data-image-id="' + nextItemId + '"]').removeClass('display-none').addClass('active');
}
