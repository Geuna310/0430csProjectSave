import React from "react";
import { Link, useLocation } from "react-router-dom";
import "../../styles/DropdownMenu.css";

function MyPageMenu({ isLoggedIn, onLogout }) {
	const location = useLocation(); // 현재 페이지 정보 가져오기

	return (
		<div className="dropdown">
			<button className="dropbtn">마이페이지</button>
			<div className="dropdown-content">
				{isLoggedIn ? (
					<>
						<button onClick={onLogout}>로그아웃</button>	{/*button 사용한 이유는 router 기능이 아니라 함수 호출 등 token, role 삭제하기 위함*/}
			            <Link to="/customer/menu/edit">회원 정보 수정</Link>
			            <Link to="/pages/customer/myPage/Reservation">예약 확인</Link>
			            <Link to="/pages/customer/myPage/Favorites">찜 목록</Link>
			            <Link to="/pages/customer/myPage/History">이용 내역</Link>
					</>
								) : (
					<>
					{/* 현재 위치를 state로 함께 넘기기 */}
						<Link to="/login" state={{ from: location }}>로그인</Link>
						<Link to="/signup">회원가입</Link>
					</>
					)}
			</div>
		</div>
		);
}

export default MyPageMenu;