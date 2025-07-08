import {Routes, Route, Link} from 'react-router-dom';
import CreerCompte from "./components/comptes/CreerCompte.tsx";
import DeposerArgent from "./components/comptes/DeposerArgent.tsx";
import RetirerArgent from "./components/comptes/RetirerArgent.tsx";
import MesComptes from "./components/comptes/MesComptes.tsx";
import {ComptesProvider} from "./components/comptes/ComptesProvider.tsx";

export default function App() {
  return (
      <ComptesProvider>
        <div
            style={{
              maxWidth: '900px',
              margin: '2rem auto',
              padding: '1rem',
              fontFamily: 'Arial, sans-serif',
            }}
        >
          <nav
              style={{
                marginBottom: '1.5rem',
                borderBottom: '1px solid #ccc',
                paddingBottom: '0.5rem',
                display: 'flex',
                gap: '1.5rem',
              }}
          >
            <Link to="/" style={{textDecoration: 'none', color: '#007bff'}}>
              Accueil
            </Link>
            <Link
                to="/creer-compte"
                style={{textDecoration: 'none', color: '#007bff'}}
            >
              Création
            </Link>
            <Link
                to="/deposer-argent"
                style={{textDecoration: 'none', color: '#007bff'}}
            >
              Dépôt
            </Link>
            <Link
                to="/retirer-argent"
                style={{textDecoration: 'none', color: '#007bff'}}
            >
              Retrait
            </Link>
            <Link
                to="/mes-comptes"
                style={{textDecoration: 'none', color: '#007bff'}}
            >
              Mes comptes
            </Link>
          </nav>

          <Routes>
            <Route path="/" element={<h1>Bienvenue dans la banque !</h1>}/>
            <Route path="/creer-compte" element={<CreerCompte/>}/>
            <Route path="/deposer-argent" element={<DeposerArgent/>}/>
            <Route path="/retirer-argent" element={<RetirerArgent/>}/>
            <Route path="/mes-comptes" element={<MesComptes/>}/>
          </Routes>
        </div>
      </ComptesProvider>
  );
}
