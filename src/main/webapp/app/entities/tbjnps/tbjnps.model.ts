import * as dayjs from 'dayjs';
import { IMFHDR } from 'app/entities/mfhdr/mfhdr.model';
import { ITBHDR } from 'app/entities/tbhdr/tbhdr.model';

export interface ITBJNPS {
  jpssts?: string;
  jpscod?: string;
  jpsnam?: string;
  jpslmd?: dayjs.Dayjs;
  jpsuid?: string;
  mFHDRS?: IMFHDR[] | null;
  tBHDRS?: ITBHDR[] | null;
}

export class TBJNPS implements ITBJNPS {
  constructor(
    public jpssts?: string,
    public jpscod?: string,
    public jpsnam?: string,
    public jpslmd?: dayjs.Dayjs,
    public jpsuid?: string,
    public mFHDRS?: IMFHDR[] | null,
    public tBHDRS?: ITBHDR[] | null
  ) {}
}

export function getTBJNPSIdentifier(tBJNPS: ITBJNPS): string | undefined {
  return tBJNPS.jpscod;
}
