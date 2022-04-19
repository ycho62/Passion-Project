import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IClubStats } from 'app/shared/model/club-stats.model';
import { getEntities as getClubStats } from 'app/entities/club-stats/club-stats.reducer';
import { IGolfBag } from 'app/shared/model/golf-bag.model';
import { getEntities as getGolfBags } from 'app/entities/golf-bag/golf-bag.reducer';
import { IClubs } from 'app/shared/model/clubs.model';
import { ClubName } from 'app/shared/model/enumerations/club-name.model';
import { getEntity, updateEntity, createEntity, reset } from './clubs.reducer';

export const ClubsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const clubStats = useAppSelector(state => state.clubStats.entities);
  const golfBags = useAppSelector(state => state.golfBag.entities);
  const clubsEntity = useAppSelector(state => state.clubs.entity);
  const loading = useAppSelector(state => state.clubs.loading);
  const updating = useAppSelector(state => state.clubs.updating);
  const updateSuccess = useAppSelector(state => state.clubs.updateSuccess);
  const clubNameValues = Object.keys(ClubName);
  const handleClose = () => {
    props.history.push('/clubs');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getClubStats({}));
    dispatch(getGolfBags({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...clubsEntity,
      ...values,
      clubStats: clubStats.find(it => it.id.toString() === values.clubStats.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          clubname: 'DRIVER',
          ...clubsEntity,
          clubStats: clubsEntity?.clubStats?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="golfKeyApp.clubs.home.createOrEditLabel" data-cy="ClubsCreateUpdateHeading">
            <Translate contentKey="golfKeyApp.clubs.home.createOrEditLabel">Create or edit a Clubs</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="clubs-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('golfKeyApp.clubs.clubname')}
                id="clubs-clubname"
                name="clubname"
                data-cy="clubname"
                type="select"
              >
                {clubNameValues.map(clubName => (
                  <option value={clubName} key={clubName}>
                    {translate('golfKeyApp.ClubName.' + clubName)}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField
                id="clubs-clubStats"
                name="clubStats"
                data-cy="clubStats"
                label={translate('golfKeyApp.clubs.clubStats')}
                type="select"
              >
                <option value="" key="0" />
                {clubStats
                  ? clubStats.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/clubs" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ClubsUpdate;
