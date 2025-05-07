import { useState } from 'react';
import { Link } from 'react-router-dom';
import { saveSupplier } from '../../../api/admin/supplier';
import styles from '../../../styles/admin/supplier/SupplierAddition.module.css';
import { useNavigate } from 'react-router-dom';

const SupplierAddition = () => {
	const navigate = useNavigate();
  const [supplierRows, setSupplierRows] = useState([
    {
      name: '',
      contactPerson: '',
      contactPhone: '',
      phone: '',
      email: '',
      address: '',
      type: '',
      status: '활성',
      account: ''
    }
  ]);

  const addSupplierRow = () => {
    setSupplierRows(prev => [
      ...prev,
      {
        name: '',
        contactPerson: '',
        contactPhone: '',
        phone: '',
        email: '',
        address: '',
        type: '',
        status: '활성',
        account: ''
      }
    ]);
  };

  const handleInputChange = (index, field, value) => {
    const updatedRows = [...supplierRows];
    updatedRows[index][field] = value;
    setSupplierRows(updatedRows);
  };
  
  const handleSave = async () => {
    // 비어있는 필드가 하나라도 있으면 제외
    const filledSuppliers = supplierRows.filter(row =>
      row.name &&
      row.contactPerson &&
      row.contactPhone &&
      row.phone &&
      row.email &&
      row.address &&
      row.type &&
      row.status &&
      row.account
    );

    if (filledSuppliers.length === 0) {
      alert("입력된 협력사 정보가 없습니다. 모든 항목을 채워주세요.");
      return;
    }

    try {
      for (const supplier of filledSuppliers) {
        await saveSupplier({
          supplier_name: supplier.name,
          supplier_contact_person: supplier.contactPerson,
          supplier_contact_phone: supplier.contactPhone,
          supplier_phone: supplier.phone,
          supplier_email: supplier.email,
          supplier_address: supplier.address,
          supplier_type: supplier.type,
          supplier_status: supplier.status,
          supplier_bank_account: supplier.account,
        });
      }
      alert("저장 완료!");
	  navigate('/admin/supplier');
    } catch (error) {
      alert("저장 중 오류가 발생했습니다.");
      console.error(error);
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      e.preventDefault();

	  const filledSuppliers = supplierRows.filter(row =>
	    row.name &&
	    row.contactPerson &&
	    row.contactPhone &&
	    row.phone &&
	    row.email &&
	    row.address &&
	    row.type &&
	    row.status &&
	    row.account
	  );

	  if (filledSuppliers.length === 0) {
	    alert("입력된 협력사 정보가 없습니다. 모든 항목을 채워주세요.");
	    return;
	  }

      handleSave();
    }
  };
  
  
  return (
    <div className={styles.body}>
      <h2 style={{ marginTop: '0px' }}>협력사 목록</h2>

      <div className={styles.menuButton}>
        <div onClick={handleSave}>추가하기</div>
        <Link to="/admin/supplier">돌아가기</Link>
      </div>

      <div className={styles.supplierTable}>
        <div className={`${styles.row} ${styles.header}`}>
          <div className={styles.cell}>협력사<span className={styles.TableMenuT}>▼</span></div>
          <div className={styles.cell}>담당자<span className={styles.TableMenuT}>▼</span></div>
          <div className={styles.cell}>담당자 전화번호<span className={styles.TableMenuT}>▼</span></div>
          <div className={styles.cell}>협력사 전화번호<span className={styles.TableMenuT}>▼</span></div>
          <div className={styles.cell}>이메일<span className={styles.TableMenuT}>▼</span></div>
          <div className={styles.cell}>주소<span className={styles.TableMenuT}>▼</span></div>
          <div className={styles.cell}>타입<span className={styles.TableMenuT}>▼</span></div>
          <div className={styles.cell}>상태<span className={styles.TableMenuT}>▼</span></div>
          <div className={styles.cell}>계좌<span className={styles.TableMenuT}>▼</span></div>
        </div>

        {supplierRows.map((supplier, index) => (
          <div key={index} className={styles.row}>
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="협력사"
                value={supplier.name}
                onChange={(e) => handleInputChange(index, 'name', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="담당자"
                value={supplier.contactPerson}
                onChange={(e) => handleInputChange(index, 'contactPerson', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="담당자 전화번호"
                value={supplier.contactPhone}
                onChange={(e) => handleInputChange(index, 'contactPhone', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="전화번호"
                value={supplier.phone}
                onChange={(e) => handleInputChange(index, 'phone', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="이메일"
                value={supplier.email}
                onChange={(e) => handleInputChange(index, 'email', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="주소"
                value={supplier.address}
                onChange={(e) => handleInputChange(index, 'address', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="타입"
                value={supplier.type}
                onChange={(e) => handleInputChange(index, 'type', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
            <div className={styles.cell}>
              <select
                value={supplier.status}
                onChange={(e) => handleInputChange(index, 'status', e.target.value)}
              >
                <option value="활성">활성</option>
                <option value="비활성">비활성</option>
              </select>
            </div>
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="계좌"
                value={supplier.account}
                onChange={(e) => handleInputChange(index, 'account', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
          </div>
        ))}
      </div>

      <div className={styles.menuButtonBottom}>
        <div onClick={addSupplierRow}>+</div>
      </div>
    </div>
  );
};

export default SupplierAddition;