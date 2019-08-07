export interface IUserApi {
  id?: number;
  userId?: string;
  userLogin?: string;
  date?: string;
}

export class UserApi implements IUserApi {
  constructor(public id?: number, public userId?: string, public userLogin?: string, public date?: string) {}
}
