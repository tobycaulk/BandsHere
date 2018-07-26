const request = require('../../request');

async function create(email, username, password) {
    try {
        let res = await request.post('/user/', {
            email: email,
            username: username,
            password: password
        });
        return {
            user: res.body,
            session: res.res.headers.session
        };
    } catch(err) {
        console.log(err);
        throw Error(err);
    }
}

module.exports = {
    create: create
};