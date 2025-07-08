import {useComptes} from "./useComptes.ts";

export default function MesComptes() {
  const { comptes, loading, erreur } = useComptes();

  if (loading) return <p>Chargement...</p>;
  if (erreur) return <p style={{ color: 'red' }}>Erreur : {erreur}</p>;

  return (
      <div
          style={{
            maxWidth: '800px',
            margin: '2rem auto',
            padding: '0 1rem',
          }}
      >
        <table
            style={{
              borderCollapse: 'collapse',
              width: '100%',
            }}
        >
          <thead>
          <tr style={{ backgroundColor: '#f2f2f2' }}>
            <th
                style={{
                  border: '1px solid #ddd',
                  padding: '8px',
                  textAlign: 'left',
                }}
            >
              Numéro
            </th>
            <th
                style={{
                  border: '1px solid #ddd',
                  padding: '8px',
                  textAlign: 'left',
                }}
            >
              Solde
            </th>
            <th
                style={{
                  border: '1px solid #ddd',
                  padding: '8px',
                  textAlign: 'left',
                }}
            >
              Type
            </th>
          </tr>
          </thead>
          <tbody>
          {comptes.map((compte) => (
              <tr key={compte.numeroDeCompte}>
                <td
                    style={{
                      border: '1px solid #ddd',
                      padding: '8px',
                    }}
                >
                  {compte.numeroDeCompte}
                </td>
                <td
                    style={{
                      border: '1px solid #ddd',
                      padding: '8px',
                    }}
                >
                  {compte.solde}
                </td>
                <td
                    style={{
                      border: '1px solid #ddd',
                      padding: '8px',
                    }}
                >
                  {compte.typeDeCompte}
                </td>
              </tr>
          ))}
          </tbody>
        </table>
      </div>

  );
}