package com.cs.campsite.customer.entity;

import java.time.LocalDate;

import com.cs.campsite.member.entity.Member;
import com.cs.campsite.member.entity.Room;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
@Table(name="reservations")
public class Reservation {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reservationNo;
	
	@Column(nullable = false)
	private LocalDate reservationCheckInDate;
	
	@Column(nullable = false)
	private LocalDate reservationCheckOutDate;
	
	@Column(nullable = false)
	private int reservationTotalPrice;
	
	private String reservationStatus;
	
	private LocalDate reservationCreatedAt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_no", nullable = false)
	private Member memberNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "room_no", nullable = false)
	private Room roomNo;

	@Builder
	public Reservation(LocalDate reservationCheckInDate, LocalDate reservationCheckOutDate, int reservationTotalPrice,
			String reservationStatus, LocalDate reservationCreatedAt, Member memberNo, Room roomNo) {
		super();
		this.reservationCheckInDate = reservationCheckInDate;
		this.reservationCheckOutDate = reservationCheckOutDate;
		this.reservationTotalPrice = reservationTotalPrice;
		this.reservationStatus = reservationStatus;
		this.reservationCreatedAt = reservationCreatedAt;
		this.memberNo = memberNo;
		this.roomNo = roomNo;
	}
	
	

}