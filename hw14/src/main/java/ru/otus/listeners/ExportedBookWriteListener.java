package ru.otus.listeners;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;
import ru.otus.export.ExportedBook;

@Component
public class ExportedBookWriteListener implements ItemWriteListener<ExportedBook> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportedBookWriteListener.class);

	@Override
	public void beforeWrite(List<? extends ExportedBook> items) {
		if(LOGGER.isDebugEnabled()) {
			items.forEach(item -> LOGGER.debug("Process before writing ExportedBook ID " + item.getId()));
		}
	}

	@Override
	public void afterWrite(List<? extends ExportedBook> items) {
		if(LOGGER.isDebugEnabled()) {
			items.forEach(item -> LOGGER.debug("Process ater writing ExportedBook ID " + item.getId()));
		}
	}

	@Override
	public void onWriteError(Exception exception, List<? extends ExportedBook> items) {
		LOGGER.warn("Could not process list of Items ...");
		items.forEach(item -> LOGGER.warn("ExportedBook ID " + item.getId()));
	}



}
