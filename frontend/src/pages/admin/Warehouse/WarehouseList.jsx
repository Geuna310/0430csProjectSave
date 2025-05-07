import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getAllWarehouses, updateWarehouses } from '../../../api/admin/warehouse.js';
import styles from '../../../styles/admin/warehouse/WarehouseList.module.css';

const WarehousesList = () => {
  const [Warehouses, setWarehouses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [sortColumn, setSortColumn] = useState(null);
  const [sortOrder, setSortOrder] = useState('asc');
  const [openId, setOpenId] = useState(null);
  const [editData, setEditData] = useState({});

  useEffect(() => {
    const fetchWarehouses = async () => {
      try {
        const data = await getAllWarehouses();
		console.log("불러온 센터 데이터:", data);  // 구조 확인
        setWarehouses(data);
      } catch (error) {
        console.error("데이터를 불러오는 데 실패했습니다.", error);
      } finally {
        setLoading(false);
      }
    };

    fetchWarehouses();
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

  const sortedwarehouses = [...Warehouses].sort((a, b) => {
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
      
      // 기존 warehouse 데이터 찾기
	  const original = Warehouses.find(w => w.warehouse_id === id);

	  const dataToSend = {
	    warehouse_name: editData.warehouse_name ?? original.warehouse_name,
	    warehouse_location: editData.warehouse_location ?? original.warehouse_location,
	    warehouse_phone: editData.warehouse_phone ?? original.warehouse_phone,
	    warehouse_email: editData.warehouse_email ?? original.warehouse_email
	  };
	  await updateWarehouses(id, { ...dataToSend, warehouse_id: id });
	

	  const updatedWarehouse = {
	    ...original,
	    ...dataToSend,
	  };
	    
	  setWarehouses((prev) =>
	    prev.map((w) => (w.warehouse_id === id ? updatedWarehouse : w))
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

  const handleKeyDown = (e, WarehouseId) => {
    if (e.key === 'Enter') {
      e.preventDefault();

      // 모든 입력이 빈 값인지 체크
      const allEmpty = Object.values(editData).every(value => value === '' || value === null);

      if (allEmpty) {
        alert('수정할 내용을 입력해 주세요');
        return; 
      }

      handleUpdate(WarehouseId); // 하나라도 입력이 있으면 처리
    }
  };




  if (loading) {
    return <div>로딩 중...</div>;
  }

  return (
    <div className={styles.body}>
	
      <h2 style={{ marginTop: '0px' }}>센터 목록</h2>

	  <div className={styles.menuButton} >
	  	<Link to="/admin/warehouse/addition">추가</Link><Link to="/admin/warehouse/delete">삭제</Link>
	  </div>
      <div className={styles.warehouseTable}>
        <div className={`${styles.row} ${styles.header}`}>
          <div onClick={() => handleSort('warehouse_id')} className={`${styles.cell} ${sortColumn === 'warehouse_id' ? styles.active : ''}`}>번호<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('warehouse_name')} className={`${styles.cell} ${sortColumn === 'warehouse_name' ? styles.active : ''}`}>센터명<span className={`${styles.TableMenuT}`}>▼</span></div>
		  <div onClick={() => handleSort('warehouse_location')} className={`${styles.cell} ${sortColumn === 'warehouse_location' ? styles.active : ''}`}>주소<span className={`${styles.TableMenuT}`}>▼</span></div>
		  <div onClick={() => handleSort('warehouse_phone')} className={`${styles.cell} ${sortColumn === 'warehouse_phone' ? styles.active : ''}`}>전화번호<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('warehouse_email')} className={`${styles.cell} ${sortColumn === 'warehouse_email' ? styles.active : ''}`}>이메일<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('warehouse_created_at')} className={`${styles.cell} ${sortColumn === 'warehouse_created_at' ? styles.active : ''}`}>등록된 날짜<span className={`${styles.TableMenuT}`}>▼</span></div>
          <div onClick={() => handleSort('warehouse_updated_at')} className={`${styles.cell} ${sortColumn === 'warehouse_updated_at' ? styles.active : ''}`}>수정된 날짜<span className={`${styles.TableMenuT}`}>▼</span></div>
        </div>

        {sortedwarehouses.map((Warehouse) => (
          <div key={Warehouse.warehouse_id}>
            <div className={styles.row} onClick={() => toggleDetails(Warehouse.warehouse_id)}>
              <div className={styles.cell}>{Warehouse.warehouse_id}</div>
              <div className={styles.cell}>{Warehouse.warehouse_name}</div>
              <div className={styles.cell}>{Warehouse.warehouse_location}</div>
		      <div className={styles.cell}>{Warehouse.warehouse_phone}</div>
              <div className={styles.cell}>{Warehouse.warehouse_email}</div>
              <div className={styles.cell}>{Warehouse.warehouse_created_at}</div>
              <div className={styles.cell}>{Warehouse.warehouse_updated_at}</div>
            </div>

            {openId === Warehouse.warehouse_id && (
              <div className={`${styles.row}`} style={{ backgroundColor: '#e0e0e0' }}>
                <div className={styles.cell}><div className={styles.reciveButton} onClick={() => handleUpdate(Warehouse.warehouse_id)} disabled={loading}>수정</div></div>

				<div className={styles.cell}>
				<input
				  type="text"
				  value={editData.warehouse_name ?? ''}
				  onChange={(e) => setEditData({ ...editData, warehouse_name: e.target.value })}
				  placeholder={Warehouse.warehouse_name}
				  onKeyDown={(e) => handleKeyDown(e, Warehouse.warehouse_id)}
				/>
				</div>

				<div className={styles.cell}>
				<input
				  type="text"
				  value={editData.warehouse_location ?? ''}
				  onChange={(e) => setEditData({ ...editData, warehouse_location: e.target.value })}
				  placeholder={Warehouse.warehouse_location}
				  onKeyDown={(e) => handleKeyDown(e, Warehouse.warehouse_id)}
				/>
				</div>

				<div className={styles.cell}>
				<input
				  type="text"
				  value={editData.warehouse_phone ?? ''}
				  onChange={(e) => setEditData({ ...editData, warehouse_phone: e.target.value })}
				  placeholder={Warehouse.warehouse_phone}
				  onKeyDown={(e) => handleKeyDown(e, Warehouse.warehouse_id)}
				/>
				</div>

				<div className={styles.cell}>
				<input
				  type="text"
				  value={editData.warehouse_email ?? ''}
				  onChange={(e) => setEditData({ ...editData, warehouse_email: e.target.value })}
				  placeholder={Warehouse.warehouse_email}
				  onKeyDown={(e) => handleKeyDown(e, Warehouse.warehouse_id)}
				/>
				</div>

                <div className={styles.cell}>{Warehouse.warehouse_created_at}</div>
                <div className={styles.cell}>{Warehouse.warehouse_updated_at}</div>
              </div>
            )}
          </div>
        ))}
      </div>
	  
    </div>
  );
};

export default WarehousesList;