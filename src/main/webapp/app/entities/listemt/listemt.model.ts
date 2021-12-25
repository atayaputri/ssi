export interface ILISTEMT {
  id?: number;
  liscode?: string;
  lisnam?: string;
  lisdir?: string;
}

export class LISTEMT implements ILISTEMT {
  constructor(public id?: number, public liscode?: string, public lisnam?: string, public lisdir?: string) {}
}

export function getLISTEMTIdentifier(lISTEMT: ILISTEMT): number | undefined {
  return lISTEMT.id;
}
