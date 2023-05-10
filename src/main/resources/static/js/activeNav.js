$(document).ready(function() {
    setActiveNav();
});

function setActiveNav() {
    const url = window.location.href;
    if (url.includes("addEmployee")) {
        $("#nav2").addClass("active");
    } else if (url.includes("articles")) {
        $("#nav3").addClass("active");
    } else {
        $("#nav1").addClass("active");
    }
}