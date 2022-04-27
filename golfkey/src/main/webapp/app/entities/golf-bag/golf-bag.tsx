import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGolfBag } from 'app/shared/model/golf-bag.model';
import { getEntities } from './golf-bag.reducer';

export const GolfBag = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const golfBagList = useAppSelector(state => state.golfBag.entities);
  const loading = useAppSelector(state => state.golfBag.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="golf-bag-heading" data-cy="GolfBagHeading">
        Golf Bags
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/golf-bag/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Golf Bag
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {golfBagList && golfBagList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Clubs</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {golfBagList.map((golfBag, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/golf-bag/${golfBag.id}`} color="link" size="sm">
                      {golfBag.id}
                    </Button>
                  </td>
                  <td>{golfBag.name}</td>
                  <td>{golfBag.clubs}</td>
                  <td>{golfBag.user ? golfBag.user.login : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/golf-bag/${golfBag.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/golf-bag/${golfBag.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/golf-bag/${golfBag.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Golf Bags found</div>
        )}
      </div>
    </div>
  );
};

export default GolfBag;
