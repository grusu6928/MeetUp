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
    try {
        userHasAuthenticated(true);
    } catch {
        
    }
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