import dayjs from 'dayjs';
import { IClubs } from 'app/shared/model/clubs.model';

export interface IAttachment {
  id?: number;
  name?: string;
  date?: string | null;
  fileContentType?: string | null;
  file?: string | null;
  clubs?: IClubs | null;
}

export const defaultValue: Readonly<IAttachment> = {};
