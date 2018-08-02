const request = require('../../request');
const facebookHelper = require('./facebook-helper');

async function get(username) {
    try {
        let res = await request.get(`/band/${username}`);
        return res.body;
    } catch(err) {
        throw Error(err);
    }
}

async function create(data, sessionId) {
    try {
        let band = data.band;

        const pageId = await facebookHelper.getPageIdFromVanityUrl(data.facebookUrl);
        band.socialComponents.push({
            type: 'FACEBOOK',
            link: `fb:\/\/page/${pageId}`
        });

        const image = await facebookHelper.getPageProfileImage(pageId);
        band.imageComponent = {
            image: image
        };

        request.headers['session'] = sessionId;
        const res = await request.post('/band/', band);
        //FINISH
    } catch(err) {
        throw Error(err);
    }
}

async function getFollowerCount(band) {
    try {
        const res = await request.get(`/band/${band}/followers`);
        return res.body;
    } catch(err) {
        throw Error(err);
    }
}

module.exports = {
    get: get,
    create: create,
    getFollowerCount: getFollowerCount
}