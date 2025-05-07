import React, { useEffect, useState } from 'react';
import { getAllWarehouses, deleteWarehouses } from '../../../api/admin/warehouse';
import styles from '../../../styles/admin/warehouse/WarehouseDelete.module.css';
import { useNavigate, Link } from 'react-router-dom';

const WarehousesDelete = () => {
  const [warehouses, setWarehouses] = useState([]);
  const [loading, setLoading] = useState(true);
  const [sortColumn, setSortColumn] = useState(null);
  const [sortOrder, setSortOrder] = useState('asc');
  const [selectedIds, setSelectedIds] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchWarehouses = async () => {
      try {
        const data = await getAllWarehouses();
        setWarehouses(data);
      } catch (error) {
        console.error("센터 데이터를 불러오는 데 실패했습니다.", error);
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
      alert('삭제할 센터를 선택해주세요.');
      return;
    }

    if (!window.confirm('정말 삭제하시겠습니까?')) {
      return;
    }

    try {
      await Promise.all(selectedIds.map((id) => deleteWarehouses(id)));
      setSelectedIds([]);
      const data = await getAllWarehouses();
      setWarehouses(data);
      alert('선택된 협력사들이 삭제되었습니다.');
      navigate('/admin/warehouse');
    } catch (error) {
      console.error('삭제 중 오류가 발생했습니다.', error);
      alert('삭제 실패!');
    }
  };

  const sortedWarehouses = [...warehouses].sort((a, b) => {
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
	    <Link to="/admin/warehouse">돌아가기</Link>
	    <div onClick={handleDelete}>삭제</div>
	  </div>
      <div className={styles.warehouseTable}>
        <div className={`${styles.row} ${styles.header}`}>
          <div className={`${styles.cell}`}></div>
		  <div onClick={() => handleSort('warehouse_id')} className={`${styles.cell} ${sortColumn === 'warehouse_id' ? styles.active : ''}`}>번호<span className={`${styles.TableMenuT}`}>▼</span></div>
		  <div onClick={() => handleSort('warehouse_name')} className={`${styles.cell} ${sortColumn === 'warehouse_name' ? styles.active : ''}`}>센터명<span className={`${styles.TableMenuT}`}>▼</span></div>
		  <div onClick={() => handleSort('warehouse_location')} className={`${styles.cell} ${sortColumn === 'warehouse_location' ? styles.active : ''}`}>주소<span className={`${styles.TableMenuT}`}>▼</span></div>
		  <div onClick={() => handleSort('warehouse_phone')} className={`${styles.cell} ${sortColumn === 'warehouse_phone' ? styles.active : ''}`}>전화번호<span className={`${styles.TableMenuT}`}>▼</span></div>
		  <div onClick={() => handleSort('warehouse_email')} className={`${styles.cell} ${sortColumn === 'warehouse_email' ? styles.active : ''}`}>이메일<span className={`${styles.TableMenuT}`}>▼</span></div>
		  <div onClick={() => handleSort('warehouse_created_at')} className={`${styles.cell} ${sortColumn === 'warehouse_created_at' ? styles.active : ''}`}>등록된 날짜<span className={`${styles.TableMenuT}`}>▼</span></div>
		  <div onClick={() => handleSort('warehouse_updated_at')} className={`${styles.cell} ${sortColumn === 'warehouse_updated_at' ? styles.active : ''}`}>수정된 날짜<span className={`${styles.TableMenuT}`}>▼</span></div>
        </div>

        {sortedWarehouses.map((warehouses) => (
			<div
			  key={warehouses.warehouse_id}
			  className={`${styles.row}`}
			>
			  <div
			    className={`${styles.cell} ${styles.checkCell} ${
			      selectedIds.includes(warehouses.warehouse_id) ? styles.selectedRow : ''
			    }`}
			    onClick={() => toggleCheckboxByCellClick(warehouses.warehouse_id)}
			  >
			    <input
			      type="checkbox"
			      checked={selectedIds.includes(warehouses.warehouse_id)}
			      readOnly
			      className={styles.hiddenCheckbox}
			    />
			    <span className={styles.customCheckbox}></span>
			  </div>
            <div className={styles.cell}>{warehouses.warehouse_id}</div>
            <div className={styles.cell}>{warehouses.warehouse_name}</div>
            <div className={styles.cell}>{warehouses.warehouse_location}</div>
            <div className={styles.cell}>{warehouses.warehouse_phone}</div>
            <div className={styles.cell}>{warehouses.warehouse_email}</div>
            <div className={styles.cell}>{warehouses.warehouse_created_at}</div>
            <div className={styles.cell}>{warehouses.warehouse_updated_at}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default WarehousesDelete;

