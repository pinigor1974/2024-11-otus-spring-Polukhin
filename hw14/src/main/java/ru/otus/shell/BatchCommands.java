package ru.otus.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.services.ImputStreamService;

import static ru.otus.config.JobConfig.*;

@RequiredArgsConstructor
@ShellComponent
public class BatchCommands {

	private final Job exportBookJob;
	private final Job exportAuthorJob;
	private final Job exportGenreJob;

	private final JobLauncher jobLauncher;
	private final JobExplorer jobExplorer;
	private final ImputStreamService ioService;

	@ShellMethod(value = "startMigrationAuthors", key = "smb-job")
	public void startMigrationBooksJob() throws Exception {
		ioService.outputString("Execute job name " + exportBookJob.getName());
		JobExecution execution = jobLauncher.run(exportBookJob, new JobParametersBuilder().toJobParameters());
		
	}

	@ShellMethod(value = "startMigrationAuthors", key = "sma-job")
	public void startMigrationAuthorsJob() throws Exception {
		ioService.outputString("Execute job name " + exportAuthorJob.getName());
		JobExecution execution = jobLauncher.run(exportAuthorJob, new JobParametersBuilder().toJobParameters());
		
	}

	@ShellMethod(value = "startMigrationGenres", key = "smg-job")
	public void startMigrationGenresJob() throws Exception {
		ioService.outputString("Execute job name " + exportGenreJob.getName());
		JobExecution execution = jobLauncher.run(exportGenreJob, new JobParametersBuilder().toJobParameters());
	}


	@ShellMethod(value = "showInfo", key = "i")
	public void showInfo() {
		jobExplorer.getJobNames().stream().map(s -> s.toString()).forEach(ioService::outputString);

	}

	@ShellMethod(value = "showInfoBookJob", key = "i-bj")
	public void showInfoBookJob() {
		ioService.outputString(jobExplorer.getLastJobInstance(EXPORT_BOOKS_JOB).toString());
	}

	@ShellMethod(value = "showInfoAuthorsJob", key = "i-aj")
	public void showInfoAuthorJob() {
		ioService.outputString(jobExplorer.getLastJobInstance(EXPORT_AUTHORS_JOB).toString());
	}

	@ShellMethod(value = "showInfoGenresJob", key = "i-gj")
	public void showInfoGenreJob() {
		ioService.outputString(jobExplorer.getLastJobInstance(EXPORT_GENRES_JOB).toString());
	}

}
