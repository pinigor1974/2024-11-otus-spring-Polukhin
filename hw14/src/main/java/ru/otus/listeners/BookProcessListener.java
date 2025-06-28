package ru.otus.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;
import ru.otus.domain.Book;
import ru.otus.export.ExportedBook;

@Component
public class BookProcessListener implements ItemProcessListener<Book,ExportedBook>{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(BookProcessListener.class);

	@Override
	public void beforeProcess(Book item) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Before processing book ID " + item.getId());
		}
	}

	@Override
	public void afterProcess(Book item, ExportedBook result) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer processing Book  " + item + " to " + result);
		}
		
	}

	@Override
	public void onProcessError(Book item, Exception e) {
		LOGGER.warn("Fail to process book with ID " + item.getId() + e.getCause());
	}

}
