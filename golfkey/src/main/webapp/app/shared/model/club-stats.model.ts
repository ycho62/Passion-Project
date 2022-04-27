import { IGolfBag } from 'app/shared/model/golf-bag.model';

export interface IClubStats {
  id?: number;
  clubDistance?: string | null;
  golfBag?: IGolfBag | null;
}

export const defaultValue: Readonly<IClubStats> = {};
