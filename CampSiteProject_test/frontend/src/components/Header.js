import React from 'react';
import { Link } from 'react-router-dom';

function Header() {
  return (
    <nav className="navbar navbar-expand navbar-dark bg-dark px-4">
      <Link className="navbar-brand" to="/">캠핑 플랫폼</Link>
      <div className="navbar-nav">
        <Link className="nav-link" to="/customer">고객</Link>
        <Link className="nav-link" to="/company">업체</Link>
        <Link className="nav-link" to="/admin">관리자</Link>
      </div>
    </nav>
  );
}

export default Header;