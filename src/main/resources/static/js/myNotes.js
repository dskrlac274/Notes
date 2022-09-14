$(document).ready(function() {
var counter = 0;
var index = 0;
var addMarginToDiv = 180;
var token = JSON.parse(localStorage.getItem('userJWT'));
$("#login-header").click(function(e) {
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
          });
$("#login-header").click(function(e) {
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
            /*data: formData,*/
            enctype: 'multipart/form-data',
            url: 'http://localhost:8080/api/notes',
            headers: {
                        'Authorization': 'Bearer ' + token
                    },
        success: function (response) {
            console.log(response);

            for(let i=0;i<response.length;i++){
                var newDiv = document.createElement("div");
                var mainElement = document.getElementById("main-my-notes");
                mainElement.appendChild(newDiv);
                var addToEndTitle = "";
                var addToEndDescription = "";

                newDiv.classList.add("note-unit");
                if(response[i].title.length > 60 || response[i].description.length > 100){
                    addToEndTitle = "...";
                    addToEndDescription = "...";
                    }
                index++;
                newDiv.insertAdjacentHTML( 'beforeend',
                '<p class="title-position">Title: '+ response[i].title.substring(0, 60) + addToEndTitle + '</p>' +
                '<p class="description-position">Description: ' + response[i].description.substring(0, 100) + addToEndDescription + '</p>' +
                '<p class="last-modified-position">Last modified: ' + response[i].lastModifiedDate + '</p>' +
                '<p class="created-position">Created: ' + response[i].creationDate + '</p>' +
                '<a id="' + index + '"' + 'href="" class="redirect-to-add-note fa-plus ">></a>');
                if(counter>0){
                    newDiv.style.cssText += 'margin-top:' +addMarginToDiv + 'px';
                    addMarginToDiv+=180;
                }
                counter++;
            }
            $(".redirect-to-add-note").click(function(e) {
                e.preventDefault();
                var formData = new FormData();
                formData.append("id", this.id);

                $.ajax({
                            method: 'post',
                            processData: false,
                            contentType: false,
                            cache: false,
                            data: formData,
                            enctype: 'multipart/form-data',
                            url: 'http://localhost:8080/api/note',
                            headers: {
                                        'Authorization': 'Bearer ' + token
                                    },
                        success: function (response) {
                            console.log(response);
                            //document.getElementById(response.id).href = "./addNote.html/?title=" + response.title +"&description=" + response.description;
                            window.location.href = "./addNote.html?id=" + response.id;
                           /* window.location.href = "./addNote.html"*/
                            /*var title = document.getElementById("title");
                            var description = document.getElementById("description");
                            title.text = response.title;
                            description.text = response.description;*/
                        },
                        error: function (response) {
                           alert("Failed....");
                          }
                  })
                });
        },
        error: function (response) {
           alert("Failed....");
          }
    })

});