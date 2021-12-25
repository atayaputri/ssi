import * as dayjs from 'dayjs';
import { ITBCOMS } from 'app/entities/tbcoms/tbcoms.model';

export interface ITBJNSHM {
  jshsts?: string;
  jshcod?: string;
  jshnam?: string;
  jshlmd?: dayjs.Dayjs;
  jshuid?: string;
  tBCOMS?: ITBCOMS[] | null;
}

export class TBJNSHM implements ITBJNSHM {
  constructor(
    public jshsts?: string,
    public jshcod?: string,
    public jshnam?: string,
    public jshlmd?: dayjs.Dayjs,
    public jshuid?: string,
    public tBCOMS?: ITBCOMS[] | null
  ) {}
}

export function getTBJNSHMIdentifier(tBJNSHM: ITBJNSHM): string | undefined {
  return tBJNSHM.jshcod;
}
