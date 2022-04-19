import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { IClubs } from 'app/shared/model/clubs.model';
import { getEntities as getClubs } from 'app/entities/clubs/clubs.reducer';
import { IGolfBag } from 'app/shared/model/golf-bag.model';
import { getEntity, updateEntity, createEntity, reset } from './golf-bag.reducer';

export const GolfBagUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const users = useAppSelector(state => state.userManagement.users);
  const clubs = useAppSelector(state => state.clubs.entities);
  const golfBagEntity = useAppSelector(state => state.golfBag.entity);
  const loading = useAppSelector(state => state.golfBag.loading);
  const updating = useAppSelector(state => state.golfBag.updating);
  const updateSuccess = useAppSelector(state => state.golfBag.updateSuccess);
  const handleClose = () => {
    props.history.push('/golf-bag');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getUsers({}));
    dispatch(getClubs({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...golfBagEntity,
      ...values,
      clubs: mapIdList(values.clubs),
      user: users.find(it => it.id.toString() === values.user.toString()),
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
          ...golfBagEntity,
          user: golfBagEntity?.user?.id,
          clubs: golfBagEntity?.clubs?.map(e => e.id.toString()),
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="golfKeyApp.golfBag.home.createOrEditLabel" data-cy="GolfBagCreateUpdateHeading">
            <Translate contentKey="golfKeyApp.golfBag.home.createOrEditLabel">Create or edit a GolfBag</Translate>
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
                  id="golf-bag-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField label={translate('golfKeyApp.golfBag.bagId')} id="golf-bag-bagId" name="bagId" data-cy="bagId" type="text" />
              <ValidatedField
                label={translate('golfKeyApp.golfBag.userName')}
                id="golf-bag-userName"
                name="userName"
                data-cy="userName"
                type="text"
              />
              <ValidatedField id="golf-bag-user" name="user" data-cy="user" label={translate('golfKeyApp.golfBag.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                label={translate('golfKeyApp.golfBag.clubs')}
                id="golf-bag-clubs"
                data-cy="clubs"
                type="select"
                multiple
                name="clubs"
              >
                <option value="" key="0" />
                {clubs
                  ? clubs.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/golf-bag" replace color="info">
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

export default GolfBagUpdate;
