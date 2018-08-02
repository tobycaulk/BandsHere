$(document).ready(function() {
    retrieveFollowerCount(getBandUsername());
});

function getBandUsername() {
    return $('#band-username').val();
}

function retrieveFollowerCount(username) {
    $.ajax({
        method: 'GET',
        url: '/band/' + username + '/followers',
        success: function(data) {
            $('#follower-count').text(data.followerCount);
        },
        error: function(data) {
        }
    })
}