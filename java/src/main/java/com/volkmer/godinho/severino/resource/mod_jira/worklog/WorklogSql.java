package com.volkmer.godinho.severino.resource.mod_jira.worklog;

public class WorklogSql {

	public String buscaSqlWorklog(String data, String author) {
		
		String sql = 
				
				" select " + 
				"project.pkey||'-'||jiraissue.issuenum issue, " + 
				"jiraissue.summary, " + 
				"worklog.startdate, " +
				"(sum(worklog.timeworked/60)/60)::numeric(15,2) timeworked " +
				"from worklog  " + 
				"join jiraissue on jiraissue.id = worklog.issueid " + 
				"join project on project.id = jiraissue.project " + 
				"where to_char(startdate,'yyyy-MM-dd') = '"+data+"' "+
				"and author = '"+author+"' " + 
				"group by 1,2,3 "+
				"order by startdate";
		
		return sql;
		
	}
	
}
