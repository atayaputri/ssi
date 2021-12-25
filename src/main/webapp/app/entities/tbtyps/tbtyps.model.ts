import * as dayjs from 'dayjs';
import { IMFHDR } from 'app/entities/mfhdr/mfhdr.model';

export interface ITBTYPS {
  tpssts?: string;
  tpscod?: string;
  tpsnam?: string;
  tpslmd?: dayjs.Dayjs;
  tpsuid?: string;
  mFHDRS?: IMFHDR[] | null;
}

export class TBTYPS implements ITBTYPS {
  constructor(
    public tpssts?: string,
    public tpscod?: string,
    public tpsnam?: string,
    public tpslmd?: dayjs.Dayjs,
    public tpsuid?: string,
    public mFHDRS?: IMFHDR[] | null
  ) {}
}

export function getTBTYPSIdentifier(tBTYPS: ITBTYPS): string | undefined {
  return tBTYPS.tpscod;
}
