import React, { useState } from "react";
import { Routes, Route, Navigate } from "react-router-dom";

import CustomerRouter from "./customer/CustomerRouter";
import CompanyRouter from "./company/CompanyRouter";
import AdminRouter from "./admin/AdminRouter";
import NotFound from "../pages/common/NotFound";
import AdminLogin from "../pages/admin/AdminLogin";

function AppRouter() {
  const [isAuthenticated, setIsAuthenticated] = useState(
    !!localStorage.getItem("jwtToken")
  );

  return (
    <Routes>
      {/* 고객 페이지 */}
      <Route path="/*" element={<CustomerRouter />} />

      {/* 업체 페이지 */}
      <Route path="/company/*" element={<CompanyRouter />} />

      {/* 관리자 로그인 페이지 */}
      <Route
        path="/adminlogin"
        element={
          !isAuthenticated ? (
            <AdminLogin setIsAuthenticated={setIsAuthenticated} />
          ) : (
            <Navigate to="/admin" />
          )
        }
      />

      {/* 관리자 페이지 */}
      <Route
        path="/admin/*"
        element={<AdminRouter isAuthenticated={isAuthenticated} />}
      />

      {/* 404 */}
      <Route path="*" element={<NotFound />} />
    </Routes>
  );
}

export default AppRouter;