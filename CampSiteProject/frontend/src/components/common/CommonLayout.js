import React from "react";
import { Outlet, useLocation } from "react-router-dom";
import CustomerHeader from "../customer/CustomerHeader";
import CompanyHeader from "../company/CompanyHeader";
import CompanyMenu from "../../pages/company/menu/CompanyMenu";
import CustomerMenu from "../../pages/customer/menu/CustomerMenu";
import Footer from "./CommonFooter";

function CommonLayout() {
	const location = useLocation();
	const isCompanyPage = location.pathname.startsWith("/company");

	return (
	<div>
		{/* 고객 or 업체 구분해서 Layout 구성 달라짐 */}
		{isCompanyPage ? <CompanyHeader /> : <CustomerHeader />}
		{isCompanyPage ? <CompanyMenu /> : <CustomerMenu />}

		<main style={{ padding: "2rem" }}>
		<Outlet /> {/* 여기에 각 페이지 컴포넌트가 렌더링됨 */}
		</main>
		<Footer />
	</div>
			);
}

export default CommonLayout;