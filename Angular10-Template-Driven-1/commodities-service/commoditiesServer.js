var express = require('express')
var commodityApi = express();
var util = require('util');
var bodyParser = require('body-parser');

//json
var jsonParser = bodyParser.json();
//application/x-www-form-urlencoded
var formUrlencodedParser = bodyParser.urlencoded({ extended: false });

	commodities = [
		{name: 'Gold', symbol: 'GLD', currentValue: 2850, previousValue: 2845, market: 'ABX', preferred: false},
		{name: 'Crude Oil', symbol: 'CRO', currentValue: 10, previousValue: 20, market: 'ABX', preferred: false},
		{name: 'Rice', symbol: 'RIC', currentValue: 11, previousValue: 8, market: 'ABX', preferred: false},
		{name: 'Silver', symbol: 'SLV', currentValue: 500, previousValue: 400, market: 'ABX', preferred: false},
		{name: 'Coal', symbol: 'COL', currentValue: 5, previousValue: 1, market: 'ABX', preferred: false},
		{name: 'Iron', symbol: 'IRN', currentValue: 20, previousValue: 19, market: 'ABX', preferred: false}
	];  

commodityApi.use(function (req, res, next) {
    res.setHeader('Access-Control-Allow-Origin', 'http://localhost:4200');
    res.setHeader('Access-Control-Allow-Methods', 'GET, POST, OPTIONS, PUT, PATCH, DELETE');
    res.setHeader('Access-Control-Allow-Headers', 'X-Requested-With,content-type');
    res.setHeader('Access-Control-Allow-Credentials', true);
    next();
});

commodityApi.get('/api/commodity', (req, res) => {
  console.log('Get Commodities Request Received! ' + (new Date().toISOString()))
  return res.status(200).json(commodities);
});

commodityApi.post('/api/commodity', jsonParser, (req, res) => {
  console.log('Create/Update Commodity request received! ' + (new Date().toISOString()));	
  //console.log(util.inspect(req, {showHidden: false, depth: null}));
  let commodity = req.body;
  console.log(commodity);
  console.log(util.inspect(commodity, {showHidden: false, depth: null}));
  let existingCommodity = commodities.find(each => each.symbol === commodity.symbol);
	if (existingCommodity) {
		if(parseFloat(existingCommodity.currentValue) != parseFloat(commodity.currentValue)){
			commodity.previousValue = existingCommodity.currentValue;//if new currentValue changed then move existing.currentValue to previousValue
		}else{
			commodity.previousValue = existingCommodity.previousValue;//input form do not have previous value on it
		}
		commodity.preferred = existingCommodity.preferred;
		this.removeCommodity(existingCommodity);
		message = 'Existing Commodity with symbol ' + commodity.symbol + ' successfully updated';
	}else{
		commodity.previousValue = commodity.currentValue;
		message = 'New Commodity with symbol ' + commodity.symbol + ' successfully created';
	}
  commodities.push(commodity);
  //console.log(util.inspect(commodities, {showHidden: false, depth: null}));
  return res.status(200).send({msg: message});
});

commodityApi.post('/api/commoditypreferred', jsonParser, (req, res) => {
  console.log('Commodity preferred Request Received! ' + (new Date().toISOString()))	
  let commodity = req.body;
  let commoditySymbol = commodity.symbol;
  let existingCommodity = commodities.find(each => each.symbol === commoditySymbol);
  let message = "";
  if (existingCommodity) {
    existingCommodity.preferred = !existingCommodity.preferred;
    message = 'Commodity with symbol ' + commoditySymbol + ' is changed to preferred=' + existingCommodity.preferred;
    return res.status(200).send({msg: message});
  }else{
	message = 'Commodity with symbol ' + commoditySymbol + ' does not exist';
  }
  return res.status(400).send({msg: message});
});


commodityApi.post('/api/commoditydelete', jsonParser, (req, res) => {
  console.log('Commodity delete Request Received! ' + (new Date().toISOString()))	
  let commodity = req.body;
  let commoditySymbol = commodity.symbol;
  let existingCommodity = commodities.find(each => each.symbol === commoditySymbol);
  let message = "";
  if (existingCommodity) {
	this.removeCommodity(existingCommodity);
    message = 'Commodity with symbol ' + commoditySymbol + ' has been deleted.';
    return res.status(200).send({msg: message});
  }else{
	message = 'Commodity with symbol ' + commoditySymbol + ' does not exist';
  }
  return res.status(400).send({msg: message});
});


  function removeCommodity(commodity){
	  var index = commodities.indexOf(commodity);
	  if (index > -1) {
		commodities.splice(index, 1);
	  }
  }


module.exports.removeCommodity = removeCommodity;

var port = 3000;
commodityApi.listen(port, function(){
	console.log('listening on port: %s', port);
})
