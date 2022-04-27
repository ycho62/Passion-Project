import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ClubStats from './club-stats';
import ClubStatsDetail from './club-stats-detail';
import ClubStatsUpdate from './club-stats-update';
import ClubStatsDeleteDialog from './club-stats-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClubStatsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClubStatsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClubStatsDetail} />
      <ErrorBoundaryRoute path={match.url} component={ClubStats} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ClubStatsDeleteDialog} />
  </>
);

export default Routes;
