import { Routes, Route, Link } from 'react-router-dom';
import CreerCompte from "./components/CreerCompte.tsx";
import DeposerArgent from "./components/DeposerArgent.tsx";
import RetirerArgent from "./components/RetirerArgent.tsx";

export default function App() {
  return (
      <div>
        <nav style={{ marginBottom: '1rem' }}>
          <Link to="/" style={{ marginRight: '1rem' }}>Accueil</Link>
          <Link to="/creer-compte" style={{ marginRight: '1rem' }}>Création</Link>
          <Link to="/deposer-argent" style={{ marginRight: '1rem' }}>Dépôt</Link>
          <Link to="/retirer-argent">Retrait</Link>
        </nav>

        <Routes>
          <Route path="/" element={<h1>Bienvenue dans la banque !</h1>} />
          <Route path="/creer-compte" element={<CreerCompte />} />
          <Route path="/deposer-argent" element={<DeposerArgent />} />
          <Route path="/retirer-argent" element={<RetirerArgent />} />
        </Routes>
      </div>
  );
}
