import dayjs from 'dayjs';
import { IClub } from 'app/shared/model/club.model';

export interface IAttachment {
  id?: number;
  name?: string;
  date?: string | null;
  fileContentType?: string | null;
  file?: string | null;
  club?: IClub | null;
}

export const defaultValue: Readonly<IAttachment> = {};
