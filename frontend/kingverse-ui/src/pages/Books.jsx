import { useSearchParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { fetchBooks } from "../api/books";
import BookCard from "../components/BookCard";

export default function Books() {
  const [params, setParams] = useSearchParams();
  const q = params.get("q") || "";
  const [books, setBooks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    let cancelled = false;
    setLoading(true);
    setError("");

    fetchBooks(q)
      .then(data => { if (!cancelled) setBooks(data); })
      .catch(err => { if (!cancelled) setError(err.message); })
      .finally(() => { if (!cancelled) setLoading(false); });

    return () => { cancelled = true; };
  }, [q]);

  return (
    <section>
      <h2>Books</h2>
      <input
        placeholder="Search by title/setting..."
        value={q}
        onChange={e => setParams({ q: e.target.value })}
      />
      {loading && <p>Loading…</p>}
      {error && <p style={{ color: "crimson" }}>Error: {error}</p>}
      {!loading && !error && (
        <div className="grid">
          {books.map(b => <BookCard key={b.bookId} book={b} />)}
        </div>
      )}
      {!loading && !error && books.length === 0 && <p>No results.</p>}
    </section>
  );
}

