var REGISTER_STATE = 0;
var SIGN_IN_STATE = 1;

var registerState = REGISTER_STATE;

$(document).ready(function() {
    $('#register-btn').click(handleRegisterClick);
    $('#signout').click(handleSignout);
    $('#signInLink').click(handleSignInFlip);
    $('#followBand').click(handleFollowBand);

    checkIfIsFollowingBand();

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
    var registerBandPage = $('#registerCreateBandPage').is(":checked");

    $.ajax({
        method: 'POST',
        url:  '/user/',
        data: {
            email: email,
            username: username,
            password: password
        },
        success: function(data) {
            if(registerBandPage) {
                
            } else {
                location.reload();
            }
        },
        error: function(data) {
            $('#registerAlert').show();
            $('#followAlert').addClass('show');
            $('#registerAlertText').text(data.responseText);
            setTimeout(function() {
                $('#registerAlert').hide();
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
            $('#registerAlert').show();
            $('#registerAlertText').text(data.responseText);
            setTimeout(function() {
                $('#registerAlert').hide();
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
            location.reload();
        }
    });
}

function handleFollowBand() {
    var bandUsername = $('#band-username').val();

    $.ajax({
        method: 'POST',
        url: '/user/followBand/',
        data: {
            username: bandUsername
        },
        success: function(following) {
            retrieveFollowerCount(bandUsername);
            if(following) {
                $('#followBand').removeClass('btn-empty');
                $('#follow-text').text('Unfollow');
            } else {
                $('#followBand').addClass('btn-empty');
                $('#follow-text').text('Follow');
            }
        },
        error: function(data) {
            $('#followAlert').show();
            $('#followAlert').addClass('show');
            $('#followAlertText').text(data.responseText);
            setTimeout(function() {
                $('#followAlert').hide();
            }, 5000);
        }
    })
}

function checkIfIsFollowingBand() {
    var bandUsername = $('#band-username').val();

    $.ajax({
        method: 'GET',
        url: '/user/followBand?username=' + bandUsername,
        success: function(following) {
            if(following) {
                $('#followBand').removeClass('btn-empty');
                $('#follow-text').text('Unfollow');
            } else {
                $('#followBand').addClass('btn-empty');
                $('#follow-text').text('Follow');
            }
        },
        error: function(data) {
            console.log('Error while checking if user is following this band [' + JSON.stringify(data) + ']');
        }
    })
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