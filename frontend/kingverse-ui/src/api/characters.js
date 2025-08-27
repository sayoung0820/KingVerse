export async function fetchCharacters(q) {
  const url = q ? `/api/characters?q=${encodeURIComponent(q)}` : `/api/characters`;
  const res = await fetch(url);
  if (!res.ok) throw new Error('Failed to load characters');
  return res.json();
}

export async function fetchCharacter(id) {
  const res = await fetch(`/api/characters/${id}`);
  if (!res.ok) throw new Error('Character not found');
  return res.json();
}

export async function fetchCharacterBooks(id) {
  const res = await fetch(`/api/characters/${id}/books`);
  if (!res.ok) throw new Error('Failed to load books for character');
  return res.json();
}

export async function fetchCharacterConnections(id) {
  const res = await fetch(`/api/characters/${id}/connections`);
  if (!res.ok) throw new Error('Failed to load connections');
  return res.json();
}




