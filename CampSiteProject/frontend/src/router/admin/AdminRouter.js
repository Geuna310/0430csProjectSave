import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";

import AdminLayout from "../../components/admin/AdminLayout";
import AdminHome from "../../pages/admin/AdminHome";
import SupplierList from "../../pages/admin/supplier/SupplierList";
import SupplierDelete from "../../pages/admin/supplier/SupplierDelete";
import SupplierAddition from "../../pages/admin/supplier/SupplierAddition";
import Product from "../../pages/admin/Product";
import WarehouseList from "../../pages/admin/Warehouse/WarehouseList";
import Location from "../../pages/admin/Location";
import Inventory from "../../pages/admin/Inventory";
import StockMovements from "../../pages/admin/StockMovements";
import NotFound from "../../pages/common/NotFound";

const AdminRouter = ({ isAuthenticated }) => {
  // isAuthenticated가 null이면 로그인 상태를 확인하는 로직을 추가
  if (isAuthenticated === null) {
    // 예를 들어 로컬스토리지에서 직접 인증 상태 확인
    isAuthenticated = localStorage.getItem('jwtToken') ? true : false;
  }

  return (
    <Routes>
      {isAuthenticated ? (
        <Route path="/" element={<AdminLayout />}>
          <Route index element={<AdminHome />} />
          <Route path="supplier" element={<SupplierList />} />
          <Route path="supplier/delete" element={<SupplierDelete />} />
          <Route path="supplier/addition" element={<SupplierAddition />} />
          <Route path="product" element={<Product />} />
          <Route path="warehouse" element={<WarehouseList />} />
          <Route path="location" element={<Location />} />
          <Route path="inventory" element={<Inventory />} />
          <Route path="stock_movements" element={<StockMovements />} />
        </Route>
      ) : (
        <Route path="*" element={<Navigate to="/adminlogin" />} />
      )}

      <Route path="*" element={<NotFound />} />
    </Routes>
  );
};

export default AdminRouter;