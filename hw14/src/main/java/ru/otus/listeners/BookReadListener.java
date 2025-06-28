package ru.otus.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import ru.otus.domain.Book;

@Component
public class BookReadListener implements ItemReadListener<Book> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BookReadListener.class);

	@Override
	public void beforeRead() {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.info("Start reading book ");
		}
		
	}

	@Override
	public void afterRead(Book item) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Book with id read successfully " + item.getId());
		}
	}

	@Override
	public void onReadError(Exception ex) {
		LOGGER.warn("Error during Book Reading " + ex.getCause());
	}

}
