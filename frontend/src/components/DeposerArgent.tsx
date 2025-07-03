import { useState } from 'react';

type ResponseDepot = {
  solde: number;
}
export default function DeposerArgent() {

  const [numeroCompteACrediter, setNumeroCompteACrediter] = useState<string | null>(null);
  const [montantACrediter, setMontantACrediter] = useState<number>(0);
  const [soldeDuCompte, setSoldeDuCompte] = useState<number | null>(null);
  const [erreur, setErreur] = useState<string | null>(null);

  async function envoyerFormulaire(event: React.FormEvent) {
    event.preventDefault();
    setErreur(null);

    try {
      const reponse = await fetch('/comptes/depot', {
        method: 'PATCH',
        headers: { 'content-type': 'application/json' },
        body: JSON.stringify({ numeroCompte : numeroCompteACrediter, montant : montantACrediter }),
      });

      if (!reponse.ok) {
        throw new Error(`Erreur HTTP ${reponse.status}`);
      }

      const response: ResponseDepot = await reponse.json();
      setSoldeDuCompte(response.solde)
    }catch (e : unknown) {
      if (e instanceof Error) {
        setErreur(e.message);
      }else {
        setErreur('Une erreur inconnue est survenue');
      }
    }
  }

  return (
      <div>
        <h2>Deposer de l'argent sur un compte</h2>
        <form onSubmit={envoyerFormulaire}>
          <label>
            Numéro de compte :
            <input
                type="text"
                value={numeroCompteACrediter ?? ''}
                onChange={e => setNumeroCompteACrediter(e.target.value)} />
          </label>
          <br/>
          <br/>
          <label>
            Montant à créditer :
            <input
                type="number"
                step="0.01"
                min="0"
                value={montantACrediter}
                onChange={e => setMontantACrediter(parseFloat(e.target.value))} />
          </label>
          <button type="submit" style={{ marginLeft: '1rem' }}>Déposer</button>
        </form>

        {soldeDuCompte !== null && <p>Le solde du compte après dépot est de : {soldeDuCompte}</p>}
        {erreur && <p style={{ color: 'red' }}>Erreur : {erreur}</p>}
      </div>
  );
}