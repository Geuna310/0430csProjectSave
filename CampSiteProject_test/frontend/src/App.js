/*import logo from './logo.svg';
import './App.css';

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
    </div>
  );
}

export default App;
*/

/*import React, { useEffect, useState } from 'react';
import { getHello } from './api/hello';
import './styles/App.css';

function App() {
  const [message, setMessage] = useState('');

  useEffect(() => {
    getHello().then(data => {
      setMessage(data.message);
    }).catch(err => {
      setMessage('API 호출 실패');
    });
  }, []);

  return (
    <div className="container mt-5">
      <h1>{message}</h1>
    </div>
  );
}

export default App;
*/
import React from 'react';
import { BrowserRouter } from "react-router-dom";
import AppRouter from './router/AppRouter';
import './styles/App.css';

function App() {
  return (
    <BrowserRouter>
      <AppRouter />
    </BrowserRouter>
  );
}

export default App;