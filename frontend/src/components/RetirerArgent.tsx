import {useState} from "react";

type ResponseRetrait = {
  solde: number;
}
export default function RetirerArgent() {

  const [numeroCompteADebiter, setNumeroCompteADebiter] = useState<string | null>(null);
  const [montantADebiter, setMontantADebiter] = useState<number>(0);
  const [soldeDuCompte, setSoldeDuCompte] = useState<number | null>(null);
  const [erreur, setErreur] = useState<string | null>(null);

  async function envoyerFormulaire(event: React.FormEvent) {
    event.preventDefault();
    setErreur(null);

    try {
      const httpReponse = await fetch('/comptes/retrait', {
        method: 'PATCH',
        headers: {'content-type': 'application/json'},
        body: JSON.stringify({ numeroCompte : numeroCompteADebiter, montant : montantADebiter}),
      });

      if (!httpReponse.ok) {
        throw new Error(`Erreur HTTP ${httpReponse.status}`);
      }

      const responseRetrait : ResponseRetrait = await httpReponse.json();
      setSoldeDuCompte(responseRetrait.solde)
    }catch (e : unknown) {
      if (e instanceof Error) {
        setErreur(e.message);
      }else {
        setErreur('Une erreur est inconnue est survenue');
      }
    }
  }

  return (
      <div>
        <h2>Retirer de l'argent sur un compte</h2>
        <form onSubmit={envoyerFormulaire}>
          <label>
            Numéro de compte :
            <input
                type="text"
                value={numeroCompteADebiter ?? ''}
                onChange={e => setNumeroCompteADebiter(e.target.value)}
            />
          </label>
          <br/>
          <br/>
          <label>
            Montant à débiter :
            <input
                type="number"
                step="0.01"
                min="0"
                value={montantADebiter}
                onChange={e => setMontantADebiter(parseFloat(e.target.value))}
            />
          </label>
          <button type="submit" style={{ marginLeft: '1rem' }}>Valider</button>
        </form>
        {soldeDuCompte !== null && <p>Le solde du compte après dépot est de : {soldeDuCompte}</p>}
        {erreur && <p style={{ color: 'red' }}>Erreur : {erreur}</p>}
      </div>
  );
}