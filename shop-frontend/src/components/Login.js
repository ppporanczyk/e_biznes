import React, {useContext, useEffect, useState} from 'react';
import {facebookLogin, getUserByEmail, googleLogin, login} from "../services/loginService";
import UserContext from "../context/UserContext";


const LoginComponent = () => {
    const [getUser, setGetUser] = useState(false);
    const userContext = useContext(UserContext);
    const [error, setError] = React.useState('');
    const [values, setValues] = useState({
        email: '',
        password: '',
    });

    useEffect(() => {
        if(getUser){
            getUserByEmail(values.email).then(res =>{
                userContext.toggleUser(values.email, res.data.id);
            });
            setGetUser(false);
        }
    }, [userContext, getUser]);

    const handleSubmit = (e) => {
        e.preventDefault();

        login(values.email, values.password).then((res) => {
            if(res.status===200)
            {
                setGetUser(true);
            }
            else {
                setError('Nieprawidłowe dane logowania');
            }
        }).catch(err => setError('Nieprawidłowe dane logowania'))

    };

    const handleChange = name => e => {
        setValues({ ...values, [name]: e.target.value });
    };

    const handleGithub = () => {
        window.location.assign("http://localhost:9000/authenticate/github")
        setGetUser(true);
    }

    const loginForm = () => {
        return (
            <div className="payment-modal">
                <button onClick={handleGithub}>Github</button>
                <button onClick={googleLogin}>Google</button>
                <button onClick={facebookLogin}>Facebook</button>
            <form onSubmit={handleSubmit}>
                {error}
                <h2>Zaloguj się</h2>
                <div className="form-group">
                    <input
                        value={values.email}
                        onChange={handleChange('email')}
                        type="email"
                        className="form-control"
                        placeholder="Email"
                    />
                </div>
                <div className="form-group">
                    <input
                        value={values.password}
                        onChange={handleChange('password')}
                        type="password"
                        className="form-control"
                        placeholder="Password"
                    />
                </div>
                <div>
                    <button className="btn btn-primary">Sign In</button>
                </div>
            </form>
            </div>
        );
    };

    return <React.Fragment>
        {loginForm()}
    </React.Fragment>;
};

export default LoginComponent;