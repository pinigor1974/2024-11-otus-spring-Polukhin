package ru.otus.listeners;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import ru.otus.export.ExportedAuthor;

@Component
public class ExportedAuthorWriteListener implements ItemWriteListener<ExportedAuthor> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportedAuthorWriteListener.class);

	@Override
	public void beforeWrite(List<? extends ExportedAuthor> items) {
		if(LOGGER.isDebugEnabled()) {
			items.forEach(item -> LOGGER.debug("Process before writing ExportedAuthor ID " + item.getId()));
		}
		
	}

	@Override
	public void afterWrite(List<? extends ExportedAuthor> items) {
		if(LOGGER.isDebugEnabled()) {
			items.forEach(item -> LOGGER.debug("Process ater writing ExportedAuthor ID " + item.getId()));
		}
		
	}

	@Override
	public void onWriteError(Exception exception, List<? extends ExportedAuthor> items) {
		LOGGER.warn("Could not process list of Items ...");
		items.forEach(item -> LOGGER.warn("ExportedAuthor ID " + item.getId()));
		
	}

}
