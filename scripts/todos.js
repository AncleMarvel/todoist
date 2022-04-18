import { getTodos } from './routes.js';

let TODOS;

function createTodoElement(todoData) {
    let date;
    todoData.completionTime ? date = todoData.completionTime : date = '';
    const todoMockup =
        `<li class="todo" data-todo-id="${todoData.id}">
            <div class="todo__checkbox-wrapper">
                <label for="todo-id__checkbox" class="todo__checkbox_label">
                    <i class="fa-solid fa-check"></i>
                    <input type="checkbox" class="todo__checkbox_input" id="todo-id__checkbox">
                </label>
            </div>
            <div class="todo__data-wrapper">
                <p class="todo__title">
                    ${todoData.title}
                </p>
                <p class="todo__description">
                    ${todoData.task}
                </p>
                <div class="todo__date">
                    <i class="fa-solid fa-calendar-day"></i>
                    ${date}
                </div>
            </div>
            <div class="todo__settings-wrapper">
                <button class="todo__settings_btn todo__edit-btn" data-todo-id="id">
                    <i class="fa-solid fa-pen"></i>
                </button>

                <button class="todo__settings_btn todo__date-btn" data-todo-id="id">
                    <i class="fa-solid fa-calendar-day"></i>
                </button>
            </div>
        </li>`;
    return todoMockup;
}

function drawTodos() {
    const container = document.querySelector('.todo-container');

    if (TODOS.length < 1) {
        return;
    }

    TODOS.forEach(todo => {
        container.insertAdjacentHTML('beforeend', createTodoElement(todo));
    });
}

export async function todosInit() {
    await getTodos()
        .then((todosList) => {
            TODOS = todosList;
        })
        .catch(error => console.log('error', error));

    drawTodos();
}