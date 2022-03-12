package com.dwu.alonealong.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "TOGETHER_MEMBER")
@SequenceGenerator(
		name = "TOGMEM_SEQ_GENERATOR"
		, sequenceName = "TOGMEM_ID_SEQ"
		, initialValue = 1
		, allocationSize = 1
)
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TogetherMember implements Serializable {
	public static final int IS_NOT_HOST = 0;
	public static final int IS_HOST = 1;
	
	@Id
	@Column(name = "togmem_id")
	private String togetherMemberId;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "tog_id")
	private String togetherId;
	@Column(name = "is_host")
	private int isHost;
	
	@ManyToOne
	@JoinColumn(name="user_id", insertable=false, updatable=false)
	private User user;
	
	public TogetherMember(String togetherMemberId, String userId, String togetherId,  int isHost) {
		super();
		this.togetherMemberId = togetherMemberId;
		this.userId = userId;
		this.togetherId = togetherId;
		this.isHost = isHost;
	}
	
}
