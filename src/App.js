import React from "react";
import Counter from './components/Counter';
import Todos from './components/Todos';
import CounterContainer from "./containers/CounterContainer";
import TodosContainer from "./containers/TodosContainer";
import ManagerPage from './components/ManagerPage';
import Navbar from "./newcomponent/Navbar";
import {
  BrowserRouter as Router, Switch,
  Route } from "react-router-dom";

import Home from "./pages/Home";
import Reports from "./pages/Reports";
import Products from "./pages/Products";
import User from "./User";
import ErrorBoundary from "./ErrorBoundary";


function App() {
  
  const user = {
    id: 1,
    username: 'velopert'
  };
  
  return (
    // <div>
    //     <CounterContainer />
    //     <hr />
    //     <TodosContainer /> */}
    //     <ManagerPage />
    // </div>

    // <>
    //   <Router>
    //     <Navbar />
    //     <Switch>
    //       <Route path='/' exact component={Home} />
    //       <Route path='/user' component={Reports} />
    //       <Route path='/esl' component={Products} />
    //     </Switch>
    //   </Router>
    // </>

    <ErrorBoundary>
      <User />
    </ErrorBoundary>
    // <User />
  );
};

export default App;
