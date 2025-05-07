import axios from "axios";

// 기본 URL 설정
const axiosInstance = axios.create({
  baseURL: 'http://localhost:3000/api/admin',  // 실제 Spring Boot API 주소
});

// 데이터 요청 시 Authorization 헤더에 JWT 토큰 추가
axiosInstance.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('jwtToken'); // 로컬 스토리지에서 토큰 가져오기
    if (token) {
      config.headers['Authorization'] = `Bearer ${token}`; // 헤더에 토큰 추가
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axiosInstance;
