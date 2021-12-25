import * as dayjs from 'dayjs';

export interface ITBBROKER {
  brsts?: string;
  brcode?: string;
  brnam?: string;
  brlmd?: dayjs.Dayjs;
  bruid?: string;
}

export class TBBROKER implements ITBBROKER {
  constructor(public brsts?: string, public brcode?: string, public brnam?: string, public brlmd?: dayjs.Dayjs, public bruid?: string) {}
}

export function getTBBROKERIdentifier(tBBROKER: ITBBROKER): string | undefined {
  return tBBROKER.brcode;
}
