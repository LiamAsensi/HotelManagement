const mongoose = require('mongoose');

let taskSchema = new mongoose.Schema({
    code: {
        type: String,
        required: true,
        unique: true
    },
    category: {
        type: String,
        required: true,
        minlength: 1,
        trim: true
    },
    description: {
        type: String,
        required: true,
        minlength: 1,
        trim: true
    },
    start_date: {
        type: Date,
        required: true
    },
    end_date: {
        type: Date,
        required: true
    },
    time: {
        type: Number,
        required: true
    },
    employee_id: {
        type: String,
        required: true
    },
    priority: {
        type: Number,
        required: true
    },
});