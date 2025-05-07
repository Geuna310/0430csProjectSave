package com.cs.campsite.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Entity
@Table(name = "rooms_table")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Room {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roomNo;
	
	@Setter
	@Column(nullable = false)
	private String roomName;
	
	@Setter
	@Column(nullable = false)
	private int roomPrice;
	
	@Setter
	@Column(nullable = false)
	private int roomPeakPrice;
	
	@Setter
	@Column(nullable = false)
	private int roomCapacity;
	
	@Enumerated
	private Onoff roomOnoff;
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "section_no")
	private Section section;
	
	@Setter
	private String roomDescription;
	
	@Setter
	@Column(nullable = false)
	private int roomQuantity;
	
	
	@ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name = "campsite_no")
	private Campsite campsite;


	public Room(String roomName, int roomPrice, int roomPeakPrice, int roomCapacity, Onoff roomOnoff,
			String roomDescription, int roomQuantity) {
		super();
		this.roomName = roomName;
		this.roomPrice = roomPrice;
		this.roomPeakPrice = roomPeakPrice;
		this.roomCapacity = roomCapacity;
		this.roomOnoff = roomOnoff;
		this.roomDescription = roomDescription;
		this.roomQuantity = roomQuantity;
	}

	
	
	
}