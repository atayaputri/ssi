import * as dayjs from 'dayjs';
import { IMFHDR } from 'app/entities/mfhdr/mfhdr.model';
import { ITBKAB } from 'app/entities/tbkab/tbkab.model';
import { ITBNEG } from 'app/entities/tbneg/tbneg.model';

export interface ITBPROV {
  provsts?: string;
  provcod?: string;
  provnam?: string;
  provlmd?: dayjs.Dayjs;
  provuid?: string;
  mFHDRS?: IMFHDR[] | null;
  tBKABS?: ITBKAB[] | null;
  provneg?: ITBNEG | null;
}

export class TBPROV implements ITBPROV {
  constructor(
    public provsts?: string,
    public provcod?: string,
    public provnam?: string,
    public provlmd?: dayjs.Dayjs,
    public provuid?: string,
    public mFHDRS?: IMFHDR[] | null,
    public tBKABS?: ITBKAB[] | null,
    public provneg?: ITBNEG | null
  ) {}
}

export function getTBPROVIdentifier(tBPROV: ITBPROV): string | undefined {
  return tBPROV.provcod;
}
