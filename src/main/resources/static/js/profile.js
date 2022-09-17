$(document).ready(function() {
var token = JSON.parse(localStorage.getItem('userJWT'));
$("#login-header").click(function(e) {
            if(document.getElementById("login-header").innerHTML == "Logout")
                {
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
                            window.location.href="./index.html";
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
                        console.log(response)
                        if(response.length == 0){
                            window.location.href = "./login.html";
                            document.getElementById("login-header").innerHTML = "Login";

                        }
                        else{
                            document.getElementById("login-header").innerHTML = "Logout";
                        }
                    },
                    error: function (response) {
                       alert("Failed....");
                      }
              })
    $.ajax({
            method: 'get',
            processData: false,
            contentType: false,
            cache: false,
            enctype: 'multipart/form-data',
            url: 'http://localhost:8080/api/profile',
            headers: {
                        'Authorization': 'Bearer ' + token
                    },
        success: function (response) {
           var firstName = document.getElementById("firstName").value = response.firstName;
           var lastName = document.getElementById("lastName").value = response.lastName;
           var username = document.getElementById("username").value = response.username;
           var email = document.getElementById("email").value =  response.email;
           var password = document.getElementById("password").value = response.password;
        },
        error: function (response) {
           alert("Failed....");
          }
  })
    $.ajax({
          method: 'get',
          processData: false,
          contentType: false,
          cache: false,
          enctype: 'multipart/form-data',
          url: 'http://localhost:8080/api/image',
          headers: {
                  'Authorization': 'Bearer ' + token
              },
      success: function (response) {
          if(response != ""){
           document.getElementById("user-profile-image").src = "data:image/png;base64," +
            btoa(String.fromCharCode.apply(null, new Uint8Array(response)));
          }

      },
      error: function (response) {
        alert("..failed");
        }
})
  $("#button-submit").click(function(e) {
          e.preventDefault();
          var formData = new FormData();
          formData.append("password",document.getElementById("password").value)
          formData.append("email", document.getElementById("email").value);
          $.ajax({
                  method: 'put',
                  processData: false,
                  contentType: false,
                  cache: false,
                  data: formData,
                  enctype: 'multipart/form-data',
                  url: 'http://localhost:8080/api/profile',
                  headers: {
                          'Authorization': 'Bearer ' + token
                      },
              success: function (response) {
                  window.location.reload();
              },
              error: function (response) {
                alert("..failed");
                }
          })
      });
      $("#button-image").on('change', function (e) {

                e.preventDefault();
                var formData = new FormData();
                jQuery.each(jQuery('#button-image')[0].files, function(i, file) {
                    formData.append('file', file);
                });
                $.ajax({
                        method: 'post',
                        processData: false,
                        contentType: false,
                        cache: false,
                        data: formData,
                        enctype: 'multipart/form-data',
                        url: 'http://localhost:8080/api/image',
                        headers: {
                                'Authorization': 'Bearer ' + token
                            },
                    success: function (response) {
                        document.getElementById("user-profile-image").src = "data:image/png;base64," +
                        btoa(String.fromCharCode.apply(null, new Uint8Array(response)));
                    },
                    error: function (response) {
                      alert("..failed");
                      }
                })
            });

});
