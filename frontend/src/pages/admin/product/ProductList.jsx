import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getAllProducts, updateProducts } from '../../../api/admin/product';
import styles from '../../../styles/admin/supplier/SupplierList.module.css';

const ProductsList = () => {
  const [products, setProducts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [sortColumn, setSortColumn] = useState(null);
  const [sortOrder, setSortOrder] = useState('asc');
  const [openId, setOpenId] = useState(null);
  const [editData, setEditData] = useState({});

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

  const toggleDetails = (id) => {
    setOpenId(openId === id ? null : id);
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
      return sortOrder === 'asc'
        ? aValue - bValue
        : bValue - aValue;
    }
  });
  
  // 수정 버튼 클릭 시 호출되는 함수
  const handleUpdate = async (id) => {
    try {
      setLoading(true);
	  
	  const allEmpty = Object.values(editData).every(value => value === '' || value === null);

	        if (allEmpty) {
	          alert('수정할 내용을 입력해 주세요');
	          return; 
	        }
      
      // 기존 supplier 데이터 찾기
	  const original = products.find(s => s.product_id === id);

	  const dataToSend = {
	    product_name: editData.product_name ?? original.product_name,
	    product_price: editData.product_price ?? original.supplier_price,
		product_supplier_id: editData.product_supplier_id ?? original.product_supplier_id
	  };
	  await updateProducts(id, { ...dataToSend, product_id: id });
	

	  const updatedProducts = {
	    ...original,
	    ...dataToSend,
	  };
	    
	  setProducts((prev) =>
	    prev.map((p) => (p.product_id === id ? updatedProducts : p))
	  );


	  
      alert("수정 성공");
	  setEditData({});
    } catch (error) {
      console.error("수정 실패:", error);
      alert("수정 실패");
    } finally {
      setLoading(false);
    }
  };

  const handleKeyDown = (e, productId) => {
    if (e.key === 'Enter') {
      e.preventDefault();

      // 모든 입력이 빈 값인지 체크
      const allEmpty = Object.values(editData).every(value => value === '' || value === null);

      if (allEmpty) {
        alert('수정할 내용을 입력해 주세요');
        return; 
      }

      handleUpdate(productId); // 하나라도 입력이 있으면 처리
    }
  };




  if (loading) {
    return <div>로딩 중...</div>;
  }

  return (
    <div className={styles.body}>
	
      <h2 style={{ marginTop: '0px' }}>협력사 목록</h2>

	  <div className={styles.menuButton} >
	  	<Link to="/admin/product/addition">추가</Link><Link to="/admin/product/delete">삭제</Link>
	  </div>
      <div className={styles.supplierTable}>
        <div className={`${styles.row} ${styles.header}`}>
          <div onClick={() => handleSort('product_id')} className={`${styles.cell} ${sortColumn === 'product_id' ? styles.active : ''}`}>번호<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('product_name')} className={`${styles.cell} ${sortColumn === 'product_name' ? styles.active : ''}`}>물건명<span className={`${styles.TableMenuT}`}>▼</span></div>
		  <div onClick={() => handleSort('product_price')} className={`${styles.cell} ${sortColumn === 'product_price' ? styles.active : ''}`}>가격<span className={`${styles.TableMenuT}`}>▼</span></div>
		  <div onClick={() => handleSort('product_supplier_id')} className={`${styles.cell} ${sortColumn === 'product_supplier_id' ? styles.active : ''}`}>협력사<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('supplier_created_at')} className={`${styles.cell} ${sortColumn === 'supplier_created_at' ? styles.active : ''}`}>등록된 날짜<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('supplier_updated_at')} className={`${styles.cell} ${sortColumn === 'supplier_updated_at' ? styles.active : ''}`}>수정된 날짜<span className={`${styles.TableMenuT}`}>▼</span></div>
        </div>

        {sortedProducts.map((Products) => (
          <div key={Products.product_id}>
            <div className={styles.row} onClick={() => toggleDetails(Products.product_id)}>
              <div className={styles.cell}>{Products.product_id}</div>
              <div className={styles.cell}>{Products.product_name}</div>
			  <div className={styles.cell}>{Products.product_price}</div>
		      <div className={styles.cell}>{Products.product_supplier_id}</div>
              <div className={styles.cell}>{Products.supplier_created_at}</div>
              <div className={styles.cell}>{Products.supplier_updated_at}</div>
            </div>

            {openId === Products.product_id && (
              <div className={`${styles.row}`} style={{ backgroundColor: '#e0e0e0' }}>
                <div className={styles.cell}><div className={styles.reciveButton} onClick={() => handleUpdate(Products.product_id)} disabled={loading}>수정</div></div>

				<div className={styles.cell}>
				  <input
				    type="text"
				    value={editData.product_name}
				    onChange={(e) => setEditData({ ...editData, product_name: e.target.value })}
				    placeholder={Products.product_name}
					onKeyDown={(e) => handleKeyDown(e, Products.product_id)}
				  />
				</div>

				<div className={styles.cell}>
				  <input
				    type="number"
				    value={editData.product_price}
				    onChange={(e) => setEditData({ ...editData, product_price: e.target.value })}
				    placeholder={Products.product_price}
					onKeyDown={(e) => handleKeyDown(e, Products.product_id)}
				  />
				</div>

				<div className={styles.cell}>
				  <input
				    type="text"
				    value={editData.product_supplier_id}
				    onChange={(e) => setEditData({ ...editData, product_supplier_id: e.target.value })}
				    placeholder={Products.product_supplier_id}
					onKeyDown={(e) => handleKeyDown(e, Products.product_id)}
				  />
				</div>
                <div className={styles.cell}>{Products.product_created_at}</div>
                <div className={styles.cell}>{Products.product_updated_at}</div>
              </div>
            )}
          </div>
        ))}
      </div>
	  
    </div>
  );
};

export default ProductsList;