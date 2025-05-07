import React from "react";
import { Routes, Route } from "react-router-dom";

import CustomerRegister from "../../../pages/customer/menu/CustomerRegister";
import MemberInfoEdit from "../../../pages/customer/myPage/MemberInfoEdit";
import Favorites from "../../../pages/customer/myPage/Favorites";
import History from "../../../pages/customer/myPage/History";
import Reservation from "../../../pages/customer/myPage/Reservation";
import CampsiteView from "../../../pages/customer/menu/CampsiteView";
import CustomerPrivateRoute from "../../../components/customer/CustomerPrivateRoute";
import ConfirmRoleLogin from "../../../components/common/ConfirmRoleLogin";

function CustomerMenuRouter() {
	return (
		<Routes>
			<Route
				path="CustomerRegister"
				element={
					<CustomerPrivateRoute>
						<CustomerRegister />
					</CustomerPrivateRoute>
				}
			/>
			<Route
				path="edit"
				element={
					<CustomerPrivateRoute>
						<MemberInfoEdit />
					</CustomerPrivateRoute>
				}
			/>
			<Route
				path="favorites"
				element={
					<CustomerPrivateRoute>
						<Favorites />
					</CustomerPrivateRoute>
				}
			/>
			<Route
				path="history"
				element={
					<CustomerPrivateRoute>
						<History />
					</CustomerPrivateRoute>
				}
			/>
			<Route
				path="reservation"
				element={
					<CustomerPrivateRoute>
						<Reservation />
					</CustomerPrivateRoute>
				}
			/>
			<Route path="campsite/:no" element={<ConfirmRoleLogin allowedRole={["CUSTOMER", "COMPANY"]} requireLogin={false}>
			<CampsiteView /></ConfirmRoleLogin>} />
		</Routes>
	);
}

export default CustomerMenuRouter;