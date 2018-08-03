const express = require('express');
const bandService = require('../components/band/band-service');
const userService = require('../components/user/user-service');

const router = express.Router();

router.get('/', (req, res, next) => {
  res.render('index', { });
});

router.get('/:username', async (req, res, next) => {
  let username = req.params.username;
  let band = await bandService.get(username);
  let isAuthenticated = await userService.isValidSession(req.cookies["session"]);
  if(!isAuthenticated) {
    res.clearCookie("session");
  }
  res.render('band', {
    band: band,
    authenticated: isAuthenticated
  });
});

router.get('/band/create', async (req, res, next) => {
  let isAuthenticated = await userService.isValidSession(req.cookies["session"]);
  if(!isAuthenticated) {
    res.clearCookie("session");
  }
  res.render('create-band', { 
    authenticated: isAuthenticated,
    dontDisplayAccountLinks: true
  });
});

router.post('/band/create', async (req, res, next) => {
  let data = req.body;
  try {
    let bandCreateRes = await bandService.create(data, req.cookies["session"]);
    res.send(bandCreateRes);
  } catch(err) {
    console.log(err);
  } 

  res.status(200);
});

router.get('/band/:band/followers', async (req, res, next) => {
  try {
    let followerCount = await bandService.getFollowerCount(req.params["band"]);
    res.send({
      followerCount: followerCount
    });
  } catch(err) {
    console.log(err);
  }
});

module.exports = router;