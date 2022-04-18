import { getAccessToken, serverDomen } from './auth.js';

const accessToken = getAccessToken();

export async function getMe() {
    let me;

    var myHeaders = new Headers();
    myHeaders.append("Authorization", `Bearer ${accessToken}`);

    var requestOptions = {
        method: 'GET',
        headers: myHeaders,
        redirect: 'follow'
    };

    await fetch(`${serverDomen}/auth/me`, requestOptions)
        .then(response => {
            return response.text();
        })
        .then(result => {
            me = JSON.parse(result);
            return JSON.parse(result);
        })
        .catch(error => console.log('auth/me', error));
    return me;
}

export async function getTodos() {
    let TODOS;

    var myHeaders = new Headers();
    myHeaders.append("Authorization", `Bearer ${accessToken}`);
    myHeaders.append("Content-Type", "application/json");

    var requestOptions = {
        method: 'GET',
        headers: myHeaders,
        redirect: 'follow'
    };

    await fetch(`${serverDomen}/todos/`, requestOptions)
        .then(response => response.text())
        .then(result => {
            TODOS = JSON.parse(result);
            return TODOS;
        })
        .catch(error => console.log('error', error));

    return TODOS;
}

export async function removeTodo(id) {
    var myHeaders = new Headers();
    myHeaders.append("Authorization", `Bearer ${accessToken}`);

    var requestOptions = {
        method: 'DELETE',
        headers: myHeaders,
        redirect: 'follow'
    };

    await fetch(`${serverDomen}/todos/${id}`, requestOptions)
        .then(response => response.text())
        .then(result => {
            console.log(result);
        })
        .catch(error => console.log('error', error));
}

export async function addTodo(params = null) {
    let createdTodo;

    if (!(params)) {
        return;
    }

    var myHeaders = new Headers();
    myHeaders.append("Authorization", `Bearer ${accessToken}`);
    myHeaders.append("Content-Type", "application/json");
    // YYYY-MMDDTHH: MM:SS
    // 2022-04-08T12:29:00

    let paramsValidated = {
        "title": params.title,
        "task": params.description
    };

    if (params.priority >= 1 && params.priority <= 4) {
        paramsValidated.priority = params.priority;
    }

    if (params.deadline) {
        paramsValidated.deadline = params.deadline;
    }

    if (params.executorId) {
        paramsValidated.executorId = params.executorId;
    }

    if (params.projectId) {
        paramsValidated.projectId = params.projectId;
    }

    var raw = JSON.stringify(paramsValidated);

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    await fetch(`${serverDomen}/todos/`, requestOptions)
        .then(response => response.text())
        .then(result => {
            createdTodo = JSON.parse(result);
        })
        .catch(error => console.log('error', error));
        
    return createdTodo;
}