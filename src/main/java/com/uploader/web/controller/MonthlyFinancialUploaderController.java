package com.uploader.web.controller;

//import com.google.gson.JsonArray;
//import com.google.gson.JsonObject;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

//import javax.inject.Inject;
//import java.sql.Timestamp;
//import java.util.*;

@CrossOrigin(origins = "*")
@RestController
public class MonthlyFinancialUploaderController
{
	private static Logger logger = LoggerFactory.getLogger(MonthlyFinancialUploaderController.class);

	@RequestMapping("/")
	String helloWorld()
	{
		return "Hello World!";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/processFiles", produces=MediaType.APPLICATION_JSON_VALUE)
	String uploadFiles(@RequestParam("files") MultipartFile[] files)
	{
		try
		{
			Arrays.asList(files).stream().forEach(file -> {
				try {
					logger.info("\"" + file.getOriginalFilename() + "\"");
					BufferedReader fileReader = new BufferedReader(new InputStreamReader(file.getInputStream()));
					CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true);
					// deprecated CSVFormat options
//					CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());
					CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.builder().setHeader().setSkipHeaderRecord(true).setIgnoreHeaderCase(true).setTrim(true).build());

					for (CSVRecord csvRecord : csvParser.getRecords())
					{
						logger.info("\"" + csvRecord.get("Date") + "\"");
						logger.info("\"" + csvRecord.get("Transaction") + "\"");
						logger.info("\"" + csvRecord.get("Name") + "\"");
						logger.info("\"" + csvRecord.get("Memo") + "\"");
						logger.info("\"" + csvRecord.get("Amount") + "\"");
					}
//					logger.info(csvParser.getRecords().toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
		}
		catch (Exception e)
		{
			logger.info(e.getLocalizedMessage());
		}

		return "{ \"something\": \"something\" }";
	}
}