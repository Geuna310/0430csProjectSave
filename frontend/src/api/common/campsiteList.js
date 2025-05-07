import publicAxios from './publicAxios';

export const getPublicCampsiteList = async () => {
  const response = await publicAxios.get('/api/campsite/list');
  return response.data;
};

export const getCampsite = async (campsiteNo) => {
  const response = await publicAxios.get(`/api/campsite/${campsiteNo}`);
  return response.data;
};