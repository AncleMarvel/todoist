'use strict';
import { createPopUp } from './pop-up.js';
import { auth } from './auth.js';
import { todosInit } from './todos.js';

function pasteFromClipboardWithoutFormatting() {
    function handlePaste(e) {
        var clipboardData, pastedData;
    
        // Stop data actually being pasted into div
        e.stopPropagation();
        e.preventDefault();
    
        // Get pasted data via clipboard API
        clipboardData = e.clipboardData || window.clipboardData;
        pastedData = clipboardData.getData('Text');
    
        // Do whatever with pasteddata
        e.target.innerText = pastedData;
    }
    
    document.querySelectorAll("div[contenteditable]").forEach(el => {
        el.addEventListener("paste", handlePaste);
    });
}

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
    pasteFromClipboardWithoutFormatting();
    (async function () {
        await auth();
        await todosInit();
    })();
});