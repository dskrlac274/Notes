$(document).ready(function() {
    var id = 0;
    var title = 0;
    var description = 0;
    var titleValue = 0
    var descriptionValue = 0;

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
    var searchParams = new URLSearchParams(window.location.search)
    if(searchParams.has('id'))
    {

        document.querySelector('#button-addNote').disabled = true;
        document.querySelector('#button-addNote').style.backgroundColor = 'grey';
        id = searchParams.get('id');
        var formData = new FormData();
        formData.append("id", id);

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
                   title = document.getElementById("title");
                   description = document.getElementById("description");
                   titleValue = response.title;
                   descriptionValue = response.description;
                   title.value = titleValue;
                   description.value = descriptionValue;
                },
                error: function (response) {
                   alert("Failed....");
                  }
          })
    }
    else{
        document.querySelector('#button-update').disabled = true;
        document.querySelector('#button-update').style.backgroundColor = 'grey';
    }

    $("#button-update").click(function(e) {
            e.preventDefault();
            titleValue = document.getElementById("title").value;
            descriptionValue = document.getElementById("description").value;
            var formData = new FormData();
            formData.append("idToUpdate", id);
            formData.append("title", titleValue);
            formData.append("description", descriptionValue);

            $.ajax({
                method: 'put',
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
                    window.location.href = document.referrer;
                },
                error: function (response) {
                   alert("Failed....");
                  }
              })
    });
    $("#button-addNote").click(function(e) {
            e.preventDefault();
            var formData = new FormData();
            formData.append("title", document.getElementById("title").value);
            formData.append("description",document.getElementById("description").value)
            $.ajax({
                method: 'post',
                processData: false,
                contentType: false,
                cache: false,
                data: formData,
                enctype: 'multipart/form-data',
                url: 'http://localhost:8080/api/addNote',
                headers: {
                            'Authorization': 'Bearer ' + token
                        },
                success: function (response) {
                    window.location.href = document.referrer;
                },
                error: function (response) {
                   alert("Failed....");
                  }
            })
        });
        $("#button-clear").click(function(e) {
            e.preventDefault();
            var element = document.getElementById("description");
            element.value="";
        });
});