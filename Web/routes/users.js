const express = require('express');
const userService = require('../components/user/user-service');

const router = express.Router();

router.post('/', async (req, res, next) => {
  try {
    let data = await userService.create(req.body.email, req.body.username, req.body.password);
    if(data.error) {
      res.status(500).send(data.error);
    } else {
      res.cookie("session", data.session);
      res.send(data.user);
    }
  } catch(err) {
    console.log(`Error in user controller [${err}]`);
    res.status(500).send('Uh-oh, something went wrong, please try again!');
  }
});

router.post('/authenticate', async (req, res, next) => {
  try {
    let data = await userService.authenticate(req.body.email, req.body.password);
    console.log(data);
    if(data.error) {
      res.status(500).send(data.error);
    } else {
      res.cookie("session", data.session);
      res.sendStatus(200);
    }
  } catch(err) {
    console.log(`Error in user controller [${err}]`);
    res.status(500).send('Uh-oh, something went wrong, please try again!');
  }
});

router.delete('/session', async (req, res, next) => {
  let sessionId = req.cookies["session"];
  if(sessionId) {
    res.clearCookie("session");
    let data = await userService.signout(sessionId);
    if(data.error) {
      res.status(500).send(data.error);
    } else {
      res.sendStatus(200);
    }
  } else {
    res.sendStatus(200);
  }
});

module.exports = router;
