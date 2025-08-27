export async function fetchBooks(q) {
  const url = q ? `/api/books?q=${encodeURIComponent(q)}` : `/api/books`;
  const res = await fetch(url);
  if (!res.ok) throw new Error('Failed to load books');
  return res.json();
}

export async function fetchBook(id) {
  const res = await fetch(`/api/books/${id}`);
  if (!res.ok) throw new Error('Book not found');
  return res.json();
}

export async function fetchBookCharacters(id) {
  const res = await fetch(`/api/books/${id}/characters`);
  if (!res.ok) throw new Error('Failed to load characters for book');
  return res.json();
}
