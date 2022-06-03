import React from 'react';
import {Signin} from "./page/Signin";
import {Signup} from "./page/Signup";
import {BrowserRouter, Switch, Route } from "react-router-dom";
import {Main} from "./page/Main";
import {Admin} from "./page/Admin";

function App() {

  return (
    <BrowserRouter>
      <Switch>
        <Route exact path={"/"} component={Signin}/>
        <Route path="/signup" component={Signup}/>
        <Route path="/api/user" component={Main}/>
        <Route path="/api/admin" component={Admin}/>
      </Switch>
    </BrowserRouter>
  );
}

export default App;
