import dayjs from 'dayjs';
import { IGolfBag } from 'app/shared/model/golf-bag.model';

export interface IAttachment {
  id?: number;
  name?: string;
  date?: string | null;
  fileContentType?: string | null;
  file?: string | null;
  golfBag?: IGolfBag | null;
}

export const defaultValue: Readonly<IAttachment> = {};
