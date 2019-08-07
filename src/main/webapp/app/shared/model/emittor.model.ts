import { IStatus } from 'app/shared/model/status.model';
import { IUser2 } from 'app/shared/model/user-2.model';

export interface IEmittor {
  id?: number;
  code?: string;
  companyName?: string;
  companySiret?: string;
  login?: string;
  canEmit?: boolean;
  canEmitSince?: string;
  lastStatus?: IStatus;
  createdBy?: IUser2;
  modifiedBy?: IUser2;
}

export class Emittor implements IEmittor {
  constructor(
    public id?: number,
    public code?: string,
    public companyName?: string,
    public companySiret?: string,
    public login?: string,
    public canEmit?: boolean,
    public canEmitSince?: string,
    public lastStatus?: IStatus,
    public createdBy?: IUser2,
    public modifiedBy?: IUser2
  ) {
    this.canEmit = this.canEmit || false;
  }
}
