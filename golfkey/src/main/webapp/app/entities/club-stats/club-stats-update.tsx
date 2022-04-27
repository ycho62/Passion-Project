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
import { IClubStats } from 'app/shared/model/club-stats.model';
import { getEntity, updateEntity, createEntity, reset } from './club-stats.reducer';

export const ClubStatsUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const golfBags = useAppSelector(state => state.golfBag.entities);
  const clubStatsEntity = useAppSelector(state => state.clubStats.entity);
  const loading = useAppSelector(state => state.clubStats.loading);
  const updating = useAppSelector(state => state.clubStats.updating);
  const updateSuccess = useAppSelector(state => state.clubStats.updateSuccess);
  const handleClose = () => {
    props.history.push('/club-stats');
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
      ...clubStatsEntity,
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
          ...clubStatsEntity,
          golfBag: clubStatsEntity?.golfBag?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="golfkeyApp.clubStats.home.createOrEditLabel" data-cy="ClubStatsCreateUpdateHeading">
            Create or edit a ClubStats
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? <ValidatedField name="id" required readOnly id="club-stats-id" label="ID" validate={{ required: true }} /> : null}
              <ValidatedField
                label="Club Distance"
                id="club-stats-clubDistance"
                name="clubDistance"
                data-cy="clubDistance"
                type="text"
                validate={{
                  maxLength: { value: 20, message: 'This field cannot be longer than 20 characters.' },
                }}
              />
              <ValidatedField id="club-stats-golfBag" name="golfBag" data-cy="golfBag" label="Golf Bag" type="select">
                <option value="" key="0" />
                {golfBags
                  ? golfBags.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.clubTypes}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/club-stats" replace color="info">
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

export default ClubStatsUpdate;
