import axios from "axios";

const customerAxios = axios.create({
	baseURL: "http://localhost:8081",
});

export const getCustomerCampsite = async (campsiteNo) => {
	const response = await customerAxios.get(`/api/campsite/${campsiteNo}`);
	return response.data;
};