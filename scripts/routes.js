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