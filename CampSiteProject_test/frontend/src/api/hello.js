import axios from 'axios';

export const getHello = async () => {
  try {
    const response = await axios.get('/api/hello');
    return response.data;
  } catch (error) {
    console.error('API 호출 실패:', error);
    throw error;
  }
};