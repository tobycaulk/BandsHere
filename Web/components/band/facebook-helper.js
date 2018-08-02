const cheerio = require('cheerio');
const request = require('request-promise-native');
const requestJson = require('request-json');
const base64Img = require('base64-img');
const fs = require('fs');

const rjClient = requestJson.createClient('https://graph.facebook.com/');

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
    return new Promise(async (resolve, reject) => {
        try {
            const profileImageRedirectRes = await rjClient.get(`${pageId}/picture?redirect=0&type=large`);
            base64Img.requestBase64(profileImageRedirectRes.body.data.url, (err, res, body) => {
                if(err) {
                    reject(err);
                }
    
                resolve(body.substring(23));
            });
        } catch(err) {
            reject(err);
        }
    });
}

module.exports = {
    getPageIdFromVanityUrl: getPageIdFromVanityUrl,
    getPageProfileImage: getPageProfileImage
};