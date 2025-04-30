import axiosInstance from '../admin/util/axiosInstance'; // 수정된 axiosInstance 임포트

// 협력사 정보 가져오기
export const getAllSuppliers = async () => {
  try {
    const response = await axiosInstance.get('/supplier');  // 인증된 요청
    return response.data; // 협력사 목록 반환
  } catch (error) {
    console.error("정보를 가져오는 데 실패했습니다.", error);
    throw error;
  }
};

// 특정 협력사 정보 가져오기
export const getSupplierById = async (id) => {
  try {
    const response = await axiosInstance.get(`/supplier/${id}`);  // 인증된 요청
    return response.data;
  } catch (error) {
    console.error("정보를 가져오는 데 실패했습니다.", error);
    throw error;
  }
};

// 협력사 등록
export const saveSupplier = async (supplier) => {
  try {
    const response = await axiosInstance.post('/supplier', supplier);  // 인증된 요청
    return response.data;
  } catch (error) {
    console.error("정보를 등록하는 데 실패했습니다.", error);
    throw error;
  }
};

// 협력사 정보 수정
export const updateSupplier = async (id, supplier) => {
  if (!id) {
    console.error('ID가 필요합니다.');
    throw new Error('ID가 필요합니다.');
  }

  try {
    const response = await axiosInstance.put(`/supplier/${id}`, supplier);  // 인증된 요청
    return response.data;
  } catch (error) {
    console.error("정보를 수정하는 데 실패했습니다.", error);
    throw error;
  }
};

// 협력사 삭제
export const deleteSupplier = async (id) => {
  try {
    const response = await axiosInstance.delete(`/supplier/${id}`);  // 인증된 요청
    return response.data;
  } catch (error) {
    console.error("정보를 삭제하는 데 실패했습니다.", error);
    throw error;
  }
};
