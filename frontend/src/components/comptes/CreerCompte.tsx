import { useState } from 'react';

type RequeteCreationCompte = {
  type: 'COURANT' | 'EPARGNE';
};

type ReponseCreationCompte = {
  numeroCompte: string;
};

export default function CreerCompte() {
  const [typeCompte, setTypeCompte] = useState<RequeteCreationCompte['type']>('COURANT');
  const [numeroCompteCree, setNumeroCompteCree] = useState<string | null>(null);
  const [erreur, setErreur] = useState<string | null>(null);

  async function envoyerFormulaire(event: React.FormEvent) {
    event.preventDefault();
    setErreur(null);
    setNumeroCompteCree(null);

    const requete: RequeteCreationCompte = { type: typeCompte };

    try {
      const reponse = await fetch('/comptes/creerCompte', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(requete),
      });

      if (!reponse.ok) {
        throw new Error(`Erreur HTTP ${reponse.status}`);
      }

      const donnees: ReponseCreationCompte = await reponse.json();
      setNumeroCompteCree(donnees.numeroCompte);
    } catch (e: unknown) {
      if (e instanceof Error) {
        setErreur(e.message);
      } else {
        setErreur('Une erreur inconnue est survenue');
      }
    }
  }

  return (
      <div
          style={{
            maxWidth: '800px',
            margin: '2rem auto',
            padding: '1rem',
            border: '1px solid #ddd',
            borderRadius: '8px',
            boxShadow: '0 2px 8px rgba(0, 0, 0, 0.05)',
          }}
      >
        <h2 style={{ textAlign: 'center', marginBottom: '1.5rem' }}>
          Créer un compte bancaire
        </h2>

        <form onSubmit={envoyerFormulaire} style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
          <label style={{ display: 'flex', flexDirection: 'column', fontWeight: 'bold' }}>
            Type de compte :
            <select
                value={typeCompte}
                onChange={e => setTypeCompte(e.target.value as RequeteCreationCompte['type'])}
                style={{
                  padding: '0.5rem',
                  border: '1px solid #ccc',
                  borderRadius: '4px',
                  marginTop: '0.5rem',
                  minWidth: '150px',
                }}
            >
              <option value="COURANT">Courant</option>
              <option value="EPARGNE">Épargne</option>
            </select>
          </label>

          <button
              type="submit"
              style={{
                padding: '0.6rem 1.2rem',
                backgroundColor: '#007bff',
                color: '#fff',
                border: 'none',
                borderRadius: '4px',
                cursor: 'pointer',
                marginTop: '1.2rem',
                height: 'fit-content',
              }}
          >
            Créer
          </button>
        </form>

        {numeroCompteCree && (
            <p style={{ marginTop: '1rem', color: 'green' }}>
              Compte créé avec le numéro : {numeroCompteCree}
            </p>
        )}

        {erreur && (
            <p style={{ marginTop: '1rem', color: 'red' }}>
              Erreur : {erreur}
            </p>
        )}
      </div>
  );
}
