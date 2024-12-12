package ru.otus.hw.dao;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Repository;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;
import ru.otus.hw.exceptions.QuestionReadException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvQuestionDao.class);

    private final TestFileNameProvider fileNameProvider;

    @Override
    public List<Question> findAll() {
        // Использовать CsvToBean
        // https://opencsv.sourceforge.net/#collection_based_bean_fields_one_to_many_mappings
        // Использовать QuestionReadException
        // Про ресурсы: https://mkyong.com/java/java-read-a-file-from-resources-folder/

        final ColumnPositionMappingStrategy<QuestionDto> strategy = new ColumnPositionMappingStrategy<>();
        strategy.setType(QuestionDto.class);
        strategy.setColumnMapping(new String[]{"text","answers"});
        try (Reader reader = Files.newBufferedReader(
                Paths.get(getResource(fileNameProvider.getTestFileName()).getURI()))
        ) {
            CsvToBean<QuestionDto> rawQuestions = new CsvToBeanBuilder<QuestionDto>(reader)
                    .withSeparator(';')
                    .withMappingStrategy(strategy)
                    .build();
            return rawQuestions.stream().map(QuestionDto::toDomainObject).collect(Collectors.toList());
        } catch (Exception e) {
            LOGGER.error("Could not get read from resource ", fileNameProvider.getTestFileName(), e);
            throw new QuestionReadException("Could not read from resource ", e);
        }

    }

    private Resource getResource(String name) {
        ResourceLoader loader = new DefaultResourceLoader();
        return  loader.getResource(name);
    }
}
