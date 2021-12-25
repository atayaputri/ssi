import * as dayjs from 'dayjs';
import { ITABFEE } from 'app/entities/tabfee/tabfee.model';

export interface ITABJTRX {
  jtsts?: string;
  jtjntx?: string;
  jtdesc?: string | null;
  jtsdes?: string | null;
  jtlmd?: dayjs.Dayjs;
  jtouid?: string;
  tABFEES?: ITABFEE[] | null;
}

export class TABJTRX implements ITABJTRX {
  constructor(
    public jtsts?: string,
    public jtjntx?: string,
    public jtdesc?: string | null,
    public jtsdes?: string | null,
    public jtlmd?: dayjs.Dayjs,
    public jtouid?: string,
    public tABFEES?: ITABFEE[] | null
  ) {}
}

export function getTABJTRXIdentifier(tABJTRX: ITABJTRX): string | undefined {
  return tABJTRX.jtjntx;
}
