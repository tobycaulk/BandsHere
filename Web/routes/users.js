const express = require('express');
const userService = require('../components/user/user-service');

const router = express.Router();

router.get('/', (req, res, next) => {
  res.send('respond with a resource');
});

router.post('/', async (req, res, next) => {
  try {
    let data = await userService.create(req.body.email, req.body.username, req.body.password);
    res.cookie("session", data.session)
    res.send(data.user);
  } catch(err) {
    console.log(err);
    res.status(500);
  }
});

module.exports = router;
