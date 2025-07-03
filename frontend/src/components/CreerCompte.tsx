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
      <div>
        <h2>Créer un compte bancaire</h2>
        <form onSubmit={envoyerFormulaire}>
          <label>
            Type de compte :
            <select value={typeCompte} onChange={e => setTypeCompte(e.target.value as RequeteCreationCompte['type'])}>
              <option value="COURANT">Courant</option>
              <option value="EPARGNE">Épargne</option>
            </select>
          </label>
          <button type="submit">Créer</button>
        </form>

        {numeroCompteCree && <p>Compte créé avec le numéro : {numeroCompteCree}</p>}
        {erreur && <p style={{ color: 'red' }}>Erreur : {erreur}</p>}
      </div>
  );
}
