import axios from 'axios';

const API_URL = '/api/admin/login';  // 로그인 API URL

// 로그인 처리
export const loginAdmin = async (adminId, adminPassword) => {
  try {
    const response = await axios.post(API_URL, {
      adminId,
      adminPassword,
    });

    // 로그인 후 JWT 토큰을 로컬 스토리지에 저장
    const token = response.data.token;
    localStorage.setItem("jwtToken", token); // 로컬 스토리지에 토큰 저장
    console.log("Logged in, token saved:", token);  // 로그인 후 토큰 확인
    return token; // 로그인 성공 시 토큰 반환
  } catch (error) {
    console.error("로그인 실패", error);
    throw error;  // 로그인 실패 시 에러 반환
  }
};
