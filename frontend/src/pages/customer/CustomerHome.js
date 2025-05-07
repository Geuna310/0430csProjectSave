import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import MyPageMenu from '../../components/customer/MyPageMenu';
import CampsiteSlider from './list/CampsiteSlider';
import {
  getRecommendedCampsites,
  getUniqueCampsites,
  getBestReviews
} from '../../api/customer/mainPageCampsiteList';

function CustomerHome() {
  const navigate = useNavigate();

  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [isCompanyAccount, setIsCompanyAccount] = useState(false);

  const [recommended, setRecommended] = useState([]);
  const [unique, setUnique] = useState([]);
  const [bestReviews, setBestReviews] = useState([]);

  // 로그인 상태 확인
  useEffect(() => {
    const token = localStorage.getItem('token');
    const role = localStorage.getItem('role');
    setIsLoggedIn(!!token);
    setIsCompanyAccount(role === 'COMPANY');
  }, []);

  // 캠핑장 정보 및 리뷰 슬라이드 데이터 호출
  useEffect(() => {
    getRecommendedCampsites()
      .then(res => setRecommended(res.data))
      .catch(err => console.error(err));
    getUniqueCampsites()
      .then(res => setUnique(res.data))
      .catch(err => console.error(err));
    getBestReviews()
      .then(res => setBestReviews(res.data))
      .catch(err => console.error(err));
  }, []);

  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    setIsLoggedIn(false);
    setIsCompanyAccount(false);
    navigate('/login');
  };

  return (
    <div style={{ padding: '20px' }}>
      <MyPageMenu isLoggedIn={isLoggedIn} onLogout={handleLogout} />
      <h1>고객 메인 페이지</h1>

      {isCompanyAccount && (
        <button onClick={() => navigate('/company')} style={{ marginTop: '20px' }}>
          👉 업체 페이지로 이동
        </button>
      )}

      <CampsiteSlider title="최신 캠핑장" data={recommended} />
      <CampsiteSlider title="이색 캠핑장" data={unique} />
      <CampsiteSlider title="베스트 리뷰" data={bestReviews} />
    </div>
  );
}

export default CustomerHome;
