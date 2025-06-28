package ru.otus.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import ru.otus.domain.Author;


@Component
public class AuthorReadListener implements ItemReadListener<Author>  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthorReadListener.class);

	@Override
	public void beforeRead() {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.info("Start reading Author ");
		}
		
	}

	@Override
	public void afterRead(Author item) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Author with id read successfully " + item.getId());
		}
		
	}

	@Override
	public void onReadError(Exception ex) {
		LOGGER.warn("Error during Author Reading " + ex.getCause());
		
	}

}
