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

router.post('/followBand/', async (req, res, next) => {
  try {
    let sessionId = req.cookies["session"];
    if(sessionId) {
      let data = await userService.followBand(sessionId, req.body.username);
      if(data.error) {
        res.status(500).send(data.error);
      } else {
        res.send(data);
      }
    } else {
      res.status(500).send('Please login or create an account to follow this band!');
    }
  } catch(err) {
    console.log(`Error in user controller [${err}]`);
    res.status(500).send('Uh-oh, something went wrong, please try again!');
  }
});

router.get('/followBand/', async (req, res, next) => {
  try {
    let sessionId = req.cookies["session"];
    if(sessionId) {
      let data = await userService.isFollowingBand(sessionId, req.query.username);
      if(data.error) {
        res.status(500).send(data.error);
      } else {
        res.send(data);
      }
    }
  } catch(err) {
    console.log(`Error in user controller [${err}]`);
    res.status(500).send('Uh-oh, something went wrong, please try again!');
  }
});

module.exports = router;
