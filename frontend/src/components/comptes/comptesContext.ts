import { createContext } from 'react';
import type {Compte} from "./comptes.types.ts";

export type ComptesContextType = {
  comptes: Compte[];
  loading: boolean;
  erreur: string | null;
  rechargerComptes: () => void;
  effectuerDepot: (numeroCompte: string, montant: number) => Promise<number>;
  effectuerRetrait: (numeroCompte: string, montant: number) => Promise<number>;
};

export const ComptesContext = createContext<ComptesContextType>({
  comptes: [],
  loading: false,
  erreur: null,
  rechargerComptes: () => {},
  effectuerDepot: async () => {
    throw new Error('effectuerDepot n’est pas encore initialisé');
  },
  effectuerRetrait: async () => {
    throw new Error('effectuerRetrait n’est pas encore initialisé');
  },
});
