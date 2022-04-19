import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import GolfBag from './golf-bag';
import GolfBagDetail from './golf-bag-detail';
import GolfBagUpdate from './golf-bag-update';
import GolfBagDeleteDialog from './golf-bag-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={GolfBagUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={GolfBagUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={GolfBagDetail} />
      <ErrorBoundaryRoute path={match.url} component={GolfBag} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={GolfBagDeleteDialog} />
  </>
);

export default Routes;
