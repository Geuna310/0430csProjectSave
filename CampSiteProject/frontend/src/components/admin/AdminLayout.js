import React, { useEffect } from "react";
import { Link, Outlet, useNavigate } from "react-router-dom";
import styles from '../../styles/admin/AdminLayout.module.css';

function AdminLayout() {
  const navigate = useNavigate();

  const handleLogout = () => {
      // 로컬 스토리지에서 토큰 삭제
      localStorage.removeItem('jwtToken');
	  // 페이지 새로 고침 (새로 고침 후 인증 상태를 다시 확인하게 됨)
	  window.location.reload();
  };

  return (
    <div className={styles.layoutReset}>
      <header className={styles.title}>
        <div className={styles.logo}>CS Project</div>
        <div className={styles.titleInput}>
          <input className={styles.searchInput} name="search" />
          <div className={styles.searchIconDiv}>
            <img src="/image/search_icon.png" alt="검색" className={styles.searchIcon} />
          </div>
        </div>
        <div className={styles.login} onClick={handleLogout}>Log Out</div>
      </header>

      <footer className={styles.footer}>
        <div className={styles.footerLeft}>
          <div className={styles.productMenu}>재고관리</div>
          <Link to="/admin/supplier" className={styles.productItem}>협력사 관리</Link>
          <Link to="/admin/product" className={styles.productItem}>상품 관리</Link>
          <Link to="/admin/warehouse" className={styles.productItem}>창고 관리</Link>
          <Link to="/admin/location" className={styles.productItem}>재고 위치 관리</Link>
          <Link to="/admin/inventory" className={styles.productItem}>재고 현황</Link>
          <Link to="/admin/stock_movements" className={styles.productItem}>재고 이동 기록</Link>
        </div>
        <div className={styles.footerRight}>
          <main style={{ padding: "2rem" }}><Outlet /></main>
        </div>
      </footer>
    </div>
  );
}

export default AdminLayout;
