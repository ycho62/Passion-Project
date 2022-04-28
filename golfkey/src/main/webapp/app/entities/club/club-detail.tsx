import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './club.reducer';

export const ClubDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const clubEntity = useAppSelector(state => state.club.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clubDetailsHeading">Club</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{clubEntity.id}</dd>
          <dt>
            <span id="clubType">Club Type</span>
          </dt>
          <dd>{clubEntity.clubType}</dd>
          <dt>Golf Bag</dt>
          <dd>{clubEntity.golfBag ? clubEntity.golfBag.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/club" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/club/${clubEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClubDetail;
