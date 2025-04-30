/*import React from "react";
import { Link } from "react-router-dom";

function CommonLayout({ children }) {
  return (
    <div>
      { 상단 공통 }
      <header style={{ padding: '1rem', background: '#f0f0f0' }}>
        <Link to="/" style={{ marginRight: '1rem' }}>🏠 홈</Link>
        <Link to="/login">🔐 로그인</Link>
      </header>

      { 본문 }
      <main style={{ minHeight: '70vh', padding: '2rem' }}>
        {children}
      </main>

      { 하단 공통 }
      <footer style={{ padding: '1rem', background: '#f0f0f0', marginTop: '2rem' }}>
        <p>회사 주소: 서울시 ...</p>
        <p>전화번호: 010-1234-5678</p>
      </footer>
    </div>
  );
}

export default CommonLayout;*/

import React from "react";
import { Link } from "react-router-dom";

function CommonLayout({ children }) {
  return (
    <div>
      {/* 상단바 */}
      <header style={{ background: "#007bff", padding: "1rem", color: "#fff", display: "flex", justifyContent: "space-between" }}>
        <Link to="/" style={{ color: "#fff", textDecoration: "none", fontWeight: "bold" }}>🏕️ Home</Link>
        <Link to="/login" style={{ color: "#fff", textDecoration: "none" }}>Login</Link>
      </header>

      {/* 본문 */}
      <main style={{ padding: "2rem" }}>
        {children}
      </main>

      {/* 하단 */}
      <footer style={{ background: "#f1f1f1", padding: "1rem", textAlign: "center", marginTop: "2rem" }}>
        <p>📍 회사 주소: 서울특별시 강남구 캠핑로 123</p>
        <p>☎️ 전화번호: 02-123-4567</p>
      </footer>
    </div>
  );
}

export default CommonLayout;