$(document).ready(function () {
    var token = JSON.parse(localStorage.getItem('userJWT'));

    $("#login-header").click(function (e) {
        if (document.getElementById("login-header").innerHTML == "Logout") {
            e.preventDefault();
            $.ajax({
                method: 'post',
                processData: false,
                contentType: false,
                cache: false,
                enctype: 'multipart/form-data',
                url: 'http://localhost:8080/api/logout',
                headers: {
                    'Authorization': 'Bearer ' + token
                },
                success: function (response) {
                    console.log(response);
                    localStorage.clear();
                    window.location.href = "./index.html";
                },
                error: function (response) {
                    alert("Failed....");
                }
            })
        }
    });
    $.ajax({
        method: 'get',
        processData: false,
        contentType: false,
        cache: false,
        enctype: 'multipart/form-data',
        url: 'http://localhost:8080/api/jwt',
        headers: {
            'Authorization': 'Bearer ' + token
        },
        success: function (response) {
            console.log(response);
            if (response.length == 0) {
                document.getElementById("login-header").innerHTML = "Login";

            }
            else {
                document.getElementById("login-header").innerHTML = "Logout";
            }
        },
        error: function (response) {
            alert("Failed....");
        }
    })
});