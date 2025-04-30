import React, { useState } from 'react';
import { registerCampsite } from '../../../../api/company/companyCampsiteList'; // axiosInstance 기반 API 사용

const CampsiteRegister = () => {
	const [formData, setFormData] = useState({
		campsiteName: '',
		campsiteLocation: '',
		campsiteDescription: '',
		campsiteImageUrl: '',
		campsitesBusinessNumber: '',
	});

	const handleChange = (e) => {
		const { name, value } = e.target;
		setFormData(prev => ({ ...prev, [name]: value }));
	};

	const handleSubmit = async (e) => {
		e.preventDefault();

		try {
			const result = await registerCampsite(formData); // token 자동 삽입됨 (axiosInstance 사용 시)
			alert('캠핑장 등록 성공: ' + result);
		} catch (error) {
			console.error('등록 실패:', error);
			alert('등록 실패: ' + (error.response?.data || '서버 오류'));
		}
	};

	return (
		<div style={{ maxWidth: "1000px", margin: "0 auto" }}>
			<h2>캠핑장 등록</h2>
			<form onSubmit={handleSubmit}>
				<div>
					<label htmlFor="campsiteName">캠핑장 이름</label>&nbsp;
					<input id="campsiteName" name="campsiteName" value={formData.campsiteName} onChange={handleChange} required	/>
				</div>
				<div>
					<label htmlFor="campsiteLocation">지역</label>&nbsp;
					<input id="campsiteLocation" name="campsiteLocation" value={formData.campsiteLocation}
					onChange={handleChange} required />
				</div>
				<div>
					<label htmlFor="campsiteDescription">상세 설명</label>&nbsp;
					<input id="campsiteDescription" name="campsiteDescription" value={formData.campsiteDescription}
					onChange={handleChange} required />
				</div>
				<div>
					<label htmlFor="campsiteImageUrl">이미지</label>&nbsp;
					<input id="campsiteImageUrl" name="campsiteImageUrl" value={formData.campsiteImageUrl}
					onChange={handleChange} />
				</div>
				<div>
					<label htmlFor="campsitesBusinessNumber">사업자 번호</label>&nbsp;
					<input id="campsitesBusinessNumber" name="campsitesBusinessNumber" 
					value={formData.campsitesBusinessNumber} onChange={handleChange} required />
				</div>
				<button type="submit">업체 등록</button>
			</form>
		</div>
	);
};

export default CampsiteRegister;