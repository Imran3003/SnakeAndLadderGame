document.addEventListener("DOMContentLoaded", function () {
    var popup = document.getElementById("popup");
    var closeBtn = document.querySelector(".close");
    var popUpH3 = document.getElementById("pop-up-h3");

    // console.log("popUpH3 = ",popUpH3)
    if (popup && closeBtn) {

        closeBtn.addEventListener("click", function () {
            popup.style.display = "none";
        });

        window.addEventListener("click", function (event) {
            if (event.target === popup) {
                popup.style.display = "none";
            }
        });
    }

});
