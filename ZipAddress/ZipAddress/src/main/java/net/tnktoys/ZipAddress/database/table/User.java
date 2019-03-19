package net.tnktoys.ZipAddress.database.table;

import net.tnktoys.ZipAddress.database.annotation.Column;
import net.tnktoys.ZipAddress.database.annotation.Table;

@Table(name="User",comment="User Table")
public class User extends AbstractTable {
	@Column(name="id",type = String.class,primary=1,notnull=true,number=20,decimal=0,comment="user table id")
	public String id;
	@Column(name="name",type = String.class,notnull=true,number=20,decimal=0,comment="user table name")
	public String name;
	@Column(name="password",type = String.class,notnull=true,number=20,decimal=0,comment="user table password")
	public String password;
}
