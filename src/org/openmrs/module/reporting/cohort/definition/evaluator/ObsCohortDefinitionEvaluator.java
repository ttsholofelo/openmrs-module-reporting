/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.reporting.cohort.definition.evaluator;

import java.util.Date;

import org.openmrs.Cohort;
import org.openmrs.annotation.Handler;
import org.openmrs.api.context.Context;
import org.openmrs.module.reporting.cohort.definition.CohortDefinition;
import org.openmrs.module.reporting.cohort.definition.ObsCohortDefinition;
import org.openmrs.module.reporting.cohort.query.service.CohortQueryService;
import org.openmrs.module.reporting.evaluation.EvaluationContext;

/**
 * Evaluates an ObsCohortDefinition and produces a Cohort
 */
@Handler(supports={ObsCohortDefinition.class})
public class ObsCohortDefinitionEvaluator implements CohortDefinitionEvaluator {

	/**
	 * Default Constructor
	 */
	public ObsCohortDefinitionEvaluator() {}
	
	/**
     * @see CohortDefinitionEvaluator#evaluateCohort(CohortDefinition, EvaluationContext)
     */
    public Cohort evaluate(CohortDefinition cohortDefinition, EvaluationContext context) {
    	ObsCohortDefinition ocd = (ObsCohortDefinition) cohortDefinition;
		
		Date fromDate = ocd.getCalculatedFromDate(context);
    	Date toDate = ocd.getCalculatedToDate(context);
    	Integer questionId = ocd.getQuestion() == null ? null : ocd.getQuestion().getConceptId();
  
    	return Context.getService(CohortQueryService.class).getPatientsHavingObs(
    			questionId, ocd.getTimeModifier(), ocd.getModifier(), ocd.getValue(), fromDate, toDate, null, ocd.getEncounterType());
    }
}