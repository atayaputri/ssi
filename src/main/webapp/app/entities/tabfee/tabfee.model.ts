import * as dayjs from 'dayjs';
import { ITABJTRX } from 'app/entities/tabjtrx/tabjtrx.model';

export interface ITABFEE {
  id?: number;
  fests?: string;
  feemt?: string;
  femin?: number;
  femax?: number;
  fefee?: number;
  fediscp?: number;
  fedisc?: number;
  fetax?: number;
  felmd?: dayjs.Dayjs;
  feuid?: string;
  fejns?: ITABJTRX | null;
}

export class TABFEE implements ITABFEE {
  constructor(
    public id?: number,
    public fests?: string,
    public feemt?: string,
    public femin?: number,
    public femax?: number,
    public fefee?: number,
    public fediscp?: number,
    public fedisc?: number,
    public fetax?: number,
    public felmd?: dayjs.Dayjs,
    public feuid?: string,
    public fejns?: ITABJTRX | null
  ) {}
}

export function getTABFEEIdentifier(tABFEE: ITABFEE): number | undefined {
  return tABFEE.id;
}
