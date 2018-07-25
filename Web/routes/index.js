const express = require('express');
const bandService = require('../components/band/band-service');

const router = express.Router();

router.get('/', (req, res, next) => {
  res.render('index', { });
});

router.get('/:username', async (req, res, next) => {
  let username = req.params.username;
  let band = await bandService.get(username);
  console.log(band);
  res.render('band', band);
});

module.exports = router;
