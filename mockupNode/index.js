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
    result: [
      {
        id: "1",
        dni: "12345678A",
        name: "John",
        surname: "Doe",
        profession: "Developer",
        password: "1234",
        email: "email@email.com"
      },
      {
        id: "2",
        dni: "87654321B",
        name: "Jane",
        surname: "Doe",
        profession: "Designer",
        password: "1234",
        email: "jane@email.com"
      },
      {
        id: "3",
        dni: "11111111C",
        name: "Alice",
        surname: "Doe",
        profession: "Manager",
        password: "1234",
        email: "alice@email.com"
      }
    ]
  })
})

app.get("/tasks", (req, res) => {
  res.send({
    result: [
      {
        code: "1",
        category: "Plumbing",
        description: "Repair bathroom pipe",
        start_date: "2020-04-01",
        end_date: "2020-04-15",
        time: 60,
        employee_id: "1",
        priority: 4
      },
      {
        code: "2",
        category: "Electricity",
        description: "Install new office lights",
        start_date: "2020-04-16",
        end_date: "2020-04-30",
        time: 40,
        employee_id: "2",
        priority: 5
      },
      {
        code: "3",
        category: "Cleaning",
        description: "Perform deep cleaning of facilities",
        start_date: "2020-05-01",
        end_date: "2020-05-15",
        time: 80,
        employee_id: "3",
        priority: 6
      },
      {
        code: "4",
        category: "Maintenance",
        description: "Computer equipment maintenance",
        start_date: "2020-05-16",
        end_date: "2020-05-31",
        time: 50,
        employee_id: "4",
        priority: 7
      },
      {
        code: "5",
        category: "Gardening",
        description: "Trim bushes and mow lawn",
        start_date: "2020-06-01",
        end_date: "2020-06-15",
        time: 70,
        employee_id: "5",
        priority: 8
      },
      {
        code: "6",
        category: "Repairs",
        description: "Fix main door",
        start_date: "2020-06-16",
        end_date: "2020-06-30",
        time: 60,
        employee_id: "6",
        priority: 9
      },
      {
        code: "7",
        category: "Logistics",
        description: "Organize warehouse inventory",
        start_date: "2020-07-01",
        end_date: "2020-07-15",
        time: 90,
        employee_id: "7",
        priority: 10
      },
      {
        code: "8",
        category: "Carpentry",
        description: "Build new shelves",
        start_date: "2020-07-16",
        end_date: "2020-07-31",
        time: 80,
        employee_id: "8",
        priority: 11
      },
      {
        code: "9",
        category: "Technical Support",
        description: "Provide technical support to users",
        start_date: "2020-08-01",
        end_date: "2020-08-15",
        time: 100,
        employee_id: "9",
        priority: 12
      },
      {
        code: "10",
        category: "Human Resources",
        description: "Conduct job interviews",
        start_date: "2020-08-16",
        end_date: "2020-08-31",
        time: 120,
        employee_id: "10",
        priority: 13
      }
    ]
  });
});