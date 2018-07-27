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
            location.reload();
        },
        error: function(data) {
            $('#registerAlert').addClass('show');
            $('#registerAlertText').text(data.responseText);
            setTimeout(function() {
                $('#registerAlert').alert('close')
            }, 5000);
        }
    })
}