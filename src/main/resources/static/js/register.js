$(document).ready(function() {
    var counter = 0;
    $("#button-register").click(function(e) {
        e.preventDefault();
         var formData = new FormData();
         formData.append("firstName", document.getElementById("firstName").value);
         formData.append("lastName",document.getElementById("lastName").value);
         formData.append("username", document.getElementById("username").value);
         formData.append("password",document.getElementById("password").value);
         formData.append("email",document.getElementById("email").value)

        $.ajax({
                method: 'post',
                processData: false,
                contentType: false,
                cache: false,
                data: formData,
                enctype: 'multipart/form-data',
                url: 'http://localhost:8080/api/register',
            success: function (response) {
                window.location.href = "./login.html";
            },
            error: function (response) {
               window.location.href = "./index.html";
              }
        })
    });
    $("#button-cancel-register").click(function(e) {
            e.preventDefault();
            window.location.href = "./index.html";
        });
});