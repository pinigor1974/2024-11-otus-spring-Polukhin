package ru.otus.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.stereotype.Component;
import ru.otus.domain.Genre;
import ru.otus.export.ExportedGenre;


@Component
public class GenreProcessListener implements ItemProcessListener<Genre,ExportedGenre>  {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GenreProcessListener.class);

	@Override
	public void beforeProcess(Genre item) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Before processing Genre ID " + item.getId());
		}
		
	}

	@Override
	public void afterProcess(Genre item, ExportedGenre result) {
		if(LOGGER.isDebugEnabled()) {
			LOGGER.debug("Transfer processing Genre  " + item + " to " + result);
		}
		
		
	}

	@Override
	public void onProcessError(Genre item, Exception e) {
		LOGGER.warn("Fail to process Genre with ID " + item.getId() + e.getCause());
		
	}
	
	

}
