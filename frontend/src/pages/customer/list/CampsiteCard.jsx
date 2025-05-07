import { useNavigate } from 'react-router-dom';

function CampsiteCard({ campsite }) {
  const navigate = useNavigate();
  
/*  console.log("카드 데이터 확인 👉", campsite);
  console.log("categoryTags:", campsite.categoryTags);
  console.log("facilityTags:", campsite.facilityTags);*/

  return (
    <div
      onClick={() => {
        // 리뷰일 경우 리뷰 상세로, 아니면 캠핑장 상세로 이동
        if (campsite.reviewNo) {
          navigate(`/reviews/${campsite.reviewNo}`);
        } else {
          navigate(`/customer/menu/campsite/${campsite.campsiteNo}`);
        }
      }}
      style={{
        width: '220px',
        border: '1px solid #ccc',
        borderRadius: '12px',
        padding: '12px',
        cursor: 'pointer',
        flexShrink: 0
      }}
    >
      <img
        src={campsite.campsiteImageUrl}
        alt={campsite.campsiteName}
        style={{ width: '100%', height: '140px', objectFit: 'cover', borderRadius: '8px' }}
      />
      <h4>{campsite.campsiteName}</h4>

      {/* 리뷰일 경우 별점 및 텍스트 표시 */}
      {campsite.reviewRating !== undefined && (
        <p>⭐ {campsite.reviewRating}점</p>
      )}
      {campsite.reviewText && (
        <p style={{ fontSize: '12px' }}>{campsite.reviewText}</p>
      )}

      {/* 캠핑장일 경우 위치 출력 */}
      {campsite.campsiteLocation && (
        <p>{campsite.campsiteLocation}</p>
      )}

      {/* ✅ 카테고리 태그가 있는 경우 출력 (이색 캠핑장용) */}
      {Array.isArray(campsite.categoryTags) && (
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '4px', fontSize: '12px' }}>
          {campsite.categoryTags.map((tag, i) => (
            <span key={i}>#{tag}</span>
          ))}
        </div>
      )}

      {/* ✅ 카테고리 태그가 없고, facilityTags가 있을 경우 출력 (추천 캠핑장, 리뷰용) */}
      {!campsite.categoryTags && Array.isArray(campsite.facilityTags) && (
        <div style={{ display: 'flex', flexWrap: 'wrap', gap: '4px', fontSize: '12px' }}>
          {campsite.facilityTags.map((tag, i) => (
            <span key={i}>#{tag}</span>
          ))}
        </div>
      )}
    </div>
  );
}

export default CampsiteCard;
