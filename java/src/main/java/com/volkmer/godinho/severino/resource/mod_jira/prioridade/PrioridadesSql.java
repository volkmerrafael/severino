package com.volkmer.godinho.severino.resource.mod_jira.prioridade;

public class PrioridadesSql {

	public String buscaSqlPrioridades(String filtros) {
		
		String sql = 
				" SELECT project_name, pkey, pkey||'-'||issuenum as issuenum, summary, cliente, /*issuetype_name, "+
			       " issuestatus_name, priority,*/ pendenteem, "+
				   " (case "+
				     " when ano_restante = 'N/A' then 0 "+
				     " else "+
				       " case "+
				         " when (DATE_PART('epoch', CAST(ano_restante::interval AS INTERVAL)) >= DATE_PART('epoch', CAST(tempo_desenvolvimento::interval AS INTERVAL)))   "+
				            " or (DATE_PART('epoch', CAST(ano_restante::interval AS INTERVAL)) = 0) then  "+
				            " -1 "+
				         " when DATE_PART('epoch', CAST(ano_restante::interval AS INTERVAL)) < DATE_PART('epoch', CAST(tempo_desenvolvimento::interval AS INTERVAL)) then "+
				            " (DATE_PART('epoch', CAST(ano_restante::interval AS INTERVAL)) / "+
				              " DATE_PART('epoch', CAST(tempo_desenvolvimento::interval AS INTERVAL))) /* tempo de sobra */ "+
				         " else -1 "+
				       " end "+
				   " end * 100)::numeric(15,2) t, "+
			       " REPLACE(REPLACE(REPLACE(REPLACE(ano_restante, 'days', 'dias'), 'day', 'dia'), 'months', 'meses'), 'month', 'mês') as ano_restante, "+
			       " tempo_desenvolvimento, "+
			       " issueid, project,  "+
			       " CASE "+
			         " WHEN TRIM(SISTEMA) = '1' THEN 'WinRural' "+
			         " WHEN TRIM(SISTEMA) = '2' THEN 'WinSaude' "+
			         " WHEN TRIM(SISTEMA) = '3' THEN 'WinSocial' "+
			         " WHEN TRIM(SISTEMA) = '4' THEN 'CadUni' "+
			         " WHEN TRIM(SISTEMA) = '5' THEN 'Laboratório' "+
			         " WHEN TRIM(SISTEMA) = '6' THEN 'IDS Educação' "+
			         " WHEN TRIM(SISTEMA) = '7' THEN 'IDS Indicadores' "+
			         " WHEN TRIM(SISTEMA) = '8' THEN 'IDS Esporte e Lazer' "+
			         " WHEN TRIM(SISTEMA) = '9' THEN 'IDS Cultura' "+
			         " WHEN TRIM(SISTEMA) = '10' THEN 'IDS Saúde' "+
			         " WHEN TRIM(SISTEMA) = '11' THEN 'IDS Social' "+
			         " WHEN TRIM(SISTEMA) = '12' THEN 'IDS Cidadão' "+
			         " WHEN TRIM(SISTEMA) = '13' THEN 'Portal do Cidadão' "+
			         " WHEN TRIM(SISTEMA) = '14' THEN 'HTML5' "+
			         " WHEN TRIM(SISTEMA) = '15' THEN 'IDSSaudeWS' "+
			         " WHEN TRIM(SISTEMA) = '16' THEN 'IDSEsfWS' "+
			         " WHEN TRIM(SISTEMA) = '17' THEN 'Gestor Integrado' "+
			         " WHEN TRIM(SISTEMA) = '18' THEN 'Mais Saúde Cidadão' "+
			         " WHEN TRIM(SISTEMA) = '19' THEN 'IDS ESF' "+
			         " WHEN TRIM(SISTEMA) = '20' THEN 'VSMobile' "+
			         " WHEN TRIM(SISTEMA) = '51' THEN 'IDS Gestor' "+
			         " WHEN TRIM(SISTEMA) = '91' THEN 'IDS Saúde - Java/Flex' "+
			         " WHEN TRIM(SISTEMA) = '92' THEN 'IDS Gestor - Java/Flex' "+
			         " WHEN TRIM(SISTEMA) = '93' THEN 'IDS Saúde - Genexus' "+
			         " WHEN TRIM(SISTEMA) = '94' THEN 'IDS Gestor - Genexus' "+
			         " WHEN TRIM(SISTEMA) = '95' THEN 'Outros' "+
			         " WHEN TRIM(SISTEMA) = '96' THEN 'A auxiliares Delphi' "+
			         " WHEN TRIM(SISTEMA) = '97' THEN 'AdmIDS' "+
			         " WHEN TRIM(SISTEMA) = '98' THEN 'Finaceiro' "+
			         " WHEN TRIM(SISTEMA) = '99' THEN 'IDS Core Java/Flex' "+
			         " ELSE SISTEMA  "+
			       " END AS sistema,  "+
			       " classificacao,  "+
			       " CASE  "+
			         " WHEN interna_externa = '10204' THEN 'Externa' "+
			         " WHEN interna_externa = '10205' THEN 'Interna' "+
			         " ELSE 'Sem Tipo' "+
			       " END AS interna_externa, "+
					 " CASE "+
					   " WHEN classificacao = '10400' THEN "+
					    " 'Erro de Desenvolvimento' "+
					   " WHEN classificacao = '10401' THEN "+
					    " 'Erro de Análise' "+
					   " WHEN classificacao = '10403' THEN "+
					    " 'Erro de Banco de Dados' "+
					   " WHEN classificacao = '10404' THEN "+
					    " 'Erro de Suporte' "+
					   " WHEN classificacao = '10406' THEN "+
					    " 'Melhoria de Produto' "+
					   " WHEN classificacao = '10407' THEN  "+
					    " 'Melhoria de Produto' "+
					   " WHEN classificacao = '10408' THEN "+
					    " 'Melhoria de Produto' "+
					   " WHEN classificacao = '10409' THEN "+
					    " 'Melhoria de Produto' "+
					   " WHEN classificacao = '10410' THEN  "+
					    " 'Erro de Usuário' "+
					   " WHEN classificacao = '10412' THEN "+
					    " 'Melhoria de Produto' "+
					   " WHEN classificacao = '10413' THEN "+
					    " 'Alteração Legal' "+
					   " WHEN classificacao = '10500' THEN "+
					    " 'Erro de Implantação' "+
					   " WHEN classificacao = '10600' THEN "+
					    " 'Erro de Infraestrutura' "+
					   " WHEN classificacao = '10601' THEN "+
					    " 'Erro de Sistemas Externos' "+
					 " ELSE 'Sem Classificação' "+
					 " END AS tipo_classificacao,   "+
			       " assignee, creator, issuetype,  "+
			       " priorityid,  "+
			       " resolution, issuestatus,   "+
			       " created, updated, duedate, resolutiondate,   "+
			       " timespent, "+
			       " REPLACE(REPLACE(REPLACE(REPLACE(ano_estouro, 'days', 'dias'), 'day', 'dia'), 'months', 'meses'), 'month', 'mês') as ano_estouro, "+
			       " tempo_atendimento "+
			" FROM ( "+
			" SELECT issue.id AS issueid, "+
			       " issue.issuenum, "+
			       " issue.project, project.pname AS project_name, "+
			       " project.pkey,  "+
			       " (SELECT TRIM( "+
			         " REPLACE( "+
			         " REPLACE( "+
			         " REPLACE( "+
			         " REPLACE( "+
			         " REPLACE( "+
			         " regexp_replace(cfv.textvalue, '\r|\n', '', ''), "+
			         " E'\n',''), "+
			         " '<content>', ''), "+
			         " '</content>', ''), "+
			         " '<value>', ''), "+
			         " '</value>', '')) "+
			          " FROM customfield cf "+
			          " JOIN customfieldvalue cfv "+
			           " ON (cf.id = cfv.customfield) "+
			        " WHERE (cf.cfname like '%Sistema%') "+
			          " AND (cfv.issue = issue.id)) AS Sistema, "+
			       " COALESCE( "+
			       " TRIM(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE(REGEXP_REPLACE( "+
			       " COALESCE( "+
			  	      " (SELECT CFV.TEXTVALUE "+
			             " FROM CUSTOMFIELDVALUE CFV "+
			           " WHERE (CFV.CUSTOMFIELD = 10300) "+
			             " AND (CFV.ISSUE = ISSUE.ID)), "+
			          " COALESCE( "+
			          " (SELECT CUSTOMFIELDVALUE.TEXTVALUE "+
			             " FROM JIRAISSUE "+
			             " JOIN CUSTOMFIELDVALUE "+
			              " ON (JIRAISSUE.ID = CUSTOMFIELDVALUE.ISSUE) "+
			             " AND (CUSTOMFIELDVALUE.CUSTOMFIELD = 10300) "+
			           " WHERE (JIRAISSUE.ID = ( "+
			                  " SELECT SOURCE  "+
			                    " FROM ISSUELINK "+
			                  " WHERE (ISSUELINK.DESTINATION = ( "+
			                  " SELECT SOURCE "+
			                    " FROM ISSUELINK "+
			                  " WHERE (ISSUELINK.DESTINATION = ISSUE.ID) ORDER BY SEQUENCE DESC LIMIT 1)) ORDER BY SEQUENCE DESC LIMIT 1))), "+
			          " COALESCE( "+
			           " (SELECT CUSTOMFIELDVALUE.TEXTVALUE "+
			             " FROM JIRAISSUE "+
			             " JOIN CUSTOMFIELDVALUE "+
			              " ON (JIRAISSUE.ID = CUSTOMFIELDVALUE.ISSUE) "+
			             " AND (CUSTOMFIELDVALUE.CUSTOMFIELD = 10300) "+
			           " WHERE (JIRAISSUE.ID = ( "+
			                 " SELECT DESTINATION "+
			                   " FROM ISSUELINK "+
			                 " WHERE (LINKTYPE = 10003) "+
			                   " AND (SOURCE = ( "+
			                        " SELECT SOURCE "+
			                          " FROM ISSUELINK "+
			                        " WHERE (DESTINATION = ISSUE.ID) AND (LINKTYPE = 10100))) ORDER BY SEQUENCE DESC LIMIT 1))), "+
			           " (SELECT CUSTOMFIELDVALUE.TEXTVALUE "+
			              " FROM JIRAISSUE "+
			              " JOIN CUSTOMFIELDVALUE "+
			               " ON (JIRAISSUE.ID = CUSTOMFIELDVALUE.ISSUE) "+
			              " AND (CUSTOMFIELDVALUE.CUSTOMFIELD = 10300) "+
			            " WHERE (JIRAISSUE.ID =  "+
			                  " (SELECT DESTINATION "+
			                     " FROM ISSUELINK "+
			                  " WHERE (SOURCE = ISSUE.ID) AND (LINKTYPE = 10003) ORDER BY SEQUENCE DESC LIMIT 1)))))),  "+
			                  " '\r|\n', '', ''), E'\n',''), '<content>', ''), '</content>', ''), '<value>', ''), '</value>', ''), '&amp;', '&')), "+
			                  " 'IDS Desenvolvimento de Software & Assessoria Ltda.') AS CLIENTE,           "+
			       " COALESCE(TRIM( "+
			       " (SELECT cfv.stringvalue "+
			       " FROM customfield cf "+
			      " LEFT JOIN customfieldvalue cfv "+
			        " ON (cf.id = cfv.customfield) "+
			     " WHERE (cf.cfname like 'Classifica%') "+
			       " AND (cfv.issue = issue.id))), '0') AS Classificacao, "+
			       " COALESCE(TRIM( "+
			       " (SELECT cfv.stringvalue "+
			       " FROM customfield cf "+
			      " LEFT JOIN customfieldvalue cfv "+
			        " ON (cf.id = cfv.customfield) "+
			     " WHERE (cf.id = 10204) "+
			       " AND (cfv.issue = issue.id))), '0') AS Interna_Externa, "+
			       " issue.assignee, issue.creator,  "+
			       " issue.issuetype, COALESCE(issuetype.pname, 'Sem tipo') AS issuetype_name, "+
			       " issue.summary,   "+
			       " CASE "+
			        " WHEN issue.priority = '1' THEN 'Urgente' "+
			        " WHEN issue.priority = '2' THEN 'Alta' "+
			        " WHEN issue.priority = '3' THEN 'Media' "+
			        " WHEN issue.priority = '4' THEN 'Baixa' "+
			        " WHEN issue.priority = '5' THEN 'Indeterminada' "+
			        " ELSE issue.priority "+
			       " END AS priority, issue.priority as priorityId, "+
			       " CASE "+
			        " WHEN issue.resolution IS NULL THEN 'Pendente' "+
			        " WHEN issue.resolution = '1' THEN 'Corrigido' "+
			        " WHEN issue.resolution = '2' THEN 'Cancelado' "+
			        " ELSE issue.resolution "+
			       " END AS resolution, "+
			       " issue.issuestatus, issuestatus.pname AS issuestatus_name, "+
			       " issue.created, issue.updated, issue.duedate,  "+
			       " COALESCE(   "+
						" (SELECT G.CREATED "+
						  " FROM CHANGEGROUP G "+
						  " JOIN CHANGEITEM I "+
						   " ON (G.ID = I.GROUPID) "+
						" WHERE (G.ISSUEID = issue.id) "+
						  " AND (I.FIELD = 'status') "+
						  " AND (I.NEWVALUE IN ('10206')) "+
						" ORDER BY G.CREATED DESC LIMIT 1), issue.resolutiondate) as resolutiondate, "+
			       " (SELECT cfv.datevalue "+
			            " FROM customfield cf "+
			            " JOIN customfieldvalue cfv "+
			             " ON (cf.id = cfv.customfield) "+
			          " WHERE (cf.id = 11410) "+
			            " AND (cfv.issue = issue.id)) as PendenteEm,        "+
			       " CAST(CAST(issue.timespent AS VARCHAR) AS INTERVAL) AS timespent, "+
			       " calcular_ano(issue.id, 'N') as ano_restante, "+
			       " calcular_ano(issue.id, 'S') as ano_estouro, "+
			       " case "+
			         " when cast(issue.created as date) <= '31/12/2017' then "+
				       " case "+
				         " when issue.priority = '1' then '04:00:00' "+
				         " when issue.priority = '2' then '07:00:00' "+
				         " when issue.priority = '3' then '16:00:00' "+
				         " when issue.priority = '4' then '22:00:00' "+
				       " else '00:00:00' "+
			           " end "+
			         " else  "+
				       " case "+
				         " when issue.priority = '1' then '04:00:00' "+
				         " when issue.priority = '2' then '08:00:00' "+
				         " when issue.priority = '3' then '24:00:00' "+
				         " when issue.priority = '4' then '40:00:00' "+
				       " else '00:00:00' "+
			           " end "+
			       " end as tempo_desenvolvimento, "+
			       " case "+
			         " when issue.priority = '1' then '02:00:00' "+
			         " when issue.priority = '2' then '03:00:00' "+
			         " when issue.priority = '3' then '08:00:00' "+
			         " when issue.priority = '4' then '10:00:00' "+
			         " else '00:00:00' "+
			       " end as tempo_atendimento "+
			  " FROM jiraissue issue "+
			  " LEFT JOIN issuetype "+
			   " ON (issue.issuetype = issuetype.id) "+
			  " LEFT JOIN issuestatus "+
			   " ON (issue.issuestatus = issuestatus.id) "+
			  " LEFT JOIN project "+
			   " ON (issue.project = project.id) "+
			" where (issue.issuetype = '10308') "+
			  " and (issue.issuestatus in ('10200', '10204', '10002', '10004'))) X  "+
			" where (x.interna_externa = '10204') "+
			  " and (X.PKEY in ("+filtros+")) "+
			" order by t, pendenteem ";
		
		return sql;
		
	}
	
}
