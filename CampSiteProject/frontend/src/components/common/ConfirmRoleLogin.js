import React from "react";
import { Navigate } from "react-router-dom";

function ConfirmRoleLogin({ children, allowedRole, requireLogin = true }) {
	const token = localStorage.getItem("token");
	const role = localStorage.getItem("role");

	// 로그인 여부 체크 (requireLogin이 true일 때만 로그인 여부를 체크)
	if (requireLogin && !token) {
		return <Navigate to="/login" replace />;
	}

	// 로그인한 경우만 role 체크
	if (token && allowedRole) {
		if (allowedRole.includes(role)) {
			return children;
		} else if (role === "COMPANY") {
			return children;
		} else {
			return <Navigate to="/" replace />;
		}
	}

	// 로그인 안 했는데 requireLogin=false 이고, allowedRole 있을 때는 그냥 통과
	return children;
}

export default ConfirmRoleLogin;