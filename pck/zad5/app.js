let express = require('express');

let app = express();

app.get('/', (req, res) => {
    res.send('hello');
});

app.listen(3000, () => {
    console.log("Application started on port 3000!");
});

