//import { email, password } from "./script.js";

export let loginPageAlert = document.getElementById("loginAlert");

export function loginUser(email, password) {
  console.log("Login function called");

  const apiUrl = "http://localhost:8080/api/login"; // adjust if needed

  const loginData = {
    email: email,
    password: password,
  };

  fetch(apiUrl, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(loginData),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error("Login failed: " + response.status);
      }
      return response.json();
    })
    .then((data) => {
      if (!data || Object.keys(data).length === 0) {
        console.log("Login failed: invalid credentials");
        loginPageAlert.innerHTML =
          "<p id='AlertW'>Invalid email or password.</p><style>#AlertW { color: red; margin: 0;padding: 10px; border: 1px solid red; }</style>";
        return;
      }

      console.log("Login successful:", data);
      //alert("Hello! This is an alert message.");
      loginPageAlert.innerHTML =
        "<p id='AlertS'>Login successful! Welcome, " +
        data.name +
        "</p><style>#AlertS { color: green; margin: 0; padding: 10px; border: 1px solid green; }</style>";

      //navigate to the dashboard after successful login
      setTimeout(() => {
        window.location.href = "home.html";
      }, 1000); // Delay for 1 second before navigation
    })
    .catch((error) => {
      console.error("Error logging in:", error);
    });
}
