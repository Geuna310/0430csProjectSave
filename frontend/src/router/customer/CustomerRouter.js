import React from "react";
import { Routes, Route } from "react-router-dom";

import CommonLayout from "../../components/common/CommonLayout";
import CustomerHome from "../../pages/customer/CustomerHome";
import LoginPage from "../../pages/common/LoginPage";
import SignupPage from "../../pages/customer/SignupPage";
import CustomerMenuRouter from "./menuBar/CustomerMenuRouter";
import NotFound from "../../pages/common/NotFound";

function CustomerRouter() {
	return (
		<Routes>
			<Route path="/" element={<CommonLayout />}>
				<Route index element={<CustomerHome />} />
				<Route path="login" element={<LoginPage />} />
				<Route path="signup" element={<SignupPage />} />
				<Route path="customer/menu/*" element={<CustomerMenuRouter />}
				/>
			</Route>

			<Route path="*" element={<NotFound />} />
		</Routes>
	);
}

export default CustomerRouter;