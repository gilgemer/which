/**
 * Created by danil on 4/15/17.
 */
var express = require('express')
    , router = express.Router()

router.get('/pipikaki', function(req, res) {
    picture = {
        description : 'test',
        url : 'http://kingofwallpapers.com/picture/picture-008.jpg'
    }
    res.render('pictures/picture', {picture : picture})
})

module.exports = router