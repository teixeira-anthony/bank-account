import {type ReactNode, useEffect, useState} from "react";
import {ComptesContext, type ComptesContextType} from "./comptesContext.ts";
import type {Compte} from "./comptes.types.ts";


export function ComptesProvider({ children }: { children: ReactNode }) {
  const [comptes, setComptes] = useState<Compte[]>([]);
  const [loading, setLoading] = useState(true);
  const [erreur, setErreur] = useState<string | null>(null);

  useEffect(() => {
    fetch('/comptes/mesComptes')
    .then(res => {
      if (!res.ok) throw new Error(`Erreur HTTP ${res.status}`);
      return res.json();
    })
    .then(data => {
      setComptes(data);
      setLoading(false);
    })
    .catch(e => {
      setLoading(false);
      setErreur(e instanceof Error ? e.message : 'Erreur inconnue');
    });
  }, []);


  const rechargerComptes = () => {
    setLoading(true);
    fetch('/comptes/mesComptes')
    .then(res => {
      if (!res.ok) throw new Error(`Erreur HTTP ${res.status}`);
      return res.json();
    })
    .then(data => {
      setComptes(data);
      setLoading(false);
    })
    .catch(e => {
      setLoading(false);
      setErreur(e instanceof Error ? e.message : 'Erreur inconnue');
    });
  };

  useEffect(() => {
    rechargerComptes();
  }, []);

  const contextValue: ComptesContextType = { comptes, loading, erreur, rechargerComptes };


  return (
      <ComptesContext.Provider value={contextValue}>
        {children}
      </ComptesContext.Provider>
  );
}
