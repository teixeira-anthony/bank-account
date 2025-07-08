import { useContext } from 'react';
import { ComptesContext } from './comptesContext.ts';

export function useComptes() {
  return useContext(ComptesContext);
}
