
export default function Footer() {
  return (
    <footer className="footer">
      © {new Date().getFullYear()} Shannon Young •{" "}
      <a
        href="https://github.com/sayoung0820/JavaRepo"
        target="_blank"
        rel="noopener noreferrer"
      >
        GitHub
      </a>{" "}
      •{" "}
      <a href="mailto:sayoung0820@example.com">Contact</a>
    </footer>
  );
}

