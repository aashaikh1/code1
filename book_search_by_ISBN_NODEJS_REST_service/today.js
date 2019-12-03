module.exports = function() {
var date = new Date();
var days = ['Sunday', 'Monday', 'Tuesday', 'Wednesday',
'Thursday', 'Friday', 'Saturday'];
return days[ date.getDay() ];
}
