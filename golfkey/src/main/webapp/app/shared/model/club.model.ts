import { IAttachment } from 'app/shared/model/attachment.model';
import { IComment } from 'app/shared/model/comment.model';
import { IClubStats } from 'app/shared/model/club-stats.model';
import { IGolfBag } from 'app/shared/model/golf-bag.model';
import { ClubType } from 'app/shared/model/enumerations/club-type.model';

export interface IClub {
  id?: number;
  clubType?: ClubType | null;
  attachments?: IAttachment[] | null;
  comments?: IComment[] | null;
  clubStats?: IClubStats[] | null;
  golfBag?: IGolfBag | null;
}

export const defaultValue: Readonly<IClub> = {};
