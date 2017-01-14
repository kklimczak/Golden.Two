let express = require('express');
let multer = require('multer');

let MULTER_CONF = {
    dest: 'files/'
};

let app = express();
let upload = multer(MULTER_CONF);

app.get('/', (req, res) => {
    res.sendFile(__dirname + '/index.html');
});

app.post('/file/upload', upload.single('xml'), (req, res, next) => {
    res.sendStatus(200);
});

app.listen(3000, () => {
    console.log("Application started on port 3000!");
});

