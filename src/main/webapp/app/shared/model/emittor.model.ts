import { IStatus } from 'app/shared/model/status.model';
import { IUserApi } from 'app/shared/model/user-api.model';

export interface IEmittor {
  id?: number;
  code?: string;
  companyName?: string;
  companySiret?: string;
  login?: string;
  canEmit?: boolean;
  canEmitSince?: string;
  lastStatus?: IStatus;
  createdBy?: IUserApi;
  modifiedBy?: IUserApi;
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
    public createdBy?: IUserApi,
    public modifiedBy?: IUserApi
  ) {
    this.canEmit = this.canEmit || false;
  }
}
