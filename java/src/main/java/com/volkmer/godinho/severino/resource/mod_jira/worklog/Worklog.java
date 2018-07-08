package com.volkmer.godinho.severino.resource.mod_jira.worklog;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.volkmer.godinho.severino.entity.mod_jira.Issue;

import lombok.Getter;
import lombok.Setter;

@Table
@Getter @Setter
@Entity
public class Worklog {

		private Issue issue;
		private Long id;
		private String startdate;
		private BigDecimal timeworked;
		private String autor;
		
}