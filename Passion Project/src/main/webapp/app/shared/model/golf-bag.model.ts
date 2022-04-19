import { IUser } from 'app/shared/model/user.model';
import { IClubs } from 'app/shared/model/clubs.model';

export interface IGolfBag {
  id?: number;
  bagId?: number | null;
  userName?: string | null;
  user?: IUser | null;
  clubs?: IClubs[] | null;
}

export const defaultValue: Readonly<IGolfBag> = {};
