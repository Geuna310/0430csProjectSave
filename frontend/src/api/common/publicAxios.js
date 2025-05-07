import axios from 'axios';

const publicAxios = axios.create({
  baseURL: 'http://localhost:8081',
  headers: {
    'Content-Type': 'application/json',
  },
});

// 필요하면 에러 핸들링도 추가 가능
publicAxios.interceptors.response.use(
  (response) => response,
  (error) => {
    console.error('공개 API 호출 에러:', error);
    return Promise.reject(error);
  }
);

export default publicAxios;