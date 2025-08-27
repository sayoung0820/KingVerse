import { BrowserRouter, Routes, Route, Link } from "react-router-dom";
import Home from "./pages/Home";
import Books from "./pages/Books";
import BookDetail from "./pages/BookDetail";
import Characters from "./pages/Characters";
import CharacterDetail from "./pages/CharacterDetail";
import Admin from "./pages/Admin";
import Login from "./pages/Login";
import Footer from "./components/Footer";
import "./styles/globals.css";

export default function App() {
  return (
    <BrowserRouter>
      <header className="nav">
        <Link to="/" className="logo">KingVerse</Link>
        <nav>
          <Link to="/books">Books</Link>
          <Link to="/characters">Characters</Link>
          <Link to="/login">Login</Link>
        </nav>
      </header>

      <main className="container">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/books" element={<Books />} />
          <Route path="/books/:id" element={<BookDetail />} />
          <Route path="/characters" element={<Characters />} />
          <Route path="/characters/:id" element={<CharacterDetail />} />
          <Route path="/admin" element={<Admin />} />
          <Route path="/login" element={<Login />} />
        </Routes>
      </main>

      <Footer />
    </BrowserRouter>
  );
}


