import { useEffect, useState } from "react";
import { me as fetchCurrentUser, authedFetch, logout } from "../api/auth";
import { Link } from "react-router-dom";

export default function Admin() {
  const [user, setUser] = useState(null);
  const [status, setStatus] = useState("");

  // Book form state
  const [book, setBook] = useState({
    title: "",
    yearPublished: "",
    setting: "",
    series: "",
    summary: "",
    coverUrl: ""
  });
  const [deleteBookId, setDeleteBookId] = useState("");

  // Character form state
  const [character, setCharacter] = useState({
    name: "",
    description: "",
    firstAppearance: "", // bookId (number)
    imageUrl: ""
  });
  const [deleteCharId, setDeleteCharId] = useState("");

  useEffect(() => {
    let cancel = false;
    fetchCurrentUser()
      .then((u) => { if (!cancel) setUser(u); })
      .catch(() => { if (!cancel) setUser({ authenticated: false }); });
    return () => { cancel = true; };
  }, []);

  if (user == null) {
    return <section><h2>Admin</h2><p>Loading…</p></section>;
  }

  const isAdmin = user.authenticated && (user.roles || []).includes("ROLE_ADMIN");

  if (!user.authenticated) {
    return (
      <section>
        <h2>Admin</h2>
        <p>
          You must <Link to="/login">log in</Link> to access this page.
        </p>
      </section>
    );
  }

  if (!isAdmin) {
    return (
      <section>
        <h2>Admin</h2>
        <p>403 — You need the ADMIN role to use this page.</p>
      </section>
    );
  }

  async function onLogout() {
    setStatus("");
    try {
      const res = await logout();
      if (res.ok) {
        setUser({ authenticated: false, roles: [] });
        setStatus("Logged out.");
      }
    } catch (e) {
      setStatus("Logout failed.");
    }
  }

  // --- BOOK handlers ---
  async function createBook(e) {
    e.preventDefault();
    setStatus("");
    const payload = {
      title: book.title,
      yearPublished: book.yearPublished ? Number(book.yearPublished) : null,
      setting: book.setting || null,
      series: book.series || null,
      summary: book.summary || null,
      coverUrl: book.coverUrl || null
    };
    const res = await authedFetch("/api/books", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });
    if (res.ok) {
      setStatus("✅ Book created.");
      setBook({ title: "", yearPublished: "", setting: "", series: "", summary: "", coverUrl: "" });
    } else {
      setStatus(`❌ Failed to create book (HTTP ${res.status}).`);
    }
  }

  async function deleteBook() {
    setStatus("");
    if (!deleteBookId) return setStatus("Enter a Book ID.");
    const res = await authedFetch(`/api/books/${deleteBookId}`, { method: "DELETE" });
    if (res.status === 204) {
      setStatus("🗑️ Book deleted.");
      setDeleteBookId("");
    } else {
      setStatus(`❌ Delete failed (HTTP ${res.status}).`);
    }
  }

  // --- CHARACTER handlers ---
  async function createCharacter(e) {
    e.preventDefault();
    setStatus("");
    const payload = {
      name: character.name,
      description: character.description || null,
      firstAppearance: character.firstAppearance ? Number(character.firstAppearance) : null,
      imageUrl: character.imageUrl || null
    };
    const res = await authedFetch("/api/characters", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });
    if (res.ok) {
      setStatus("✅ Character created.");
      setCharacter({ name: "", description: "", firstAppearance: "", imageUrl: "" });
    } else {
      setStatus(`❌ Failed to create character (HTTP ${res.status}).`);
    }
  }

  async function deleteCharacter() {
    setStatus("");
    if (!deleteCharId) return setStatus("Enter a Character ID.");
    const res = await authedFetch(`/api/characters/${deleteCharId}`, { method: "DELETE" });
    if (res.status === 204) {
      setStatus("🗑️ Character deleted.");
      setDeleteCharId("");
    } else {
      setStatus(`❌ Delete failed (HTTP ${res.status}).`);
    }
  }

  return (
    <section>
      {/* Logged-in banner */}
      <div style={{display:"flex",justifyContent:"space-between",alignItems:"center",marginBottom:"1rem"}}>
        <p style={{margin:0}}>
          <strong>User:</strong> {user.username} &nbsp;•&nbsp; 
          <strong>Roles:</strong> {(user.roles || []).join(", ")}
        </p>
        <button className="btn" onClick={onLogout}>Logout</button>
      </div>

      {status && <p style={{marginTop:".25rem"}}>{status}</p>}

      {/* BOOKS */}
      <h3 style={{ marginTop: "1.5rem" }}>Add Book</h3>
      <form className="stack" onSubmit={createBook}>
        <input placeholder="Title" value={book.title}
               onChange={e=>setBook({...book, title:e.target.value})} required />
        <input placeholder="Year Published" value={book.yearPublished}
               onChange={e=>setBook({...book, yearPublished:e.target.value})} />
        <input placeholder="Setting" value={book.setting}
               onChange={e=>setBook({...book, setting:e.target.value})} />
        <input placeholder="Series" value={book.series}
               onChange={e=>setBook({...book, series:e.target.value})} />
        <input placeholder="Cover URL (/images/bookcovers/...)" value={book.coverUrl}
               onChange={e=>setBook({...book, coverUrl:e.target.value})} />
        <textarea placeholder="Summary" value={book.summary}
               onChange={e=>setBook({...book, summary:e.target.value})} />
        <button className="btn primary" type="submit">Create Book</button>
      </form>

      <h4>Delete Book</h4>
      <div className="row">
        <input placeholder="Book ID" value={deleteBookId}
               onChange={e=>setDeleteBookId(e.target.value)} />
        <button className="btn danger" onClick={deleteBook}>Delete</button>
      </div>

      {/* CHARACTERS */}
      <h3 style={{ marginTop: "2rem" }}>Add Character</h3>
      <form className="stack" onSubmit={createCharacter}>
        <input placeholder="Name" value={character.name}
               onChange={e=>setCharacter({...character, name:e.target.value})} required />
        <textarea placeholder="Description" value={character.description}
               onChange={e=>setCharacter({...character, description:e.target.value})} />
        <input placeholder="First Appearance (Book ID)" value={character.firstAppearance}
               onChange={e=>setCharacter({...character, firstAppearance:e.target.value})} />
        <input placeholder="Image URL (/images/characters/...)" value={character.imageUrl}
               onChange={e=>setCharacter({...character, imageUrl:e.target.value})} />
        <button className="btn primary" type="submit">Create Character</button>
      </form>

      <h4>Delete Character</h4>
      <div className="row">
        <input placeholder="Character ID" value={deleteCharId}
               onChange={e=>setDeleteCharId(e.target.value)} />
        <button className="btn danger" onClick={deleteCharacter}>Delete</button>
      </div>
    </section>
  );
}



