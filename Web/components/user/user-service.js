const request = require('../../request');

async function create(email, username, password) {
    try {
        let res = await request.post('/user/', {
            email: email,
            username: username,
            password: password
        });

        if(res.body.status && res.body.status == 409) {
            return { error: "Sorry, that email is already registered! Please try another one." }
        } else  if(res.body.status && res.body.status != 200) {
            return { error: res.body.message }
        } else {
            return {
                user: res.body,
                session: res.res.headers.session
            };
        }
    } catch(err) {
        throw Error(err);
    }
}

async function authenticate(email, password) {
    try {
        let res = await request.post('/user/authenticate/', {
            email: email,
            password: password
        });

        return {
            session: res.res.headers.session
        };
    } catch(err) {
        throw Error(err);
    }
}

async function signout(sessionId) {
    try {
        let res = await request.del(`/session/${sessionId}`)
        if(res.body.status && res.body.status != 200) {
            return { error: 'Internal service error' };
        } else {
            return;
        }
    } catch(err) {
        throw Error(err);
    }
}

module.exports = {
    create: create,
    signout: signout,
    authenticate: authenticate
};