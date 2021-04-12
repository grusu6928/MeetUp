import React, { useState } from "react";
import Form from "react-bootstrap/Form";
import Button from "./Button";

import { useAppContext } from "./Contexts";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const { userHasAuthenticated } = useAppContext();

  function validateForm() {
    return isValidEmail(email) && isValidPassword(password);
  }
  function isValidEmail(acct) {
      const emailRegex = RegExp('^[a-zA-Z0-9._%+-]+@brown+\.edu$')
      return emailRegex.test(acct)
  }
  function isValidPassword(pass) {
      return pass.length > 0;
  }

  function handleSubmit(event) {
    event.preventDefault();
    requestAuthentication(email, password);
  }
  
  const requestAuthentication = () => {
    const toSend = {
        email: email,
        pass: password,
    }
    let config = {
        headers: {
          "Content-Type": "application/json",
          'Access-Control-Allow-Origin': '*',
          }
        }
        axios.post('http://localhost:4567/login', toSend, config)
        .then(response => {
            if(response.data) {
                // successful login, we want to redirect to home
                console.log("sucess");
            } else {
                // rerender page, with alert
                console.log("failure");
            }
        })
        .catch(function (error) {
          console.log(error);
        });
      }

  return (
    <div className="Login">
      <Form onSubmit={handleSubmit}>
        <Form.Group size="lg" controlId="email">
          <Form.Label>Email</Form.Label>
          <Form.Control
            autoFocus
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </Form.Group>
        <Form.Group size="lg" controlId="password">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </Form.Group>
        <a href="Home.js">
        <Button block size="lg" type="submit" disabled={!validateForm()}>
          Login
        </Button>
        </a>
      </Form>
    </div>
  );
}
export default Login;