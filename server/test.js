var express = require('express');
var app = express();

app.use(express.static('public'))

app.get('/', function (req, res) {
    console.log("got a GET request for root_directory");
    res.send("root!");
});

app.get('/user_list', function (req, res) {
    console.log("got a GET request for user_list");
    res.send("user list");
});

var server = app.listen(8081, function () {
    var host = server.address().address;
    var port = server.address().port;

    console.log("Example app listening at http://%s:%s", host, port)
});