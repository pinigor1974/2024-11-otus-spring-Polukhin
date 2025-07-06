package ru.otus.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;

import ru.otus.domain.Author;
import ru.otus.export.ExportedAuthor;

@Component
public class AuthorProcessListener implements ItemProcessListener<Author,ExportedAuthor> {

	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorProcessListener.class);
	
	@Override
	public void beforeProcess(Author item) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Before processing Author ID " + item.getId());
		}
		
	}

	@Override
	public void afterProcess(Author item, ExportedAuthor result) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer processing Author  " + item + " to " + result);
		}
		
	}

	@Override
	public void onProcessError(Author item, Exception e) {
		LOGGER.warn("Fail to process Author with ID " + item.getId() + e.getCause());
		
	}

}
