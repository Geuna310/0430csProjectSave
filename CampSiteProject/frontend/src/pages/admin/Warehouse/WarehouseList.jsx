import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getAllSuppliers } from '../../../api/admin/supplier.js';
import styles from '../../../styles/admin/supplier/SupplierList.module.css';
import { updateSupplier } from '../../../api/admin/supplier.js';

const SupplierList = () => {
  const [suppliers, setSuppliers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [sortColumn, setSortColumn] = useState(null);
  const [sortOrder, setSortOrder] = useState('asc');
  const [openId, setOpenId] = useState(null);
  const [editData, setEditData] = useState({});

  useEffect(() => {
    const fetchSuppliers = async () => {
      try {
        const data = await getAllSuppliers();
        setSuppliers(data);
      } catch (error) {
        console.error("데이터를 불러오는 데 실패했습니다.", error);
      } finally {
        setLoading(false);
      }
    };

    fetchSuppliers();
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

  const sortedSuppliers = [...suppliers].sort((a, b) => {
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
	  const original = suppliers.find(s => s.supplier_id === id);

	  const dataToSend = {
	    supplier_name: editData.supplier_name ?? original.supplier_name,
	    supplier_contact_person: editData.supplier_contact_person ?? original.supplier_contact_person,
	    supplier_contact_phone: editData.supplier_contact_phone ?? original.supplier_contact_phone,
	    supplier_phone: editData.supplier_phone ?? original.supplier_phone,
	    supplier_email: editData.supplier_email ?? original.supplier_email,
	    supplier_address: editData.supplier_address ?? original.supplier_address,
	    supplier_type: editData.supplier_type ?? original.supplier_type,
	    supplier_status: editData.supplier_status ?? original.supplier_status,
	    supplier_bank_account: editData.supplier_bank_account ?? original.supplier_bank_account,
	  };
	  await updateSupplier(id, { ...dataToSend, supplier_id: id });
	

	  const updatedSupplier = {
	    ...original,
	    ...dataToSend,
	  };
	    
	  setSuppliers((prev) =>
	    prev.map((s) => (s.supplier_id === id ? updatedSupplier : s))
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

  const handleKeyDown = (e, supplierId) => {
    if (e.key === 'Enter') {
      e.preventDefault();

      // 모든 입력이 빈 값인지 체크
      const allEmpty = Object.values(editData).every(value => value === '' || value === null);

      if (allEmpty) {
        alert('수정할 내용을 입력해 주세요');
        return; 
      }

      handleUpdate(supplierId); // 하나라도 입력이 있으면 처리
    }
  };




  if (loading) {
    return <div>로딩 중...</div>;
  }

  return (
    <div className={styles.body}>
	
      <h2 style={{ marginTop: '0px' }}>협력사 목록</h2>

	  <div className={styles.menuButton} >
	  	<Link to="/admin/supplier/addition">추가</Link><Link to="/admin/supplier/delete">삭제</Link>
	  </div>
      <div className={styles.supplierTable}>
        <div className={`${styles.row} ${styles.header}`}>
          <div onClick={() => handleSort('supplier_id')} className={`${styles.cell} ${sortColumn === 'supplier_id' ? styles.active : ''}`}>고유번호<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('supplier_name')} className={`${styles.cell} ${sortColumn === 'supplier_name' ? styles.active : ''}`}>협력사 이름<span className={`${styles.TableMenuT}`}>▼</span></div>
		  <div onClick={() => handleSort('supplier_contact_person')} className={`${styles.cell} ${sortColumn === 'supplier_contact_person' ? styles.active : ''}`}>담당자<span className={`${styles.TableMenuT}`}>▼</span></div>
		  <div onClick={() => handleSort('supplier_contact_phone')} className={`${styles.cell} ${sortColumn === 'supplier_contact_phone' ? styles.active : ''}`}>담당자 전화번호<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('supplier_phone')} className={`${styles.cell} ${sortColumn === 'supplier_phone' ? styles.active : ''}`}>협력사 전화번호<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('supplier_email')} className={`${styles.cell} ${sortColumn === 'supplier_email' ? styles.active : ''}`}>이메일<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('supplier_address')} className={`${styles.cell} ${sortColumn === 'supplier_address' ? styles.active : ''}`}>주소<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('supplier_type')} className={`${styles.cell} ${sortColumn === 'supplier_type' ? styles.active : ''}`}>납품 타입<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('supplier_status')} className={`${styles.cell} ${sortColumn === 'supplier_status' ? styles.active : ''}`}>거래 상태<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('supplier_bank_account')} className={`${styles.cell} ${sortColumn === 'supplier_bank_account' ? styles.active : ''}`}>계좌<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('supplier_created_at')} className={`${styles.cell} ${sortColumn === 'supplier_created_at' ? styles.active : ''}`}>등록된 날짜<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('supplier_updated_at')} className={`${styles.cell} ${sortColumn === 'supplier_updated_at' ? styles.active : ''}`}>수정된 날짜<span className={`${styles.TableMenuT}`}>▼</span></div>
        </div>

        {sortedSuppliers.map((supplier) => (
          <div key={supplier.supplier_id}>
            <div className={styles.row} onClick={() => toggleDetails(supplier.supplier_id)}>
              <div className={styles.cell}>{supplier.supplier_id}</div>
              <div className={styles.cell}>{supplier.supplier_name}</div>
			  <div className={styles.cell}>{supplier.supplier_contact_person}</div>
		      <div className={styles.cell}>{supplier.supplier_contact_phone}</div>
              <div className={styles.cell}>{supplier.supplier_phone}</div>
              <div className={styles.cell}>{supplier.supplier_email}</div>
              <div className={styles.cell}>{supplier.supplier_address}</div>
              <div className={styles.cell}>{supplier.supplier_type}</div>
              <div className={styles.cell}>{supplier.supplier_status}</div>
              <div className={styles.cell}>{supplier.supplier_bank_account}</div>
              <div className={styles.cell}>{supplier.supplier_created_at}</div>
              <div className={styles.cell}>{supplier.supplier_updated_at}</div>
            </div>

            {openId === supplier.supplier_id && (
              <div className={`${styles.row}`} style={{ backgroundColor: '#e0e0e0' }}>
                <div className={styles.cell}><div className={styles.reciveButton} onClick={() => handleUpdate(supplier.supplier_id)} disabled={loading}>수정</div></div>

				<div className={styles.cell}>
				  <input
				    type="text"
				    value={editData.supplier_name}
				    onChange={(e) => setEditData({ ...editData, supplier_name: e.target.value })}
				    placeholder={supplier.supplier_name}
					onKeyDown={(e) => handleKeyDown(e, supplier.supplier_id)}
				  />
				</div>

				<div className={styles.cell}>
				  <input
				    type="text"
				    value={editData.supplier_contact_person}
				    onChange={(e) => setEditData({ ...editData, supplier_contact_person: e.target.value })}
				    placeholder={supplier.supplier_contact_person}
					onKeyDown={(e) => handleKeyDown(e, supplier.supplier_id)}
				  />
				</div>

				<div className={styles.cell}>
				  <input
				    type="text"
				    value={editData.supplier_contact_phone}
				    onChange={(e) => setEditData({ ...editData, supplier_contact_phone: e.target.value })}
				    placeholder={supplier.supplier_contact_phone}
					onKeyDown={(e) => handleKeyDown(e, supplier.supplier_id)}
				  />
				</div>
				
				<div className={styles.cell}>
				  <input
				    type="text"
				    value={editData.supplier_phone}
				    onChange={(e) => setEditData({ ...editData, supplier_phone: e.target.value })}
				    placeholder={supplier.supplier_phone}
					onKeyDown={(e) => handleKeyDown(e, supplier.supplier_id)}
				  />
				</div>

				<div className={styles.cell}>
				  <input
				    type="text"
				    value={editData.supplier_email}
				    onChange={(e) => setEditData({ ...editData, supplier_email: e.target.value })}
				    placeholder={supplier.supplier_email}
					onKeyDown={(e) => handleKeyDown(e, supplier.supplier_id)}
				  />
				</div>

				<div className={styles.cell}>
				  <input
				    type="text"
				    value={editData.supplier_address}
				    onChange={(e) => setEditData({ ...editData, supplier_address: e.target.value })}
				    placeholder={supplier.supplier_address}
					onKeyDown={(e) => handleKeyDown(e, supplier.supplier_id)}
				  />
				</div>


				<div className={styles.cell}>
				  <input
				    type="text"
				    value={editData.supplier_type}
				    onChange={(e) => setEditData({ ...editData, supplier_type: e.target.value })}
				    placeholder={supplier.supplier_type}
					onKeyDown={(e) => handleKeyDown(e, supplier.supplier_id)}
				  />
				</div>

				<div className={styles.cell}>
					<select
				  		value={editData.supplier_status ?? supplier.supplier_status}
				  		onChange={(e) => setEditData({ ...editData, supplier_status: e.target.value })}
					>
				  		<option value="활성">활성</option>
				  		<option value="비활성">비활성</option>
					</select>
				</div>

				<div className={styles.cell}>
				  <input
				    type="text"
				    value={editData.supplier_bank_account}
				    onChange={(e) => setEditData({ ...editData, supplier_bank_account: e.target.value })}
				    placeholder={supplier.supplier_bank_account}
					onKeyDown={(e) => handleKeyDown(e, supplier.supplier_id)}
				  />
				</div>
				
                <div className={styles.cell}>{supplier.supplier_created_at}</div>
                <div className={styles.cell}>{supplier.supplier_updated_at}</div>
              </div>
            )}
          </div>
        ))}
      </div>
	  
    </div>
  );
};

export default SupplierList;