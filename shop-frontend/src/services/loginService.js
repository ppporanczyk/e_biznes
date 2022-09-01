import axios from 'axios';

export const login = (email, password) => {
    return axios.post('http://localhost:9000/login', {
        email : email,
        password : password
    });
}

export const getUserByEmail = (email) => {
    return axios.get('http://localhost:9000/user/email/' + email);
}

export const logout = (email) => {
    return axios.post('http://localhost:9000/logout', {
        email : email
    }).then( (res)  => console.log(res.data));
}

export const register = (email, password) => {
    return axios.post(`http://localhost:9000/register`, {
        email : email,
        password : password
    }).then( (res)  => console.log(res.data));
}

export const Oauth = (path) => {
    return axios.post(`http://localhost:9000` + path)
        .then( (res)  => console.log(res.data));
}

export const githubLogin = () => {
    axios.get('http://localhost:9000/authenticate/github').then((data) => {
        window.open(data.data, '_self', 'noopener,noreferrer');
    })
}
export const googleLogin = () => {
    axios.get('http://localhost:9000/authenticate/google').then((data) => {
        window.open(data.data, '_self', 'noopener,noreferrer');
    })
}

export const facebookLogin = () => {
    axios.get('http://localhost:9000/authenticate/facebook').then((data) => {
        window.open(data.data, '_self', 'noopener,noreferrer');
    })
}