export const enum StatusEnum {
  ENABLE = 'ENABLE',
  DISABLE = 'DISABLE'
}

export interface IStatus {
  id?: number;
  code?: StatusEnum;
  date?: string;
}

export class Status implements IStatus {
  constructor(public id?: number, public code?: StatusEnum, public date?: string) {}
}
