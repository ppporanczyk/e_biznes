import React from "react";
import {
    BrowserRouter as Router,
    Switch,
    Route
} from "react-router-dom";

import Category from './components/Category';
import Cart from "./components/cart";
import Login from "./components/login";
import NotFound from './components/NotFound';


const AppRoutes = () => {
    return (
        <Router>
            <Switch>
                <Route exact path="/" component={Category} />
                <Route path="/cart" component={Cart} />
                <Route path="/login" component={Login}/>
                <Route path="*" component={NotFound} />
            </Switch>
        </Router>
    );
}

export default AppRoutes;