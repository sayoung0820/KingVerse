import { Link } from "react-router-dom";

export default function BookCard({ book }) {
  const src = book.coverUrl || "/placeholder-book.jpg";
  return (
    <Link to={`/books/${book.bookId}`} className="card card-vertical">
      <img
        src={src}
        alt={`${book.title} cover`}
        className="book-cover-lg"
        width={240}
        height={360}
        loading="lazy"
        onError={(e) => { e.currentTarget.src = "/placeholder-book.jpg"; }}
      />
      <div className="card-body">
        <h3 className="card-title">{book.title}</h3>
        <p className="card-meta">
          {book.yearPublished} • {book.setting} • {book.series}
        </p>
      </div>
    </Link>
  );
}




