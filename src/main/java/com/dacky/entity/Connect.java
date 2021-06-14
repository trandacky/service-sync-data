package com.dacky.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.relational.core.mapping.Column;
@Entity
@Table(name="connect")
public class Connect {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;

	
	@Column
	private String driverStart;
	@Column
	private String urlStart;
	@Column
	private String tableNameTemp;
	@Column
	private String usernameStart;
	@Column
	private String passwordStart;
	
	@Column
	private String driverEnd;
	@Column
	private String urlEnd;
	@Column
	private String usernameEnd;
	@Column
	private String passwordEnd;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDriverStart() {
		return driverStart;
	}
	public void setDriverStart(String driverStart) {
		this.driverStart = driverStart;
	}
	public String getUrlStart() {
		return urlStart;
	}
	public void setUrlStart(String urlStart) {
		this.urlStart = urlStart;
	}
	public String getTableNameTemp() {
		return tableNameTemp;
	}
	public void setTableNameTemp(String tableNameTemp) {
		this.tableNameTemp = tableNameTemp;
	}
	public String getUsernameStart() {
		return usernameStart;
	}
	public void setUsernameStart(String usernameStart) {
		this.usernameStart = usernameStart;
	}
	public String getPasswordStart() {
		return passwordStart;
	}
	public void setPasswordStart(String passwordStart) {
		this.passwordStart = passwordStart;
	}
	public String getDriverEnd() {
		return driverEnd;
	}
	public void setDriverEnd(String driverEnd) {
		this.driverEnd = driverEnd;
	}
	public String getUrlEnd() {
		return urlEnd;
	}
	public void setUrlEnd(String urlEnd) {
		this.urlEnd = urlEnd;
	}
	public String getUsernameEnd() {
		return usernameEnd;
	}
	public void setUsernameEnd(String usernameEnd) {
		this.usernameEnd = usernameEnd;
	}
	public String getPasswordEnd() {
		return passwordEnd;
	}
	public void setPasswordEnd(String passwordEnd) {
		this.passwordEnd = passwordEnd;
	}
	
	
	
}
