import { IAttachment } from 'app/shared/model/attachment.model';
import { IGolfBag } from 'app/shared/model/golf-bag.model';
import { IClubStats } from 'app/shared/model/club-stats.model';
import { ClubType } from 'app/shared/model/enumerations/club-type.model';

export interface IClubs {
  id?: number;
  clubtype?: ClubType | null;
  attachments?: IAttachment[] | null;
  golfBag?: IGolfBag | null;
  clubStats?: IClubStats | null;
}

export const defaultValue: Readonly<IClubs> = {};
