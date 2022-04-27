import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { openFile, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attachment.reducer';

export const AttachmentDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const attachmentEntity = useAppSelector(state => state.attachment.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attachmentDetailsHeading">Attachment</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">ID</span>
          </dt>
          <dd>{attachmentEntity.id}</dd>
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{attachmentEntity.name}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{attachmentEntity.date ? <TextFormat value={attachmentEntity.date} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="file">File</span>
          </dt>
          <dd>
            {attachmentEntity.file ? (
              <div>
                {attachmentEntity.fileContentType ? (
                  <a onClick={openFile(attachmentEntity.fileContentType, attachmentEntity.file)}>Open&nbsp;</a>
                ) : null}
                <span>
                  {attachmentEntity.fileContentType}, {byteSize(attachmentEntity.file)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>Golf Bag</dt>
          <dd>{attachmentEntity.golfBag ? attachmentEntity.golfBag.clubTypes : ''}</dd>
        </dl>
        <Button tag={Link} to="/attachment" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attachment/${attachmentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttachmentDetail;
