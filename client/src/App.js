import React from 'react';
import {Signin} from "./page/Signin";
import {Signup} from "./page/Signup";
import {BrowserRouter, Switch, Route } from "react-router-dom";

function App() {

  return (
    <BrowserRouter>
      <Switch>
        <Route exact path={"/"} component={Signin} ></Route>
        <Route path="/signup" component={Signup}/>
      </Switch>
    </BrowserRouter>
  );
}

export default App;
