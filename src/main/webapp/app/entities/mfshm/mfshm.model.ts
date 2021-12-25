import * as dayjs from 'dayjs';
import { IMFSKS } from 'app/entities/mfsks/mfsks.model';

export interface IMFSHM {
  id?: number;
  shsts?: string;
  shfr?: number;
  shto?: number;
  shjshm?: number;
  shbat?: number;
  shseq?: number;
  shref?: string;
  shdis?: dayjs.Dayjs;
  shlmd?: dayjs.Dayjs;
  shuid?: string;
  shsks?: IMFSKS | null;
}

export class MFSHM implements IMFSHM {
  constructor(
    public id?: number,
    public shsts?: string,
    public shfr?: number,
    public shto?: number,
    public shjshm?: number,
    public shbat?: number,
    public shseq?: number,
    public shref?: string,
    public shdis?: dayjs.Dayjs,
    public shlmd?: dayjs.Dayjs,
    public shuid?: string,
    public shsks?: IMFSKS | null
  ) {}
}

export function getMFSHMIdentifier(mFSHM: IMFSHM): number | undefined {
  return mFSHM.id;
}
