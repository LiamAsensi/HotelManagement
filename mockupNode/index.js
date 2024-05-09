const mongoose = require("mongoose");
const Employee = require("./employee");
const Task = require("./task");
const express = require("express");
const bodyParser = require("body-parser");
let app = express();
app.use(bodyParser.json());

//mongoose.connect("mongodb://127.0.0.1:27017/contacts");

app.listen(8080);

app.get("/employees", (req, res) => {
  res.send({
    error: false,
    result: [
      {
        id: "1",
        dni: "12345678A",
        name: "John",
        surnames: "Doe",
        profession: "Developer",
        password: "1234",
        email: "email@email.com"
      },
      {
        id: "2",
        dni: "87654321B",
        name: "Jane",
        surnames: "Doe",
        profession: "Designer",
        password: "1234",
        email: "jane@email.com"
      },
      {
        id: "3",
        dni: "11111111C",
        name: "Alice",
        surnames: "Doe",
        profession: "Manager",
        password: "1234",
        email: "alice@email.com"
      }
    ]
  })
})

app.get("/tasks", (req, res) => {
  res.send({
    error: false,
    result: [
      {
        code: "1",
        category: "Development",
        description: "Develop a web app",
        start_date: "2020-01-01",
        end_date: "2020-01-31",
        time: 100,
        employee_id: "1",
        priority: 1
      },
      {
        code: "2",
        category: "Design",
        description: "Design a logo",
        start_date: "2020-02-01",
        end_date: "2020-02-28",
        time: 50,
        employee_id: "2",
        priority: 2
      },
      {
        code: "3",
        category: "Management",
        description: "Manage the team",
        start_date: "2020-03-01",
        end_date: "2020-03-31",
        time: 150,
        employee_id: "3",
        priority: 3
      }
    ]
  })
})