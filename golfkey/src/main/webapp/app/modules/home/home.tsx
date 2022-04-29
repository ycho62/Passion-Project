import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';

import { Row, Col, Alert} from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <Row>
      <Col md="3" className="pad">
        <span className="hipster rounded" />
      </Col>
      <Col md="9">
      <p></p>
      <p></p>
      <p></p>
        <h2>Welcome {account.login}, to Golf Key!</h2>
        <p className="lead">Analyze and record your swings to unlock your best game!</p>

        {account?.login ? (
          <div>
            <Alert color="success">You are logged in as user {account.login}.</Alert>
          </div>
        ) : (
          <div>
            <Alert color="warning">
              If you want to
              <span>&nbsp;</span>
              <Link to="/login" className="alert-link">
                {' '}
                sign in
              </Link>
              , you can try the default accounts:
              <br />- Administrator (login=&quot;admin&quot; and password=&quot;admin&quot;)
              <br />- User (login=&quot;user&quot; and password=&quot;user&quot;).
            </Alert>

            <Alert color="warning">
              You do not have an account yet?&nbsp;
              <Link to="/account/register" className="alert-link">
                Register a new account
              </Link>
            </Alert>
          </div>
        )}
        <Col>
          <Link to ='/golf-bag'>
            <button className="btn btn-primary justify-content-center">All Golf Bags</button>
          </Link>
        </Col>
<div className="space">
  </div>
        <Col>
          <Link to ='/club'>
            <button className="btn btn-primary justify-content-center">All Clubs</button>
          </Link>
        </Col>
        <div className="space">
        </div>
        <Col>
          <Link to ='/club-stats'>
            <button className="btn btn-primary justify-content-center">Add Club Distances</button>
          </Link>
        </Col>
        <div className="space">
        </div>
        <Col>
          <Link to ='/comment'>
            <button className="btn btn-primary justify-content-center">Comments</button>
          </Link>
        </Col>
        <div className="space">
          </div>
        <Col>
          <Link to ='/attachment'>
            <button className="btn btn-primary justify-content-center">Attach video to your clubs</button>
          </Link>
        </Col>
      </Col>

    </Row>

  );
};

export default Home;
