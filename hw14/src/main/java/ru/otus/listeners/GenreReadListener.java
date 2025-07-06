package ru.otus.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.stereotype.Component;

import ru.otus.domain.Genre;

@Component
public class GenreReadListener implements ItemReadListener<Genre> {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GenreReadListener.class);

	@Override
	public void beforeRead() {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.info("Start reading Genre ");
		}
		
	}

	@Override
	public void afterRead(Genre item) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Genre with id read successfully " + item.getId());
		}
		
	}

	@Override
	public void onReadError(Exception ex) {
		LOGGER.warn("Error during Genre Reading " + ex.getCause());
		
	}

}
