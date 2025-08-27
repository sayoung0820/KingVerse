import { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Login() {
  const { login } = useAuth();
  const nav = useNavigate();
  const location = useLocation();
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [err, setErr] = useState("");

  // optional: redirect back to where user came from
  const from = location.state?.from || "/";

  async function onSubmit(e) {
    e.preventDefault();
    setErr("");
    try {
      await login(username, password);
      nav(from, { replace: true });
    } catch (ex) {
      setErr(ex.message || "Login failed");
    }
    window.location.href = "/";
  }
  

  return (
    <section className="login-page">
      <div className="login-card">
        <h2>Login</h2>
        <form className="form" onSubmit={onSubmit}>
          <input
            placeholder="Username"
            value={username}
            onChange={e=>setUsername(e.target.value)}
          />
          <input
            type="password"
            placeholder="Password"
            value={password}
            onChange={e=>setPassword(e.target.value)}
          />
          {err && <p style={{color:"salmon"}}>{err}</p>}
          <button className="btn">Login</button>
        </form>
      </div>
    </section>
  );
}


