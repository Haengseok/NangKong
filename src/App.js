import React from "react";
import Counter from './components/Counter';
import Todos from './components/Todos';
import CounterContainer from "./containers/CounterContainer";
import TodosContainer from "./containers/TodosContainer";
import ManagerPage from './components/ManagerPage';

function App() {
  return (
    <div>
        {/* <CounterContainer />
        <hr />
        <TodosContainer /> */}
        <ManagerPage />
    </div>
  );
};

export default App;
