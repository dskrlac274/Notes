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
    var firstCharIsSpace = false
    var isFirst = true
    var diff = 0
    var wordSugg = ""


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
           let range2 = range.cloneRange()
           range2.selectNodeContents(el)
           console.log(range2)
           position.start = range.startOffset
           position.end = range.endOffset
         }
    return [position.start , position.end];
};
function replaceAt(wholeString, fetchingString, index, replacement) {
    var restOfString = ""
    var cut = 0;
    var string = 0
    var spaces = wholeString.split(' ').length - 1;
    var charsBefore = wholeString.split(' ')
    var wholeStringLength = wholeString.length

    wholeStringLength = wholeStringLength - fetchingString.length;
    if(spaces > 0) {
        cut = index - wholeStringLength ;
        string = fetchingString.substring(0, cut) + replacement;
    }
    else{
        string = fetchingString.substring(0, index) + replacement;
    }
    if(fetchingString.length != string.length){
        if(spaces >0 )restOfString = fetchingString.substring(cut+1)
        else restOfString = fetchingString.substring(index+1)
        string = string + restOfString
    }
    return string;
}
$("#button-live-check").click(function(e) {
    if(document.getElementById("button-live-check").getAttribute('name') == "turn-on")
     {
        document.getElementById("button-live-check").value = "Turn on LIVE spellcheck"
        document.getElementById("button-live-check").name= "turn-off"

     }
     else{
         document.getElementById("button-live-check").value = "Turn off LIVE spellcheck "
         document.getElementById("button-live-check").name= "turn-on"
     }
});

 $('#description').on('keydown', function(e) {
 var wordInput = e.key
     console.log(e)
     if(isFirst == true && e.keyCode == 32) {firstCharIsSpace = true}
     isFirst = false;
     if(e.keyCode>32 || e.keyCode<126 ){
     if(e.keyCode<=32 || e.keyCode>=126 || e.key == "ArrowLeft" || e.key == "ArrowRight" ||
            e.key == "ArrowDown" || e.key == "ArrowUp" || e.key == "Delete")
         word = word
     else word += wordInput;

     if(e.keyCode != 32)
     {
         firstCharIsSpace = false
         console.log(e.keyCode)
         var position = $(this).getCursorPosition();
         console.log("Position je" + position)
             var deleted = '';
             var val = document.getElementById('description').textContent;
             if (e.keyCode == 8) {
             console.log("sad je delete gornji")
                 if (position[0] == 0)
                     deleted = '';
                 else{
                     deleted = val.substr(position[0] - 1, 1);
                     var indexToDelete = position[0]-1;
                     console.log("brisem na:" + indexToDelete)
                     word = replaceAt(document.getElementById('description').textContent,word,indexToDelete, "")}

             }
             else if (e.keyCode == 46) {
                         console.log("sad je delete donji")

                 var val = document.getElementById('description').textContent;

                     if (position[0] === val.length)
                         deleted = '';
                     else
                         deleted = val.substr(position[0], 1);
                         var indexToDelete = position[0];
                         console.log("brisem: " + indexToDelete)
                         word = replaceAt(document.getElementById('description').textContent,word,indexToDelete, "")
             }
                     console.log("deleted je " + deleted)



         console.log("word jee " + word)
         }
     }
 if(document.getElementById("button-live-check").getAttribute('name') == "turn-on")
 {
     if(firstCharIsSpace == false && wordInput.charCodeAt(0) == 32 && numberOfSpacesInRow < 1){
        numberOfSpacesInRow++;
/*        var originHeaders = new Headers();

         originHeaders.append("Access-Control-Allow-Origin", "*//*")
         originHeaders.append("Access-Control-Allow-Headers", "Content-Type")
         originHeaders.append("Access-Control-Allow-Methods", "POST, GET, OPTIONS")*/
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
                                            var len = $("#description").val().length;
                                            const input = document.getElementById('description');
                                            input.focus();
                                            document.execCommand('selectAll', false, null);
                                            document.getSelection().collapseToEnd();
                                           }
                                           console.log(wordsInTextArea);
                                         word = "";
                                       },
                                       error: function (response) {
                                          alert("Failed....");
                                         }
                                 })

                                  }, 350);

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
    }
    if(document.getElementById("button-enable-suggestions").getAttribute('name') == "turn-on")
     {
        if(firstCharIsSpace == false && numberOfSpacesInRow < 1){

            numberOfSpacesInRow++;
            if(word.length>2){
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
                                                       url: 'http://localhost:8081/suggestions',
                                                       data: word,
                                                       headers: {
                                                         'Access-Control-Allow-Origin': '*',
                                                         'Access-Control-Allow-Methods': 'POST, GET, OPTIONS',
                                                         'Content-Type': 'application/json'
                                                        },
                                                   success: function (response) {
                                                   response = response.replace(/'/g, '"')
                                                   response = JSON.parse(response)
                                                   wordsInTextArea.push(word);
                                                   console.log(response)

                                                    /*<div class="dropdown">
                                                             <span>Mouse over me</span>
                                                             <div class="dropdown-content">
                                                             <a href="./">Hello World!</a>
                                                             </div>
                                                           </div>*/
                                                    $("#description").html($("#description").html().replace(word,''));

                                                    var newDiv = document.createElement("div");
                                                    var descriptionDiv = document.getElementById("description")
                                                      newDiv.classList.add("dropdown");
                                                      descriptionDiv.appendChild(newDiv);
                                                    var newSpan = document.createElement("span");
                                                    var spanContent = document.createTextNode(word);

                                                    newSpan.appendChild(spanContent)
                                                    newDiv.appendChild(newSpan);
                                                    console.log("word je "+ word)


                                                    var newDiv2 = document.createElement("div");
                                                    newDiv2.classList.add("dropdown-content");
                                                    newDiv.appendChild(newDiv2);


                                                    for(let i=0;i<response.length;i++){
                                                        var newA = document.createElement("a");
                                                        var AContent = document.createTextNode(String(response[i]));
                                                        newA.appendChild(AContent);
                                                        newDiv2.appendChild(newA);
                                                    }

                                                        word = "";
                                                   },
                                                   error: function (response) {
                                                      alert("Failed....");
                                                     }
                                             })

                                              }, 350);
                      },
                      error: function (response) {
                         alert("Failed....");
                         console.log("failed1")
                        }
                })
                }
        }
        else{
                numberOfSpacesInRow = 0;
            }
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
            descriptionValue = document.getElementById("description").textContent;
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
                    window.location.href = "./myNotes.html";
                },
                error: function (response) {
                   alert("Failed....");
                  }
              })
    });
    $("#button-off-line-check").click(function(e) {
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
              /*var formData = new FormData();
              formData.append("text", wordsInTextArea);*/
                    window.setTimeout(function(){
                           $.ajax({
                                               method: 'post',
                                               crossDomain: true,
                                               processData: false,
                                               cache: false,
                                               url: 'http://localhost:8081/testAll',
                                               data: document.getElementById("description").textContent,
                                               headers: {
                                                 'Access-Control-Allow-Origin': '*',
                                                 'Access-Control-Allow-Methods': 'POST, GET, OPTIONS',
                                                 'Content-Type': 'application/json'
                                                },
                                           success: function (response) {
                                           response = response.replace(/'/g, '"')
                                           var responseJson = JSON.parse(response)
                                           //var keys = Object.keys(responseJson)
                                          Object.keys(responseJson).forEach(function(key) {
                                            //console.log('Key : ' + key + ', Value : ' + data[key])
                                            if(responseJson[key] == "False")
                                               {
                                                    console.log(responseJson[key])
                                                    $("#description").html($("#description").html().replace(key,'<span class = "span-class" style="text-decoration: underline wavy red;" >' + key + '</span>' + " "));
                                                    $( "#description" ).append("&nbsp;");

                                               }
                                          })
                                           },
                                           error: function (response) {
                                              alert("Failed2....");
                                             }
                                     })

                                      }, 350);
              },
              error: function (response) {
                 alert("Failed1....");
                }
        })
    });
/*    $( "#description" ).focus(function() {
           document.getElementById("description").focus();

    });*/
    $("#button-addNote").click(function(e) {
            e.preventDefault();
            var formData = new FormData();
            formData.append("title", document.getElementById("title").value);
            formData.append("description",document.getElementById("description").textContent)
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
                    window.location.href = "./myNotes.html";
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

      $("#button-music").click(function(e) {
        e.preventDefault();
        $.ajax({
                          method: 'get',
                          processData: false,
                          cache: false,
                          contentType: "application/json",
                          crossDomain: true,
                          url: 'http://localhost:8080/api/python',
                      success: function (response) {
                      console.log(response)

                        window.setTimeout(function(){
                               $.ajax({
                                                   method: 'post',
                                                   crossDomain: true,
                                                   processData: false,
                                                   cache: false,
                                                   url: 'http://localhost:8081/music',
                                                   headers: {
                                                     'Access-Control-Allow-Origin': '*',
                                                     'Access-Control-Allow-Methods': 'POST, GET, OPTIONS',
                                                     'Content-Type': 'application/json'
                                                    },
                                               success: function (response) {
                                                    console.log(response)
                                               },
                                               error: function (response) {
                                                  alert("Failed2....");
                                                 }
                                         })

                                          }, 350);
                      },
                      error: function (response) {
                         alert("Failed1....");
                        }
                })
      });
      $("#button-enable-suggestions").click(function(e) {
          if(document.getElementById("button-enable-suggestions").getAttribute('name') == "turn-on")
           {
              document.getElementById("button-enable-suggestions").value = "Enable suggestions"
              document.getElementById("button-enable-suggestions").name= "turn-off"

           }
           else{
               document.getElementById("button-enable-suggestions").value = "Disable suggestions "
               document.getElementById("button-enable-suggestions").name= "turn-on"
           }
      });

      /*$("#description")..on('keydown', function(e) {
      if(document.getElementById("button-live-check").getAttribute('name') == "turn-on")
       {
                var wordInput2 = e.key

                if(e.keyCode<=32 || e.keyCode>=126 || e.key == "ArrowLeft" || e.key == "ArrowRight" ||
                       e.key == "ArrowDown" || e.key == "ArrowUp" || e.key == "Delete")
                    wordSugg = wordSugg
                else wordSugg += wordInput;
              $.ajax({
                    method: 'get',
                    processData: false,
                    cache: false,
                    contentType: "application/json",
                    crossDomain: true,
                    url: 'http://localhost:8080/api/python',
                success: function (response) {
                console.log(response)
                var formData = new FormData();
                formData.append("word", word);
                      window.setTimeout(function(){
                             $.ajax({
                                                 method: 'post',
                                                 crossDomain: true,
                                                 processData: false,
                                                 cache: false,
                                                 url: 'http://localhost:8081/suggestions',
                                                 data: word,
                                                 headers: {
                                                   'Access-Control-Allow-Origin': '*',
                                                   'Access-Control-Allow-Methods': 'POST, GET, OPTIONS',
                                                   'Content-Type': 'application/json'
                                                  },
                                             success: function (response) {

                                             },
                                             error: function (response) {
                                                alert("Failed....");
                                               }
                                       })

                                        }, 350);
                },
                error: function (response) {
                   alert("Failed....");
                   console.log("failed1")
                  }
          })
          }
          });*/
});