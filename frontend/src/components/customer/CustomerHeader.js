import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import MyPageMenu from "./MyPageMenu";
import CustomerServiceMenu from "./CustomerServiceMenu";
import "../../styles/CustomerHeader.css";

function CustomerHeader() {
	const navigate = useNavigate();
	const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem("token"));

	const handleLogout = () => {
	// 로그아웃 처리
		localStorage.removeItem("token");
		localStorage.removeItem("role");
		alert("로그아웃 되었습니다.");

	// 상태 변경 후 새로고침
		setIsLoggedIn(false);  // 로그인 상태를 false로 설정

	// 페이지를 새로 고침하도록 처리
		window.location.reload(); // 페이지 새로고침

	// 홈 페이지로 리디렉션
		navigate("/");  // 홈 페이지로 리다이렉트
	};

	return (
		<header className="customer-header">
			<a href="/" className="logo">🏕️ Home</a>
			<input type="text" className="search-bar" placeholder="캠핑장 검색..." />
			<div className="menu-wrapper">
				<MyPageMenu isLoggedIn={isLoggedIn} onLogout={handleLogout} />
				<CustomerServiceMenu />
			</div>
		</header>
			);
}

export default CustomerHeader;