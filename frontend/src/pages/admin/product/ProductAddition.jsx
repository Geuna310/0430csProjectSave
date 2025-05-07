import { useState } from 'react';
import { saveProducts } from '../../../api/admin/product';
import styles from '../../../styles/admin/supplier/SupplierAddition.module.css';
import { useNavigate, Link } from 'react-router-dom';

const ProductsAddition = () => {
	const navigate = useNavigate();
  const [productRows, setProductRows] = useState([
    {
      name: '',
      price: '',
	  productSupplierId: ''
    }
  ]);

  const addProductRow = () => {
    setProductRows(prev => [
      ...prev,
	  {
	    name: '',
	    price: '',
	    productSupplierId: ''
	  }
    ]);
  };

  const handleInputChange = (index, field, value) => {
    const updatedRows = [...productRows];
    updatedRows[index][field] = value;
    setProductRows(updatedRows);
  };
  
  const handleSave = async () => {
    // 비어있는 필드가 하나라도 있으면 제외
    const filledProducts = productRows.filter(row =>
      row.name &&
      row.price &&
      row.supplierId
    );

    if (filledProducts.length === 0) {
      alert("입력된 협력사 정보가 없습니다. 모든 항목을 채워주세요.");
      return;
    }

    try {
      for (const Product of filledProducts) {
        await saveProducts({
          product_name: Product.name,
          product_price: Product.price,
          supplier_id: Product.supplierId
        });
      }
      alert("저장 완료!");
	  navigate('/admin/product');
    } catch (error) {
      alert("저장 중 오류가 발생했습니다.");
      console.error(error);
    }
  };

  const handleKeyDown = (e) => {
    if (e.key === 'Enter') {
      e.preventDefault();

	  const filledProducts = productRows.filter(row =>
		row.name &&
		row.price &&
		row.supplierId
	  );

	  if (filledProducts.length === 0) {
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
        <Link to="/admin/supplier">돌아가기</Link>
        <div onClick={handleSave}>추가하기</div>
      </div>

	  
	  
      <div className={styles.supplierTable}>
        <div className={`${styles.row} ${styles.header}`}>
          <div className={styles.cell}>물건명<span className={styles.TableMenuT}>▼</span></div>
          <div className={styles.cell}>가격<span className={styles.TableMenuT}>▼</span></div>
          <div className={styles.cell}>협력사<span className={styles.TableMenuT}>▼</span></div>
        </div>

        {productRows.map((product, index) => (
          <div key={index} className={styles.row}>
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="물건명"
                value={product.name}
                onChange={(e) => handleInputChange(index, 'name', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
            <div className={styles.cell}>
              <input
                type="number"
                placeholder="가격"
                value={product.price}
                onChange={(e) => handleInputChange(index, 'price', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
            <div className={styles.cell}>
              <input
                type="text"
                placeholder="협력사"
                value={product.productSupplierId}
                onChange={(e) => handleInputChange(index, 'supplierId', e.target.value)}
				onKeyDown={(e) => handleKeyDown(e)}
              />
            </div>
          </div>
        ))}
      </div>

      <div className={styles.menuButtonBottom}>
        <div onClick={addProductRow}>+</div>
      </div>
    </div>
  );
};

export default ProductsAddition;