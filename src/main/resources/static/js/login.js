$(document).ready(function () {
    var counter = 0;
    $("#button-submit-user").click(function (e) {
        e.preventDefault();
        var formData = new FormData();
        formData.append("username", document.getElementById("username").value);
        formData.append("password", document.getElementById("password").value)
        $.ajax({
            method: 'post',
            processData: false,
            contentType: false,
            cache: false,
            data: formData,
            enctype: 'multipart/form-data',
            url: 'http://localhost:8080/api/login',
            success: function (response) {
                localStorage.setItem('userJWT', JSON.stringify(response.accessToken));
                window.location.href = "./index.html";
            },
            error: function (response) {
                if (counter == 0) {
                    var paragraph = document.createElement("p");
                    var text = document.createTextNode("Bad credentials");
                    paragraph.appendChild(text);
                    var element = document.getElementById("main-element");
                    element.appendChild(paragraph);
                    paragraph.style.cssText += 'grid-row:2; display: grid; justify-content: center; align-content: center; border-radius: 5px; background-color: rgb(249, 122, 122); font-family: initial;'
                }
                counter++;

            }
        })
    });
});