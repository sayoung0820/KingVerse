import { useSearchParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { fetchCharacters } from "../api/characters";
import { fetchBooks } from "../api/books";         // <-- NEW
import CharacterCard from "../components/CharacterCard"; // <-- NEW

export default function Characters() {
  const [params, setParams] = useSearchParams();
  const q = params.get("q") || "";

  const [chars, setChars] = useState([]);
  const [bookTitleById, setBookTitleById] = useState({}); // id -> title
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    let cancelled = false;
    setLoading(true);
    setError("");

    // fetch characters (with search) and all books in parallel
    Promise.all([fetchCharacters(q), fetchBooks("")])
      .then(([characters, books]) => {
        if (cancelled) return;
        setChars(characters);

        const map = {};
        books.forEach(b => { map[b.bookId] = b.title; });
        setBookTitleById(map);
      })
      .catch(err => { if (!cancelled) setError(err.message); })
      .finally(() => { if (!cancelled) setLoading(false); });

    return () => { cancelled = true; };
  }, [q]);

  return (
    <section>
      <h2>Characters</h2>
      <input
        placeholder="Search by name…"
        value={q}
        onChange={e => setParams({ q: e.target.value })}
      />

      {loading && <p>Loading…</p>}
      {error && <p style={{ color: "crimson" }}>Error: {error}</p>}

      {!loading && !error && (
        <div className="grid">
          {chars.map(c => (
            <CharacterCard
              key={c.characterId}
              character={c}
              firstBookTitle={bookTitleById[c.firstAppearance] || "—"}
            />
          ))}
        </div>
      )}

      {!loading && !error && chars.length === 0 && <p>No results.</p>}
    </section>
  );
}

