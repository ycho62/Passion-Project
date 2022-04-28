import dayjs from 'dayjs';
import { IClub } from 'app/shared/model/club.model';

export interface IComment {
  id?: number;
  date?: string | null;
  text?: string | null;
  club?: IClub | null;
}

export const defaultValue: Readonly<IComment> = {};
