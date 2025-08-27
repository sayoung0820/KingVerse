import { useParams, Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { fetchBook, fetchBookCharacters } from "../api/books";
import { authedFetch } from "../api/auth";
import { useAuth } from "../context/AuthContext";

export default function BookDetail() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { hasRole } = useAuth();              // ← read auth from context
  const isAdmin = hasRole("ROLE_ADMIN");

  const [book, setBook] = useState(null);
  const [chars, setChars] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    let cancelled = false;
    setLoading(true);
    setError("");

    Promise.all([fetchBook(id), fetchBookCharacters(id)])
      .then(([b, c]) => {
        if (!cancelled) {
          setBook(b);
          setChars(c);
        }
      })
      .catch(err => { if (!cancelled) setError(err.message); })
      .finally(() => { if (!cancelled) setLoading(false); });

    return () => { cancelled = true; };
  }, [id]);

  async function handleDelete() {
    if (!confirm("Delete this book?")) return;
    const res = await authedFetch(`/api/books/${book.bookId}`, { method: "DELETE" });
    if (res.status === 204) {
      alert("Book deleted.");
      navigate("/books");
    } else {
      alert("Delete failed: HTTP " + res.status);
    }
  }

  async function handleQuickEdit() {
    // tiny prompt-based editor to satisfy PUT
    const title = prompt("Title:", book.title);
    if (title == null) return;
    const year = Number(prompt("Year published:", book.yearPublished ?? "") || book.yearPublished);
    const setting = prompt("Setting:", book.setting ?? "");
    const series = prompt("Series:", book.series ?? "");
    const summary = prompt("Summary:", book.summary ?? "");
    const coverUrl = prompt("Cover URL:", book.coverUrl ?? "");

    const payload = { ...book, title, yearPublished: year, setting, series, summary, coverUrl };
    const res = await authedFetch(`/api/books/${book.bookId}`, {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload)
    });
    if (res.ok) {
      const updated = await res.json();
      setBook(updated);
      alert("Updated.");
    } else {
      alert("Update failed: HTTP " + res.status);
    }
  }

  if (loading) return <p>Loading…</p>;
  if (error)   return <p style={{color:'crimson'}}>Error: {error}</p>;
  if (!book)   return <p>Book not found.</p>;

  return (
    <section>
      <Link to="/books" className="btn">← Back to Books</Link>

      {book.coverUrl && (
        <img
          src={book.coverUrl || "/placeholder-book.jpg"}
          alt={`${book.title} cover`}
          className="book-cover-detail"
          onError={(e)=> e.currentTarget.src = "/placeholder-book.jpg"}
        />
      )}

      <h2>{book.title} ({book.yearPublished})</h2>
      <p><b>Setting:</b> {book.setting} • <b>Series:</b> {book.series}</p>
      <p>{book.summary}</p>

      {isAdmin && (
        <div className="admin-actions" style={{display:"flex", gap:".5rem", margin:"1rem 0"}}>
          <button className="btn warn" onClick={handleQuickEdit}>Edit</button>
          <button className="btn danger" onClick={handleDelete}>Delete</button>
        </div>
      )}

      <h3>Characters</h3>
      <ul>
        {chars.map(c => (
          <li key={c.characterId}>
            <Link to={`/characters/${c.characterId}`}>{c.name}</Link>
          </li>
        ))}
        {chars.length === 0 && <li>None listed.</li>}
      </ul>
    </section>
  );
}

