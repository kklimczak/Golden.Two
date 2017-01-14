let express = require('express');

let app = express();

app.get('/', (req, res) => {
    res.sendFile(__dirname + '/index.html');
});

app.listen(3000, () => {
    console.log("Application started on port 3000!");
});

