$(document).ready(function() {
    if(JSON.parse(localStorage.getItem('userJWT')) == null){
        window.location.href = "./login.html"
    }
});