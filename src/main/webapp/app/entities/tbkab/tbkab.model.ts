import * as dayjs from 'dayjs';
import { IMFHDR } from 'app/entities/mfhdr/mfhdr.model';
import { ITBPROV } from 'app/entities/tbprov/tbprov.model';

export interface ITBKAB {
  kabsts?: string;
  kabcod?: string;
  kabnam?: string;
  kablmd?: dayjs.Dayjs;
  kabuid?: string;
  mFHDRS?: IMFHDR[] | null;
  kabprov?: ITBPROV | null;
}

export class TBKAB implements ITBKAB {
  constructor(
    public kabsts?: string,
    public kabcod?: string,
    public kabnam?: string,
    public kablmd?: dayjs.Dayjs,
    public kabuid?: string,
    public mFHDRS?: IMFHDR[] | null,
    public kabprov?: ITBPROV | null
  ) {}
}

export function getTBKABIdentifier(tBKAB: ITBKAB): string | undefined {
  return tBKAB.kabcod;
}
