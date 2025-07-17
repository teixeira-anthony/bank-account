import {ComptesContext, type ComptesContextType} from "./comptesContext.ts";
import {type ReactNode, useEffect, useState} from "react";
import type {Compte} from "./comptes.types.ts";

export function ComptesProvider({ children }: { children: ReactNode }) {
  const [comptes, setComptes] = useState<Compte[]>([]);
  const [loading, setLoading] = useState(true);
  const [erreur, setErreur] = useState<string | null>(null);

  // Appeler `rechargerComptes` lors du montage
  useEffect(() => {
    rechargerComptes();
  }, []);

  async function rechargerComptes() {
    setLoading(true);
    setErreur(null);
    try {
      const res = await fetch('/comptes/mesComptes');
      if (!res.ok) throw new Error(`Erreur HTTP ${res.status}`);
      const data = await res.json();
      setComptes(data);
    } catch (e) {
      setErreur(e instanceof Error ? e.message : 'Erreur inconnue');
    } finally {
      setLoading(false);
    }
  }

  async function effectuerDepot(numeroCompte: string, montant: number): Promise<number> {
    const res = await fetch('/comptes/depot', {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ numeroCompte, montant }),
    });

    if (!res.ok) throw new Error(`Erreur HTTP ${res.status}`);

    const data = await res.json();
    await rechargerComptes();
    return data.solde;
  }

  async function effectuerRetrait(numeroCompte: string, montant: number): Promise<number> {
    const response = await fetch('/comptes/retrait', {
      method: 'PATCH',
      headers: {'content-type': 'application/json'},
      body: JSON.stringify({numeroCompte: numeroCompte, montant: montant}),
    });
    if (!response.ok) throw new Error(`Erreur HTTP ${response.status}`);

    const data = await response.json();
    await rechargerComptes();
    return data.solde;
  }

  const contextValue: ComptesContextType = {
    comptes,
    loading,
    erreur,
    rechargerComptes,
    effectuerDepot,
    effectuerRetrait,
  };

  return (
      <ComptesContext.Provider value={contextValue}>
        {children}
      </ComptesContext.Provider>
  );
}
