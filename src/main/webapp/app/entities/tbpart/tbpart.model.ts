import * as dayjs from 'dayjs';

export interface ITBPART {
  tpasts?: string;
  tpacode?: string;
  tpanam?: string;
  tparek?: string;
  tpadis?: dayjs.Dayjs;
  tpalmd?: dayjs.Dayjs;
  tpauid?: string;
}

export class TBPART implements ITBPART {
  constructor(
    public tpasts?: string,
    public tpacode?: string,
    public tpanam?: string,
    public tparek?: string,
    public tpadis?: dayjs.Dayjs,
    public tpalmd?: dayjs.Dayjs,
    public tpauid?: string
  ) {}
}

export function getTBPARTIdentifier(tBPART: ITBPART): string | undefined {
  return tBPART.tpacode;
}
