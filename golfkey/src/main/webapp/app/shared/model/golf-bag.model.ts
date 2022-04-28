import { IUser } from 'app/shared/model/user.model';
import { IClub } from 'app/shared/model/club.model';

export interface IGolfBag {
  id?: number;
  name?: string;
  user?: IUser | null;
  clubs?: IClub[] | null;
}

export const defaultValue: Readonly<IGolfBag> = {};
