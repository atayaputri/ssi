import * as dayjs from 'dayjs';
import { ITBJNSHM } from 'app/entities/tbjnshm/tbjnshm.model';

export interface ITBCOMS {
  costs?: string;
  cocode?: string;
  conam?: string;
  cocbei?: string;
  conbei?: string;
  cosat?: string;
  conom?: number;
  coisin?: string;
  conpwp?: string;
  coseri?: string;
  colshm?: number;
  colsks?: number;
  cotshm?: number;
  codshm?: number;
  conote1?: string | null;
  conote2?: string | null;
  conote3?: string | null;
  coskps?: number;
  cothld?: number;
  codir1?: string | null;
  codir2?: string | null;
  codir3?: string | null;
  codir4?: string | null;
  codir5?: string | null;
  colmd?: dayjs.Dayjs;
  couid?: string;
  cojnsh?: ITBJNSHM | null;
}

export class TBCOMS implements ITBCOMS {
  constructor(
    public costs?: string,
    public cocode?: string,
    public conam?: string,
    public cocbei?: string,
    public conbei?: string,
    public cosat?: string,
    public conom?: number,
    public coisin?: string,
    public conpwp?: string,
    public coseri?: string,
    public colshm?: number,
    public colsks?: number,
    public cotshm?: number,
    public codshm?: number,
    public conote1?: string | null,
    public conote2?: string | null,
    public conote3?: string | null,
    public coskps?: number,
    public cothld?: number,
    public codir1?: string | null,
    public codir2?: string | null,
    public codir3?: string | null,
    public codir4?: string | null,
    public codir5?: string | null,
    public colmd?: dayjs.Dayjs,
    public couid?: string,
    public cojnsh?: ITBJNSHM | null
  ) {}
}

export function getTBCOMSIdentifier(tBCOMS: ITBCOMS): string | undefined {
  return tBCOMS.cocode;
}
