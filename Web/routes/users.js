const express = require('express');
const userService = require('../components/user/user-service');

const router = express.Router();

router.get('/', (req, res, next) => {
  res.send('respond with a resource');
});

router.post('/', async (req, res, next) => {
  try {
    let data = await userService.create(req.body.email, req.body.username, req.body.password);
    if(data.error) {
      res.status(500).send(data.error);
    } else {
      res.cookie("session", data.session)
      res.send(data.user);
    }
  } catch(err) {
    console.log("Error in users controller [" + err + "]");
    res.status(500);
  }
});

router.delete('/session', async (req, res, next) => {
  let sessionId = req.cookies["session"];
  if(sessionId) {
    res.clearCookie("session");
  } else {
    res.status(200);
  }
});

module.exports = router;
