import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IGolfBag } from 'app/shared/model/golf-bag.model';
import { getEntities as getGolfBags } from 'app/entities/golf-bag/golf-bag.reducer';
import { IClub } from 'app/shared/model/club.model';
import { ClubType } from 'app/shared/model/enumerations/club-type.model';
import { getEntity, updateEntity, createEntity, reset } from './club.reducer';

export const ClubUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const golfBags = useAppSelector(state => state.golfBag.entities);
  const clubEntity = useAppSelector(state => state.club.entity);
  const loading = useAppSelector(state => state.club.loading);
  const updating = useAppSelector(state => state.club.updating);
  const updateSuccess = useAppSelector(state => state.club.updateSuccess);
  const clubTypeValues = Object.keys(ClubType);
  const handleClose = () => {
    props.history.push('/club');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getGolfBags({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...clubEntity,
      ...values,
      golfBag: golfBags.find(it => it.id.toString() === values.golfBag.toString()),
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
          clubType: 'DRIVER',
          ...clubEntity,
          golfBag: clubEntity?.golfBag?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="golfKeyApp.club.home.createOrEditLabel" data-cy="ClubCreateUpdateHeading">
            Create or edit a Club
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="club-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField label="Club Type" id="club-clubType" name="clubType" data-cy="clubType" type="select">
                {clubTypeValues.map(clubType => (
                  <option value={clubType} key={clubType}>
                    {clubType}
                  </option>
                ))}
              </ValidatedField>
              <ValidatedField id="club-golfBag" name="golfBag" data-cy="golfBag" label="Golf Bag" type="select">
                <option value="" key="0" />
                {golfBags
                  ? golfBags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.name}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/club" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default ClubUpdate;
