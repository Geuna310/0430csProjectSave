import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { getCustomerCampsite } from "../../../api/customer/customerCampsiteList";

function CampsiteView() {
	const { no } = useParams();
	const [campsite, setCampsite] = useState(null);
	const [loading, setLoading] = useState(true);
	const [error, setError] = useState(null);

	useEffect(() => {
		const fetchCampsite = async () => {
			if (!no) {
				setError("정확하지 않은 값입니다.");
				setLoading(false);
				return;
			}

			const campsiteNo = Number(no);
			if (isNaN(campsiteNo)) {
				setError("잘못된 값입니다.");
				setLoading(false);
				return;
			}

			try {
				const response = await getCustomerCampsite(campsiteNo);
				console.log("조회 데이터 확인:", response);
				setCampsite(response);
			} catch (err) {
				console.error("캠핑장 조회 실패", err);
				setError("캠핑장 정보를 불러오지 못했습니다.");
			} finally {
				setLoading(false);
			}
		};

		fetchCampsite();
	}, [no]);

	if (loading) return <p>캠핑장 정보를 불러오는 중...</p>;
	if (error) return <p style={{ color: "red" }}>{error}</p>;
	if (!campsite) return <p>캠핑장 정보가 없습니다.</p>;

	return (
		<div style={{ maxWidth: "600px", margin: "0 auto" }}>
			<h2>캠핑장 상세 정보</h2>
			<p><strong>캠핑장 이름:</strong> {campsite.campsiteName}</p>
			<p><strong>주소:</strong> {campsite.campsiteLocation}</p>
			<p><strong>상세설명:</strong> {campsite.campsiteDescription}</p>
			<p><strong>이미지:</strong> {campsite.campsiteImageUrl || "이미지 없음"}</p>
			<p><strong>사업자번호:</strong> {campsite.campsitesBusinessNumber}</p>
		</div>
	);
}

export default CampsiteView;