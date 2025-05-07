import React, { useEffect, useState } from 'react';
import { getAllProducts, deleteProducts } from '../../../api/admin/product';
import styles from '../../../styles/admin/supplier/SupplierDelete.module.css';
import { useNavigate, Link } from 'react-router-dom';

const ProductsDelete = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [sortColumn, setSortColumn] = useState(null);
  const [sortOrder, setSortOrder] = useState('asc');
  const [selectedIds, setSelectedIds] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchProducts = async () => {
      try {
        const data = await getAllProducts();
        setProducts(data);
      } catch (error) {
        console.error("협력사 데이터를 불러오는 데 실패했습니다.", error);
      } finally {
        setLoading(false);
      }
    };

    fetchProducts();
  }, []);

  const handleSort = (column) => {
    if (column === sortColumn) {
      setSortOrder(sortOrder === 'asc' ? 'desc' : 'asc');
    } else {
      setSortColumn(column);
      setSortOrder('asc');
    }
  };

  const handleCheckboxChange = (e, id) => {
    const isChecked = e.target.checked;
    setSelectedIds((prev) =>
      isChecked ? [...prev, id] : prev.filter((item) => item !== id)
    );
  };

  const toggleCheckboxByCellClick = (id) => {
    const isChecked = selectedIds.includes(id);
    const fakeEvent = { target: { checked: !isChecked } };
    handleCheckboxChange(fakeEvent, id);
  };

  const handleDelete = async () => {
    if (selectedIds.length === 0) {
      alert('삭제할 협력사를 선택해주세요.');
      return;
    }

    if (!window.confirm('정말 삭제하시겠습니까?')) {
      return;
    }

    try {
      await Promise.all(selectedIds.map((id) => deleteProducts(id)));
      setSelectedIds([]);
      const data = await getAllProducts();
      setProducts(data);
      alert('선택된 협력사들이 삭제되었습니다.');
      navigate('/admin/supplier');
    } catch (error) {
      console.error('삭제 중 오류가 발생했습니다.', error);
      alert('삭제 실패!');
    }
  };

  const sortedProducts = [...products].sort((a, b) => {
    if (!sortColumn) return 0;
    const aValue = a[sortColumn];
    const bValue = b[sortColumn];

    if (typeof aValue === 'string') {
      return sortOrder === 'asc'
        ? aValue.localeCompare(bValue)
        : bValue.localeCompare(aValue);
    } else {
      return sortOrder === 'asc' ? aValue - bValue : bValue - aValue;
    }
  });

  if (loading) {
    return <div>로딩 중...</div>;
  }

  return (
    <div className={styles.body}>
      <h2 style={{ marginTop: '0px' }}>협력사 목록</h2>
	  <div className={styles.menuButton}>
	    <Link to="/admin/product">돌아가기</Link>
	    <div onClick={handleDelete}>삭제</div>
	  </div>
	  

	  <div className={styles.supplierTable}>
	    <div className={`${styles.row} ${styles.header}`}>
	      <div onClick={() => handleSort('product_id')} className={`${styles.cell} ${sortColumn === 'product_id' ? styles.active : ''}`}>번호<span className={`${styles.TableMenuT}`}>▼</span></div>
	      <div onClick={() => handleSort('product_name')} className={`${styles.cell} ${sortColumn === 'product_name' ? styles.active : ''}`}>물건명<span className={`${styles.TableMenuT}`}>▼</span></div>
	    <div onClick={() => handleSort('product_price')} className={`${styles.cell} ${sortColumn === 'product_price' ? styles.active : ''}`}>가격<span className={`${styles.TableMenuT}`}>▼</span></div>
	    <div onClick={() => handleSort('product_supplier_id')} className={`${styles.cell} ${sortColumn === 'product_supplier_id' ? styles.active : ''}`}>협력사<span className={`${styles.TableMenuT}`}>▼</span></div>
	      <div onClick={() => handleSort('product_created_at')} className={`${styles.cell} ${sortColumn === 'product_created_at' ? styles.active : ''}`}>등록된 날짜<span className={`${styles.TableMenuT}`}>▼</span></div>
	      <div onClick={() => handleSort('product_updated_at')} className={`${styles.cell} ${sortColumn === 'product_updated_at' ? styles.active : ''}`}>수정된 날짜<span className={`${styles.TableMenuT}`}>▼</span></div>
	    </div>

        {sortedProducts.map((Products) => (
			<div
			  key={Products.product_id}
			  className={`${styles.row}`}
			>
			  <div
			    className={`${styles.cell} ${styles.checkCell} ${
			      selectedIds.includes(Products.supplier_id) ? styles.selectedRow : ''
			    }`}
			    onClick={() => toggleCheckboxByCellClick(Products.product_id)}
			  >
			    <input
			      type="checkbox"
			      checked={selectedIds.includes(Products.product_id)}
			      readOnly
			      className={styles.hiddenCheckbox}
			    />
			    <span className={styles.customCheckbox}></span>
			  </div>
            <div className={styles.cell}>{Products.product_id}</div>
            <div className={styles.cell}>{Products.product_name}</div>
            <div className={styles.cell}>{Products.product_price}</div>
            <div className={styles.cell}>{Products.product_supplier_id}</div>
            <div className={styles.cell}>{Products.product_created_at}</div>
            <div className={styles.cell}>{Products.product_updated_at}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default ProductsDelete;

