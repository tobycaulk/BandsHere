const express = require('express');
const bandService = require('../components/band/band-service');

const router = express.Router();

router.get('/', (req, res, next) => {
  res.render('index', { });
});

router.get('/:username', async (req, res, next) => {
  let username = req.params.username;
  let band = await bandService.get(username);
  console.log(req.cookies);
  res.render('band', {
    band: band,
    authenticated: req.cookies["session"] != null
  });
});

module.exports = router;
