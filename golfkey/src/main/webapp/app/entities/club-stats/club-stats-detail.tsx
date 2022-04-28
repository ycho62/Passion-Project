import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './club-stats.reducer';

export const ClubStatsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const clubStatsEntity = useAppSelector(state => state.clubStats.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clubStatsDetailsHeading">ClubStats</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{clubStatsEntity.id}</dd>
          <dt>
            <span id="clubDistance">Club Distance</span>
          </dt>
          <dd>{clubStatsEntity.clubDistance}</dd>
          <dt>Club</dt>
          <dd>{clubStatsEntity.club ? clubStatsEntity.club.clubType : ''}</dd>
        </dl>
        <Button tag={Link} to="/club-stats" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/club-stats/${clubStatsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClubStatsDetail;
