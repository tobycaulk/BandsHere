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
        let res = await request.del(`/user/session/${sessionId}`);
        if(res.body.status && res.body.status != 200) {
            return { error: 'Uh-oh, something went wrong, please try again!' };
        } else {
            return;
        }
    } catch(err) {
        throw Error(err);
    }
}

async function followBand(sessionId, bandUsername) {
    try {
        request.headers["session"] = sessionId;
        let res = await request.patch(`/user/${sessionId}/follow/${bandUsername}`);
        if(res.body.status && res.body.status != 200) {
            return { error: 'Uh-oh, something went wrong, please try again!' };
        } else {
            return res.body;
        }
    } catch(err) {
        throw Error(err);
    }
}

async function isFollowingBand(sessionId, bandUsername) {
    try {
        request.headers["session"] = sessionId;
        let res = await request.get(`/user/${sessionId}/follow/${bandUsername}`);
        if(res.body.status && res.body.status != 200) {
            return { error: 'Uh-oh, something went wrong, please try again!' };
        } else {
            return res.body;
        }
    } catch(err) {
        throw Error(err);
    }
}

async function isValidSession(sessionId) {
    try {
        request.headers["session"] = sessionId;
        let res = await request.get('/user/session/valid');
        if(res.body.status && res.body.status != 200) {
            return { error: 'Uh-oh, something went wrong, please try again!' };
        } else {
            return res.body;
        }
    } catch(err) {
        throw Error(err);
    }
}

module.exports = {
    create: create,
    signout: signout,
    authenticate: authenticate,
    followBand: followBand,
    isFollowingBand: isFollowingBand,
    isValidSession: isValidSession
};