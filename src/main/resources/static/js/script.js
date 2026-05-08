import { loginPageAlert, loginUser } from "./app.js";

const emailInput = document.getElementById("emailInput");
const passwordInput = document.getElementById("passwordInput");

const loginBtn = document.getElementById("loginButton");
const registerBtn = document.getElementById("registerButton");

let email = "";
let password = "";

loginBtn.addEventListener("click", function () {
  console.log("Login button clicked");

  email = emailInput.value;
  password = passwordInput.value;

  while (email.length === 0 || password.length === 0) {
    loginPageAlert.innerHTML =
      "<p id='AlertW'>Please enter both email and password.</p><style>#AlertW { color: red; margin: 0;padding: 10px; border: 1px solid red; }</style>";
    return;
  }

  loginUser(email, password);

  emailInput.value = "";
  passwordInput.value = "";
  console.log("Email: " + email);
  console.log("Password: " + password);
});

registerBtn.addEventListener("click", function () {
  console.log("Register button clicked");

  loginUser(email, password);
});
