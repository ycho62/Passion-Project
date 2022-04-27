import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IAttachment } from 'app/shared/model/attachment.model';
import { getEntities } from './attachment.reducer';

export const Attachment = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const attachmentList = useAppSelector(state => state.attachment.entities);
  const loading = useAppSelector(state => state.attachment.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="attachment-heading" data-cy="AttachmentHeading">
        <Translate contentKey="golfKeyApp.attachment.home.title">Attachments</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="golfKeyApp.attachment.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/attachment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="golfKeyApp.attachment.home.createLabel">Create new Attachment</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {attachmentList && attachmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="golfKeyApp.attachment.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="golfKeyApp.attachment.name">Name</Translate>
                </th>
                <th>
                  <Translate contentKey="golfKeyApp.attachment.date">Date</Translate>
                </th>
                <th>
                  <Translate contentKey="golfKeyApp.attachment.file">File</Translate>
                </th>
                <th>
                  <Translate contentKey="golfKeyApp.attachment.clubs">Clubs</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {attachmentList.map((attachment, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/attachment/${attachment.id}`} color="link" size="sm">
                      {attachment.id}
                    </Button>
                  </td>
                  <td>{attachment.name}</td>
                  <td>{attachment.date ? <TextFormat type="date" value={attachment.date} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>
                    {attachment.file ? (
                      <div>
                        {attachment.fileContentType ? (
                          <a onClick={openFile(attachment.fileContentType, attachment.file)}>
                            <Translate contentKey="entity.action.open">Open</Translate>
                            &nbsp;
                          </a>
                        ) : null}
                        <span>
                          {attachment.fileContentType}, {byteSize(attachment.file)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{attachment.clubs ? <Link to={`/clubs/${attachment.clubs.id}`}>{attachment.clubs.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/attachment/${attachment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/attachment/${attachment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`/attachment/${attachment.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="golfKeyApp.attachment.home.notFound">No Attachments found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default Attachment;
