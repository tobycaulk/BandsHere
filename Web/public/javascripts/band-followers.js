$(document).ready(function() {
    retrieveFollowerCount(getBandUsername());
});

function getBandUsername() {
    return $('#band-username').val();
}

function retrieveFollowerCount(username) {
    $.ajax({
        method: 'GET',
        url: 'http://69fb615f.ngrok.io/band/' + username + '/followers',
        success: function(data) {
            $('#follower-count').text(data);
        },
        error: function(data) {
            alert(JSON.stringify(data));
        }
    })
}