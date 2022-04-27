import { IUser } from 'app/shared/model/user.model';
import { IAttachment } from 'app/shared/model/attachment.model';
import { IComment } from 'app/shared/model/comment.model';
import { IClubStats } from 'app/shared/model/club-stats.model';
import { ClubTypes } from 'app/shared/model/enumerations/club-types.model';

export interface IGolfBag {
  id?: number;
  name?: string;
  clubs?: ClubTypes | null;
  user?: IUser | null;
  attachments?: IAttachment[] | null;
  comments?: IComment[] | null;
  clubStats?: IClubStats[] | null;
}

export const defaultValue: Readonly<IGolfBag> = {};
