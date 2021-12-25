import * as dayjs from 'dayjs';
import { IMFSHM } from 'app/entities/mfshm/mfshm.model';
import { IMAPSKS } from 'app/entities/mapsks/mapsks.model';
import { IMFHDR } from 'app/entities/mfhdr/mfhdr.model';

export interface IMFSKS {
  sksts?: string;
  skno?: number;
  skjsh?: number;
  skbat?: number;
  skseq?: number;
  skref?: string;
  skdis?: dayjs.Dayjs;
  sklmd?: dayjs.Dayjs;
  skuid?: string;
  skfil1?: number | null;
  skfil2?: number | null;
  skfil3?: string | null;
  skfil4?: string | null;
  mfshms?: IMFSHM[] | null;
  mAPSKS?: IMAPSKS[] | null;
  skshdr?: IMFHDR | null;
}

export class MFSKS implements IMFSKS {
  constructor(
    public sksts?: string,
    public skno?: number,
    public skjsh?: number,
    public skbat?: number,
    public skseq?: number,
    public skref?: string,
    public skdis?: dayjs.Dayjs,
    public sklmd?: dayjs.Dayjs,
    public skuid?: string,
    public skfil1?: number | null,
    public skfil2?: number | null,
    public skfil3?: string | null,
    public skfil4?: string | null,
    public mfshms?: IMFSHM[] | null,
    public mAPSKS?: IMAPSKS[] | null,
    public skshdr?: IMFHDR | null
  ) {}
}

export function getMFSKSIdentifier(mFSKS: IMFSKS): number | undefined {
  return mFSKS.skno;
}
