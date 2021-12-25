import * as dayjs from 'dayjs';
import { IMFSKS } from 'app/entities/mfsks/mfsks.model';
import { IMAPSKS } from 'app/entities/mapsks/mapsks.model';
import { ITBKAB } from 'app/entities/tbkab/tbkab.model';
import { ITBPROV } from 'app/entities/tbprov/tbprov.model';
import { ITBNEG } from 'app/entities/tbneg/tbneg.model';
import { ITBJNPS } from 'app/entities/tbjnps/tbjnps.model';
import { ITBTYPS } from 'app/entities/tbtyps/tbtyps.model';
import { HolderGroup } from 'app/entities/enumerations/holder-group.model';
import { Citizenship } from 'app/entities/enumerations/citizenship.model';

export interface IMFHDR {
  hdsts?: string;
  hdno?: number;
  hdsid?: string;
  hdnm1?: string;
  hdnm2?: string;
  hdal1?: string;
  hdal2?: string;
  hdjsh?: number;
  hdinco?: HolderGroup;
  hdkwn?: Citizenship;
  hdktp?: string;
  hdnpwp?: string;
  hdsiup?: string;
  hdtax?: number;
  hddis?: dayjs.Dayjs;
  hdlmd?: dayjs.Dayjs;
  hduid?: string;
  mFSKS?: IMFSKS[] | null;
  sksLosts?: IMAPSKS[] | null;
  sksAdds?: IMAPSKS[] | null;
  hdkota?: ITBKAB | null;
  hdprov?: ITBPROV | null;
  hdneg?: ITBNEG | null;
  hdjnps?: ITBJNPS | null;
  hdtyps?: ITBTYPS | null;
}

export class MFHDR implements IMFHDR {
  constructor(
    public hdsts?: string,
    public hdno?: number,
    public hdsid?: string,
    public hdnm1?: string,
    public hdnm2?: string,
    public hdal1?: string,
    public hdal2?: string,
    public hdjsh?: number,
    public hdinco?: HolderGroup,
    public hdkwn?: Citizenship,
    public hdktp?: string,
    public hdnpwp?: string,
    public hdsiup?: string,
    public hdtax?: number,
    public hddis?: dayjs.Dayjs,
    public hdlmd?: dayjs.Dayjs,
    public hduid?: string,
    public mFSKS?: IMFSKS[] | null,
    public sksLosts?: IMAPSKS[] | null,
    public sksAdds?: IMAPSKS[] | null,
    public hdkota?: ITBKAB | null,
    public hdprov?: ITBPROV | null,
    public hdneg?: ITBNEG | null,
    public hdjnps?: ITBJNPS | null,
    public hdtyps?: ITBTYPS | null
  ) {}
}

export function getMFHDRIdentifier(mFHDR: IMFHDR): number | undefined {
  return mFHDR.hdno;
}
