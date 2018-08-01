const cheerio = require('cheerio');
const request = require('request-promise-native');

const facebookAboutPage = "about";
const pageIdMetaProperty = "al:ios:url";

async function getPageIdFromVanityUrl(vanityUrl) {
    try {
        const res = await request(vanityUrl + facebookAboutPage);
        const $ = cheerio.load(res);
        console.log($(`meta[property="${pageIdMetaProperty}"]`).attribs['content']);
        const pageIdMetaPropertyRaw = $(`meta[property="${pageIdMetaProperty}"]`).attr('content');
        const pageId = pageIdMetaPropertyRaw.split('=')[1];
        return pageId;
    } catch(err) {
        console.log(err);
        return undefined;
    }
}

module.exports = {
    getPageIdFromVanityUrl: getPageIdFromVanityUrl
};