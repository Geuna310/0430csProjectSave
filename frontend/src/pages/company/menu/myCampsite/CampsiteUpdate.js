import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

function CampsiteUpdate() {
	const { campsiteNo } = useParams();
	const navigate = useNavigate();
	const [form, setForm] = useState({
		campsiteName: "",
		campsiteLocation: "",
		campsiteDescription: "",
		campsiteImageUrl: "",
		campsitesBusinessNumber: "",
});

	const [loading, setLoading] = useState(true);
	const [error, setError] = useState(null);

	// 캠핑장 정보 불러오기
	useEffect(() => {
		const fetchCampsite = async () => {
			try {
				const response = await axios.get(`/api/campsite/${campsiteNo}`,
					{ headers: { Authorization: `Bearer ${localStorage.getItem("token")}`, },
				});
				setForm(response.data);
				} catch (err) {
					console.error("캠핑장 정보 로딩 실패", err);
					setError("캠핑장 정보를 불러오는 데 실패했습니다.");
				} finally {
					setLoading(false);
				}
											};
		fetchCampsite();
					}, [campsiteNo]);

	const handleChange = (e) => {
		const { name, value } = e.target;
		setForm((prev) => ({ ...prev, [name]: value }));
};

	const handleSubmit = async (e) => {
		e.preventDefault();
		try {
			await axios.put(`/api/campsite/${campsiteNo}`, form, 
				{ headers: { Authorization: `Bearer ${localStorage.getItem("token")}`, }, });
			alert("캠핑장이 성공적으로 수정되었습니다.");
			navigate("/company/menu/myCampsite/MyCampsiteList");
			} catch (err) {
				console.error("캠핑장 수정 실패", err);
				alert("수정에 실패했습니다.");
							}
};

	if (loading) return <p>로딩 중...</p>;
	if (error) return <p style={{ color: "red" }}>{error}</p>;

	return (
		<div style={{ maxWidth: "600px", margin: "0 auto" }}>
			<h2>캠핑장 수정</h2>
			<form onSubmit={handleSubmit}>
				<div>
					<label>캠핑장 이름</label>
					<input type="text" name="campsiteName" value={form.campsiteName} onChange={handleChange} required />
				</div>
				<div>
					<label>주소</label>
					<input type="text" name="campsiteLocation" value={form.campsiteLocation} onChange={handleChange} required />
				</div>
				<div>
					<label>상세설명</label>
					<textarea name="campsiteDescription" value={form.campsiteDescription} onChange={handleChange} required />
				</div>
				<div>
					<label>이미지 URL</label>
					<input type="text" name="campsiteImageUrl" value={form.campsiteImageUrl} onChange={handleChange} />
				</div>
				<div>
					<label>사업자번호</label>
					<input type="text" name="campsitesBusinessNumber" value={form.campsitesBusinessNumber} 
					onChange={handleChange} required />
				</div>
				<button type="submit">수정 완료</button>
			</form>
		</div>
		);
}

export default CampsiteUpdate;