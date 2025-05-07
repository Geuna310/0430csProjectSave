import React from "react";
import { Routes, Route } from "react-router-dom";

import CampsiteRegister from "../../../pages/company/menu/myCampsite/CampsiteRegister";
import MyCampsiteList from "../../../pages/company/menu/myCampsite/MyCampsiteList";
import CampsiteUpdate from "../../../pages/company/menu/myCampsite/CampsiteUpdate";
import CompanyPrivateRoute from "../../../components/company/CompanyPrivateRoute";
import CampsiteView from "../../../pages/customer/menu/CampsiteView";

function CompanyMenuRouter() {
	return (
		<Routes>
			<Route
				path="CampsiteRegister"
				element={
					<CompanyPrivateRoute>
						<CampsiteRegister />
					</CompanyPrivateRoute>
				}
			/>
			<Route
				path="MyCampsiteList"
				element={
					<CompanyPrivateRoute>
						<MyCampsiteList />
					</CompanyPrivateRoute>
				}
			/>
			<Route
				path="CampsiteUpdate/:campsiteNo"
				element={
					<CompanyPrivateRoute>
						<CampsiteUpdate />
					</CompanyPrivateRoute>
				}
			/>
			<Route path="campsite/:no" element={<CampsiteView />} />
		</Routes>
	);
}

export default CompanyMenuRouter;