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
	
	commoditiesPriceHistory = [
		{symbol: 'GLD', x: ['2020-12-01 10:20:20', '2020-12-01 12:20:20', '2020-12-01 14:20:20', '2020-12-02 10:20:20', '2020-12-03 11:20:20', '2020-12-04 15:20:20', '2020-12-05 14:20:20', '2020-12-06 13:20:20', '2020-12-07 10:20:20', '2020-12-08 10:20:20'], y: [2800, 2822, 2760, 2810, 2890, 2900, 2805, 2600, 2845, 2850]},
		{symbol: 'CRO', x: ['2020-12-01 15:20:20', '2020-12-01 16:20:20', '2020-12-01 16:55:20', '2020-12-02 13:20:20', '2020-12-03 16:20:20', '2020-12-04 16:20:20', '2020-12-05 16:20:20', '2020-12-06 11:20:20', '2020-12-07 14:20:20', '2020-12-08 16:20:20'], y: [15, 9, 10, 15, 16, 11, 12, 18, 20, 10]},
		{symbol: 'RIC', x: ['2020-12-01 14:20:20', '2020-12-01 15:20:20', '2020-12-01 15:30:20', '2020-12-02 11:20:20', '2020-12-03 11:20:20', '2020-12-04 11:20:20', '2020-12-05 13:20:20', '2020-12-06 14:20:20', '2020-12-07 15:20:20', '2020-12-08 15:20:20'], y: [7, 6, 11, 13, 10, 14, 13, 7, 8, 11]},
		{symbol: 'SLV', x: ['2020-12-01 13:20:20', '2020-12-01 14:20:20', '2020-12-01 14:50:20', '2020-12-02 14:20:20', '2020-12-03 15:20:20', '2020-12-04 13:20:20', '2020-12-05 14:20:20', '2020-12-06 15:20:20', '2020-12-07 11:20:20', '2020-12-08 14:20:20'], y: [410, 450, 405, 490, 380, 400, 450, 420, 400, 500]},
		{symbol: 'COL', x: ['2020-12-01 12:20:20', '2020-12-01 13:20:20', '2020-12-01 14:20:20', '2020-12-02 15:20:20', '2020-12-03 14:20:20', '2020-12-04 15:20:20', '2020-12-05 15:20:20', '2020-12-06 14:20:20', '2020-12-07 13:20:20', '2020-12-08 13:20:20'], y: [1, 2, 4, 3, 6, 5, 6, 7, 1, 5]},
		{symbol: 'IRN', x: ['2020-12-01 15:20:20', '2020-12-01 16:20:20', '2020-12-01 16:55:20', '2020-12-02 13:20:20', '2020-12-03 16:20:20', '2020-12-04 16:20:20', '2020-12-05 16:20:20', '2020-12-06 11:20:20', '2020-12-07 14:20:20', '2020-12-08 16:20:20'], y: [20, 18, 23, 11, 20, 18, 15, 17, 19, 20]}
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



commodityApi.get('/api/searchcommodity', (req, res) => {
  var query = (req.query['q'] || '').toLowerCase();
  console.log('Search Commodities Request Received! queryString=' + query + ' ' + (new Date().toISOString()));
  if (query) {
    const filteredCommodities = commodities.filter(
      (commodity) => commodity.name.toLowerCase().indexOf(query) != -1);
    return res.status(200).json(filteredCommodities);
  }
  return res.status(200).json(commodities);
});


commodityApi.get('/api/commoditypricehistory', (req, res) => {
  var query = (req.query['q'] || '').toLowerCase();
  console.log('Commodities Price History Request Received! queryString=' + query + ' ' + (new Date().toISOString()));
  if (query) {
    const filteredCommodities = commoditiesPriceHistory.filter(
      (commodity) => commodity.symbol.toLowerCase().indexOf(query) != -1);
	console.log('filtered commodities size: ' + filteredCommodities.length); 
	if(filteredCommodities.length > 0){
		console.log('filtered commodity: ' + filteredCommodities[0].symbol + ' ' + filteredCommodities[0].x + ' ' + filteredCommodities[0].y); 
	    return res.status(200).json(filteredCommodities[0]);
	}
    return res.status(200).json(  [
		{symbol: '000000', x: ['2021-01-01 00:00:00'], y: [0]}
		]);
  }
  return res.status(200).json(  [
		{symbol: '000000', x: ['2021-01-01 00:00:00'], y: [0]}
		]);
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
  this.maintainPriceHistory(commodity);
  //console.log(util.inspect(commodities, {showHidden: false, depth: null}));
  return res.status(200).send({msg: message});
});



  function maintainPriceHistory(commodity){
	  let existingCommodity = commoditiesPriceHistory.find(each => each.symbol === commodity.symbol);
	  if(existingCommodity){
		  existingCommodity.x.push(new Date());
		  existingCommodity.y.push(commodity.currentValue);
	  }else{
		  commoditiesPriceHistory.push({symbol: commodity.symbol, x: [new Date()], y: [commodity.currentValue]});
	  }
  }




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
module.exports.maintainPriceHistory = maintainPriceHistory;


var port = 3000;
commodityApi.listen(port, function(){
	console.log('listening on port: %s', port);
})
