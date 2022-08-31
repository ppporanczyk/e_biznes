import './App.css';
import Orders from "./components/Orders";
import User from "./components/User";
import Category from "./components/Category";
import {setHeaders} from "./services/productService";
import UserContext from "./context/UserContext";
import React, { useState } from 'react';


function App() {

    setHeaders();
    const [userId, setUserId] = useState(0);
    const [userEmail, setUserEmail] = useState(0);
    const [newOrder, setNewOrder] = useState(false);
    const toggleUser = (email, Id) => {
        setUserEmail(email);
        setUserId(Id);
    };
    const toggleOrder = () => {
        if (newOrder)
            setNewOrder(false);
        else
            setNewOrder(true);
    }
    const userSettings = { userEmail, userId, newOrder, toggleOrder, toggleUser};


    return (
        <UserContext.Provider value={userSettings} id="block_container">
          <header id="header">
              <section> Shop </section>
          </header>

          <section id="main-body-container">
              <article id="main-article">
                  <User/>
              </article>
              <article >
                  <div id="blockLeft"><Category /></div>
                  <div id="blockRight"><Orders /></div>
              </article>
          </section>

          <footer id="footer">
          </footer>
        </UserContext.Provider>
  );
}

export default App;
