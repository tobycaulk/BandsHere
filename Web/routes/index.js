const express = require('express');
const bandService = require('../components/band/band-service');

const router = express.Router();

router.get('/', (req, res, next) => {
  res.render('index', { });
});

router.get('/:username', async (req, res, next) => {
  let username = req.params.username;
  let band = await bandService.get(username);
  res.render('band', {
    band: band,
    authenticated: req.cookies["session"] != null
  });
});


router.get('/band/create', (req, res, next) => {
  res.render('create-band', { 
    authenticated: req.cookies["session"] != null,
    dontDisplayAccountLinks: true
  });
});

router.post('/band/create', async (req, res, next) => {
  let data = req.body;
  try {
    let res = bandService.create(data);
  } catch(err) {
    console.log(err);
  } 

  res.status(200);
});

module.exports = router;
