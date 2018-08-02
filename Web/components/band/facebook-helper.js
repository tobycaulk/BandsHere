const cheerio = require('cheerio');
const request = require('request-promise-native');

const facebookAboutPage = "about";
const pageIdMetaProperty = "al:ios:url";

async function getPageIdFromVanityUrl(vanityUrl) {
    try {
        const res = await request({ 
            url: vanityUrl + '\\' + facebookAboutPage,
            headers: {
                'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_13_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36'
            }
        });
        const $ = cheerio.load(res);
        const pageIdMetaPropertyRaw = $(`meta[property="${pageIdMetaProperty}"]`).attr('content');
        const pageId = pageIdMetaPropertyRaw.split('=')[1];
        
        return pageId;
    } catch(err) {
        throw Error(err);
    }
}

async function getPageProfileImage(pageId) {
    console.log(pageId);
    try {
        const res = await request(`https://graph.facebook.com/${pageId}/picture?type=large`);
        console.log(res);
    } catch(err) {
        throw Error(err);
    }
}

module.exports = {
    getPageIdFromVanityUrl: getPageIdFromVanityUrl,
    getPageProfileImage: getPageProfileImage
};