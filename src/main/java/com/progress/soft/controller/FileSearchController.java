package com.progress.soft.controller;

import com.progress.soft.config.AppConfig;
import com.progress.soft.service.TransactionOperationService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by inshaf on 10/21/17.
 */

@Controller
public class FileSearchController {

    private Log logger = LogFactory.getLog(UploadController.class);

    @RequestMapping(value = "/searchFile", method = RequestMethod.GET)
    public String getDetails(HttpServletRequest request, HttpServletResponse response, ModelMap model) {

        //logs debug message
        if(logger.isDebugEnabled()){
            logger.debug("File details invoked");
        }
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        TransactionOperationService transactionService = context.getBean(TransactionOperationService.class);

        if(logger.isDebugEnabled()){
            logger.debug("Count : " + transactionService.listOfDistinctFile().size());
        }

        //Getting list of distinct file from the database
        model.put("fileList", transactionService.listOfDistinctFile());

        return "fileSearch";
    }


    @RequestMapping(value = "/getNoOfRecords", method = RequestMethod.GET)
    public @ResponseBody
    String getRowCount(@RequestParam("fileName") String fileName, HttpServletRequest request, HttpServletResponse response) {

        if(logger.isDebugEnabled()){
            logger.debug("File name: "+fileName);
        }

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(AppConfig.class);

        TransactionOperationService transactionService = context.getBean(TransactionOperationService.class);

        //return no of records for given file
        long rowsCount = transactionService.getRecordCountForFileName(fileName);

        return Long.toString(rowsCount);
    }
}
