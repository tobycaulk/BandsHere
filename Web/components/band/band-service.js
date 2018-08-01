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

async function create(data) {
    try {
        const pageId = await facebookHelper.getPageIdFromVanityUrl(data.facebookUrl);
        console.log(pageId);
    } catch(err) {
        throw Error(err);
    }
}

module.exports = {
    get: get,
    create: create
}