import React, { useEffect, useState } from 'react';
import { getAllSuppliers, deleteSupplier } from '../../../api/admin/supplier';
import styles from '../../../styles/admin/supplier/SupplierDelete.module.css';
import { useNavigate } from 'react-router-dom';

const SupplierDelete = () => {
  const [suppliers, setSuppliers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [sortColumn, setSortColumn] = useState(null);
  const [sortOrder, setSortOrder] = useState('asc');
  const [selectedIds, setSelectedIds] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchSuppliers = async () => {
      try {
        const data = await getAllSuppliers();
        setSuppliers(data);
      } catch (error) {
        console.error("협력사 데이터를 불러오는 데 실패했습니다.", error);
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
      await Promise.all(selectedIds.map((id) => deleteSupplier(id)));
      setSelectedIds([]);
      const data = await getAllSuppliers();
      setSuppliers(data);
      alert('선택된 협력사들이 삭제되었습니다.');
      navigate('/admin/supplier');
    } catch (error) {
      console.error('삭제 중 오류가 발생했습니다.', error);
      alert('삭제 실패!');
    }
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
      return sortOrder === 'asc' ? aValue - bValue : bValue - aValue;
    }
  });

  if (loading) {
    return <div>로딩 중...</div>;
  }

  return (
    <div className={styles.body}>
      <h2 style={{ marginTop: '0px' }}>협력사 목록</h2>
      <div className={styles.deleteButton}>
        <div onClick={handleDelete}>삭제</div>
      </div>
      <div className={styles.supplierTable}>
        <div className={`${styles.row} ${styles.header}`}>
          <div className={`${styles.cell}`}></div>
          <div onClick={() => handleSort('supplier_id')} className={`${styles.cell} ${sortColumn === 'supplier_id' ? styles.active : ''}`}>번호<span className={styles.TableMenuT}>▼</span></div>
          <div onClick={() => handleSort('supplier_name')} className={`${styles.cell} ${sortColumn === 'supplier_name' ? styles.active : ''}`}>협력사<span className={styles.TableMenuT}>▼</span></div>
          <div onClick={() => handleSort('supplier_contact_person')} className={`${styles.cell} ${sortColumn === 'supplier_contact_person' ? styles.active : ''}`}>담당자<span className={styles.TableMenuT}>▼</span></div>
          <div onClick={() => handleSort('supplier_contact_phone')} className={`${styles.cell} ${sortColumn === 'supplier_contact_phone' ? styles.active : ''}`}>담당자 전화번호<span className={styles.TableMenuT}>▼</span></div>
          <div onClick={() => handleSort('supplier_phone')} className={`${styles.cell} ${sortColumn === 'supplier_phone' ? styles.active : ''}`}>협력사 전화번호<span className={styles.TableMenuT}>▼</span></div>
          <div onClick={() => handleSort('supplier_email')} className={`${styles.cell} ${sortColumn === 'supplier_email' ? styles.active : ''}`}>이메일<span className={styles.TableMenuT}>▼</span></div>
          <div onClick={() => handleSort('supplier_address')} className={`${styles.cell} ${sortColumn === 'supplier_address' ? styles.active : ''}`}>주소<span className={styles.TableMenuT}>▼</span></div>
          <div onClick={() => handleSort('supplier_type')} className={`${styles.cell} ${sortColumn === 'supplier_type' ? styles.active : ''}`}>타입<span className={styles.TableMenuT}>▼</span></div>
          <div onClick={() => handleSort('supplier_status')} className={`${styles.cell} ${sortColumn === 'supplier_status' ? styles.active : ''}`}>상태<span className={styles.TableMenuT}>▼</span></div>
          <div onClick={() => handleSort('supplier_bank_account')} className={`${styles.cell} ${sortColumn === 'supplier_bank_account' ? styles.active : ''}`}>계좌<span className={styles.TableMenuT}>▼</span></div>
          <div onClick={() => handleSort('supplier_created_at')} className={`${styles.cell} ${sortColumn === 'supplier_created_at' ? styles.active : ''}`}>등록된 날짜<span className={styles.TableMenuT}>▼</span></div>
          <div onClick={() => handleSort('supplier_updated_at')} className={`${styles.cell} ${sortColumn === 'supplier_updated_at' ? styles.active : ''}`}>수정된 날짜<span className={styles.TableMenuT}>▼</span></div>
        </div>

        {sortedSuppliers.map((supplier) => (
			<div
			  key={supplier.supplier_id}
			  className={`${styles.row}`}
			>
			  <div
			    className={`${styles.cell} ${styles.checkCell} ${
			      selectedIds.includes(supplier.supplier_id) ? styles.selectedRow : ''
			    }`}
			    onClick={() => toggleCheckboxByCellClick(supplier.supplier_id)}
			  >
			    <input
			      type="checkbox"
			      checked={selectedIds.includes(supplier.supplier_id)}
			      readOnly
			      className={styles.hiddenCheckbox}
			    />
			    <span className={styles.customCheckbox}></span>
			  </div>
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
        ))}
      </div>
    </div>
  );
};

export default SupplierDelete;

