import React from 'react';
import { Link } from 'react-router-dom';
import '../../../styles/index.css';
import '../../../styles/CompanyMenu.css';

function CompanyMenu() {
	const makeAnimatedText = (text, delayStart) => text.split('').map((char, i) => (
		<span key={i} className="letter" style={{ transitionDelay: `${(delayStart + i) * 0.1}s` }}>
			{char === ' ' ? '\u00A0' : char}
		</span>
	));

	return (
		<div className="company-menu-wrapper">
			<div className="camping-hover-area">
				<span className="camping-title">캠핑장 정보</span>
				<span className="camping-submenu">
					<Link to="/company/menu/myCampsite/CampsiteRegister" className="word-link">
						{makeAnimatedText('( 등록하기', 3)}
					</Link>
					<span className="spacer">&nbsp;</span>
					<Link to="/company/menu/myCampsite/MyCampsiteList" className="word-link">
						{makeAnimatedText('조회하기 )', 8)}
					</Link>
				</span>
			</div>

			<div className="camping-hover-area">
				<span className="camping-title">고객센터</span>
				<span className="camping-submenu">
					<Link to="/customer/inquiry" className="word-link">
						{makeAnimatedText('( 문의하기', 3)}
					</Link>
					<span className="spacer">&nbsp;</span>
					<Link to="/customer/notice" className="word-link">
						{makeAnimatedText('공지사항 )', 8)}
					</Link>
				</span>
			</div>
		</div>
	);
}

export default CompanyMenu;