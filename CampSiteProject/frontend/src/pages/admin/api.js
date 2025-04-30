import React, { useState, useEffect } from 'react';

const AddressSearch = () => {
  // 선택된 주소를 저장할 상태 변수
  const [selectedAddress, setSelectedAddress] = useState('');

  // useEffect 훅을 사용하여 다움 우편번호 API의 스크립트를 동적으로 로드
  useEffect(() => {
    // 다움 우편번호 API 스크립트를 동적으로 생성
    const script = document.createElement('script');
    script.src = "//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"; // 다움 우편번호 API의 URL
    script.async = true; // 비동기로 스크립트를 로드

    // 스크립트가 로드되면 실행되는 함수 (onload 이벤트)
    script.onload = () => {
      console.log('Daum Postcode script loaded'); // 스크립트 로드 성공시 콘솔에 메시지 출력
    };

    // 스크립트 로드 중 오류가 발생하면 실행되는 함수 (onerror 이벤트)
    script.onerror = () => {
      console.error('Failed to load Daum Postcode script'); // 오류 발생 시 콘솔에 에러 메시지 출력
    };

    // 동적으로 로드한 스크립트를 문서의 <head>에 추가
    document.head.appendChild(script);

    // 컴포넌트가 언마운트되면 스크립트를 제거하는 클린업 함수
    return () => {
      document.head.removeChild(script); // 컴포넌트가 언마운트될 때 추가한 스크립트 제거
    };
  }, []); // 빈 배열을 의존성 배열로 전달하여 최초 한 번만 실행되도록 설정

  // 우편번호 검색 팝업을 여는 함수
  const openPostcodePopup = () => {
    // `window.daum` 객체가 존재할 때만 실행 (다움 API가 로드된 경우)
    if (window.daum) {
      // 다움 우편번호 API의 Postcode 객체를 생성하여 팝업을 엶
      new window.daum.Postcode({
        oncomplete: function (data) {
          // 검색이 완료되었을 때 실행되는 콜백 함수
          const fullAddress = data.address; // 검색된 주소 정보를 가져옴
          setSelectedAddress(fullAddress); // 선택된 주소를 상태에 저장
        }
      }).open(); // 우편번호 검색 팝업을 오픈
    } else {
      // 만약 `window.daum`이 로드되지 않았다면 오류 메시지를 출력
      console.error('Daum Postcode API not loaded yet');
    }
  };

  return (
    <div>
      <h2>주소 검색</h2>
      {/* 주소 검색 버튼 클릭 시 openPostcodePopup 함수 실행 */}
      <button onClick={openPostcodePopup}>주소 검색</button>

      {/* 선택된 주소가 있을 경우 이를 화면에 표시 */}
      {selectedAddress && (
        <div>
          선택된 주소: <strong>{selectedAddress}</strong>
        </div>
      )}
    </div>
  );
};

export default AddressSearch;
