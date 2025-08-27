import { Link } from "react-router-dom";
import { motion, useReducedMotion } from "framer-motion";

export default function Home(){
  const prefersReduced = useReducedMotion();

   // subtle float: up/down a few pixels forever
  const floatAnim = prefersReduced
    ? {} // respect accessibility setting
    : {
        y: [0, -8, 0, -4, 0],
        rotate: [0, -1.5, 0, 1, 0],
        transition: {
          duration: 6,
          repeat: Infinity,
          repeatType: "loop",
          ease: "easeInOut",
        },
      };

  return (
    <section className="hero">
      <div className="hero-overlay">
        <h1>Welcome to the Multiverse</h1>
        <h1 className="headline">Explore the terrifying World of Stephen King</h1>
        <p className="lede">
          Browse books, characters, and the eerie threads that connect them across the King multiverse.
        </p>
        <Link to="/books" className="btn primary">Start Exploring</Link>
      </div>
      <div className="panel">
        <img src="/combo.webp" alt="Stephen King multiverse" />
      </div>
      
      
      <motion.img
        src="/multiverse.jpg"                     
        alt="Floating spooky accent"
        className="float-bottom"              
        initial={{ x: "-40%", opacity: 0 }} 
        animate={{ x: 0, opacity: 0.9, ...floatAnim }}
        transition={{ type: "spring", stiffness: 60, damping: 16, delay: 0.3 }}
      />
    </section>
  );
}
