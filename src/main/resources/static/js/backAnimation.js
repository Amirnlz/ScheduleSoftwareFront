let $sitelading = $('#site-landing');
$sitelading.polygonizr();

// Update size.
$(window).resize(function () {
    $sitelading.polygonizr("stop");
    $sitelading.polygonizr({
        canvasHeight: $(this).height(),
        canvasWidth: $(this).width()
    });

    $sitelading.polygonizr("refresh");
});