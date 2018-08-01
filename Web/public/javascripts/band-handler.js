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
        data: {
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
                        link: `twitter://user?screen_name=${twitterUsername}`
                    },
                    {
                        type: 'INSTAGRAM',
                        link: `instagram://user?username=${instagramUsername}`
                    },
                    {
                        type: 'SPOTIFY',
                        link: spotifyUrl
                    }
                ],
                name: name,
                about: about,
                facebookUrl: facebookUrl,
                instagramUsername: instagramUsername,
            }
        },
        success: function(data) {
            console.log(data);
        }
    })
}