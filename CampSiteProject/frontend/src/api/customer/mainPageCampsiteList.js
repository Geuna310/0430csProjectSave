// ✅ ✅ 우리가 만든 axiosInstance를 불러오기!
import axiosInstance from '../common/publicAxios';

// 추천 캠핑장 가져오기
export const getRecommendedCampsites = () => {
  return axiosInstance.get('/api/customer/campsites/recommended');
};

// 이색 캠핑장 가져오기
export const getUniqueCampsites = () => {
  return axiosInstance.get('/api/customer/campsites/category/all');
};

// 베스트 리뷰 가져오기
export const getBestReviews = () => {
  return axiosInstance.get("/api/customer/reviews/best");
};
