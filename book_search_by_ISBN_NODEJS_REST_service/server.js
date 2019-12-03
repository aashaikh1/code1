var port = 3000;
var today = require('./today');
var request = require('request');
var express = require('express');
var parse = require('xml2js').parseString;
var app = express();

app.get('/api/today', function(req, res) {
	var body = "The day of the week is "+ today()+ ".";
	res.type('text/plain');
	res.set('Content-Length', Buffer.byteLength(body));
	res.status(200).send(body);
});



app.get('/api/book_search_by_isbn/:isbn_number', function(req, res) {
	var options = {
		method: 'GET',
		uri: 'https://www.googleapis.com/books/v1/volumes?q=isbn:' + req.params.isbn_number,
		headers: {
			'User-agent': 'bookRequest/1.0'
		}
	};
	
	var callback = function(error, response, body) {
		if (error) {
			res.status(500).send(error.message);
		}
		
		var jsonObject = JSON.parse(body);
		var message;
		if(jsonObject.totalItems > 0){
			var message = 'ISBN: ' + req.params.isbn_number + '\r\nBook Name: ' + jsonObject.items[0].volumeInfo.title +  
					'\r\nBook Author: ' + jsonObject.items[0].volumeInfo.authors[0];
		}else{
			
			var message = 'ISBN: ' + req.params.isbn_number + ' not found';
		}
		
		res.type('text/plain');
		res.set('Content-Length', Buffer.byteLength(message));
		res.status(response.statusCode).send(message);
	};

	request(options, callback);
});

app.get('/api/getbook_war_and_peace', function(req, res) {
	var options = {
		method: 'GET',
		uri: 'https://www.googleapis.com/books/v1/volumes?q=isbn:1788886526',
		headers: {
			'User-agent': 'bookRequest/1.0'
		}
	};
	
	var callback = function(error, response, body) {
		res.type('text/plain');
		res.status(response.statusCode).send(body);
	};

	request(options, callback);
});

app.listen(port, function() {
	console.log('Listening on port %s.', port);
});