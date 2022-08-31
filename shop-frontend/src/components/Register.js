import React, { useState } from 'react';
import {register} from "../services/loginService";

const RegisterComponent = () => {
    const [error, setError] = React.useState('');
    const [values, setValues] = useState({
        email: '',
        password: ''
    });

    const handleSubmit = (e) => {
        e.preventDefault();

        register(values.email, values.password).then((res) => {
            if(res.status!==200)
            {
                setError('Nieprawidłowe dane');
            }
        }).catch(err => setError('Nieprawidłowe dane'))
    };

    const handleChange = name => e => {
        setValues({ ...values, [name]: e.target.value });
    };

    const registerForm = () => {
        return (
            <form onSubmit={handleSubmit}>
                {error}
                <h2>Zajerestruj się</h2>
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
                    <button className="btn btn-primary">Sign Up</button>
                </div>
            </form>
        );
    };

    return <React.Fragment>
        {registerForm()}
    </React.Fragment>;
};

export default RegisterComponent;