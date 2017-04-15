/**
 * Created by danil on 4/15/17.
 */
/**
 * Created by danil on 4/15/17.
 */
var express = require('express')
    , router = express.Router()

router.get('/:id', function(req, res) {
    picture_1 = {
        description : 'winter',
        url : 'http://az616578.vo.msecnd.net/files/2015/12/04/635848557150633136-120303261_winter.jpg'
    }

    picture_2 = {
        description : 'summer',
        url : 'http://weknowyourdreams.com/images/summer/summer-09.jpg'
    }

    question = 'Vacation with the loved one is better at???'

    which = {
        question : question,
        picture_1 : picture_1,
        picture_2 : picture_2
    }
    res.render('which/which', {which : which})
})

module.exports = router