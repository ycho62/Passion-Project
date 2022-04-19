import { IClubs } from 'app/shared/model/clubs.model';

export interface IClubStats {
  id?: number;
  bagId?: number | null;
  clubDistance?: string | null;
  comment?: string | null;
  clubs?: IClubs[] | null;
}

export const defaultValue: Readonly<IClubStats> = {};
