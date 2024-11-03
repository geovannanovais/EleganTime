var modal = document.getElementById("cartModal");
    var cartLink = document.getElementById("cartLink");
    var span = document.getElementsByClassName("close")[0];

    cartLink.onclick = function (event) {
        event.preventDefault();
        modal.style.display = "block";
    }

    span.onclick = function () {
        modal.style.display = "none";
    }

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }