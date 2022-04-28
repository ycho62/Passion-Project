import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Club from './club';
import GolfBag from './golf-bag';
import ClubStats from './club-stats';
import Comment from './comment';
import Attachment from './attachment';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}club`} component={Club} />
        <ErrorBoundaryRoute path={`${match.url}golf-bag`} component={GolfBag} />
        <ErrorBoundaryRoute path={`${match.url}club-stats`} component={ClubStats} />
        <ErrorBoundaryRoute path={`${match.url}comment`} component={Comment} />
        <ErrorBoundaryRoute path={`${match.url}attachment`} component={Attachment} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};
