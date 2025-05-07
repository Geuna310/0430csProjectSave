import React from "react";
import { Navigate, useLocation } from "react-router-dom";

function CustomerPrivateRoute({ children }) {
	const token = localStorage.getItem("token");
	const location = useLocation();

	if (!token) {
		return (
			<Navigate to="/customer/login" replace state={{ from: location.pathname }}/>
				);
				}

	return children;
}

export default CustomerPrivateRoute;