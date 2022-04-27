import dayjs from 'dayjs';
import { IGolfBag } from 'app/shared/model/golf-bag.model';

export interface IComment {
  id?: number;
  date?: string | null;
  text?: string | null;
  golfBag?: IGolfBag | null;
}

export const defaultValue: Readonly<IComment> = {};
