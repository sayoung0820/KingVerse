import { useParams, Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { fetchCharacter, fetchCharacterBooks, fetchCharacterConnections } from "../api/characters";
import { authedFetch } from "../api/auth";
import { useAuth } from "../context/AuthContext";

export default function CharacterDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { hasRole } = useAuth();            // ← use context
  const isAdmin = hasRole("ROLE_ADMIN");

  const [c, setC] = useState(null);
  const [books, setBooks] = useState([]);
  const [connections, setConnections] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const formatConnType = (t) => {
    if (!t) return "";
    const s = t.replace(/_/g, " ");
    return s.charAt(0).toUpperCase() + s.slice(1);
  };

  useEffect(() => {
    let cancelled = false;
    setLoading(true);
    setError("");

    Promise.all([
      fetchCharacter(id),
      fetchCharacterBooks(id),
      fetchCharacterConnections(id)
    ])
      .then(([ch, bs, conns]) => {
        if (!cancelled) {
          setC(ch);
          setBooks(bs);
          setConnections(conns);
        }
      })
      .catch(err => { if (!cancelled) setError(err.message); })
      .finally(() => { if (!cancelled) setLoading(false); });

    return () => { cancelled = true; };
  }, [id]);

  async function handleDelete() {
    if (!confirm("Delete this character?")) return;
    const res = await authedFetch(`/api/characters/${c.characterId}`, { method: "DELETE" });
    if (res.status === 204) {
      alert("Character deleted.");
      navigate("/characters");
    } else {
      alert("Delete failed: HTTP " + res.status);
    }
  }

  async function handleQuickEdit() {
    const name = prompt("Name:", c.name);
    if (name == null) return;
    const description = prompt("Description:", c.description ?? "");
    const imageUrl = prompt("Image URL:", c.imageUrl ?? "");
    const payload = { ...c, name, description, imageUrl };

    const res = await authedFetch(`/api/characters/${c.characterId}`, {
      method: "PUT",
      headers: { "Content-Type":"application/json" },
      body: JSON.stringify(payload)
    });
    if (res.ok) {
      const updated = await res.json();
      setC(updated);
      alert("Updated.");
    } else {
      alert("Update failed: HTTP " + res.status);
    }
  }

  if (loading) return <p>Loading…</p>;
  if (error)   return <p style={{color:'crimson'}}>Error: {error}</p>;
  if (!c)      return <p>Character not found.</p>;

  return (
    <section>
      <Link to="/characters" className="btn">← Back to Characters</Link>

      {c.imageUrl && (
        <img
          src={c.imageUrl}
          alt={c.name}
          className="character-portrait-detail"
          onError={(e)=> e.currentTarget.src = "/placeholder-character.jpg"}
        />
      )}

      <h2>{c.name}</h2>
      <p>{c.description}</p>

      {isAdmin && (
        <div className="admin-actions" style={{display:"flex", gap:".5rem", margin:"1rem 0"}}>
          <button className="btn warn" onClick={handleQuickEdit}>Edit</button>
          <button className="btn danger" onClick={handleDelete}>Delete</button>
        </div>
      )}

      <h3>Books</h3>
      <ul>
        {books.map(b => (
          <li key={b.bookId}>
            <Link to={`/books/${b.bookId}`}>{b.title}</Link>
          </li>
        ))}
        {books.length === 0 && <li>None listed.</li>}
      </ul>

      <h3>Connections</h3>
      <ul>
        {connections.map(cx => (
          <li key={cx.connectionId}>
            <span className="conn-type">{formatConnType(cx.connectionType)}</span> {cx.objectName}
            {cx.bookTitle ? <> <em>({cx.bookTitle})</em></> : null}
            {cx.note ? <> — {cx.note}</> : null}
          </li>
        ))}
        {connections.length === 0 && <li>None listed.</li>}
      </ul>
    </section>
  );
}




