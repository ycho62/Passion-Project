import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import Clubs from './clubs';
import ClubsDetail from './clubs-detail';
import ClubsUpdate from './clubs-update';
import ClubsDeleteDialog from './clubs-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ClubsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ClubsUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ClubsDetail} />
      <ErrorBoundaryRoute path={match.url} component={Clubs} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ClubsDeleteDialog} />
  </>
);

export default Routes;
