import axiosInstance from '../axiosInstance';

// 캠핑장 등록
export const registerCampsite = async (formData) => {
	const response = await axiosInstance.post('/api/campsite/register', formData);
	return response.data;
};

// 캠핑장 수정
export const updateCampsite = async (campsiteNo, formData) => {
	const response = await axiosInstance.put(`/api/campsite/${campsiteNo}`, formData);
	return response.data;
};

// 캠핑장 삭제
export const deleteCampsite = async (campsiteNo) => {
	const response = await axiosInstance.delete(`/api/campsite/${campsiteNo}`);
	return response.data;
};

// 캠핑장 단건 조회
/*export const getCampsite = async (campsiteNo) => {
	const response = await axiosInstance.get(`/api/campsite/${campsiteNo}`);
	return response.data;
};
*/
// 캠핑장 전체 리스트 조회
export const getCampsiteList = async () => {
	const response = await axiosInstance.get('/api/campsite/list');
	return response.data;
};