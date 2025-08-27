import { NavLink, Link, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import { me as fetchMe, logout } from "../api/auth";

export default function NavBar() {
  const [who, setWho] = useState({ authenticated: false, roles: [] });
  const location = useLocation();

  const isAdmin =
    who.authenticated && (who.roles || []).includes("ROLE_ADMIN");

  // Re-fetch session whenever the route changes (e.g., after login redirect)
  useEffect(() => {
    let cancelled = false;
    fetchMe()
      .then((w) => !cancelled && setWho(w))
      .catch(() => !cancelled && setWho({ authenticated: false, roles: [] }));
    return () => {
      cancelled = true;
    };
  }, [location.key]);

  async function handleLogout() {
    try {
      await logout();
    } finally {
      // simplest: hard refresh so state resets
      window.location.href = "/";
    }
  }

  return (
    <header className="nav">
      <div className="container nav-inner">
        <Link to="/" className="logo">
          <span className="logo-badge">KV</span> KingVerse
        </Link>

        <nav>
          <NavLink to="/books">Books</NavLink>
          <NavLink to="/characters">Characters</NavLink>

          {/* Show Admin only for logged-in admins */}
          {isAdmin && <NavLink to="/admin">Admin</NavLink>}

          {!who.authenticated ? (
            <NavLink to="/login">Login</NavLink>
          ) : (
            <button className="linklike" onClick={handleLogout}>
              Logout {who.username ? `(${who.username})` : ""}
            </button>
          )}
        </nav>
      </div>
    </header>
  );
}









