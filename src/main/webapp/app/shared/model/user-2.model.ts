export interface IUser2 {
  id?: number;
  userId?: string;
  userLogin?: string;
  date?: string;
}

export class User2 implements IUser2 {
  constructor(public id?: number, public userId?: string, public userLogin?: string, public date?: string) {}
}
