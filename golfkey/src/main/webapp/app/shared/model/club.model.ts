import { IGolfBag } from 'app/shared/model/golf-bag.model';
import { ClubType } from 'app/shared/model/enumerations/club-type.model';

export interface IClub {
  id?: number;
  clubType?: ClubType | null;
  golfBag?: IGolfBag | null;
}

export const defaultValue: Readonly<IClub> = {};
