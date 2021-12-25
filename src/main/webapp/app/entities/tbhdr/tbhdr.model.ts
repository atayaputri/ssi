import * as dayjs from 'dayjs';
import { ITBJNPS } from 'app/entities/tbjnps/tbjnps.model';

export interface ITBHDR {
  thsts?: string;
  thno?: number;
  thsid?: string;
  thnm1?: string;
  thjsh?: number;
  thtax?: number;
  thdis?: dayjs.Dayjs;
  thlmd?: dayjs.Dayjs;
  thuid?: string;
  thfil1?: number | null;
  thfil2?: number | null;
  thfil3?: string | null;
  thfil4?: string | null;
  thjnps?: ITBJNPS | null;
}

export class TBHDR implements ITBHDR {
  constructor(
    public thsts?: string,
    public thno?: number,
    public thsid?: string,
    public thnm1?: string,
    public thjsh?: number,
    public thtax?: number,
    public thdis?: dayjs.Dayjs,
    public thlmd?: dayjs.Dayjs,
    public thuid?: string,
    public thfil1?: number | null,
    public thfil2?: number | null,
    public thfil3?: string | null,
    public thfil4?: string | null,
    public thjnps?: ITBJNPS | null
  ) {}
}

export function getTBHDRIdentifier(tBHDR: ITBHDR): number | undefined {
  return tBHDR.thno;
}
