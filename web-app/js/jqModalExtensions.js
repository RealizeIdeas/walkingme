$(function() {
  $(window).delegate("*", "keyup", function (evt) {
    if (evt.which == 27) {
      $(".jqmWindow").each(function() {
        if ($(this).css("display") == "block") {
          $(this).jqmHide();
        }
      });
    }
  });
});
