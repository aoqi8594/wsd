const mysql = require("mysql-pro");
const config = require("../config");
const db = new mysql({
  mysql: {
    host: config.DB_HOST,
    port: config.DB_PORT,
    user: config.DB_USER,
    password: config.DB_PASS,
    database: config.DB_NAME,
  },
});
module.exports = db;
