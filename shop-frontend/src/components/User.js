import React, {useContext, useEffect, useState} from 'react';
import Register from "./Register";
import Login from "./Login";
import LogOut from "./LogOut";
import UserContext from "../context/UserContext";

const UserComponent = () => {

    const userContext = useContext(UserContext);
    const [registerForm, setRegisterForm] = React.useState(false);
    const [loginForm, setLoginForm] = React.useState(false);

    useEffect(() => {
    }, [userContext]);

    const handleRegister = () => {
        setRegisterForm(true);
        setLoginForm(false);
    }

    const handleLogin = () => {
        setLoginForm(true);
        setRegisterForm(false);
    }

    const handleClose = () => {
        setLoginForm(false);
        setRegisterForm(false);
    }

    const showForm = () => {
        if(loginForm){
            return(<div><Login/><button onClick={handleClose}>zamknij</button></div>)
        }else if(registerForm){
            return(<div><Register/><button onClick={handleClose}>zamknij</button></div>)
        }
    }

    const ifLogIn = () => {
        if(userContext.userId > 0 ){
            return(
                <React.Fragment>
                    <LogOut/>
                </React.Fragment>
            )
        }
        else{
            return (
                <React.Fragment>
                    <div className="navbar">
                        <button onClick={handleRegister}>zarejestruj się</button>
                        <button onClick={handleLogin}>zaloguj się</button>
                    </div>
                    {showForm()}
                </React.Fragment>
            )
        }
    }

    return <React.Fragment>
        {ifLogIn()}
    </React.Fragment>;
};

export default UserComponent;