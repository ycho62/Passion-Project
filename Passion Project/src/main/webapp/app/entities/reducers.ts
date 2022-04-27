import golfBag from 'app/entities/golf-bag/golf-bag.reducer';
import clubStats from 'app/entities/club-stats/club-stats.reducer';
import clubs from 'app/entities/clubs/clubs.reducer';
import comment from 'app/entities/comment/comment.reducer';
import attachment from 'app/entities/attachment/attachment.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  golfBag,
  clubStats,
  clubs,
  comment,
  attachment,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
