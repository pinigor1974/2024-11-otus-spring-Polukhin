package ru.otus.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;


@Component
public class BatchExecutionJobListener implements JobExecutionListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchExecutionJobListener.class);

	@Override
	public void beforeJob(JobExecution jobExecution) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug(" jobExecution job Id " + jobExecution.getJobId() + " jobExecutionContext " + jobExecution.getExecutionContext());
		}
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Finished jobExecution job Id " + jobExecution.getJobId());
		}
		
	}
	
	
	

}
