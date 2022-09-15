$(document).ready(function() {
    var id = 0;
    var title = 0;
    var description = 0;
    var titleValue = 0
    var descriptionValue = 0;
    var wordsInTextArea = [];
    var word = "";

/*.curly-underline {
  text-decoration: underline wavy red;
}*/
function demo(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
async function sleep() {
        await demo(100000);
}

 $('#description').on('input', function(e) {
    var lastCharacter = this.textContent.charAt(this.textContent.length-1);
    var wordInput = e.originalEvent.data;
     word += wordInput;

    if(lastCharacter.charCodeAt(0) == 160){
        wordsInTextArea.push(word);
        word = "";
        var originHeaders = new Headers();

         originHeaders.append("Access-Control-Allow-Origin", "/*")
         originHeaders.append("Access-Control-Allow-Headers", "Content-Type")
         originHeaders.append("Access-Control-Allow-Methods", "POST, GET, OPTIONS")
        $.ajax({
              method: 'get',
              processData: false,
              cache: false,
              contentType: "application/json",
              crossDomain: true,
              url: 'http://localhost:8080/api/python',
              headers: {
                      'Authorization': 'Bearer ' + token,
                      'Access-Control-Allow-Origin': 'http://localhost:8081/test',
                      'Access-Control-Allow-Methods': 'POST, GET, OPTIONS'
                  },
          success: function (response) {
          console.log(response)
          var formData = new FormData();
          formData.append("word", wordsInTextArea[wordsInTextArea.length - 1]);
                window.setTimeout(function(){
                       $.ajax({
                                           method: 'post',
                                           crossDomain: true,
                                           processData: false,
                                           cache: false,
                                           url: 'http://localhost:8081/test',
                                           data: wordsInTextArea[wordsInTextArea.length - 1],
                                           headers: {
                                             'Access-Control-Allow-Origin': '*',
                                             'Access-Control-Allow-Methods': 'POST, GET, OPTIONS',
                                             'Content-Type': 'application/json'
                                            },
                                       success: function (response) {
                                       console.log(response)
                                       if(response == "False"){
                                            console.log(wordsInTextArea[wordsInTextArea.length - 1])
                                        //wordsInTextArea[wordsInTextArea.length - 1] = '<span style="text-decoration: underline wavy red;" >' + wordsInTextArea[wordsInTextArea.length - 1] + '</span>';
                                        $('#description').html($('#description').html().replace(wordsInTextArea[wordsInTextArea.length - 1], "<span style='color: red;'>wrd</span>"));
                                           }
                                       },
                                       error: function (response) {
                                          alert("Failed....");
                                         }
                                 })

                                  }, 300);

          },
          error: function (response) {
             alert("Failed....");
             console.log("failed1")
            }
    })
    }
});
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