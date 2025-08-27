import { Link } from "react-router-dom";

export default function CharacterCard({ character, firstBookTitle }) {
  const src = character.imageUrl || "/placeholder-character.jpg";

  return (
    <Link to={`/characters/${character.characterId}`} className="card char-card-vertical">
      <img
        src={src}
        alt={character.name}
        className="character-portrait-lg"
        width={240}
        height={360}
        loading="lazy"
        onError={(e) => { e.currentTarget.src = "/placeholder-character.jpg"; }}
      />
      <div className="card-body">
        <h3 className="card-title">{character.name}</h3>
        <p className="card-meta">First appearance: {firstBookTitle ?? "—"}</p>
      </div>
    </Link>
  );
}



