import React from 'react';
import { Switch } from 'react-router-dom';
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GolfBag from './golf-bag';
import ClubStats from './club-stats';
import Clubs from './clubs';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default ({ match }) => {
  return (
    <div>
      <Switch>
        {/* prettier-ignore */}
        <ErrorBoundaryRoute path={`${match.url}golf-bag`} component={GolfBag} />
        <ErrorBoundaryRoute path={`${match.url}club-stats`} component={ClubStats} />
        <ErrorBoundaryRoute path={`${match.url}clubs`} component={Clubs} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </Switch>
    </div>
  );
};