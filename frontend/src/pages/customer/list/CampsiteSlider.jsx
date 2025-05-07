import React from 'react';
import CampsiteCard from './CampsiteCard';

function CampsiteSlider({ title, data }) {
  return (
    <section style={{ marginBottom: '40px' }}>
      <h3 style={{ marginBottom: '12px' }}>{title}</h3>

      {/* ✅ 카드 4~5개만 보이고 나머지는 화면 밖으로 감춤 */}
      <div
        style={{
          display: 'flex',
          flexWrap: 'nowrap',           // ❌ 줄바꿈 금지
          overflow: 'hidden',           // ❌ 넘치는 카드 숨김
          gap: '20px',
          maxWidth: '100%',             // or 원하는 고정값 (예: '1200px')
        }}
      >
        {data.map((camp, index) => {
          const safeTitle = title.replace(/\s+/g, '-').toLowerCase(); // ✅ 슬라이더 제목을 안전하게 변환
          return (
            <div
              key={`slider-${safeTitle}-${camp.campsiteNo}-${index}`} // ✅ 고유한 key로 변경 (슬라이더별 캠핑장 구분)
              style={{
                flex: '0 0 22%',           // 약 4~5개만 보이도록 설정
                maxWidth: '250px',
                display: index < 5 ? 'block' : 'none' // 👈 아주 명확하게 제어도 가능
              }}
            >
              <CampsiteCard campsite={camp} />
            </div>
          );
        })}
      </div>
    </section>
  );
}

export default CampsiteSlider;
