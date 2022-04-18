import { getAccessToken, serverDomen } from './auth.js';

export async function getMe() {
    const accessToken = getAccessToken();
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
