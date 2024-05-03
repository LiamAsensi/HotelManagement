const mongoose = require('mongoose');

let employeeSchema = new mongoose.Schema({
    id: {
        type: String,
        required: true,
        unique: true
    },
    dni: {
        type: String,
        required: true,
        unique: true
    },
    name: {
        type: String,
        required: true,
        minlength: 1,
        trim: true
    },
    surname: {
        type: String,
        required: true,
        minlength: 1,
        trim: true
    },
    profession: {
        type: String,
        required: true,
        minlength: 1,
        trim: true
    },
    password: {
        type: String,
        required: true,
        minlength: 1,
        trim: true
    },
    email: {
        type: String,
        required: true,
        minlength: 1,
        trim: true
    },
});