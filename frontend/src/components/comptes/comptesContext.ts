import { createContext } from 'react';
import type {Compte} from "./comptes.types.ts";

export type ComptesContextType = {
  comptes: Compte[];
  loading: boolean;
  erreur: string | null;
};

export const ComptesContext = createContext<ComptesContextType>({
  comptes: [],
  loading: false,
  erreur: null,
});
