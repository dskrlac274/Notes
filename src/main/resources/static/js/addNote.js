$(document).ready(function() {
//add update button
//if search params are present deactivate add button ,activate update button
//add update note in backend
var token = JSON.parse(localStorage.getItem('userJWT'));
    console.log(`Authorization=Bearer ${token}`)
    var searchParams = new URLSearchParams(window.location.search)
    if(searchParams.has('title') || searchParams.has('description'))
    {
    console.log("luckyy");
        let titleValue = searchParams.get('title');
        let descriptionValue = searchParams.get('description');
        var title = document.getElementById("title");
        var description = document.getElementById("description");
        title.value = titleValue;
        description.value = descriptionValue;
    }

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