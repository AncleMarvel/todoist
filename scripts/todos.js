import { getTodos, removeTodo, addTodo } from './routes.js';

let TODOS;

function createTodoElement(todoData) {
    let date;
    todoData.completionTime ? date = todoData.completionTime : date = 'No deadline';

    return `<li class="todo" data-todo-id="${todoData.id}">
            <div class="todo__checkbox-wrapper">
                <label for="${todoData.id}" class="todo__checkbox_label">
                    <input type="checkbox" class="todo__checkbox_input" id="${todoData.id}">
                    <i class="fa-solid fa-check"></i>
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

}

function pushTodoElementToContainer(todoElement) {
    const container = document.querySelector('.todo-container');
    container.insertAdjacentHTML('beforeend', todoElement);
    return container.querySelectorAll('.todo')[container.querySelectorAll('.todo').length - 1];
}

function drawTodos() {
    if (TODOS.length < 1) {
        return;
    }

    TODOS.forEach(todo => {
        pushTodoElementToContainer(createTodoElement(todo));
    });
}

function startRemovingTodo(todo) {
    todo.classList.add('todo__removed');
}

function endRemovingTodo(todo) {
    todo.remove();
}

function getParamsForTodoAdding() {
    const titleInput = document.querySelector("#todo-title-input");
    const descriptionInput = document.querySelector("#todo-description-input");

    return {
        'title': titleInput.innerText,
        'description': descriptionInput.innerText
    }
}

function removeTodoHandler(id) {
    const todo = document.querySelector(`.todo[data-todo-id="${id}"]`);
    startRemovingTodo(todo);
    removeTodo(id).then(() => {
        setTimeout(() => {
            endRemovingTodo(todo);
        }, 150);
    });
}

function clearAndHideAddTodoPopUp() {
    const popUp = document.querySelector('.add-todo__wrapper');
    const titleInput = popUp.querySelector('#todo-title-input');
    const descriptionInput = popUp.querySelector('#todo-description-input');
    titleInput.innerText = '';
    descriptionInput.innerText = '';
    popUp.classList.add('none');
}

function addTodoHandler() {
    const params = getParamsForTodoAdding();
    addTodo(params).then((createdTodo) => {
        updateTodoList(createdTodo);
    }).then(() => {
        clearAndHideAddTodoPopUp();
    });
}

function updateTodoList(newTodo) {
    TODOS.push(newTodo);
    const newTodoElement = createTodoElement(TODOS[TODOS.length - 1]);
    const insertedElement = pushTodoElementToContainer(newTodoElement);

    insertedElement.querySelector('.todo__checkbox_input').addEventListener('change', (ev) => {
        removeTodoHandler(ev.target.id);
    });
}

function todoEventsInit() {
    const removeTodoBtnsList = document.querySelectorAll('.todo__checkbox_input');
    removeTodoBtnsList.forEach(btn => {
        btn.addEventListener('change', (ev) => {
            removeTodoHandler(ev.target.id);
        });
    });

    const addTodoBtn = document.querySelector('.add-todo__footer-btn_add');
    addTodoBtn.addEventListener('click', addTodoHandler);
}

export async function todosInit() {
    await getTodos()
        .then((todosList) => {
            TODOS = todosList;
        })
        .catch(error => console.log('error', error));

    drawTodos();

    todoEventsInit();
}