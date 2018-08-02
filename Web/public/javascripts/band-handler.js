$(document).ready(function() {
    $('#create-band').click(handleCreateBand);
});

function handleCreateBand() {
    var name = $('#bandName').val();
    var about = $('#about').val();
    var facebookUrl = $('#facebookUrl').val();
    var instagramUsername = $('#instagramUsername').val();
    var spotifyUrl = $('#spotifyUrl').val();
    var twitterUsername = $('#twitterUsername').val();
    var youtubeVideoUrl = $('#youtubeVideoUrl').val();

    $.ajax({
        method: 'POST',
        url: '/band/create',
        contentType: 'application/json; charset=utf-8',
        dataType: 'json',
        data: JSON.stringify({
            facebookUrl: facebookUrl,
            band: {
                bandInfo: {
                    name: name,
                    username: name.replace(/ /g, '').toLowerCase()
                },
                aboutComponent: {
                    description: about
                },
                youtubeComponent: {
                    link: youtubeVideoUrl
                },
                socialComponents: [
                    {
                        type: 'TWITTER',
                        link: `twitter:\/\/user?screen_name=${twitterUsername}`
                    },
                    {
                        type: 'INSTAGRAM',
                        link: `instagram:\/\/user?username=${instagramUsername}`
                    },
                    {
                        type: 'SPOTIFY',
                        link: spotifyUrl
                    }
                ]
            }
        }),
        success: function(data) {
            console.log(data);
        }
    })
}