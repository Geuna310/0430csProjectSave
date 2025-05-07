import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function MyCampsiteList() {
	const [campsites, setCampsites] = useState([]);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState(null);
	const navigate = useNavigate();

	useEffect(() => {
		const fetchMyCampsites = async () => {
			try {
				const response = await axios.get("/api/campsite/myCampsites", {
				headers: {
				Authorization: `Bearer ${localStorage.getItem("token")}`,
				},
				});
				console.log("내 캠핑장 목록:", response.data);
				setCampsites(response.data);
				} catch (err) {
					console.error("캠핑장 목록 조회 실패", err);
					setError("캠핑장 목록을 불러오지 못했습니다.");
								} finally {
									setLoading(false);
											}
												};

		fetchMyCampsites();
					}, []);

	const handleEdit = (campsiteNo) => {
		navigate(`/company/menu/myCampsite/CampsiteUpdate/${campsiteNo}`);
										};

	const handleView = (campsiteNo) => {
		navigate(`/customer/menu/campsite/${campsiteNo}`);
										};

	const handleDelete = async (campsiteNo) => {
		if (!window.confirm("정말 이 캠핑장을 삭제하시겠습니까?")) {
			return;
															}
		try {
			await axios.delete(`/api/campsite/${campsiteNo}`, {
			headers: {
			Authorization: `Bearer ${localStorage.getItem("token")}`,
			},
																});
			alert("캠핑장이 성공적으로 삭제되었습니다.");
			// 삭제 성공하면 목록 새로고침
			setCampsites(prevCampsites => prevCampsites.filter(c => c.campsiteNo !== campsiteNo));
			} catch (err) {
				console.error("캠핑장 삭제 실패", err);
				alert("캠핑장 삭제에 실패했습니다.");
							}
};

	if (loading) return <p>캠핑장 목록을 불러오는 중...</p>;
	if (error) return <p style={{ color: "red" }}>{error}</p>;

	return (
	<div style={{ maxWidth: "1000px", margin: "0 auto" }}>
		<h2>내 캠핑장 목록</h2>
		{campsites.length === 0 ? (
		<p>등록한 캠핑장이 없습니다.</p>
									) : (
		campsites.map(campsite => (
		<div 
			key={campsite.campsiteNo} style={{ display: "flex", alignItems: "center", justifyContent: "space-between", 
				border: "1px solid #ccc", padding: "20px", marginBottom: "15px", borderRadius: "8px" }} >
            {/* 캠핑장 정보 */}
			<div style={{ flex: 2 }}>
				<p><strong>캠핑장 이름:</strong> {campsite.campsiteName}</p>
				<p><strong>주소:</strong> {campsite.campsiteLocation}</p>
				<p><strong>상세설명:</strong> {campsite.campsiteDescription}</p>
				<p><strong>이미지:</strong> {campsite.campsiteImageUrl || "이미지 없음"}</p>
				<p><strong>사업자번호:</strong> {campsite.campsitesBusinessNumber}</p>
			</div>
			{/* 버튼 그룹 */}
			<div style={{ flex: 0.5, display: "flex", flexDirection: "column", alignItems: "center", gap: "10px" }}>
				<button onClick={() => handleView(campsite.campsiteNo)} style={{ padding: "8px 12px", 
					backgroundColor: "#2196F3", color: "white", border: "none", borderRadius: "5px", cursor: "pointer" }} >
				조회하기
				</button>
				<button  onClick={() => handleEdit(campsite.campsiteNo)} style={{ padding: "8px 12px", 
					backgroundColor: "#4CAF50", color: "white", border: "none", borderRadius: "5px", cursor: "pointer" }} >
				수정하기
				</button>
				<button onClick={() => handleDelete(campsite.campsiteNo)} style={{ padding: "8px 12px", 
					backgroundColor: "#f44336", color: "white", border: "none", borderRadius: "5px", cursor: "pointer" }} >
				삭제하기
				</button>
			</div>
		</div>
									))
									)}
	</div>
	);
}

export default MyCampsiteList;