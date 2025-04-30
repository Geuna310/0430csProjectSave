/*import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { updateCampsite, getCampsite } from "../../../../api/campsite";

function CampsiteUpdateTest() {
  const { no } = useParams(); // 문자열로 들어옴
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    campsiteName: "",
    address: "",
    phone: "",
    // 필요한 필드는 여기에 계속 추가하세요
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const campsiteNo = parseInt(no); // 🔥 int로 변환해서 백엔드와 일치
        const response = await getCampsite(campsiteNo);

        // 데이터 구조에 따라 수정 필요
        // ex) response.data가 객체면 그대로, response가 객체면 response로
        console.log("불러온 캠핑장 정보:", response);
        setFormData(response); 
      } catch (error) {
        console.error("데이터 불러오기 실패", error);
        alert("캠핑장 정보를 불러오지 못했습니다.");
      }
    };

    fetchData();
  }, [no]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");

    try {
      const campsiteNo = parseInt(no);
      await updateCampsite(campsiteNo, formData, token);
      alert("수정이 완료되었습니다.");
      navigate("/company");
    } catch (error) {
      console.error("수정 실패", error);
      alert("수정에 실패했습니다.");
    }
  };

  return (
    <form onSubmit={handleSubmit} style={{ maxWidth: "600px", margin: "0 auto" }}>
      <h2>캠핑장 정보 수정</h2>

      <div style={{ marginBottom: "1rem" }}>
        <label>캠핑장 이름</label><br />
        <input
          type="text"
          name="campsiteName"
          placeholder="캠핑장 이름 입력"
          value={formData.campsiteName}
          onChange={handleChange}
          required
        />
      </div>

      <div style={{ marginBottom: "1rem" }}>
        <label>주소</label><br />
        <input
          type="text"
          name="campsiteLocation"
          placeholder="주소 입력"
          value={formData.campsiteLocatio}
          onChange={handleChange}
          required
        />
      </div>
      <div style={{ marginBottom: "1rem" }}>
        <label>상세 설명</label><br />
        <input
          type="text"
          name="campsiteDescription"
          placeholder="상세 설명 입력"
          value={formData.campsiteDescription}
          onChange={handleChange}
          required
        />
      </div>
      <div style={{ marginBottom: "1rem" }}>
        <label>상세 설명</label><br />
        <input
          type="text"
          name="campsiteImageUrl"
          placeholder="이미지"
          value={formData.campsiteImageUrl}
          onChange={handleChange}
          required
        />
      </div>
      <div style={{ marginBottom: "1rem" }}>
        <label>사업자 번호</label><br />
        <input
          type="text"
          name="campsitesBusinessNumber"
          placeholder="사업자 번호 입력"
          value={formData.campsitesBusinessNumber}
          onChange={handleChange}
          required
        />
      </div>

      <button type="submit" style={{ padding: "0.5rem 1rem" }}>
        수정하기
      </button>
    </form>
  );
}

export default CampsiteUpdateTest;*/