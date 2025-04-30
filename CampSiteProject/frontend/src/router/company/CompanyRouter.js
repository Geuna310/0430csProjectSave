import React from "react";
import { Routes, Route } from "react-router-dom";

import CommonLayout from "../../components/common/CommonLayout";
import CompanyHome from "../../pages/company/CompanyHome";
import LoginPage from "../../pages/common/LoginPage";
import CompanyMenuRouter from "./menuBar/CompanyMenuRouter";
import NotFound from "../../pages/common/NotFound";
import ConfirmRoleLogin from "../../components/common/ConfirmRoleLogin";

function CompanyRouter() {
	return (
		<Routes>
			<Route path="/" element={<CommonLayout />}>
				{/* 업체 계정만 접근 가능 */}
				<Route index element={<ConfirmRoleLogin allowedRole="COMPANY"><CompanyHome /></ConfirmRoleLogin>}/>
				<Route path="login" element={<LoginPage />} />
				{/* 업체 계정만 접근 가능 */}
				<Route path="menu/myCampsite/*" element={<ConfirmRoleLogin allowedRole="COMPANY">
					<CompanyMenuRouter /></ConfirmRoleLogin>}
				/>
			</Route>

			<Route path="*" element={<NotFound />} />
		</Routes>
	);
}

export default CompanyRouter;