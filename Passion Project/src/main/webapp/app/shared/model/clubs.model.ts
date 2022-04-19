import { IClubStats } from 'app/shared/model/club-stats.model';
import { IGolfBag } from 'app/shared/model/golf-bag.model';
import { ClubName } from 'app/shared/model/enumerations/club-name.model';

export interface IClubs {
  id?: number;
  clubname?: ClubName | null;
  clubStats?: IClubStats | null;
  golfBags?: IGolfBag[] | null;
}

export const defaultValue: Readonly<IClubs> = {};
