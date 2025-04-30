import React, { useState } from 'react';
import styles from '../../styles/admin/AdminLogin.module.css';
import axios from 'axios';

function AdminLogin({ setIsAuthenticated }) {
  const [adminId, setAdminId] = useState('');
  const [adminPassword, setAdminPassword] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post('/api/admin/login', {
        adminId,
        adminPassword,
      });

      const { token } = response.data;
      localStorage.setItem('jwtToken', token);

      setIsAuthenticated(true);  // 로그인 성공 후 인증 상태 변경
    } catch (error) {
      console.error('로그인 실패:', error);
      alert('아이디 또는 비밀번호가 올바르지 않습니다.');
    }
  };

  return (
    <div className={styles.overlay}>
      <div className={styles.loginBox}>
        <h2>관리자 로그인</h2>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder="관리자 ID"
            value={adminId}
            onChange={(e) => setAdminId(e.target.value)}
            required
          />
          <input
            type="password"
            placeholder="비밀번호"
            value={adminPassword}
            onChange={(e) => setAdminPassword(e.target.value)}
            required
          />
          <button type="submit">로그인</button>
        </form>
      </div>
    </div>
  );
}

export default AdminLogin;

