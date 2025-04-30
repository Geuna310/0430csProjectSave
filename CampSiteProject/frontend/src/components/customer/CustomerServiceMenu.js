import React from "react";
import { Link } from "react-router-dom";
import "../../styles/DropdownMenu.css";

function CustomerServiceMenu() {
	return (
		<div className="dropdown">
			<button className="dropbtn">고객센터</button>
			<div className="dropdown-content">
				<Link to="/support/contact">문의하기</Link>
				<Link to="/support/notice">공지사항</Link>
				<Link to="/support/join">입점 문의</Link>
			</div>
		</div>
	);
}

export default CustomerServiceMenu;