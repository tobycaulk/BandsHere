const request = require('../../request');

async function get(username) {
    try {
        let res = await request.get(`/band/${username}`);
        return res.body;
    } catch(err) {
        throw Error(err);
    }
}

module.exports = {
    get: get
}