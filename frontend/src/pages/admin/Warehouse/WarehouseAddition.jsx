import { useState } from 'react';
import { Link } from 'react-router-dom';
import { saveWarehouses } from '../../../api/admin/warehouse';
import styles from '../../../styles/admin/warehouse/WarehouseAddition.module.css';
import { useNavigate } from 'react-router-dom';

const WarehousesAddition = () => {
	const navigate = useNavigate();
  const [warehousesRows, setWarehousesRows] = useState([
    {
      name: '',
      location: '',
      phone: '',
      email: ''
    }
  ]);

  const addWarehousesRow = () => {
    setWarehousesRows(prev => [
      ...prev,
      {
		name: '',
		location: '',
		phone: '',
		email: ''
      }
    ]);
  };

  const handleInputChange = (index, field, value) => {
    const updatedRows = [...warehousesRows];
    updatedRows[index][field] = value;
    setWarehousesRows(updatedRows);
  };
  
  const handleSave = async () => {
    // 비어있는 필드가 하나라도 있으면 제외
    const filledWarehouses = warehousesRows.filter(row =>
      row.name &&
      row.location &&
      row.phone &&
      row.email
    );

    if (filledWarehouses.length === 0) {
      alert("입력된 협력사 정보가 없습니다. 모든 항목을 채워주세요.");
      return;
    }

    try {
      for (const warehouses of filledWarehouses) {
        await saveWarehouses({
          warehouse_name: warehouses.name,
          warehouse_location: warehouses.location,
          warehouse_phone: warehouses.phone,
          warehouse_email: warehouses.email,
        });
      }
      alert("저장 완료!");
	  navigate('/admin/warehouse');
    } catch (error) {
      alert("저장 중 오류가 발생했습니다.");
      console.error(error);
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      e.preventDefault();

	  const filledWarehouses = warehousesRows.filter(row =>
		row.name &&
		row.location &&
		row.phone &&
		row.email
	  );

	  if (filledWarehouses.length === 0) {
	    alert("입력된 협력사 정보가 없습니다. 모든 항목을 채워주세요.");
	    return;
	  }

      handleSave();
    }
  };
  
  
  return (
    <div className={styles.body}>
      <h2 style={{ marginTop: '0px' }}>센터 목록</h2>

      <div className={styles.menuButton}>
        <Link to="/admin/warehouse">돌아가기</Link>
        <div onClick={handleSave}>추가하기</div>
      </div>

      <div className={styles.warehouseTable}>
        <div className={`${styles.row} ${styles.header}`}>
          <div className={styles.cell}>센터명<span className={styles.TableMenuT}>▼</span></div>
          <div className={styles.cell}>주소<span className={styles.TableMenuT}>▼</span></div>
          <div className={styles.cell}>전화번호<span className={styles.TableMenuT}>▼</span></div>
          <div className={styles.cell}>이메일<span className={styles.TableMenuT}>▼</span></div>
        </div>

        {warehousesRows.map((Warehouses, index) => (
          <div key={index} className={styles.row}>
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="센터명"
                value={Warehouses.name}
                onChange={(e) => handleInputChange(index, 'name', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
			
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="주소"
                value={Warehouses.location}
                onChange={(e) => handleInputChange(index, 'location', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
			
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="전화번호"
                value={Warehouses.phone}
                onChange={(e) => handleInputChange(index, 'phone', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
			
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="이메일"
                value={Warehouses.email}
                onChange={(e) => handleInputChange(index, 'email', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
          </div>
        ))}
      </div>

      <div className={styles.menuButtonBottom}>
        <div onClick={addWarehousesRow}>+</div>
      </div>
    </div>
  );
};

export default WarehousesAddition;