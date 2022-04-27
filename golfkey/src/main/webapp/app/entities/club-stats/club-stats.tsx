import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClubStats } from 'app/shared/model/club-stats.model';
import { getEntities } from './club-stats.reducer';

export const ClubStats = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const clubStatsList = useAppSelector(state => state.clubStats.entities);
  const loading = useAppSelector(state => state.clubStats.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="club-stats-heading" data-cy="ClubStatsHeading">
        Club Stats
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/club-stats/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Club Stats
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {clubStatsList && clubStatsList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Club Distance</th>
                <th>Golf Bag</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {clubStatsList.map((clubStats, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/club-stats/${clubStats.id}`} color="link" size="sm">
                      {clubStats.id}
                    </Button>
                  </td>
                  <td>{clubStats.clubDistance}</td>
                  <td>{clubStats.golfBag ? <Link to={`/golf-bag/${clubStats.golfBag.id}`}>{clubStats.golfBag.clubTypes}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/club-stats/${clubStats.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/club-stats/${clubStats.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/club-stats/${clubStats.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Club Stats found</div>
        )}
      </div>
    </div>
  );
};

export default ClubStats;
