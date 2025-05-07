import React, { useState } from "react";
import { useNavigate, useLocation } from "react-router-dom";
import axiosInstance from "../../api/axiosInstance";

function LoginPage() {
	const [memberId, setMemberId] = useState("");
	const [memberPassword, setMemberPassword] = useState("");
	const [error, setError] = useState("");
	const navigate = useNavigate();
	const location = useLocation();

	const handleLogin = async (e) => {
		e.preventDefault();
		try {
			const response = await axiosInstance.post("/api/login", {
				memberId, memberPassword,
			});

			const { token, role } = response.data;
			localStorage.setItem("token", token);
			localStorage.setItem("role", role);
			alert("로그인 성공!");

			if (role === "COMPANY") {
				navigate("/company");
			} else { const redirectPath = location.state?.from?.pathname || "/";
				window.location.replace(redirectPath); // 새로고침 포함된 이동
			}
			} catch (err) {
				setError("로그인 실패: " + (err.response?.data?.error || err.message));
							}
};

	return (
	<div style={{ maxWidth: "400px", margin: "0 auto" }}>
		<h2>로그인</h2>
		<form onSubmit={handleLogin}>
		<div>
			<label>ID</label>
			<input type="text" value={memberId} onChange={(e) => setMemberId(e.target.value)} required />
		</div>
		<div>
			<label>PW</label>
			<input type="password" value={memberPassword} onChange={(e) => setMemberPassword(e.target.value)} required />
		</div>
		<button type="submit">Login</button>
		{error && <p style={{ color: "red" }}>{error}</p>}
		</form>
	</div>
			);
}

export default LoginPage;