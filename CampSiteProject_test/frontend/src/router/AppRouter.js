/*import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';

import Header from '../components/Header';
import AdminHome from '../pages/admin/AdminHome';
import CompanyHome from '../pages/company/CompanyHome';
import CustomerHome from '../pages/customer/CustomerHome';

function AppRouter() {
  return (
    <Router>
      <Header />
      <div className="container mt-4">
        <Routes>
          <Route path="/admin" element={<AdminHome />} />
          <Route path="/company" element={<CompanyHome />} />
          <Route path="/customer" element={<CustomerHome />} />
          <Route path="/" element={<CustomerHome />} /> { 기본 루트는 고객 페이지 }
        </Routes>
      </div>
    </Router>
  );
}

export default AppRouter;*/

import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AdminHome from "../pages/admin/AdminHome";
import CustomerHome from "../pages/customer/CustomerHome";
import CompanyHome from "../pages/company/CompanyHome";
import CommonLayout from "../pages/common/Common";

function AppRouter() {
  return (
    <Routes>
      <Route
        path="/"
        element={
          <CommonLayout>
            <CustomerHome />
          </CommonLayout>
        }
      />
      <Route
        path="/company"
        element={
          <CommonLayout>
            <CompanyHome />
          </CommonLayout>
        }
      />
      <Route
        path="/admin"
        element={
          <CommonLayout>
            <AdminHome />
          </CommonLayout>
        }
      />
    </Routes>
  );
}

export default AppRouter;