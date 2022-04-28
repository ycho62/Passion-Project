import { IClub } from 'app/shared/model/club.model';

export interface IClubStats {
  id?: number;
  clubDistance?: string | null;
  club?: IClub | null;
}

export const defaultValue: Readonly<IClubStats> = {};
