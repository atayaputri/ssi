import { IMFSKS } from 'app/entities/mfsks/mfsks.model';
import { IMFHDR } from 'app/entities/mfhdr/mfhdr.model';
import { StatusSKS } from 'app/entities/enumerations/status-sks.model';

export interface IMAPSKS {
  id?: number;
  msksts?: StatusSKS;
  mskno?: IMFSKS | null;
  mskohdr?: IMFHDR | null;
  mskhdr?: IMFHDR | null;
}

export class MAPSKS implements IMAPSKS {
  constructor(
    public id?: number,
    public msksts?: StatusSKS,
    public mskno?: IMFSKS | null,
    public mskohdr?: IMFHDR | null,
    public mskhdr?: IMFHDR | null
  ) {}
}

export function getMAPSKSIdentifier(mAPSKS: IMAPSKS): number | undefined {
  return mAPSKS.id;
}
