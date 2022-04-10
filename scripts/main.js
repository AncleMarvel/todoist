'use strict';
import { createPopUp } from './pop-up.js';

const addTodoPopUpConfig = {
    openInitiatorSelector: '.add-todo__pop-up_open',
    cancelInitiatorSelector: '.add-todo__footer-btn_cancel',
    cancelByClickOutside: true,
    popUpWrapperSelector: '.add-todo__wrapper',
    classesManaging: [
        {
            elementSelector: '.add-todo__wrapper',
            classListForToggling: ['none']
        },
        {
            elementSelector: 'body',
            classListForToggling: ['noscrool']
        }
    ],

};

const addTodoPopUp = createPopUp(addTodoPopUpConfig);

document.addEventListener('DOMContentLoaded', () => {
});