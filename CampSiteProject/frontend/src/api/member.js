import axiosInstance from "./axiosInstance.js";

export const registerMember = async (formData) => {
	const response = await axiosInstance.post("/api/register", formData, {
		headers: {
			"Content-Type": "multipart/form-data" // 파일 업로드 하려면 multipart/form-data 사용해야함 + JSON 사용 가능,
				},
																			});
	return response.data;
};