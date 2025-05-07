import React from "react";
import { Link, useNavigate } from "react-router-dom";
import { Outlet } from "react-router-dom"; // Outlet import 추가

function CompanyHeader() {
	const navigate = useNavigate();
	const isLoggedIn = !!localStorage.getItem("token");

	const handleLogout = () => {
		localStorage.removeItem("token");
		localStorage.removeItem("role");
		alert("로그아웃 되었습니다.");
		navigate("/");  // 회사 페이지로 이동
								};

	const handleLoginClick = () => {
		navigate("/company/login");  // 회사 로그인 페이지로 이동
									};

	return (
	<div>
		<header
			style={{
				background: "#28a745",
				padding: "1rem",
				color: "#fff",
				display: "flex",
				justifyContent: "space-between",
				alignItems: "center",
					}}
		>
		<Link to="/company" style={{ color: "#fff", textDecoration: "none", fontWeight: "bold" }}>
			🏢 Company Home
		</Link>

		<div style={{ display: "flex", gap: "1rem" }}>
		{isLoggedIn ? (
			<button onClick={handleLogout} style={{ color: "#fff", background: "none", border: "none" }}>
				Logout
			</button>
						) : (
			<button onClick={handleLoginClick} style={{ color: "#fff", background: "none", border: "none" }}>
				Login
			</button>
						)}
		</div>
		</header>
	</div>
			);
}

export default CompanyHeader;