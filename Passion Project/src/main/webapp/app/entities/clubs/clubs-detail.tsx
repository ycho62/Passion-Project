import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './clubs.reducer';

export const ClubsDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const clubsEntity = useAppSelector(state => state.clubs.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="clubsDetailsHeading">
          <Translate contentKey="golfKeyApp.clubs.detail.title">Clubs</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{clubsEntity.id}</dd>
          <dt>
            <span id="clubtype">
              <Translate contentKey="golfKeyApp.clubs.clubtype">Clubtype</Translate>
            </span>
          </dt>
          <dd>{clubsEntity.clubtype}</dd>
          <dt>
            <Translate contentKey="golfKeyApp.clubs.golfBag">Golf Bag</Translate>
          </dt>
          <dd>{clubsEntity.golfBag ? clubsEntity.golfBag.name : ''}</dd>
          <dt>
            <Translate contentKey="golfKeyApp.clubs.clubStats">Club Stats</Translate>
          </dt>
          <dd>{clubsEntity.clubStats ? clubsEntity.clubStats.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/clubs" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/clubs/${clubsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClubsDetail;
