import React, {useContext, useEffect} from 'react';
import {logout} from "../services/loginService";
import UserContext from "../context/UserContext";

const LogOutComponent = () => {

    const userContext = useContext(UserContext);

    useEffect(() => {
    }, [userContext]);

    const handleLogOut = () => {
        // logout(userContext.email).then((res) => {
        //     userContext.toggleUser('', 0);
        // })
        userContext.toggleUser('', 0);
        window.location.reload();
    }

    return (
        <div >
            <button onClick={handleLogOut}>Wyloguj się</button>
        </div>
    )
};

export default LogOutComponent;