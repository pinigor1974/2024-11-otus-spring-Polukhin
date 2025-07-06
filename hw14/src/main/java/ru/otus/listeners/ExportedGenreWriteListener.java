package ru.otus.listeners;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemWriteListener;
import org.springframework.stereotype.Component;

import ru.otus.export.ExportedGenre;

@Component
public class ExportedGenreWriteListener implements ItemWriteListener<ExportedGenre>  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ExportedGenreWriteListener.class);

	@Override
	public void beforeWrite(List<? extends ExportedGenre> items) {
		if(LOGGER.isDebugEnabled()) {
			items.forEach(item -> LOGGER.debug("Process before writing ExportedGenre ID " + item.getId()));
		}
		
	}

	@Override
	public void afterWrite(List<? extends ExportedGenre> items) {
		if(LOGGER.isDebugEnabled()) {
			items.forEach(item -> LOGGER.debug("Process ater writing ExportedGenre ID " + item.getId()));
		}
		
	}

	@Override
	public void onWriteError(Exception exception, List<? extends ExportedGenre> items) {
		LOGGER.warn("Could not process list of Items ...");
		items.forEach(item -> LOGGER.warn("ExportedGenre ID " + item.getId()));
		
	}

}
