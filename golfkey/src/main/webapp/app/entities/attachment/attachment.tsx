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
        Attachments
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} /> Refresh List
          </Button>
          <Link to="/attachment/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Attachment
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {attachmentList && attachmentList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Date</th>
                <th>File</th>
                <th>Golf Bag</th>
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
                          <a onClick={openFile(attachment.fileContentType, attachment.file)}>Open &nbsp;</a>
                        ) : null}
                        <span>
                          {attachment.fileContentType}, {byteSize(attachment.file)}
                        </span>
                      </div>
                    ) : null}
                  </td>
                  <td>{attachment.golfBag ? <Link to={`/golf-bag/${attachment.golfBag.id}`}>{attachment.golfBag.clubTypes}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/attachment/${attachment.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`/attachment/${attachment.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`/attachment/${attachment.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Attachments found</div>
        )}
      </div>
    </div>
  );
};

export default Attachment;
