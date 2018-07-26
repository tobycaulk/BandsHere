$(document).ready(function() {
    $('#register-btn').click(handleRegister);
});

function handleRegister() {
    var email = $('#registerEmail').val();
    var username =  $('#registerUsername').val();
    var password = $('#registerPassword').val();

    $.ajax({
        method: 'POST',
        url:  '/user/',
        data: {
            email: email,
            username: username,
            password: password
        },
        success: function(data) {
            console.log(data);
        }
    })
}