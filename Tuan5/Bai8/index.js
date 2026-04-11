const express = require('express');
const mysql = require('mysql2');
const app = express();
const port = 3000;

const connection = mysql.createConnection({
  host: process.env.DB_HOST || 'db',
  user: process.env.DB_USER || 'root',
  password: process.env.DB_PASSWORD || 'password',
  database: process.env.DB_NAME || 'test'
});

app.get('/', (req, res) => {
  connection.query('SELECT 1 + 1 AS solution', (error, results) => {
    if (error) {
      return res.status(500).send('Error connecting to database: ' + error.message);
    }
    res.send('Node.js successfully connected to MySQL! Result: ' + results[0].solution);
  });
});

app.listen(port, () => {
  console.log(`Server running on port ${port}`);
});
