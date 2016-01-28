package jp.dip.fission.database.entity;

import jp.dip.fission.annotation.Column;
import jp.dip.fission.annotation.Table;

public class User {

	@Table(name="user")
	public String user;

	@Column(name="user_id")
	public String userId;

}
