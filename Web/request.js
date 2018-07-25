const request = require('request-json');
const config = require('./config');

module.exports = request.createClient(config.request.baseUrl);