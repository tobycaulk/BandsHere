var REGISTER_STATE = 0;
var SIGN_IN_STATE = 1;

var registerState = REGISTER_STATE;

$(document).ready(function() {
    $('#register-btn').click(handleRegisterClick);
    $('#signout').click(handleSignout);
    $('#signInLink').click(handleSignInFlip);

    $('#registerModal').on("show", function() {
        $("body").addClass("modal-open");
    }).on("hidden", function() {
        $("body").removeClass("modal-open");
    });
});

function handleRegisterClick() {
    if(registerState == REGISTER_STATE) {
        handleRegister();
    } else if(registerState == SIGN_IN_STATE) {
        handleSignIn();
    }
}

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
    });
}

function handleSignIn() {
    var email = $('#registerEmail').val();
    var password = $('#registerPassword').val();

    $.ajax({
        method: 'POST',
        url: '/user/authenticate/',
        data: {
            email: email,
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
    });
}

function handleSignout() {
    $.ajax({
        method: 'DELETE',
        url: '/user/session',
        success: function(data) {
            location.reload();
        },
        error: function(data) {
            console.log(data);
            location.reload();
        }
    });
}

function handleSignInFlip() {
    if(registerState == REGISTER_STATE) {
        registerState = SIGN_IN_STATE;
        displaySignInForm();
    } else if(registerState == SIGN_IN_STATE) {
        registerState = REGISTER_STATE;
        displayRegisterForm();
    }
}

function displayRegisterForm() {
    $('.register').each(function(index, element) {
        $(this).show();
    });

    $('#register-btn').text('Register');
}

function displaySignInForm() {
    $('.register').each(function(index, element) {
        $(this).hide();
    });

    $('#register-btn').text('Sign In');
}