import * as dayjs from 'dayjs';

export interface ITBCOMFO {
  id?: number;
  costs?: string;
  cocode?: string;
  conam?: string;
  cocbei?: string;
  conbei?: string;
  cosat?: string;
  conom?: number;
  coseri?: string;
  codir?: string;
  colmd?: dayjs.Dayjs;
  couid?: string;
}

export class TBCOMFO implements ITBCOMFO {
  constructor(
    public id?: number,
    public costs?: string,
    public cocode?: string,
    public conam?: string,
    public cocbei?: string,
    public conbei?: string,
    public cosat?: string,
    public conom?: number,
    public coseri?: string,
    public codir?: string,
    public colmd?: dayjs.Dayjs,
    public couid?: string
  ) {}
}

export function getTBCOMFOIdentifier(tBCOMFO: ITBCOMFO): number | undefined {
  return tBCOMFO.id;
}
