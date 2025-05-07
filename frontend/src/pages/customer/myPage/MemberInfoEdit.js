import React, { useState, useEffect } from "react";
import axios from "axios";

function MemberInfoEdit() {
	const [formData, setFormData] = useState({
		memberNickname: "",
		memberEmail: "",
		memberPhone: "",
		memberGender: "",
		memberPassword: "",
												});

	const [profileImage, setProfileImage] = useState(null);

	const memberId = localStorage.getItem("memberId"); // 로그인 시 저장된 ID 사용

	// 기존 회원 정보 불러오기
	useEffect(() => {
		axios.get(`/api/members/${memberId}`).then(res => {
			setFormData({
				memberNickname: res.data.memberNickname || "",
				memberEmail: res.data.memberEmail || "",
				memberPhone: res.data.memberPhone || "",
				memberGender: res.data.memberGender || "",
				memberPassword: "",
						});
															})
		.catch(err => console.error("회원 정보 불러오기 실패:", err));
					}, [memberId]);

	// 입력값 변경 처리
	const handleChange = (e) => {
		const { name, value } = e.target;
		setFormData(prev => ({ ...prev, [name]: value }));
};

	// 프로필 이미지 파일 변경 처리
	const handleImageChange = (e) => {
		setProfileImage(e.target.files[0]);
};

	// 회원 정보 수정 요청
	const handleInfoUpdate = () => {
		axios.put(`/api/memberpage`, formData, {
			headers: {
				Authorization: `Bearer ${localStorage.getItem("token")}`, // 로그인 시 저장된 토큰
						},
												})
		.then(() => {
			alert("회원 정보가 수정되었습니다.");
					})
		.catch(err => {
			console.error(err);
			alert("회원 정보 수정 실패");
						});
};

	// 프로필 이미지 업로드 요청
	const handleImageUpdate = () => {
		const form = new FormData();
		form.append("profileImage", profileImage);
		axios.put("/api/memberpage/profile-image", form, {
			headers: {
				"Content-Type": "multipart/form-data",
				"Authorization": `Bearer ${localStorage.getItem("token")}`, // JWT 토큰 추가
						},
															})
		.then(() => {
			alert("프로필 이미지가 변경되었습니다.");
					})
		.catch(err => {
			console.error(err);
			alert("프로필 이미지 업로드 실패");
						});
};

	return (
		<div style={{ maxWidth: "500px", margin: "2rem auto" }}>
			<h2>회원 정보 수정</h2>
			<div>
				<label>닉네임</label>
				<input type="text" name="memberNickname" value={formData.memberNickname} onChange={handleChange} />
			</div>
			<div>
				<label>이메일</label>
				<input type="email" name="memberEmail" value={formData.memberEmail} onChange={handleChange} />
			</div>
			<div>
				<label>전화번호</label>
				<input type="tel" name="memberPhone" value={formData.memberPhone} onChange={handleChange} />
			</div>
			<div>
				<label>성별</label>
				<select name="memberGender" value={formData.memberGender} onChange={handleChange} >
					<option value="">선택</option>
					<option value="남성">남성</option>
					<option value="여성">여성</option>
				</select>
			</div>
			<div>
				<label>비밀번호 (변경 시 입력)</label>
				<input type="password" name="memberPassword" value={formData.memberPassword} onChange={handleChange} />
			</div>
			<div>
				<label>프로필 이미지</label>
				<input type="file" onChange={handleImageChange} />
				<button onClick={handleImageUpdate}>이미지 변경</button>
			</div>
			<div style={{ marginTop: "1rem" }}>
				<button onClick={handleInfoUpdate}>정보 수정</button>
			</div>
		</div>
			);
}

export default MemberInfoEdit;