import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClubs } from 'app/shared/model/clubs.model';
import { getEntities } from './clubs.reducer';

export const Clubs = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const clubsList = useAppSelector(state => state.clubs.entities);
  const loading = useAppSelector(state => state.clubs.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="clubs-heading" data-cy="ClubsHeading">
        <Translate contentKey="golfKeyApp.clubs.home.title">Clubs</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="golfKeyApp.clubs.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/clubs/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="golfKeyApp.clubs.home.createLabel">Create new Clubs</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {clubsList && clubsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="golfKeyApp.clubs.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="golfKeyApp.clubs.clubtype">Clubtype</Translate>
                </th>
                <th>
                  <Translate contentKey="golfKeyApp.clubs.golfBag">Golf Bag</Translate>
                </th>
                <th>
                  <Translate contentKey="golfKeyApp.clubs.clubStats">Club Stats</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {clubsList.map((clubs, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/clubs/${clubs.id}`} color="link" size="sm">
                      {clubs.id}
                    </Button>
                  </td>
                  <td>
                    <Translate contentKey={`golfKeyApp.ClubType.${clubs.clubtype}`} />
                  </td>
                  <td>{clubs.golfBag ? <Link to={`/golf-bag/${clubs.golfBag.id}`}>{clubs.golfBag.name}</Link> : ''}</td>
                  <td>{clubs.clubStats ? <Link to={`/club-stats/${clubs.clubStats.id}`}>{clubs.clubStats.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/clubs/${clubs.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/clubs/${clubs.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/clubs/${clubs.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="golfKeyApp.clubs.home.notFound">No Clubs found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Clubs;
