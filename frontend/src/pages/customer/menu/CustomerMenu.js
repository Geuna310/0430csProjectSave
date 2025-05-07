import React from 'react';
import { Link } from 'react-router-dom';
import '../../../styles/index.css';
import '../../../styles/CompanyMenu.css';

function CustomerMenu() {
	const makeAnimatedText = (text, delayStart) => text.split('').map((char, i) => (
		<span key={i} className="letter" style={{ transitionDelay: `${(delayStart + i) * 0.1}s` }}>
			{char === ' ' ? '\u00A0' : char}
		</span>
));

	return (
		<div className="company-menu-wrapper">
			<div className="camping-hover-area">
				<span className="camping-title">캠핑장</span>
				<span className="camping-submenu">
					<Link to="/customer/menu/CustomerRegister" className="word-link">
						{makeAnimatedText('( 오토캠핑', 3)}
					</Link>
					<span className="spacer">&nbsp;</span>
					<Link to="/customer/menu/update" className="word-link">
						{makeAnimatedText('글램핑', 8)}
					</Link>
					<span className="spacer">&nbsp;</span>
					<Link to="/customer/menu/delete" className="word-link">
						{makeAnimatedText('카라반 )', 13)}
					</Link>
				</span>
			</div>
			<div className="camping-hover-area">
				<span className="camping-title">고객센터</span>
				<span className="camping-submenu">
					<Link to="/customer/menu/rentalShop" className="word-link">
						{makeAnimatedText('( 장비 렌탈', 3)}
					</Link>
					<span className="spacer">&nbsp;</span>
					<Link to="/customer/menu/securityGuide" className="word-link">
						{makeAnimatedText('안전수칙', 8)}
					</Link>
					<span className="spacer">&nbsp;</span>
					<Link to="/customer/menu/customerService/review" className="word-link">
						{makeAnimatedText('리뷰', 13)}
					</Link>
					<span className="spacer">&nbsp;</span>
					<Link to="/customer/menu/event" className="word-link">
						{makeAnimatedText('이벤트 )', 18)}
					</Link>
				</span>
			</div>
		</div>
			);
}

export default CustomerMenu;