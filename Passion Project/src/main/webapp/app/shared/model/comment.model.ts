import dayjs from 'dayjs';
import { IClubs } from 'app/shared/model/clubs.model';

export interface IComment {
  id?: number;
  date?: string | null;
  text?: string | null;
  clubs?: IClubs | null;
}

export const defaultValue: Readonly<IComment> = {};
