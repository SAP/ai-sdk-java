var modal = document.getElementById("show-realtime-scheme");

// Get the image and insert it inside the modal - use its "alt" text as a caption
var btn = document.getElementById("show-arch-scheme-btn");
var modalImg = document.getElementById("realtime-scheme");
var captionText = document.getElementById("modal-caption");
btn.onclick = function(){
    modal.style.display = "block";
    modalImg.src = "realtime-api-scheme.svg";
    captionText.innerHTML = "Principal interactions scheme";
}
btn.disabled = false;

// Get the <span> element that closes the modal
var span = document.getElementsByClassName("modal-close-button")[0];

// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}