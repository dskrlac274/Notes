$(document).ready(function() {
var token = JSON.parse(localStorage.getItem('userJWT'));
    console.log(`Authorization=Bearer ${token}`)
    fetch('http://localhost:8080/api/profile', {
        method: 'GET',
        headers: {
            'Authorization': 'Bearer ' + token
        }
    })
        .then(res => res.json())
        .then(data => {
            console.log(data)
            var firstName = document.getElementById("firstName").value = data.firstName;
            var lastName = document.getElementById("lastName").value = data.lastName;
            var username = document.getElementById("username").value = data.username;
            var email = document.getElementById("email").value =  data.email;
            var password = document.getElementById("password").value = data.password;
            //add sliku jos (later)
        })
        .catch(err => {
        console.log(err)
        //logout
        })

});