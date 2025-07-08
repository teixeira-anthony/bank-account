import {useState} from "react";
import {useComptes} from "./useComptes.ts";

type ResponseRetrait = {
  solde: number;
}
export default function RetirerArgent() {

  const {comptes, loading, erreur: erreurChargement} = useComptes();
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
        body: JSON.stringify({numeroCompte: numeroCompteADebiter, montant: montantADebiter}),
      });

      if (!httpReponse.ok) {
        throw new Error(`Erreur HTTP ${httpReponse.status}`);
      }

      const responseRetrait: ResponseRetrait = await httpReponse.json();
      setSoldeDuCompte(responseRetrait.solde)
    } catch (e: unknown) {
      if (e instanceof Error) {
        setErreur(e.message);
      } else {
        setErreur('Une erreur est inconnue est survenue');
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
        <h2 style={{textAlign: 'center', marginBottom: '1.5rem'}}>
          Retirer de l'argent sur un compte
        </h2>

        {loading ? (
            <p>Chargement des comptes...</p>
        ) : erreurChargement ? (
            <p style={{color: 'red'}}>Erreur lors du chargement : {erreurChargement}</p>
        ) : (
            <form onSubmit={envoyerFormulaire}>
              <div style={{marginBottom: '1rem'}}>
                <label style={{display: 'block', marginBottom: '0.5rem'}}>
                  Sélectionnez un compte :
                </label>
                <select
                    value={numeroCompteADebiter ?? ''}
                    onChange={(e) => setNumeroCompteADebiter(e.target.value)}
                    style={{
                      width: '100%',
                      padding: '0.5rem',
                      border: '1px solid #ccc',
                      borderRadius: '4px',
                    }}
                >
                  <option value="" disabled>
                    -- Choisissez un compte --
                  </option>
                  {comptes.map((compte) => (
                      <option key={compte.numeroDeCompte} value={compte.numeroDeCompte}>
                        {compte.numeroDeCompte} | {compte.solde.toFixed(2)} €
                        | {compte.typeDeCompte}
                      </option>
                  ))}
                </select>
              </div>

              <div style={{marginBottom: '1rem'}}>
                <label style={{display: 'block', marginBottom: '0.5rem'}}>
                  Montant à débiter :
                </label>
                <input
                    type="number"
                    step="0.01"
                    min="0"
                    value={montantADebiter}
                    onChange={(e) => setMontantADebiter(parseFloat(e.target.value))}
                    style={{
                      width: '100%',
                      padding: '0.5rem',
                      border: '1px solid #ccc',
                      borderRadius: '4px',
                    }}
                />
              </div>

              <button
                  type="submit"
                  style={{
                    padding: '0.6rem 1.2rem',
                    backgroundColor: '#007bff',
                    color: '#fff',
                    border: 'none',
                    borderRadius: '4px',
                    cursor: 'pointer',
                  }}
              >
                Valider
              </button>
            </form>
        )}
        {soldeDuCompte !== null && (
            <p style={{marginTop: '1rem', color: 'green'}}>
              Le solde du compte après retrait est de : {soldeDuCompte}
            </p>
        )}

        {erreur && (
            <p style={{marginTop: '1rem', color: 'red'}}>
              Erreur : {erreur}
            </p>
        )}
      </div>
  );
}