import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { registerMember } from "../../api/member";

function SignupPage() {
	const navigate = useNavigate();
	const [formData, setFormData] = useState({
		memberId: "",
		memberPassword: "",
		memberName: "",
		memberNickname: "",
		memberPhone: "",
		memberEmail: "",
		memberBirth: "",
		memberGender: "",
});
	const [profileImage, setProfileImage] = useState(null);

	const handleChange = (e) => {
	const { name, value } = e.target;
	setFormData((prev) => ({ ...prev, [name]: value }));
};

	const handleFileChange = (e) => {
	setProfileImage(e.target.files[0]);
};

	const handleSubmit = async (e) => {
	e.preventDefault();

	const data = new FormData();

	// JSON 회원 데이터 → Blob으로 감싸서 "member"로 추가
	const memberBlob = new Blob([JSON.stringify(formData)], {
		type: "application/json",
});
	data.append("member", memberBlob);

	// 이미지가 있다면 "imageFile"로 추가
	if (profileImage) {
		data.append("imageFile", profileImage);
}

	try {
		await registerMember(data);
		alert("회원가입 성공!");
		navigate("/");
		}
	catch (err) {
		alert("회원가입 실패: " + (err.response?.data || "서버 오류"));
				}
};

	return (
	<div style={{ maxWidth: "500px", margin: "0 auto" }}>
		<h2>회원가입</h2>
			<form onSubmit={handleSubmit}>
				<label>이름</label>&nbsp;
				<input name="memberName" placeholder="이름" value={formData.memberName} onChange={handleChange} required />
				<label>생년월일</label>&nbsp;
				<input name="memberBirth" placeholder="생년월일" value={formData.memberBirth} onChange={handleChange} required /><br />
				<label>아이디</label>&nbsp;
				<input name="memberId" placeholder="아이디" value={formData.memberId} onChange={handleChange} required />
				<label>비밀번호</label>&nbsp;
				<input name="memberPassword" type="password" placeholder="비밀번호" value={formData.memberPassword} onChange={handleChange} required /><br />
				<label>닉네임</label>&nbsp;
				<input name="memberNickname" placeholder="닉네임" value={formData.memberNickname} onChange={handleChange} required />
				<label>전화번호</label>&nbsp;
				<input name="memberPhone" placeholder="전화번호" value={formData.memberPhone} onChange={handleChange} required /><br />
				<label>이메일</label>&nbsp;
				<input name="memberEmail" placeholder="이메일" value={formData.memberEmail} onChange={handleChange} required />
				<label>성별</label>&nbsp;
				<select name="memberGender" value={formData.memberGender} onChange={handleChange} required>
				<option value="">성별 선택</option>
				<option value="남">남</option>
				<option value="여">여</option>
				</select>
				<div style={{ margin: "10px 0" }}>
					<label>프로필 이미지 (선택): </label>
					<input type="file" accept="image/*" onChange={handleFileChange} />
				</div>
				<button type="submit">가입하기</button>
			</form>
	</div>
			);
}

export default SignupPage;