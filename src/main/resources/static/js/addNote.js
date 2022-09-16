$(document).ready(function() {
    var id = 0;
    var title = 0;
    var description = 0;
    var titleValue = 0
    var descriptionValue = 0;
    var wordsInTextArea = [];
    var word = "";
    var itWasSpace = false;
    var numberOfSpacesInRow = 0;

/*.curly-underline {
  text-decoration: underline wavy red;
}*/
function demo(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
async function sleep() {
        await demo(100000);
}
$.fn.getCursorPosition = function() {
    var el = $(this).get(0);
    console.log(el)
    let position = {start:0,end:0};
    let selection = document.getSelection();
    console.log(selection)
     if (selection.rangeCount){
           let range = selection.getRangeAt(0);
           let range2 = range.cloneRange()  // don't mess with visible cursor
           range2.selectNodeContents(el)    // select content
           console.log(range2)
           position.start = range.startOffset
           position.end = range.endOffset
         }
    // return both selection start and end;
    return [position.start , position.end];
};
function replaceAt(fetchingString, index, replacement) {
    var string = fetchingString.substring(0, index) + replacement;
    var restOfString = ""
    if(fetchingString.length != string.length){
        restOfString = fetchingString.substring(index+1)
        string = string + restOfString
    }
    return string;
}
 $('#description').on('keydown', function(e) {
    var wordInput = e.key
    console.log(e.key.charCodeAt(0))
    if(e.keyCode<=32 || e.keyCode>=126 || e.key == "ArrowLeft" || e.key == "ArrowRight" ||
    e.key == "ArrowDown" || e.key == "ArrowUp"){
        word = word;
    }
    else{
         word += wordInput;

        var position = $(this).getCursorPosition();
        console.log("Position je" + position)
            var deleted = '';
            var val = document.getElementById('description').textContent;
            if (e.keyCode== 8) {
            console.log("sad je delete gornji")
                if (position[0] == 0)
                    deleted = '';
                else{
                    deleted = val.substr(position[0] - 1, 1);
                    var indexToDelete = position[0]-1;
                    console.log("brisem na:" + indexToDelete)
                    word = replaceAt(word,indexToDelete, "")}

            }
            else if (e.keyCode == 127) {
                        console.log("sad je delete donji")

                var val = $(this).val();
                if (position[0] == position[1]) {

                    if (position[0] === val.length)
                        deleted = '';
                    else
                        deleted = val.substr(position[0], 1);
                        word.replaceAt(1 - position[0], "")
                }
            }
                    console.log("deleted je " + deleted)

            //regex = /\w\W*(deleted)\W*/;
            //word = word.replace(word.match(regex)[0],"");

        console.log("word jee " + word)
    }
     if(wordInput.charCodeAt(0) == 32 && numberOfSpacesInRow < 1){
        numberOfSpacesInRow++;
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
          formData.append("word", word);
                window.setTimeout(function(){
                       $.ajax({
                       //ne uzeti uvijek zadnju rijec nego uzeti zadnju upisanu riječ
                       //reci mu da ne cita delete i spacaeove
                                           method: 'post',
                                           crossDomain: true,
                                           processData: false,
                                           cache: false,
                                           url: 'http://localhost:8081/test',
                                           data: word,
                                           headers: {
                                             'Access-Control-Allow-Origin': '*',
                                             'Access-Control-Allow-Methods': 'POST, GET, OPTIONS',
                                             'Content-Type': 'application/json'
                                            },
                                       success: function (response) {
                                       console.log(response)
                                       wordsInTextArea.push(word);

                                       if(response == "False"){
                                            //wordsInTextArea[wordsInTextArea.length - 1] = wordsInTextArea[wordsInTextArea.length - 1].replace(" ", "")
                                            console.log("word je " + word)
                                            $("#description").html($("#description").html().replace(word,'<span style="text-decoration: underline wavy red;" >' + word + '</span>'));
                                           }
                                           console.log(wordsInTextArea);
                                         word = "";
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
    else{
        numberOfSpacesInRow = 0;
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