import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './golf-bag.reducer';

export const GolfBagDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const golfBagEntity = useAppSelector(state => state.golfBag.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="golfBagDetailsHeading">
          <Translate contentKey="golfKeyApp.golfBag.detail.title">GolfBag</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{golfBagEntity.id}</dd>
          <dt>
            <span id="bagId">
              <Translate contentKey="golfKeyApp.golfBag.bagId">Bag Id</Translate>
            </span>
          </dt>
          <dd>{golfBagEntity.bagId}</dd>
          <dt>
            <span id="userName">
              <Translate contentKey="golfKeyApp.golfBag.userName">User Name</Translate>
            </span>
          </dt>
          <dd>{golfBagEntity.userName}</dd>
          <dt>
            <Translate contentKey="golfKeyApp.golfBag.user">User</Translate>
          </dt>
          <dd>{golfBagEntity.user ? golfBagEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="golfKeyApp.golfBag.clubs">Clubs</Translate>
          </dt>
          <dd>
            {golfBagEntity.clubs
              ? golfBagEntity.clubs.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.id}</a>
                    {golfBagEntity.clubs && i === golfBagEntity.clubs.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/golf-bag" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/golf-bag/${golfBagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default GolfBagDetail;