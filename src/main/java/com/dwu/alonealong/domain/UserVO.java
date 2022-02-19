package com.dwu.alonealong.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="userinfo")
@NoArgsConstructor
@AllArgsConstructor
public class UserVO  implements Serializable {
    @Id
    @Column(name="user_id")
    private String userId;
    private String nickname;
}
