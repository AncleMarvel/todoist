import { getMe } from './routes.js';

export const serverDomen = 'http://localhost:8080';
const mainDomen = 'http://localhost:5500';

let me;

function setAccessToken(token) {
    localStorage.setItem('accessToken', token);
}

function setRefreshToken(token) {
    localStorage.setItem('refreshToken', token);
}

export function getAccessToken() {
    return localStorage.getItem('accessToken');
}

function getRefreshToken() {
    return localStorage.getItem('refreshToken');
}

function getJwtTokensFromWindowLocation() {
    const urlString = window.location;
    const url = new URL(urlString);
    if (url.searchParams.get("accessToken") && url.searchParams.get("refreshToken")) {
        return {
            accessToken: url.searchParams.get("accessToken"),
            refreshToken: url.searchParams.get("refreshToken")
        }
    } else {
        return false;
    }
}

async function tryToRefreshAccessToken() {
    let tokens;
    var myHeaders = new Headers();
    myHeaders.append("Content-Type", "application/json");

    const refreshToken = getRefreshToken();
    var raw = JSON.stringify({
        "refreshToken": refreshToken
    });

    var requestOptions = {
        method: 'POST',
        headers: myHeaders,
        body: raw,
        redirect: 'follow'
    };

    await fetch(`${serverDomen}/auth/refresh`, requestOptions)
        .then(response => response.text())
        .then(result => {
            tokens = JSON.parse(result);
            return tokens;
        })
        .catch(error => console.log('error', error));

    return tokens;
}

async function canIGetMe() {
    let foo;
    await getMe().then(result => {

        if (!(result)) {
            foo = false;
            return;
        }

        if (result.id) {
            me = result;
            foo = true;
        } else {
            foo = false;
        }
    });
    return foo;
}

function ifTokensExistInLS() {
    const accessToken = getAccessToken();
    const refreshToken = getRefreshToken();

    if (accessToken && refreshToken) return true;
}

function ifTokensExistInWindowLocation() {
    if (getJwtTokensFromWindowLocation()) return true;
}

async function ifAccessTokenDoesntExpire() {
    let result;

    await canIGetMe().then((value) => {
        result = value;
    });

    if (result) {
        return true;
    } else {
        return false;
    }
}

function redirectToAuth() {
    const redirectUrl = mainDomen;
    const url = `${serverDomen}/login/oauth2/google?redirect_uri=${redirectUrl}`;
    window.location.replace(url);
}

function removeTokensFromLS() {
    localStorage.removeItem('accessToken');
    localStorage.removeItem('refreshToken');
}

function clearWindowLocation() {
    window.location.replace(mainDomen);
}

function changeAccountIcon() {
    const accountBtn = document.querySelector('#header__account_btn');
    const accountImg = document.querySelector('.header__account-img');

    accountBtn.dataset.auth = true;

    accountImg.src = me.userpic;
    accountImg.alt = me.email;
}

export async function auth() {
    if (ifTokensExistInLS()) {
        let accessTokenDoesntExpire;

        await ifAccessTokenDoesntExpire().then((value) => {
            accessTokenDoesntExpire = value;
        });

        if (accessTokenDoesntExpire) {
            if (ifTokensExistInWindowLocation()) {
                clearWindowLocation();
            }
            changeAccountIcon();
            return true;
        } else {
            let tryingRefreshAccessToken;

            await tryToRefreshAccessToken().then((res) => {
                if (res.accessToken) {
                    tryingRefreshAccessToken = res.accessToken;
                    return true;
                } else {
                    removeTokensFromLS();
                    redirectToAuth();
                }
            });

            setAccessToken(tryingRefreshAccessToken);
            await canIGetMe();
            changeAccountIcon();
            clearWindowLocation();
            return true;
        }
    } else if (ifTokensExistInWindowLocation()) {
        const { accessToken, refreshToken } = getJwtTokensFromWindowLocation();
        setAccessToken(accessToken);
        setRefreshToken(refreshToken);
        clearWindowLocation();
    } else {
        redirectToAuth()
    }
}