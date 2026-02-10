import { BrowserRouter, Routes, Route } from "react-router-dom";
import Register from "./components/Register";
import Users from "./components/Users";
import Login from "./components/Login";
import Skills from "./components/Skills";
import Home from "./components/Home";
import AddSkill from "./components/AddSkill";
import Reports from "./components/Reports";
import { useState } from "react";
import ChatBot from "./components/ChatBot";

function App() {
  const [user, setUser] = useState(() => {
    const stored = localStorage.getItem("user");
    return stored ? JSON.parse(stored) : null;
  });


  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login setUser={setUser} />} />
        <Route path="/register" element={<Register />} />
        <Route path="/home" element={<Home />} />
        <Route path="/skills" element={<Skills />} />
        <Route path="/add-skill" element={<AddSkill userId={user?.id} />} />
        <Route path="/reports" element={<Reports userId={user?.id} />} />
        <Route path="/chatbot" element={<ChatBot/>} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
