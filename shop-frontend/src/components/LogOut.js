import React, {useContext, useEffect} from 'react';
import UserContext from "../context/UserContext";

const LogOutComponent = () => {

    const userContext = useContext(UserContext);

    useEffect(() => {
    }, [userContext]);

    const handleLogOut = () => {
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