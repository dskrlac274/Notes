$(document).ready(function() {
var token = JSON.parse(localStorage.getItem('userJWT'));
    console.log(`Authorization=Bearer ${token}`)
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