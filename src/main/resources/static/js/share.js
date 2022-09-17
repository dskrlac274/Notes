$(document).ready(function() {
var counter = 0;
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

    $("#choose-note-label").focus(function(e) {
            e.preventDefault();
            $.ajax({
                    method: 'get',
                    processData: false,
                    contentType: false,
                    cache: false,
                    enctype: 'multipart/form-data',
                    url: 'http://localhost:8080/api/notes',
                    headers: {
                                'Authorization': 'Bearer ' + token
                            },
                success: function (response) {
                    if(counter==0)
                        for(let i=0;i<response.length;i++)
                            $("#choose-note-label").append(""+ '<option value=' + response[i].id + ' ' + '>' + response[i].title +'</option>' +"");
                    counter++;
                },
                error: function (response) {
                   alert("Failed....");
                  }
            })
        });
        $("#choose-note-label").click(function(e) {
        //addat opciju ako se klikne vaj value -1 da se ne dogodi nista jer inace baca error
        //i promijeniti te eventove krivo rade (pogledaj to kasnije)
        var formData = new FormData();
        formData.append("id",$('#choose-note-label').find(":selected")[0].attributes[0].value);
                    e.preventDefault();
                    var xhr = new XMLHttpRequest();
                        xhr.open('POST', 'http://localhost:8080/api/pdf', true);
                        xhr.responseType = 'arraybuffer';
                        xhr.onload = function(e) {
                        console.log(this.response);
                              var blob=new Blob([this.response], {type:"application/pdf"});
                              var link=document.createElement('a');
                              $('#iframe-for-pdf').attr("src", window.URL.createObjectURL(blob));
                              /*link.click()*/
                        };
                    xhr.send(formData);
                });

});