import * as dayjs from 'dayjs';
import { IMFHDR } from 'app/entities/mfhdr/mfhdr.model';
import { ITBPROV } from 'app/entities/tbprov/tbprov.model';

export interface ITBNEG {
  negsts?: string;
  negcod?: string;
  negnam?: string;
  negtax?: number;
  neglmd?: dayjs.Dayjs;
  neguid?: string;
  mFHDRS?: IMFHDR[] | null;
  tBPROVS?: ITBPROV[] | null;
}

export class TBNEG implements ITBNEG {
  constructor(
    public negsts?: string,
    public negcod?: string,
    public negnam?: string,
    public negtax?: number,
    public neglmd?: dayjs.Dayjs,
    public neguid?: string,
    public mFHDRS?: IMFHDR[] | null,
    public tBPROVS?: ITBPROV[] | null
  ) {}
}

export function getTBNEGIdentifier(tBNEG: ITBNEG): string | undefined {
  return tBNEG.negcod;
}
